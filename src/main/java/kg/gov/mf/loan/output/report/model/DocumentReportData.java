package kg.gov.mf.loan.output.report.model;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;


public class DocumentReportData extends ReportData
{
	private DocumentReportData Parent                = null;
	private LinkedList<DocumentReportData> ChildDataList = null;

	private short  Level             = 0;

	private String Name                 = "";

	private LinkedHashSet<DocumentView> documentViews = new LinkedHashSet<DocumentView>(0);

	//*************************************************************

	private Date   onDate         = null;


	private long   documentID            = 0;
	private String documentDescription   = "";
	private Date   documentDueDate       = null;
	private String   documentStateID       = "";
	private String documentIndexNumber   = "";
	private long   documentOwnerID       = 0;

	private String documentReceiverRegisteredNumber   = "";
	private Date   documentReceiverRegisteredDate      = null;

	private String documentSenderRegisteredNumber   = "";
	private Date   documentSenderRegisteredDate      = null;

	private String documentTitle   = "";

	private long   documentTypeID            		= 0;
	private long   documentSubTypeID            	= 0;

	private long   documentReceiverExecutorID       = 0;
	private long   documentReceiverResponsibleID    = 0;

	private long   documentSenderExecutorID         = 0;
	private long   documentSenderResponsibleID      = 0;

	private String   documentReceiverExecutorName = ""   ;
	private String   documentReceiverResponsibleName     = "";

	private String   documentSenderExecutorName = ""   ;
	private String   documentSenderResponsibleName     = "";

	private long   documentUserID            		= 0;
	private long   documentDepartmentID            	= 0;

	private int    documentCount         = 0;
	private int    Count         = 0;


	public DocumentReportData()
	{
		ChildDataList = new LinkedList<DocumentReportData>();
	}


	public DocumentReportData addChild()
	{
		DocumentReportData ChildData = new DocumentReportData();
		ChildData.setParent(this);
		ChildDataList.add(ChildData);

		return ChildData;
	}


	public DocumentReportData[] getChilds()
	{
		return  (DocumentReportData[])ChildDataList.toArray(new DocumentReportData[ChildDataList.size()]);
	}


	public LinkedList<DocumentReportData> getChildDataList() {
		return ChildDataList;
	}

	public void setChildDataList(LinkedList<DocumentReportData> childDataList) {
		ChildDataList = childDataList;
	}

	public LinkedList<DocumentReportData> getData()
	{
		return ChildDataList;
	}


	public void setData(LinkedList<DocumentReportData> data)
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
	public DocumentReportData getParent()
	{
		return Parent;
	}
	public void setParent(DocumentReportData parent)
	{
		Parent = parent;
	}

	public short getLevel() {
		return Level;
	}

	public void setLevel(short level) {
		Level = level;
	}


	public LinkedHashSet<DocumentView> getDocumentViews() {
		return documentViews;
	}

	public void setDocumentViews(LinkedHashSet<DocumentView> documentViews) {
		this.documentViews = documentViews;
	}

	public Date getOnDate() {
		return onDate;
	}

	public void setOnDate(Date onDate) {
		this.onDate = onDate;
	}


	public long getDocumentID() {
		return documentID;
	}

	public void setDocumentID(long documentID) {
		this.documentID = documentID;
	}

	public String getDocumentDescription() {
		return documentDescription;
	}

	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}

	public Date getDocumentDueDate() {
		return documentDueDate;
	}

	public void setDocumentDueDate(Date documentDueDate) {
		this.documentDueDate = documentDueDate;
	}

	public String getDocumentStateID() {
		return documentStateID;
	}

	public void setDocumentStateID(String documentStateID) {
		this.documentStateID = documentStateID;
	}

	public String getDocumentIndexNumber() {
		return documentIndexNumber;
	}

	public void setDocumentIndexNumber(String documentIndexNumber) {
		this.documentIndexNumber = documentIndexNumber;
	}

	public long getDocumentOwnerID() {
		return documentOwnerID;
	}

	public void setDocumentOwnerID(long documentOwnerID) {
		this.documentOwnerID = documentOwnerID;
	}

	public String getDocumentReceiverRegisteredNumber() {
		return documentReceiverRegisteredNumber;
	}

	public void setDocumentReceiverRegisteredNumber(String documentReceiverRegisteredNumber) {
		this.documentReceiverRegisteredNumber = documentReceiverRegisteredNumber;
	}

	public Date getDocumentReceiverRegisteredDate() {
		return documentReceiverRegisteredDate;
	}

	public void setDocumentReceiverRegisteredDate(Date documentReceiverRegisteredDate) {
		this.documentReceiverRegisteredDate = documentReceiverRegisteredDate;
	}

	public String getDocumentSenderRegisteredNumber() {
		return documentSenderRegisteredNumber;
	}

	public void setDocumentSenderRegisteredNumber(String documentSenderRegisteredNumber) {
		this.documentSenderRegisteredNumber = documentSenderRegisteredNumber;
	}

	public Date getDocumentSenderRegisteredDate() {
		return documentSenderRegisteredDate;
	}

	public void setDocumentSenderRegisteredDate(Date documentSenderRegisteredDate) {
		this.documentSenderRegisteredDate = documentSenderRegisteredDate;
	}

	public String getDocumentTitle() {
		return documentTitle;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	public long getDocumentTypeID() {
		return documentTypeID;
	}

	public void setDocumentTypeID(long documentTypeID) {
		this.documentTypeID = documentTypeID;
	}

	public long getDocumentSubTypeID() {
		return documentSubTypeID;
	}

	public void setDocumentSubTypeID(long documentSubTypeID) {
		this.documentSubTypeID = documentSubTypeID;
	}

	public long getDocumentReceiverExecutorID() {
		return documentReceiverExecutorID;
	}

	public void setDocumentReceiverExecutorID(long documentReceiverExecutorID) {
		this.documentReceiverExecutorID = documentReceiverExecutorID;
	}

	public long getDocumentReceiverResponsibleID() {
		return documentReceiverResponsibleID;
	}

	public void setDocumentReceiverResponsibleID(long documentReceiverResponsibleID) {
		this.documentReceiverResponsibleID = documentReceiverResponsibleID;
	}

	public long getDocumentSenderExecutorID() {
		return documentSenderExecutorID;
	}

	public void setDocumentSenderExecutorID(long documentSenderExecutorID) {
		this.documentSenderExecutorID = documentSenderExecutorID;
	}

	public long getDocumentSenderResponsibleID() {
		return documentSenderResponsibleID;
	}

	public void setDocumentSenderResponsibleID(long documentSenderResponsibleID) {
		this.documentSenderResponsibleID = documentSenderResponsibleID;
	}

	public String getDocumentReceiverExecutorName() {
		return documentReceiverExecutorName;
	}

	public void setDocumentReceiverExecutorName(String documentReceiverExecutorName) {
		this.documentReceiverExecutorName = documentReceiverExecutorName;
	}

	public String getDocumentReceiverResponsibleName() {
		return documentReceiverResponsibleName;
	}

	public void setDocumentReceiverResponsibleName(String documentReceiverResponsibleName) {
		this.documentReceiverResponsibleName = documentReceiverResponsibleName;
	}

	public String getDocumentSenderExecutorName() {
		return documentSenderExecutorName;
	}

	public void setDocumentSenderExecutorName(String documentSenderExecutorName) {
		this.documentSenderExecutorName = documentSenderExecutorName;
	}

	public String getDocumentSenderResponsibleName() {
		return documentSenderResponsibleName;
	}

	public void setDocumentSenderResponsibleName(String documentSenderResponsibleName) {
		this.documentSenderResponsibleName = documentSenderResponsibleName;
	}

	public long getDocumentUserID() {
		return documentUserID;
	}

	public void setDocumentUserID(long documentUserID) {
		this.documentUserID = documentUserID;
	}

	public long getDocumentDepartmentID() {
		return documentDepartmentID;
	}

	public void setDocumentDepartmentID(long documentDepartmentID) {
		this.documentDepartmentID = documentDepartmentID;
	}

	public int getDocumentCount() {
		return documentCount;
	}

	public void setDocumentCount(int documentCount) {
		this.documentCount = documentCount;
	}

	public int getCount() {
		return Count;
	}

	public void setCount(int count) {
		Count = count;
	}
}