package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.model.LoanSummaryView;

import java.util.LinkedHashMap;
import java.util.List;

public interface LoanSummaryViewService {


	public void create(LoanSummaryView loanSummaryView);

	public void edit(LoanSummaryView loanSummaryView);

	public void deleteById(long id);

	public LoanSummaryView findById(long id);

	public List<LoanSummaryView> findAll();

	public List<LoanSummaryView> findByParameter(LinkedHashMap<String, List<String>> parameters);

}
