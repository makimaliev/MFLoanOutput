package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.ObjectListValue;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectListValueDao {

	public void create(ObjectListValue objectListValue);

	public void edit(ObjectListValue objectListValue);

	public void deleteById(long id);

	public ObjectListValue findById(long id);
	
	public List<ObjectListValue> findAll();

}
