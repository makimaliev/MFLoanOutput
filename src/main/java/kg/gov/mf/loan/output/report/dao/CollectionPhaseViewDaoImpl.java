package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.CollectionPhaseView;
import kg.gov.mf.loan.output.report.utils.ReportTool;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

@Repository
public class CollectionPhaseViewDaoImpl implements CollectionPhaseViewDao {

    private static final Logger logger = LoggerFactory.getLogger(CollectionPhaseViewDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public CollectionPhaseViewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(CollectionPhaseView collectionPhaseView) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(collectionPhaseView);
		
		logger.info("CollectionPhaseView added == "+collectionPhaseView);
		
	} 


	@Override
	public void edit(CollectionPhaseView collectionPhaseView) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(collectionPhaseView);
		
		logger.info("CollectionPhaseView edited == "+collectionPhaseView);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		CollectionPhaseView collectionPhaseView = (CollectionPhaseView) session.load(CollectionPhaseView.class, new Long (id));
		if(collectionPhaseView!=null)
		{
			session.delete(collectionPhaseView);
		}
		
		logger.info("CollectionPhaseView deleted == "+collectionPhaseView);
		
	}


	@Override
	public CollectionPhaseView findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		CollectionPhaseView collectionPhaseView = (CollectionPhaseView) session.load(CollectionPhaseView.class, new Long (id));
		
		logger.info("CollectionPhaseView get by id == "+collectionPhaseView);

		return collectionPhaseView ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<CollectionPhaseView> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<CollectionPhaseView> collectionPhaseViewsList = session.createQuery("from CollectionPhaseView").list();
        return collectionPhaseViewsList;
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<CollectionPhaseView> findByParameter(LinkedHashMap<String,List<String>> parameters) {

		Session session = this.sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(CollectionPhaseView.class);

		ReportTool reportTool = new ReportTool();

		reportTool.applyParameters(parameters,criteria);

		return criteria.list();
	}

	@Override
	public List<CollectionPhaseView> findByParameter(LinkedHashMap<String, List<String>> parameters, Integer offset, Integer limit, String sortStr, String sortField) {
		Session session=sessionFactory.getCurrentSession();

		Criteria criteria=session.createCriteria(CollectionPhaseView.class);
		ReportTool reportTool = new ReportTool();
		reportTool.applyParameters(parameters,criteria);

		if(sortStr.equals("asc")){
			criteria.addOrder(Order.asc(sortField));
		}
		else {
			criteria.addOrder(Order.desc(sortField));
		}

		criteria.setFirstResult(offset);
		criteria.setMaxResults(limit);

		return criteria.list();
	}


}