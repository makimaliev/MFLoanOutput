package kg.gov.mf.loan.output.report.model;

import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="supervisor_plan_view")
@Immutable
public class SupervisorPlanView extends DebtorView
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
	private Long v_sp_id;

	@Column
	private String v_sp_description;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_sp_date;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_sp_reg_date;


	@Column
	private Long v_sp_reg_by_id;

	@Column
	private double v_sp_principal;

	@Column
	private double v_sp_interest;

	@Column
	private double v_sp_penalty;

	@Column
	private double v_sp_fee;

	@Column
	private double v_sp_amount;

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

	public Long getV_sp_id() {
		return v_sp_id;
	}

	public void setV_sp_id(Long v_sp_id) {
		this.v_sp_id = v_sp_id;
	}

	public String getV_sp_description() {
		return v_sp_description;
	}

	public void setV_sp_description(String v_sp_description) {
		this.v_sp_description = v_sp_description;
	}

	public Date getV_sp_date() {
		return v_sp_date;
	}

	public void setV_sp_date(Date v_sp_date) {
		this.v_sp_date = v_sp_date;
	}

	public Date getV_sp_reg_date() {
		return v_sp_reg_date;
	}

	public void setV_sp_reg_date(Date v_sp_reg_date) {
		this.v_sp_reg_date = v_sp_reg_date;
	}

	public Long getV_sp_reg_by_id() {
		return v_sp_reg_by_id;
	}

	public void setV_sp_reg_by_id(Long v_sp_reg_by_id) {
		this.v_sp_reg_by_id = v_sp_reg_by_id;
	}

	public double getV_sp_principal() {
		return v_sp_principal;
	}

	public void setV_sp_principal(double v_sp_principal) {
		this.v_sp_principal = v_sp_principal;
	}

	public double getV_sp_interest() {
		return v_sp_interest;
	}

	public void setV_sp_interest(double v_sp_interest) {
		this.v_sp_interest = v_sp_interest;
	}

	public double getV_sp_penalty() {
		return v_sp_penalty;
	}

	public void setV_sp_penalty(double v_sp_penalty) {
		this.v_sp_penalty = v_sp_penalty;
	}

	public double getV_sp_fee() {
		return v_sp_fee;
	}

	public void setV_sp_fee(double v_sp_fee) {
		this.v_sp_fee = v_sp_fee;
	}

	public double getV_sp_amount() {
		return v_sp_amount;
	}

	public void setV_sp_amount(double v_sp_amount) {
		this.v_sp_amount = v_sp_amount;
	}
}