package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.EntityDocumentViewDao;
import kg.gov.mf.loan.output.report.model.EntityDocumentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class EntityDocumentViewServiceJpaImpl implements EntityDocumentViewService {
	
	@Autowired
    private EntityDocumentViewDao entityDocumentViewDao;
 
    public void setEntityDocumentViewDao(EntityDocumentViewDao entityDocumentViewDao) {
        this.entityDocumentViewDao = entityDocumentViewDao;
    }
 
    

	@Override
	@Transactional	
	public void create(EntityDocumentView entityDocumentView) {
		this.entityDocumentViewDao.create(entityDocumentView);
		
	}

	@Override
	@Transactional	
	public void edit(EntityDocumentView entityDocumentView) {
		this.entityDocumentViewDao.edit(entityDocumentView);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.entityDocumentViewDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public EntityDocumentView findById(long id) {
		return this.entityDocumentViewDao.findById(id);
	}

	@Override
    @Transactional
    public List<EntityDocumentView> findAll() {
        return this.entityDocumentViewDao.findAll();
    }


	@Override
	@Transactional
	public List<EntityDocumentView> findByParameter(LinkedHashMap<String,List<String>> parameters) {
		return this.entityDocumentViewDao.findByParameter(parameters);
	}

	@Override
	@Transactional
	public List<EntityDocumentView> findByParameter(LinkedHashMap<String, List<String>> parameters, int limit, int offset, String sortStr, String sortField) {
		return this.entityDocumentViewDao.findByParameter(parameters,limit,offset,sortStr,sortField);
	}


}
