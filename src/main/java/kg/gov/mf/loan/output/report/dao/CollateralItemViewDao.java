package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.CollateralItemView;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface CollateralItemViewDao {

	public void create(CollateralItemView collateralItemView);

	public void edit(CollateralItemView collateralItemView);

	public void deleteById(long id);

	public CollateralItemView findById(long id);

	public List<CollateralItemView> findAll();

	public List<CollateralItemView> findByParameter(LinkedHashMap<String, List<String>> parameters);

	public List<CollateralItemView> findByParameter(LinkedHashMap<String, List<String>> parameters,Integer offset,Integer limit,String sortStr,String sortField);

	public Long getCount(LinkedHashMap<String, List<String>> parameters);



}
