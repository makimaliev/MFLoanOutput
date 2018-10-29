package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.LoanDebtTransferView;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface LoanDebtTransferViewDao {

	public void create(LoanDebtTransferView loanDebtTransferView);

	public void edit(LoanDebtTransferView loanDebtTransferView);

	public void deleteById(long id);

	public LoanDebtTransferView findById(long id);

	public List<LoanDebtTransferView> findAll();

	public List<LoanDebtTransferView> findByParameter(LinkedHashMap<String, List<String>> parameters);



}
