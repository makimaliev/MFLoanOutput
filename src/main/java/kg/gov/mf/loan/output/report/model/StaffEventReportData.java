package kg.gov.mf.loan.output.report.model;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;


public class StaffEventReportData extends ReportData
{
	private StaffEventReportData Parent                = null;
	private LinkedList<StaffEventReportData> ChildDataList = null;

	private short  Level             = 0;

	private String Name                 = "";

	private LinkedHashSet<StaffEventView> staffEventViews = new LinkedHashSet<StaffEventView>(0);

	//*************************************************************

	private Date   onDate         = null;

	private String staffName        = "";

	private long   staffOrganizationID            = 0;
	private long   staffDepartmentID            = 0;
	private long   staffPositionID            = 0;

	private long   staffRegionID            = 0;
	private long   staffDistrictID            = 0;

	private String staffLine        = "";
	private String staffContactName        = "";


	private Date   staffIdDocDate         = null;

	private String staffIdDocName        = "";
	private String staffIdDocNumber        = "";
	private String staffIdDocPin        = "";

	private long   staffIdDocGivenByID            = 0;

	private long   staffIdDocTypeID            = 0;

	private int    Count         = 0;

	private String staffEventName      = "";
	private Date   staffEventDate          = null;

	private int    staffEventCount         = 0;

	private long   staffEventID            = 0;
	private long   staffEventTypeID            = 0;


	public StaffEventReportData()
	{
		ChildDataList = new LinkedList<StaffEventReportData>();
	}


	public StaffEventReportData addChild()
	{
		StaffEventReportData ChildData = new StaffEventReportData();
		ChildData.setParent(this);
		ChildDataList.add(ChildData);

		return ChildData;
	}


	public StaffEventReportData[] getChilds()
	{
		return  (StaffEventReportData[])ChildDataList.toArray(new StaffEventReportData[ChildDataList.size()]);
	}


	public LinkedList<StaffEventReportData> getChildDataList() {
		return ChildDataList;
	}

	public void setChildDataList(LinkedList<StaffEventReportData> childDataList) {
		ChildDataList = childDataList;
	}

	public LinkedList<StaffEventReportData> getData()
	{
		return ChildDataList;
	}


	public void setData(LinkedList<StaffEventReportData> data)
	{
		ChildDataList = data;
	}


	public String getName()
	{
		return Name;
	}
	public void setName(String name)
	{
		Name = name;
	}
	public StaffEventReportData getParent()
	{
		return Parent;
	}
	public void setParent(StaffEventReportData parent)
	{
		Parent = parent;
	}

	public short getLevel() {
		return Level;
	}

	public void setLevel(short level) {
		Level = level;
	}


	public LinkedHashSet<StaffEventView> getStaffEventViews() {
		return staffEventViews;
	}

	public void setStaffEventViews(LinkedHashSet<StaffEventView> staffEventViews) {
		this.staffEventViews = staffEventViews;
	}

	public Date getOnDate() {
		return onDate;
	}

	public void setOnDate(Date onDate) {
		this.onDate = onDate;
	}


	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public long getStaffOrganizationID() {
		return staffOrganizationID;
	}

	public void setStaffOrganizationID(long staffOrganizationID) {
		this.staffOrganizationID = staffOrganizationID;
	}

	public long getStaffDepartmentID() {
		return staffDepartmentID;
	}

	public void setStaffDepartmentID(long staffDepartmentID) {
		this.staffDepartmentID = staffDepartmentID;
	}

	public long getStaffPositionID() {
		return staffPositionID;
	}

	public void setStaffPositionID(long staffPositionID) {
		this.staffPositionID = staffPositionID;
	}

	public long getStaffRegionID() {
		return staffRegionID;
	}

	public void setStaffRegionID(long staffRegionID) {
		this.staffRegionID = staffRegionID;
	}

	public long getStaffDistrictID() {
		return staffDistrictID;
	}

	public void setStaffDistrictID(long staffDistrictID) {
		this.staffDistrictID = staffDistrictID;
	}

	public String getStaffLine() {
		return staffLine;
	}

	public void setStaffLine(String staffLine) {
		this.staffLine = staffLine;
	}

	public String getStaffContactName() {
		return staffContactName;
	}

	public void setStaffContactName(String staffContactName) {
		this.staffContactName = staffContactName;
	}

	public Date getStaffIdDocDate() {
		return staffIdDocDate;
	}

	public void setStaffIdDocDate(Date staffIdDocDate) {
		this.staffIdDocDate = staffIdDocDate;
	}

	public String getStaffIdDocName() {
		return staffIdDocName;
	}

	public void setStaffIdDocName(String staffIdDocName) {
		this.staffIdDocName = staffIdDocName;
	}

	public String getStaffIdDocNumber() {
		return staffIdDocNumber;
	}

	public void setStaffIdDocNumber(String staffIdDocNumber) {
		this.staffIdDocNumber = staffIdDocNumber;
	}

	public String getStaffIdDocPin() {
		return staffIdDocPin;
	}

	public void setStaffIdDocPin(String staffIdDocPin) {
		this.staffIdDocPin = staffIdDocPin;
	}

	public long getStaffIdDocGivenByID() {
		return staffIdDocGivenByID;
	}

	public void setStaffIdDocGivenByID(long staffIdDocGivenByID) {
		this.staffIdDocGivenByID = staffIdDocGivenByID;
	}

	public long getStaffIdDocTypeID() {
		return staffIdDocTypeID;
	}

	public void setStaffIdDocTypeID(long staffIdDocTypeID) {
		this.staffIdDocTypeID = staffIdDocTypeID;
	}

	public String getStaffEventName() {
		return staffEventName;
	}

	public void setStaffEventName(String staffEventName) {
		this.staffEventName = staffEventName;
	}

	public Date getStaffEventDate() {
		return staffEventDate;
	}

	public void setStaffEventDate(Date staffEventDate) {
		this.staffEventDate = staffEventDate;
	}

	public int getStaffEventCount() {
		return staffEventCount;
	}

	public void setStaffEventCount(int staffEventCount) {
		this.staffEventCount = staffEventCount;
	}

	public long getStaffEventID() {
		return staffEventID;
	}

	public void setStaffEventID(long staffEventID) {
		this.staffEventID = staffEventID;
	}

	public long getStaffEventTypeID() {
		return staffEventTypeID;
	}

	public void setStaffEventTypeID(long staffEventTypeID) {
		this.staffEventTypeID = staffEventTypeID;
	}

	public int getCount() {
		return Count;
	}

	public void setCount(int count) {
		Count = count;
	}
}