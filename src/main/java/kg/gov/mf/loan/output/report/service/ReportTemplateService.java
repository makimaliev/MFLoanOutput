package kg.gov.mf.loan.output.report.service;

import java.util.List;

import kg.gov.mf.loan.output.report.model.*;

public interface ReportTemplateService {

	
	public void create(ReportTemplate reportTemplate);
	
	public void edit(ReportTemplate reportTemplate);
	
	public void deleteById(long id);
	
	public ReportTemplate findById (long id);
	
	public List<ReportTemplate> findAll();	
	
}
