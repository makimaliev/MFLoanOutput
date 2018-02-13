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
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


public class LoanData
{
	@Autowired
	LoanService loanService;

	@Autowired
	PersonService personService;

	@Autowired
	OrganizationService organizationService;

	@Autowired
	RegionService regionService;

	@Autowired
	DistrictService districtService;

	private long loan_id;

	private long debtor_id;

	private long region_id;

	private long district_id;

	private Loan loan;

	private Debtor debtor;

	private Region region;

	private District district;


	public LoanData(Loan loanTemp)
	{
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

		loan_id = loanTemp.getId();

		loan = loanTemp;


		debtor_id = loanTemp.getDebtor().getId();

		debtor = loanTemp.getDebtor();

		Address address = null;

		if(loanTemp.getDebtor().getOwner().getOwnerType().name()=="PERSON")
		{
			address=personService.findById((long)loanTemp.getDebtor().getOwner().getEntityId()).getAddress();
		}
		else
		{
			address=organizationService.findById((long)loanTemp.getDebtor().getOwner().getEntityId()).getAddress();
		}

		region_id = address.getRegion().getId();

		region = address.getRegion();

		district_id = address.getDistrict().getId();

		district = address.getDistrict();

	}


	public long getLoan_id() {
		return loan_id;
	}

	public void setLoan_id(long loan_id) {
		this.loan_id = loan_id;
	}

	public long getDebtor_id() {
		return debtor_id;
	}

	public void setDebtor_id(long debtor_id) {
		this.debtor_id = debtor_id;
	}

	public long getRegion_id() {
		return region_id;
	}

	public void setRegion_id(long region_id) {
		this.region_id = region_id;
	}

	public long getDistrict_id() {
		return district_id;
	}

	public void setDistrict_id(long district_id) {
		this.district_id = district_id;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public Debtor getDebtor() {
		return debtor;
	}

	public void setDebtor(Debtor debtor) {
		this.debtor = debtor;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}
}