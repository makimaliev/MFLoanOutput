package kg.gov.mf.loan.output.report.model;

import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="document_view")
@Immutable
public class DocumentView extends ReportView
{

	@Id
	@Column
	private Long v_doc_id;

	@Column
	private String v_doc_description;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_doc_documentDueDate;

	@Column
	private String v_doc_documentState;

	@Column
	private String v_doc_indexNo;

	@Column
	private Long v_doc_owner;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_doc_receiverRegisteredDate;

	@Column
	private String v_doc_receiverRegisteredNumber;

	@DateTimeFormat(pattern = "dd.mm.yyyy")
	@Temporal(TemporalType.DATE)
	private Date v_doc_senderRegisteredDate;

	@Column
	private String v_doc_senderRegisteredNumber;

	@Column
	private String v_doc_title;


	@Column
	private Long v_doc_documentType;


	@Column
	private Long v_doc_documentSubType;


	@Column
	private Long v_doc_receiverExecutor;

	@Column
	private Long v_doc_receiverResponsible;


	@Column
	private Long v_doc_senderExecutor;

	@Column
	private Long v_doc_senderResponsible;

	@Column
	private String v_doc_senderResponsibleName;

	@Column
	private String v_doc_receiverResponsibleName;


	@Column
	private String v_doc_sender_responsible_name;


	@Column
	private String v_doc_sender_executor_name;

	@Column
	private String v_doc_receiver_responsible_name;

	@Column
	private String v_doc_receiver_executor_name;

	@Column
	private Long v_doc_document_user_id;

	@Column
	private Long v_doc_document_department_id;


	public Long getV_doc_id() {
		return v_doc_id;
	}

	public void setV_doc_id(Long v_doc_id) {
		this.v_doc_id = v_doc_id;
	}

	public String getV_doc_description() {
		return v_doc_description;
	}

	public void setV_doc_description(String v_doc_description) {
		this.v_doc_description = v_doc_description;
	}

	public Date getV_doc_documentDueDate() {
		return v_doc_documentDueDate;
	}

	public void setV_doc_documentDueDate(Date v_doc_documentDueDate) {
		this.v_doc_documentDueDate = v_doc_documentDueDate;
	}

	public String getV_doc_documentState() {
		return v_doc_documentState;
	}

	public void setV_doc_documentState(String v_doc_documentState) {
		this.v_doc_documentState = v_doc_documentState;
	}

	public String getV_doc_indexNo() {
		return v_doc_indexNo;
	}

	public void setV_doc_indexNo(String v_doc_indexNo) {
		this.v_doc_indexNo = v_doc_indexNo;
	}

	public Long getV_doc_owner() {
		return v_doc_owner;
	}

	public void setV_doc_owner(Long v_doc_owner) {
		this.v_doc_owner = v_doc_owner;
	}

	public Date getV_doc_receiverRegisteredDate() {
		return v_doc_receiverRegisteredDate;
	}

	public void setV_doc_receiverRegisteredDate(Date v_doc_receiverRegisteredDate) {
		this.v_doc_receiverRegisteredDate = v_doc_receiverRegisteredDate;
	}

	public String getV_doc_receiverRegisteredNumber() {
		return v_doc_receiverRegisteredNumber;
	}

	public void setV_doc_receiverRegisteredNumber(String v_doc_receiverRegisteredNumber) {
		this.v_doc_receiverRegisteredNumber = v_doc_receiverRegisteredNumber;
	}

	public Date getV_doc_senderRegisteredDate() {
		return v_doc_senderRegisteredDate;
	}

	public void setV_doc_senderRegisteredDate(Date v_doc_senderRegisteredDate) {
		this.v_doc_senderRegisteredDate = v_doc_senderRegisteredDate;
	}

	public String getV_doc_senderRegisteredNumber() {
		return v_doc_senderRegisteredNumber;
	}

	public void setV_doc_senderRegisteredNumber(String v_doc_senderRegisteredNumber) {
		this.v_doc_senderRegisteredNumber = v_doc_senderRegisteredNumber;
	}

	public String getV_doc_title() {
		return v_doc_title;
	}

	public void setV_doc_title(String v_doc_title) {
		this.v_doc_title = v_doc_title;
	}

	public Long getV_doc_documentType() {
		return v_doc_documentType;
	}

	public void setV_doc_documentType(Long v_doc_documentType) {
		this.v_doc_documentType = v_doc_documentType;
	}

	public Long getV_doc_documentSubType() {
		return v_doc_documentSubType;
	}

	public void setV_doc_documentSubType(Long v_doc_documentSubType) {
		this.v_doc_documentSubType = v_doc_documentSubType;
	}

	public Long getV_doc_receiverExecutor() {
		return v_doc_receiverExecutor;
	}

	public void setV_doc_receiverExecutor(Long v_doc_receiverExecutor) {
		this.v_doc_receiverExecutor = v_doc_receiverExecutor;
	}

	public Long getV_doc_receiverResponsible() {
		return v_doc_receiverResponsible;
	}

	public void setV_doc_receiverResponsible(Long v_doc_receiverResponsible) {
		this.v_doc_receiverResponsible = v_doc_receiverResponsible;
	}

	public Long getV_doc_senderExecutor() {
		return v_doc_senderExecutor;
	}

	public void setV_doc_senderExecutor(Long v_doc_senderExecutor) {
		this.v_doc_senderExecutor = v_doc_senderExecutor;
	}

	public Long getV_doc_senderResponsible() {
		return v_doc_senderResponsible;
	}

	public void setV_doc_senderResponsible(Long v_doc_senderResponsible) {
		this.v_doc_senderResponsible = v_doc_senderResponsible;
	}

	public String getV_doc_sender_responsible_name() {
		return v_doc_sender_responsible_name;
	}

	public void setV_doc_sender_responsible_name(String v_doc_sender_responsible_name) {
		this.v_doc_sender_responsible_name = v_doc_sender_responsible_name;
	}

	public String getV_doc_sender_executor_name() {
		return v_doc_sender_executor_name;
	}

	public void setV_doc_sender_executor_name(String v_doc_sender_executor_name) {
		this.v_doc_sender_executor_name = v_doc_sender_executor_name;
	}

	public String getV_doc_receiver_responsible_name() {
		return v_doc_receiver_responsible_name;
	}

	public void setV_doc_receiver_responsible_name(String v_doc_receiver_responsible_name) {
		this.v_doc_receiver_responsible_name = v_doc_receiver_responsible_name;
	}

	public String getV_doc_receiver_executor_name() {
		return v_doc_receiver_executor_name;
	}

	public void setV_doc_receiver_executor_name(String v_doc_receiver_executor_name) {
		this.v_doc_receiver_executor_name = v_doc_receiver_executor_name;
	}

	public Long getV_doc_document_user_id() {
		return v_doc_document_user_id;
	}

	public void setV_doc_document_user_id(Long v_doc_document_user_id) {
		this.v_doc_document_user_id = v_doc_document_user_id;
	}

	public Long getV_doc_document_department_id() {
		return v_doc_document_department_id;
	}

	public void setV_doc_document_department_id(Long v_doc_document_department_id) {
		this.v_doc_document_department_id = v_doc_document_department_id;
	}

	public String getV_doc_senderResponsibleName() {
		return v_doc_senderResponsibleName;
	}

	public void setV_doc_senderResponsibleName(String v_doc_senderResponsibleName) {
		this.v_doc_senderResponsibleName = v_doc_senderResponsibleName;
	}

	public String getV_doc_receiverResponsibleName() {
		return v_doc_receiverResponsibleName;
	}

	public void setV_doc_receiverResponsibleName(String v_doc_receiverResponsibleName) {
		this.v_doc_receiverResponsibleName = v_doc_receiverResponsibleName;
	}
}