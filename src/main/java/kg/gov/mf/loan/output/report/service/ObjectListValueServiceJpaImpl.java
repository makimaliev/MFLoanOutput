package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.ObjectListValueDao;
import kg.gov.mf.loan.output.report.dao.GenerationParameterDao;
import kg.gov.mf.loan.output.report.model.ObjectListValue;
import kg.gov.mf.loan.output.report.model.GenerationParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ObjectListValueServiceJpaImpl implements ObjectListValueService {

	@Autowired
	private ObjectListValueDao objectListValueDao;

	public void setObjectListValueDao(ObjectListValueDao objectListValueDao) {
		this.objectListValueDao = objectListValueDao;
	}



	@Override
	@Transactional
	public void create(ObjectListValue objectListValue) {
		this.objectListValueDao.create(objectListValue);

	}

	@Override
	@Transactional
	public void edit(ObjectListValue objectListValue) {
		this.objectListValueDao.edit(objectListValue);

	}

	@Override
	@Transactional
	public void deleteById(long id) {
		this.objectListValueDao.deleteById(id);

	}

	@Override
	@Transactional
	public ObjectListValue findById(long id) {
		return this.objectListValueDao.findById(id);
	}

	@Override
	@Transactional
	public List<ObjectListValue> findAll() {
		return this.objectListValueDao.findAll();
	}
}
