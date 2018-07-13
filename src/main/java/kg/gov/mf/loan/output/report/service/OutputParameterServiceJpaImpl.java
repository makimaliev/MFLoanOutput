package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.OutputParameterDao;
import kg.gov.mf.loan.output.report.model.OutputParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OutputParameterServiceJpaImpl implements OutputParameterService {
	
	@Autowired
    private OutputParameterDao outputParameterDao;
 
    public void setOutputParameterDao(OutputParameterDao outputParameterDao) {
        this.outputParameterDao = outputParameterDao;
    }
 
    

	@Override
	@Transactional	
	public void create(OutputParameter outputParameter) {
		this.outputParameterDao.create(outputParameter);
		
	}

	@Override
	@Transactional	
	public void edit(OutputParameter outputParameter) {
		this.outputParameterDao.edit(outputParameter);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.outputParameterDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public OutputParameter findById(long id) {
		return this.outputParameterDao.findById(id);
	}

	@Override
    @Transactional
    public List<OutputParameter> findAll() {
        return this.outputParameterDao.findAll();
    }
}
