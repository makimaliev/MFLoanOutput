package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.ObjectListDao;
import kg.gov.mf.loan.output.report.model.GroupType;
import kg.gov.mf.loan.output.report.model.ObjectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ObjectListServiceJpaImpl implements ObjectListService {

	@Autowired
	private ObjectListDao objectListDao;

	public void setObjectListDao(ObjectListDao objectListDao) {
		this.objectListDao = objectListDao;
	}



	@Override
	@Transactional
	public void create(ObjectList objectList) {
		this.objectListDao.create(objectList);

	}

	@Override
	@Transactional
	public void edit(ObjectList objectList) {
		this.objectListDao.edit(objectList);

	}

	@Override
	@Transactional
	public void deleteById(long id) {
		this.objectListDao.deleteById(id);

	}

	@Override
	@Transactional
	public ObjectList findById(long id) {
		return this.objectListDao.findById(id);
	}

	@Override
	@Transactional
	public List<ObjectList> findAll() {
		return this.objectListDao.findAll();
	}

	@Override
	@Transactional
	public List<ObjectList> findAllByGroupType(GroupType groupType) {
		return this.objectListDao.findAllByGroupType(groupType);
	}

	@Override
	@Transactional
	public List<ObjectList> findAllByGroupTypeAndUser(GroupType groupType, Long userId) {
		return this.objectListDao.findAllByGroupTypeAndUser(groupType,userId);
	}
}
