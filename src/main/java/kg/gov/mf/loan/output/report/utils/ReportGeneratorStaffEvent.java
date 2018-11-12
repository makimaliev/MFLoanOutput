package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.StaffEventReportData;
import kg.gov.mf.loan.output.report.model.ReportData;
import kg.gov.mf.loan.output.report.model.ReportTemplate;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


public class ReportGeneratorStaffEvent extends ReportGenerator{
	
    public HSSFWorkbook generateReportByTemplate(ReportTemplate reportTemplate){

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        StaffEventReportData staffEventReportData = new StaffEventReportData();

        ReportTool reportTool = new ReportTool();
        reportTool.initReference();

        staffEventReportData = new StaffEventReportDataManager().getReportDataGrouped(staffEventReportData,reportTemplate);

        HSSFWorkbook     WorkBook       = new HSSFWorkbook();

        HSSFSheet        Sheet          = WorkBook.createSheet();

        StaffEventReportData MainData = staffEventReportData;

        ReportData reportData = MainData;

        reportTool.drawSheet(reportTemplate, Sheet, reportData, WorkBook, reportTool);

        return WorkBook;

    }




}
