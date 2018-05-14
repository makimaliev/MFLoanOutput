package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.output.report.model.LoanSummaryView;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface LoanSummaryViewDao {

	public void create(LoanSummaryView loanSummaryView);

	public void edit(LoanSummaryView loanSummaryView);

	public void deleteById(long id);

	public LoanSummaryView findById(long id);

	public List<LoanSummaryView> findAll();

	public List<LoanSummaryView> findByParameter(LinkedHashMap<String, List<Long>> parameters);



}
