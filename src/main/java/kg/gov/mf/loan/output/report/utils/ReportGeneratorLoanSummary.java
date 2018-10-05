package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.LoanSummaryReportData;
import kg.gov.mf.loan.output.report.model.ReportData;
import kg.gov.mf.loan.output.report.model.ReportTemplate;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class ReportGeneratorLoanSummary extends ReportGenerator
{
	
	/**
     * Report GENERATION TOOL
     */

    @Autowired
    LoanViewService loanViewService;


    public HSSFWorkbook generateReportByTemplate(ReportTemplate reportTemplate){

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        LoanSummaryReportData LoanSummary = new LoanSummaryReportData();

        ReportTool reportTool = new ReportTool();
        reportTool.initReference();

        LoanSummary = new LoanSummaryReportDataManager().getReportDataGrouped(LoanSummary,reportTemplate);

        ReportData reportData = LoanSummary;

        HSSFWorkbook     WorkBook       = new HSSFWorkbook();

        HSSFSheet        Sheet          = WorkBook.createSheet();

        reportTool.drawSheet(reportTemplate, Sheet, reportData, WorkBook, reportTool);

        return WorkBook;

    }
}
