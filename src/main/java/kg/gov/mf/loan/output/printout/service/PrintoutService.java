package kg.gov.mf.loan.output.printout.service;

import kg.gov.mf.loan.output.printout.model.Printout;

import java.util.List;

public interface PrintoutService {


	public void create(Printout printout);

	public void edit(Printout printout);

	public void deleteById(long id);

	public Printout findById(long id);
	
	public List<Printout> findAll();
	
}
