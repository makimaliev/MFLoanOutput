package kg.gov.mf.loan.output.report.dao;

import kg.gov.mf.loan.admin.org.model.Region;
import kg.gov.mf.loan.output.report.model.LoanView;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface LoanViewDao {

	public void create(LoanView loanView);

	public void edit(LoanView loanView);

	public void deleteById(long id);

	public LoanView findById(long id);
	
	public List<LoanView> findAll();

	public List<LoanView> findByParameter(LinkedHashMap<String,List<String>> parameters);


    List<LoanView> findByParameters(LinkedHashMap<String,List<String>> parameter, Integer perPage, Integer offset);
    List<LoanView> findByParameter(LinkedHashMap<String,List<String>> parameter,Integer perPage,Integer offset,String sortStr,String sortField);
    public Long getCount(LinkedHashMap<String,List<String>> parameter);
}
