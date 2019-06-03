package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.CollectionPhaseView;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

@Repository
public interface CollectionPhaseViewDao {

	public void create(CollectionPhaseView collectionPhaseView);

	public void edit(CollectionPhaseView collectionPhaseView);

	public void deleteById(long id);

	public CollectionPhaseView findById(long id);

	public List<CollectionPhaseView> findAll();

	public List<CollectionPhaseView> findByParameter(LinkedHashMap<String, List<String>> parameters);

	public Set<CollectionPhaseView> findByParameter(LinkedHashMap<String, List<String>> parameters, Integer offset, Integer limit, String sortStr, String sortField);



}
