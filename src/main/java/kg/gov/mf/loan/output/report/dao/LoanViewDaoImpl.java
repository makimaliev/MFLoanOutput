package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.LoanView;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
	public List<LoanView> findByParameter(LinkedHashMap<String,List<Long>> parameters) {

		Session session = this.sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(LoanView.class);


		for (Map.Entry<String, List<Long>> parameterInLoop : parameters.entrySet())
		{
			String paramaterType = parameterInLoop.getKey();

			List<Long> ids = parameterInLoop.getValue();

			switch(paramaterType)
			{
				case "region":
					criteria.add(Restrictions.in("v_debtor_region_id",ids));
					break;
				case "district":
					criteria.add(Restrictions.in("v_debtor_district_id",ids));
					break;
				case "debtor":
					criteria.add(Restrictions.in("v_debtor_id",ids));
					break;
				case "loan":
					criteria.add(Restrictions.in("v_loan_id",ids));
					break;

			}


		}

		//List<LoanView> loanViewsList = session.createQuery("from LoanView").list();

		return criteria.list();
	}



 

}