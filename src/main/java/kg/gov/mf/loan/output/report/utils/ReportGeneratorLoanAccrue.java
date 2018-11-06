package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.ReportData;
import kg.gov.mf.loan.output.report.model.ReportTemplate;
import kg.gov.mf.loan.output.report.model.LoanAccrueReportData;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


public class ReportGeneratorLoanAccrue extends ReportGenerator{

    public HSSFWorkbook generateReportByTemplate(ReportTemplate reportTemplate){

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        LoanAccrueReportData loanAccrueReportData = new LoanAccrueReportData();

        ReportTool reportTool = new ReportTool();
        reportTool.initReference();

        loanAccrueReportData = new LoanAccrueReportDataManager().getReportDataGrouped(loanAccrueReportData,reportTemplate);

        HSSFWorkbook     WorkBook       = new HSSFWorkbook();

        HSSFSheet        Sheet          = WorkBook.createSheet();

        LoanAccrueReportData MainData = loanAccrueReportData;

        ReportData reportData = MainData;

        reportTool.drawSheet(reportTemplate, Sheet, reportData, WorkBook, reportTool);

        return WorkBook;

    }


}
