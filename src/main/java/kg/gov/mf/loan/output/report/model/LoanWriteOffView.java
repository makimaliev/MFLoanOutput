package kg.gov.mf.loan.output.report.model;

import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="loan_write_off_view")
@Immutable
public class LoanWriteOffView extends DebtorView
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
	private Long v_wo_id;

	@Column
	private String v_wo_description;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_wo_date;

	@Column
	private double v_wo_principal;

	@Column
	private double v_wo_interest;

	@Column
	private double v_wo_penalty;

	@Column
	private double v_wo_fee;

	@Column
	private double v_wo_totalAmount;

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

	public Long getV_wo_id() {
		return v_wo_id;
	}

	public void setV_wo_id(Long v_wo_id) {
		this.v_wo_id = v_wo_id;
	}

	public String getV_wo_description() {
		return v_wo_description;
	}

	public void setV_wo_description(String v_wo_description) {
		this.v_wo_description = v_wo_description;
	}

	public Date getV_wo_date() {
		return v_wo_date;
	}

	public void setV_wo_date(Date v_wo_date) {
		this.v_wo_date = v_wo_date;
	}

	public double getV_wo_principal() {
		return v_wo_principal;
	}

	public void setV_wo_principal(double v_wo_principal) {
		this.v_wo_principal = v_wo_principal;
	}

	public double getV_wo_interest() {
		return v_wo_interest;
	}

	public void setV_wo_interest(double v_wo_interest) {
		this.v_wo_interest = v_wo_interest;
	}

	public double getV_wo_penalty() {
		return v_wo_penalty;
	}

	public void setV_wo_penalty(double v_wo_penalty) {
		this.v_wo_penalty = v_wo_penalty;
	}

	public double getV_wo_fee() {
		return v_wo_fee;
	}

	public void setV_wo_fee(double v_wo_fee) {
		this.v_wo_fee = v_wo_fee;
	}

	public double getV_wo_totalAmount() {
		return v_wo_totalAmount;
	}

	public void setV_wo_totalAmount(double v_wo_totalAmount) {
		this.v_wo_totalAmount = v_wo_totalAmount;
	}
}