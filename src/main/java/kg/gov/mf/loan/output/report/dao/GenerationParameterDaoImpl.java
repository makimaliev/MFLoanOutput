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
public class GenerationParameterDaoImpl implements GenerationParameterDao {
     
    private static final Logger logger = LoggerFactory.getLogger(GenerationParameterDaoImpl.class);
 
    @Autowired
    private SessionFactory sessionFactory;
     
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
    
    
    @Autowired
    public GenerationParameterDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(GenerationParameter generationParameter) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(generationParameter);
		
		logger.info("GenerationParameter added == "+generationParameter);
		
	} 


	@Override
	public void edit(GenerationParameter generationParameter) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(generationParameter);
		
		logger.info("GenerationParameter edited == "+generationParameter);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		GenerationParameter generationParameter = (GenerationParameter) session.load(GenerationParameter.class, new Long (id));
		if(generationParameter!=null)
		{
			session.delete(generationParameter);
		}
		
		logger.info("GenerationParameter deleted == "+generationParameter);
		
	}


	@Override
	public GenerationParameter findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		GenerationParameter generationParameter = (GenerationParameter) session.load(GenerationParameter.class, new Long (id));
		
		logger.info("GenerationParameter get by id == "+generationParameter);

		return generationParameter ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<GenerationParameter> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<GenerationParameter> generationParametersList = session.createQuery("from GenerationParameter").list();
        return generationParametersList;
    }
 

}