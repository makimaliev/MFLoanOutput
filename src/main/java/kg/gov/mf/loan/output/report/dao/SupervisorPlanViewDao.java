package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.SupervisorPlanView;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface SupervisorPlanViewDao {

	public void create(SupervisorPlanView supervisorPlanView);

	public void edit(SupervisorPlanView supervisorPlanView);

	public void deleteById(long id);

	public SupervisorPlanView findById(long id);

	public List<SupervisorPlanView> findAll();

	public List<SupervisorPlanView> findByParameter(LinkedHashMap<String, List<String>> parameters);



}
