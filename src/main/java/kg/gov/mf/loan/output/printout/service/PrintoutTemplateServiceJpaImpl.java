package kg.gov.mf.loan.output.printout.service;

import kg.gov.mf.loan.output.printout.dao.PrintoutTemplateDao;
import kg.gov.mf.loan.output.printout.model.PrintoutTemplate;
import kg.gov.mf.loan.output.printout.service.PrintoutTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PrintoutTemplateServiceJpaImpl implements PrintoutTemplateService {
	
	@Autowired
    private PrintoutTemplateDao printoutTemplateDao;
 
    public void setPrintoutTemplateDao(PrintoutTemplateDao printoutTemplateDao) {
        this.printoutTemplateDao = printoutTemplateDao;
    }
 
    

	@Override
	@Transactional	
	public void create(PrintoutTemplate printoutTemplate) {
		this.printoutTemplateDao.create(printoutTemplate);
		
	}

	@Override
	@Transactional	
	public void edit(PrintoutTemplate printoutTemplate) {
		this.printoutTemplateDao.edit(printoutTemplate);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.printoutTemplateDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public PrintoutTemplate findById(long id) {
		return this.printoutTemplateDao.findById(id);
	}

	@Override
    @Transactional
    public List<PrintoutTemplate> findAll() {
        return this.printoutTemplateDao.findAll();
    }
}
