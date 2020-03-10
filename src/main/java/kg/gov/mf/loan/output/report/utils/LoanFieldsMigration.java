package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.loan.Description;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.loan.SupervisorPlan;
import kg.gov.mf.loan.manage.service.loan.DescriptionService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.loan.SupervisorPlanService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.persistence.EntityManager;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class LoanFieldsMigration {

    @Autowired
    LoanService loanService;

    @Autowired
    DescriptionService descriptionService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    SupervisorPlanService supervisorPlanService;

    @Autowired
    UserService userService;

    Set<String> errorList = new HashSet<String>();
    HashMap<Long,Long> userMap=new HashMap<>();

    private Connection getSourceConnection(String ip,String database,String username,String password)
    {
        Connection connection = null;
        try
        {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e)
        {
            errorList.add(" Class for name error"+e);
            return null;
        }

        try
        {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://"+ip+":5432/"+database, username,
                    password);
        }
        catch (SQLException e)
        {
            errorList.add("connection error"+e);
            return null;
        }

        return connection;
    }
    /*
    ip:150.0.0.89
    dbName:migration2019;
    username:postgres;
    password:armad27raptor
    */

//    migrate loan fields(deposit_freed,item.inspection_needed,profitDescription,normalDescription)
    public void migrateLoanField(String ip,String database,String username,String password){
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        String loanIds="";
        Connection connection=this.getSourceConnection(ip,database,username,password);

        Session session;
        try{
            session=sessionFactory.getCurrentSession();
        }
        catch (Exception e){
            session=sessionFactory.openSession();
        }

        try{
            Statement st = connection.createStatement();
            ResultSet detailsResultSet=st.executeQuery("select credit_id,details,act_freed,deposit_freed from credit_details");
            while (detailsResultSet.next())
            {
                try{
                    Loan loan=loanService.getByVersion(detailsResultSet.getLong("credit_id"));
                    if(detailsResultSet.getShort("act_freed")==1){
                        loanIds=loanIds+loan.getId()+",";
                    }
                    if(detailsResultSet.getShort("deposit_freed")==1){
                        loan.setDepositFreed(true);
                    }
                    if(loan.getNormalDescription()==null){
                        Description description=new Description();
                        description.setText(detailsResultSet.getString("details"));
                        descriptionService.add(description);
                        loan.setNormalDescription(description);
                        loanService.update(loan);
                    }
                    if(loan.getProfitDescription()==null){
                        Statement profitDescStatement=connection.createStatement();
                        ResultSet profitDescription=profitDescStatement.executeQuery("select st.type_name,cd.profit_details\n" +
                                "from system_type st,credit_details cd where st.group_id=48 and cd.credit_profit_type=st.type_id and cd.credit_id="+detailsResultSet.getLong("credit_id"));
                        Description description=new Description();
                        String text="";
                        if (profitDescription!=null){
                            while (profitDescription.next()){
                                text=profitDescription.getString("type_name");
                                if(profitDescription.getString("profit_details")!=null ){
                                    if(profitDescription.getString("profit_details").equals(""))
                                        text=text+" , "+profitDescription.getString("profit_details");
                                }
                            }
                            description.setText(text);
                            descriptionService.add(description);
                            loan.setProfitDescription(description);
                        }
                        loanService.update(loan);
                    }

                }
                catch (Exception e){
                    errorList.add("loan version,details->"+detailsResultSet.getLong("credit_id")+","+detailsResultSet.getString("details"));
                }

            }
            String updateItemQuery="update collateralItem ci,collateralAgreement ca,loanCollateralAgreement la\n" +
                    "  set ci.inspection_needed=0\n" +
                    "  where ci.collateralAgreementId=ca.id and ca.id=la.collateralAgreementId and la.loanId in ("+loanIds.substring(0,loanIds.length()-1)+")";
            session.createSQLQuery(updateItemQuery).executeUpdate();
        }
        catch (Exception e){

        }
    }

//    migrate supervisorPlans
    public void migrateSupervisorPlans(String ip,String database,String username,String password){

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        Connection connection=this.getSourceConnection(ip,database,username,password);

        userMapMaker(connection);

        try{
            Statement st=connection.createStatement();
            ResultSet plans=st.executeQuery("select * from plan where year=2019");
            while (plans.next()){
                Loan loan=loanService.getByVersion(plans.getLong("credit_id"));
                Double main=0.0;
                Double interest=0.0;
                Double penalty=0.0;
                Date date= new Date();
                date.setYear(plans.getInt("year"));
                for (int i=1;i<13;i++){
                    SupervisorPlan supervisorPlan= new SupervisorPlan();
                    if(i<10){
                        main=plans.getDouble("m0"+i+"_main");
                        interest=plans.getDouble("m0"+i+"_percent");
                        penalty=plans.getDouble("m0"+i+"_penalty");
                    }
                    else{
                        main=plans.getDouble("m"+i+"_main");
                        interest=plans.getDouble("m"+i+"_percent");
                        penalty=plans.getDouble("m"+i+"_penalty");
                    }
                    if(i==2){
                        date.setDate(28);
                    }
                    else {
                        date.setDate(30);
                    }
                    date.setMonth(i);
                    supervisorPlan.setDate(date);

                    supervisorPlan.setReg_date(plans.getDate("reg_date"));
                    if(userMap.get(plans.getLong("reg_by"))!=null)
                        supervisorPlan.setReg_by_id(userMap.get(plans.getLong("reg_by")));
                    else
                        supervisorPlan.setReg_by_id(1);

                    supervisorPlan.setLoan(loan);
                    supervisorPlan.setPrincipal(main);
                    supervisorPlan.setInterest(interest);
                    supervisorPlan.setPenalty(penalty);
                    supervisorPlan.setFee((double)0);
                    supervisorPlan.setAmount(main+interest+penalty);
                    supervisorPlan.setDescription("");

                    if(supervisorPlan.getAmount()>1)
                        this.supervisorPlanService.add(supervisorPlan);
                }
            }
            plans.close();
            st.close();
        }
        catch (Exception e){

        }
    }

    public void userMapMaker(Connection connection){
        try{
            Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery("select id,login from users");
            while (rs.next()){
                User user=userService.findByUsername(rs.getString("login"));
                userMap.put(rs.getLong("id"),user.getId());
            }
            statement.close();
            rs.close();
        }
        catch (Exception e){

        }

    }

}
