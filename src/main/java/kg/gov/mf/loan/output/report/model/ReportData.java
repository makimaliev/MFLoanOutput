package kg.gov.mf.loan.output.report.model;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.LinkedList;

@MappedSuperclass
public abstract class ReportData implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private ReportData Parent                = null;
	private LinkedList<ReportData> ChildDataList = null;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public ReportData[] getChilds()
	{
		return  (ReportData[])ChildDataList.toArray(new ReportData[ChildDataList.size()]);
	}
}
