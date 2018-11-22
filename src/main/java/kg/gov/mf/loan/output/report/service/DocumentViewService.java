package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.model.DocumentView;

import java.util.LinkedHashMap;
import java.util.List;

public interface DocumentViewService {


	public void create(DocumentView documentView);

	public void edit(DocumentView documentView);

	public void deleteById(long id);

	public DocumentView findById(long id);

	public List<DocumentView> findAll();

	public List<DocumentView> findByParameter(LinkedHashMap<String, List<String>> parameters);

	public List<DocumentView> findByParameter(LinkedHashMap<String, List<String>> parameters, Integer offset, Integer limit, String sortStr, String sortField);

	public Long getCount(LinkedHashMap<String, List<String>> parameters);

}
