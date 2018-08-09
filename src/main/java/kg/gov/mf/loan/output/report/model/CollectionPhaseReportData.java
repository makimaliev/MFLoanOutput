package kg.gov.mf.loan.output.report.model;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;


public class CollectionPhaseReportData extends ReportData
{
	private CollectionPhaseReportData Parent                = null;
	private LinkedList<CollectionPhaseReportData> ChildDataList = null;

	private short  Level             = 0;

	private String Name                 = "";

	private LinkedHashSet<CollectionPhaseView> collectionPhaseView = new LinkedHashSet<CollectionPhaseView>(0);




	//*************************************************************

	private Date   onDate         = null;

	private String loanRegNumber        = "";
	private String orderRegNumber       = "";
	private Date   loanRegDate          = null;
	private Date   loanLastDate         = null;
	private int    Count                = 0;
	private int    DetailsCount         = 0;
	private long   ID                   = 0;
	private short  Currency             = 1;
	private double ExchangeRate         = 1;
	private double loanAmount           = 0;
	private double TotalDisbursment     = 0;
	private double TotalPaid         	= 0;
	private double RemainingSum   		= 0;
	private double RemainingPrincipal 	= 0;
	private double RemainingInterest  	= 0;
	private double RemainingPenalty 	= 0;

	private double OverdueAll      		= 0;
	private double OverduePrincipal     = 0;
	private double OverdueInterest      = 0;
	private double OverduePenalty      	= 0;


    private int    phaseCount         = 0;
    private int    resultCount         = 0;

	private short  loanType    			= 0;

	private double totalPaidInCurrency    = 0;

	private String details     			= "";

	private long   collection_procedure_id                   = 0;

	private String collection_procedure_name     			= "";

	private Date   collection_procedure_start_date          = null;
	private Date   collection_procedure_close_date          = null;

	private long   collection_procedure_status_id                   = 0;

	private long   collection_procedure_type_id                   = 0;

	private long   collection_last_phase_id                   = 0;

	private long   collection_last_phase_status_id                   = 0;




	private long   collection_phase_id                   = 0;

	private String collection_phase_name     			= "";

	private String collection_phase_type_name     			= "";

	private Date   collection_phase_start_date          = null;
	private Date   collection_phase_close_date          = null;

	private long   collection_phase_status_id                   = 0;

	private long   collection_phase_type_id                   = 0;

	private long   collection_last_event_id                   = 0;

	private long   collection_last_event_status_id                   = 0;

	private  double collection_phase_start_total_amount = 0;

	private  double collection_phase_close_total_amount = 0;

	public CollectionPhaseReportData()
	{
		ChildDataList = new LinkedList<CollectionPhaseReportData>();
	}


	public CollectionPhaseReportData addChild()
	{
		CollectionPhaseReportData ChildData = new CollectionPhaseReportData();
		ChildData.setParent(this);
		ChildDataList.add(ChildData);

		return ChildData;
	}


	public CollectionPhaseReportData[] getChilds()
	{
		return  (CollectionPhaseReportData[])ChildDataList.toArray(new CollectionPhaseReportData[ChildDataList.size()]);
	}


	public LinkedList<CollectionPhaseReportData> getChildDataList() {
		return ChildDataList;
	}

	public void setChildDataList(LinkedList<CollectionPhaseReportData> childDataList) {
		ChildDataList = childDataList;
	}

	public LinkedList<CollectionPhaseReportData> getData()
	{
		return ChildDataList;
	}


	public void setData(LinkedList<CollectionPhaseReportData> data)
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
	public CollectionPhaseReportData getParent()
	{
		return Parent;
	}
	public void setParent(CollectionPhaseReportData parent)
	{
		Parent = parent;
	}

	public short getLevel() {
		return Level;
	}

	public void setLevel(short level) {
		Level = level;
	}


	public LinkedHashSet<CollectionPhaseView> getCollectionPhaseViews() {
		return collectionPhaseView;
	}

	public void setCollectionPhaseViews(LinkedHashSet<CollectionPhaseView> collectionPhaseView) {
		this.collectionPhaseView = collectionPhaseView;
	}

	public LinkedHashSet<CollectionPhaseView> getCollectionPhaseView() {
		return collectionPhaseView;
	}

	public void setCollectionPhaseView(LinkedHashSet<CollectionPhaseView> collectionPhaseView) {
		this.collectionPhaseView = collectionPhaseView;
	}

	public Date getOnDate() {
		return onDate;
	}

	public void setOnDate(Date onDate) {
		this.onDate = onDate;
	}

	public String getLoanRegNumber() {
		return loanRegNumber;
	}

	public void setLoanRegNumber(String loanRegNumber) {
		this.loanRegNumber = loanRegNumber;
	}

	public String getOrderRegNumber() {
		return orderRegNumber;
	}

	public void setOrderRegNumber(String orderRegNumber) {
		this.orderRegNumber = orderRegNumber;
	}

	public Date getLoanRegDate() {
		return loanRegDate;
	}

	public void setLoanRegDate(Date loanRegDate) {
		this.loanRegDate = loanRegDate;
	}

	public Date getLoanLastDate() {
		return loanLastDate;
	}

	public void setLoanLastDate(Date loanLastDate) {
		this.loanLastDate = loanLastDate;
	}

	public int getCount() {
		return Count;
	}

	public void setCount(int count) {
		Count = count;
	}

	public int getDetailsCount() {
		return DetailsCount;
	}

	public void setDetailsCount(int detailsCount) {
		DetailsCount = detailsCount;
	}

	public long getID() {
		return ID;
	}

	public void setID(long ID) {
		this.ID = ID;
	}

	public short getCurrency() {
		return Currency;
	}

	public void setCurrency(short currency) {
		Currency = currency;
	}

	public double getExchangeRate() {
		return ExchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		ExchangeRate = exchangeRate;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public double getTotalDisbursment() {
		return TotalDisbursment;
	}

	public void setTotalDisbursment(double totalDisbursment) {
		TotalDisbursment = totalDisbursment;
	}

	public double getTotalPaid() {
		return TotalPaid;
	}

	public void setTotalPaid(double totalPaid) {
		TotalPaid = totalPaid;
	}

	public double getRemainingSum() {
		return RemainingSum;
	}

	public void setRemainingSum(double remainingSum) {
		RemainingSum = remainingSum;
	}

	public double getRemainingPrincipal() {
		return RemainingPrincipal;
	}

	public void setRemainingPrincipal(double remainingPrincipal) {
		RemainingPrincipal = remainingPrincipal;
	}

	public double getRemainingInterest() {
		return RemainingInterest;
	}

	public void setRemainingInterest(double remainingInterest) {
		RemainingInterest = remainingInterest;
	}

	public double getRemainingPenalty() {
		return RemainingPenalty;
	}

	public void setRemainingPenalty(double remainingPenalty) {
		RemainingPenalty = remainingPenalty;
	}

	public double getOverdueAll() {
		return OverdueAll;
	}

	public void setOverdueAll(double overdueAll) {
		OverdueAll = overdueAll;
	}

	public double getOverduePrincipal() {
		return OverduePrincipal;
	}

	public void setOverduePrincipal(double overduePrincipal) {
		OverduePrincipal = overduePrincipal;
	}

	public double getOverdueInterest() {
		return OverdueInterest;
	}

	public void setOverdueInterest(double overdueInterest) {
		OverdueInterest = overdueInterest;
	}

	public double getOverduePenalty() {
		return OverduePenalty;
	}

	public void setOverduePenalty(double overduePenalty) {
		OverduePenalty = overduePenalty;
	}

	public short getLoanType() {
		return loanType;
	}

	public void setLoanType(short loanType) {
		this.loanType = loanType;
	}

	public double getTotalPaidInCurrency() {
		return totalPaidInCurrency;
	}

	public void setTotalPaidInCurrency(double totalPaidInCurrency) {
		this.totalPaidInCurrency = totalPaidInCurrency;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public long getCollection_procedure_id() {
		return collection_procedure_id;
	}

	public void setCollection_procedure_id(long collection_procedure_id) {
		this.collection_procedure_id = collection_procedure_id;
	}

	public String getCollection_procedure_name() {
		return collection_procedure_name;
	}

	public void setCollection_procedure_name(String collection_procedure_name) {
		this.collection_procedure_name = collection_procedure_name;
	}

	public Date getCollection_procedure_start_date() {
		return collection_procedure_start_date;
	}

	public void setCollection_procedure_start_date(Date collection_procedure_start_date) {
		this.collection_procedure_start_date = collection_procedure_start_date;
	}

	public Date getCollection_procedure_close_date() {
		return collection_procedure_close_date;
	}

	public void setCollection_procedure_close_date(Date collection_procedure_close_date) {
		this.collection_procedure_close_date = collection_procedure_close_date;
	}

	public long getCollection_procedure_status_id() {
		return collection_procedure_status_id;
	}

	public void setCollection_procedure_status_id(long collection_procedure_status_id) {
		this.collection_procedure_status_id = collection_procedure_status_id;
	}

	public long getCollection_procedure_type_id() {
		return collection_procedure_type_id;
	}

	public void setCollection_procedure_type_id(long collection_procedure_type_id) {
		this.collection_procedure_type_id = collection_procedure_type_id;
	}

	public long getCollection_last_phase_id() {
		return collection_last_phase_id;
	}

	public void setCollection_last_phase_id(long collection_last_phase_id) {
		this.collection_last_phase_id = collection_last_phase_id;
	}

	public long getCollection_last_phase_status_id() {
		return collection_last_phase_status_id;
	}

	public void setCollection_last_phase_status_id(long collection_last_phase_status_id) {
		this.collection_last_phase_status_id = collection_last_phase_status_id;
	}

	public long getCollection_phase_id() {
		return collection_phase_id;
	}

	public void setCollection_phase_id(long collection_phase_id) {
		this.collection_phase_id = collection_phase_id;
	}

	public String getCollection_phase_name() {
		return collection_phase_name;
	}

	public void setCollection_phase_name(String collection_phase_name) {
		this.collection_phase_name = collection_phase_name;
	}

	public Date getCollection_phase_start_date() {
		return collection_phase_start_date;
	}

	public void setCollection_phase_start_date(Date collection_phase_start_date) {
		this.collection_phase_start_date = collection_phase_start_date;
	}

	public Date getCollection_phase_close_date() {
		return collection_phase_close_date;
	}

	public void setCollection_phase_close_date(Date collection_phase_close_date) {
		this.collection_phase_close_date = collection_phase_close_date;
	}

	public long getCollection_phase_status_id() {
		return collection_phase_status_id;
	}

	public void setCollection_phase_status_id(long collection_phase_status_id) {
		this.collection_phase_status_id = collection_phase_status_id;
	}

	public long getCollection_phase_type_id() {
		return collection_phase_type_id;
	}

	public void setCollection_phase_type_id(long collection_phase_type_id) {
		this.collection_phase_type_id = collection_phase_type_id;
	}

	public long getCollection_last_event_id() {
		return collection_last_event_id;
	}

	public void setCollection_last_event_id(long collection_last_event_id) {
		this.collection_last_event_id = collection_last_event_id;
	}

	public long getCollection_last_event_status_id() {
		return collection_last_event_status_id;
	}

	public void setCollection_last_event_status_id(long collection_last_event_status_id) {
		this.collection_last_event_status_id = collection_last_event_status_id;
	}

	public String getCollection_phase_type_name() {
		return collection_phase_type_name;
	}

	public void setCollection_phase_type_name(String collection_phase_type_name) {
		this.collection_phase_type_name = collection_phase_type_name;
	}

	public double getCollection_phase_start_total_amount() {
		return collection_phase_start_total_amount;
	}

	public void setCollection_phase_start_total_amount(double collection_phase_start_total_amount) {
		this.collection_phase_start_total_amount = collection_phase_start_total_amount;
	}

	public double getCollection_phase_close_total_amount() {
		return collection_phase_close_total_amount;
	}

	public void setCollection_phase_close_total_amount(double collection_phase_close_total_amount) {
		this.collection_phase_close_total_amount = collection_phase_close_total_amount;
	}

    public int getPhaseCount() {
        return phaseCount;
    }

    public void setPhaseCount(int phaseCount) {
        this.phaseCount = phaseCount;
    }

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }
}