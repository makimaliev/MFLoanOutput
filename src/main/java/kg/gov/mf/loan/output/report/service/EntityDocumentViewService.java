package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.model.EntityDocumentView;

import java.util.LinkedHashMap;
import java.util.List;

public interface EntityDocumentViewService {


	public void create(EntityDocumentView entityDocumentView);

	public void edit(EntityDocumentView entityDocumentView);

	public void deleteById(long id);

	public EntityDocumentView findById(long id);

	public List<EntityDocumentView> findAll();

	public List<EntityDocumentView> findByParameter(LinkedHashMap<String, List<Long>> parameters);

}
