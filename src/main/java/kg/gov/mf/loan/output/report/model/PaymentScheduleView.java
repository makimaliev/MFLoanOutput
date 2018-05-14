package kg.gov.mf.loan.output.report.model;

import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="payment_schedule_view")
@Immutable
public class PaymentScheduleView extends DebtorView
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
	private long v_ps_id;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_ps_expectedDate;

	@Column
	private double v_ps_collectedInterestPayment;

	@Column
	private double v_ps_collectedPenaltyPayment;

	@Column
	private double v_ps_disbursement;

	@Column
	private double v_ps_interestPayment;

	@Column
	private double v_ps_principalPayment;


	@Column
	private long v_ps_installmentStateId;

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

	public long getV_ps_id() {
		return v_ps_id;
	}

	public void setV_ps_id(long v_ps_id) {
		this.v_ps_id = v_ps_id;
	}

	public Date getV_ps_expectedDate() {
		return v_ps_expectedDate;
	}

	public void setV_ps_expectedDate(Date v_ps_expectedDate) {
		this.v_ps_expectedDate = v_ps_expectedDate;
	}

	public double getV_ps_collectedInterestPayment() {
		return v_ps_collectedInterestPayment;
	}

	public void setV_ps_collectedInterestPayment(double v_ps_collectedInterestPayment) {
		this.v_ps_collectedInterestPayment = v_ps_collectedInterestPayment;
	}

	public double getV_ps_collectedPenaltyPayment() {
		return v_ps_collectedPenaltyPayment;
	}

	public void setV_ps_collectedPenaltyPayment(double v_ps_collectedPenaltyPayment) {
		this.v_ps_collectedPenaltyPayment = v_ps_collectedPenaltyPayment;
	}

	public double getV_ps_disbursement() {
		return v_ps_disbursement;
	}

	public void setV_ps_disbursement(double v_ps_disbursement) {
		this.v_ps_disbursement = v_ps_disbursement;
	}

	public double getV_ps_interestPayment() {
		return v_ps_interestPayment;
	}

	public void setV_ps_interestPayment(double v_ps_interestPayment) {
		this.v_ps_interestPayment = v_ps_interestPayment;
	}

	public double getV_ps_principalPayment() {
		return v_ps_principalPayment;
	}

	public void setV_ps_principalPayment(double v_ps_principalPayment) {
		this.v_ps_principalPayment = v_ps_principalPayment;
	}

	public long getV_ps_installmentStateId() {
		return v_ps_installmentStateId;
	}

	public void setV_ps_installmentStateId(long v_ps_installmentStateId) {
		this.v_ps_installmentStateId = v_ps_installmentStateId;
	}
}