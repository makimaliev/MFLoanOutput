package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.model.GroupType;
import kg.gov.mf.loan.output.report.model.ObjectList;

import java.util.List;

public interface ObjectListService {


	public void create(ObjectList objectList);

	public void edit(ObjectList objectList);

	public void deleteById(long id);

	public ObjectList findById(long id);
	
	public List<ObjectList> findAll();

	public List<ObjectList> findAllByGroupType(GroupType groupType);
	
}
