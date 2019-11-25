package kg.gov.mf.loan.output.report.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class DebtorView extends ReportView {

	private static final long serialVersionUID = 1L;

	@Column
	private Long v_debtor_id;

	@Column
	private String v_debtor_name;

	@Column
	private Long v_debtor_type_id;

	@Column
	private Long v_debtor_org_form_id;

	@Column
	private Long v_debtor_work_sector_id;

	@Column
	private Long v_debtor_entity_id;

	@Column
	private String v_debtor_owner_type;

	@Column
	private Long v_debtor_region_id;

	@Column
	private Long v_debtor_district_id;

	@Column
	private Long v_debtor_aokmotu_id;

	@Column
	private Long v_debtor_village_id;

	@Column
	private Long v_debtor_group_id;

	@Column
	private Long v_debtor_subGroup_id;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getV_debtor_id() {
		return v_debtor_id;
	}

	public void setV_debtor_id(Long v_debtor_id) {
		this.v_debtor_id = v_debtor_id;
	}

	public String getV_debtor_name() {
		return v_debtor_name;
	}

	public void setV_debtor_name(String v_debtor_name) {
		this.v_debtor_name = v_debtor_name;
	}

	public Long getV_debtor_type_id() {
		return v_debtor_type_id;
	}

	public void setV_debtor_type_id(Long v_debtor_type_id) {
		this.v_debtor_type_id = v_debtor_type_id;
	}

	public Long getV_debtor_org_form_id() {
		return v_debtor_org_form_id;
	}

	public void setV_debtor_org_form_id(Long v_debtor_org_form_id) {
		this.v_debtor_org_form_id = v_debtor_org_form_id;
	}

	public Long getV_debtor_work_sector_id() {
		return v_debtor_work_sector_id;
	}

	public void setV_debtor_work_sector_id(Long v_debtor_work_sector_id) {
		this.v_debtor_work_sector_id = v_debtor_work_sector_id;
	}

	public Long getV_debtor_entity_id() {
		return v_debtor_entity_id;
	}

	public void setV_debtor_entity_id(Long v_debtor_entity_id) {
		this.v_debtor_entity_id = v_debtor_entity_id;
	}

	public String getV_debtor_owner_type() {
		return v_debtor_owner_type;
	}

	public void setV_debtor_owner_type(String v_debtor_owner_type) {
		this.v_debtor_owner_type = v_debtor_owner_type;
	}

	public Long getV_debtor_region_id() {
		return v_debtor_region_id;
	}

	public void setV_debtor_region_id(Long v_debtor_region_id) {
		this.v_debtor_region_id = v_debtor_region_id;
	}

	public Long getV_debtor_district_id() {
		return v_debtor_district_id;
	}

	public void setV_debtor_district_id(Long v_debtor_district_id) {
		this.v_debtor_district_id = v_debtor_district_id;
	}

	public Long getV_debtor_aokmotu_id() {
		return v_debtor_aokmotu_id;
	}

	public void setV_debtor_aokmotu_id(Long v_debtor_aokmotu_id) {
		this.v_debtor_aokmotu_id = v_debtor_aokmotu_id;
	}

	public Long getV_debtor_village_id() {
		return v_debtor_village_id;
	}

	public void setV_debtor_village_id(Long v_debtor_village_id) {
		this.v_debtor_village_id = v_debtor_village_id;
	}

	public Long getV_debtor_group_id() {
		return v_debtor_group_id;
	}

	public void setV_debtor_group_id(Long v_debtor_group_id) {
		this.v_debtor_group_id = v_debtor_group_id;
	}

	public Long getV_debtor_subGroup_id() {
		return v_debtor_subGroup_id;
	}

	public void setV_debtor_subGroup_id(Long v_debtor_subGroup_id) {
		this.v_debtor_subGroup_id = v_debtor_subGroup_id;
	}
}
