package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.model.ObjectListValue;

import java.util.List;

public interface ObjectListValueService {


	public void create(ObjectListValue objectListValue);

	public void edit(ObjectListValue objectListValue);

	public void deleteById(long id);

	public ObjectListValue findById(long id);
	
	public List<ObjectListValue> findAll();
	
}
