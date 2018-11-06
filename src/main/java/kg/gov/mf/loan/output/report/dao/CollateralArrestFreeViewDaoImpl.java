package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.CollateralArrestFreeView;
import kg.gov.mf.loan.output.report.utils.ReportTool;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public class CollateralArrestFreeViewDaoImpl implements CollateralArrestFreeViewDao {

    private static final Logger logger = LoggerFactory.getLogger(CollateralArrestFreeViewDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public CollateralArrestFreeViewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(CollateralArrestFreeView collateralArrestFreeView) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(collateralArrestFreeView);
		
		logger.info("CollateralArrestFreeView added == "+collateralArrestFreeView);
		
	} 


	@Override
	public void edit(CollateralArrestFreeView collateralArrestFreeView) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(collateralArrestFreeView);
		
		logger.info("CollateralArrestFreeView edited == "+collateralArrestFreeView);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		CollateralArrestFreeView collateralArrestFreeView = (CollateralArrestFreeView) session.load(CollateralArrestFreeView.class, new Long (id));
		if(collateralArrestFreeView!=null)
		{
			session.delete(collateralArrestFreeView);
		}
		
		logger.info("CollateralArrestFreeView deleted == "+collateralArrestFreeView);
		
	}


	@Override
	public CollateralArrestFreeView findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		CollateralArrestFreeView collateralArrestFreeView = (CollateralArrestFreeView) session.load(CollateralArrestFreeView.class, new Long (id));
		
		logger.info("CollateralArrestFreeView get by id == "+collateralArrestFreeView);

		return collateralArrestFreeView ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<CollateralArrestFreeView> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<CollateralArrestFreeView> collateralArrestFreeViewsList = session.createQuery("from CollateralArrestFreeView").list();
        return collateralArrestFreeViewsList;
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<CollateralArrestFreeView> findByParameter(LinkedHashMap<String,List<String>> parameters) {

		Session session = this.sessionFactory.getCurrentSession();

		ReportTool reportTool = new ReportTool();

		Criteria criteria = session.createCriteria(CollateralArrestFreeView.class);

		reportTool.applyParameters(parameters,criteria);

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CollateralArrestFreeView> findByParameter(LinkedHashMap<String, List<String>> parameters, Integer offset, Integer limit) {

    	Session session = this.sessionFactory.getCurrentSession();

		ReportTool reportTool = new ReportTool();

		Criteria criteria = session.createCriteria(CollateralArrestFreeView.class);

		reportTool.applyParameters(parameters,criteria);

		criteria.setFirstResult(offset);
		criteria.setMaxResults(limit);

		return criteria.list();
	}


}