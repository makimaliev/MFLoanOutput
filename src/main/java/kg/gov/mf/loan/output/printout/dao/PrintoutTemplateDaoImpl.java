package kg.gov.mf.loan.output.printout.dao;

import kg.gov.mf.loan.output.printout.dao.PrintoutTemplateDao;
import kg.gov.mf.loan.output.printout.model.PrintoutTemplate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrintoutTemplateDaoImpl implements PrintoutTemplateDao {

    private static final Logger logger = LoggerFactory.getLogger(PrintoutTemplateDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public PrintoutTemplateDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(PrintoutTemplate printoutTemplate) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(printoutTemplate);
		
		logger.info("PrintoutTemplate added == "+printoutTemplate);
		
	} 


	@Override
	public void edit(PrintoutTemplate printoutTemplate) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(printoutTemplate);
		
		logger.info("PrintoutTemplate edited == "+printoutTemplate);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		PrintoutTemplate printoutTemplate = (PrintoutTemplate) session.load(PrintoutTemplate.class, new Long (id));
		if(printoutTemplate!=null)
		{
			session.delete(printoutTemplate);
		}
		
		logger.info("PrintoutTemplate deleted == "+printoutTemplate);
		
	}


	@Override
	public PrintoutTemplate findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		PrintoutTemplate printoutTemplate = (PrintoutTemplate) session.load(PrintoutTemplate.class, new Long (id));
		
		logger.info("PrintoutTemplate get by id == "+printoutTemplate);

		return printoutTemplate ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<PrintoutTemplate> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<PrintoutTemplate> printoutTemplatesList = session.createQuery("from PrintoutTemplate").list();
        return printoutTemplatesList;
    }
 

}