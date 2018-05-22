package kg.gov.mf.loan.output.printout.model;

import kg.gov.mf.loan.output.report.model.ReportTemplate;
import kg.gov.mf.loan.output.report.model.ReportType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="printout")

public class Printout {
 
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
 
    @Column(name="name", nullable=false)
    private String name;
    
    @OneToMany(mappedBy = "printout", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    private Set<PrintoutTemplate> printoutTemplates = new HashSet<PrintoutTemplate>();


	@Enumerated(EnumType.STRING)
	private PrintoutType printoutType;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<PrintoutTemplate> getPrintoutTemplates() {
		return printoutTemplates;
	}

	public void setPrintoutTemplates(Set<PrintoutTemplate> printoutTemplates) {
		this.printoutTemplates = printoutTemplates;
	}

	public PrintoutType getPrintoutType() {
		return printoutType;
	}

	public void setPrintoutType(PrintoutType printoutType) {
		this.printoutType = printoutType;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 83 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Printout other = (Printout) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
	
    
    
}
