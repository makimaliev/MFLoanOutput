package kg.gov.mf.loan.output.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kg.gov.mf.loan.output.report.dao.*;
import kg.gov.mf.loan.output.report.model.*;

@Service
public class GenerationParameterServiceJpaImpl implements GenerationParameterService {
	
	@Autowired
    private GenerationParameterDao generationParameterDao;
 
    public void setGenerationParameterDao(GenerationParameterDao generationParameterDao) {
        this.generationParameterDao = generationParameterDao;
    }
 
    

	@Override
	@Transactional	
	public void create(GenerationParameter generationParameter) {
		this.generationParameterDao.create(generationParameter);
		
	}

	@Override
	@Transactional	
	public void edit(GenerationParameter generationParameter) {
		this.generationParameterDao.edit(generationParameter);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.generationParameterDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public GenerationParameter findById(long id) {
		return this.generationParameterDao.findById(id);
	}

	@Override
    @Transactional
    public List<GenerationParameter> findAll() {
        return this.generationParameterDao.findAll();
    }
}
