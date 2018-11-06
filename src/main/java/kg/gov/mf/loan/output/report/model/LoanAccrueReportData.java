package kg.gov.mf.loan.output.report.model;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;


public class LoanAccrueReportData extends ReportData
{
	private LoanAccrueReportData Parent                = null;
	private LinkedList<LoanAccrueReportData> ChildDataList = null;

	private short  Level             = 0;

	private String Name                 = "";

	private LinkedHashSet<LoanAccrueView> loanAccrueView = new LinkedHashSet<LoanAccrueView>(0);




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


	private Date   v_acc_fromDate          = null;

	private Date   v_acc_toDate          = null;

	private long v_acc_daysInPeriod = 0;

	private boolean v_acc_lastInstPassed =false;


	private double v_acc_interestAccrued= 0;

	private double v_acc_penaltyAccrued   = 0;


	private double v_acc_penaltyOnPrincipalOverdue= 0;


	private double v_acc_penaltyOnInterestOverdue = 0;



	public LoanAccrueReportData()
	{
		ChildDataList = new LinkedList<LoanAccrueReportData>();
	}


	public LoanAccrueReportData addChild()
	{
		LoanAccrueReportData ChildData = new LoanAccrueReportData();
		ChildData.setParent(this);
		ChildDataList.add(ChildData);

		return ChildData;
	}


	public LoanAccrueReportData[] getChilds()
	{
		return  (LoanAccrueReportData[])ChildDataList.toArray(new LoanAccrueReportData[ChildDataList.size()]);
	}


	public LinkedList<LoanAccrueReportData> getChildDataList() {
		return ChildDataList;
	}

	public void setChildDataList(LinkedList<LoanAccrueReportData> childDataList) {
		ChildDataList = childDataList;
	}

	public LinkedList<LoanAccrueReportData> getData()
	{
		return ChildDataList;
	}


	public void setData(LinkedList<LoanAccrueReportData> data)
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
	public LoanAccrueReportData getParent()
	{
		return Parent;
	}
	public void setParent(LoanAccrueReportData parent)
	{
		Parent = parent;
	}

	public short getLevel() {
		return Level;
	}

	public void setLevel(short level) {
		Level = level;
	}


	public LinkedHashSet<LoanAccrueView> getLoanAccrueViews() {
		return loanAccrueView;
	}

	public void setLoanAccrueViews(LinkedHashSet<LoanAccrueView> loanAccrueView) {
		this.loanAccrueView = loanAccrueView;
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


	public LinkedHashSet<LoanAccrueView> getLoanAccrueView() {
		return loanAccrueView;
	}

	public void setLoanAccrueView(LinkedHashSet<LoanAccrueView> loanAccrueView) {
		this.loanAccrueView = loanAccrueView;
	}

	public Date getV_acc_fromDate() {
		return v_acc_fromDate;
	}

	public void setV_acc_fromDate(Date v_acc_fromDate) {
		this.v_acc_fromDate = v_acc_fromDate;
	}

	public Date getV_acc_toDate() {
		return v_acc_toDate;
	}

	public void setV_acc_toDate(Date v_acc_toDate) {
		this.v_acc_toDate = v_acc_toDate;
	}

	public long getV_acc_daysInPeriod() {
		return v_acc_daysInPeriod;
	}

	public void setV_acc_daysInPeriod(long v_acc_daysInPeriod) {
		this.v_acc_daysInPeriod = v_acc_daysInPeriod;
	}

	public boolean isV_acc_lastInstPassed() {
		return v_acc_lastInstPassed;
	}

	public void setV_acc_lastInstPassed(boolean v_acc_lastInstPassed) {
		this.v_acc_lastInstPassed = v_acc_lastInstPassed;
	}

	public double getV_acc_interestAccrued() {
		return v_acc_interestAccrued;
	}

	public void setV_acc_interestAccrued(double v_acc_interestAccrued) {
		this.v_acc_interestAccrued = v_acc_interestAccrued;
	}

	public double getV_acc_penaltyAccrued() {
		return v_acc_penaltyAccrued;
	}

	public void setV_acc_penaltyAccrued(double v_acc_penaltyAccrued) {
		this.v_acc_penaltyAccrued = v_acc_penaltyAccrued;
	}

	public double getV_acc_penaltyOnPrincipalOverdue() {
		return v_acc_penaltyOnPrincipalOverdue;
	}

	public void setV_acc_penaltyOnPrincipalOverdue(double v_acc_penaltyOnPrincipalOverdue) {
		this.v_acc_penaltyOnPrincipalOverdue = v_acc_penaltyOnPrincipalOverdue;
	}

	public double getV_acc_penaltyOnInterestOverdue() {
		return v_acc_penaltyOnInterestOverdue;
	}

	public void setV_acc_penaltyOnInterestOverdue(double v_acc_penaltyOnInterestOverdue) {
		this.v_acc_penaltyOnInterestOverdue = v_acc_penaltyOnInterestOverdue;
	}
}
