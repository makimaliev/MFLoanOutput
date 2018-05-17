package kg.gov.mf.loan.output.report.model;

import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="collection_phase_view")
@Immutable
public class CollectionPhaseView extends DebtorView
{
	@Column
	private long v_cp_id;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_cp_closeDate;

	@Column
	private long v_cp_lastPhase;

	@Column
	private long v_cp_lastStatusId;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_cp_startDate;

	@Column
	private long v_cp_procedureStatusId;

	@Column
	private long v_cp_procedureTypeId;

	@Column
	private long v_debtor_id;


	@Column
	private String v_debtor_name;

	@Column
	private long v_debtor_type_id;

	@Column
	private long v_debtor_org_form_id;

	@Column
	private long v_debtor_owner_id;




	@Column
	private long v_debtor_work_sector_id;


	@Column
	private long v_debtor_entity_id;


	@Column
	private String v_debtor_owner_type;


	@Column
	private long v_debtor_address_id;



	@Column
	private long v_debtor_region_id;
	@Column
	private long v_debtor_district_id;
	@Column
	private long v_debtor_aokmotu_id;
	@Column
	private long v_debtor_village_id;










	@Id
	@Column
	private long v_cph_id;



	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_cph_closeDate;

	@Column
	private long v_cph_lastEvent;

	@Column
	private long v_cph_lastStatusId;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_cph_startDate;


	@Column
	private long v_cph_collectionProcedureId;

//	@Column
//	private Long v_cph_phaseStatusId;

	@Column
	private long v_cph_phaseTypeId;

	@Column
	private long v_cph_start_total_amount;


	public long getV_cp_id() {
		return v_cp_id;
	}

	public void setV_cp_id(long v_cp_id) {
		this.v_cp_id = v_cp_id;
	}

	public Date getV_cp_closeDate() {
		return v_cp_closeDate;
	}

	public void setV_cp_closeDate(Date v_cp_closeDate) {
		this.v_cp_closeDate = v_cp_closeDate;
	}

	public long getV_cp_lastPhase() {
		return v_cp_lastPhase;
	}

	public void setV_cp_lastPhase(long v_cp_lastPhase) {
		this.v_cp_lastPhase = v_cp_lastPhase;
	}

	public long getV_cp_lastStatusId() {
		return v_cp_lastStatusId;
	}

	public void setV_cp_lastStatusId(long v_cp_lastStatusId) {
		this.v_cp_lastStatusId = v_cp_lastStatusId;
	}

	public Date getV_cp_startDate() {
		return v_cp_startDate;
	}

	public void setV_cp_startDate(Date v_cp_startDate) {
		this.v_cp_startDate = v_cp_startDate;
	}

	public long getV_cp_procedureStatusId() {
		return v_cp_procedureStatusId;
	}

	public void setV_cp_procedureStatusId(long v_cp_procedureStatusId) {
		this.v_cp_procedureStatusId = v_cp_procedureStatusId;
	}

	public long getV_cp_procedureTypeId() {
		return v_cp_procedureTypeId;
	}

	public void setV_cp_procedureTypeId(long v_cp_procedureTypeId) {
		this.v_cp_procedureTypeId = v_cp_procedureTypeId;
	}

	@Override
	public long getV_debtor_id() {
		return v_debtor_id;
	}

	@Override
	public void setV_debtor_id(long v_debtor_id) {
		this.v_debtor_id = v_debtor_id;
	}

	@Override
	public String getV_debtor_name() {
		return v_debtor_name;
	}

	@Override
	public void setV_debtor_name(String v_debtor_name) {
		this.v_debtor_name = v_debtor_name;
	}

	@Override
	public long getV_debtor_type_id() {
		return v_debtor_type_id;
	}

	@Override
	public void setV_debtor_type_id(long v_debtor_type_id) {
		this.v_debtor_type_id = v_debtor_type_id;
	}

	@Override
	public long getV_debtor_org_form_id() {
		return v_debtor_org_form_id;
	}

	@Override
	public void setV_debtor_org_form_id(long v_debtor_org_form_id) {
		this.v_debtor_org_form_id = v_debtor_org_form_id;
	}

	public long getV_debtor_owner_id() {
		return v_debtor_owner_id;
	}

	public void setV_debtor_owner_id(long v_debtor_owner_id) {
		this.v_debtor_owner_id = v_debtor_owner_id;
	}

	@Override
	public long getV_debtor_work_sector_id() {
		return v_debtor_work_sector_id;
	}

	@Override
	public void setV_debtor_work_sector_id(long v_debtor_work_sector_id) {
		this.v_debtor_work_sector_id = v_debtor_work_sector_id;
	}

	@Override
	public long getV_debtor_entity_id() {
		return v_debtor_entity_id;
	}

	@Override
	public void setV_debtor_entity_id(long v_debtor_entity_id) {
		this.v_debtor_entity_id = v_debtor_entity_id;
	}

	@Override
	public String getV_debtor_owner_type() {
		return v_debtor_owner_type;
	}

	@Override
	public void setV_debtor_owner_type(String v_debtor_owner_type) {
		this.v_debtor_owner_type = v_debtor_owner_type;
	}

	public long getV_debtor_address_id() {
		return v_debtor_address_id;
	}

	public void setV_debtor_address_id(long v_debtor_address_id) {
		this.v_debtor_address_id = v_debtor_address_id;
	}

	@Override
	public long getV_debtor_region_id() {
		return v_debtor_region_id;
	}

	@Override
	public void setV_debtor_region_id(long v_debtor_region_id) {
		this.v_debtor_region_id = v_debtor_region_id;
	}

	@Override
	public long getV_debtor_district_id() {
		return v_debtor_district_id;
	}

	@Override
	public void setV_debtor_district_id(long v_debtor_district_id) {
		this.v_debtor_district_id = v_debtor_district_id;
	}

	@Override
	public long getV_debtor_aokmotu_id() {
		return v_debtor_aokmotu_id;
	}

	@Override
	public void setV_debtor_aokmotu_id(long v_debtor_aokmotu_id) {
		this.v_debtor_aokmotu_id = v_debtor_aokmotu_id;
	}

	@Override
	public long getV_debtor_village_id() {
		return v_debtor_village_id;
	}

	@Override
	public void setV_debtor_village_id(long v_debtor_village_id) {
		this.v_debtor_village_id = v_debtor_village_id;
	}

	public long getV_cph_id() {
		return v_cph_id;
	}

	public void setV_cph_id(long v_cph_id) {
		this.v_cph_id = v_cph_id;
	}

	public Date getV_cph_closeDate() {
		return v_cph_closeDate;
	}

	public void setV_cph_closeDate(Date v_cph_closeDate) {
		this.v_cph_closeDate = v_cph_closeDate;
	}

	public long getV_cph_lastEvent() {
		return v_cph_lastEvent;
	}

	public void setV_cph_lastEvent(long v_cph_lastEvent) {
		this.v_cph_lastEvent = v_cph_lastEvent;
	}

	public long getV_cph_lastStatusId() {
		return v_cph_lastStatusId;
	}

	public void setV_cph_lastStatusId(long v_cph_lastStatusId) {
		this.v_cph_lastStatusId = v_cph_lastStatusId;
	}

	public Date getV_cph_startDate() {
		return v_cph_startDate;
	}

	public void setV_cph_startDate(Date v_cph_startDate) {
		this.v_cph_startDate = v_cph_startDate;
	}

	public long getV_cph_collectionProcedureId() {
		return v_cph_collectionProcedureId;
	}

	public void setV_cph_collectionProcedureId(long v_cph_collectionProcedureId) {
		this.v_cph_collectionProcedureId = v_cph_collectionProcedureId;
	}

//	public long getV_cph_phaseStatusId() {
//		return v_cph_phaseStatusId;
//	}
//
//	public void setV_cph_phaseStatusId(long v_cph_phaseStatusId) {
//		this.v_cph_phaseStatusId = v_cph_phaseStatusId;
//	}


	public long getV_cph_start_total_amount() {
		return v_cph_start_total_amount;
	}

	public void setV_cph_start_total_amount(long v_cph_start_total_amount) {
		this.v_cph_start_total_amount = v_cph_start_total_amount;
	}

	public long getV_cph_phaseTypeId() {
		return v_cph_phaseTypeId;
	}

	public void setV_cph_phaseTypeId(long v_cph_phaseTypeId) {
		this.v_cph_phaseTypeId = v_cph_phaseTypeId;
	}
}