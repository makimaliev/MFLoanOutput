package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.LoanDebtTransferView;
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
public class LoanDebtTransferViewDaoImpl implements LoanDebtTransferViewDao {

    private static final Logger logger = LoggerFactory.getLogger(LoanDebtTransferViewDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public LoanDebtTransferViewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(LoanDebtTransferView loanDebtTransferView) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(loanDebtTransferView);
		
		logger.info("LoanDebtTransferView added == "+loanDebtTransferView);
		
	} 


	@Override
	public void edit(LoanDebtTransferView loanDebtTransferView) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(loanDebtTransferView);
		
		logger.info("LoanDebtTransferView edited == "+loanDebtTransferView);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		LoanDebtTransferView loanDebtTransferView = (LoanDebtTransferView) session.load(LoanDebtTransferView.class, new Long (id));
		if(loanDebtTransferView!=null)
		{
			session.delete(loanDebtTransferView);
		}
		
		logger.info("LoanDebtTransferView deleted == "+loanDebtTransferView);
		
	}


	@Override
	public LoanDebtTransferView findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		LoanDebtTransferView loanDebtTransferView = (LoanDebtTransferView) session.load(LoanDebtTransferView.class, new Long (id));
		
		logger.info("LoanDebtTransferView get by id == "+loanDebtTransferView);

		return loanDebtTransferView ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<LoanDebtTransferView> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<LoanDebtTransferView> loanDebtTransferViewsList = session.createQuery("from LoanDebtTransferView").list();
        return loanDebtTransferViewsList;
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<LoanDebtTransferView> findByParameter(LinkedHashMap<String,List<String>> parameters) {

		Session session = this.sessionFactory.getCurrentSession();

		ReportTool reportTool = new ReportTool();

		Criteria criteria = session.createCriteria(LoanDebtTransferView.class);

		reportTool.applyParameters(parameters,criteria);

		return criteria.list();
	}

	@Override
	public List<LoanDebtTransferView> findByParameter(LinkedHashMap<String, List<String>> parameters, Integer offset, Integer limit) {
		Session session = this.sessionFactory.getCurrentSession();

		ReportTool reportTool = new ReportTool();

		Criteria criteria = session.createCriteria(LoanDebtTransferView.class);

		reportTool.applyParameters(parameters,criteria);

		criteria.setFirstResult(offset);
		criteria.setMaxResults(limit);

		return criteria.list();
	}


}