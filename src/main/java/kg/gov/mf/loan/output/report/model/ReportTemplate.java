package kg.gov.mf.loan.output.report.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import kg.gov.mf.loan.output.report.model.*;

@Entity
@Table(name="report_template")

public class ReportTemplate {
 
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
 
    @Column(name="name", nullable=false)
    private String name;
    
    @ManyToOne(targetEntity=Report.class, fetch = FetchType.EAGER)
    @JoinColumn(name="report_id")
    Report report;   
    
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="report_template_generation_parameter",
			joinColumns = { @JoinColumn(name = "report_template_id") },
			inverseJoinColumns = { @JoinColumn(name = "generation_parameter_id") })
	Set<GenerationParameter> generationParameters = new HashSet<GenerationParameter>();


	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="report_template_filter_parameter",
			joinColumns = { @JoinColumn(name = "report_template_id") },
			inverseJoinColumns = { @JoinColumn(name = "filter_parameter_id") })
	Set<FilterParameter> filterParameters = new HashSet<FilterParameter>();


	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="report_template_content_parameter",
			joinColumns = { @JoinColumn(name = "report_template_id") },
			inverseJoinColumns = { @JoinColumn(name = "content_parameter_id") })
	Set<ContentParameter> contentParameters = new HashSet<ContentParameter>();


	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="report_template_output_parameter",
			joinColumns = { @JoinColumn(name = "report_template_id") },
			inverseJoinColumns = { @JoinColumn(name = "output_parameter_id") })
	Set<OutputParameter> outputParameters = new HashSet<OutputParameter>();


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

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public Set<GenerationParameter> getGenerationParameters() {
		return generationParameters;
	}

	public void setGenerationParameters(Set<GenerationParameter> generationParameters) {
		this.generationParameters = generationParameters;
	}


	public Set<FilterParameter> getFilterParameters() {
		return filterParameters;
	}

	public void setFilterParameters(Set<FilterParameter> filterParameters) {
		this.filterParameters = filterParameters;
	}

	public Set<ContentParameter> getContentParameters() {
		return contentParameters;
	}

	public void setContentParameters(Set<ContentParameter> contentParameters) {
		this.contentParameters = contentParameters;
	}

	public Set<OutputParameter> getOutputParameters() {
		return outputParameters;
	}

	public void setOutputParameters(Set<OutputParameter> outputParameters) {
		this.outputParameters = outputParameters;
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
		final ReportTemplate other = (ReportTemplate) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}


}
