package kg.gov.mf.loan.output.report.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class ReportView implements Serializable {
	
	private static final long serialVersionUID = 1L;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}
