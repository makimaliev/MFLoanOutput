package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.ReportData;
import kg.gov.mf.loan.output.report.model.ReportTemplate;
import kg.gov.mf.loan.output.report.model.DocumentReportData;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


public class ReportGeneratorDocument extends ReportGenerator{
	
    public HSSFWorkbook generateReportByTemplate(ReportTemplate reportTemplate){

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        DocumentReportData documentReportData = new DocumentReportData();

        ReportTool reportTool = new ReportTool();
        reportTool.initReference();

        documentReportData = new DocumentReportDataManager().getReportDataGrouped(documentReportData,reportTemplate);

        HSSFWorkbook     WorkBook       = new HSSFWorkbook();

        HSSFSheet        Sheet          = WorkBook.createSheet();

        DocumentReportData MainData = documentReportData;

        ReportData reportData = MainData;

        reportTool.drawSheet(reportTemplate, Sheet, reportData, WorkBook, reportTool);

        return WorkBook;

    }




}
