package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.ReferenceView;
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
public class ReferenceViewDaoImpl implements ReferenceViewDao {

    private static final Logger logger = LoggerFactory.getLogger(ReferenceViewDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public ReferenceViewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(ReferenceView referenceView) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(referenceView);
		
		logger.info("ReferenceView added == "+referenceView);
		
	} 


	@Override
	public void edit(ReferenceView referenceView) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(referenceView);
		
		logger.info("ReferenceView edited == "+referenceView);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		ReferenceView referenceView = (ReferenceView) session.load(ReferenceView.class, new Long (id));
		if(referenceView!=null)
		{
			session.delete(referenceView);
		}
		
		logger.info("ReferenceView deleted == "+referenceView);
		
	}


	@Override
	public ReferenceView findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		ReferenceView referenceView = (ReferenceView) session.load(ReferenceView.class, new Long (id));
		
		logger.info("ReferenceView get by id == "+referenceView);

		return referenceView ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<ReferenceView> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<ReferenceView> referenceViewsList = session.createQuery("from ReferenceView").list();
        return referenceViewsList;
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<ReferenceView> findByParameter(String list_type) {

		Session session = this.sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(ReferenceView.class);

		criteria.add(Restrictions.eq("list_type",list_type));
		criteria.addOrder(Order.asc("id"));

		return criteria.list();
	}



 

}