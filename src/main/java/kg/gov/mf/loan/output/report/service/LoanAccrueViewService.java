package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.model.LoanAccrueView;

import java.util.LinkedHashMap;
import java.util.List;

public interface LoanAccrueViewService {


	public void create(LoanAccrueView loanAccrueView);

	public void edit(LoanAccrueView loanAccrueView);

	public void deleteById(long id);

	public LoanAccrueView findById(long id);

	public List<LoanAccrueView> findAll();

	public List<LoanAccrueView> findByParameter(LinkedHashMap<String, List<String>> parameters);

}
