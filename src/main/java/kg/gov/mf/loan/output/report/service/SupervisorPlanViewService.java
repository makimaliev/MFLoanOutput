package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.model.SupervisorPlanView;

import java.util.LinkedHashMap;
import java.util.List;

public interface SupervisorPlanViewService {


	public void create(SupervisorPlanView supervisorPlanView);

	public void edit(SupervisorPlanView supervisorPlanView);

	public void deleteById(long id);

	public SupervisorPlanView findById(long id);

	public List<SupervisorPlanView> findAll();

	public List<SupervisorPlanView> findByParameter(LinkedHashMap<String, List<String>> parameters);

	public List<SupervisorPlanView> findByParameter(LinkedHashMap<String, List<String>> parameters,Integer offset,Integer limit);

}
