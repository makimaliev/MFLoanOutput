package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.EntityDocumentReportData;

import kg.gov.mf.loan.output.report.model.ReportData;
import kg.gov.mf.loan.output.report.model.ReportTemplate;
import kg.gov.mf.loan.output.report.service.EntityDocumentViewService;

import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class ReportGeneratorEntityDocument extends ReportGenerator
{
	
    @Autowired
    EntityDocumentViewService entityDocumentViewService;



    public HSSFWorkbook generateReportByTemplate(ReportTemplate reportTemplate){

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        EntityDocumentReportData EntityDocument = new EntityDocumentReportData();

        ReportTool reportTool = new ReportTool();
        reportTool.initReference();



        EntityDocument = new EntityDocumentReportDataManager().getReportDataGrouped(EntityDocument,reportTemplate);

        ReportData reportData = EntityDocument;

        HSSFWorkbook     WorkBook       = new HSSFWorkbook();

        HSSFSheet        Sheet          = WorkBook.createSheet();

        reportTool.drawSheet(reportTemplate, Sheet, reportData, WorkBook, reportTool);

        return WorkBook;

    }
}
