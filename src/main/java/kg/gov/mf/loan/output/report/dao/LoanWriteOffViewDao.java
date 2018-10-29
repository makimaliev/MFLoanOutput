package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.LoanWriteOffView;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface LoanWriteOffViewDao {

	public void create(LoanWriteOffView loanWriteOffView);

	public void edit(LoanWriteOffView loanWriteOffView);

	public void deleteById(long id);

	public LoanWriteOffView findById(long id);

	public List<LoanWriteOffView> findAll();

	public List<LoanWriteOffView> findByParameter(LinkedHashMap<String, List<String>> parameters);



}
