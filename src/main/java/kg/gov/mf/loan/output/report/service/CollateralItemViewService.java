package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.model.CollateralItemView;

import java.util.LinkedHashMap;
import java.util.List;

public interface CollateralItemViewService {


	public void create(CollateralItemView collateralItemView);

	public void edit(CollateralItemView collateralItemView);

	public void deleteById(long id);

	public CollateralItemView findById(long id);

	public List<CollateralItemView> findAll();

	public List<CollateralItemView> findByParameter(LinkedHashMap<String, List<String>> parameters);

}
