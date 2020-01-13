package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.model.CollectionPhaseView;

import java.util.LinkedHashMap;
import java.util.List;

public interface CollectionPhaseViewService {


	public void create(CollectionPhaseView collectionPhaseView);

	public void edit(CollectionPhaseView collectionPhaseView);

	public void deleteById(long id);

	public CollectionPhaseView findById(long id);

	public List<CollectionPhaseView> findAll();

	public List<CollectionPhaseView> findByParameter(LinkedHashMap<String, List<String>> parameters);

	public List<CollectionPhaseView> findByParameter(LinkedHashMap<String, List<String>> parameters, Integer offset, Integer limit, String sortStr, String sortField);
}
