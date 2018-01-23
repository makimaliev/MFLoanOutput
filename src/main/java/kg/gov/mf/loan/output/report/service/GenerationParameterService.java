package kg.gov.mf.loan.output.report.service;

import java.util.List;

import kg.gov.mf.loan.output.report.model.*;

public interface GenerationParameterService {

	
	public void create(GenerationParameter generationParameter);
	
	public void edit(GenerationParameter generationParameter);
	
	public void deleteById(long id);
	
	public GenerationParameter findById (long id);
	
	public List<GenerationParameter> findAll();	
	
}
