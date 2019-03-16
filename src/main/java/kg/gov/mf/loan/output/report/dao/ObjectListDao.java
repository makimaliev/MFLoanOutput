package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.GroupType;
import kg.gov.mf.loan.output.report.model.ObjectList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectListDao {

	public void create(ObjectList objectList);

	public void edit(ObjectList objectList);

	public void deleteById(long id);

	public ObjectList findById(long id);
	
	public List<ObjectList> findAll();

	public List<ObjectList> findAllByGroupType(GroupType groupType);

	public List<ObjectList> findAllByGroupTypeAndUser(GroupType groupType,Long userId);

}
