package kg.gov.mf.loan.output.report.model;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class ReportData implements Serializable {
	
	private static final long serialVersionUID = 1L;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}
