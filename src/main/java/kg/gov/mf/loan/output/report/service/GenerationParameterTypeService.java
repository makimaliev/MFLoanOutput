package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.model.GenerationParameterType;

import java.util.List;

public interface GenerationParameterTypeService {


	public void create(GenerationParameterType generationParameterType);

	public void edit(GenerationParameterType generationParameterType);

	public void deleteById(long id);

	public GenerationParameterType findById(long id);
	
	public List<GenerationParameterType> findAll();
	
}
