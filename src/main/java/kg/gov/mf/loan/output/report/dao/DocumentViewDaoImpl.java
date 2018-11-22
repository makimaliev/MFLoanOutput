package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.DocumentView;
import kg.gov.mf.loan.output.report.utils.ReportTool;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public class DocumentViewDaoImpl implements DocumentViewDao {

    private static final Logger logger = LoggerFactory.getLogger(DocumentViewDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }


    @Autowired
    public DocumentViewDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
 



	@Override
	public void create(DocumentView documentView) {
		
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(documentView);
		
		logger.info("DocumentView added == "+documentView);
		
	} 


	@Override
	public void edit(DocumentView documentView) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.update(documentView);
		
		logger.info("DocumentView edited == "+documentView);
	}


	@Override
	public void deleteById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		DocumentView documentView = (DocumentView) session.load(DocumentView.class, new Long (id));
		if(documentView!=null)
		{
			session.delete(documentView);
		}
		
		logger.info("DocumentView deleted == "+documentView);
		
	}


	@Override
	public DocumentView findById(long id) {
		
		Session session = this.sessionFactory.getCurrentSession();
		DocumentView documentView = (DocumentView) session.load(DocumentView.class, new Long (id));
		
		logger.info("DocumentView get by id == "+documentView);

		return documentView ;
	}


	
    @SuppressWarnings("unchecked")
    @Override
    public List<DocumentView> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<DocumentView> documentViewsList = session.createQuery("from DocumentView").list();
        return documentViewsList;
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentView> findByParameter(LinkedHashMap<String,List<String>> parameters) {

		Session session = this.sessionFactory.getCurrentSession();

		ReportTool reportTool = new ReportTool();

		Criteria criteria = session.createCriteria(DocumentView.class);

		reportTool.applyParameters(parameters,criteria);

		return criteria.list();
	}


	@Override
	public List<DocumentView> findByParameter(LinkedHashMap<String, List<String>> parameters, Integer offset, Integer limit, String sortStr, String sortField) {

    	Session session=this.sessionFactory.getCurrentSession();

    	ReportTool reportTool=new ReportTool();

    	Criteria criteria=session.createCriteria(DocumentView.class);

    	reportTool.applyParameters(parameters,criteria);

		if(sortStr.equals("asc")){
			criteria.addOrder(Order.asc(sortField));
		}
		else if(sortStr.equals("desc")){
			criteria.addOrder(Order.desc(sortField));
		}
		criteria.setFirstResult(offset);
		criteria.setMaxResults(limit);


    	return criteria.list();
	}

	@Override
	public Long getCount(LinkedHashMap<String, List<String>> parameters) {
		Session session=this.sessionFactory.getCurrentSession();

		ReportTool reportTool=new ReportTool();

		Criteria criteria=session.createCriteria(DocumentView.class);

		reportTool.applyParameters(parameters,criteria);

		Long count= (Long) criteria
				.setProjection(Projections.rowCount())
				.uniqueResult();

		return count;
	}


}