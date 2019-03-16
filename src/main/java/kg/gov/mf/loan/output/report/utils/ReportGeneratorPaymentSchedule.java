package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.LoanView;
import kg.gov.mf.loan.output.report.model.PaymentScheduleReportData;
import kg.gov.mf.loan.output.report.model.ReportData;
import kg.gov.mf.loan.output.report.model.ReportTemplate;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.List;


public class ReportGeneratorPaymentSchedule extends ReportGenerator{

    public HSSFWorkbook generateReportByTemplate(ReportTemplate reportTemplate){

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        PaymentScheduleReportData paymentScheduleReportData = new PaymentScheduleReportData();

        ReportTool reportTool = new ReportTool();
        reportTool.initReference();

        paymentScheduleReportData = new PaymentScheduleReportDataManager().getReportDataGrouped(paymentScheduleReportData,reportTemplate);

        HSSFWorkbook     WorkBook       = new HSSFWorkbook();

        HSSFSheet        Sheet          = WorkBook.createSheet();

        PaymentScheduleReportData MainData = paymentScheduleReportData;

        ReportData reportData = MainData;

        reportTool.drawSheet(reportTemplate, Sheet, reportData, WorkBook, reportTool);

        return WorkBook;

    }


}
