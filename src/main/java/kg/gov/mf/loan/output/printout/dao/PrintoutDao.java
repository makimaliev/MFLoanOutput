package kg.gov.mf.loan.output.printout.dao;

import kg.gov.mf.loan.output.printout.model.Printout;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrintoutDao {

	public void create(Printout printout);

	public void edit(Printout printout);

	public void deleteById(long id);

	public Printout findById(long id);
	
	public List<Printout> findAll();

}
