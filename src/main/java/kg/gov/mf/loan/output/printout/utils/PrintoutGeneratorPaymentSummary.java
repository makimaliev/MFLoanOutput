package kg.gov.mf.loan.output.printout.utils;


import com.lowagie.text.*;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.process.LoanSummary;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.process.LoanSummaryService;
import kg.gov.mf.loan.output.printout.model.PrintoutTemplate;
import kg.gov.mf.loan.output.report.model.DebtorView;
import kg.gov.mf.loan.output.report.model.ObjectList;
import kg.gov.mf.loan.output.report.model.ObjectListValue;
import kg.gov.mf.loan.output.report.model.PaymentView;
import kg.gov.mf.loan.output.report.service.LoanViewService;

import kg.gov.mf.loan.output.report.service.PaymentViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.CMYKColor;

import java.io.FileOutputStream;
import java.util.*;
import java.util.List;


public class PrintoutGeneratorPaymentSummary {
	
	/**
     * Printout GENERATION TOOL
     */

	@Autowired
    PaymentViewService paymentViewService;

	@Autowired
    LoanSummaryService loanSummaryService;

	@Autowired
    LoanService loanService;

    public void generatePrintoutByTemplate(PrintoutTemplate printoutTemplate, Date onDate, long objectId, Document document){


        switch (String.valueOf(printoutTemplate.getId()))
        {
            case "1" :
                try
                {

                    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);


                    LoanSummary loanSummary = loanSummaryService.getById(objectId);

                    Loan loan = loanSummary.getLoan();


                    Date      tRasDate  = loanSummary.getOnDate();
                    long      iCreditID = loan.getId();


                    //*********** Document ********************************************************************************
                    //*******************************************************************************************

                    PdfPTable table = null;
                    PdfPCell cell  = null;

                    float TitleColumnPadding  = 3;
                    int   TitleColumnBorder   = 0;
                    float FooterColumnPadding = 3;
                    int   FooterColumnBorder  = 0;

                    BaseFont myfont        = BaseFont.createFont("//ArialCyr.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);


//            BaseFont myfont = BaseFont.createFont("//ARIAL.TTF","UTF-8", BaseFont.EMBEDDED);


                    Font      HeaderFont   = new  Font(myfont,8,Font.NORMAL,CMYKColor.BLACK);
                    Font      ColumnFont   = new  Font(myfont,8,Font.NORMAL,CMYKColor.BLACK);
                    Font      TitleFont    = new  Font(myfont,12,Font.BOLD,CMYKColor.RED);
                    Font      SubTitleFont = new  Font(myfont,9,Font.BOLD,CMYKColor.BLACK);




//            CMYKColor HeaderColor = new CMYKColor (218,218,218,218);

                    CMYKColor HeaderColor = new CMYKColor(2,0,0,22);

                    CMYKColor ColumnColor = new CMYKColor (0,0,0,0);
                    CMYKColor FooterColor = new CMYKColor (2,0,0,22);

                    document.open();

                    //*****************************************************************************************
                    //*****************************************************************************************


                    Set<PaymentView> paymentViews =  new HashSet<PaymentView>();

                    LinkedHashMap<String,List<Long>> parameterS = new LinkedHashMap<String,List<Long>>();


                    List<Long> Ids = new ArrayList<>();

                    Ids.add(iCreditID);


                    parameterS.put("loan",Ids);

                    paymentViews.addAll(this.paymentViewService.findByParameter(parameterS));



                    if(paymentViews.size()>0)
                    {

                        String debtorName = "";
                        String loanNumber = "";
                        Date loanDate = null;
                        String loanType = "";

                        double paymentsSum=0;
                        double paymentsPrincipal=0;
                        double paymentsInterest=0;
                        double paymentsPenalty=0;


                        for (PaymentView paymentViewInLoop: paymentViews)
                        {
                            debtorName = paymentViewInLoop.getV_debtor_name();
                            loanNumber = paymentViewInLoop.getV_loan_reg_number();
                            loanDate = paymentViewInLoop.getV_loan_reg_date();
                            loanType = String.valueOf(paymentViewInLoop.getV_loan_type_id());
                        }


                        //***********Title********************************************************************************
                        //*******************************************************************************************
                        table=new PdfPTable(1);
                        table.setWidthPercentage(90);
                        table.setHorizontalAlignment(Element.ALIGN_LEFT);

                        cell = new PdfPCell (new Paragraph ("Погашения",TitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        cell.setColspan(4);
                        table.addCell(cell);

                        document.add(table);

                        //********** SubTitle ***********************************************************************
                        //*********************************************************************************

                        table=new PdfPTable(2);
                        table.setWidthPercentage(90);
                        table.setHorizontalAlignment(Element.ALIGN_LEFT);
                        {
                            int iColWidth[] = new int[2];

                            iColWidth[0] = 20;
                            iColWidth[1] = 80;
                            table.setWidths(iColWidth);
                        }

                        cell = new PdfPCell (new Paragraph ("Наименование субъекта:",SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell(cell);

                        cell = new PdfPCell (new Paragraph (debtorName,SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell(cell);

                        cell = new PdfPCell (new Paragraph ("№ договора:",SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph (loanNumber,SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("Дата договора:",SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph (loanDate.toString(),SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("Вид кредита:",SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph (loanType,SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph (" ",SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        cell.setColspan(2);
                        table.addCell (cell);

                        document.add(table);

                        //************ Header *******************************************************************
                        //*******************************************************************************
                        table=new PdfPTable(8);
                        table.setWidthPercentage(100);
                        table.setHorizontalAlignment(Element.ALIGN_LEFT);

                        cell = new PdfPCell(new Paragraph("№ платежного документа",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph("Дата платежа",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph("Погашено в сомах",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph("Курс валюты",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph("Всего погашено",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph("Основной долг",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph("Проценты",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph("Штрафы",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        table.addCell (cell);

                        //************** Column **************************************************************************
                        //****************************************************************************************


                        for (PaymentView pv: paymentViews)
                        {
                            if(true)
                            {
                                cell = new PdfPCell(new Paragraph(pv.getV_payment_number(),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cell.setBackgroundColor (ColumnColor);
                                table.addCell (cell);

                                cell = new PdfPCell(new Paragraph(pv.getV_payment_date().toString(),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cell.setBackgroundColor (ColumnColor);
                                table.addCell (cell);

                                cell = new PdfPCell(new Paragraph(String.valueOf(pv.getV_payment_total_amount()),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cell.setBackgroundColor (ColumnColor);
                                table.addCell (cell);

                                cell = new PdfPCell(new Paragraph(String.valueOf(pv.getV_payment_exchange_rate()),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cell.setBackgroundColor (ColumnColor);
                                table.addCell (cell);

                                cell = new PdfPCell(new Paragraph(String.valueOf(pv.getV_payment_total_amount()),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cell.setBackgroundColor (ColumnColor);
                                table.addCell (cell);

                                cell = new PdfPCell(new Paragraph(String.valueOf(pv.getV_payment_principal()),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cell.setBackgroundColor (ColumnColor);
                                table.addCell (cell);

                                cell = new PdfPCell(new Paragraph(String.valueOf(pv.getV_payment_interest()),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cell.setBackgroundColor (ColumnColor);
                                table.addCell (cell);

                                cell = new PdfPCell(new Paragraph(String.valueOf(pv.getV_payment_penalty()),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cell.setBackgroundColor (ColumnColor);
                                table.addCell (cell);

                                paymentsSum += pv.getV_payment_total_amount();
                                paymentsPrincipal +=pv.getV_payment_principal();
                                paymentsInterest +=pv.getV_payment_interest();
                                paymentsPenalty +=pv.getV_payment_penalty();



                            }

                        }
                        //************************************************************************
                        //************************************************************************

                        cell = new PdfPCell(new Paragraph("ИТОГО :",ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph(" ",ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph(String.valueOf(paymentsSum),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph(" ",ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);


                        cell = new PdfPCell(new Paragraph(String.valueOf(paymentsSum),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph(String.valueOf(paymentsPrincipal),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph(String.valueOf(paymentsInterest),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph(String.valueOf(paymentsPenalty),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);
                        table.setHeaderRows(1);
                        document.add(table);

                        //**********Footer*********************************************************************************
                        //*******************************************************************************************


                        if(true)
                        {
                            table=new PdfPTable(2);
                            table.setWidthPercentage(100);
                            {
                                int iColWidth[] = new int[2];

                                iColWidth[0] = 25;
                                iColWidth[1] = 75;
                                table.setWidths(iColWidth);
                            }

                            cell = new PdfPCell (new Paragraph (" ",SubTitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            cell.setColspan(2);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph ("ФИО руководителя:",SubTitleFont));
                            cell.setFixedHeight(35);
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph ("responsible",SubTitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph ("Главный бухгалтер:",SubTitleFont));
                            cell.setFixedHeight(35);
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph ("accountant",SubTitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph ("Куратор:",SubTitleFont));
                            cell.setFixedHeight(35);
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph ("curator" ,SubTitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setPadding(FooterColumnPadding);
                            cell.setBorder(FooterColumnBorder);
                            table.addCell (cell);

                            document.add(table);
                        }

                    }
                    //****************************************************************************************
                    //****************************************************************************************
                    //**************************************************************************

                    document.close();
                    //**************************************************************************
                }
                catch(Exception Ex)
                {
                    System.out.println(Ex);
                }

                break;

            case "2" :
                try
                {

                    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);


                    LoanSummary loanSummary = loanSummaryService.getById(objectId);

                    Loan loan = loanSummary.getLoan();


                    Date      tRasDate  = loanSummary.getOnDate();
                    long      iCreditID = loan.getId();


                    //*********** Document ********************************************************************************
                    //*******************************************************************************************

                    PdfPTable table = null;
                    PdfPCell cell  = null;

                    float TitleColumnPadding  = 3;
                    int   TitleColumnBorder   = 0;
                    float FooterColumnPadding = 3;
                    int   FooterColumnBorder  = 0;

                    BaseFont myfont        = BaseFont.createFont("//ArialCyr.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);


//            BaseFont myfont = BaseFont.createFont("//ARIAL.TTF","UTF-8", BaseFont.EMBEDDED);


                    Font      HeaderFont   = new  Font(myfont,8,Font.NORMAL,CMYKColor.BLACK);
                    Font      ColumnFont   = new  Font(myfont,8,Font.NORMAL,CMYKColor.BLACK);
                    Font      TitleFont    = new  Font(myfont,12,Font.BOLD,CMYKColor.RED);
                    Font      SubTitleFont = new  Font(myfont,9,Font.BOLD,CMYKColor.BLACK);




//            CMYKColor HeaderColor = new CMYKColor (218,218,218,218);

                    CMYKColor HeaderColor = new CMYKColor(2,0,0,22);

                    CMYKColor ColumnColor = new CMYKColor (0,0,0,0);
                    CMYKColor FooterColor = new CMYKColor (2,0,0,22);

                    document.open();

                    //*****************************************************************************************
                    //*****************************************************************************************


                    Set<PaymentView> paymentViews =  new HashSet<PaymentView>();

                    LinkedHashMap<String,List<Long>> parameterS = new LinkedHashMap<String,List<Long>>();


                    List<Long> Ids = new ArrayList<>();

                    Ids.add(iCreditID);


                    parameterS.put("loan",Ids);

                    paymentViews.addAll(this.paymentViewService.findByParameter(parameterS));



                    if(paymentViews.size()>0)
                    {

                        String debtorName = "";
                        String loanNumber = "";
                        Date loanDate = null;
                        String loanType = "";

                        double paymentsSum=0;
                        double paymentsPrincipal=0;
                        double paymentsInterest=0;
                        double paymentsPenalty=0;


                        for (PaymentView paymentViewInLoop: paymentViews)
                        {
                            debtorName = paymentViewInLoop.getV_debtor_name();
                            loanNumber = paymentViewInLoop.getV_loan_reg_number();
                            loanDate = paymentViewInLoop.getV_loan_reg_date();
                            loanType = String.valueOf(paymentViewInLoop.getV_loan_type_id());
                        }


                        //***********Title********************************************************************************
                        //*******************************************************************************************
                        table=new PdfPTable(1);
                        table.setWidthPercentage(90);
                        table.setHorizontalAlignment(Element.ALIGN_LEFT);

                        cell = new PdfPCell (new Paragraph ("Погашения",TitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        cell.setColspan(4);
                        table.addCell(cell);

                        document.add(table);

                        //********** SubTitle ***********************************************************************
                        //*********************************************************************************

                        table=new PdfPTable(2);
                        table.setWidthPercentage(90);
                        table.setHorizontalAlignment(Element.ALIGN_LEFT);
                        {
                            int iColWidth[] = new int[2];

                            iColWidth[0] = 20;
                            iColWidth[1] = 80;
                            table.setWidths(iColWidth);
                        }

                        cell = new PdfPCell (new Paragraph ("Наименование субъекта:",SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell(cell);

                        cell = new PdfPCell (new Paragraph (debtorName,SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell(cell);

                        cell = new PdfPCell (new Paragraph ("№ договора:",SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph (loanNumber,SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("Дата договора:",SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph (loanDate.toString(),SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("Вид кредита:",SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph (loanType,SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph (" ",SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        cell.setColspan(2);
                        table.addCell (cell);

                        document.add(table);

                        //************ Header *******************************************************************
                        //*******************************************************************************
                        table=new PdfPTable(8);
                        table.setWidthPercentage(100);
                        table.setHorizontalAlignment(Element.ALIGN_LEFT);

                        cell = new PdfPCell(new Paragraph("№ платежного документа",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph("Дата платежа",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph("Погашено в сомах",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph("Курс валюты",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph("Всего погашено",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph("Основной долг",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph("Проценты",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph("Штрафы",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        table.addCell (cell);

                        //************** Column **************************************************************************
                        //****************************************************************************************


                        for (PaymentView pv: paymentViews)
                        {
                            if(true)
                            {
                                cell = new PdfPCell(new Paragraph(pv.getV_payment_number(),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cell.setBackgroundColor (ColumnColor);
                                table.addCell (cell);

                                cell = new PdfPCell(new Paragraph(pv.getV_payment_date().toString(),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cell.setBackgroundColor (ColumnColor);
                                table.addCell (cell);

                                cell = new PdfPCell(new Paragraph(String.valueOf(pv.getV_payment_total_amount()),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cell.setBackgroundColor (ColumnColor);
                                table.addCell (cell);

                                cell = new PdfPCell(new Paragraph(String.valueOf(pv.getV_payment_exchange_rate()),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cell.setBackgroundColor (ColumnColor);
                                table.addCell (cell);

                                cell = new PdfPCell(new Paragraph(String.valueOf(pv.getV_payment_total_amount()),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cell.setBackgroundColor (ColumnColor);
                                table.addCell (cell);

                                cell = new PdfPCell(new Paragraph(String.valueOf(pv.getV_payment_principal()),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cell.setBackgroundColor (ColumnColor);
                                table.addCell (cell);

                                cell = new PdfPCell(new Paragraph(String.valueOf(pv.getV_payment_interest()),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cell.setBackgroundColor (ColumnColor);
                                table.addCell (cell);

                                cell = new PdfPCell(new Paragraph(String.valueOf(pv.getV_payment_penalty()),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cell.setBackgroundColor (ColumnColor);
                                table.addCell (cell);

                                paymentsSum += pv.getV_payment_total_amount();
                                paymentsPrincipal +=pv.getV_payment_principal();
                                paymentsInterest +=pv.getV_payment_interest();
                                paymentsPenalty +=pv.getV_payment_penalty();



                            }

                        }
                        //************************************************************************
                        //************************************************************************

                        cell = new PdfPCell(new Paragraph("ИТОГО :",ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph(" ",ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph(String.valueOf(paymentsSum),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph(" ",ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);


                        cell = new PdfPCell(new Paragraph(String.valueOf(paymentsSum),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph(String.valueOf(paymentsPrincipal),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph(String.valueOf(paymentsInterest),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph(String.valueOf(paymentsPenalty),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);
                        table.setHeaderRows(1);
                        document.add(table);

                        //**********Footer*********************************************************************************
                        //*******************************************************************************************


                        if(true)
                        {
                            table=new PdfPTable(2);
                            table.setWidthPercentage(100);
                            {
                                int iColWidth[] = new int[2];

                                iColWidth[0] = 25;
                                iColWidth[1] = 75;
                                table.setWidths(iColWidth);
                            }

                            cell = new PdfPCell (new Paragraph (" ",SubTitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            cell.setColspan(2);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph ("ФИО руководителя:",SubTitleFont));
                            cell.setFixedHeight(35);
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph ("responsible",SubTitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph ("Главный бухгалтер:",SubTitleFont));
                            cell.setFixedHeight(35);
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph ("accountant",SubTitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph ("Куратор:",SubTitleFont));
                            cell.setFixedHeight(35);
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph ("curator" ,SubTitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setPadding(FooterColumnPadding);
                            cell.setBorder(FooterColumnBorder);
                            table.addCell (cell);

                            document.add(table);
                        }

                    }
                    //****************************************************************************************
                    //****************************************************************************************
                    //**************************************************************************

                    document.close();
                    //**************************************************************************
                }
                catch(Exception Ex)
                {
                    System.out.println(Ex);
                }

                break;


        }

    }


}
