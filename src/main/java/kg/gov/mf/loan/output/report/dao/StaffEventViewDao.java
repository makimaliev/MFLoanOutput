package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.StaffEventView;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface StaffEventViewDao {

	public void create(StaffEventView staffEventView);

	public void edit(StaffEventView staffEventView);

	public void deleteById(long id);

	public StaffEventView findById(long id);

	public List<StaffEventView> findAll();

	public List<StaffEventView> findByParameter(LinkedHashMap<String, List<String>> parameters);
	public List<StaffEventView> findByParameter(LinkedHashMap<String, List<String>> parameters, Integer offset, Integer limit, String sortStr, String sortField);
	public Long getCount(LinkedHashMap<String, List<String>> parameters);



}
