package kg.gov.mf.loan.output.report.model;

import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="entity_document_view")
@Immutable
public class EntityDocumentView extends  ReportView
{


	@Id
	@Column
	private Long v_entity_document_id;

	@Column
	private String v_entity_document_name;


	@Column
	private Long v_entity_document_approvedBy;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_entity_document_approvedDate;

	@Column
	private String v_entity_document_approvedDescription;

	@Column
	private Long v_entity_document_completedBy;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_entity_document_completedDate;

	@Column
	private String v_entity_document_completedDescription;


	@Column
	private Long v_entity_document_entityDocumentStateId;

	@Column
	private Long v_entity_document_documentTypeId;

	@Column
	private Long v_entity_document_documentPackageId;

    @Column
    private Long v_document_package_id;


    @Column
	private String v_document_package_name;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_document_package_approvedDate;

	@Column
	private Double v_document_package_approvedRatio;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_document_package_completedDate;

	@Column
	private Double v_document_package_completedRatio;

	@Column
	private Long v_document_package_documentPackageStateId;

	@Column
	private Long v_document_package_documentPackageTypeId;

	@Column
	private Long v_applied_entity_appliedEntityStateId;


	@Column
	private Long v_applied_entity_ownerId;

    @Column
    private Long v_applied_entity_id;


    @Column
	private Long v_owner_address_id;

	@Column
	private Long v_owner_region_id;


	@Column
	private Long v_owner_district_id;


	@Column
	private Long v_owner_aokmotu_id;


	@Column
	private Long v_owner_village_id;

	@Column
	private Long v_owner_entityId;

	@Column
	private String v_owner_ownerType;

	@Column
	private String v_owner_name;


	@Column
	private Long v_ael_id;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_ael_listDate;

	@Column
	private String v_ael_listNumber;


	@Column
	private Long v_ael_appliedEntityListStateId;


	@Column
	private Long v_ael_appliedEntityListTypeId;


	@Column
	private Long v_credit_order_id;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_co_regDate;

	@Column
	private String v_co_regNumber;

	@Column
	private Long v_co_creditOrderStateId;

	@Column
	private Long v_co_creditOrderTypeId;


	@Column
	private Long v_entity_document_completed_count;

	@Column
	private Long v_entity_document_not_completed_count;

	@Column
	private Long v_entity_document_approved_count;

	@Column
	private Long v_entity_document_not_approved_count;


	public Long getV_entity_document_id() {
		return v_entity_document_id;
	}

	public void setV_entity_document_id(Long v_entity_document_id) {
		this.v_entity_document_id = v_entity_document_id;
	}

	public String getV_entity_document_name() {
		return v_entity_document_name;
	}

	public void setV_entity_document_name(String v_entity_document_name) {
		this.v_entity_document_name = v_entity_document_name;
	}

	public Long getV_entity_document_approvedBy() {
		return v_entity_document_approvedBy;
	}

	public void setV_entity_document_approvedBy(Long v_entity_document_approvedBy) {
		this.v_entity_document_approvedBy = v_entity_document_approvedBy;
	}

	public Date getV_entity_document_approvedDate() {
		return v_entity_document_approvedDate;
	}

	public void setV_entity_document_approvedDate(Date v_entity_document_approvedDate) {
		this.v_entity_document_approvedDate = v_entity_document_approvedDate;
	}

	public String getV_entity_document_approvedDescription() {
		return v_entity_document_approvedDescription;
	}

	public void setV_entity_document_approvedDescription(String v_entity_document_approvedDescription) {
		this.v_entity_document_approvedDescription = v_entity_document_approvedDescription;
	}

	public Long getV_entity_document_completedBy() {
		return v_entity_document_completedBy;
	}

	public void setV_entity_document_completedBy(Long v_entity_document_completedBy) {
		this.v_entity_document_completedBy = v_entity_document_completedBy;
	}

	public Date getV_entity_document_completedDate() {
		return v_entity_document_completedDate;
	}

	public void setV_entity_document_completedDate(Date v_entity_document_completedDate) {
		this.v_entity_document_completedDate = v_entity_document_completedDate;
	}

	public String getV_entity_document_completedDescription() {
		return v_entity_document_completedDescription;
	}

	public void setV_entity_document_completedDescription(String v_entity_document_completedDescription) {
		this.v_entity_document_completedDescription = v_entity_document_completedDescription;
	}

	public Long getV_entity_document_entityDocumentStateId() {
		return v_entity_document_entityDocumentStateId;
	}

	public void setV_entity_document_entityDocumentStateId(Long v_entity_document_entityDocumentStateId) {
		this.v_entity_document_entityDocumentStateId = v_entity_document_entityDocumentStateId;
	}

	public Long getV_entity_document_documentTypeId() {
		return v_entity_document_documentTypeId;
	}

	public void setV_entity_document_documentTypeId(Long v_entity_document_documentTypeId) {
		this.v_entity_document_documentTypeId = v_entity_document_documentTypeId;
	}

	public Long getV_entity_document_documentPackageId() {
		return v_entity_document_documentPackageId;
	}

	public void setV_entity_document_documentPackageId(Long v_entity_document_documentPackageId) {
		this.v_entity_document_documentPackageId = v_entity_document_documentPackageId;
	}

	public Long getV_document_package_id() {
		return v_document_package_id;
	}

	public void setV_document_package_id(Long v_document_package_id) {
		this.v_document_package_id = v_document_package_id;
	}

	public String getV_document_package_name() {
		return v_document_package_name;
	}

	public void setV_document_package_name(String v_document_package_name) {
		this.v_document_package_name = v_document_package_name;
	}

	public Date getV_document_package_approvedDate() {
		return v_document_package_approvedDate;
	}

	public void setV_document_package_approvedDate(Date v_document_package_approvedDate) {
		this.v_document_package_approvedDate = v_document_package_approvedDate;
	}

	public Double getV_document_package_approvedRatio() {
		return v_document_package_approvedRatio;
	}

	public void setV_document_package_approvedRatio(Double v_document_package_approvedRatio) {
		this.v_document_package_approvedRatio = v_document_package_approvedRatio;
	}

	public Date getV_document_package_completedDate() {
		return v_document_package_completedDate;
	}

	public void setV_document_package_completedDate(Date v_document_package_completedDate) {
		this.v_document_package_completedDate = v_document_package_completedDate;
	}

	public Double getV_document_package_completedRatio() {
		return v_document_package_completedRatio;
	}

	public void setV_document_package_completedRatio(Double v_document_package_completedRatio) {
		this.v_document_package_completedRatio = v_document_package_completedRatio;
	}

	public Long getV_document_package_documentPackageStateId() {
		return v_document_package_documentPackageStateId;
	}

	public void setV_document_package_documentPackageStateId(Long v_document_package_documentPackageStateId) {
		this.v_document_package_documentPackageStateId = v_document_package_documentPackageStateId;
	}

	public Long getV_document_package_documentPackageTypeId() {
		return v_document_package_documentPackageTypeId;
	}

	public void setV_document_package_documentPackageTypeId(Long v_document_package_documentPackageTypeId) {
		this.v_document_package_documentPackageTypeId = v_document_package_documentPackageTypeId;
	}

	public Long getV_applied_entity_appliedEntityStateId() {
		return v_applied_entity_appliedEntityStateId;
	}

	public void setV_applied_entity_appliedEntityStateId(Long v_applied_entity_appliedEntityStateId) {
		this.v_applied_entity_appliedEntityStateId = v_applied_entity_appliedEntityStateId;
	}

	public Long getV_applied_entity_ownerId() {
		return v_applied_entity_ownerId;
	}

	public void setV_applied_entity_ownerId(Long v_applied_entity_ownerId) {
		this.v_applied_entity_ownerId = v_applied_entity_ownerId;
	}

	public Long getV_applied_entity_id() {
		return v_applied_entity_id;
	}

	public void setV_applied_entity_id(Long v_applied_entity_id) {
		this.v_applied_entity_id = v_applied_entity_id;
	}

	public Long getV_owner_address_id() {
		return v_owner_address_id;
	}

	public void setV_owner_address_id(Long v_owner_address_id) {
		this.v_owner_address_id = v_owner_address_id;
	}

	public Long getV_owner_region_id() {
		return v_owner_region_id;
	}

	public void setV_owner_region_id(Long v_owner_region_id) {
		this.v_owner_region_id = v_owner_region_id;
	}

	public Long getV_owner_district_id() {
		return v_owner_district_id;
	}

	public void setV_owner_district_id(Long v_owner_district_id) {
		this.v_owner_district_id = v_owner_district_id;
	}

	public Long getV_owner_aokmotu_id() {
		return v_owner_aokmotu_id;
	}

	public void setV_owner_aokmotu_id(Long v_owner_aokmotu_id) {
		this.v_owner_aokmotu_id = v_owner_aokmotu_id;
	}

	public Long getV_owner_village_id() {
		return v_owner_village_id;
	}

	public void setV_owner_village_id(Long v_owner_village_id) {
		this.v_owner_village_id = v_owner_village_id;
	}

	public Long getV_owner_entityId() {
		return v_owner_entityId;
	}

	public void setV_owner_entityId(Long v_owner_entityId) {
		this.v_owner_entityId = v_owner_entityId;
	}

	public String getV_owner_ownerType() {
		return v_owner_ownerType;
	}

	public void setV_owner_ownerType(String v_owner_ownerType) {
		this.v_owner_ownerType = v_owner_ownerType;
	}

	public String getV_owner_name() {
		return v_owner_name;
	}

	public void setV_owner_name(String v_owner_name) {
		this.v_owner_name = v_owner_name;
	}

	public Long getV_ael_id() {
		return v_ael_id;
	}

	public void setV_ael_id(Long v_ael_id) {
		this.v_ael_id = v_ael_id;
	}

	public Date getV_ael_listDate() {
		return v_ael_listDate;
	}

	public void setV_ael_listDate(Date v_ael_listDate) {
		this.v_ael_listDate = v_ael_listDate;
	}

	public String getV_ael_listNumber() {
		return v_ael_listNumber;
	}

	public void setV_ael_listNumber(String v_ael_listNumber) {
		this.v_ael_listNumber = v_ael_listNumber;
	}

	public Long getV_ael_appliedEntityListStateId() {
		return v_ael_appliedEntityListStateId;
	}

	public void setV_ael_appliedEntityListStateId(Long v_ael_appliedEntityListStateId) {
		this.v_ael_appliedEntityListStateId = v_ael_appliedEntityListStateId;
	}

	public Long getV_ael_appliedEntityListTypeId() {
		return v_ael_appliedEntityListTypeId;
	}

	public void setV_ael_appliedEntityListTypeId(Long v_ael_appliedEntityListTypeId) {
		this.v_ael_appliedEntityListTypeId = v_ael_appliedEntityListTypeId;
	}

	public Long getV_credit_order_id() {
		return v_credit_order_id;
	}

	public void setV_credit_order_id(Long v_credit_order_id) {
		this.v_credit_order_id = v_credit_order_id;
	}

	public Date getV_co_regDate() {
		return v_co_regDate;
	}

	public void setV_co_regDate(Date v_co_regDate) {
		this.v_co_regDate = v_co_regDate;
	}

	public String getV_co_regNumber() {
		return v_co_regNumber;
	}

	public void setV_co_regNumber(String v_co_regNumber) {
		this.v_co_regNumber = v_co_regNumber;
	}

	public Long getV_co_creditOrderStateId() {
		return v_co_creditOrderStateId;
	}

	public void setV_co_creditOrderStateId(Long v_co_creditOrderStateId) {
		this.v_co_creditOrderStateId = v_co_creditOrderStateId;
	}

	public Long getV_co_creditOrderTypeId() {
		return v_co_creditOrderTypeId;
	}

	public void setV_co_creditOrderTypeId(Long v_co_creditOrderTypeId) {
		this.v_co_creditOrderTypeId = v_co_creditOrderTypeId;
	}

	public Long getV_entity_document_completed_count() {
		return v_entity_document_completed_count;
	}

	public void setV_entity_document_completed_count(Long v_entity_document_completed_count) {
		this.v_entity_document_completed_count = v_entity_document_completed_count;
	}

	public Long getV_entity_document_not_completed_count() {
		return v_entity_document_not_completed_count;
	}

	public void setV_entity_document_not_completed_count(Long v_entity_document_not_completed_count) {
		this.v_entity_document_not_completed_count = v_entity_document_not_completed_count;
	}

	public Long getV_entity_document_approved_count() {
		return v_entity_document_approved_count;
	}

	public void setV_entity_document_approved_count(Long v_entity_document_approved_count) {
		this.v_entity_document_approved_count = v_entity_document_approved_count;
	}

	public Long getV_entity_document_not_approved_count() {
		return v_entity_document_not_approved_count;
	}

	public void setV_entity_document_not_approved_count(Long v_entity_document_not_approved_count) {
		this.v_entity_document_not_approved_count = v_entity_document_not_approved_count;
	}
}