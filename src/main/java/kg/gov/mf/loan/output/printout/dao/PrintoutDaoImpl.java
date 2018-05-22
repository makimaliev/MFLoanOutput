package kg.gov.mf.loan.output.printout.dao;

import kg.gov.mf.loan.output.printout.dao.PrintoutDao;
import kg.gov.mf.loan.output.printout.model.Printout;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrintoutDaoImpl implements PrintoutDao {

    private static final Logger logger = LoggerFactory.getLogger(PrintoutDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public PrintoutDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(Printout printout) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(printout);
		
		logger.info("Printout added == "+printout);
		
	} 


	@Override
	public void edit(Printout printout) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(printout);
		
		logger.info("Printout edited == "+printout);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		Printout printout = (Printout) session.load(Printout.class, new Long (id));
		if(printout!=null)
		{
			session.delete(printout);
		}
		
		logger.info("Printout deleted == "+printout);
		
	}


	@Override
	public Printout findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		Printout printout = (Printout) session.load(Printout.class, new Long (id));
		
		logger.info("Printout get by id == "+printout);

		return printout ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<Printout> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Printout> printoutsList = session.createQuery("from Printout").list();
        return printoutsList;
    }
 

}