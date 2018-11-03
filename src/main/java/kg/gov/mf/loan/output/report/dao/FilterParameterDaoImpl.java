package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.output.report.model.FilterParameter;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FilterParameterDaoImpl implements FilterParameterDao {

    private static final Logger logger = LoggerFactory.getLogger(FilterParameterDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public FilterParameterDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(FilterParameter filterParameter) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(filterParameter);
		
		logger.info("FilterParameter added == "+filterParameter);
		
	} 


	@Override
	public void edit(FilterParameter filterParameter) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(filterParameter);
		
		logger.info("FilterParameter edited == "+filterParameter);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		FilterParameter filterParameter = (FilterParameter) session.load(FilterParameter.class, new Long (id));
		if(filterParameter!=null)
		{
			session.delete(filterParameter);
		}
		
		logger.info("FilterParameter deleted == "+filterParameter);
		
	}


	@Override
	public FilterParameter findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		FilterParameter filterParameter = (FilterParameter) session.load(FilterParameter.class, new Long (id));

		Hibernate.initialize(filterParameter.getUsers());

		logger.info("FilterParameter get by id == "+filterParameter);

		return filterParameter ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<FilterParameter> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<FilterParameter> filterParametersList = session.createQuery("from FilterParameter").list();
        return filterParametersList;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<FilterParameter> findByUser(User user)
	{
		Session session = this.sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(FilterParameter.class);

		criteria.createAlias("users", "usersAlias");
		criteria.add(Restrictions.eq("usersAlias.id", user.getId()));

		return criteria.list() ;

	}
 

}