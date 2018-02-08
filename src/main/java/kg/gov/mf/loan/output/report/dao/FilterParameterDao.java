package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.FilterParameter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilterParameterDao {

	public void create(FilterParameter filterParameter);

	public void edit(FilterParameter filterParameter);

	public void deleteById(long id);

	public FilterParameter findById(long id);
	
	public List<FilterParameter> findAll();

}
