package kg.gov.mf.loan.output.report.service;

import java.util.List;

import kg.gov.mf.loan.admin.sys.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kg.gov.mf.loan.output.report.dao.*;
import kg.gov.mf.loan.output.report.model.*;

@Service
public class ReportServiceJpaImpl implements ReportService {
	
	@Autowired
    private ReportDao reportDao;
 
    public void setReportDao(ReportDao reportDao) {
        this.reportDao = reportDao;
    }
 
    

	@Override
	@Transactional	
	public void create(Report report) {
		this.reportDao.create(report);
		
	}

	@Override
	@Transactional	
	public void edit(Report report) {
		this.reportDao.edit(report);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.reportDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public Report findById(long id) {
		return this.reportDao.findById(id);
	}

	@Override
    @Transactional
    public List<Report> findAll() {
        return this.reportDao.findAll();
    }

	@Override
	@Transactional
	public List<Report> findByUser(User user) {
		return this.reportDao.findByUser(user);
	}
}
