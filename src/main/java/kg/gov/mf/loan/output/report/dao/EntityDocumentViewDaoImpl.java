package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.EntityDocumentView;
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
public class EntityDocumentViewDaoImpl implements EntityDocumentViewDao {

    private static final Logger logger = LoggerFactory.getLogger(EntityDocumentViewDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public EntityDocumentViewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(EntityDocumentView entitydocumentView) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(entitydocumentView);
		
		logger.info("EntityDocumentView added == "+entitydocumentView);
		
	} 


	@Override
	public void edit(EntityDocumentView entitydocumentView) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(entitydocumentView);
		
		logger.info("EntityDocumentView edited == "+entitydocumentView);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		EntityDocumentView entitydocumentView = (EntityDocumentView) session.load(EntityDocumentView.class, new Long (id));
		if(entitydocumentView!=null)
		{
			session.delete(entitydocumentView);
		}
		
		logger.info("EntityDocumentView deleted == "+entitydocumentView);
		
	}


	@Override
	public EntityDocumentView findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		EntityDocumentView entitydocumentView = (EntityDocumentView) session.load(EntityDocumentView.class, new Long (id));
		
		logger.info("EntityDocumentView get by id == "+entitydocumentView);

		return entitydocumentView ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<EntityDocumentView> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<EntityDocumentView> entitydocumentViewsList = session.createQuery("from EntityDocumentView").list();
        return entitydocumentViewsList;
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<EntityDocumentView> findByParameter(LinkedHashMap<String,List<Long>> parameters) {

		Session session = this.sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(EntityDocumentView.class);

		PaymentReportDataManager paymentReportDataManager =  new PaymentReportDataManager();


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

				case "onDate":
					criteria.add(Restrictions.le("v_ls_onDate",new Date((ids.get(0)))));
					break;

				case "betweenDates":
					criteria.add(Restrictions.ge("v_ls_onDate",new Date((ids.get(0)))));
					criteria.add(Restrictions.le("v_ls_onDate",new Date((ids.get(1)))));
					break;


				case "work_sector":
					criteria.add(Restrictions.in("v_debtor_work_sector_id",ids));
					break;
				case "orderBy":
					for (Long id:ids
						 )
					{
						switch(paymentReportDataManager.getParameterTypeNameById(id.toString()))
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
						}

					}

					criteria.addOrder(Order.asc("v_ls_onDate"));

					break;







			}


		}

		//List<EntityDocumentView> entitydocumentViewsList = session.createQuery("from EntityDocumentView").list();

		return criteria.list();
	}



 

}