package kg.gov.mf.loan.output.report.model;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;


public class PaymentScheduleReportData extends ReportData
{
	private PaymentScheduleReportData Parent                = null;
	private LinkedList<PaymentScheduleReportData> ChildDataList = null;

	private short  Level             = 0;

	private String Name                 = "";

	private LinkedHashSet<PaymentScheduleView> paymentScheduleView = new LinkedHashSet<PaymentScheduleView>(0);




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


	private Date   expectedDate          = null;


	private double disbursement   = 0;

	private double principal_payment   = 0;


	private double interest_payment   = 0;


	private double collected_interest_payment   = 0;


	private double collected_penalty_payment   = 0;


	private long installment_state_id = 0;

	private double TotalPayment         	= 0;

	public PaymentScheduleReportData()
	{
		ChildDataList = new LinkedList<PaymentScheduleReportData>();
	}


	public PaymentScheduleReportData addChild()
	{
		PaymentScheduleReportData ChildData = new PaymentScheduleReportData();
		ChildData.setParent(this);
		ChildDataList.add(ChildData);

		return ChildData;
	}


	public PaymentScheduleReportData[] getChilds()
	{
		return  (PaymentScheduleReportData[])ChildDataList.toArray(new PaymentScheduleReportData[ChildDataList.size()]);
	}


	public LinkedList<PaymentScheduleReportData> getChildDataList() {
		return ChildDataList;
	}

	public void setChildDataList(LinkedList<PaymentScheduleReportData> childDataList) {
		ChildDataList = childDataList;
	}

	public LinkedList<PaymentScheduleReportData> getData()
	{
		return ChildDataList;
	}


	public void setData(LinkedList<PaymentScheduleReportData> data)
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
	public PaymentScheduleReportData getParent()
	{
		return Parent;
	}
	public void setParent(PaymentScheduleReportData parent)
	{
		Parent = parent;
	}

	public short getLevel() {
		return Level;
	}

	public void setLevel(short level) {
		Level = level;
	}


	public LinkedHashSet<PaymentScheduleView> getPaymentScheduleViews() {
		return paymentScheduleView;
	}

	public void setPaymentScheduleViews(LinkedHashSet<PaymentScheduleView> paymentScheduleView) {
		this.paymentScheduleView = paymentScheduleView;
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


	public Date getExpectedDate() {
		return expectedDate;
	}

	public void setExpectedDate(Date expectedDate) {
		this.expectedDate = expectedDate;
	}

	public double getDisbursement() {
		return disbursement;
	}

	public void setDisbursement(double disbursement) {
		this.disbursement = disbursement;
	}

	public double getPrincipal_payment() {
		return principal_payment;
	}

	public void setPrincipal_payment(double principal_payment) {
		this.principal_payment = principal_payment;
	}

	public double getInterest_payment() {
		return interest_payment;
	}

	public void setInterest_payment(double interest_payment) {
		this.interest_payment = interest_payment;
	}

	public double getCollected_interest_payment() {
		return collected_interest_payment;
	}

	public void setCollected_interest_payment(double collected_interest_payment) {
		this.collected_interest_payment = collected_interest_payment;
	}

	public double getCollected_penalty_payment() {
		return collected_penalty_payment;
	}

	public void setCollected_penalty_payment(double collected_penalty_payment) {
		this.collected_penalty_payment = collected_penalty_payment;
	}

	public long getInstallment_state_id() {
		return installment_state_id;
	}

	public void setInstallment_state_id(long installment_state_id) {
		this.installment_state_id = installment_state_id;
	}

	public double getTotalPayment() {
		return TotalPayment;
	}

	public void setTotalPayment(double totalPayment) {
		TotalPayment = totalPayment;
	}
}
