package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.CollateralArrestFreeView;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface CollateralArrestFreeViewDao {

	public void create(CollateralArrestFreeView collateralArrestFreeView);

	public void edit(CollateralArrestFreeView collateralArrestFreeView);

	public void deleteById(long id);

	public CollateralArrestFreeView findById(long id);

	public List<CollateralArrestFreeView> findAll();

	public List<CollateralArrestFreeView> findByParameter(LinkedHashMap<String, List<String>> parameters);



}
