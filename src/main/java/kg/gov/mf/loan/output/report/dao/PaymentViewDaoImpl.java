package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.PaymentView;
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
public class PaymentViewDaoImpl implements PaymentViewDao {

    private static final Logger logger = LoggerFactory.getLogger(PaymentViewDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public PaymentViewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(PaymentView paymentView) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(paymentView);
		
		logger.info("PaymentView added == "+paymentView);
		
	} 


	@Override
	public void edit(PaymentView paymentView) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(paymentView);
		
		logger.info("PaymentView edited == "+paymentView);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		PaymentView paymentView = (PaymentView) session.load(PaymentView.class, new Long (id));
		if(paymentView!=null)
		{
			session.delete(paymentView);
		}
		
		logger.info("PaymentView deleted == "+paymentView);
		
	}


	@Override
	public PaymentView findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		PaymentView paymentView = (PaymentView) session.load(PaymentView.class, new Long (id));
		
		logger.info("PaymentView get by id == "+paymentView);

		return paymentView ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<PaymentView> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<PaymentView> paymentViewsList = session.createQuery("from PaymentView").list();
        return paymentViewsList;
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentView> findByParameter(LinkedHashMap<String,List<String>> parameters) {

		Session session = this.sessionFactory.getCurrentSession();

		ReportTool reportTool = new ReportTool();

		Criteria criteria = session.createCriteria(PaymentView.class);

		reportTool.applyParameters(parameters,criteria);

		return criteria.list();
	}



 

}