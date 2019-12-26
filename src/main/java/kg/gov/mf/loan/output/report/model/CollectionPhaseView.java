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
	private Long v_cp_id;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_cp_closeDate;

	@Column
	private Long v_cp_lastPhase;

	@Column
	private Long v_cp_lastStatusId;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_cp_startDate;

	@Column
	private Long v_cp_procedureStatusId;

	@Column
	private Long v_cp_procedureTypeId;

	@Column
	private Long v_debtor_id;


	@Column
	private String v_debtor_name;

	@Column
	private Long v_debtor_type_id;

	@Column
	private Long v_debtor_org_form_id;

	@Column
	private Long v_debtor_owner_id;




	@Column
	private Long v_debtor_work_sector_id;


	@Column
	private Long v_debtor_entity_id;


	@Column
	private String v_debtor_owner_type;


	@Column
	private Long v_debtor_address_id;



	@Column
	private Long v_debtor_region_id;
	@Column
	private Long v_debtor_district_id;
	@Column
	private Long v_debtor_aokmotu_id;
	@Column
	private Long v_debtor_village_id;



	@Column
	private Long v_loan_id;

	@Column
	private Double v_loan_amount;

	@Column
	private String v_loan_reg_number;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_loan_reg_date;

	@Column
	private Long v_loan_supervisor_id;

	@Column
	private  Long v_loan_fin_group_id;

	@Column
	private Long v_loan_currency_id;

	@Column
	private Long v_loan_state_id;

	@Column
	private Long v_loan_type_id;

	@Column
	private Long v_credit_order_type_id;

	@Column
	private String v_credit_order_reg_number;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_credit_order_reg_date;







	@Id
	@Column
	private Long v_cph_id;



	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_cph_closeDate;


	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_cph_paymentFromDate;

	@Column
	private Long v_cph_lastEvent;

	@Column
	private Long v_cph_lastStatusId;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_cph_startDate;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_cph_regDate;


	@Column
	private Long v_cph_collectionProcedureId;

	@Column
	private Long v_cph_phaseStatusId;

	@Column
	private Long v_cph_phaseTypeId;

	@Column
	private Long v_cph_start_total_amount;

	@Column
	private Long v_cph_close_total_amount;

	@Column
	private Long v_cph_paid_total_amount;


	@Column
	private String v_cph_doc_number;

	@Column
	private String v_cph_result_doc_number;

	@Column
	private Long v_cph_group_id;

	@Column
	private Long v_cph_index_id;

	@Column
	private Long v_cph_sub_index_id;

	@Column
	private String v_cph_description;


	public Long getV_cp_id() {
		return v_cp_id;
	}

	public void setV_cp_id(Long v_cp_id) {
		this.v_cp_id = v_cp_id;
	}

	public Date getV_cp_closeDate() {
		return v_cp_closeDate;
	}

	public void setV_cp_closeDate(Date v_cp_closeDate) {
		this.v_cp_closeDate = v_cp_closeDate;
	}

	public Long getV_cp_lastPhase() {
		return v_cp_lastPhase;
	}

	public void setV_cp_lastPhase(Long v_cp_lastPhase) {
		this.v_cp_lastPhase = v_cp_lastPhase;
	}

	public Long getV_cp_lastStatusId() {
		return v_cp_lastStatusId;
	}

	public void setV_cp_lastStatusId(Long v_cp_lastStatusId) {
		this.v_cp_lastStatusId = v_cp_lastStatusId;
	}

	public Date getV_cp_startDate() {
		return v_cp_startDate;
	}

	public void setV_cp_startDate(Date v_cp_startDate) {
		this.v_cp_startDate = v_cp_startDate;
	}

	public Long getV_cp_procedureStatusId() {
		return v_cp_procedureStatusId;
	}

	public void setV_cp_procedureStatusId(Long v_cp_procedureStatusId) {
		this.v_cp_procedureStatusId = v_cp_procedureStatusId;
	}

	public Long getV_cp_procedureTypeId() {
		return v_cp_procedureTypeId;
	}

	public void setV_cp_procedureTypeId(Long v_cp_procedureTypeId) {
		this.v_cp_procedureTypeId = v_cp_procedureTypeId;
	}

	@Override
	public Long getV_debtor_id() {
		return v_debtor_id;
	}

	@Override
	public void setV_debtor_id(Long v_debtor_id) {
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
	public Long getV_debtor_type_id() {
		return v_debtor_type_id;
	}

	@Override
	public void setV_debtor_type_id(Long v_debtor_type_id) {
		this.v_debtor_type_id = v_debtor_type_id;
	}

	@Override
	public Long getV_debtor_org_form_id() {
		return v_debtor_org_form_id;
	}

	@Override
	public void setV_debtor_org_form_id(Long v_debtor_org_form_id) {
		this.v_debtor_org_form_id = v_debtor_org_form_id;
	}

	public Long getV_debtor_owner_id() {
		return v_debtor_owner_id;
	}

	public void setV_debtor_owner_id(Long v_debtor_owner_id) {
		this.v_debtor_owner_id = v_debtor_owner_id;
	}

	@Override
	public Long getV_debtor_work_sector_id() {
		return v_debtor_work_sector_id;
	}

	@Override
	public void setV_debtor_work_sector_id(Long v_debtor_work_sector_id) {
		this.v_debtor_work_sector_id = v_debtor_work_sector_id;
	}

	@Override
	public Long getV_debtor_entity_id() {
		return v_debtor_entity_id;
	}

	@Override
	public void setV_debtor_entity_id(Long v_debtor_entity_id) {
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

	public Long getV_debtor_address_id() {
		return v_debtor_address_id;
	}

	public void setV_debtor_address_id(Long v_debtor_address_id) {
		this.v_debtor_address_id = v_debtor_address_id;
	}

	@Override
	public Long getV_debtor_region_id() {
		return v_debtor_region_id;
	}

	@Override
	public void setV_debtor_region_id(Long v_debtor_region_id) {
		this.v_debtor_region_id = v_debtor_region_id;
	}

	@Override
	public Long getV_debtor_district_id() {
		return v_debtor_district_id;
	}

	@Override
	public void setV_debtor_district_id(Long v_debtor_district_id) {
		this.v_debtor_district_id = v_debtor_district_id;
	}

	@Override
	public Long getV_debtor_aokmotu_id() {
		return v_debtor_aokmotu_id;
	}

	@Override
	public void setV_debtor_aokmotu_id(Long v_debtor_aokmotu_id) {
		this.v_debtor_aokmotu_id = v_debtor_aokmotu_id;
	}

	@Override
	public Long getV_debtor_village_id() {
		return v_debtor_village_id;
	}

	@Override
	public void setV_debtor_village_id(Long v_debtor_village_id) {
		this.v_debtor_village_id = v_debtor_village_id;
	}

	public Long getV_cph_id() {
		return v_cph_id;
	}

	public void setV_cph_id(Long v_cph_id) {
		this.v_cph_id = v_cph_id;
	}

	public Date getV_cph_closeDate() {
		return v_cph_closeDate;
	}

	public void setV_cph_closeDate(Date v_cph_closeDate) {
		this.v_cph_closeDate = v_cph_closeDate;
	}

	public Long getV_cph_lastEvent() {
		return v_cph_lastEvent;
	}

	public void setV_cph_lastEvent(Long v_cph_lastEvent) {
		this.v_cph_lastEvent = v_cph_lastEvent;
	}

	public Long getV_cph_lastStatusId() {
		return v_cph_lastStatusId;
	}

	public void setV_cph_lastStatusId(Long v_cph_lastStatusId) {
		this.v_cph_lastStatusId = v_cph_lastStatusId;
	}

	public Date getV_cph_startDate() {
		return v_cph_startDate;
	}

	public void setV_cph_startDate(Date v_cph_startDate) {
		this.v_cph_startDate = v_cph_startDate;
	}

	public Date getV_cph_regDate() {
		return v_cph_regDate;
	}

	public void setV_cph_regDate(Date v_cph_regDate) {
		this.v_cph_regDate = v_cph_regDate;
	}

	public Long getV_cph_collectionProcedureId() {
		return v_cph_collectionProcedureId;
	}

	public void setV_cph_collectionProcedureId(Long v_cph_collectionProcedureId) {
		this.v_cph_collectionProcedureId = v_cph_collectionProcedureId;
	}



	public Long getV_cph_phaseStatusId() {
		return v_cph_phaseStatusId;
	}

	public void setV_cph_phaseStatusId(Long v_cph_phaseStatusId) {
		this.v_cph_phaseStatusId = v_cph_phaseStatusId;
	}

	public Long getV_cph_phaseTypeId() {
		return v_cph_phaseTypeId;
	}

	public void setV_cph_phaseTypeId(Long v_cph_phaseTypeId) {
		this.v_cph_phaseTypeId = v_cph_phaseTypeId;
	}

	public Long getV_cph_start_total_amount() {
		return v_cph_start_total_amount;
	}

	public void setV_cph_start_total_amount(Long v_cph_start_total_amount) {
		this.v_cph_start_total_amount = v_cph_start_total_amount;
	}

	public Long getV_cph_close_total_amount() {
		return v_cph_close_total_amount;
	}

	public void setV_cph_close_total_amount(Long v_cph_close_total_amount) {
		this.v_cph_close_total_amount = v_cph_close_total_amount;
	}

	public Long getV_loan_id() {
		return v_loan_id;
	}

	public void setV_loan_id(Long v_loan_id) {
		this.v_loan_id = v_loan_id;
	}

	public Double getV_loan_amount() {
		return v_loan_amount;
	}

	public void setV_loan_amount(Double v_loan_amount) {
		this.v_loan_amount = v_loan_amount;
	}

	public String getV_loan_reg_number() {
		return v_loan_reg_number;
	}

	public void setV_loan_reg_number(String v_loan_reg_number) {
		this.v_loan_reg_number = v_loan_reg_number;
	}

	public Date getV_loan_reg_date() {
		return v_loan_reg_date;
	}

	public void setV_loan_reg_date(Date v_loan_reg_date) {
		this.v_loan_reg_date = v_loan_reg_date;
	}

	public Long getV_loan_supervisor_id() {
		return v_loan_supervisor_id;
	}

	public void setV_loan_supervisor_id(Long v_loan_supervisor_id) {
		this.v_loan_supervisor_id = v_loan_supervisor_id;
	}

	public Long getV_loan_fin_group_id() {
		return v_loan_fin_group_id;
	}

	public void setV_loan_fin_group_id(Long v_loan_fin_group_id) {
		this.v_loan_fin_group_id = v_loan_fin_group_id;
	}

	public Long getV_loan_currency_id() {
		return v_loan_currency_id;
	}

	public void setV_loan_currency_id(Long v_loan_currency_id) {
		this.v_loan_currency_id = v_loan_currency_id;
	}

	public Long getV_loan_state_id() {
		return v_loan_state_id;
	}

	public void setV_loan_state_id(Long v_loan_state_id) {
		this.v_loan_state_id = v_loan_state_id;
	}

	public Long getV_loan_type_id() {
		return v_loan_type_id;
	}

	public void setV_loan_type_id(Long v_loan_type_id) {
		this.v_loan_type_id = v_loan_type_id;
	}

	public Long getV_credit_order_type_id() {
		return v_credit_order_type_id;
	}

	public void setV_credit_order_type_id(Long v_credit_order_type_id) {
		this.v_credit_order_type_id = v_credit_order_type_id;
	}

	public String getV_credit_order_reg_number() {
		return v_credit_order_reg_number;
	}

	public void setV_credit_order_reg_number(String v_credit_order_reg_number) {
		this.v_credit_order_reg_number = v_credit_order_reg_number;
	}

	public Date getV_credit_order_reg_date() {
		return v_credit_order_reg_date;
	}

	public void setV_credit_order_reg_date(Date v_credit_order_reg_date) {
		this.v_credit_order_reg_date = v_credit_order_reg_date;
	}

	public String getV_cph_doc_number() {
		return v_cph_doc_number;
	}

	public void setV_cph_doc_number(String v_cph_doc_number) {
		this.v_cph_doc_number = v_cph_doc_number;
	}

	public String getV_cph_result_doc_number() {
		return v_cph_result_doc_number;
	}

	public void setV_cph_result_doc_number(String v_cph_result_doc_number) {
		this.v_cph_result_doc_number = v_cph_result_doc_number;
	}

	public Long getV_cph_group_id() {
		return v_cph_group_id;
	}

	public void setV_cph_group_id(Long v_cph_group_id) {
		this.v_cph_group_id = v_cph_group_id;
	}

	public Long getV_cph_index_id() {
		return v_cph_index_id;
	}

	public void setV_cph_index_id(Long v_cph_index_id) {
		this.v_cph_index_id = v_cph_index_id;
	}

	public Long getV_cph_sub_index_id() {
		return v_cph_sub_index_id;
	}

	public void setV_cph_sub_index_id(Long v_cph_sub_index_id) {
		this.v_cph_sub_index_id = v_cph_sub_index_id;
	}

	public Long getV_cph_paid_total_amount() {
		return v_cph_paid_total_amount;
	}

	public void setV_cph_paid_total_amount(Long v_cph_paid_total_amount) {
		this.v_cph_paid_total_amount = v_cph_paid_total_amount;
	}


	public Date getV_cph_paymentFromDate() {
		return v_cph_paymentFromDate;
	}

	public void setV_cph_paymentFromDate(Date v_cph_paymentFromDate) {
		this.v_cph_paymentFromDate = v_cph_paymentFromDate;
	}

	public String getV_cph_description() {
		return v_cph_description;
	}

	public void setV_cph_description(String v_cph_description) {
		this.v_cph_description = v_cph_description;
	}
}