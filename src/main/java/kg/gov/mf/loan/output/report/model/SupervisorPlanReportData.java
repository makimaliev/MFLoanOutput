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


	private int    sp_Count         = 0;

	private String sp_description        = "";

	private Date   sp_date          = null;

	private Date   sp_reg_date          = null;


	private double sp_amount   = 0;

	private double sp_principal   = 0;


	private double sp_interest   = 0;


	private double sp_fee = 0;


	private double sp_penalty= 0;


	private long sp_reg_by_id= 0;


	private double sp_year = 0;

	private double sp_quarter1 = 0;
	private double sp_quarter2 = 0;
	private double sp_quarter3 = 0;
	private double sp_quarter4 = 0;

	private double sp_month1 = 0;
	private double sp_month2 = 0;
	private double sp_month3 = 0;
	private double sp_month4 = 0;
	private double sp_month5 = 0;
	private double sp_month6 = 0;
	private double sp_month7 = 0;
	private double sp_month8 = 0;
	private double sp_month9 = 0;
	private double sp_month10 = 0;
	private double sp_month11 = 0;
	private double sp_month12 = 0;

	private double ps_year = 0;

	private double ps_quarter1 = 0;
	private double ps_quarter2 = 0;
	private double ps_quarter3 = 0;
	private double ps_quarter4 = 0;

	private double ps_month1 = 0;
	private double ps_month2 = 0;
	private double ps_month3 = 0;
	private double ps_month4 = 0;
	private double ps_month5 = 0;
	private double ps_month6 = 0;
	private double ps_month7 = 0;
	private double ps_month8 = 0;
	private double ps_month9 = 0;
	private double ps_month10 = 0;
	private double ps_month11 = 0;
	private double ps_month12 = 0;

	public double getPs_year() {
		return ps_year;
	}

	public void setPs_year(double ps_year) {
		this.ps_year = ps_year;
	}

	public double getPs_quarter1() {
		return ps_quarter1;
	}

	public void setPs_quarter1(double ps_quarter1) {
		this.ps_quarter1 = ps_quarter1;
	}

	public double getPs_quarter2() {
		return ps_quarter2;
	}

	public void setPs_quarter2(double ps_quarter2) {
		this.ps_quarter2 = ps_quarter2;
	}

	public double getPs_quarter3() {
		return ps_quarter3;
	}

	public void setPs_quarter3(double ps_quarter3) {
		this.ps_quarter3 = ps_quarter3;
	}

	public double getPs_quarter4() {
		return ps_quarter4;
	}

	public void setPs_quarter4(double ps_quarter4) {
		this.ps_quarter4 = ps_quarter4;
	}

	public double getPs_month1() {
		return ps_month1;
	}

	public void setPs_month1(double ps_month1) {
		this.ps_month1 = ps_month1;
	}

	public double getPs_month2() {
		return ps_month2;
	}

	public void setPs_month2(double ps_month2) {
		this.ps_month2 = ps_month2;
	}

	public double getPs_month3() {
		return ps_month3;
	}

	public void setPs_month3(double ps_month3) {
		this.ps_month3 = ps_month3;
	}

	public double getPs_month4() {
		return ps_month4;
	}

	public void setPs_month4(double ps_month4) {
		this.ps_month4 = ps_month4;
	}

	public double getPs_month5() {
		return ps_month5;
	}

	public void setPs_month5(double ps_month5) {
		this.ps_month5 = ps_month5;
	}

	public double getPs_month6() {
		return ps_month6;
	}

	public void setPs_month6(double ps_month6) {
		this.ps_month6 = ps_month6;
	}

	public double getPs_month7() {
		return ps_month7;
	}

	public void setPs_month7(double ps_month7) {
		this.ps_month7 = ps_month7;
	}

	public double getPs_month8() {
		return ps_month8;
	}

	public void setPs_month8(double ps_month8) {
		this.ps_month8 = ps_month8;
	}

	public double getPs_month9() {
		return ps_month9;
	}

	public void setPs_month9(double ps_month9) {
		this.ps_month9 = ps_month9;
	}

	public double getPs_month10() {
		return ps_month10;
	}

	public void setPs_month10(double ps_month10) {
		this.ps_month10 = ps_month10;
	}

	public double getPs_month11() {
		return ps_month11;
	}

	public void setPs_month11(double ps_month11) {
		this.ps_month11 = ps_month11;
	}

	public double getPs_month12() {
		return ps_month12;
	}

	public void setPs_month12(double ps_month12) {
		this.ps_month12 = ps_month12;
	}

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

	public int getSp_Count() {
		return sp_Count;
	}

	public void setSp_Count(int sp_Count) {
		this.sp_Count = sp_Count;
	}

	public double getSp_year() {
		return sp_year;
	}

	public void setSp_year(double sp_year) {
		this.sp_year = sp_year;
	}

	public double getSp_quarter1() {
		return sp_quarter1;
	}

	public void setSp_quarter1(double sp_quarter1) {
		this.sp_quarter1 = sp_quarter1;
	}

	public double getSp_quarter2() {
		return sp_quarter2;
	}

	public void setSp_quarter2(double sp_quarter2) {
		this.sp_quarter2 = sp_quarter2;
	}

	public double getSp_quarter3() {
		return sp_quarter3;
	}

	public void setSp_quarter3(double sp_quarter3) {
		this.sp_quarter3 = sp_quarter3;
	}

	public double getSp_quarter4() {
		return sp_quarter4;
	}

	public void setSp_quarter4(double sp_quarter4) {
		this.sp_quarter4 = sp_quarter4;
	}

	public double getSp_month1() {
		return sp_month1;
	}

	public void setSp_month1(double sp_month1) {
		this.sp_month1 = sp_month1;
	}

	public double getSp_month2() {
		return sp_month2;
	}

	public void setSp_month2(double sp_month2) {
		this.sp_month2 = sp_month2;
	}

	public double getSp_month3() {
		return sp_month3;
	}

	public void setSp_month3(double sp_month3) {
		this.sp_month3 = sp_month3;
	}

	public double getSp_month4() {
		return sp_month4;
	}

	public void setSp_month4(double sp_month4) {
		this.sp_month4 = sp_month4;
	}

	public double getSp_month5() {
		return sp_month5;
	}

	public void setSp_month5(double sp_month5) {
		this.sp_month5 = sp_month5;
	}

	public double getSp_month6() {
		return sp_month6;
	}

	public void setSp_month6(double sp_month6) {
		this.sp_month6 = sp_month6;
	}

	public double getSp_month7() {
		return sp_month7;
	}

	public void setSp_month7(double sp_month7) {
		this.sp_month7 = sp_month7;
	}

	public double getSp_month8() {
		return sp_month8;
	}

	public void setSp_month8(double sp_month8) {
		this.sp_month8 = sp_month8;
	}

	public double getSp_month9() {
		return sp_month9;
	}

	public void setSp_month9(double sp_month9) {
		this.sp_month9 = sp_month9;
	}

	public double getSp_month10() {
		return sp_month10;
	}

	public void setSp_month10(double sp_month10) {
		this.sp_month10 = sp_month10;
	}

	public double getSp_month11() {
		return sp_month11;
	}

	public void setSp_month11(double sp_month11) {
		this.sp_month11 = sp_month11;
	}

	public double getSp_month12() {
		return sp_month12;
	}

	public void setSp_month12(double sp_month12) {
		this.sp_month12 = sp_month12;
	}
}
