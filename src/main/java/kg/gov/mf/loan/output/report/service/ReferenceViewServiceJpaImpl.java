package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.ReferenceViewDao;
import kg.gov.mf.loan.output.report.model.ReferenceView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ReferenceViewServiceJpaImpl implements ReferenceViewService {
	
	@Autowired
    private ReferenceViewDao referenceViewDao;
 
    public void setReferenceViewDao(ReferenceViewDao referenceViewDao) {
        this.referenceViewDao = referenceViewDao;
    }
 
    

	@Override
	@Transactional	
	public void create(ReferenceView referenceView) {
		this.referenceViewDao.create(referenceView);
		
	}

	@Override
	@Transactional	
	public void edit(ReferenceView referenceView) {
		this.referenceViewDao.edit(referenceView);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.referenceViewDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public ReferenceView findById(long id) {
		return this.referenceViewDao.findById(id);
	}

	@Override
    @Transactional
    public List<ReferenceView> findAll() {
        return this.referenceViewDao.findAll();
    }


	@Override
	@Transactional
	public List<ReferenceView> findByParameter(String link_type) {
		return this.referenceViewDao.findByParameter(link_type);
	}


}
