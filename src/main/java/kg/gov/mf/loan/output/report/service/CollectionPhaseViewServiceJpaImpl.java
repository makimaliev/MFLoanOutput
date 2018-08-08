package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.CollectionPhaseViewDao;
import kg.gov.mf.loan.output.report.model.CollectionPhaseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CollectionPhaseViewServiceJpaImpl implements CollectionPhaseViewService {
	
	@Autowired
    private CollectionPhaseViewDao collectionPhaseViewDao;
 
    public void setCollectionPhaseViewDao(CollectionPhaseViewDao collectionPhaseViewDao) {
        this.collectionPhaseViewDao = collectionPhaseViewDao;
    }
 
    

	@Override
	@Transactional	
	public void create(CollectionPhaseView collectionPhaseView) {
		this.collectionPhaseViewDao.create(collectionPhaseView);
		
	}

	@Override
	@Transactional	
	public void edit(CollectionPhaseView collectionPhaseView) {
		this.collectionPhaseViewDao.edit(collectionPhaseView);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.collectionPhaseViewDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public CollectionPhaseView findById(long id) {
		return this.collectionPhaseViewDao.findById(id);
	}

	@Override
    @Transactional
    public List<CollectionPhaseView> findAll() {
        return this.collectionPhaseViewDao.findAll();
    }


	@Override
	@Transactional
	public List<CollectionPhaseView> findByParameter(LinkedHashMap<String,List<String>> parameters) {
		return this.collectionPhaseViewDao.findByParameter(parameters);
	}


}
