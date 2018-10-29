package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.CollateralInspectionView;
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
public class CollateralInspectionViewDaoImpl implements CollateralInspectionViewDao {

    private static final Logger logger = LoggerFactory.getLogger(CollateralInspectionViewDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public CollateralInspectionViewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(CollateralInspectionView collateralInspectionView) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(collateralInspectionView);
		
		logger.info("CollateralInspectionView added == "+collateralInspectionView);
		
	} 


	@Override
	public void edit(CollateralInspectionView collateralInspectionView) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(collateralInspectionView);
		
		logger.info("CollateralInspectionView edited == "+collateralInspectionView);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		CollateralInspectionView collateralInspectionView = (CollateralInspectionView) session.load(CollateralInspectionView.class, new Long (id));
		if(collateralInspectionView!=null)
		{
			session.delete(collateralInspectionView);
		}
		
		logger.info("CollateralInspectionView deleted == "+collateralInspectionView);
		
	}


	@Override
	public CollateralInspectionView findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		CollateralInspectionView collateralInspectionView = (CollateralInspectionView) session.load(CollateralInspectionView.class, new Long (id));
		
		logger.info("CollateralInspectionView get by id == "+collateralInspectionView);

		return collateralInspectionView ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<CollateralInspectionView> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<CollateralInspectionView> collateralInspectionViewsList = session.createQuery("from CollateralInspectionView").list();
        return collateralInspectionViewsList;
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<CollateralInspectionView> findByParameter(LinkedHashMap<String,List<String>> parameters) {

		Session session = this.sessionFactory.getCurrentSession();

		ReportTool reportTool = new ReportTool();

		Criteria criteria = session.createCriteria(CollateralInspectionView.class);

		reportTool.applyParameters(parameters,criteria);

		return criteria.list();
	}


 

}