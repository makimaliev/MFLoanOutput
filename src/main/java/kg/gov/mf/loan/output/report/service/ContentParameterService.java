package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.model.ContentParameter;

import java.util.List;

public interface ContentParameterService {


	public void create(ContentParameter contentParameter);

	public void edit(ContentParameter contentParameter);

	public void deleteById(long id);

	public ContentParameter findById(long id);
	
	public List<ContentParameter> findAll();
	
}
