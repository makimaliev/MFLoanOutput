package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.model.CollateralArrestFreeView;

import java.util.LinkedHashMap;
import java.util.List;

public interface CollateralArrestFreeViewService {


	public void create(CollateralArrestFreeView collateralArrestFreeView);

	public void edit(CollateralArrestFreeView collateralArrestFreeView);

	public void deleteById(long id);

	public CollateralArrestFreeView findById(long id);

	public List<CollateralArrestFreeView> findAll();

	public List<CollateralArrestFreeView> findByParameter(LinkedHashMap<String, List<String>> parameters);

	public List<CollateralArrestFreeView> findByParameter(LinkedHashMap<String, List<String>> parameters,Integer offset,Integer limit);

}
