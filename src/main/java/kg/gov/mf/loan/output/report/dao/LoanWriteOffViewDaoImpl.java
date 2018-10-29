package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.LoanWriteOffView;
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
public class LoanWriteOffViewDaoImpl implements LoanWriteOffViewDao {

    private static final Logger logger = LoggerFactory.getLogger(LoanWriteOffViewDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public LoanWriteOffViewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(LoanWriteOffView loanWriteOffView) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(loanWriteOffView);
		
		logger.info("LoanWriteOffView added == "+loanWriteOffView);
		
	} 


	@Override
	public void edit(LoanWriteOffView loanWriteOffView) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(loanWriteOffView);
		
		logger.info("LoanWriteOffView edited == "+loanWriteOffView);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		LoanWriteOffView loanWriteOffView = (LoanWriteOffView) session.load(LoanWriteOffView.class, new Long (id));
		if(loanWriteOffView!=null)
		{
			session.delete(loanWriteOffView);
		}
		
		logger.info("LoanWriteOffView deleted == "+loanWriteOffView);
		
	}


	@Override
	public LoanWriteOffView findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		LoanWriteOffView loanWriteOffView = (LoanWriteOffView) session.load(LoanWriteOffView.class, new Long (id));
		
		logger.info("LoanWriteOffView get by id == "+loanWriteOffView);

		return loanWriteOffView ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<LoanWriteOffView> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<LoanWriteOffView> loanWriteOffViewsList = session.createQuery("from LoanWriteOffView").list();
        return loanWriteOffViewsList;
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<LoanWriteOffView> findByParameter(LinkedHashMap<String,List<String>> parameters) {

		Session session = this.sessionFactory.getCurrentSession();

		ReportTool reportTool = new ReportTool();

		Criteria criteria = session.createCriteria(LoanWriteOffView.class);

		reportTool.applyParameters(parameters,criteria);

		return criteria.list();
	}



 

}