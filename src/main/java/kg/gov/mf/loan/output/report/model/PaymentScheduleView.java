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
	private Long v_ps_id;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_ps_expected_date;

	@Column
	private double v_ps_collected_interest_payment;

	@Column
	private double v_ps_collected_penalty_payment;

	@Column
	private double v_ps_disbursement;

	@Column
	private double v_ps_interest_payment;

	@Column
	private double v_ps_principal_payment;


	@Column
	private Long v_ps_installment_state_id;


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

	public Long getV_ps_id() {
		return v_ps_id;
	}

	public void setV_ps_id(Long v_ps_id) {
		this.v_ps_id = v_ps_id;
	}

	public Date getV_ps_expected_date() {
		return v_ps_expected_date;
	}

	public void setV_ps_expected_date(Date v_ps_expected_date) {
		this.v_ps_expected_date = v_ps_expected_date;
	}

	public double getV_ps_collected_interest_payment() {
		return v_ps_collected_interest_payment;
	}

	public void setV_ps_collected_interest_payment(double v_ps_collected_interest_payment) {
		this.v_ps_collected_interest_payment = v_ps_collected_interest_payment;
	}

	public double getV_ps_collected_penalty_payment() {
		return v_ps_collected_penalty_payment;
	}

	public void setV_ps_collected_penalty_payment(double v_ps_collected_penalty_payment) {
		this.v_ps_collected_penalty_payment = v_ps_collected_penalty_payment;
	}

	public double getV_ps_disbursement() {
		return v_ps_disbursement;
	}

	public void setV_ps_disbursement(double v_ps_disbursement) {
		this.v_ps_disbursement = v_ps_disbursement;
	}

	public double getV_ps_interest_payment() {
		return v_ps_interest_payment;
	}

	public void setV_ps_interest_payment(double v_ps_interest_payment) {
		this.v_ps_interest_payment = v_ps_interest_payment;
	}

	public double getV_ps_principal_payment() {
		return v_ps_principal_payment;
	}

	public void setV_ps_principal_payment(double v_ps_principal_payment) {
		this.v_ps_principal_payment = v_ps_principal_payment;
	}

	public Long getV_ps_installment_state_id() {
		return v_ps_installment_state_id;
	}

	public void setV_ps_installment_state_id(Long v_ps_installment_state_id) {
		this.v_ps_installment_state_id = v_ps_installment_state_id;
	}

	public Long getV_loan_fin_group_id() {
		return v_loan_fin_group_id;
	}

	public void setV_loan_fin_group_id(Long v_loan_fin_group_id) {
		this.v_loan_fin_group_id = v_loan_fin_group_id;
	}
}