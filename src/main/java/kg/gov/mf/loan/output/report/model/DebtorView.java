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
	private long v_debtor_id;

	@Column
	private String v_debtor_name;

	@Column
	private long v_debtor_type_id;

	@Column
	private long v_debtor_org_form_id;

	@Column
	private long v_debtor_work_sector_id;

	@Column
	private long v_debtor_entity_id;

	@Column
	private String v_debtor_owner_type;

	@Column
	private long v_debtor_region_id;

	@Column
	private long v_debtor_district_id;

	@Column
	private long v_debtor_aokmotu_id;

	@Column
	private long v_debtor_village_id;

	@Column
	private String v_region_name;

	@Column
	private String v_district_name;

	@Column
	private String v_work_sector_name;



	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public long getV_debtor_id() {
		return v_debtor_id;
	}

	public void setV_debtor_id(long v_debtor_id) {
		this.v_debtor_id = v_debtor_id;
	}

	public String getV_debtor_name() {
		return v_debtor_name;
	}

	public void setV_debtor_name(String v_debtor_name) {
		this.v_debtor_name = v_debtor_name;
	}

	public long getV_debtor_type_id() {
		return v_debtor_type_id;
	}

	public void setV_debtor_type_id(long v_debtor_type_id) {
		this.v_debtor_type_id = v_debtor_type_id;
	}

	public long getV_debtor_org_form_id() {
		return v_debtor_org_form_id;
	}

	public void setV_debtor_org_form_id(long v_debtor_org_form_id) {
		this.v_debtor_org_form_id = v_debtor_org_form_id;
	}

	public long getV_debtor_work_sector_id() {
		return v_debtor_work_sector_id;
	}

	public void setV_debtor_work_sector_id(long v_debtor_work_sector_id) {
		this.v_debtor_work_sector_id = v_debtor_work_sector_id;
	}

	public long getV_debtor_entity_id() {
		return v_debtor_entity_id;
	}

	public void setV_debtor_entity_id(long v_debtor_entity_id) {
		this.v_debtor_entity_id = v_debtor_entity_id;
	}

	public String getV_debtor_owner_type() {
		return v_debtor_owner_type;
	}

	public void setV_debtor_owner_type(String v_debtor_owner_type) {
		this.v_debtor_owner_type = v_debtor_owner_type;
	}

	public long getV_debtor_region_id() {
		return v_debtor_region_id;
	}

	public void setV_debtor_region_id(long v_debtor_region_id) {
		this.v_debtor_region_id = v_debtor_region_id;
	}

	public long getV_debtor_district_id() {
		return v_debtor_district_id;
	}

	public void setV_debtor_district_id(long v_debtor_district_id) {
		this.v_debtor_district_id = v_debtor_district_id;
	}

	public long getV_debtor_aokmotu_id() {
		return v_debtor_aokmotu_id;
	}

	public void setV_debtor_aokmotu_id(long v_debtor_aokmotu_id) {
		this.v_debtor_aokmotu_id = v_debtor_aokmotu_id;
	}

	public long getV_debtor_village_id() {
		return v_debtor_village_id;
	}

	public void setV_debtor_village_id(long v_debtor_village_id) {
		this.v_debtor_village_id = v_debtor_village_id;
	}

	public String getV_region_name() {
		return v_region_name;
	}

	public void setV_region_name(String v_region_name) {
		this.v_region_name = v_region_name;
	}

	public String getV_district_name() {
		return v_district_name;
	}

	public void setV_district_name(String v_district_name) {
		this.v_district_name = v_district_name;
	}

	public String getV_work_sector_name() {
		return v_work_sector_name;
	}

	public void setV_work_sector_name(String v_work_sector_name) {
		this.v_work_sector_name = v_work_sector_name;
	}
}
