package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.output.report.model.FilterParameter;

import java.util.List;

public interface FilterParameterService {


	public void create(FilterParameter filterParameter);

	public void edit(FilterParameter filterParameter);

	public void deleteById(long id);

	public FilterParameter findById(long id);
	
	public List<FilterParameter> findAll();

	public List<FilterParameter> findByUser(User user);

	
}
