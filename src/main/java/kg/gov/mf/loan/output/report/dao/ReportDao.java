package kg.gov.mf.loan.output.report.dao;

import java.util.List;

import kg.gov.mf.loan.admin.sys.model.User;
import org.springframework.stereotype.Repository;

import kg.gov.mf.loan.output.report.model.*;

@Repository
public interface ReportDao {

	public void create(Report report);
	
	public void edit(Report report);
	
	public void deleteById(long id);
	
	public Report findById (long id);
	
	public List<Report> findAll();

	public List<Report> findByUser(User user);

	public void clone(Report report);

}
