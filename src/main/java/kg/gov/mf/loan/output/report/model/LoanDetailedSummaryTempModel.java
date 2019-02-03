package kg.gov.mf.loan.output.report.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;

@Entity
public class LoanDetailedSummaryTempModel
{

	private long loan_id;
	private long parent_id;

	private long state_id;

	@Id
	private long id;

	private Long version = 1L;
	private Date onDate;

	private Double totalDisbursement;
	private Double loanAmount;

	private Double totalPrincipalPaid;
	private Double totalInterestPaid;
	private Double totalPenaltyPaid;

	private Double totalPrincipalWriteOff;
	private Double totalInterestAccruedOnInterestPayment;

	private Double principalOutstanding;
	private Double principalOverdue;

	private Double interestOutstanding;
	private Double interestOverdue;

	private Double penaltyOutstanding;
	private Double penaltyOverdue;


	public long getLoan_id() {
		return loan_id;
	}

	public void setLoan_id(long loan_id) {
		this.loan_id = loan_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getOnDate() {
		return onDate;
	}

	public void setOnDate(Date onDate) {
		this.onDate = onDate;
	}

	public Double getTotalDisbursement() {
		return totalDisbursement;
	}

	public void setTotalDisbursement(Double totalDisbursement) {
		this.totalDisbursement = totalDisbursement;
	}

	public Double getTotalPrincipalPaid() {
		return totalPrincipalPaid;
	}

	public void setTotalPrincipalPaid(Double totalPrincipalPaid) {
		this.totalPrincipalPaid = totalPrincipalPaid;
	}

	public Double getTotalInterestPaid() {
		return totalInterestPaid;
	}

	public void setTotalInterestPaid(Double totalInterestPaid) {
		this.totalInterestPaid = totalInterestPaid;
	}

	public Double getTotalPenaltyPaid() {
		return totalPenaltyPaid;
	}

	public void setTotalPenaltyPaid(Double totalPenaltyPaid) {
		this.totalPenaltyPaid = totalPenaltyPaid;
	}

	public Double getTotalPrincipalWriteOff() {
		return totalPrincipalWriteOff;
	}

	public void setTotalPrincipalWriteOff(Double totalPrincipalWriteOff) {
		this.totalPrincipalWriteOff = totalPrincipalWriteOff;
	}

	public Double getTotalInterestAccruedOnInterestPayment() {
		return totalInterestAccruedOnInterestPayment;
	}

	public void setTotalInterestAccruedOnInterestPayment(Double totalInterestAccruedOnInterestPayment) {
		this.totalInterestAccruedOnInterestPayment = totalInterestAccruedOnInterestPayment;
	}

	public Double getPrincipalOutstanding() {
		return principalOutstanding;
	}

	public void setPrincipalOutstanding(Double principalOutstanding) {
		this.principalOutstanding = principalOutstanding;
	}

	public Double getPrincipalOverdue() {
		return principalOverdue;
	}

	public void setPrincipalOverdue(Double principalOverdue) {
		this.principalOverdue = principalOverdue;
	}

	public Double getInterestOutstanding() {
		return interestOutstanding;
	}

	public void setInterestOutstanding(Double interestOutstanding) {
		this.interestOutstanding = interestOutstanding;
	}

	public Double getInterestOverdue() {
		return interestOverdue;
	}

	public void setInterestOverdue(Double interestOverdue) {
		this.interestOverdue = interestOverdue;
	}

	public Double getPenaltyOutstanding() {
		return penaltyOutstanding;
	}

	public void setPenaltyOutstanding(Double penaltyOutstanding) {
		this.penaltyOutstanding = penaltyOutstanding;
	}

	public Double getPenaltyOverdue() {
		return penaltyOverdue;
	}

	public void setPenaltyOverdue(Double penaltyOverdue) {
		this.penaltyOverdue = penaltyOverdue;
	}

	public long getParent_id() {
		return parent_id;
	}

	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public long getState_id() {
		return state_id;
	}

	public void setState_id(long state_id) {
		this.state_id = state_id;
	}
}