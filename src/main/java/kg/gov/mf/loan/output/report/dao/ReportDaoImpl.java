package kg.gov.mf.loan.output.report.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kg.gov.mf.loan.admin.sys.model.User;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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



		Hibernate.initialize(report.getContentParameters());
		Hibernate.initialize(report.getFilterParameters());
		Hibernate.initialize(report.getGroupTypes());
		Hibernate.initialize(report.getReportTemplates());
		Hibernate.initialize(report.getOutputParameters());
		Hibernate.initialize(report.getUsers());
		
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


	@SuppressWarnings("unchecked")
	@Override
	public List<Report> findByUser(User user)
	{
		Session session = this.sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Report.class);

		criteria.createAlias("users", "usersAlias");
		criteria.add(Restrictions.eq("usersAlias.id", user.getId()));

		return criteria.list() ;

	}

	@Override
	public void clone(Report report) {

		Report clonedReport = new Report();


		clonedReport.setName(report.getName()+" копия ");
		clonedReport.setReportType(report.getReportType());


//		Set<ReportTemplate> reportTemplates= new HashSet<>();
//		reportTemplates.addAll(report.getReportTemplates());
//		clonedReport.setReportTemplates(reportTemplates);


		Set<GroupType> groupTypes= new HashSet<>();
		groupTypes.addAll(report.getGroupTypes());
		clonedReport.setGroupTypes(groupTypes);



		Set<ContentParameter> contentParameters= new HashSet<>();
		contentParameters.addAll(report.getContentParameters());
		clonedReport.setContentParameters(contentParameters);



		Set<FilterParameter> filterParameters= new HashSet<>();
		filterParameters.addAll(report.getFilterParameters());
		clonedReport.setFilterParameters(filterParameters);


		Set<OutputParameter> outputParameters = new HashSet<>();
		outputParameters.addAll(report.getOutputParameters());
		clonedReport.setOutputParameters(outputParameters);

		Set<User> users = new HashSet<>();
		users.addAll(report.getUsers());
		clonedReport.setUsers(users);

		Session session = this.sessionFactory.getCurrentSession();
		session.persist(clonedReport);

		logger.info("Report cloned == "+report);

	}

}