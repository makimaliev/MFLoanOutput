package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.FilterParameterDao;
import kg.gov.mf.loan.output.report.model.FilterParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FilterParameterServiceJpaImpl implements FilterParameterService {
	
	@Autowired
    private FilterParameterDao filterParameterDao;
 
    public void setFilterParameterDao(FilterParameterDao filterParameterDao) {
        this.filterParameterDao = filterParameterDao;
    }
 
    

	@Override
	@Transactional	
	public void create(FilterParameter filterParameter) {
		this.filterParameterDao.create(filterParameter);
		
	}

	@Override
	@Transactional	
	public void edit(FilterParameter filterParameter) {
		this.filterParameterDao.edit(filterParameter);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.filterParameterDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public FilterParameter findById(long id) {
		return this.filterParameterDao.findById(id);
	}

	@Override
    @Transactional
    public List<FilterParameter> findAll() {
        return this.filterParameterDao.findAll();
    }
}
