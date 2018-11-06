package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.SupervisorPlanReportData;
import kg.gov.mf.loan.output.report.model.ReportData;
import kg.gov.mf.loan.output.report.model.ReportTemplate;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


public class ReportGeneratorSupervisorPlan extends ReportGenerator{

    public HSSFWorkbook generateReportByTemplate(ReportTemplate reportTemplate){

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        SupervisorPlanReportData supervisorPlanReportData = new SupervisorPlanReportData();

        ReportTool reportTool = new ReportTool();
        reportTool.initReference();

        supervisorPlanReportData = new SupervisorPlanReportDataManager().getReportDataGrouped(supervisorPlanReportData,reportTemplate);

        HSSFWorkbook     WorkBook       = new HSSFWorkbook();

        HSSFSheet        Sheet          = WorkBook.createSheet();

        SupervisorPlanReportData MainData = supervisorPlanReportData;

        ReportData reportData = MainData;

        reportTool.drawSheet(reportTemplate, Sheet, reportData, WorkBook, reportTool);

        return WorkBook;

    }


}
