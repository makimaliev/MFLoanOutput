package kg.gov.mf.loan.output.report.model;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;


public class CollateralArrestFreeReportData extends ReportData
{
	private CollateralArrestFreeReportData Parent                = null;
	private LinkedList<CollateralArrestFreeReportData> ChildDataList = null;

	private short  Level             = 0;

	private String Name                 = "";

	private LinkedHashSet<CollateralArrestFreeView> collateralArrestFreeViews = new LinkedHashSet<CollateralArrestFreeView>(0);




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

	private short  loanType    			= 0;

	private double totalPaidInCurrency    = 0;

	private String details     			= "";


	private String paymentNumber        = "";
	private String paymentTypeName      = "";
	private Date   paymentDate          = null;
	private int    PaymentCount         = 0;

	private long   paymentID            = 0;
	private double paymentPrincipal     = 0;
	private double paymentInterest     	= 0;
	private double paymentPenalty     	= 0;
	private double paymentFee     		= 0;
	private double paymentTotalAmount   = 0;

	private double paymentExchangeRate   = 0;

	private long   collateralItemID            = 0;
	private Date   collateralAgreementDate          = null;

	private String collateralAgreementNumber        = "";

	private Date   collateralArrestRegDate          = null;

	private String collateralArrestRegNumber        = "";


	private Date   collateralOfficeRegDate          = null;

	private String collateralOfficeRegNumber        = "";


	private Date   collateralNotaryOfficeRegDate          = null;

	private String collateralNotaryOfficeRegNumber        = "";

	private double collateralItemCollateralValue   = 0;

	private double collateralItemDemandRate   = 0;

	private String collateralItemDescription        = "";

	private double collateralItemEstimatedValue   = 0;

	private String collateralItemName        = "";


	private double collateralItemQuantity   = 0;

	private double collateralItemRiskRate   = 0;

	private long collateralItemConditionTypeId = 0;

	private long collateralItemTypeId = 0;

	private String collateralItemTypeName = "";

	private long collateralItemQuantityTypeId = 0;



	private String collateralArrestFreeDetails        = "";

	private long collateralArrestFreeBy = 0;

	private Date   collateralArrestFreeOnDate          = null;


	private int    arrestFreeCount                = 0;



	public CollateralArrestFreeReportData()
	{
		ChildDataList = new LinkedList<CollateralArrestFreeReportData>();
	}


	public CollateralArrestFreeReportData addChild()
	{
		CollateralArrestFreeReportData ChildData = new CollateralArrestFreeReportData();
		ChildData.setParent(this);
		ChildDataList.add(ChildData);

		return ChildData;
	}


	public CollateralArrestFreeReportData[] getChilds()
	{
		return  (CollateralArrestFreeReportData[])ChildDataList.toArray(new CollateralArrestFreeReportData[ChildDataList.size()]);
	}


	public LinkedList<CollateralArrestFreeReportData> getChildDataList() {
		return ChildDataList;
	}

	public void setChildDataList(LinkedList<CollateralArrestFreeReportData> childDataList) {
		ChildDataList = childDataList;
	}

	public LinkedList<CollateralArrestFreeReportData> getData()
	{
		return ChildDataList;
	}


	public void setData(LinkedList<CollateralArrestFreeReportData> data)
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
	public CollateralArrestFreeReportData getParent()
	{
		return Parent;
	}
	public void setParent(CollateralArrestFreeReportData parent)
	{
		Parent = parent;
	}

	public short getLevel() {
		return Level;
	}

	public void setLevel(short level) {
		Level = level;
	}


	public LinkedHashSet<CollateralArrestFreeView> getCollateralArrestFreeViews() {
		return collateralArrestFreeViews;
	}

	public void setCollateralArrestFreeViews(LinkedHashSet<CollateralArrestFreeView> collateralArrestFreeViews) {
		this.collateralArrestFreeViews = collateralArrestFreeViews;
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


	public String getPaymentNumber() {
		return paymentNumber;
	}

	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	public String getPaymentTypeName() {
		return paymentTypeName;
	}

	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getPaymentCount() {
		return PaymentCount;
	}

	public void setPaymentCount(int paymentCount) {
		PaymentCount = paymentCount;
	}

	public long getPaymentID() {
		return paymentID;
	}

	public void setPaymentID(long paymentID) {
		this.paymentID = paymentID;
	}

	public double getPaymentPrincipal() {
		return paymentPrincipal;
	}

	public void setPaymentPrincipal(double paymentPrincipal) {
		this.paymentPrincipal = paymentPrincipal;
	}

	public double getPaymentInterest() {
		return paymentInterest;
	}

	public void setPaymentInterest(double paymentInterest) {
		this.paymentInterest = paymentInterest;
	}

	public double getPaymentPenalty() {
		return paymentPenalty;
	}

	public void setPaymentPenalty(double paymentPenalty) {
		this.paymentPenalty = paymentPenalty;
	}

	public double getPaymentFee() {
		return paymentFee;
	}

	public void setPaymentFee(double paymentFee) {
		this.paymentFee = paymentFee;
	}

	public double getPaymentTotalAmount() {
		return paymentTotalAmount;
	}

	public void setPaymentTotalAmount(double paymentTotalAmount) {
		this.paymentTotalAmount = paymentTotalAmount;
	}

	public double getPaymentExchangeRate() {
		return paymentExchangeRate;
	}

	public void setPaymentExchangeRate(double paymentExchangeRate) {
		this.paymentExchangeRate = paymentExchangeRate;
	}


	public long getCollateralItemID() {
		return collateralItemID;
	}

	public void setCollateralItemID(long collateralItemID) {
		this.collateralItemID = collateralItemID;
	}

	public Date getCollateralAgreementDate() {
		return collateralAgreementDate;
	}

	public void setCollateralAgreementDate(Date collateralAgreementDate) {
		this.collateralAgreementDate = collateralAgreementDate;
	}

	public String getCollateralAgreementNumber() {
		return collateralAgreementNumber;
	}

	public void setCollateralAgreementNumber(String collateralAgreementNumber) {
		this.collateralAgreementNumber = collateralAgreementNumber;
	}

	public Date getCollateralArrestRegDate() {
		return collateralArrestRegDate;
	}

	public void setCollateralArrestRegDate(Date collateralArrestRegDate) {
		this.collateralArrestRegDate = collateralArrestRegDate;
	}

	public String getCollateralArrestRegNumber() {
		return collateralArrestRegNumber;
	}

	public void setCollateralArrestRegNumber(String collateralArrestRegNumber) {
		this.collateralArrestRegNumber = collateralArrestRegNumber;
	}

	public Date getCollateralOfficeRegDate() {
		return collateralOfficeRegDate;
	}

	public void setCollateralOfficeRegDate(Date collateralOfficeRegDate) {
		this.collateralOfficeRegDate = collateralOfficeRegDate;
	}

	public String getCollateralOfficeRegNumber() {
		return collateralOfficeRegNumber;
	}

	public void setCollateralOfficeRegNumber(String collateralOfficeRegNumber) {
		this.collateralOfficeRegNumber = collateralOfficeRegNumber;
	}

	public Date getCollateralNotaryOfficeRegDate() {
		return collateralNotaryOfficeRegDate;
	}

	public void setCollateralNotaryOfficeRegDate(Date collateralNotaryOfficeRegDate) {
		this.collateralNotaryOfficeRegDate = collateralNotaryOfficeRegDate;
	}

	public String getCollateralNotaryOfficeRegNumber() {
		return collateralNotaryOfficeRegNumber;
	}

	public void setCollateralNotaryOfficeRegNumber(String collateralNotaryOfficeRegNumber) {
		this.collateralNotaryOfficeRegNumber = collateralNotaryOfficeRegNumber;
	}

	public double getCollateralItemCollateralValue() {
		return collateralItemCollateralValue;
	}

	public void setCollateralItemCollateralValue(double collateralItemCollateralValue) {
		this.collateralItemCollateralValue = collateralItemCollateralValue;
	}

	public double getCollateralItemDemandRate() {
		return collateralItemDemandRate;
	}

	public void setCollateralItemDemandRate(double collateralItemDemandRate) {
		this.collateralItemDemandRate = collateralItemDemandRate;
	}

	public String getCollateralItemDescription() {
		return collateralItemDescription;
	}

	public void setCollateralItemDescription(String collateralItemDescription) {
		this.collateralItemDescription = collateralItemDescription;
	}

	public double getCollateralItemEstimatedValue() {
		return collateralItemEstimatedValue;
	}

	public void setCollateralItemEstimatedValue(double collateralItemEstimatedValue) {
		this.collateralItemEstimatedValue = collateralItemEstimatedValue;
	}

	public String getCollateralItemName() {
		return collateralItemName;
	}

	public void setCollateralItemName(String collateralItemName) {
		this.collateralItemName = collateralItemName;
	}

	public double getCollateralItemQuantity() {
		return collateralItemQuantity;
	}

	public void setCollateralItemQuantity(double collateralItemQuantity) {
		this.collateralItemQuantity = collateralItemQuantity;
	}

	public double getCollateralItemRiskRate() {
		return collateralItemRiskRate;
	}

	public void setCollateralItemRiskRate(double collateralItemRiskRate) {
		this.collateralItemRiskRate = collateralItemRiskRate;
	}

	public long getCollateralItemConditionTypeId() {
		return collateralItemConditionTypeId;
	}

	public void setCollateralItemConditionTypeId(long collateralItemConditionTypeId) {
		this.collateralItemConditionTypeId = collateralItemConditionTypeId;
	}

	public long getCollateralItemTypeId() {
		return collateralItemTypeId;
	}

	public void setCollateralItemTypeId(long collateralItemTypeId) {
		this.collateralItemTypeId = collateralItemTypeId;
	}

	public long getCollateralItemQuantityTypeId() {
		return collateralItemQuantityTypeId;
	}

	public void setCollateralItemQuantityTypeId(long collateralItemQuantityTypeId) {
		this.collateralItemQuantityTypeId = collateralItemQuantityTypeId;
	}

	public String getCollateralItemTypeName() {
		return collateralItemTypeName;
	}

	public void setCollateralItemTypeName(String collateralItemTypeName) {
		this.collateralItemTypeName = collateralItemTypeName;
	}


	public String getCollateralArrestFreeDetails() {
		return collateralArrestFreeDetails;
	}

	public void setCollateralArrestFreeDetails(String collateralArrestFreeDetails) {
		this.collateralArrestFreeDetails = collateralArrestFreeDetails;
	}



	public Date getCollateralArrestFreeOnDate() {
		return collateralArrestFreeOnDate;
	}

	public void setCollateralArrestFreeOnDate(Date collateralArrestFreeOnDate) {
		this.collateralArrestFreeOnDate = collateralArrestFreeOnDate;
	}

	public long getCollateralArrestFreeBy() {
		return collateralArrestFreeBy;
	}

	public void setCollateralArrestFreeBy(long collateralArrestFreeBy) {
		this.collateralArrestFreeBy = collateralArrestFreeBy;
	}

	public int getArrestFreeCount() {
		return arrestFreeCount;
	}

	public void setArrestFreeCount(int arrestFreeCount) {
		this.arrestFreeCount = arrestFreeCount;
	}
}