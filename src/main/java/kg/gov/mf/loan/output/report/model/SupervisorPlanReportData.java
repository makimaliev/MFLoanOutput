package kg.gov.mf.loan.output.report.model;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;


public class SupervisorPlanReportData extends ReportData
{
	private SupervisorPlanReportData Parent                = null;
	private LinkedList<SupervisorPlanReportData> ChildDataList = null;

	private short  Level             = 0;

	private String Name                 = "";

	private LinkedHashSet<SupervisorPlanView> supervisorPlanView = new LinkedHashSet<SupervisorPlanView>(0);




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


	private String sp_description        = "";

	private Date   sp_date          = null;

	private Date   sp_reg_date          = null;


	private double sp_amount   = 0;

	private double sp_principal   = 0;


	private double sp_interest   = 0;


	private double sp_fee = 0;


	private double sp_penalty= 0;


	private long sp_reg_by_id= 0;

	public SupervisorPlanReportData()
	{
		ChildDataList = new LinkedList<SupervisorPlanReportData>();
	}


	public SupervisorPlanReportData addChild()
	{
		SupervisorPlanReportData ChildData = new SupervisorPlanReportData();
		ChildData.setParent(this);
		ChildDataList.add(ChildData);

		return ChildData;
	}


	public SupervisorPlanReportData[] getChilds()
	{
		return  (SupervisorPlanReportData[])ChildDataList.toArray(new SupervisorPlanReportData[ChildDataList.size()]);
	}


	public LinkedList<SupervisorPlanReportData> getChildDataList() {
		return ChildDataList;
	}

	public void setChildDataList(LinkedList<SupervisorPlanReportData> childDataList) {
		ChildDataList = childDataList;
	}

	public LinkedList<SupervisorPlanReportData> getData()
	{
		return ChildDataList;
	}


	public void setData(LinkedList<SupervisorPlanReportData> data)
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
	public SupervisorPlanReportData getParent()
	{
		return Parent;
	}
	public void setParent(SupervisorPlanReportData parent)
	{
		Parent = parent;
	}

	public short getLevel() {
		return Level;
	}

	public void setLevel(short level) {
		Level = level;
	}


	public LinkedHashSet<SupervisorPlanView> getSupervisorPlanViews() {
		return supervisorPlanView;
	}

	public void setSupervisorPlanViews(LinkedHashSet<SupervisorPlanView> supervisorPlanView) {
		this.supervisorPlanView = supervisorPlanView;
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


	public LinkedHashSet<SupervisorPlanView> getSupervisorPlanView() {
		return supervisorPlanView;
	}

	public void setSupervisorPlanView(LinkedHashSet<SupervisorPlanView> supervisorPlanView) {
		this.supervisorPlanView = supervisorPlanView;
	}

	public String getSp_description() {
		return sp_description;
	}

	public void setSp_description(String sp_description) {
		this.sp_description = sp_description;
	}

	public Date getSp_date() {
		return sp_date;
	}

	public void setSp_date(Date sp_date) {
		this.sp_date = sp_date;
	}

	public Date getSp_reg_date() {
		return sp_reg_date;
	}

	public void setSp_reg_date(Date sp_reg_date) {
		this.sp_reg_date = sp_reg_date;
	}

	public double getSp_amount() {
		return sp_amount;
	}

	public void setSp_amount(double sp_amount) {
		this.sp_amount = sp_amount;
	}

	public double getSp_principal() {
		return sp_principal;
	}

	public void setSp_principal(double sp_principal) {
		this.sp_principal = sp_principal;
	}

	public double getSp_interest() {
		return sp_interest;
	}

	public void setSp_interest(double sp_interest) {
		this.sp_interest = sp_interest;
	}

	public double getSp_fee() {
		return sp_fee;
	}

	public void setSp_fee(double sp_fee) {
		this.sp_fee = sp_fee;
	}

	public double getSp_penalty() {
		return sp_penalty;
	}

	public void setSp_penalty(double sp_penalty) {
		this.sp_penalty = sp_penalty;
	}

	public long getSp_reg_by_id() {
		return sp_reg_by_id;
	}

	public void setSp_reg_by_id(long sp_reg_by_id) {
		this.sp_reg_by_id = sp_reg_by_id;
	}
}
