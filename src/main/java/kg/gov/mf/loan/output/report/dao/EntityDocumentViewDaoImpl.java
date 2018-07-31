package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.EntityDocumentView;
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
public class EntityDocumentViewDaoImpl implements EntityDocumentViewDao {

    private static final Logger logger = LoggerFactory.getLogger(EntityDocumentViewDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public EntityDocumentViewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(EntityDocumentView entitydocumentView) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(entitydocumentView);
		
		logger.info("EntityDocumentView added == "+entitydocumentView);
		
	} 


	@Override
	public void edit(EntityDocumentView entitydocumentView) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(entitydocumentView);
		
		logger.info("EntityDocumentView edited == "+entitydocumentView);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		EntityDocumentView entitydocumentView = (EntityDocumentView) session.load(EntityDocumentView.class, new Long (id));
		if(entitydocumentView!=null)
		{
			session.delete(entitydocumentView);
		}
		
		logger.info("EntityDocumentView deleted == "+entitydocumentView);
		
	}


	@Override
	public EntityDocumentView findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		EntityDocumentView entitydocumentView = (EntityDocumentView) session.load(EntityDocumentView.class, new Long (id));
		
		logger.info("EntityDocumentView get by id == "+entitydocumentView);

		return entitydocumentView ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<EntityDocumentView> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<EntityDocumentView> entitydocumentViewsList = session.createQuery("from EntityDocumentView").list();
        return entitydocumentViewsList;
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<EntityDocumentView> findByParameter(LinkedHashMap<String,List<String>> parameters) {

		Session session = this.sessionFactory.getCurrentSession();

		ReportTool reportTool = new ReportTool();

		Criteria criteria = session.createCriteria(EntityDocumentView.class);

		reportTool.applyParameters(parameters,criteria);

		return criteria.list();
	}



 

}