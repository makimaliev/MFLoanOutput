package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.LoanAccrueViewDao;
import kg.gov.mf.loan.output.report.model.LoanAccrueView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class LoanAccrueViewServiceJpaImpl implements LoanAccrueViewService {
	
	@Autowired
    private LoanAccrueViewDao loanAccrueViewDao;
 
    public void setLoanAccrueViewDao(LoanAccrueViewDao loanAccrueViewDao) {
        this.loanAccrueViewDao = loanAccrueViewDao;
    }
 
    

	@Override
	@Transactional	
	public void create(LoanAccrueView loanAccrueView) {
		this.loanAccrueViewDao.create(loanAccrueView);
		
	}

	@Override
	@Transactional	
	public void edit(LoanAccrueView loanAccrueView) {
		this.loanAccrueViewDao.edit(loanAccrueView);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.loanAccrueViewDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public LoanAccrueView findById(long id) {
		return this.loanAccrueViewDao.findById(id);
	}

	@Override
    @Transactional
    public List<LoanAccrueView> findAll() {
        return this.loanAccrueViewDao.findAll();
    }


	@Override
	@Transactional
	public List<LoanAccrueView> findByParameter(LinkedHashMap<String,List<String>> parameters) {
		return this.loanAccrueViewDao.findByParameter(parameters);
	}


}
