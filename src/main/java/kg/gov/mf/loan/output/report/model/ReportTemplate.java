package kg.gov.mf.loan.output.report.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.output.report.model.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="report_template")

public class ReportTemplate {
 
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
 
    @Column(name="name", nullable=false)
    private String name;

	@Column(name="showGroup1", columnDefinition = "boolean default true")
	private Boolean showGroup1 = true;

	@Column(name="showGroup2", columnDefinition = "boolean default true")
	private Boolean showGroup2 = true;

	@Column(name="showGroup3", columnDefinition = "boolean default false")
	private Boolean showGroup3 = true;

	@Column(name="showGroup4", columnDefinition = "boolean default false")
	private Boolean showGroup4 = true;

	@Column(name="showGroup5", columnDefinition = "boolean default false")
	private Boolean showGroup5 = true;

	@Column(name="showGroup6", columnDefinition = "boolean default false")
	private Boolean showGroup6 = true;

	@ManyToOne(targetEntity=Report.class, fetch = FetchType.LAZY)
    @JoinColumn(name="report_id")
    Report report;

	@ManyToOne(targetEntity=GroupType.class, fetch = FetchType.LAZY)
	@JoinColumn(name="group_type1_id")
	GroupType groupType1;

	@ManyToOne(targetEntity=GroupType.class, fetch = FetchType.LAZY)
	@JoinColumn(name="group_type2_id")
	GroupType groupType2;


	@ManyToOne(targetEntity=GroupType.class, fetch = FetchType.LAZY)
	@JoinColumn(name="group_type3_id")
	GroupType groupType3;



	@ManyToOne(targetEntity=GroupType.class, fetch = FetchType.LAZY)
	@JoinColumn(name="group_type4_id")
	GroupType groupType4;



	@ManyToOne(targetEntity=GroupType.class, fetch = FetchType.LAZY)
	@JoinColumn(name="group_type5_id")
	GroupType groupType5;



	@ManyToOne(targetEntity=GroupType.class, fetch = FetchType.LAZY)
	@JoinColumn(name="group_type6_id")
	GroupType groupType6;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date onDate;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date additionalDate;


	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="report_template_generation_parameter",
			joinColumns = { @JoinColumn(name = "report_template_id") },
			inverseJoinColumns = { @JoinColumn(name = "generation_parameter_id") })
	Set<GenerationParameter> generationParameters = new HashSet<GenerationParameter>();


	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="report_template_filter_parameter",
			joinColumns = { @JoinColumn(name = "report_template_id") },
			inverseJoinColumns = { @JoinColumn(name = "filter_parameter_id") })
	Set<FilterParameter> filterParameters = new HashSet<FilterParameter>();


	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="report_template_content_parameter",
			joinColumns = { @JoinColumn(name = "report_template_id") },
			inverseJoinColumns = { @JoinColumn(name = "content_parameter_id") })
	Set<ContentParameter> contentParameters = new HashSet<ContentParameter>();


	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="report_template_output_parameter",
			joinColumns = { @JoinColumn(name = "report_template_id") },
			inverseJoinColumns = { @JoinColumn(name = "output_parameter_id") })
	Set<OutputParameter> outputParameters = new HashSet<OutputParameter>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="report_template_user",
			joinColumns = { @JoinColumn(name = "report_template_id") },
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

	public GroupType getGroupType1() {
		return groupType1;
	}

	public void setGroupType1(GroupType groupType1) {
		this.groupType1 = groupType1;
	}

	public GroupType getGroupType2() {
		return groupType2;
	}

	public void setGroupType2(GroupType groupType2) {
		this.groupType2 = groupType2;
	}

	public GroupType getGroupType3() {
		return groupType3;
	}

	public void setGroupType3(GroupType groupType3) {
		this.groupType3 = groupType3;
	}

	public GroupType getGroupType4() {
		return groupType4;
	}

	public void setGroupType4(GroupType groupType4) {
		this.groupType4 = groupType4;
	}

	public GroupType getGroupType5() {
		return groupType5;
	}

	public void setGroupType5(GroupType groupType5) {
		this.groupType5 = groupType5;
	}

	public GroupType getGroupType6() {
		return groupType6;
	}

	public void setGroupType6(GroupType groupType6) {
		this.groupType6 = groupType6;
	}

	public Date getOnDate() {
		return onDate;
	}

	public void setOnDate(Date onDate) {
		this.onDate = onDate;
	}

	public Date getAdditionalDate() {
		return additionalDate;
	}

	public void setAdditionalDate(Date additionalDate) {
		this.additionalDate = additionalDate;
	}

	public Boolean getShowGroup1() {
		return showGroup1;
	}

	public void setShowGroup1(Boolean showGroup1) {
		this.showGroup1 = showGroup1;
	}

	public Boolean getShowGroup2() {
		return showGroup2;
	}

	public void setShowGroup2(Boolean showGroup2) {
		this.showGroup2 = showGroup2;
	}

	public Boolean getShowGroup3() {
		return showGroup3;
	}

	public void setShowGroup3(Boolean showGroup3) {
		this.showGroup3 = showGroup3;
	}

	public Boolean getShowGroup4() {
		return showGroup4;
	}

	public void setShowGroup4(Boolean showGroup4) {
		this.showGroup4 = showGroup4;
	}

	public Boolean getShowGroup5() {
		return showGroup5;
	}

	public void setShowGroup5(Boolean showGroup5) {
		this.showGroup5 = showGroup5;
	}

	public Boolean getShowGroup6() {
		return showGroup6;
	}

	public void setShowGroup6(Boolean showGroup6) {
		this.showGroup6 = showGroup6;
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
		final ReportTemplate other = (ReportTemplate) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}


}
