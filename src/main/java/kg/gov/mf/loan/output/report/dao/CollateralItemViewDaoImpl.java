package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.CollateralItemView;
import kg.gov.mf.loan.output.report.model.EntityDocumentView;
import kg.gov.mf.loan.output.report.utils.CollateralItemReportDataManager;
import kg.gov.mf.loan.output.report.utils.PaymentReportDataManager;
import kg.gov.mf.loan.output.report.utils.ReportTool;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.*;

@Repository
public class CollateralItemViewDaoImpl implements CollateralItemViewDao {

	private static final Logger logger = LoggerFactory.getLogger(CollateralItemViewDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}


	@Autowired
	public CollateralItemViewDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}







	@Override
	public void create(CollateralItemView collateralItemView) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(collateralItemView);
		
		logger.info("CollateralItemView added == "+collateralItemView);
		
	} 


	@Override
	public void edit(CollateralItemView collateralItemView) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(collateralItemView);
		
		logger.info("CollateralItemView edited == "+collateralItemView);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		CollateralItemView collateralItemView = (CollateralItemView) session.load(CollateralItemView.class, new Long (id));
		if(collateralItemView!=null)
		{
			session.delete(collateralItemView);
		}
		
		logger.info("CollateralItemView deleted == "+collateralItemView);
		
	}


	@Override
	public CollateralItemView findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		CollateralItemView collateralItemView = (CollateralItemView) session.load(CollateralItemView.class, new Long (id));
		
		logger.info("CollateralItemView get by id == "+collateralItemView);

		return collateralItemView ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<CollateralItemView> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<CollateralItemView> collateralItemViewsList = session.createQuery("from CollateralItemView").list();
        return collateralItemViewsList;
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<CollateralItemView> findByParameter(LinkedHashMap<String,List<String>> parameters) {

		Session session = this.sessionFactory.getCurrentSession();

		ReportTool reportTool = new ReportTool();

		Criteria criteria = session.createCriteria(CollateralItemView.class);

		reportTool.applyParameters(parameters,criteria);

		return criteria.list();
	}

//	@SuppressWarnings("unchecked")
	@Override
	public List<CollateralItemView> findByParameter(LinkedHashMap<String, List<String>> parameters, Integer offset, Integer limit, String sortStr, String sortField) {

		Session session=this.sessionFactory.getCurrentSession();
		ReportTool reportTool=new ReportTool();

		Criteria criteria=session.createCriteria(CollateralItemView.class);
		reportTool.applyParameters(parameters,criteria);

		if(sortStr.equals("asc")){
			criteria.addOrder(Order.asc(sortField));
		}
		else if(sortStr.equals("desc")){
			criteria.addOrder(Order.desc(sortField));
		}
		criteria.setFirstResult(offset);
		criteria.setMaxResults(limit);


//		Iterator it2 = list2.iterator();
//		while (it2.hasNext()) {
//			Object[] obj = (Object[]) it2.next();
//		}

		return criteria.list();
	}

//	@SuppressWarnings("unchecked")
	@Override
	public Long getCount(LinkedHashMap<String, List<String>> parameters) {

		Session session=this.sessionFactory.getCurrentSession();
		ReportTool reportTool=new ReportTool();

		Criteria criteria=session.createCriteria(CollateralItemView.class);
		reportTool.applyParameters(parameters,criteria);

//		criteria.setProjection(Projections.property("v_ci_id"));

		Long count= (Long) criteria
				.setProjection(Projections.rowCount())
				.uniqueResult();
		return count;
	}


}