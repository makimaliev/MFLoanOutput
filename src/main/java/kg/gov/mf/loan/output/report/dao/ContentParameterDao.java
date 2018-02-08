package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.ContentParameter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentParameterDao {

	public void create(ContentParameter contentParameter);

	public void edit(ContentParameter contentParameter);

	public void deleteById(long id);

	public ContentParameter findById(long id);
	
	public List<ContentParameter> findAll();

}
