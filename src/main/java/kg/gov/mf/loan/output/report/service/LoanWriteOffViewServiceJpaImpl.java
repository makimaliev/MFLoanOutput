package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.LoanWriteOffViewDao;
import kg.gov.mf.loan.output.report.model.LoanWriteOffView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class LoanWriteOffViewServiceJpaImpl implements LoanWriteOffViewService {
	
	@Autowired
    private LoanWriteOffViewDao loanWriteOffViewDao;
 
    public void setLoanWriteOffViewDao(LoanWriteOffViewDao loanWriteOffViewDao) {
        this.loanWriteOffViewDao = loanWriteOffViewDao;
    }
 
    

	@Override
	@Transactional	
	public void create(LoanWriteOffView paymentView) {
		this.loanWriteOffViewDao.create(paymentView);
		
	}

	@Override
	@Transactional	
	public void edit(LoanWriteOffView paymentView) {
		this.loanWriteOffViewDao.edit(paymentView);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.loanWriteOffViewDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public LoanWriteOffView findById(long id) {
		return this.loanWriteOffViewDao.findById(id);
	}

	@Override
    @Transactional
    public List<LoanWriteOffView> findAll() {
        return this.loanWriteOffViewDao.findAll();
    }


	@Override
	@Transactional
	public List<LoanWriteOffView> findByParameter(LinkedHashMap<String,List<String>> parameters) {
		return this.loanWriteOffViewDao.findByParameter(parameters);
	}


}
