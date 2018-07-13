package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.OutputParameter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OutputParameterDaoImpl implements OutputParameterDao {

    private static final Logger logger = LoggerFactory.getLogger(OutputParameterDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public OutputParameterDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(OutputParameter outputParamater) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(outputParamater);
		
		logger.info("OutputParameter added == "+outputParamater);
		
	} 


	@Override
	public void edit(OutputParameter outputParamater) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(outputParamater);
		
		logger.info("OutputParameter edited == "+outputParamater);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		OutputParameter outputParamater = (OutputParameter) session.load(OutputParameter.class, new Long (id));
		if(outputParamater!=null)
		{
			session.delete(outputParamater);
		}
		
		logger.info("OutputParameter deleted == "+outputParamater);
		
	}


	@Override
	public OutputParameter findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		OutputParameter outputParamater = (OutputParameter) session.load(OutputParameter.class, new Long (id));
		
		logger.info("OutputParameter get by id == "+outputParamater);

		return outputParamater ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<OutputParameter> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<OutputParameter> outputParamatersList = session.createQuery("from OutputParameter").list();
        return outputParamatersList;
    }
 

}