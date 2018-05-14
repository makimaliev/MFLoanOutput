package kg.gov.mf.loan.output.report.model;

import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="loan_summary_view")
@Immutable
public class LoanSummaryView extends DebtorView
{
	@Column
	private long v_loan_id;

	@Column
	private Double v_loan_amount;

	@Column
	private String v_loan_reg_number;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_loan_reg_date;

	@Column
	private long v_loan_supervisor_id;

	@Column
	private long v_loan_currency_id;

	@Column
	private long v_loan_state_id;

	@Column
	private long v_loan_type_id;

	@Column
	private long v_credit_order_type_id;

	@Column
	private String v_credit_order_regNumber;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_credit_order_regDate;

	@Id
	@Column
	private long v_ls_id;

	@Column
	private  double v_ls_loanAmount;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_ls_onDate;

	@Column
	private  double v_ls_outstadingFee;

	@Column
	private  double v_ls_outstadingInterest;



	@Column
	private  double v_ls_outstadingPenalty;



	@Column
	private  double v_ls_outstadingPrincipal;


	@Column
	private  double v_ls_overdueFee;


	@Column
	private  double v_ls_overdueInterest;


	@Column
	private  double v_ls_overduePenalty;


	@Column
	private  double v_ls_overduePrincipal;


	@Column
	private  double v_ls_paidFee;

	@Column
	private  double v_ls_paidInterest;


	@Column
	private  double v_ls_paidPenalty;

	@Column
	private  double v_ls_paidPrincipal;

	@Column
	private  double v_ls_totalDisbursed;

	@Column
	private  double v_ls_totalOutstanding;

	@Column
	private  double v_ls_totalOverdue;

	@Column
	private  double v_ls_totalPaid;

	public long getV_loan_id() {
		return v_loan_id;
	}

	public void setV_loan_id(long v_loan_id) {
		this.v_loan_id = v_loan_id;
	}

	public Double getV_loan_amount() {
		return v_loan_amount;
	}

	public void setV_loan_amount(Double v_loan_amount) {
		this.v_loan_amount = v_loan_amount;
	}

	public String getV_loan_reg_number() {
		return v_loan_reg_number;
	}

	public void setV_loan_reg_number(String v_loan_reg_number) {
		this.v_loan_reg_number = v_loan_reg_number;
	}

	public Date getV_loan_reg_date() {
		return v_loan_reg_date;
	}

	public void setV_loan_reg_date(Date v_loan_reg_date) {
		this.v_loan_reg_date = v_loan_reg_date;
	}

	public long getV_loan_supervisor_id() {
		return v_loan_supervisor_id;
	}

	public void setV_loan_supervisor_id(long v_loan_supervisor_id) {
		this.v_loan_supervisor_id = v_loan_supervisor_id;
	}

	public long getV_loan_currency_id() {
		return v_loan_currency_id;
	}

	public void setV_loan_currency_id(long v_loan_currency_id) {
		this.v_loan_currency_id = v_loan_currency_id;
	}

	public long getV_loan_state_id() {
		return v_loan_state_id;
	}

	public void setV_loan_state_id(long v_loan_state_id) {
		this.v_loan_state_id = v_loan_state_id;
	}

	public long getV_loan_type_id() {
		return v_loan_type_id;
	}

	public void setV_loan_type_id(long v_loan_type_id) {
		this.v_loan_type_id = v_loan_type_id;
	}

	public long getV_credit_order_type_id() {
		return v_credit_order_type_id;
	}

	public void setV_credit_order_type_id(long v_credit_order_type_id) {
		this.v_credit_order_type_id = v_credit_order_type_id;
	}

	public String getV_credit_order_regNumber() {
		return v_credit_order_regNumber;
	}

	public void setV_credit_order_regNumber(String v_credit_order_regNumber) {
		this.v_credit_order_regNumber = v_credit_order_regNumber;
	}

	public Date getV_credit_order_regDate() {
		return v_credit_order_regDate;
	}

	public void setV_credit_order_regDate(Date v_credit_order_regDate) {
		this.v_credit_order_regDate = v_credit_order_regDate;
	}

	public long getV_ls_id() {
		return v_ls_id;
	}

	public void setV_ls_id(long v_ls_id) {
		this.v_ls_id = v_ls_id;
	}

	public double getV_ls_loanAmount() {
		return v_ls_loanAmount;
	}

	public void setV_ls_loanAmount(double v_ls_loanAmount) {
		this.v_ls_loanAmount = v_ls_loanAmount;
	}

	public double getV_ls_outstadingFee() {
		return v_ls_outstadingFee;
	}

	public void setV_ls_outstadingFee(double v_ls_outstadingFee) {
		this.v_ls_outstadingFee = v_ls_outstadingFee;
	}

	public double getV_ls_outstadingInterest() {
		return v_ls_outstadingInterest;
	}

	public void setV_ls_outstadingInterest(double v_ls_outstadingInterest) {
		this.v_ls_outstadingInterest = v_ls_outstadingInterest;
	}

	public double getV_ls_outstadingPenalty() {
		return v_ls_outstadingPenalty;
	}

	public void setV_ls_outstadingPenalty(double v_ls_outstadingPenalty) {
		this.v_ls_outstadingPenalty = v_ls_outstadingPenalty;
	}

	public double getV_ls_outstadingPrincipal() {
		return v_ls_outstadingPrincipal;
	}

	public void setV_ls_outstadingPrincipal(double v_ls_outstadingPrincipal) {
		this.v_ls_outstadingPrincipal = v_ls_outstadingPrincipal;
	}

	public double getV_ls_overdueFee() {
		return v_ls_overdueFee;
	}

	public void setV_ls_overdueFee(double v_ls_overdueFee) {
		this.v_ls_overdueFee = v_ls_overdueFee;
	}

	public double getV_ls_overdueInterest() {
		return v_ls_overdueInterest;
	}

	public void setV_ls_overdueInterest(double v_ls_overdueInterest) {
		this.v_ls_overdueInterest = v_ls_overdueInterest;
	}

	public double getV_ls_overduePenalty() {
		return v_ls_overduePenalty;
	}

	public void setV_ls_overduePenalty(double v_ls_overduePenalty) {
		this.v_ls_overduePenalty = v_ls_overduePenalty;
	}

	public double getV_ls_overduePrincipal() {
		return v_ls_overduePrincipal;
	}

	public void setV_ls_overduePrincipal(double v_ls_overduePrincipal) {
		this.v_ls_overduePrincipal = v_ls_overduePrincipal;
	}

	public double getV_ls_paidFee() {
		return v_ls_paidFee;
	}

	public void setV_ls_paidFee(double v_ls_paidFee) {
		this.v_ls_paidFee = v_ls_paidFee;
	}

	public double getV_ls_paidInterest() {
		return v_ls_paidInterest;
	}

	public void setV_ls_paidInterest(double v_ls_paidInterest) {
		this.v_ls_paidInterest = v_ls_paidInterest;
	}

	public double getV_ls_paidPenalty() {
		return v_ls_paidPenalty;
	}

	public void setV_ls_paidPenalty(double v_ls_paidPenalty) {
		this.v_ls_paidPenalty = v_ls_paidPenalty;
	}

	public double getV_ls_paidPrincipal() {
		return v_ls_paidPrincipal;
	}

	public void setV_ls_paidPrincipal(double v_ls_paidPrincipal) {
		this.v_ls_paidPrincipal = v_ls_paidPrincipal;
	}

	public double getV_ls_totalDisbursed() {
		return v_ls_totalDisbursed;
	}

	public void setV_ls_totalDisbursed(double v_ls_totalDisbursed) {
		this.v_ls_totalDisbursed = v_ls_totalDisbursed;
	}

	public double getV_ls_totalOutstanding() {
		return v_ls_totalOutstanding;
	}

	public void setV_ls_totalOutstanding(double v_ls_totalOutstanding) {
		this.v_ls_totalOutstanding = v_ls_totalOutstanding;
	}

	public double getV_ls_totalOverdue() {
		return v_ls_totalOverdue;
	}

	public void setV_ls_totalOverdue(double v_ls_totalOverdue) {
		this.v_ls_totalOverdue = v_ls_totalOverdue;
	}

	public double getV_ls_totalPaid() {
		return v_ls_totalPaid;
	}

	public void setV_ls_totalPaid(double v_ls_totalPaid) {
		this.v_ls_totalPaid = v_ls_totalPaid;
	}

	public Date getV_ls_onDate() {
		return v_ls_onDate;
	}

	public void setV_ls_onDate(Date v_ls_onDate) {
		this.v_ls_onDate = v_ls_onDate;
	}
}