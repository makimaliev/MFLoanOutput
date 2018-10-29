package kg.gov.mf.loan.output.report.model;

import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="payment_view")
@Immutable
public class LoanDebtTransferView extends DebtorView
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
	private Long v_dt_id;

	@Column
	private String v_dt_number;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_dt_date;


	@Column
	private Long v_dt_goodsTypeId;


	@Column
	private Long v_dt_quantity;


	@Column
	private double v_dt_pricePerUnit;


	@Column
	private double v_dt_totalCost;


	@Column
	private Long v_dt_transferCreditId;

	@Column
	private Long v_dt_transferPaymentId;

	@Column
	private Long v_dt_transferPersonId;

	@Column
	private Long v_dt_unitTypeId;


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

	public Long getV_dt_id() {
		return v_dt_id;
	}

	public void setV_dt_id(Long v_dt_id) {
		this.v_dt_id = v_dt_id;
	}

	public String getV_dt_number() {
		return v_dt_number;
	}

	public void setV_dt_number(String v_dt_number) {
		this.v_dt_number = v_dt_number;
	}

	public Date getV_dt_date() {
		return v_dt_date;
	}

	public void setV_dt_date(Date v_dt_date) {
		this.v_dt_date = v_dt_date;
	}

	public Long getV_dt_goodsTypeId() {
		return v_dt_goodsTypeId;
	}

	public void setV_dt_goodsTypeId(Long v_dt_goodsTypeId) {
		this.v_dt_goodsTypeId = v_dt_goodsTypeId;
	}

	public Long getV_dt_quantity() {
		return v_dt_quantity;
	}

	public void setV_dt_quantity(Long v_dt_quantity) {
		this.v_dt_quantity = v_dt_quantity;
	}

	public double getV_dt_pricePerUnit() {
		return v_dt_pricePerUnit;
	}

	public void setV_dt_pricePerUnit(double v_dt_pricePerUnit) {
		this.v_dt_pricePerUnit = v_dt_pricePerUnit;
	}

	public double getV_dt_totalCost() {
		return v_dt_totalCost;
	}

	public void setV_dt_totalCost(double v_dt_totalCost) {
		this.v_dt_totalCost = v_dt_totalCost;
	}

	public Long getV_dt_transferCreditId() {
		return v_dt_transferCreditId;
	}

	public void setV_dt_transferCreditId(Long v_dt_transferCreditId) {
		this.v_dt_transferCreditId = v_dt_transferCreditId;
	}

	public Long getV_dt_transferPaymentId() {
		return v_dt_transferPaymentId;
	}

	public void setV_dt_transferPaymentId(Long v_dt_transferPaymentId) {
		this.v_dt_transferPaymentId = v_dt_transferPaymentId;
	}

	public Long getV_dt_transferPersonId() {
		return v_dt_transferPersonId;
	}

	public void setV_dt_transferPersonId(Long v_dt_transferPersonId) {
		this.v_dt_transferPersonId = v_dt_transferPersonId;
	}

	public Long getV_dt_unitTypeId() {
		return v_dt_unitTypeId;
	}

	public void setV_dt_unitTypeId(Long v_dt_unitTypeId) {
		this.v_dt_unitTypeId = v_dt_unitTypeId;
	}
}