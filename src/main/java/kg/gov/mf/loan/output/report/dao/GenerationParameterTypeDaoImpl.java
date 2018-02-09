package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.GenerationParameterType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenerationParameterTypeDaoImpl implements GenerationParameterTypeDao {

    private static final Logger logger = LoggerFactory.getLogger(GenerationParameterTypeDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public GenerationParameterTypeDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(GenerationParameterType generationParameterType) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(generationParameterType);
		
		logger.info("GenerationParameterType added == "+generationParameterType);
		
	} 


	@Override
	public void edit(GenerationParameterType generationParameterType) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(generationParameterType);
		
		logger.info("GenerationParameterType edited == "+generationParameterType);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		GenerationParameterType generationParameterType = (GenerationParameterType) session.load(GenerationParameterType.class, new Long (id));
		if(generationParameterType!=null)
		{
			session.delete(generationParameterType);
		}
		
		logger.info("GenerationParameterType deleted == "+generationParameterType);
		
	}


	@Override
	public GenerationParameterType findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		GenerationParameterType generationParameterType = (GenerationParameterType) session.load(GenerationParameterType.class, new Long (id));
		
		logger.info("GenerationParameterType get by id == "+generationParameterType);

		return generationParameterType ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<GenerationParameterType> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<GenerationParameterType> generationParameterTypesList = session.createQuery("from GenerationParameterType").list();
        return generationParameterTypesList;
    }
 

}