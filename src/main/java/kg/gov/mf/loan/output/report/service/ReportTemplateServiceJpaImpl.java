package kg.gov.mf.loan.output.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kg.gov.mf.loan.output.report.dao.*;
import kg.gov.mf.loan.output.report.model.*;

@Service
public class ReportTemplateServiceJpaImpl implements ReportTemplateService {
	
	@Autowired
    private ReportTemplateDao reportTemplateDao;
 
    public void setReportTemplateDao(ReportTemplateDao reportTemplateDao) {
        this.reportTemplateDao = reportTemplateDao;
    }
 
    

	@Override
	@Transactional	
	public void create(ReportTemplate reportTemplate) {
		this.reportTemplateDao.create(reportTemplate);
		
	}

	@Override
	@Transactional	
	public void edit(ReportTemplate reportTemplate) {
		this.reportTemplateDao.edit(reportTemplate);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.reportTemplateDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public ReportTemplate findById(long id) {
		return this.reportTemplateDao.findById(id);
	}

	@Override
    @Transactional
    public List<ReportTemplate> findAll() {
        return this.reportTemplateDao.findAll();
    }


	@Override
	@Transactional
	public void clone(ReportTemplate reportTemplate) {
		this.reportTemplateDao.clone(reportTemplate);

	}
}
