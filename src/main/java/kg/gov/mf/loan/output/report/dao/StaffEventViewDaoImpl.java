package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.StaffEventView;
import kg.gov.mf.loan.output.report.utils.ReportTool;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public class StaffEventViewDaoImpl implements StaffEventViewDao {

    private static final Logger logger = LoggerFactory.getLogger(StaffEventViewDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public StaffEventViewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(StaffEventView staffEventView) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(staffEventView);
		
		logger.info("StaffEventView added == "+staffEventView);
		
	} 


	@Override
	public void edit(StaffEventView staffEventView) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(staffEventView);
		
		logger.info("StaffEventView edited == "+staffEventView);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		StaffEventView staffEventView = (StaffEventView) session.load(StaffEventView.class, new Long (id));
		if(staffEventView!=null)
		{
			session.delete(staffEventView);
		}
		
		logger.info("StaffEventView deleted == "+staffEventView);
		
	}


	@Override
	public StaffEventView findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		StaffEventView staffEventView = (StaffEventView) session.load(StaffEventView.class, new Long (id));
		
		logger.info("StaffEventView get by id == "+staffEventView);

		return staffEventView ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<StaffEventView> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<StaffEventView> staffEventViewsList = session.createQuery("from StaffEventView").list();
        return staffEventViewsList;
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<StaffEventView> findByParameter(LinkedHashMap<String,List<String>> parameters) {

		Session session = this.sessionFactory.getCurrentSession();

		ReportTool reportTool = new ReportTool();

		Criteria criteria = session.createCriteria(StaffEventView.class);

		reportTool.applyParameters(parameters,criteria);

		return criteria.list();
	}


	@Override
	public List<StaffEventView> findByParameter(LinkedHashMap<String, List<String>> parameters, Integer offset, Integer limit, String sortStr, String sortField) {

    	Session session=this.sessionFactory.getCurrentSession();

    	ReportTool reportTool=new ReportTool();

    	Criteria criteria=session.createCriteria(StaffEventView.class);

    	reportTool.applyParameters(parameters,criteria);

		if(sortStr.equals("asc")){
			criteria.addOrder(Order.asc(sortField));
		}
		else if(sortStr.equals("desc")){
			criteria.addOrder(Order.desc(sortField));
		}
		criteria.setFirstResult(offset);
		criteria.setMaxResults(limit);


    	return criteria.list();
	}

	@Override
	public Long getCount(LinkedHashMap<String, List<String>> parameters) {
		Session session=this.sessionFactory.getCurrentSession();

		ReportTool reportTool=new ReportTool();

		Criteria criteria=session.createCriteria(StaffEventView.class);

		reportTool.applyParameters(parameters,criteria);

		Long count= (Long) criteria
				.setProjection(Projections.rowCount())
				.uniqueResult();

		return count;
	}


}