package kg.gov.mf.loan.output.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import kg.gov.mf.loan.output.report.model.*;

@Entity
@Table(name="generation_parameter")

public class GenerationParameter {
 
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
 
    @Column(name="name", nullable=false)
    private String name;
    
    @ManyToOne(targetEntity=ReportTemplate.class, fetch = FetchType.EAGER)
    @JoinColumn(name="report_template_id")
    ReportTemplate reportTemplate;
    
    @DateTimeFormat(pattern = "dd.mm.yyyy")
    @Temporal(TemporalType.DATE)
    private Date date;  
    
    @Column(name="ref_id")
    private long refId;

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

	public ReportTemplate getReportTemplate() {
		return reportTemplate;
	}

	public void setReportTemplate(ReportTemplate reportTemplate) {
		this.reportTemplate = reportTemplate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getRefId() {
		return refId;
	}

	public void setRefId(long refId) {
		this.refId = refId;
	}
    
    
    

    
}
