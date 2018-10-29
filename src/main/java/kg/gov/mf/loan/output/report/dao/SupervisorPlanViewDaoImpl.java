package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.SupervisorPlanView;
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
public class SupervisorPlanViewDaoImpl implements SupervisorPlanViewDao {

    private static final Logger logger = LoggerFactory.getLogger(SupervisorPlanViewDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public SupervisorPlanViewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(SupervisorPlanView supervisorPlanView) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(supervisorPlanView);
		
		logger.info("SupervisorPlanView added == "+supervisorPlanView);
		
	} 


	@Override
	public void edit(SupervisorPlanView supervisorPlanView) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(supervisorPlanView);
		
		logger.info("SupervisorPlanView edited == "+supervisorPlanView);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		SupervisorPlanView supervisorPlanView = (SupervisorPlanView) session.load(SupervisorPlanView.class, new Long (id));
		if(supervisorPlanView!=null)
		{
			session.delete(supervisorPlanView);
		}
		
		logger.info("SupervisorPlanView deleted == "+supervisorPlanView);
		
	}


	@Override
	public SupervisorPlanView findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		SupervisorPlanView supervisorPlanView = (SupervisorPlanView) session.load(SupervisorPlanView.class, new Long (id));
		
		logger.info("SupervisorPlanView get by id == "+supervisorPlanView);

		return supervisorPlanView ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<SupervisorPlanView> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<SupervisorPlanView> supervisorPlanViewsList = session.createQuery("from SupervisorPlanView").list();
        return supervisorPlanViewsList;
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<SupervisorPlanView> findByParameter(LinkedHashMap<String,List<String>> parameters) {

		Session session = this.sessionFactory.getCurrentSession();

		ReportTool reportTool = new ReportTool();

		Criteria criteria = session.createCriteria(SupervisorPlanView.class);

		reportTool.applyParameters(parameters,criteria);

		return criteria.list();
	}



 

}