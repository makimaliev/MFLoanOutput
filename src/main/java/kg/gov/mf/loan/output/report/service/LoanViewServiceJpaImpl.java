package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.LoanViewDao;
import kg.gov.mf.loan.output.report.model.LoanView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class LoanViewServiceJpaImpl implements LoanViewService {
	
	@Autowired
    private LoanViewDao loanViewDao;
 
    public void setLoanViewDao(LoanViewDao loanViewDao) {
        this.loanViewDao = loanViewDao;
    }
 
    

	@Override
	@Transactional	
	public void create(LoanView loanView) {
		this.loanViewDao.create(loanView);
		
	}

	@Override
	@Transactional	
	public void edit(LoanView loanView) {
		this.loanViewDao.edit(loanView);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.loanViewDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public LoanView findById(long id) {
		return this.loanViewDao.findById(id);
	}

	@Override
    @Transactional
    public List<LoanView> findAll() {
        return this.loanViewDao.findAll();
    }


	@Override
	@Transactional
	public List<LoanView> findByParameter(LinkedHashMap<String,List<Long>> parameters) {
		return this.loanViewDao.findByParameter(parameters);
	}


}