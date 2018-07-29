package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.model.GroupType;

import java.util.List;

public interface GroupTypeService {


	public void create(GroupType groupType);

	public void edit(GroupType groupType);

	public void deleteById(long id);

	public GroupType findById(long id);
	
	public List<GroupType> findAll();
	
}
