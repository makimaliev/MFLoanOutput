package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.ContentParameter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContentParameterDaoImpl implements ContentParameterDao {

    private static final Logger logger = LoggerFactory.getLogger(ContentParameterDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public ContentParameterDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(ContentParameter contentParameter) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(contentParameter);
		
		logger.info("ContentParameter added == "+contentParameter);
		
	} 


	@Override
	public void edit(ContentParameter contentParameter) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(contentParameter);
		
		logger.info("ContentParameter edited == "+contentParameter);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		ContentParameter contentParameter = (ContentParameter) session.load(ContentParameter.class, new Long (id));
		if(contentParameter!=null)
		{
			session.delete(contentParameter);
		}
		
		logger.info("ContentParameter deleted == "+contentParameter);
		
	}


	@Override
	public ContentParameter findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		ContentParameter contentParameter = (ContentParameter) session.load(ContentParameter.class, new Long (id));
		
		logger.info("ContentParameter get by id == "+contentParameter);

		return contentParameter ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<ContentParameter> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<ContentParameter> contentParametersList = session.createQuery("from ContentParameter").list();
        return contentParametersList;
    }
 

}