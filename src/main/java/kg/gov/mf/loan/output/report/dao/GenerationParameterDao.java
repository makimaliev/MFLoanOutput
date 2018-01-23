package kg.gov.mf.loan.output.report.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kg.gov.mf.loan.output.report.model.*;

@Repository
public interface GenerationParameterDao {

	public void create(GenerationParameter generationParameter);
	
	public void edit(GenerationParameter generationParameter);
	
	public void deleteById(long id);
	
	public GenerationParameter findById (long id);
	
	public List<GenerationParameter> findAll();

}
