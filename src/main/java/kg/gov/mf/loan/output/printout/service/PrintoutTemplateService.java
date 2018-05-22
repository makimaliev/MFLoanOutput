package kg.gov.mf.loan.output.printout.service;

import kg.gov.mf.loan.output.printout.model.PrintoutTemplate;

import java.util.List;

public interface PrintoutTemplateService {


	public void create(PrintoutTemplate printoutTemplate);

	public void edit(PrintoutTemplate printoutTemplate);

	public void deleteById(long id);

	public PrintoutTemplate findById(long id);
	
	public List<PrintoutTemplate> findAll();
	
}
