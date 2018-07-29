package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.GroupTypeDao;
import kg.gov.mf.loan.output.report.model.GroupType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GroupTypeServiceJpaImpl implements GroupTypeService {

	@Autowired
	private GroupTypeDao groupTypeDao;

	public void setGroupTypeDao(GroupTypeDao groupTypeDao) {
		this.groupTypeDao = groupTypeDao;
	}



	@Override
	@Transactional
	public void create(GroupType groupType) {
		this.groupTypeDao.create(groupType);

	}

	@Override
	@Transactional
	public void edit(GroupType groupType) {
		this.groupTypeDao.edit(groupType);

	}

	@Override
	@Transactional
	public void deleteById(long id) {
		this.groupTypeDao.deleteById(id);

	}

	@Override
	@Transactional
	public GroupType findById(long id) {
		return this.groupTypeDao.findById(id);
	}

	@Override
	@Transactional
	public List<GroupType> findAll() {
		return this.groupTypeDao.findAll();
	}
}
