package kg.gov.mf.loan.output.report.model;

import kg.gov.mf.loan.admin.org.model.Address;
import kg.gov.mf.loan.admin.org.model.District;
import kg.gov.mf.loan.admin.org.model.Region;
import kg.gov.mf.loan.admin.org.service.DistrictService;
import kg.gov.mf.loan.admin.org.service.OrganizationService;
import kg.gov.mf.loan.admin.org.service.PersonService;
import kg.gov.mf.loan.admin.org.service.RegionService;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import org.hibernate.annotations.Immutable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="loan_view")
@Immutable
public class LoanView extends DebtorView
{
	@Id
	@Column
	private Long v_loan_id;

	@Column
	private Double v_loan_amount;

	@Column
	private String v_loan_reg_number;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_loan_reg_date;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_last_date;


	@Column
	private Long v_loan_supervisor_id;

	@Column
	private Long v_loan_currency_id;

	@Column
	private Long v_loan_state_id;

	@Column
	private Long v_loan_type_id;

	@Column
	private Short v_loan_class_id;


	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_loan_close_date;

	@Column
	private Double v_loan_close_rate;

	@Column
	private Long v_credit_order_type_id;

	@Column
	private String v_credit_order_reg_number;

	@Column
	private Long v_loan_fin_group_id;

	@Column
	private Long v_loan_fund_id;


	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_credit_order_reg_date;


	@Column
	private Long v_supervisor_description_id;

	@Column
	private Long v_collection_description_id;

	@Column
	private Long v_collateral_description_id;






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

    public Long getV_loan_fin_group_id() {
        return v_loan_fin_group_id;
    }

    public void setV_loan_fin_group_id(Long v_loan_fin_group_id) {
        this.v_loan_fin_group_id = v_loan_fin_group_id;
    }

	public Short getV_loan_class_id() {
		return v_loan_class_id;
	}

	public void setV_loan_class_id(Short v_loan_class_id) {
		this.v_loan_class_id = v_loan_class_id;
	}

	public Date getV_loan_close_date() {
		return v_loan_close_date;
	}

	public void setV_loan_close_date(Date v_loan_close_date) {
		this.v_loan_close_date = v_loan_close_date;
	}

	public Double getV_loan_close_rate() {
		return v_loan_close_rate;
	}

	public void setV_loan_close_rate(Double v_loan_close_rate) {
		this.v_loan_close_rate = v_loan_close_rate;
	}

	public Date getV_last_date() {
		return v_last_date;
	}

	public void setV_last_date(Date v_last_date) {
		this.v_last_date = v_last_date;
	}

	public Long getV_loan_fund_id() {
		return v_loan_fund_id;
	}

	public void setV_loan_fund_id(Long v_loan_fund_id) {
		this.v_loan_fund_id = v_loan_fund_id;
	}

	public Long getV_supervisor_description_id() {
		return v_supervisor_description_id;
	}

	public void setV_supervisor_description_id(Long v_supervisor_description_id) {
		this.v_supervisor_description_id = v_supervisor_description_id;
	}

	public Long getV_collection_description_id() {
		return v_collection_description_id;
	}

	public void setV_collection_description_id(Long v_collection_description_id) {
		this.v_collection_description_id = v_collection_description_id;
	}

	public Long getV_collateral_description_id() {
		return v_collateral_description_id;
	}

	public void setV_collateral_description_id(Long v_collateral_description_id) {
		this.v_collateral_description_id = v_collateral_description_id;
	}
}