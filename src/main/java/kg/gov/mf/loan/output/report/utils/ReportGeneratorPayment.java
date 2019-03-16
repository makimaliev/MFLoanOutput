package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.LoanView;
import kg.gov.mf.loan.output.report.model.PaymentReportData;
import kg.gov.mf.loan.output.report.model.ReportData;
import kg.gov.mf.loan.output.report.model.ReportTemplate;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.List;


public class ReportGeneratorPayment extends ReportGenerator{
	
    public HSSFWorkbook generateReportByTemplate(ReportTemplate reportTemplate){

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        PaymentReportData paymentReportData = new PaymentReportData();

        ReportTool reportTool = new ReportTool();
        reportTool.initReference();

        paymentReportData = new PaymentReportDataManager().getReportDataGrouped(paymentReportData,reportTemplate);

        HSSFWorkbook     WorkBook       = new HSSFWorkbook();

        HSSFSheet        Sheet          = WorkBook.createSheet();

        PaymentReportData MainData = paymentReportData;

        ReportData reportData = MainData;

        reportTool.drawSheet(reportTemplate, Sheet, reportData, WorkBook, reportTool);

        return WorkBook;

    }




}
