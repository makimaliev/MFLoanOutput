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
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="loan_view")
@Immutable
public class LoanView
{
	@Id
	@Column
	private long v_loan_id;

	@Column
	private long v_debtor_id;

	@Column
	private long v_debtor_region_id;

	@Column
	private long v_debtor_district_id;

	@Column
	private String v_region_name;

	@Column
	private String v_district_name;

	public long getV_loan_id() {
		return v_loan_id;
	}

	public void setV_loan_id(long v_loan_id) {
		this.v_loan_id = v_loan_id;
	}

	public long getV_debtor_id() {
		return v_debtor_id;
	}

	public void setV_debtor_id(long v_debtor_id) {
		this.v_debtor_id = v_debtor_id;
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
}