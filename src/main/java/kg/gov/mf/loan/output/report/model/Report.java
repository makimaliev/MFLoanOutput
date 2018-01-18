package kg.gov.mf.loan.output.report.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import kg.gov.mf.loan.output.report.model.*;

@Entity
@Table(name="report")

public class Report {
 
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
 
    @Column(name="name", nullable=false)
    private String name;
    
    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    private Set<ReportTemplate> reportTemplate = new HashSet<ReportTemplate>();    

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

	public Set<ReportTemplate> getReportTemplate() {
		return reportTemplate;
	}

	public void setReportTemplate(Set<ReportTemplate> reportTemplate) {
		this.reportTemplate = reportTemplate;
	}
	
	
    
    
}
