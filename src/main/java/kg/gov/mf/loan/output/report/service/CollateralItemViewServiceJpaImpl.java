package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.CollateralItemViewDao;
import kg.gov.mf.loan.output.report.model.CollateralItemView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CollateralItemViewServiceJpaImpl implements CollateralItemViewService {
	
	@Autowired
    private CollateralItemViewDao collateralItemViewDao;
 
    public void setCollateralItemViewDao(CollateralItemViewDao collateralItemViewDao) {
        this.collateralItemViewDao = collateralItemViewDao;
    }
 
    

	@Override
	@Transactional	
	public void create(CollateralItemView collateralItemView) {
		this.collateralItemViewDao.create(collateralItemView);
		
	}

	@Override
	@Transactional	
	public void edit(CollateralItemView collateralItemView) {
		this.collateralItemViewDao.edit(collateralItemView);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.collateralItemViewDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public CollateralItemView findById(long id) {
		return this.collateralItemViewDao.findById(id);
	}

	@Override
    @Transactional
    public List<CollateralItemView> findAll() {
        return this.collateralItemViewDao.findAll();
    }


	@Override
	@Transactional
	public List<CollateralItemView> findByParameter(LinkedHashMap<String,List<String>> parameters) {
		return this.collateralItemViewDao.findByParameter(parameters);
	}


}
