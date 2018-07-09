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



        HSSFCell         CellDate       = null;
        HSSFDataFormat   format         = WorkBook.createDataFormat();

        reportTool.setupSheetSettings(Sheet, reportTemplate);

        short Group1Color = (short)reportTool.getColorByLevel((long)1,reportTemplate).getIndex();
        short Group2Color = (short)reportTool.getColorByLevel((long)2,reportTemplate).getIndex();
        short PersonColor = (short)reportTool.getColorByLevel((long)3,reportTemplate).getIndex();
        short CreditColor = (short)reportTool.getColorByLevel((long)4,reportTemplate).getIndex();

        short Group3Color = (short)reportTool.getColorByLevel((long)3,reportTemplate).getIndex();
        short Group4Color = (short)reportTool.getColorByLevel((long)4,reportTemplate).getIndex();
        short Group5Color = (short)reportTool.getColorByLevel((long)5,reportTemplate).getIndex();
        short Group6Color = (short)reportTool.getColorByLevel((long)6,reportTemplate).getIndex();

        short Group1Border = reportTool.getBorderTypeByLevel((long)1,reportTemplate);
        short Group2Border = reportTool.getBorderTypeByLevel((long)2,reportTemplate);
        short PersonBorder = reportTool.getBorderTypeByLevel((long)3,reportTemplate);

        short Group3Border = reportTool.getBorderTypeByLevel((long)3,reportTemplate);
        short Group4Border = reportTool.getBorderTypeByLevel((long)4,reportTemplate);
        short Group5Border = reportTool.getBorderTypeByLevel((long)5,reportTemplate);
        short Group6Border = reportTool.getBorderTypeByLevel((long)6,reportTemplate);

        HSSFFont         FontTitle      = reportTool.getFontByLevel(10, reportTemplate, WorkBook);
        HSSFFont         FontHeader     = reportTool.getFontByLevel(11, reportTemplate, WorkBook);
        HSSFFont         FontSum        = reportTool.getFontByLevel(12, reportTemplate, WorkBook);
        HSSFFont         FontFooter     = reportTool.getFontByLevel(13, reportTemplate, WorkBook);

        HSSFFont         FontGroup1     = reportTool.getFontByLevel(1, reportTemplate, WorkBook);
        HSSFFont         FontGroup2     = reportTool.getFontByLevel(2, reportTemplate, WorkBook);
        HSSFFont         FontGroup3     = reportTool.getFontByLevel(3, reportTemplate, WorkBook);
        HSSFFont         FontGroup4     = reportTool.getFontByLevel(4, reportTemplate, WorkBook);
        HSSFFont         FontGroup5     = reportTool.getFontByLevel(5, reportTemplate, WorkBook);
        HSSFFont         FontGroup6     = reportTool.getFontByLevel(6, reportTemplate, WorkBook);

        HSSFFont         FontPerson     = reportTool.getFontByLevel(3, reportTemplate, WorkBook);
        HSSFFont         FontData       = reportTool.getFontByLevel(4, reportTemplate, WorkBook);


        reportTool.initReportSetup(reportTemplate,WorkBook);

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
