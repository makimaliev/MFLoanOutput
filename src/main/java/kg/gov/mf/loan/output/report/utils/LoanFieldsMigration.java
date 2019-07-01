package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.manage.model.loan.Description;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.service.loan.DescriptionService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.persistence.EntityManager;
import java.sql.*;
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

    Set<String> errorList = new HashSet<String>();

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
            System.out.println(e);
        }
    }

}
