package kg.gov.mf.loan.output.report.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kg.gov.mf.loan.output.report.model.*;
 
@Repository
public class ReportTemplateDaoImpl implements ReportTemplateDao {
     
    private static final Logger logger = LoggerFactory.getLogger(ReportTemplateDaoImpl.class);
 
    @Autowired
    private SessionFactory sessionFactory;
     
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
    
    
    @Autowired
    public ReportTemplateDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(ReportTemplate reportTemplate) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(reportTemplate);
		
		logger.info("ReportTemplate added == "+reportTemplate);
		
	} 


	@Override
	public void edit(ReportTemplate reportTemplate) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(reportTemplate);
		
		logger.info("ReportTemplate edited == "+reportTemplate);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		ReportTemplate reportTemplate = (ReportTemplate) session.load(ReportTemplate.class, new Long (id));
		if(reportTemplate!=null)
		{
			session.delete(reportTemplate);
		}
		
		logger.info("ReportTemplate deleted == "+reportTemplate);
		
	}


	@Override
	public ReportTemplate findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		ReportTemplate reportTemplate = (ReportTemplate) session.load(ReportTemplate.class, new Long (id));

		Hibernate.initialize(reportTemplate.getFilterParameters());
		Hibernate.initialize(reportTemplate.getGenerationParameters());
		Hibernate.initialize(reportTemplate.getContentParameters());
		Hibernate.initialize(reportTemplate.getOutputParameters());
		Hibernate.initialize(reportTemplate.getGroupType1());
		Hibernate.initialize(reportTemplate.getGroupType2());
		Hibernate.initialize(reportTemplate.getGroupType3());
		Hibernate.initialize(reportTemplate.getGroupType4());
		Hibernate.initialize(reportTemplate.getGroupType5());
		Hibernate.initialize(reportTemplate.getGroupType6());
		Hibernate.initialize(reportTemplate.getReport());

		logger.info("ReportTemplate get by id == "+reportTemplate);

		return reportTemplate ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<ReportTemplate> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<ReportTemplate> reportTemplatesList = session.createQuery("from ReportTemplate").list();
        return reportTemplatesList;
    }

	@Override
	public void clone(ReportTemplate reportTemplate) {

    	ReportTemplate clonedTemplate = new ReportTemplate();

    	clonedTemplate.setGenerationParameters(reportTemplate.getGenerationParameters());
    	clonedTemplate.setName(reportTemplate.getName()+" копия ");
    	clonedTemplate.setReport(reportTemplate.getReport());
    	clonedTemplate.setContentParameters(reportTemplate.getContentParameters());
    	clonedTemplate.setFilterParameters(reportTemplate.getFilterParameters());
    	clonedTemplate.setGroupType1(reportTemplate.getGroupType1());
		clonedTemplate.setGroupType2(reportTemplate.getGroupType2());
		clonedTemplate.setGroupType3(reportTemplate.getGroupType3());
		clonedTemplate.setGroupType4(reportTemplate.getGroupType4());
		clonedTemplate.setGroupType5(reportTemplate.getGroupType5());
		clonedTemplate.setGroupType6(reportTemplate.getGroupType6());
		clonedTemplate.setOnDate(reportTemplate.getOnDate());
		clonedTemplate.setOutputParameters(reportTemplate.getOutputParameters());



		Session session = this.sessionFactory.getCurrentSession();
		session.persist(clonedTemplate);

		logger.info("ReportTemplate cloned == "+reportTemplate);

	}
 

}