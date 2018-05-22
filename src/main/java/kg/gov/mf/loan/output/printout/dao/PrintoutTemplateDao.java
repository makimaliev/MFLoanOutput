package kg.gov.mf.loan.output.printout.dao;

import kg.gov.mf.loan.output.printout.model.PrintoutTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrintoutTemplateDao {

	public void create(PrintoutTemplate printoutTemplate);

	public void edit(PrintoutTemplate printoutTemplate);

	public void deleteById(long id);

	public PrintoutTemplate findById(long id);
	
	public List<PrintoutTemplate> findAll();

}
