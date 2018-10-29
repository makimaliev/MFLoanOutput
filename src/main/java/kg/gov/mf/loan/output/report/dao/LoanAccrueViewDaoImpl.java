package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.LoanAccrueView;
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
public class LoanAccrueViewDaoImpl implements LoanAccrueViewDao {

    private static final Logger logger = LoggerFactory.getLogger(LoanAccrueViewDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public LoanAccrueViewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(LoanAccrueView loanAccrueView) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(loanAccrueView);
		
		logger.info("LoanAccrueView added == "+loanAccrueView);
		
	} 


	@Override
	public void edit(LoanAccrueView loanAccrueView) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(loanAccrueView);
		
		logger.info("LoanAccrueView edited == "+loanAccrueView);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		LoanAccrueView loanAccrueView = (LoanAccrueView) session.load(LoanAccrueView.class, new Long (id));
		if(loanAccrueView!=null)
		{
			session.delete(loanAccrueView);
		}
		
		logger.info("LoanAccrueView deleted == "+loanAccrueView);
		
	}


	@Override
	public LoanAccrueView findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		LoanAccrueView loanAccrueView = (LoanAccrueView) session.load(LoanAccrueView.class, new Long (id));
		
		logger.info("LoanAccrueView get by id == "+loanAccrueView);

		return loanAccrueView ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<LoanAccrueView> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<LoanAccrueView> loanAccrueViewsList = session.createQuery("from LoanAccrueView").list();
        return loanAccrueViewsList;
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<LoanAccrueView> findByParameter(LinkedHashMap<String,List<String>> parameters) {

		Session session = this.sessionFactory.getCurrentSession();

		ReportTool reportTool = new ReportTool();

		Criteria criteria = session.createCriteria(LoanAccrueView.class);

		reportTool.applyParameters(parameters,criteria);

		return criteria.list();
	}



 

}