package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.CollateralItemView;
import kg.gov.mf.loan.output.report.utils.CollateralItemReportDataManager;
import kg.gov.mf.loan.output.report.utils.PaymentReportDataManager;
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
public class CollateralItemViewDaoImpl implements CollateralItemViewDao {

    private static final Logger logger = LoggerFactory.getLogger(CollateralItemViewDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public CollateralItemViewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(CollateralItemView collateralItemView) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(collateralItemView);
		
		logger.info("CollateralItemView added == "+collateralItemView);
		
	} 


	@Override
	public void edit(CollateralItemView collateralItemView) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(collateralItemView);
		
		logger.info("CollateralItemView edited == "+collateralItemView);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		CollateralItemView collateralItemView = (CollateralItemView) session.load(CollateralItemView.class, new Long (id));
		if(collateralItemView!=null)
		{
			session.delete(collateralItemView);
		}
		
		logger.info("CollateralItemView deleted == "+collateralItemView);
		
	}


	@Override
	public CollateralItemView findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		CollateralItemView collateralItemView = (CollateralItemView) session.load(CollateralItemView.class, new Long (id));
		
		logger.info("CollateralItemView get by id == "+collateralItemView);

		return collateralItemView ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<CollateralItemView> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<CollateralItemView> collateralItemViewsList = session.createQuery("from CollateralItemView").list();
        return collateralItemViewsList;
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<CollateralItemView> findByParameter(LinkedHashMap<String,List<Long>> parameters) {

		Session session = this.sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(CollateralItemView.class);

		CollateralItemReportDataManager collateralItemReportDataManager =  new CollateralItemReportDataManager();


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
				case "payment":
					criteria.add(Restrictions.in("v_payment_id",ids));
					break;
				case "paymentDateFrom":
					criteria.add(Restrictions.ge("v_payment_date",new Date((ids.get(0)))));
					System.out.println(new Date((ids.get(0))));
					break;
				case "paymentDateTo":
					criteria.add(Restrictions.lt("v_payment_date",new Date((ids.get(0)))));
					System.out.println(new Date((ids.get(0))));
					break;
				case "work_sector":
					criteria.add(Restrictions.in("v_debtor_work_sector_id",ids));
					break;
				case "orderBy":
					for (Long id:ids
						 )
					{

						switch(collateralItemReportDataManager.getParameterTypeNameById(id.toString()))
						{
							case "region":
								criteria.addOrder(Order.asc("v_debtor_region_id"));
								break;
							case "district":
								criteria.addOrder(Order.asc("v_debtor_district_id"));
								break;
							case "debtor":
								criteria.addOrder(Order.asc("v_debtor_name"));
								break;
							case "loan":
								criteria.addOrder(Order.asc("v_loan_id"));
								break;
							case "payment":
								criteria.addOrder(Order.asc("v_payment_id"));
								break;
							case "work_sector":
								criteria.addOrder(Order.asc("v_debtor_work_sector_id"));
								break;
							case "collateral_agreement":
								criteria.addOrder(Order.asc("v_ca_id"));
								break;

							case "collateral_item":
								criteria.addOrder(Order.asc("v_ci_id"));
								break;

						}

					}


					break;







			}


		}

		//List<CollateralItemView> collateralItemViewsList = session.createQuery("from CollateralItemView").list();

		return criteria.list();
	}



 

}