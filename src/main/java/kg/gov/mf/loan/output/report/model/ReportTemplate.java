package kg.gov.mf.loan.output.report.model;

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

import kg.gov.mf.loan.output.report.model.*;

@Entity
@Table(name="report_template")

public class ReportTemplate {
 
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
 
    @Column(name="name", nullable=false)
    private String name;
    
    @ManyToOne(targetEntity=Report.class, fetch = FetchType.EAGER)
    @JoinColumn(name="report_template_id")
    Report report;      

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
    
    
}
