package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.ReferenceView;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface ReferenceViewDao {

	public void create(ReferenceView referenceView);

	public void edit(ReferenceView referenceView);

	public void deleteById(long id);

	public ReferenceView findById(long id);

	public List<ReferenceView> findAll();

	public List<ReferenceView> findByParameter(String list_type);



}
