package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.PaymentScheduleView;
import kg.gov.mf.loan.output.report.utils.PaymentReportDataManager;
import kg.gov.mf.loan.output.report.utils.ReportTool;
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
public class PaymentScheduleViewDaoImpl implements PaymentScheduleViewDao {

    private static final Logger logger = LoggerFactory.getLogger(PaymentScheduleViewDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public PaymentScheduleViewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(PaymentScheduleView paymentScheduleView) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(paymentScheduleView);
		
		logger.info("PaymentScheduleView added == "+paymentScheduleView);
		
	} 


	@Override
	public void edit(PaymentScheduleView paymentScheduleView) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(paymentScheduleView);
		
		logger.info("PaymentScheduleView edited == "+paymentScheduleView);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		PaymentScheduleView paymentScheduleView = (PaymentScheduleView) session.load(PaymentScheduleView.class, new Long (id));
		if(paymentScheduleView!=null)
		{
			session.delete(paymentScheduleView);
		}
		
		logger.info("PaymentScheduleView deleted == "+paymentScheduleView);
		
	}


	@Override
	public PaymentScheduleView findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		PaymentScheduleView paymentScheduleView = (PaymentScheduleView) session.load(PaymentScheduleView.class, new Long (id));
		
		logger.info("PaymentScheduleView get by id == "+paymentScheduleView);

		return paymentScheduleView ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<PaymentScheduleView> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<PaymentScheduleView> paymentScheduleViewsList = session.createQuery("from PaymentScheduleView").list();
        return paymentScheduleViewsList;
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentScheduleView> findByParameter(LinkedHashMap<String,List<Long>> parameters) {

		Session session = this.sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(PaymentScheduleView.class);

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
				case "work_sector":
					criteria.add(Restrictions.in("v_debtor_work_sector_id",ids));
					break;
				case "orderBy":
					for (Long id:ids
						 )
					{
						switch(new ReportTool().getParameterTypeNameById(id.toString()))
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
							case "payment_schedule":
								criteria.addOrder(Order.asc("v_ps_id"));
								break;
						}

					}


					break;







			}


		}

		//List<PaymentScheduleView> paymentScheduleViewsList = session.createQuery("from PaymentScheduleView").list();

		return criteria.list();
	}



 

}