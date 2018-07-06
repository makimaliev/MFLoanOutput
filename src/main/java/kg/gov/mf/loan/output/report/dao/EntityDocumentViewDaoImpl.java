package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.EntityDocumentView;
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
					criteria.add(Restrictions.in("v_owner_region_id",ids));
					break;
				case "district":
					criteria.add(Restrictions.in("v_owner_district_id",ids));
					break;
				case "credit_order":
					criteria.add(Restrictions.in("v_co_id",ids));
					break;
				case "applied_entity_list":
					criteria.add(Restrictions.in("v_ael_id",ids));
					break;

				case "applied_entity":
					criteria.add(Restrictions.in("v_applied_entity_id",ids));
					break;

				case "document_package":
					criteria.add(Restrictions.in("v_document_package_id",ids));
					break;

				case "entity_document":
					criteria.add(Restrictions.in("v_entity_document_id",ids));
					break;

				case "orderBy":
					for (Long id:ids
						 )
					{
						switch(new ReportTool().getParameterTypeNameById(id.toString()))
						{
							case "region":
								criteria.addOrder(Order.asc("v_owner_region_id"));
								break;
							case "district":
								criteria.addOrder(Order.asc("v_owner_district_id"));
								break;
							case "credit_order":
								criteria.addOrder(Order.asc("v_co_id"));
								break;
							case "applied_entity_list":
								criteria.addOrder(Order.asc("v_ael_id"));
								break;
							case "applied_entity":
								criteria.addOrder(Order.asc("v_applied_entity_id"));
								break;
							case "document_package":
								criteria.addOrder(Order.asc("v_document_package_id"));
								break;

							case "entity_document":
								criteria.addOrder(Order.asc("v_entity_document_id"));
								break;

						}

					}

					break;







			}


		}

		//List<EntityDocumentView> entitydocumentViewsList = session.createQuery("from EntityDocumentView").list();

		return criteria.list();
	}



 

}