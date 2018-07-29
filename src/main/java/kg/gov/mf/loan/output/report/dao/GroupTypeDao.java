package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.GroupType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupTypeDao {

	public void create(GroupType groupType);

	public void edit(GroupType groupType);

	public void deleteById(long id);

	public GroupType findById(long id);
	
	public List<GroupType> findAll();

}
