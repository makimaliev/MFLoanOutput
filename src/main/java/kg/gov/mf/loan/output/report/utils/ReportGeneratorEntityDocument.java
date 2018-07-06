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


        HSSFRow          Row            = null;
        HSSFCell         Cell           = null;
        HSSFCell         CellDate       = null;
        HSSFDataFormat   format         = WorkBook.createDataFormat();

        reportTool.setupSheetSettings(Sheet, reportTemplate);

        short Group1Color = (short)reportTool.getColorByLevel((long)1,reportTemplate).getIndex();
        short Group2Color = (short)reportTool.getColorByLevel((long)2,reportTemplate).getIndex();
        short PersonColor = (short)reportTool.getColorByLevel((long)3,reportTemplate).getIndex();
        short CreditColor = (short)reportTool.getColorByLevel((long)4,reportTemplate).getIndex();

        short Group1Border = (short)HSSFCellStyle.BORDER_THIN;
        short Group2Border = (short)HSSFCellStyle.BORDER_THIN;
        short PersonBorder = (short)HSSFCellStyle.BORDER_THIN;


        EntityDocumentReportData MainData = EntityDocument;

        System.out.println(MainData.getName());

        EntityDocumentReportData GroupData1[] = MainData.getChilds();

        for(int x=0;x<GroupData1.length;x++)
        {
            System.out.println(x+" "+GroupData1[x].getName());

            EntityDocumentReportData GroupData2[] = GroupData1[x].getChilds();

            for(int y=0;y<GroupData2.length;y++)
            {
                System.out.println(y+" "+GroupData2[y].getName());

                EntityDocumentReportData GroupData3[] = GroupData2[y].getChilds();

                for(int z=0;z<GroupData3.length;z++)
                {
                    System.out.println(z+" "+GroupData3[z].getName());

                    EntityDocumentReportData GroupData4[] = GroupData3[z].getChilds();

                    for(int a=0;a<GroupData4.length;a++)
                    {
                        System.out.println(a+" "+GroupData4[a].getName());

                        EntityDocumentReportData GroupData5[] = GroupData4[a].getChilds();

                        for(int b=0;b<GroupData5.length;b++)
                        {
                            System.out.println(b+" "+GroupData5[b].getName());

                        }


                    }

                }

            }

        }



        return WorkBook;

    }
}
