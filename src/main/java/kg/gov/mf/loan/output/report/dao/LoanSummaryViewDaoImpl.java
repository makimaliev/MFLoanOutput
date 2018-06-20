package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.LoanSummaryView;
import kg.gov.mf.loan.output.report.utils.PaymentReportDataManager;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LoanSummaryViewDaoImpl implements LoanSummaryViewDao {

    private static final Logger logger = LoggerFactory.getLogger(LoanSummaryViewDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public LoanSummaryViewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(LoanSummaryView loanSummaryView) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(loanSummaryView);
		
		logger.info("LoanSummaryView added == "+loanSummaryView);
		
	} 


	@Override
	public void edit(LoanSummaryView loanSummaryView) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(loanSummaryView);
		
		logger.info("LoanSummaryView edited == "+loanSummaryView);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		LoanSummaryView loanSummaryView = (LoanSummaryView) session.load(LoanSummaryView.class, new Long (id));
		if(loanSummaryView!=null)
		{
			session.delete(loanSummaryView);
		}
		
		logger.info("LoanSummaryView deleted == "+loanSummaryView);
		
	}


	@Override
	public LoanSummaryView findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		LoanSummaryView loanSummaryView = (LoanSummaryView) session.load(LoanSummaryView.class, new Long (id));
		
		logger.info("LoanSummaryView get by id == "+loanSummaryView);

		return loanSummaryView ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<LoanSummaryView> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<LoanSummaryView> loanSummaryViewsList = session.createQuery("from LoanSummaryView").list();
        return loanSummaryViewsList;
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<LoanSummaryView> findByParameter(LinkedHashMap<String,List<Long>> parameters) {

		Session session = this.sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(LoanSummaryView.class);

		PaymentReportDataManager paymentReportDataManager =  new PaymentReportDataManager();


		for (Map.Entry<String, List<Long>> parameterInLoop : parameters.entrySet())
		{
			String paramaterType = parameterInLoop.getKey();

			List<Long> ids = parameterInLoop.getValue();

			switch(paramaterType)
			{
				case "region":
					criteria.add(Restrictions.in("v_debtor_region_id",ids));
					break;
				case "district":
					criteria.add(Restrictions.in("v_debtor_district_id",ids));
					break;
				case "debtor":
					criteria.add(Restrictions.in("v_debtor_id",ids));
					break;
				case "loan":
					criteria.add(Restrictions.in("v_loan_id",ids));
					break;
				case "payment":
					criteria.add(Restrictions.in("v_payment_id",ids));
					break;
				case "paymentDateFrom":
					criteria.add(Restrictions.ge("v_payment_date",new Date((ids.get(0)))));
					System.out.println(new Date((ids.get(0))));
					break;
				case "paymentDateTo":
					criteria.add(Restrictions.lt("v_payment_date",new Date((ids.get(0)))));
					System.out.println(new Date((ids.get(0))));
					break;

				case "onDate":
					criteria.add(Restrictions.le("v_ls_onDate",new Date((ids.get(0)))));
					break;

				case "betweenDates":
					criteria.add(Restrictions.ge("v_ls_onDate",new Date((ids.get(0)))));
					criteria.add(Restrictions.le("v_ls_onDate",new Date((ids.get(1)))));
					break;


				case "work_sector":
					criteria.add(Restrictions.in("v_debtor_work_sector_id",ids));
					break;
				case "orderBy":
					for (Long id:ids
						 )
					{
						switch(paymentReportDataManager.getParameterTypeNameById(id.toString()))
						{
							case "region":
								criteria.addOrder(Order.asc("v_debtor_region_id"));
								break;
							case "district":
								criteria.addOrder(Order.asc("v_debtor_district_id"));
								break;
							case "debtor":
								criteria.addOrder(Order.asc("v_debtor_name"));
								break;
							case "loan":
								criteria.addOrder(Order.asc("v_loan_id"));
								break;
							case "payment":
								criteria.addOrder(Order.asc("v_payment_id"));
								break;
							case "work_sector":
								criteria.addOrder(Order.asc("v_debtor_work_sector_id"));
								break;
						}

					}

					criteria.addOrder(Order.asc("v_ls_onDate"));

					break;







			}


		}

		//List<LoanSummaryView> loanSummaryViewsList = session.createQuery("from LoanSummaryView").list();

		return criteria.list();
	}



 

}