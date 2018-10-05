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
	private Long v_loan_id;

	@Column
	private Double v_loan_amount;

	@Column
	private String v_loan_reg_number;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_loan_reg_date;

	@Column
	private Long v_loan_supervisor_id;

	@Column
	private Long v_loan_currency_id;

	@Column
	private Long v_loan_state_id;

	@Column
	private Long v_loan_type_id;

	@Column
	private Long v_credit_order_type_id;

	@Column
	private String v_credit_order_reg_number;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_credit_order_reg_date;

	@Id
	@Column
	private Long v_ls_id;

	@Column
	private  double v_ls_loan_amount;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_ls_on_date;

	@Column
	private  double v_ls_outstading_fee;

	@Column
	private  double v_ls_outstading_interest;



	@Column
	private  double v_ls_outstading_penalty;



	@Column
	private  double v_ls_outstading_principal;


	@Column
	private  double v_ls_overdue_fee;


	@Column
	private  double v_ls_overdue_interest;


	@Column
	private  double v_ls_overdue_penalty;


	@Column
	private  double v_ls_overdue_principal;


	@Column
	private  double v_ls_paid_fee;

	@Column
	private  double v_ls_paid_interest;


	@Column
	private  double v_ls_paid_penalty;

	@Column
	private  double v_ls_paid_principal;

	@Column
	private  double v_ls_total_disbursed;

	@Column
	private  double v_ls_total_outstanding;

	@Column
	private  double v_ls_total_overdue;

	@Column
	private  double v_ls_total_paid;


	public Long getV_loan_id() {
		return v_loan_id;
	}

	public void setV_loan_id(Long v_loan_id) {
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

	public Long getV_loan_supervisor_id() {
		return v_loan_supervisor_id;
	}

	public void setV_loan_supervisor_id(Long v_loan_supervisor_id) {
		this.v_loan_supervisor_id = v_loan_supervisor_id;
	}

	public Long getV_loan_currency_id() {
		return v_loan_currency_id;
	}

	public void setV_loan_currency_id(Long v_loan_currency_id) {
		this.v_loan_currency_id = v_loan_currency_id;
	}

	public Long getV_loan_state_id() {
		return v_loan_state_id;
	}

	public void setV_loan_state_id(Long v_loan_state_id) {
		this.v_loan_state_id = v_loan_state_id;
	}

	public Long getV_loan_type_id() {
		return v_loan_type_id;
	}

	public void setV_loan_type_id(Long v_loan_type_id) {
		this.v_loan_type_id = v_loan_type_id;
	}

	public Long getV_credit_order_type_id() {
		return v_credit_order_type_id;
	}

	public void setV_credit_order_type_id(Long v_credit_order_type_id) {
		this.v_credit_order_type_id = v_credit_order_type_id;
	}

	public String getV_credit_order_reg_number() {
		return v_credit_order_reg_number;
	}

	public void setV_credit_order_reg_number(String v_credit_order_reg_number) {
		this.v_credit_order_reg_number = v_credit_order_reg_number;
	}

	public Date getV_credit_order_reg_date() {
		return v_credit_order_reg_date;
	}

	public void setV_credit_order_reg_date(Date v_credit_order_reg_date) {
		this.v_credit_order_reg_date = v_credit_order_reg_date;
	}

	public Long getV_ls_id() {
		return v_ls_id;
	}

	public void setV_ls_id(Long v_ls_id) {
		this.v_ls_id = v_ls_id;
	}

	public double getV_ls_loan_amount() {
		return v_ls_loan_amount;
	}

	public void setV_ls_loan_amount(double v_ls_loan_amount) {
		this.v_ls_loan_amount = v_ls_loan_amount;
	}

	public Date getV_ls_on_date() {
		return v_ls_on_date;
	}

	public void setV_ls_on_date(Date v_ls_on_date) {
		this.v_ls_on_date = v_ls_on_date;
	}

	public double getV_ls_outstading_fee() {
		return v_ls_outstading_fee;
	}

	public void setV_ls_outstading_fee(double v_ls_outstading_fee) {
		this.v_ls_outstading_fee = v_ls_outstading_fee;
	}

	public double getV_ls_outstading_interest() {
		return v_ls_outstading_interest;
	}

	public void setV_ls_outstading_interest(double v_ls_outstading_interest) {
		this.v_ls_outstading_interest = v_ls_outstading_interest;
	}

	public double getV_ls_outstading_penalty() {
		return v_ls_outstading_penalty;
	}

	public void setV_ls_outstading_penalty(double v_ls_outstading_penalty) {
		this.v_ls_outstading_penalty = v_ls_outstading_penalty;
	}

	public double getV_ls_outstading_principal() {
		return v_ls_outstading_principal;
	}

	public void setV_ls_outstading_principal(double v_ls_outstading_principal) {
		this.v_ls_outstading_principal = v_ls_outstading_principal;
	}

	public double getV_ls_overdue_fee() {
		return v_ls_overdue_fee;
	}

	public void setV_ls_overdue_fee(double v_ls_overdue_fee) {
		this.v_ls_overdue_fee = v_ls_overdue_fee;
	}

	public double getV_ls_overdue_interest() {
		return v_ls_overdue_interest;
	}

	public void setV_ls_overdue_interest(double v_ls_overdue_interest) {
		this.v_ls_overdue_interest = v_ls_overdue_interest;
	}

	public double getV_ls_overdue_penalty() {
		return v_ls_overdue_penalty;
	}

	public void setV_ls_overdue_penalty(double v_ls_overdue_penalty) {
		this.v_ls_overdue_penalty = v_ls_overdue_penalty;
	}

	public double getV_ls_overdue_principal() {
		return v_ls_overdue_principal;
	}

	public void setV_ls_overdue_principal(double v_ls_overdue_principal) {
		this.v_ls_overdue_principal = v_ls_overdue_principal;
	}

	public double getV_ls_paid_fee() {
		return v_ls_paid_fee;
	}

	public void setV_ls_paid_fee(double v_ls_paid_fee) {
		this.v_ls_paid_fee = v_ls_paid_fee;
	}

	public double getV_ls_paid_interest() {
		return v_ls_paid_interest;
	}

	public void setV_ls_paid_interest(double v_ls_paid_interest) {
		this.v_ls_paid_interest = v_ls_paid_interest;
	}

	public double getV_ls_paid_penalty() {
		return v_ls_paid_penalty;
	}

	public void setV_ls_paid_penalty(double v_ls_paid_penalty) {
		this.v_ls_paid_penalty = v_ls_paid_penalty;
	}

	public double getV_ls_paid_principal() {
		return v_ls_paid_principal;
	}

	public void setV_ls_paid_principal(double v_ls_paid_principal) {
		this.v_ls_paid_principal = v_ls_paid_principal;
	}

	public double getV_ls_total_disbursed() {
		return v_ls_total_disbursed;
	}

	public void setV_ls_total_disbursed(double v_ls_total_disbursed) {
		this.v_ls_total_disbursed = v_ls_total_disbursed;
	}

	public double getV_ls_total_outstanding() {
		return v_ls_total_outstanding;
	}

	public void setV_ls_total_outstanding(double v_ls_total_outstanding) {
		this.v_ls_total_outstanding = v_ls_total_outstanding;
	}

	public double getV_ls_total_overdue() {
		return v_ls_total_overdue;
	}

	public void setV_ls_total_overdue(double v_ls_total_overdue) {
		this.v_ls_total_overdue = v_ls_total_overdue;
	}

	public double getV_ls_total_paid() {
		return v_ls_total_paid;
	}

	public void setV_ls_total_paid(double v_ls_total_paid) {
		this.v_ls_total_paid = v_ls_total_paid;
	}
}