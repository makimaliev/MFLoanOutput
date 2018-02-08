package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.ContentParameterDao;
import kg.gov.mf.loan.output.report.model.ContentParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContentParameterServiceJpaImpl implements ContentParameterService {
	
	@Autowired
    private ContentParameterDao contentParameterDao;
 
    public void setContentParameterDao(ContentParameterDao contentParameterDao) {
        this.contentParameterDao = contentParameterDao;
    }
 
    

	@Override
	@Transactional	
	public void create(ContentParameter contentParameter) {
		this.contentParameterDao.create(contentParameter);
		
	}

	@Override
	@Transactional	
	public void edit(ContentParameter contentParameter) {
		this.contentParameterDao.edit(contentParameter);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.contentParameterDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public ContentParameter findById(long id) {
		return this.contentParameterDao.findById(id);
	}

	@Override
    @Transactional
    public List<ContentParameter> findAll() {
        return this.contentParameterDao.findAll();
    }
}
