package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.model.CollateralInspectionView;

import java.util.LinkedHashMap;
import java.util.List;

public interface CollateralInspectionViewService {


	public void create(CollateralInspectionView collateralInspectionView);

	public void edit(CollateralInspectionView collateralInspectionView);

	public void deleteById(long id);

	public CollateralInspectionView findById(long id);

	public List<CollateralInspectionView> findAll();

	public List<CollateralInspectionView> findByParameter(LinkedHashMap<String, List<String>> parameters);

	public List<CollateralInspectionView> findByParameter(LinkedHashMap<String, List<String>> parameters,Integer offset,Integer limit);

}
