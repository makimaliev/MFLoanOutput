package kg.gov.mf.loan.output.report.model;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;


public class EntityDocumentReportData extends ReportData
{
	private EntityDocumentReportData Parent                = null;
	private LinkedList<EntityDocumentReportData> ChildDataList = null;

	private short  Level             = 0;

	private String Name                 = "";

	private LinkedHashSet<EntityDocumentView> entityDocumentViews = new LinkedHashSet<EntityDocumentView>(0);



	private Date   onDate         = null;

	private int    entityCount          = 0;
	private int    entityDocumentCount  = 0;
	private int    entityDocumentCompletedCount  = 0;
	private int    entityDocumentNotCompletedCount  = 0;

	private int    entityDocumentApprovedCount  = 0;
	private int    entityDocumentNotApprovedCount  = 0;




	//*************************************************************


	private long v_entity_document_id;
	private String v_entity_document_name;
	private long v_entity_document_approvedBy;
	private Date v_entity_document_approvedDate;
	private String v_entity_document_approvedDescription;
	private long v_entity_document_completedBy;
	private Date v_entity_document_completedDate;
	private String v_entity_document_completedDescription;
	private long v_entity_document_entityDocumentStateId;
	private long v_entity_document_documentTypeId;
	private long v_entity_document_documentPackageId;
	private String v_document_package_name;
	private Date v_document_package_approvedDate;
	private Double v_document_package_approvedRatio;
	private Date v_document_package_completedDate;
	private Double v_document_package_completedRatio;
	private long v_document_package_documentPackageStateId;
	private long v_document_package_documentPackageTypeId;
	private long v_applied_entity_appliedEntityStateId;
	private long v_applied_entity_ownerId;
	private long v_owner_address_id;
	private long v_owner_region_id;
	private long v_owner_district_id;
	private long v_owner_aokmotu_id;
	private long v_owner_village_id;
	private long v_owner_entityId;
	private String v_owner_ownerType;
	private String v_owner_name;
	private long v_ael_id;
	private Date v_ael_listDate;
	private String v_ael_listNumber;
	private long v_ael_appliedEntityListStateId;
	private long v_ael_appliedEntityListTypeId;
	private long v_co_id;
	private Date v_co_regDate;
	private String v_co_regNumber;
	private long v_co_creditOrderStateId;
	private long v_co_creditOrderTypeId;


	public EntityDocumentReportData()
	{
		ChildDataList = new LinkedList<EntityDocumentReportData>();
	}


	public EntityDocumentReportData addChild()
	{
		EntityDocumentReportData ChildData = new EntityDocumentReportData();
		ChildData.setParent(this);
		ChildDataList.add(ChildData);

		return ChildData;
	}


	public EntityDocumentReportData[] getChilds()
	{
		return  (EntityDocumentReportData[])ChildDataList.toArray(new EntityDocumentReportData[ChildDataList.size()]);
	}


	public LinkedList<EntityDocumentReportData> getChildDataList() {
		return ChildDataList;
	}

	public void setChildDataList(LinkedList<EntityDocumentReportData> childDataList) {
		ChildDataList = childDataList;
	}

	public LinkedList<EntityDocumentReportData> getData()
	{
		return ChildDataList;
	}

	public void setData(LinkedList<EntityDocumentReportData> data)
	{
		ChildDataList = data;
	}


	public int getEntityCount() {
		return entityCount;
	}

	public void setEntityCount(int entityCount) {
		this.entityCount = entityCount;
	}

	public int getEntityDocumentCount() {
		return entityDocumentCount;
	}

	public void setEntityDocumentCount(int entityDocumentCount) {
		this.entityDocumentCount = entityDocumentCount;
	}

	public Date getOnDate() {
		return onDate;
	}

	public void setOnDate(Date onDate) {
		this.onDate = onDate;
	}

	public String getName()
	{
		return Name;
	}
	public void setName(String name)
	{
		Name = name;
	}
	public EntityDocumentReportData getParent()
	{
		return Parent;
	}
	public void setParent(EntityDocumentReportData parent)
	{
		Parent = parent;
	}

	public short getLevel() {
		return Level;
	}

	public void setLevel(short level) {
		Level = level;
	}

	public LinkedHashSet<EntityDocumentView> getEntityDocumentViews() {
		return entityDocumentViews;
	}

	public void setEntityDocumentViews(LinkedHashSet<EntityDocumentView> entityDocumentViews) {
		this.entityDocumentViews = entityDocumentViews;
	}

	public long getV_entity_document_id() {
		return v_entity_document_id;
	}

	public void setV_entity_document_id(long v_entity_document_id) {
		this.v_entity_document_id = v_entity_document_id;
	}

	public String getV_entity_document_name() {
		return v_entity_document_name;
	}

	public void setV_entity_document_name(String v_entity_document_name) {
		this.v_entity_document_name = v_entity_document_name;
	}

	public long getV_entity_document_approvedBy() {
		return v_entity_document_approvedBy;
	}

	public void setV_entity_document_approvedBy(long v_entity_document_approvedBy) {
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

	public long getV_entity_document_completedBy() {
		return v_entity_document_completedBy;
	}

	public void setV_entity_document_completedBy(long v_entity_document_completedBy) {
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

	public long getV_entity_document_entityDocumentStateId() {
		return v_entity_document_entityDocumentStateId;
	}

	public void setV_entity_document_entityDocumentStateId(long v_entity_document_entityDocumentStateId) {
		this.v_entity_document_entityDocumentStateId = v_entity_document_entityDocumentStateId;
	}

	public long getV_entity_document_documentTypeId() {
		return v_entity_document_documentTypeId;
	}

	public void setV_entity_document_documentTypeId(long v_entity_document_documentTypeId) {
		this.v_entity_document_documentTypeId = v_entity_document_documentTypeId;
	}

	public long getV_entity_document_documentPackageId() {
		return v_entity_document_documentPackageId;
	}

	public void setV_entity_document_documentPackageId(long v_entity_document_documentPackageId) {
		this.v_entity_document_documentPackageId = v_entity_document_documentPackageId;
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

	public long getV_document_package_documentPackageStateId() {
		return v_document_package_documentPackageStateId;
	}

	public void setV_document_package_documentPackageStateId(long v_document_package_documentPackageStateId) {
		this.v_document_package_documentPackageStateId = v_document_package_documentPackageStateId;
	}

	public long getV_document_package_documentPackageTypeId() {
		return v_document_package_documentPackageTypeId;
	}

	public void setV_document_package_documentPackageTypeId(long v_document_package_documentPackageTypeId) {
		this.v_document_package_documentPackageTypeId = v_document_package_documentPackageTypeId;
	}

	public long getV_applied_entity_appliedEntityStateId() {
		return v_applied_entity_appliedEntityStateId;
	}

	public void setV_applied_entity_appliedEntityStateId(long v_applied_entity_appliedEntityStateId) {
		this.v_applied_entity_appliedEntityStateId = v_applied_entity_appliedEntityStateId;
	}

	public long getV_applied_entity_ownerId() {
		return v_applied_entity_ownerId;
	}

	public void setV_applied_entity_ownerId(long v_applied_entity_ownerId) {
		this.v_applied_entity_ownerId = v_applied_entity_ownerId;
	}

	public long getV_owner_address_id() {
		return v_owner_address_id;
	}

	public void setV_owner_address_id(long v_owner_address_id) {
		this.v_owner_address_id = v_owner_address_id;
	}

	public long getV_owner_region_id() {
		return v_owner_region_id;
	}

	public void setV_owner_region_id(long v_owner_region_id) {
		this.v_owner_region_id = v_owner_region_id;
	}

	public long getV_owner_district_id() {
		return v_owner_district_id;
	}

	public void setV_owner_district_id(long v_owner_district_id) {
		this.v_owner_district_id = v_owner_district_id;
	}

	public long getV_owner_aokmotu_id() {
		return v_owner_aokmotu_id;
	}

	public void setV_owner_aokmotu_id(long v_owner_aokmotu_id) {
		this.v_owner_aokmotu_id = v_owner_aokmotu_id;
	}

	public long getV_owner_village_id() {
		return v_owner_village_id;
	}

	public void setV_owner_village_id(long v_owner_village_id) {
		this.v_owner_village_id = v_owner_village_id;
	}

	public long getV_owner_entityId() {
		return v_owner_entityId;
	}

	public void setV_owner_entityId(long v_owner_entityId) {
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

	public long getV_ael_id() {
		return v_ael_id;
	}

	public void setV_ael_id(long v_ael_id) {
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

	public long getV_ael_appliedEntityListStateId() {
		return v_ael_appliedEntityListStateId;
	}

	public void setV_ael_appliedEntityListStateId(long v_ael_appliedEntityListStateId) {
		this.v_ael_appliedEntityListStateId = v_ael_appliedEntityListStateId;
	}

	public long getV_ael_appliedEntityListTypeId() {
		return v_ael_appliedEntityListTypeId;
	}

	public void setV_ael_appliedEntityListTypeId(long v_ael_appliedEntityListTypeId) {
		this.v_ael_appliedEntityListTypeId = v_ael_appliedEntityListTypeId;
	}

	public long getV_co_id() {
		return v_co_id;
	}

	public void setV_co_id(long v_co_id) {
		this.v_co_id = v_co_id;
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

	public long getV_co_creditOrderStateId() {
		return v_co_creditOrderStateId;
	}

	public void setV_co_creditOrderStateId(long v_co_creditOrderStateId) {
		this.v_co_creditOrderStateId = v_co_creditOrderStateId;
	}

	public long getV_co_creditOrderTypeId() {
		return v_co_creditOrderTypeId;
	}

	public void setV_co_creditOrderTypeId(long v_co_creditOrderTypeId) {
		this.v_co_creditOrderTypeId = v_co_creditOrderTypeId;
	}


	public int getEntityDocumentCompletedCount() {
		return entityDocumentCompletedCount;
	}

	public void setEntityDocumentCompletedCount(int entityDocumentCompletedCount) {
		this.entityDocumentCompletedCount = entityDocumentCompletedCount;
	}

	public int getEntityDocumentNotCompletedCount() {
		return entityDocumentNotCompletedCount;
	}

	public void setEntityDocumentNotCompletedCount(int entityDocumentNotCompletedCount) {
		this.entityDocumentNotCompletedCount = entityDocumentNotCompletedCount;
	}

	public int getEntityDocumentApprovedCount() {
		return entityDocumentApprovedCount;
	}

	public void setEntityDocumentApprovedCount(int entityDocumentApprovedCount) {
		this.entityDocumentApprovedCount = entityDocumentApprovedCount;
	}

	public int getEntityDocumentNotApprovedCount() {
		return entityDocumentNotApprovedCount;
	}

	public void setEntityDocumentNotApprovedCount(int entityDocumentNotApprovedCount) {
		this.entityDocumentNotApprovedCount = entityDocumentNotApprovedCount;
	}
}