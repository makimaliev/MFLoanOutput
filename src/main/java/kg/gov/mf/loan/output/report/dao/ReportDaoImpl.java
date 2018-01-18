package kg.gov.mf.loan.output.report.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kg.gov.mf.loan.output.report.model.*;
 
@Repository
public class ReportDaoImpl implements ReportDao {
     
    private static final Logger logger = LoggerFactory.getLogger(ReportDaoImpl.class);
 
    @Autowired
    private SessionFactory sessionFactory;
     
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
    
    
    @Autowired
    public ReportDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(Report report) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(report);
		
		logger.info("Report added == "+report);
		
	} 


	@Override
	public void edit(Report report) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(report);
		
		logger.info("Report edited == "+report);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		Report report = (Report) session.load(Report.class, new Long (id));
		if(report!=null)
		{
			session.delete(report);
		}
		
		logger.info("Report deleted == "+report);
		
	}


	@Override
	public Report findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		Report report = (Report) session.load(Report.class, new Long (id));
		
		logger.info("Report get by id == "+report);

		return report ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<Report> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Report> reportsList = session.createQuery("from Report").list();
        return reportsList;
    }
 

}