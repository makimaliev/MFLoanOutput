package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.ReportData;
import kg.gov.mf.loan.output.report.model.ReportTemplate;
import kg.gov.mf.loan.output.report.model.LoanWriteOffReportData;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


public class ReportGeneratorLoanWriteOff extends ReportGenerator{

    public HSSFWorkbook generateReportByTemplate(ReportTemplate reportTemplate){

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        LoanWriteOffReportData loanWriteOffReportData = new LoanWriteOffReportData();

        ReportTool reportTool = new ReportTool();
        reportTool.initReference();

        loanWriteOffReportData = new LoanWriteOffReportDataManager().getReportDataGrouped(loanWriteOffReportData,reportTemplate);

        HSSFWorkbook     WorkBook       = new HSSFWorkbook();

        HSSFSheet        Sheet          = WorkBook.createSheet();

        LoanWriteOffReportData MainData = loanWriteOffReportData;

        ReportData reportData = MainData;

        reportTool.drawSheet(reportTemplate, Sheet, reportData, WorkBook, reportTool);

        return WorkBook;

    }


}
