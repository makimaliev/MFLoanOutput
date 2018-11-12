package kg.gov.mf.loan.output.report.model;

import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="staff_event_view")
@Immutable
public class StaffEventView extends ReportView
{

//	    `s`.`name`                              AS `v_s_staff_name`,
//		`s`.`department_id`                     AS `v_s_department_id`,
//		`s`.`position_id`                       AS `v_s_position_id`,
//		`s`.`organization_id`                   AS `v_s_organization_id`,
//		`a`.`region_id`                         AS `v_s_region_id`,
//		`a`.`district_id`                       AS `v_s_district_id`,
//		`a`.`line`                              AS `v_s_line`,
//		`c`.`name`                              AS `v_s_contact_name`,
//		`idoc`.`date`                           AS `v_s_id_doc_date`,
//		`idoc`.`name`                           AS `v_s_id_doc_name`,
//		`idoc`.`number`                         AS `v_s_id_doc_number`,
//		`idoc`.`pin`                            AS `v_s_id_doc_pin`,
//		`idoc`.`identity_doc_given_by_id`       AS `v_s_id_doc_given_by_id`,
//		`idoc`.`identity_doc_type_id`           AS `v_s_id_doc_type_id`,
//		`ev`.`date`                             AS `v_se_date`,
//		`ev`.`name`                             AS `v_se_name`,
//		`ev`.`employment_history_event_type_id` AS `v_se_type_id`
//
	@Column
	private Long v_s_id;

	@Column
	private String v_s_name;

	@Column
	private Long v_s_organization_id;

	@Column
	private Long v_s_department_id;

	@Column
	private Long v_s_position_id;

	@Column
	private Long v_s_region_id;

	@Column
	private Long v_s_district_id;

	@Column
	private String v_s_line;

	@Column
	private String v_s_contact_name;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_s_id_doc_date;

	@Column
	private String v_s_id_doc_name;

	@Column
	private String v_s_id_doc_number;

	@Column
	private String v_s_id_doc_pin;

	@Column
	private Long v_s_id_doc_given_by_id;

	@Column
	private Long v_s_id_doc_type_id;

	@Id
	@Column
	private Long v_se_id;

	@Column
	private String v_se_name;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_se_date;

	@Column
	private Long v_se_type_id;

	public Long getV_s_id() {
		return v_s_id;
	}

	public void setV_s_id(Long v_s_id) {
		this.v_s_id = v_s_id;
	}

	public String getV_s_name() {
		return v_s_name;
	}

	public void setV_s_name(String v_s_name) {
		this.v_s_name = v_s_name;
	}

	public Long getV_s_organization_id() {
		return v_s_organization_id;
	}

	public void setV_s_organization_id(Long v_s_organization_id) {
		this.v_s_organization_id = v_s_organization_id;
	}

	public Long getV_s_department_id() {
		return v_s_department_id;
	}

	public void setV_s_department_id(Long v_s_department_id) {
		this.v_s_department_id = v_s_department_id;
	}

	public Long getV_s_position_id() {
		return v_s_position_id;
	}

	public void setV_s_position_id(Long v_s_position_id) {
		this.v_s_position_id = v_s_position_id;
	}

	public Long getV_s_region_id() {
		return v_s_region_id;
	}

	public void setV_s_region_id(Long v_s_region_id) {
		this.v_s_region_id = v_s_region_id;
	}

	public Long getV_s_district_id() {
		return v_s_district_id;
	}

	public void setV_s_district_id(Long v_s_district_id) {
		this.v_s_district_id = v_s_district_id;
	}

	public String getV_s_line() {
		return v_s_line;
	}

	public void setV_s_line(String v_s_line) {
		this.v_s_line = v_s_line;
	}

	public String getV_s_contact_name() {
		return v_s_contact_name;
	}

	public void setV_s_contact_name(String v_s_contact_name) {
		this.v_s_contact_name = v_s_contact_name;
	}

	public Date getV_s_id_doc_date() {
		return v_s_id_doc_date;
	}

	public void setV_s_id_doc_date(Date v_s_id_doc_date) {
		this.v_s_id_doc_date = v_s_id_doc_date;
	}

	public String getV_s_id_doc_name() {
		return v_s_id_doc_name;
	}

	public void setV_s_id_doc_name(String v_s_id_doc_name) {
		this.v_s_id_doc_name = v_s_id_doc_name;
	}

	public String getV_s_id_doc_number() {
		return v_s_id_doc_number;
	}

	public void setV_s_id_doc_number(String v_s_id_doc_number) {
		this.v_s_id_doc_number = v_s_id_doc_number;
	}

	public String getV_s_id_doc_pin() {
		return v_s_id_doc_pin;
	}

	public void setV_s_id_doc_pin(String v_s_id_doc_pin) {
		this.v_s_id_doc_pin = v_s_id_doc_pin;
	}

	public Long getV_s_id_doc_given_by_id() {
		return v_s_id_doc_given_by_id;
	}

	public void setV_s_id_doc_given_by_id(Long v_s_id_doc_given_by_id) {
		this.v_s_id_doc_given_by_id = v_s_id_doc_given_by_id;
	}

	public Long getV_s_id_doc_type_id() {
		return v_s_id_doc_type_id;
	}

	public void setV_s_id_doc_type_id(Long v_s_id_doc_type_id) {
		this.v_s_id_doc_type_id = v_s_id_doc_type_id;
	}

	public Long getV_se_id() {
		return v_se_id;
	}

	public void setV_se_id(Long v_se_id) {
		this.v_se_id = v_se_id;
	}

	public String getV_se_name() {
		return v_se_name;
	}

	public void setV_se_name(String v_se_name) {
		this.v_se_name = v_se_name;
	}

	public Date getV_se_date() {
		return v_se_date;
	}

	public void setV_se_date(Date v_se_date) {
		this.v_se_date = v_se_date;
	}

	public Long getV_se_type_id() {
		return v_se_type_id;
	}

	public void setV_se_type_id(Long v_se_type_id) {
		this.v_se_type_id = v_se_type_id;
	}
}