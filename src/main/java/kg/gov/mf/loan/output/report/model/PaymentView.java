package kg.gov.mf.loan.output.report.model;

import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="payment_view")
@Immutable
public class PaymentView extends DebtorView
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
	private long v_payment_id;

	@Column
	private String v_payment_number;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_payment_date;

	@Column
	private long v_payment_type_id;

	@Column
	private String v_payment_type_name;

	@Column
	private double v_payment_principal;

	@Column
	private double v_payment_interest;

	@Column
	private double v_payment_penalty;

	@Column
	private double v_payment_fee;

	@Column
	private double v_payment_total_amount;


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

	public long getV_payment_id() {
		return v_payment_id;
	}

	public void setV_payment_id(long v_payment_id) {
		this.v_payment_id = v_payment_id;
	}

	public String getV_payment_number() {
		return v_payment_number;
	}

	public void setV_payment_number(String v_payment_number) {
		this.v_payment_number = v_payment_number;
	}

	public Date getV_payment_date() {
		return v_payment_date;
	}

	public void setV_payment_date(Date v_payment_date) {
		this.v_payment_date = v_payment_date;
	}

	public long getV_payment_type_id() {
		return v_payment_type_id;
	}

	public void setV_payment_type_id(long v_payment_type_id) {
		this.v_payment_type_id = v_payment_type_id;
	}

	public String getV_payment_type_name() {
		return v_payment_type_name;
	}

	public void setV_payment_type_name(String v_payment_type_name) {
		this.v_payment_type_name = v_payment_type_name;
	}

	public double getV_payment_principal() {
		return v_payment_principal;
	}

	public void setV_payment_principal(double v_payment_principal) {
		this.v_payment_principal = v_payment_principal;
	}

	public double getV_payment_interest() {
		return v_payment_interest;
	}

	public void setV_payment_interest(double v_payment_interest) {
		this.v_payment_interest = v_payment_interest;
	}

	public double getV_payment_penalty() {
		return v_payment_penalty;
	}

	public void setV_payment_penalty(double v_payment_penalty) {
		this.v_payment_penalty = v_payment_penalty;
	}

	public double getV_payment_fee() {
		return v_payment_fee;
	}

	public void setV_payment_fee(double v_payment_fee) {
		this.v_payment_fee = v_payment_fee;
	}

	public double getV_payment_total_amount() {
		return v_payment_total_amount;
	}

	public void setV_payment_total_amount(double v_payment_total_amount) {
		this.v_payment_total_amount = v_payment_total_amount;
	}
}