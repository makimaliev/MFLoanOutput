package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.LoanView;
import kg.gov.mf.loan.output.report.model.CollateralItemReportData;
import kg.gov.mf.loan.output.report.model.ReportData;
import kg.gov.mf.loan.output.report.model.ReportTemplate;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.List;


public class ReportGeneratorCollateralItem extends ReportGenerator{
	
	/**
     * Report GENERATION TOOL
     */

    @Autowired
    LoanViewService loanViewService;


    /*
	@Autowired
	LoanService loanService;




    @Autowired
    PersonService personService;

    @Autowired
    OrganizationService organizationService;
*/
    public HSSFWorkbook generateReportByTemplate(ReportTemplate reportTemplate){

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        CollateralItemReportData collectionPhaseReportData = new CollateralItemReportData();

        ReportTool reportTool = new ReportTool();
        reportTool.initReference();

//        collectionPhaseReportData = new CollateralItemReportDataManager().getReportDataGrouped(collectionPhaseReportData,reportTemplate);

        collectionPhaseReportData = new CollateralItemAndLoanSummaryReportDataManager().getReportDataGrouped(collectionPhaseReportData,reportTemplate);

        HSSFWorkbook     WorkBook       = new HSSFWorkbook();

        HSSFSheet        Sheet          = WorkBook.createSheet();

        CollateralItemReportData MainData = collectionPhaseReportData;

        ReportData reportData = MainData;

        reportTool.drawSheet(reportTemplate, Sheet, reportData, WorkBook, reportTool);

        return WorkBook;

    }



}
