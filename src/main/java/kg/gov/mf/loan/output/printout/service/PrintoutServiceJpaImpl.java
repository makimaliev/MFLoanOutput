package kg.gov.mf.loan.output.printout.service;

import kg.gov.mf.loan.output.printout.dao.PrintoutDao;
import kg.gov.mf.loan.output.printout.model.Printout;
import kg.gov.mf.loan.output.printout.service.PrintoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PrintoutServiceJpaImpl implements PrintoutService {
	
	@Autowired
    private PrintoutDao printoutDao;
 
    public void setPrintoutDao(PrintoutDao printoutDao) {
        this.printoutDao = printoutDao;
    }
 
    

	@Override
	@Transactional	
	public void create(Printout printout) {
		this.printoutDao.create(printout);
		
	}

	@Override
	@Transactional	
	public void edit(Printout printout) {
		this.printoutDao.edit(printout);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.printoutDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public Printout findById(long id) {
		return this.printoutDao.findById(id);
	}

	@Override
    @Transactional
    public List<Printout> findAll() {
        return this.printoutDao.findAll();
    }
}
