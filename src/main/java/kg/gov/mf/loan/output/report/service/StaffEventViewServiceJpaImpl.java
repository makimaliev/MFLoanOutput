package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.StaffEventViewDao;
import kg.gov.mf.loan.output.report.model.StaffEventView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class StaffEventViewServiceJpaImpl implements StaffEventViewService {
	
	@Autowired
    private StaffEventViewDao staffEventViewDao;
 
    public void setStaffEventViewDao(StaffEventViewDao staffEventViewDao) {
        this.staffEventViewDao = staffEventViewDao;
    }
 
    

	@Override
	@Transactional	
	public void create(StaffEventView staffEventView) {
		this.staffEventViewDao.create(staffEventView);
		
	}

	@Override
	@Transactional	
	public void edit(StaffEventView staffEventView) {
		this.staffEventViewDao.edit(staffEventView);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.staffEventViewDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public StaffEventView findById(long id) {
		return this.staffEventViewDao.findById(id);
	}

	@Override
    @Transactional
    public List<StaffEventView> findAll() {
        return this.staffEventViewDao.findAll();
    }


	@Override
	@Transactional
	public List<StaffEventView> findByParameter(LinkedHashMap<String,List<String>> parameters) {
		return this.staffEventViewDao.findByParameter(parameters);
	}

	@Override
	@Transactional
	public List<StaffEventView> findByParameter(LinkedHashMap<String, List<String>> parameters, Integer offset, Integer limit, String sortStr, String sortField) {
		return this.staffEventViewDao.findByParameter(parameters,offset,limit,sortStr,sortField);
	}

	@Override
	@Transactional
	public Long getCount(LinkedHashMap<String, List<String>> parameters) {
		return this.staffEventViewDao.getCount(parameters);
	}


}
