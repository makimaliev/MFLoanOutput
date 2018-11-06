package kg.gov.mf.loan.output.report.model;

import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="loan_accrue_view")
@Immutable
public class LoanAccrueView extends DebtorView
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
	private Long v_acc_id;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_acc_fromDate;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_acc_toDate;

	@Column
	private Long v_acc_dayInPeriod;

	@Column
	private double v_acc_interestAccrued;

	@Column
	private double v_acc_penaltyAccrued;

	@Column
	private double v_acc_penaltyOnInterestOverdue;

	@Column
	private double v_acc_penaltyOnPrincipalOverdue;


	@Column
	private boolean v_acc_lastInstPassed;


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

	public Long getV_acc_id() {
		return v_acc_id;
	}

	public void setV_acc_id(Long v_acc_id) {
		this.v_acc_id = v_acc_id;
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

	public Long getV_acc_dayInPeriod() {
		return v_acc_dayInPeriod;
	}

	public void setV_acc_dayInPeriod(Long v_acc_dayInPeriod) {
		this.v_acc_dayInPeriod = v_acc_dayInPeriod;
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

	public double getV_acc_penaltyOnInterestOverdue() {
		return v_acc_penaltyOnInterestOverdue;
	}

	public void setV_acc_penaltyOnInterestOverdue(double v_acc_penaltyOnInterestOverdue) {
		this.v_acc_penaltyOnInterestOverdue = v_acc_penaltyOnInterestOverdue;
	}

	public double getV_acc_penaltyOnPrincipalOverdue() {
		return v_acc_penaltyOnPrincipalOverdue;
	}

	public void setV_acc_penaltyOnPrincipalOverdue(double v_acc_penaltyOnPrincipalOverdue) {
		this.v_acc_penaltyOnPrincipalOverdue = v_acc_penaltyOnPrincipalOverdue;
	}

	public boolean isV_acc_lastInstPassed() {
		return v_acc_lastInstPassed;
	}

	public void setV_acc_lastInstPassed(boolean v_acc_lastInstPassed) {
		this.v_acc_lastInstPassed = v_acc_lastInstPassed;
	}
}