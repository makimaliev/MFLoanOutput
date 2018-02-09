package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.GenerationParameterTypeDao;
import kg.gov.mf.loan.output.report.dao.GenerationParameterDao;
import kg.gov.mf.loan.output.report.model.GenerationParameterType;
import kg.gov.mf.loan.output.report.model.GenerationParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenerationParameterTypeServiceJpaImpl implements GenerationParameterTypeService {

	@Autowired
	private GenerationParameterTypeDao generationParameterTypeDao;

	public void setGenerationParameterTypeDao(GenerationParameterTypeDao generationParameterTypeDao) {
		this.generationParameterTypeDao = generationParameterTypeDao;
	}



	@Override
	@Transactional
	public void create(GenerationParameterType generationParameterType) {
		this.generationParameterTypeDao.create(generationParameterType);

	}

	@Override
	@Transactional
	public void edit(GenerationParameterType generationParameterType) {
		this.generationParameterTypeDao.edit(generationParameterType);

	}

	@Override
	@Transactional
	public void deleteById(long id) {
		this.generationParameterTypeDao.deleteById(id);

	}

	@Override
	@Transactional
	public GenerationParameterType findById(long id) {
		return this.generationParameterTypeDao.findById(id);
	}

	@Override
	@Transactional
	public List<GenerationParameterType> findAll() {
		return this.generationParameterTypeDao.findAll();
	}
}
