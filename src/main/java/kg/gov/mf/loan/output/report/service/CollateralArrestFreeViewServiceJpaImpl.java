package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.CollateralArrestFreeViewDao;
import kg.gov.mf.loan.output.report.model.CollateralArrestFreeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CollateralArrestFreeViewServiceJpaImpl implements CollateralArrestFreeViewService {
	
	@Autowired
    private CollateralArrestFreeViewDao collateralArrestFreeViewDao;
 
    public void setCollateralArrestFreeViewDao(CollateralArrestFreeViewDao collateralArrestFreeViewDao) {
        this.collateralArrestFreeViewDao = collateralArrestFreeViewDao;
    }
 
    

	@Override
	@Transactional	
	public void create(CollateralArrestFreeView collateralArrestFreeView) {
		this.collateralArrestFreeViewDao.create(collateralArrestFreeView);
		
	}

	@Override
	@Transactional	
	public void edit(CollateralArrestFreeView collateralArrestFreeView) {
		this.collateralArrestFreeViewDao.edit(collateralArrestFreeView);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.collateralArrestFreeViewDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public CollateralArrestFreeView findById(long id) {
		return this.collateralArrestFreeViewDao.findById(id);
	}

	@Override
    @Transactional
    public List<CollateralArrestFreeView> findAll() {
        return this.collateralArrestFreeViewDao.findAll();
    }


	@Override
	@Transactional
	public List<CollateralArrestFreeView> findByParameter(LinkedHashMap<String,List<String>> parameters) {
		return this.collateralArrestFreeViewDao.findByParameter(parameters);
	}


}
