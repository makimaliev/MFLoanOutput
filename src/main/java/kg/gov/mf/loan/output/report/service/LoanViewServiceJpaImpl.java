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
	public List<LoanView> findByParameter(LinkedHashMap<String,List<String>> parameters) {
		return this.loanViewDao.findByParameter(parameters);
	}

	@Override
	@Transactional
	public List<LoanView> findByParameters(LinkedHashMap<String, List<String>> parameter, Integer perPage, Integer offset) {
		return this.loanViewDao.findByParameters(parameter,perPage,offset);
	}

	@Override
	@Transactional
	public List<LoanView> findByParameter(LinkedHashMap<String, List<String>> parameter, Integer perPage, Integer offset, String sortStr, String sortField) {
		return this.loanViewDao.findByParameter(parameter,perPage,offset,sortStr,sortField);
	}

	@Override
    @Transactional
    public int findByParamete(LinkedHashMap<String, List<String>> parameter) {
        return this.loanViewDao.findByParamete(parameter);
    }


}
