package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.CollateralArrestFreeReportData;
import kg.gov.mf.loan.output.report.model.ReportData;
import kg.gov.mf.loan.output.report.model.ReportTemplate;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


public class ReportGeneratorCollateralArrestFree extends ReportGenerator{
	
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

        CollateralArrestFreeReportData collateralArrestFreeReportData = new CollateralArrestFreeReportData();

        ReportTool reportTool = new ReportTool();
        reportTool.initReference();

        collateralArrestFreeReportData = new CollateralArrestFreeReportDataManager().getReportDataGrouped(collateralArrestFreeReportData,reportTemplate);

        HSSFWorkbook     WorkBook       = new HSSFWorkbook();

        HSSFSheet        Sheet          = WorkBook.createSheet();

        CollateralArrestFreeReportData MainData = collateralArrestFreeReportData;

        ReportData reportData = MainData;

        reportTool.drawSheet(reportTemplate, Sheet, reportData, WorkBook, reportTool);

        return WorkBook;

    }



}
