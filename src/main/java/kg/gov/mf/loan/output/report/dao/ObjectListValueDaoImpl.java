package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.ObjectListValue;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ObjectListValueDaoImpl implements ObjectListValueDao {

    private static final Logger logger = LoggerFactory.getLogger(ObjectListValueDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public ObjectListValueDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(ObjectListValue objectListValue) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(objectListValue);
		
		logger.info("ObjectListValue added == "+objectListValue);
		
	} 


	@Override
	public void edit(ObjectListValue objectListValue) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(objectListValue);
		
		logger.info("ObjectListValue edited == "+objectListValue);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		ObjectListValue objectListValue = (ObjectListValue) session.load(ObjectListValue.class, new Long (id));
		if(objectListValue!=null)
		{
			session.delete(objectListValue);
		}
		
		logger.info("ObjectListValue deleted == "+objectListValue);
		
	}


	@Override
	public ObjectListValue findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		ObjectListValue objectListValue = (ObjectListValue) session.load(ObjectListValue.class, new Long (id));
		
		logger.info("ObjectListValue get by id == "+objectListValue);

		return objectListValue ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<ObjectListValue> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<ObjectListValue> objectListValuesList = session.createQuery("from ObjectListValue").list();
        return objectListValuesList;
    }
 

}