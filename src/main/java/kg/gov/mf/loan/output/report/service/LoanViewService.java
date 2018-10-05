package kg.gov.mf.loan.output.report.service;

import kg.gov.mf.loan.output.report.model.LoanView;

import java.util.LinkedHashMap;
import java.util.List;

public interface LoanViewService {


	public void create(LoanView loanView);

	public void edit(LoanView loanView);

	public void deleteById(long id);

	public LoanView findById(long id);
	
	public List<LoanView> findAll();

	public List<LoanView> findByParameter(LinkedHashMap<String,List<String>> parameters);

    List<LoanView> findByParameters(LinkedHashMap<String,List<String>> parameter, Integer perPage, Integer offset);
    List<LoanView> findByParameter(LinkedHashMap<String,List<String>> parameter, Integer perPage, Integer offset,String sortStr,String sortField);
    int findByParamete(LinkedHashMap<String,List<String>> parameter);
}
