package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.LoanSummaryViewDao;
import kg.gov.mf.loan.output.report.model.LoanSummaryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class LoanSummaryViewServiceJpaImpl implements LoanSummaryViewService {
	
	@Autowired
    private LoanSummaryViewDao loanSummaryViewDao;
 
    public void setLoanSummaryViewDao(LoanSummaryViewDao loanSummaryViewDao) {
        this.loanSummaryViewDao = loanSummaryViewDao;
    }
 
    

	@Override
	@Transactional	
	public void create(LoanSummaryView loanSummaryView) {
		this.loanSummaryViewDao.create(loanSummaryView);
		
	}

	@Override
	@Transactional	
	public void edit(LoanSummaryView loanSummaryView) {
		this.loanSummaryViewDao.edit(loanSummaryView);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.loanSummaryViewDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public LoanSummaryView findById(long id) {
		return this.loanSummaryViewDao.findById(id);
	}

	@Override
    @Transactional
    public List<LoanSummaryView> findAll() {
        return this.loanSummaryViewDao.findAll();
    }


	@Override
	@Transactional
	public List<LoanSummaryView> findByParameter(LinkedHashMap<String,List<String>> parameters) {
		return this.loanSummaryViewDao.findByParameter(parameters);
	}


}
