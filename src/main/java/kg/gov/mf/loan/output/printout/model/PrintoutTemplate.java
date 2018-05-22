package kg.gov.mf.loan.output.printout.model;

import kg.gov.mf.loan.output.report.model.ContentParameter;
import kg.gov.mf.loan.output.report.model.FilterParameter;
import kg.gov.mf.loan.output.report.model.GenerationParameter;
import kg.gov.mf.loan.output.report.model.Report;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="printout_template")

public class PrintoutTemplate {
 
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
 
    @Column(name="name", nullable=false)
    private String name;
    
    @ManyToOne(targetEntity=Printout.class, fetch = FetchType.EAGER)
    @JoinColumn(name="printout_id")
    Printout printout;
    
//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(
//			name="report_template_generation_parameter",
//			joinColumns = { @JoinColumn(name = "report_template_id") },
//			inverseJoinColumns = { @JoinColumn(name = "generation_parameter_id") })
//	Set<GenerationParameter> generationParameters = new HashSet<GenerationParameter>();
//
//
//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(
//			name="report_template_filter_parameter",
//			joinColumns = { @JoinColumn(name = "report_template_id") },
//			inverseJoinColumns = { @JoinColumn(name = "filter_parameter_id") })
//	Set<FilterParameter> filterParameters = new HashSet<FilterParameter>();
//
//
//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(
//			name="report_template_content_parameter",
//			joinColumns = { @JoinColumn(name = "report_template_id") },
//			inverseJoinColumns = { @JoinColumn(name = "content_parameter_id") })
//	Set<ContentParameter> contentParameters = new HashSet<ContentParameter>();


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

	public Printout getPrintout() {
		return printout;
	}

	public void setPrintout(Printout printout) {
		this.printout = printout;
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
		final PrintoutTemplate other = (PrintoutTemplate) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}


}
