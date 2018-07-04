package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.ReportTemplate;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class ReportGenerator  {

	public HSSFWorkbook generateReportByTemplate(ReportTemplate reportTemplate)
	{
		HSSFWorkbook     WorkBook       = new HSSFWorkbook();

		return WorkBook;
	}

}
