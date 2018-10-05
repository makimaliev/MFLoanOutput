package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.LoanSummaryView;
import kg.gov.mf.loan.output.report.utils.PaymentReportDataManager;
import kg.gov.mf.loan.output.report.utils.ReportTool;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LoanSummaryViewDaoImpl implements LoanSummaryViewDao {

    private static final Logger logger = LoggerFactory.getLogger(LoanSummaryViewDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public LoanSummaryViewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(LoanSummaryView loanSummaryView) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(loanSummaryView);
		
		logger.info("LoanSummaryView added == "+loanSummaryView);
		
	} 


	@Override
	public void edit(LoanSummaryView loanSummaryView) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(loanSummaryView);
		
		logger.info("LoanSummaryView edited == "+loanSummaryView);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		LoanSummaryView loanSummaryView = (LoanSummaryView) session.load(LoanSummaryView.class, new Long (id));
		if(loanSummaryView!=null)
		{
			session.delete(loanSummaryView);
		}
		
		logger.info("LoanSummaryView deleted == "+loanSummaryView);
		
	}


	@Override
	public LoanSummaryView findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		LoanSummaryView loanSummaryView = (LoanSummaryView) session.load(LoanSummaryView.class, new Long (id));
		
		logger.info("LoanSummaryView get by id == "+loanSummaryView);

		return loanSummaryView ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<LoanSummaryView> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<LoanSummaryView> loanSummaryViewsList = session.createQuery("from LoanSummaryView").list();
        return loanSummaryViewsList;
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<LoanSummaryView> findByParameter(LinkedHashMap<String,List<String>> parameters) {

		Session session = this.sessionFactory.getCurrentSession();

		ReportTool reportTool = new ReportTool();

		Criteria criteria = session.createCriteria(LoanSummaryView.class);

		reportTool.applyParameters(parameters,criteria);

		return criteria.list();
	}



 

}