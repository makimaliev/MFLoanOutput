package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.CollateralInspectionViewDao;
import kg.gov.mf.loan.output.report.model.CollateralInspectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CollateralInspectionViewServiceJpaImpl implements CollateralInspectionViewService {
	
	@Autowired
    private CollateralInspectionViewDao collateralInspectionViewDao;
 
    public void setCollateralInspectionViewDao(CollateralInspectionViewDao collateralInspectionViewDao) {
        this.collateralInspectionViewDao = collateralInspectionViewDao;
    }
 
    

	@Override
	@Transactional	
	public void create(CollateralInspectionView collateralInspectionView) {
		this.collateralInspectionViewDao.create(collateralInspectionView);
		
	}

	@Override
	@Transactional	
	public void edit(CollateralInspectionView collateralInspectionView) {
		this.collateralInspectionViewDao.edit(collateralInspectionView);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.collateralInspectionViewDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public CollateralInspectionView findById(long id) {
		return this.collateralInspectionViewDao.findById(id);
	}

	@Override
    @Transactional
    public List<CollateralInspectionView> findAll() {
        return this.collateralInspectionViewDao.findAll();
    }


	@Override
	@Transactional
	public List<CollateralInspectionView> findByParameter(LinkedHashMap<String,List<String>> parameters) {
		return this.collateralInspectionViewDao.findByParameter(parameters);
	}


}
