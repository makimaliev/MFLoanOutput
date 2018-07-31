package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.EntityDocumentView;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface EntityDocumentViewDao {

	public void create(EntityDocumentView entityDocumentView);

	public void edit(EntityDocumentView entityDocumentView);

	public void deleteById(long id);

	public EntityDocumentView findById(long id);

	public List<EntityDocumentView> findAll();

	public List<EntityDocumentView> findByParameter(LinkedHashMap<String, List<String>> parameters);



}
