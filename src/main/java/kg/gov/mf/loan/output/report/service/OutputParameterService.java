package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.model.OutputParameter;

import java.util.List;

public interface OutputParameterService {


	public void create(OutputParameter outputParameter);

	public void edit(OutputParameter outputParameter);

	public void deleteById(long id);

	public OutputParameter findById(long id);
	
	public List<OutputParameter> findAll();
	
}
