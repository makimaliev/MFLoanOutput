package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.manage.model.entitydocument.EntityDocument;
import kg.gov.mf.loan.output.report.model.EntityDocumentReportData;
import kg.gov.mf.loan.output.report.model.LoanSummaryReportData;
import kg.gov.mf.loan.output.report.model.ReportTemplate;
import kg.gov.mf.loan.output.report.service.EntityDocumentViewService;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


public class ReportGeneratorEntityDocument extends ReportGenerator
{
	
    @Autowired
    EntityDocumentViewService entityDocumentViewService;



    public HSSFWorkbook generateReportByTemplate(ReportTemplate reportTemplate){

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        System.out.println("Report Generated!!!");

        EntityDocumentReportData EntityDocument = new EntityDocumentReportData();

        ReportTool reportTool = new ReportTool();
        reportTool.initReference();

        EntityDocument = new EntityDocumentReportDataManager().getReportDataGrouped(EntityDocument,reportTemplate);

        HSSFWorkbook     WorkBook       = new HSSFWorkbook();

        HSSFSheet        Sheet          = WorkBook.createSheet();

        reportTool.initReportSetup(reportTemplate,WorkBook);
        reportTool.setupSheetSettings(Sheet, reportTemplate);

        reportTool.drawTitle(reportTemplate,Sheet, EntityDocument);
        reportTool.drawHeader(reportTemplate,Sheet,EntityDocument);

        EntityDocumentReportData MainData = EntityDocument;

        reportTool.drawSumRow(reportTemplate,Sheet,MainData);

        EntityDocumentReportData GroupData1[] = MainData.getChilds();

        for(int x=0;x<GroupData1.length;x++)
        {
            reportTool.drawGroup1Row(reportTemplate,Sheet, GroupData1[x]);

            EntityDocumentReportData GroupData2[] = GroupData1[x].getChilds();

            for(int y=0;y<GroupData2.length;y++)
            {
                reportTool.drawGroup2Row(reportTemplate,Sheet, GroupData2[y]);

                EntityDocumentReportData GroupData3[] = GroupData2[y].getChilds();

                for(int z=0;z<GroupData3.length;z++)
                {
                    reportTool.drawGroup3Row(reportTemplate,Sheet, GroupData3[z]);

                    EntityDocumentReportData GroupData4[] = GroupData3[z].getChilds();

                    for(int a=0;a<GroupData4.length;a++)
                    {
                        reportTool.drawGroup4Row(reportTemplate,Sheet, GroupData4[a]);

                        EntityDocumentReportData GroupData5[] = GroupData4[a].getChilds();

                        for(int b=0;b<GroupData5.length;b++)
                        {
                            reportTool.drawGroup5Row(reportTemplate,Sheet, GroupData5[b]);

                        }


                    }

                }

            }

        }



        return WorkBook;

    }
}
