package kg.gov.mf.loan.output.report.model;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;


public class LoanDebtTransferReportData extends ReportData
{
	private LoanDebtTransferReportData Parent                = null;
	private LinkedList<LoanDebtTransferReportData> ChildDataList = null;

	private short  Level             = 0;

	private String Name                 = "";

	private LinkedHashSet<LoanDebtTransferView> loanDebtTransferView = new LinkedHashSet<LoanDebtTransferView>(0);




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


	private String dt_number        = "";

	private Date   dt_date          = null;

	private long dt_goodsTypeId= 0;
	private long dt_unitTypeId= 0;

	private long dt_transferCreditId= 0;
	private long dt_transferPaymentId= 0;
	private long dt_transferPersonId= 0;


	private int    dt_Count         = 0;

	private double dt_pricePerUnit   = 0;

	private double dt_quantity   = 0;


	private double dt_totalCost   = 0;


	public LoanDebtTransferReportData()
	{
		ChildDataList = new LinkedList<LoanDebtTransferReportData>();
	}


	public LoanDebtTransferReportData addChild()
	{
		LoanDebtTransferReportData ChildData = new LoanDebtTransferReportData();
		ChildData.setParent(this);
		ChildDataList.add(ChildData);

		return ChildData;
	}


	public LoanDebtTransferReportData[] getChilds()
	{
		return  (LoanDebtTransferReportData[])ChildDataList.toArray(new LoanDebtTransferReportData[ChildDataList.size()]);
	}


	public LinkedList<LoanDebtTransferReportData> getChildDataList() {
		return ChildDataList;
	}

	public void setChildDataList(LinkedList<LoanDebtTransferReportData> childDataList) {
		ChildDataList = childDataList;
	}

	public LinkedList<LoanDebtTransferReportData> getData()
	{
		return ChildDataList;
	}


	public void setData(LinkedList<LoanDebtTransferReportData> data)
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
	public LoanDebtTransferReportData getParent()
	{
		return Parent;
	}
	public void setParent(LoanDebtTransferReportData parent)
	{
		Parent = parent;
	}

	public short getLevel() {
		return Level;
	}

	public void setLevel(short level) {
		Level = level;
	}


	public LinkedHashSet<LoanDebtTransferView> getLoanDebtTransferViews() {
		return loanDebtTransferView;
	}

	public void setLoanDebtTransferViews(LinkedHashSet<LoanDebtTransferView> loanDebtTransferView) {
		this.loanDebtTransferView = loanDebtTransferView;
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


	public LinkedHashSet<LoanDebtTransferView> getLoanDebtTransferView() {
		return loanDebtTransferView;
	}

	public void setLoanDebtTransferView(LinkedHashSet<LoanDebtTransferView> loanDebtTransferView) {
		this.loanDebtTransferView = loanDebtTransferView;
	}

	public String getDt_number() {
		return dt_number;
	}

	public void setDt_number(String dt_number) {
		this.dt_number = dt_number;
	}

	public Date getDt_date() {
		return dt_date;
	}

	public void setDt_date(Date dt_date) {
		this.dt_date = dt_date;
	}

	public long getDt_goodsTypeId() {
		return dt_goodsTypeId;
	}

	public void setDt_goodsTypeId(long dt_goodsTypeId) {
		this.dt_goodsTypeId = dt_goodsTypeId;
	}

	public long getDt_unitTypeId() {
		return dt_unitTypeId;
	}

	public void setDt_unitTypeId(long dt_unitTypeId) {
		this.dt_unitTypeId = dt_unitTypeId;
	}

	public long getDt_transferCreditId() {
		return dt_transferCreditId;
	}

	public void setDt_transferCreditId(long dt_transferCreditId) {
		this.dt_transferCreditId = dt_transferCreditId;
	}

	public long getDt_transferPaymentId() {
		return dt_transferPaymentId;
	}

	public void setDt_transferPaymentId(long dt_transferPaymentId) {
		this.dt_transferPaymentId = dt_transferPaymentId;
	}

	public long getDt_transferPersonId() {
		return dt_transferPersonId;
	}

	public void setDt_transferPersonId(long dt_transferPersonId) {
		this.dt_transferPersonId = dt_transferPersonId;
	}

	public double getDt_pricePerUnit() {
		return dt_pricePerUnit;
	}

	public void setDt_pricePerUnit(double dt_pricePerUnit) {
		this.dt_pricePerUnit = dt_pricePerUnit;
	}

	public double getDt_quantity() {
		return dt_quantity;
	}

	public void setDt_quantity(double dt_quantity) {
		this.dt_quantity = dt_quantity;
	}

	public double getDt_totalCost() {
		return dt_totalCost;
	}

	public void setDt_totalCost(double dt_totalCost) {
		this.dt_totalCost = dt_totalCost;
	}

	public int getDt_Count() {
		return dt_Count;
	}

	public void setDt_Count(int dt_Count) {
		this.dt_Count = dt_Count;
	}
}
