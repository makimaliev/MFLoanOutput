package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.model.LoanDebtTransferView;

import java.util.LinkedHashMap;
import java.util.List;

public interface LoanDebtTransferViewService {


	public void create(LoanDebtTransferView loanDebtTransferView);

	public void edit(LoanDebtTransferView loanDebtTransferView);

	public void deleteById(long id);

	public LoanDebtTransferView findById(long id);

	public List<LoanDebtTransferView> findAll();

	public List<LoanDebtTransferView> findByParameter(LinkedHashMap<String, List<String>> parameters);

}
