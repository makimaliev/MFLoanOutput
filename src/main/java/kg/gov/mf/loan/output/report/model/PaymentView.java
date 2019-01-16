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
	private  Long v_loan_fin_group_id;

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
	private Long v_payment_id;

	@Column
	private String v_payment_number;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_payment_date;

	@Column
	private Long v_payment_type_id;

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

	@Column
	private double v_payment_exchange_rate;

	@Column
	private boolean v_payment_in_loan_currency;


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

	public Long getV_payment_id() {
		return v_payment_id;
	}

	public void setV_payment_id(Long v_payment_id) {
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

	public Long getV_payment_type_id() {
		return v_payment_type_id;
	}

	public void setV_payment_type_id(Long v_payment_type_id) {
		this.v_payment_type_id = v_payment_type_id;
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

	public double getV_payment_exchange_rate() {
		return v_payment_exchange_rate;
	}

	public void setV_payment_exchange_rate(double v_payment_exchange_rate) {
		this.v_payment_exchange_rate = v_payment_exchange_rate;
	}

	public boolean isV_payment_in_loan_currency() {
		return v_payment_in_loan_currency;
	}

	public void setV_payment_in_loan_currency(boolean v_payment_in_loan_currency) {
		this.v_payment_in_loan_currency = v_payment_in_loan_currency;
	}

	public Long getV_loan_fin_group_id() {
		return v_loan_fin_group_id;
	}

	public void setV_loan_fin_group_id(Long v_loan_fin_group_id) {
		this.v_loan_fin_group_id = v_loan_fin_group_id;
	}
}