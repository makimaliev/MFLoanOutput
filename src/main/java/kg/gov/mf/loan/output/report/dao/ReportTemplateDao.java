package kg.gov.mf.loan.output.report.dao;

import java.util.List;

import kg.gov.mf.loan.admin.sys.model.User;
import org.springframework.stereotype.Repository;

import kg.gov.mf.loan.output.report.model.*;

@Repository
public interface ReportTemplateDao {

	public void create(ReportTemplate reportTemplate);
	
	public void edit(ReportTemplate reportTemplate);
	
	public void deleteById(long id);
	
	public ReportTemplate findById (long id);
	
	public List<ReportTemplate> findAll();

	public List<ReportTemplate> findByUser(User user);

	public void clone(ReportTemplate reportTemplate);

	public ReportTemplate findByReportId(Long reportId);

}
