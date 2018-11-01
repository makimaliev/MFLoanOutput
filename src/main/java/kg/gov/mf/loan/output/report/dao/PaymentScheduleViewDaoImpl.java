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
	public List<PaymentScheduleView> findByParameter(LinkedHashMap<String,List<String>> parameters) {

		Session session = this.sessionFactory.getCurrentSession();

		ReportTool reportTool = new ReportTool();

		Criteria criteria = session.createCriteria(PaymentScheduleView.class);

		reportTool.applyParameters(parameters,criteria);

		return criteria.list();
	}

	@Override
	public List<PaymentScheduleView> findByParameter(LinkedHashMap<String, List<String>> parameters, Integer offset, Integer limit) {
		Session session = this.sessionFactory.getCurrentSession();

		ReportTool reportTool = new ReportTool();

		Criteria criteria = session.createCriteria(PaymentScheduleView.class);

		reportTool.applyParameters(parameters,criteria);
		criteria.setMaxResults(limit);
		criteria.setFirstResult(offset);

		return criteria.list();
	}


}