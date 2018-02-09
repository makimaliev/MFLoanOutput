package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.GenerationParameterType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenerationParameterTypeDao {

	public void create(GenerationParameterType generationParameterType);

	public void edit(GenerationParameterType generationParameterType);

	public void deleteById(long id);

	public GenerationParameterType findById(long id);
	
	public List<GenerationParameterType> findAll();

}
