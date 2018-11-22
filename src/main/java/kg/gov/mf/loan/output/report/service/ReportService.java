package kg.gov.mf.loan.output.report.service;

import java.util.List;

import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.output.report.model.*;

public interface ReportService {

	
	public void create(Report report);
	
	public void edit(Report report);
	
	public void deleteById(long id);
	
	public Report findById (long id);
	
	public List<Report> findAll();

	public List<Report> findByUser(User user);

}
