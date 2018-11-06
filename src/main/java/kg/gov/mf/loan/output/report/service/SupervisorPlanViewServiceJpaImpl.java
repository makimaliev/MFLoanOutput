package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.SupervisorPlanViewDao;
import kg.gov.mf.loan.output.report.model.SupervisorPlanView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class SupervisorPlanViewServiceJpaImpl implements SupervisorPlanViewService {
	
	@Autowired
    private SupervisorPlanViewDao supervisorPlanViewDao;
 
    public void setSupervisorPlanViewDao(SupervisorPlanViewDao supervisorPlanViewDao) {
        this.supervisorPlanViewDao = supervisorPlanViewDao;
    }
 
    

	@Override
	@Transactional	
	public void create(SupervisorPlanView supervisorPlanView) {
		this.supervisorPlanViewDao.create(supervisorPlanView);
		
	}

	@Override
	@Transactional	
	public void edit(SupervisorPlanView supervisorPlanView) {
		this.supervisorPlanViewDao.edit(supervisorPlanView);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.supervisorPlanViewDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public SupervisorPlanView findById(long id) {
		return this.supervisorPlanViewDao.findById(id);
	}

	@Override
    @Transactional
    public List<SupervisorPlanView> findAll() {
        return this.supervisorPlanViewDao.findAll();
    }


	@Override
	@Transactional
	public List<SupervisorPlanView> findByParameter(LinkedHashMap<String,List<String>> parameters) {
		return this.supervisorPlanViewDao.findByParameter(parameters);
	}

	@Override
	@Transactional
	public List<SupervisorPlanView> findByParameter(LinkedHashMap<String, List<String>> parameters, Integer offset, Integer limit) {
		return this.supervisorPlanViewDao.findByParameter(parameters,offset,limit);
	}


}
