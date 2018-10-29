package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.CollateralInspectionView;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface CollateralInspectionViewDao {

	public void create(CollateralInspectionView collateralInspectionView);

	public void edit(CollateralInspectionView collateralInspectionView);

	public void deleteById(long id);

	public CollateralInspectionView findById(long id);

	public List<CollateralInspectionView> findAll();

	public List<CollateralInspectionView> findByParameter(LinkedHashMap<String, List<String>> parameters);



}
