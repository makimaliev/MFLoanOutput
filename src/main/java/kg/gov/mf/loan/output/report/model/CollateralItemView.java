package kg.gov.mf.loan.output.report.model;

import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="collateral_item_view")
@Immutable
public class CollateralItemView extends DebtorView
{
	@Column
	private Long v_ca_id;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_ca_agreementDate;

	@Column
	private String v_ca_agreementNumber;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_ca_arrestRegDate;

	@Column
	private String v_ca_arrestRegNumber;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_ca_collateralOfficeRegDate;

	@Column
	private String v_ca_collateralOfficeRegNumber;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_ca_notaryOfficeRegDate;

	@Column
	private String v_ca_notaryOfficeRegNumber;






	@Id
	@Column
	private Long v_ci_id;



	@Column
	private double v_ci_collateralValue;

	@Column
	private double v_ci_demand_rate;

	@Column
	private String v_ci_description;


	@Column
	private double v_ci_estimatedValue;

	@Column
	private String v_ci_name;

	@Column
	private double v_ci_quantity;

	@Column
	private double v_ci_risk_rate;


	@Column
	private Long v_ci_collateralAgreementId;



	@Column
	private Long v_ci_itemTypeId;

	@Column
	private Long v_ci_quantityTypeId;


	public Long getV_ca_id() {
		return v_ca_id;
	}

	public void setV_ca_id(Long v_ca_id) {
		this.v_ca_id = v_ca_id;
	}

	public Date getV_ca_agreementDate() {
		return v_ca_agreementDate;
	}

	public void setV_ca_agreementDate(Date v_ca_agreementDate) {
		this.v_ca_agreementDate = v_ca_agreementDate;
	}

	public String getV_ca_agreementNumber() {
		return v_ca_agreementNumber;
	}

	public void setV_ca_agreementNumber(String v_ca_agreementNumber) {
		this.v_ca_agreementNumber = v_ca_agreementNumber;
	}

	public Date getV_ca_arrestRegDate() {
		return v_ca_arrestRegDate;
	}

	public void setV_ca_arrestRegDate(Date v_ca_arrestRegDate) {
		this.v_ca_arrestRegDate = v_ca_arrestRegDate;
	}

	public String getV_ca_arrestRegNumber() {
		return v_ca_arrestRegNumber;
	}

	public void setV_ca_arrestRegNumber(String v_ca_arrestRegNumber) {
		this.v_ca_arrestRegNumber = v_ca_arrestRegNumber;
	}

	public Date getV_ca_collateralOfficeRegDate() {
		return v_ca_collateralOfficeRegDate;
	}

	public void setV_ca_collateralOfficeRegDate(Date v_ca_collateralOfficeRegDate) {
		this.v_ca_collateralOfficeRegDate = v_ca_collateralOfficeRegDate;
	}

	public String getV_ca_collateralOfficeRegNumber() {
		return v_ca_collateralOfficeRegNumber;
	}

	public void setV_ca_collateralOfficeRegNumber(String v_ca_collateralOfficeRegNumber) {
		this.v_ca_collateralOfficeRegNumber = v_ca_collateralOfficeRegNumber;
	}

	public Date getV_ca_notaryOfficeRegDate() {
		return v_ca_notaryOfficeRegDate;
	}

	public void setV_ca_notaryOfficeRegDate(Date v_ca_notaryOfficeRegDate) {
		this.v_ca_notaryOfficeRegDate = v_ca_notaryOfficeRegDate;
	}

	public String getV_ca_notaryOfficeRegNumber() {
		return v_ca_notaryOfficeRegNumber;
	}

	public void setV_ca_notaryOfficeRegNumber(String v_ca_notaryOfficeRegNumber) {
		this.v_ca_notaryOfficeRegNumber = v_ca_notaryOfficeRegNumber;
	}

	public Long getV_ci_id() {
		return v_ci_id;
	}

	public void setV_ci_id(Long v_ci_id) {
		this.v_ci_id = v_ci_id;
	}

	public double getV_ci_collateralValue() {
		return v_ci_collateralValue;
	}

	public void setV_ci_collateralValue(double v_ci_collateralValue) {
		this.v_ci_collateralValue = v_ci_collateralValue;
	}

	public double getV_ci_demand_rate() {
		return v_ci_demand_rate;
	}

	public void setV_ci_demand_rate(double v_ci_demand_rate) {
		this.v_ci_demand_rate = v_ci_demand_rate;
	}

	public String getV_ci_description() {
		return v_ci_description;
	}

	public void setV_ci_description(String v_ci_description) {
		this.v_ci_description = v_ci_description;
	}

	public double getV_ci_estimatedValue() {
		return v_ci_estimatedValue;
	}

	public void setV_ci_estimatedValue(double v_ci_estimatedValue) {
		this.v_ci_estimatedValue = v_ci_estimatedValue;
	}

	public String getV_ci_name() {
		return v_ci_name;
	}

	public void setV_ci_name(String v_ci_name) {
		this.v_ci_name = v_ci_name;
	}

	public double getV_ci_quantity() {
		return v_ci_quantity;
	}

	public void setV_ci_quantity(double v_ci_quantity) {
		this.v_ci_quantity = v_ci_quantity;
	}

	public double getV_ci_risk_rate() {
		return v_ci_risk_rate;
	}

	public void setV_ci_risk_rate(double v_ci_risk_rate) {
		this.v_ci_risk_rate = v_ci_risk_rate;
	}

	public Long getV_ci_collateralAgreementId() {
		return v_ci_collateralAgreementId;
	}

	public void setV_ci_collateralAgreementId(Long v_ci_collateralAgreementId) {
		this.v_ci_collateralAgreementId = v_ci_collateralAgreementId;
	}

	public Long getV_ci_itemTypeId() {
		return v_ci_itemTypeId;
	}

	public void setV_ci_itemTypeId(Long v_ci_itemTypeId) {
		this.v_ci_itemTypeId = v_ci_itemTypeId;
	}

	public Long getV_ci_quantityTypeId() {
		return v_ci_quantityTypeId;
	}

	public void setV_ci_quantityTypeId(Long v_ci_quantityTypeId) {
		this.v_ci_quantityTypeId = v_ci_quantityTypeId;
	}
}