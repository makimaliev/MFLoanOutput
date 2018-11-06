package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.ReportData;
import kg.gov.mf.loan.output.report.model.ReportTemplate;
import kg.gov.mf.loan.output.report.model.LoanDebtTransferReportData;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


public class ReportGeneratorLoanDebtTransfer extends ReportGenerator{

    public HSSFWorkbook generateReportByTemplate(ReportTemplate reportTemplate){

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        LoanDebtTransferReportData loanDebtTransferReportData = new LoanDebtTransferReportData();

        ReportTool reportTool = new ReportTool();
        reportTool.initReference();

        loanDebtTransferReportData = new LoanDebtTransferReportDataManager().getReportDataGrouped(loanDebtTransferReportData,reportTemplate);

        HSSFWorkbook     WorkBook       = new HSSFWorkbook();

        HSSFSheet        Sheet          = WorkBook.createSheet();

        LoanDebtTransferReportData MainData = loanDebtTransferReportData;

        ReportData reportData = MainData;

        reportTool.drawSheet(reportTemplate, Sheet, reportData, WorkBook, reportTool);

        return WorkBook;

    }


}
