package kg.gov.mf.loan.output.report.model;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;


public class LoanSummaryReportData extends ReportData
{
	private LoanSummaryReportData Parent                = null;
	private LinkedList<LoanSummaryReportData> ChildDataList = null;

	private short  Level             = 0;

	private String Name                 = "";

	private LinkedHashSet<LoanSummaryView> loanSummaryViews = new LinkedHashSet<LoanSummaryView>(0);




	//*************************************************************

	private Date   onDate         = null;

	private Date   lastDate         = null;

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
	private double TotalPaidKGS         = 0;
	private double RemainingSum   		= 0;
	private double RemainingPrincipal 	= 0;
	private double RemainingInterest  	= 0;
	private double RemainingPenalty 	= 0;

	private double OverdueAll      		= 0;
	private double OverduePrincipal     = 0;
	private double OverdueInterest      = 0;
	private double OverduePenalty      	= 0;

	private short  loanType    			= 0;

	private long   loanFinGroup    		= 0;

	private long   debtorGroup    		= 0;
	private long   debtorSubGroup    	= 0;

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


	private double OverdueDiff      	= 0;
	private double RemainingDiff   		= 0;

	private Date   loanCloseDate        = null;
	private Double loanCloseRate   		= 1.0;


	private double loanAmountInCurrency           = 0;
	private double TotalDisbursmentInCurrency     = 0;

	private double RemainingSumInCurrency   		= 0;
	private double RemainingPrincipalInCurrency 	= 0;
	private double RemainingInterestInCurrency  	= 0;
	private double RemainingPenaltyInCurrency 	= 0;

	private double OverdueAllInCurrency      		= 0;
	private double OverduePrincipalInCurrency     = 0;
	private double OverdueInterestInCurrency      = 0;
	private double OverduePenaltyInCurrency      	= 0;

	private short  region    			= 0;
	private short  district    			= 0;
	private short  workSector    			= 0;
	private short  creditLine    			= 0;

	private String debtorName        = "";

	public LoanSummaryReportData()
	{
		ChildDataList = new LinkedList<LoanSummaryReportData>();
	}


	public LoanSummaryReportData addChild()
	{
		LoanSummaryReportData ChildData = new LoanSummaryReportData();
		ChildData.setParent(this);
		ChildDataList.add(ChildData);

		return ChildData;
	}


	public LoanSummaryReportData[] getChilds()
	{
		return  (LoanSummaryReportData[])ChildDataList.toArray(new LoanSummaryReportData[ChildDataList.size()]);
	}


	public LinkedList<LoanSummaryReportData> getChildDataList() {
		return ChildDataList;
	}

	public void setChildDataList(LinkedList<LoanSummaryReportData> childDataList) {
		ChildDataList = childDataList;
	}

	public LinkedList<LoanSummaryReportData> getData()
	{
		return ChildDataList;
	}


	public void setData(LinkedList<LoanSummaryReportData> data)
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
	public LoanSummaryReportData getParent()
	{
		return Parent;
	}
	public void setParent(LoanSummaryReportData parent)
	{
		Parent = parent;
	}

	public short getLevel() {
		return Level;
	}

	public void setLevel(short level) {
		Level = level;
	}


	public LinkedHashSet<LoanSummaryView> getLoanSummaryViews() {
		return loanSummaryViews;
	}

	public void setLoanSummaryViews(LinkedHashSet<LoanSummaryView> loanSummaryViews) {
		this.loanSummaryViews = loanSummaryViews;
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

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public double getTotalPaidKGS() {
		return TotalPaidKGS;
	}

	public void setTotalPaidKGS(double totalPaidKGS) {
		TotalPaidKGS = totalPaidKGS;
	}

	public double getOverdueDiff() {
		return OverdueDiff;
	}

	public void setOverdueDiff(double overdueDiff) {
		OverdueDiff = overdueDiff;
	}

	public double getRemainingDiff() {
		return RemainingDiff;
	}

	public void setRemainingDiff(double remainingDiff) {
		RemainingDiff = remainingDiff;
	}

	public Date getLoanCloseDate() {
		return loanCloseDate;
	}

	public void setLoanCloseDate(Date loanCloseDate) {
		this.loanCloseDate = loanCloseDate;
	}

	public Double getLoanCloseRate() {
		return loanCloseRate;
	}

	public void setLoanCloseRate(Double loanCloseRate) {
		this.loanCloseRate = loanCloseRate;
	}

	public long getLoanFinGroup() {
		return loanFinGroup;
	}

	public void setLoanFinGroup(long loanFinGroup) {
		this.loanFinGroup = loanFinGroup;
	}

	public long getDebtorGroup() {
		return debtorGroup;
	}

	public void setDebtorGroup(long debtorGroup) {
		this.debtorGroup = debtorGroup;
	}

	public long getDebtorSubGroup() {
		return debtorSubGroup;
	}

	public void setDebtorSubGroup(long debtorSubGroup) {
		this.debtorSubGroup = debtorSubGroup;
	}


	public double getLoanAmountInCurrency() {
		return loanAmountInCurrency;
	}

	public void setLoanAmountInCurrency(double loanAmountInCurrency) {
		this.loanAmountInCurrency = loanAmountInCurrency;
	}

	public double getTotalDisbursmentInCurrency() {
		return TotalDisbursmentInCurrency;
	}

	public void setTotalDisbursmentInCurrency(double totalDisbursmentInCurrency) {
		TotalDisbursmentInCurrency = totalDisbursmentInCurrency;
	}

	public double getRemainingSumInCurrency() {
		return RemainingSumInCurrency;
	}

	public void setRemainingSumInCurrency(double remainingSumInCurrency) {
		RemainingSumInCurrency = remainingSumInCurrency;
	}

	public double getRemainingPrincipalInCurrency() {
		return RemainingPrincipalInCurrency;
	}

	public void setRemainingPrincipalInCurrency(double remainingPrincipalInCurrency) {
		RemainingPrincipalInCurrency = remainingPrincipalInCurrency;
	}

	public double getRemainingInterestInCurrency() {
		return RemainingInterestInCurrency;
	}

	public void setRemainingInterestInCurrency(double remainingInterestInCurrency) {
		RemainingInterestInCurrency = remainingInterestInCurrency;
	}

	public double getRemainingPenaltyInCurrency() {
		return RemainingPenaltyInCurrency;
	}

	public void setRemainingPenaltyInCurrency(double remainingPenaltyInCurrency) {
		RemainingPenaltyInCurrency = remainingPenaltyInCurrency;
	}

	public double getOverdueAllInCurrency() {
		return OverdueAllInCurrency;
	}

	public void setOverdueAllInCurrency(double overdueAllInCurrency) {
		OverdueAllInCurrency = overdueAllInCurrency;
	}

	public double getOverduePrincipalInCurrency() {
		return OverduePrincipalInCurrency;
	}

	public void setOverduePrincipalInCurrency(double overduePrincipalInCurrency) {
		OverduePrincipalInCurrency = overduePrincipalInCurrency;
	}

	public double getOverdueInterestInCurrency() {
		return OverdueInterestInCurrency;
	}

	public void setOverdueInterestInCurrency(double overdueInterestInCurrency) {
		OverdueInterestInCurrency = overdueInterestInCurrency;
	}

	public double getOverduePenaltyInCurrency() {
		return OverduePenaltyInCurrency;
	}

	public void setOverduePenaltyInCurrency(double overduePenaltyInCurrency) {
		OverduePenaltyInCurrency = overduePenaltyInCurrency;
	}

	public short getRegion() {
		return region;
	}

	public void setRegion(short region) {
		this.region = region;
	}

	public short getDistrict() {
		return district;
	}

	public void setDistrict(short district) {
		this.district = district;
	}

	public short getWorkSector() {
		return workSector;
	}

	public void setWorkSector(short workSector) {
		this.workSector = workSector;
	}

	public short getCreditLine() {
		return creditLine;
	}

	public void setCreditLine(short creditLine) {
		this.creditLine = creditLine;
	}

	public String getDebtorName() {
		return debtorName;
	}

	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}
}