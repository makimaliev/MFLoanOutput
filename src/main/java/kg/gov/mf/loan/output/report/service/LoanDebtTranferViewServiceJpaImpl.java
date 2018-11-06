package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.dao.LoanDebtTransferViewDao;
import kg.gov.mf.loan.output.report.model.LoanDebtTransferView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class LoanDebtTranferViewServiceJpaImpl implements LoanDebtTransferViewService {
	
	@Autowired
    private LoanDebtTransferViewDao loanDebtTransferViewDao;
 
    public void setLoanDebtTransferViewDao(LoanDebtTransferViewDao loanDebtTransferViewDao) {
        this.loanDebtTransferViewDao = loanDebtTransferViewDao;
    }
 
    

	@Override
	@Transactional	
	public void create(LoanDebtTransferView loanDebtTransferView) {
		this.loanDebtTransferViewDao.create(loanDebtTransferView);
		
	}

	@Override
	@Transactional	
	public void edit(LoanDebtTransferView loanDebtTransferView) {
		this.loanDebtTransferViewDao.edit(loanDebtTransferView);
		
	}

	@Override
	@Transactional	
	public void deleteById(long id) {
		this.loanDebtTransferViewDao.deleteById(id);
		
	}

	@Override
	@Transactional	
	public LoanDebtTransferView findById(long id) {
		return this.loanDebtTransferViewDao.findById(id);
	}

	@Override
    @Transactional
    public List<LoanDebtTransferView> findAll() {
        return this.loanDebtTransferViewDao.findAll();
    }


	@Override
	@Transactional
	public List<LoanDebtTransferView> findByParameter(LinkedHashMap<String,List<String>> parameters) {
		return this.loanDebtTransferViewDao.findByParameter(parameters);
	}

	@Override
	@Transactional
	public List<LoanDebtTransferView> findByParameter(LinkedHashMap<String, List<String>> parameters, Integer offset, Integer limit) {
		return this.loanDebtTransferViewDao.findByParameter(parameters,offset,limit);
	}


}
