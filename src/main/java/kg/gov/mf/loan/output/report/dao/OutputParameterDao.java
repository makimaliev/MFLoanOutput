package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.OutputParameter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutputParameterDao {

	public void create(OutputParameter outputParameter);

	public void edit(OutputParameter outputParameter);

	public void deleteById(long id);

	public OutputParameter findById(long id);
	
	public List<OutputParameter> findAll();

}
