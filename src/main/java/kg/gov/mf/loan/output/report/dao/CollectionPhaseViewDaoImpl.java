package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.CollectionPhaseView;
import kg.gov.mf.loan.output.report.utils.CollectionPhaseReportDataManager;
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
public class CollectionPhaseViewDaoImpl implements CollectionPhaseViewDao {

    private static final Logger logger = LoggerFactory.getLogger(CollectionPhaseViewDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public CollectionPhaseViewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(CollectionPhaseView collectionPhaseView) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(collectionPhaseView);
		
		logger.info("CollectionPhaseView added == "+collectionPhaseView);
		
	} 


	@Override
	public void edit(CollectionPhaseView collectionPhaseView) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(collectionPhaseView);
		
		logger.info("CollectionPhaseView edited == "+collectionPhaseView);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		CollectionPhaseView collectionPhaseView = (CollectionPhaseView) session.load(CollectionPhaseView.class, new Long (id));
		if(collectionPhaseView!=null)
		{
			session.delete(collectionPhaseView);
		}
		
		logger.info("CollectionPhaseView deleted == "+collectionPhaseView);
		
	}


	@Override
	public CollectionPhaseView findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		CollectionPhaseView collectionPhaseView = (CollectionPhaseView) session.load(CollectionPhaseView.class, new Long (id));
		
		logger.info("CollectionPhaseView get by id == "+collectionPhaseView);

		return collectionPhaseView ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<CollectionPhaseView> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<CollectionPhaseView> collectionPhaseViewsList = session.createQuery("from CollectionPhaseView").list();
        return collectionPhaseViewsList;
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<CollectionPhaseView> findByParameter(LinkedHashMap<String,List<Long>> parameters) {

		Session session = this.sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(CollectionPhaseView.class);

		CollectionPhaseReportDataManager collectionPhaseReportDataManager =  new CollectionPhaseReportDataManager();


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

				case "collection_phase":
					criteria.add(Restrictions.in("v_cph_id",ids));
					break;
				case "orderBy":
					for (Long id:ids
						 )
					{

						switch(collectionPhaseReportDataManager.getParameterTypeNameById(id.toString()))
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
							case "collection_procedure":
								criteria.addOrder(Order.asc("v_cp_id"));
								break;

							case "collection_phase":
								criteria.addOrder(Order.asc("v_cph_id"));
								break;

						}

					}


					break;







			}


		}

		//List<CollectionPhaseView> collectionPhaseViewsList = session.createQuery("from CollectionPhaseView").list();

		return criteria.list();
	}



 

}