package kg.gov.mf.loan.output.report.model;


import org.hibernate.annotations.Immutable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="reference_view")
@Immutable
public class ReferenceView extends ReportView
{

	@Id
	@Column
	private long id;

	@Id
	@Column
	private String list_type;

	@Column
	private String name;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getList_type() {
		return list_type;
	}

	public void setList_type(String list_type) {
		this.list_type = list_type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}