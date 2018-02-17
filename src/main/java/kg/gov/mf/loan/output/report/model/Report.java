package kg.gov.mf.loan.output.report.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;


import kg.gov.mf.loan.output.report.model.*;

@Entity
@Table(name="report")

public class Report {
 
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
 
    @Column(name="name", nullable=false)
    private String name;
    
    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    private Set<ReportTemplate> reportTemplates = new HashSet<ReportTemplate>();

	@Enumerated(EnumType.STRING)
	private ReportType reportType;

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
		return reportTemplates;
	}

	public void setReportTemplate(Set<ReportTemplate> reportTemplates) {
		this.reportTemplates = reportTemplates;
	}

	public ReportType getReportType() {
		return reportType;
	}

	public void setReportType(ReportType reportType) {
		this.reportType = reportType;
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
		final Report other = (Report) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
	
    
    
}
