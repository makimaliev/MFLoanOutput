package kg.gov.mf.loan.output.report.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;


import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.output.report.model.*;

@Entity
@Table(name="report")

public class Report {
 
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
 
    @Column(name="name", nullable=false)
    private String name;
    
    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
    private Set<ReportTemplate> reportTemplates = new HashSet<ReportTemplate>();

	@Enumerated(EnumType.STRING)
	private ReportType reportType;



	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="report_group_type",
			joinColumns = { @JoinColumn(name = "report_id") },
			inverseJoinColumns = { @JoinColumn(name = "group_type_id") })
	Set<GroupType> groupTypes = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="report_content_parameter",
			joinColumns = { @JoinColumn(name = "report_id") },
			inverseJoinColumns = { @JoinColumn(name = "content_parameter_id") })
	Set<ContentParameter> contentParameters = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="report_filter_parameter",
			joinColumns = { @JoinColumn(name = "report_id") },
			inverseJoinColumns = { @JoinColumn(name = "filter_parameter_id") })
	Set<FilterParameter> filterParameters = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="report_output_parameter",
			joinColumns = { @JoinColumn(name = "report_id") },
			inverseJoinColumns = { @JoinColumn(name = "output_parameter_id") })
	Set<OutputParameter> outputParameters = new HashSet<OutputParameter>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="report_user",
			joinColumns = { @JoinColumn(name = "report_id") },
			inverseJoinColumns = { @JoinColumn(name = "user_id") })
	Set<User> users = new HashSet<User>();


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


	public Set<ReportTemplate> getReportTemplates() {
		return reportTemplates;
	}

	public void setReportTemplates(Set<ReportTemplate> reportTemplates) {
		this.reportTemplates = reportTemplates;
	}

	public Set<GroupType> getGroupTypes() {
		return groupTypes;
	}

	public void setGroupTypes(Set<GroupType> groupTypes) {
		this.groupTypes = groupTypes;
	}

	public Set<ContentParameter> getContentParameters() {
		return contentParameters;
	}

	public void setContentParameters(Set<ContentParameter> contentParameters) {
		this.contentParameters = contentParameters;
	}

	public Set<FilterParameter> getFilterParameters() {
		return filterParameters;
	}

	public void setFilterParameters(Set<FilterParameter> filterParameters) {
		this.filterParameters = filterParameters;
	}


	public Set<OutputParameter> getOutputParameters() {
		return outputParameters;
	}

	public void setOutputParameters(Set<OutputParameter> outputParameters) {
		this.outputParameters = outputParameters;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
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
