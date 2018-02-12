package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.ReportTemplate;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.net.URLConnection;


public class ReportGenerator {
	
	/**
     * Report GENERATION TOOL
     */
    public Workbook generateReportByTemplate(ReportTemplate reportTemplate){

        System.out.println("Report Generated!!!");


        /*
        *
        * WORKBOOK CREATE
        *
        * */

        HSSFWorkbook     WorkBook       = new HSSFWorkbook();


        /*
        *
        * SHEET CREATE
        *
        * */

        HSSFSheet        Sheet          = WorkBook.createSheet();


        HSSFRow          Row            = null;
        HSSFCell         Cell           = null;
        HSSFCell         CellDate       = null;
        HSSFDataFormat   format         = WorkBook.createDataFormat();




        Sheet.getPrintSetup().setLandscape(true);
        Sheet.setMargin(HSSFSheet.BottomMargin,0.5);
        Sheet.setMargin(HSSFSheet.TopMargin,0.5);
        Sheet.setMargin(HSSFSheet.LeftMargin,0);
        Sheet.setMargin(HSSFSheet.RightMargin,0);


        Sheet.setAutobreaks(true);
        HSSFPrintSetup ps = Sheet.getPrintSetup();
        ps.setFitWidth((short)1);
        ps.setFitHeight((short)0);
        ps.setHeaderMargin(0);
        ps.setFooterMargin(0);

        HSSFFooter footer = Sheet.getFooter();
        HSSFHeader header = Sheet.getHeader();

        footer.setRight( "Страница " + HSSFFooter.page() + " из " + HSSFFooter.numPages() );

        short Group1Color = (short)HSSFColor.PALE_BLUE.index;
        short Group2Color = (short)HSSFColor.YELLOW.index;
        short PersonColor = (short)HSSFColor.WHITE.index;
        //short CreditColor = (short)HSSFColor.WHITE.index;

        short Group1Border = (short)HSSFCellStyle.BORDER_THIN;
        short Group2Border = (short)HSSFCellStyle.BORDER_THIN;
        short PersonBorder = (short)HSSFCellStyle.BORDER_THIN;


        // Font Header

        HSSFFont         FontHeader     = WorkBook.createFont();
        FontHeader.setFontHeightInPoints((short)8);
        FontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // Font Sum

        HSSFFont         FontSum     = WorkBook.createFont();
        FontSum.setFontHeightInPoints((short)9);
        FontSum.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontSum.setColor(HSSFColor.RED.index);

        // Font Footer

        HSSFFont         FontFooter     = WorkBook.createFont();
        FontFooter.setFontHeightInPoints((short)12);
        FontFooter.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // Font Bold

        HSSFFont         FontDataBold     = WorkBook.createFont();
        FontDataBold.setFontHeightInPoints((short)8);
        FontDataBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // Font Person

        HSSFFont         FontPerson     = WorkBook.createFont();
        FontPerson.setFontHeightInPoints((short)8);
        FontPerson.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // Font Group1

        HSSFFont         FontGroup1     = WorkBook.createFont();
        FontGroup1.setFontHeightInPoints((short)8);
        FontGroup1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // Font Group2

        HSSFFont         FontGroup2     = WorkBook.createFont();
        FontGroup2.setFontHeightInPoints((short)8);
        FontGroup2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);


        // Font Normal

        HSSFFont         FontData     = WorkBook.createFont();
        FontData.setFontHeightInPoints((short)7);





        // Title

        HSSFCellStyle    CellStyleTitleBlue     = WorkBook.createCellStyle();
        HSSFFont         FontTitleBlue          = WorkBook.createFont();
        FontTitleBlue.setFontHeightInPoints((short)12);
        FontTitleBlue.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        FontTitleBlue.setColor(HSSFColor.BLUE.index);
        CellStyleTitleBlue.setFont(FontTitleBlue);
        CellStyleTitleBlue.setAlignment(HSSFCellStyle.ALIGN_CENTER);


        // Header


        HSSFCellStyle    CellStyleHeader    = WorkBook.createCellStyle();
        CellStyleHeader.setFont(FontHeader);
        CellStyleHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        CellStyleHeader.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        CellStyleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        CellStyleHeader.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        CellStyleHeader.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        CellStyleHeader.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        CellStyleHeader.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        CellStyleHeader.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        CellStyleHeader.setWrapText(true);



        // Footer

        HSSFCellStyle    CellStyleFooter    = WorkBook.createCellStyle();
        CellStyleFooter.setFont(FontFooter);
        CellStyleFooter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        CellStyleFooter.setFillBackgroundColor(HSSFColor.AQUA.index);



        // Group1

        HSSFCellStyle    CellStyleDataGroup1    = WorkBook.createCellStyle();
        CellStyleDataGroup1.setFont(FontGroup1);
        CellStyleDataGroup1.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        CellStyleDataGroup1.setBorderBottom(Group1Border);
        CellStyleDataGroup1.setBorderTop(Group1Border);
        CellStyleDataGroup1.setBorderRight(HSSFCellStyle.BORDER_THIN);
        CellStyleDataGroup1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        CellStyleDataGroup1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        CellStyleDataGroup1.setDataFormat(format.getFormat("#,#0.00"));
        CellStyleDataGroup1.setWrapText(true);
        CellStyleDataGroup1.setFillForegroundColor(Group1Color);
        CellStyleDataGroup1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        HSSFCellStyle    CellStyleDataGroup1Int    = WorkBook.createCellStyle();
        CellStyleDataGroup1Int.setFont(FontGroup1);
        CellStyleDataGroup1Int.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        CellStyleDataGroup1Int.setBorderBottom(Group1Border);
        CellStyleDataGroup1Int.setBorderTop(Group1Border);
        CellStyleDataGroup1Int.setBorderRight(HSSFCellStyle.BORDER_THIN);
        CellStyleDataGroup1Int.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        CellStyleDataGroup1Int.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        CellStyleDataGroup1Int.setDataFormat(format.getFormat("#,#0"));
        CellStyleDataGroup1Int.setWrapText(true);
        CellStyleDataGroup1Int.setFillForegroundColor(Group1Color);
        CellStyleDataGroup1Int.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);



        HSSFCellStyle    CellStyleDataStringGroup1    = WorkBook.createCellStyle();
        CellStyleDataStringGroup1.setFont(FontGroup1);
        CellStyleDataStringGroup1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        CellStyleDataStringGroup1.setBorderBottom(Group1Border);
        CellStyleDataStringGroup1.setBorderTop(Group1Border);
        CellStyleDataStringGroup1.setBorderRight(HSSFCellStyle.BORDER_THIN);
        CellStyleDataStringGroup1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        CellStyleDataStringGroup1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        CellStyleDataStringGroup1.setWrapText(true);
        CellStyleDataStringGroup1.setFillForegroundColor(Group1Color);
        CellStyleDataStringGroup1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);



        // Group 2

        HSSFCellStyle    CellStyleDataGroup2    = WorkBook.createCellStyle();
        CellStyleDataGroup2.setFont(FontGroup2);
        CellStyleDataGroup2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        CellStyleDataGroup2.setBorderBottom(Group2Border);
        CellStyleDataGroup2.setBorderTop(Group2Border);
        CellStyleDataGroup2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        CellStyleDataGroup2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        CellStyleDataGroup2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        CellStyleDataGroup2.setDataFormat(format.getFormat("#,#0.00"));
        CellStyleDataGroup2.setWrapText(true);
        CellStyleDataGroup2.setFillForegroundColor(Group2Color);
        CellStyleDataGroup2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);


        HSSFCellStyle    CellStyleDataGroup2Int    = WorkBook.createCellStyle();
        CellStyleDataGroup2Int.setFont(FontGroup2);
        CellStyleDataGroup2Int.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        CellStyleDataGroup2Int.setBorderBottom(Group2Border);
        CellStyleDataGroup2Int.setBorderTop(Group2Border);
        CellStyleDataGroup2Int.setBorderRight(HSSFCellStyle.BORDER_THIN);
        CellStyleDataGroup2Int.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        CellStyleDataGroup2Int.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        CellStyleDataGroup2Int.setDataFormat(format.getFormat("#,#0"));
        CellStyleDataGroup2Int.setWrapText(true);
        CellStyleDataGroup2Int.setFillForegroundColor(Group2Color);
        CellStyleDataGroup2Int.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);


        HSSFCellStyle    CellStyleDataStringGroup2    = WorkBook.createCellStyle();
        CellStyleDataStringGroup2.setFont(FontGroup2);
        CellStyleDataStringGroup2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        CellStyleDataStringGroup2.setBorderBottom(Group2Border);
        CellStyleDataStringGroup2.setBorderTop(Group2Border);
        CellStyleDataStringGroup2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        CellStyleDataStringGroup2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        CellStyleDataStringGroup2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        CellStyleDataStringGroup2.setWrapText(true);
        CellStyleDataStringGroup2.setFillForegroundColor(Group2Color);
        CellStyleDataStringGroup2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);




        // Person

        HSSFCellStyle    CellStyleDataPerson    = WorkBook.createCellStyle();
        CellStyleDataPerson.setFont(FontPerson);
        CellStyleDataPerson.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        CellStyleDataPerson.setBorderBottom(PersonBorder);
        CellStyleDataPerson.setBorderTop(PersonBorder);
        CellStyleDataPerson.setBorderRight(HSSFCellStyle.BORDER_THIN);
        CellStyleDataPerson.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        CellStyleDataPerson.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        CellStyleDataPerson.setDataFormat(format.getFormat("#,#0.00"));
        CellStyleDataPerson.setWrapText(true);
        CellStyleDataPerson.setFillForegroundColor(PersonColor);
        CellStyleDataPerson.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);



        HSSFCellStyle    CellStyleDataPersonInt    = WorkBook.createCellStyle();
        CellStyleDataPersonInt.setFont(FontPerson);
        CellStyleDataPersonInt.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        CellStyleDataPersonInt.setBorderBottom(PersonBorder);
        CellStyleDataPersonInt.setBorderTop(PersonBorder);
        CellStyleDataPersonInt.setBorderRight(HSSFCellStyle.BORDER_THIN);
        CellStyleDataPersonInt.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        CellStyleDataPersonInt.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        CellStyleDataPersonInt.setDataFormat(format.getFormat("#,#0"));
        CellStyleDataPersonInt.setWrapText(true);
        CellStyleDataPersonInt.setFillForegroundColor(PersonColor);
        CellStyleDataPersonInt.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);


        HSSFCellStyle    CellStyleDataStringPerson    = WorkBook.createCellStyle();
        CellStyleDataStringPerson.setFont(FontPerson);
        CellStyleDataStringPerson.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        CellStyleDataStringPerson.setBorderBottom(PersonBorder);
        CellStyleDataStringPerson.setBorderTop(PersonBorder);
        CellStyleDataStringPerson.setBorderRight(HSSFCellStyle.BORDER_THIN);
        CellStyleDataStringPerson.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        CellStyleDataStringPerson.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        CellStyleDataStringPerson.setWrapText(true);
        CellStyleDataStringPerson.setFillForegroundColor(PersonColor);
        CellStyleDataStringPerson.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);



        // Credit

        HSSFCellStyle    CellStyleData    = WorkBook.createCellStyle();
        CellStyleData.setFont(FontData);
        CellStyleData.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        CellStyleData.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        CellStyleData.setBorderTop(HSSFCellStyle.BORDER_THIN);
        CellStyleData.setBorderRight(HSSFCellStyle.BORDER_THIN);
        CellStyleData.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        CellStyleData.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        CellStyleData.setDataFormat(format.getFormat("#,#0.00"));
        CellStyleData.setWrapText(true);

        HSSFCellStyle    CellStyleDataLeft    = WorkBook.createCellStyle();
        CellStyleDataLeft.setFont(FontData);
        CellStyleDataLeft.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        CellStyleDataLeft.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        CellStyleDataLeft.setBorderTop(HSSFCellStyle.BORDER_THIN);
        CellStyleDataLeft.setBorderRight(HSSFCellStyle.BORDER_THIN);
        CellStyleDataLeft.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        CellStyleDataLeft.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        CellStyleDataLeft.setDataFormat(format.getFormat("#,#0.00"));
        CellStyleDataLeft.setWrapText(true);


        HSSFCellStyle    CellStyleDataInt    = WorkBook.createCellStyle();
        CellStyleDataInt.setFont(FontData);
        CellStyleDataInt.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        CellStyleDataInt.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        CellStyleDataInt.setBorderTop(HSSFCellStyle.BORDER_THIN);
        CellStyleDataInt.setBorderRight(HSSFCellStyle.BORDER_THIN);
        CellStyleDataInt.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        CellStyleDataInt.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        CellStyleDataInt.setDataFormat(format.getFormat("#,#0"));
        CellStyleDataInt.setWrapText(true);

        HSSFCellStyle    CellStyleDate    = WorkBook.createCellStyle();
        CellStyleDate.setFont(FontData);
        CellStyleDate.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        CellStyleDate.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        CellStyleDate.setBorderTop(HSSFCellStyle.BORDER_THIN);
        CellStyleDate.setBorderRight(HSSFCellStyle.BORDER_THIN);
        CellStyleDate.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        CellStyleDate.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        CellStyleDate.setDataFormat(format.getFormat("dd.mm.yyyy"));

        HSSFCellStyle    CellStyleDataColor    = WorkBook.createCellStyle();
        CellStyleDataColor.setFont(FontData);
        CellStyleDataColor.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        CellStyleDataColor.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        CellStyleDataColor.setBorderTop(HSSFCellStyle.BORDER_THIN);
        CellStyleDataColor.setBorderRight(HSSFCellStyle.BORDER_THIN);
        CellStyleDataColor.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        CellStyleDataColor.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        CellStyleDataColor.setDataFormat(format.getFormat("#,#0.00"));
        CellStyleDataColor.setWrapText(true);

        HSSFCellStyle    CellStyleDataString    = WorkBook.createCellStyle();
        CellStyleDataString.setFont(FontData);
        CellStyleDataString.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        CellStyleDataString.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        CellStyleDataString.setBorderTop(HSSFCellStyle.BORDER_THIN);
        CellStyleDataString.setBorderRight(HSSFCellStyle.BORDER_THIN);
        CellStyleDataString.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        CellStyleDataString.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        CellStyleDataString.setWrapText(true);

        HSSFCellStyle    CellStyleDataStringColor    = WorkBook.createCellStyle();
        CellStyleDataStringColor.setFont(FontData);
        CellStyleDataStringColor.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        CellStyleDataStringColor.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        CellStyleDataStringColor.setBorderTop(HSSFCellStyle.BORDER_THIN);
        CellStyleDataStringColor.setBorderRight(HSSFCellStyle.BORDER_THIN);
        CellStyleDataStringColor.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        CellStyleDataStringColor.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        CellStyleDataStringColor.setWrapText(true);
        CellStyleDataStringColor.setFillForegroundColor(HSSFColor.AQUA.index);
        CellStyleDataStringColor.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);



        // Sum

        HSSFCellStyle    CellStyleDataSum    = WorkBook.createCellStyle();
        CellStyleDataSum.setFont(FontSum);
        CellStyleDataSum.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        CellStyleDataSum.setBorderBottom(HSSFCellStyle.BIG_SPOTS);
        CellStyleDataSum.setBorderTop(HSSFCellStyle.BIG_SPOTS);
        CellStyleDataSum.setBorderRight(HSSFCellStyle.BORDER_THIN);
        CellStyleDataSum.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        CellStyleDataSum.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        CellStyleDataSum.setDataFormat(format.getFormat("#,#0.00"));
        CellStyleDataSum.setWrapText(true);
        CellStyleDataSum.setFillForegroundColor(HSSFColor.AQUA.index);
        CellStyleDataSum.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        HSSFCellStyle    CellStyleDataSumInt    = WorkBook.createCellStyle();
        CellStyleDataSumInt.setFont(FontSum);
        CellStyleDataSumInt.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        CellStyleDataSumInt.setBorderBottom(HSSFCellStyle.BIG_SPOTS);
        CellStyleDataSumInt.setBorderTop(HSSFCellStyle.BIG_SPOTS);
        CellStyleDataSumInt.setBorderRight(HSSFCellStyle.BORDER_THIN);
        CellStyleDataSumInt.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        CellStyleDataSumInt.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        CellStyleDataSumInt.setDataFormat(format.getFormat("#,#0"));
        CellStyleDataSumInt.setWrapText(true);
        CellStyleDataSumInt.setFillForegroundColor(HSSFColor.AQUA.index);
        CellStyleDataSumInt.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        HSSFCellStyle    CellStyleDataStringSum    = WorkBook.createCellStyle();
        CellStyleDataStringSum.setFont(FontSum);
        CellStyleDataStringSum.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        CellStyleDataStringSum.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        CellStyleDataStringSum.setBorderTop(HSSFCellStyle.BORDER_THIN);
        CellStyleDataStringSum.setBorderRight(HSSFCellStyle.BORDER_THIN);
        CellStyleDataStringSum.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        CellStyleDataStringSum.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        CellStyleDataStringSum.setWrapText(true);
        CellStyleDataStringSum.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
        CellStyleDataStringSum.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);




        short width = 3300;
        short count=0;
        Sheet.setColumnWidth(count,(short)(width/3));count++;
        Sheet.setColumnWidth(count,(short)(width/3));count++;


        boolean iDetail4 = true;
        int RowCount = 0;
        short ColumnCount=0;

        if(iDetail4)
        {
            Sheet.setColumnWidth(count,(short)(width/2-200));count++;
        }

        Sheet.setColumnWidth(count,(short)(width*2+1000));count++;

        if(iDetail4)
        {
            Sheet.setColumnWidth(count,(short)width);count++;
        }

        Sheet.setColumnWidth(count,(short)width);count++;
        Sheet.setColumnWidth(count,(short)width);count++;

        if(iDetail4)
        {
            Sheet.setColumnWidth(count,(short)(width*0.7));count++;
        }

        Sheet.setColumnWidth(count,(short)width);count++;
        Sheet.setColumnWidth(count,(short)width);count++;
        Sheet.setColumnWidth(count,(short)width);count++;
        Sheet.setColumnWidth(count,(short)width);count++;
        Sheet.setColumnWidth(count,(short)width);count++;
        Sheet.setColumnWidth(count,(short)width);count++;
        Sheet.setColumnWidth(count,(short)width);count++;
        Sheet.setColumnWidth(count,(short)width);count++;
        Sheet.setColumnWidth(count,(short)width);count++;
        Sheet.setColumnWidth(count,(short)(width*2+1000));count++;

        Sheet.setColumnWidth(count,(short)width);count++;
        Sheet.setColumnWidth(count,(short)width);count++;
        Sheet.setColumnWidth(count,(short)width);count++;
        Sheet.setColumnWidth(count,(short)width);count++;
        Sheet.setColumnWidth(count,(short)width);count++;
        Sheet.setColumnWidth(count,(short)width);count++;



        Row = Sheet.createRow(RowCount);
        Row.setRowNum(( short ) RowCount);

        Sheet.addMergedRegion(new Region(RowCount,ColumnCount,RowCount,(short)(ColumnCount+14)));
        Cell = Row.createCell(ColumnCount, HSSFCell.CELL_TYPE_STRING);
        Cell.setCellStyle(CellStyleTitleBlue);
        Cell.setCellValue("Сводная по задолженности");

        RowCount++;
        ColumnCount=0;

        Row = Sheet.createRow(RowCount);
        Row.setRowNum(( short ) RowCount);

        Sheet.addMergedRegion(new Region(RowCount,ColumnCount,RowCount,(short)(ColumnCount+14)));
        Cell = Row.createCell(ColumnCount, HSSFCell.CELL_TYPE_STRING);
        Cell.setCellStyle(CellStyleTitleBlue);
        Cell.setCellValue("хозсубъектов перед Государственным агентством по управлению бюджетными кредитами при Министерстве финансов Кыргызской Республики");

        RowCount++;
        ColumnCount=0;
        Row = Sheet.createRow(RowCount);
        Row.setRowNum(( short ) RowCount);

        //String sCreditTypeString ="";


        Sheet.addMergedRegion(new Region(RowCount,ColumnCount,RowCount,(short)(ColumnCount+14)));
        Cell = Row.createCell(ColumnCount, HSSFCell.CELL_TYPE_STRING);
        Cell.setCellStyle(CellStyleTitleBlue);
        Cell.setCellValue("по состоянию на _______________г.");

        RowCount++;
        ColumnCount=0;
        Row = Sheet.createRow(RowCount);
        Row.setRowNum(( short ) RowCount);

        Sheet.addMergedRegion(new Region(RowCount,ColumnCount,RowCount,(short)(ColumnCount+14)));
        Cell = Row.createCell(ColumnCount, HSSFCell.CELL_TYPE_BLANK);
        Cell.setCellStyle(CellStyleTitleBlue);
        Cell.setCellValue("");

/*
        sContentType = "application/vnd.ms-excel" ;
        response.setContentType(sContentType);
        response.setHeader("Content-disposition","attachment; filename=report.xls");
        OutputStream out = response.getOutputStream();
        WorkBook.write(out);
        out.close();
*/
        return WorkBook;

    }

}
