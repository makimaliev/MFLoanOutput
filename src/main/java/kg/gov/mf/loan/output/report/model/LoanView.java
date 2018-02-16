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
public class LoanView
{
	@Id
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
	private long v_debtor_id;

	@Column
	private long v_debtor_type_id;

	@Column
	private long v_debtor_org_form_id;

	@Column
	private long v_debtor_work_sector_id;

	@Column
	private String v_debtor_name;

	@Column
	private long v_debtor_region_id;

	@Column
	private long v_debtor_district_id;
	@Column
	private long v_debtor_aokmotu_id;

	@Column
	private long v_debtor_village_id;

	@Column
	private String v_region_name;

	@Column
	private String v_district_name;


	@Column
	private long v_credit_order_type_id;

	@Column
	private String v_credit_order_regNumber;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_credit_order_regDate;

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

	public long getV_debtor_id() {
		return v_debtor_id;
	}

	public void setV_debtor_id(long v_debtor_id) {
		this.v_debtor_id = v_debtor_id;
	}

	public String getV_debtor_name() {
		return v_debtor_name;
	}

	public void setV_debtor_name(String v_debtor_name) {
		this.v_debtor_name = v_debtor_name;
	}

	public long getV_debtor_region_id() {
		return v_debtor_region_id;
	}

	public void setV_debtor_region_id(long v_debtor_region_id) {
		this.v_debtor_region_id = v_debtor_region_id;
	}

	public long getV_debtor_district_id() {
		return v_debtor_district_id;
	}

	public void setV_debtor_district_id(long v_debtor_district_id) {
		this.v_debtor_district_id = v_debtor_district_id;
	}

	public String getV_region_name() {
		return v_region_name;
	}

	public void setV_region_name(String v_region_name) {
		this.v_region_name = v_region_name;
	}

	public String getV_district_name() {
		return v_district_name;
	}

	public void setV_district_name(String v_district_name) {
		this.v_district_name = v_district_name;
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

	public long getV_debtor_type_id() {
		return v_debtor_type_id;
	}

	public void setV_debtor_type_id(long v_debtor_type_id) {
		this.v_debtor_type_id = v_debtor_type_id;
	}

	public long getV_debtor_org_form_id() {
		return v_debtor_org_form_id;
	}

	public void setV_debtor_org_form_id(long v_debtor_org_form_id) {
		this.v_debtor_org_form_id = v_debtor_org_form_id;
	}

	public long getV_debtor_work_sector_id() {
		return v_debtor_work_sector_id;
	}

	public void setV_debtor_work_sector_id(long v_debtor_work_sector_id) {
		this.v_debtor_work_sector_id = v_debtor_work_sector_id;
	}

	public long getV_debtor_aokmotu_id() {
		return v_debtor_aokmotu_id;
	}

	public void setV_debtor_aokmotu_id(long v_debtor_aokmotu_id) {
		this.v_debtor_aokmotu_id = v_debtor_aokmotu_id;
	}

	public long getV_debtor_village_id() {
		return v_debtor_village_id;
	}

	public void setV_debtor_village_id(long v_debtor_village_id) {
		this.v_debtor_village_id = v_debtor_village_id;
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
}