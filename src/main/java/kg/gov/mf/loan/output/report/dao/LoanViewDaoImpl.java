package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.admin.org.model.Address;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.output.report.model.LoanView;
import kg.gov.mf.loan.output.report.utils.ReportTool;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LoanViewDaoImpl implements LoanViewDao {

    private static final Logger logger = LoggerFactory.getLogger(LoanViewDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public LoanViewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(LoanView loanView) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(loanView);
		
		logger.info("LoanView added == "+loanView);
		
	} 


	@Override
	public void edit(LoanView loanView) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(loanView);
		
		logger.info("LoanView edited == "+loanView);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		LoanView loanView = (LoanView) session.load(LoanView.class, new Long (id));
		if(loanView!=null)
		{
			session.delete(loanView);
		}
		
		logger.info("LoanView deleted == "+loanView);
		
	}


	@Override
	public LoanView findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		LoanView loanView = (LoanView) session.load(LoanView.class, new Long (id));
		
		logger.info("LoanView get by id == "+loanView);

		return loanView ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<LoanView> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<LoanView> loanViewsList = session.createQuery("from LoanView").list();
        return loanViewsList;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<LoanView> findByParameter(LinkedHashMap<String,List<String>> parameters) {

        Session session = this.sessionFactory.getCurrentSession();

        ReportTool reportTool = new ReportTool();

        Criteria criteria = session.createCriteria(LoanView.class);

        reportTool.applyParameters(parameters,criteria);

        return criteria.list();
    }

	@Override
	@SuppressWarnings("unchecked")

	public List<LoanView> findByParameters(LinkedHashMap<String, List<String>> parameter, Integer perPage, Integer offset) {
		Session session = this.sessionFactory.getCurrentSession();

		ReportTool reportTool = new ReportTool();

		Criteria criteria = session.createCriteria(LoanView.class);

		reportTool.applyParameters(parameter,criteria);

        criteria.setFirstResult(offset);
        criteria.setMaxResults(perPage);

		return criteria.list();
	}

    @Override
    public int findByParamete(LinkedHashMap<String, List<String>> parameter) {
        Session session = this.sessionFactory.getCurrentSession();

        ReportTool reportTool = new ReportTool();

        Criteria criteria = session.createCriteria(LoanView.class);

        reportTool.applyParameters(parameter,criteria);


        return criteria.list().size();
    }

    @Override
    public List<LoanView> findByParameter(LinkedHashMap<String, List<String>> parameter, Integer perPage, Integer offset, String sortStr, String sortField) {
        Session session = this.sessionFactory.getCurrentSession();

        ReportTool reportTool = new ReportTool();

        Criteria criteria = session.createCriteria(LoanView.class);

        reportTool.applyParameters(parameter,criteria);

        if(sortStr.equals("asc")){
            criteria.addOrder(Order.asc(sortField));
        }
        else if(sortStr.equals("desc")){
            criteria.addOrder(Order.desc(sortField));
        }
        criteria.setFirstResult(offset);
        criteria.setMaxResults(perPage);

        return criteria.list();
    }



 

}