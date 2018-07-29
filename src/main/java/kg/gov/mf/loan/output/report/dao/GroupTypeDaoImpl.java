package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.GroupType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupTypeDaoImpl implements GroupTypeDao {

    private static final Logger logger = LoggerFactory.getLogger(GroupTypeDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public GroupTypeDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(GroupType groupType) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(groupType);
		
		logger.info("GroupType added == "+groupType);
		
	} 


	@Override
	public void edit(GroupType groupType) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(groupType);
		
		logger.info("GroupType edited == "+groupType);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		GroupType groupType = (GroupType) session.load(GroupType.class, new Long (id));
		if(groupType!=null)
		{
			session.delete(groupType);
		}
		
		logger.info("GroupType deleted == "+groupType);
		
	}


	@Override
	public GroupType findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		GroupType groupType = (GroupType) session.load(GroupType.class, new Long (id));
		
		logger.info("GroupType get by id == "+groupType);

		return groupType ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<GroupType> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<GroupType> groupTypesList = session.createQuery("from GroupType").list();
        return groupTypesList;
    }
 

}