package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.GroupType;
import kg.gov.mf.loan.output.report.model.ObjectList;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ObjectListDaoImpl implements ObjectListDao {

    private static final Logger logger = LoggerFactory.getLogger(ObjectListDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public ObjectListDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(ObjectList objectList) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(objectList);
		
		logger.info("ObjectList added == "+objectList);
		
	} 


	@Override
	public void edit(ObjectList objectList) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(objectList);
		
		logger.info("ObjectList edited == "+objectList);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		ObjectList objectList = (ObjectList) session.load(ObjectList.class, new Long (id));
		if(objectList!=null)
		{
			session.delete(objectList);
		}
		
		logger.info("ObjectList deleted == "+objectList);
		
	}


	@Override
	public ObjectList findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		ObjectList objectList = (ObjectList) session.load(ObjectList.class, new Long (id));
		
		logger.info("ObjectList get by id == "+objectList);

		return objectList ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<ObjectList> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<ObjectList> objectListsList = session.createQuery("from ObjectList").list();
        return objectListsList;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<ObjectList> findAllByGroupType(GroupType groupType) {
		Session session = this.sessionFactory.getCurrentSession();

//
//		List<ObjectList> objectListsList = session.createQuery("from ObjectList").list();
//		return objectListsList;


		Criteria criteria = session.createCriteria(ObjectList.class);

		criteria.add(Restrictions.eq("groupType",groupType));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria.list();
	}

	@Override
	public List<ObjectList> findAllByGroupTypeAndUser(GroupType groupType, Long userId) {

    	Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ObjectList.class);

		criteria.add(Restrictions.eq("groupType",groupType));
		criteria.add(Restrictions.eq("user_id",userId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();

	}


}