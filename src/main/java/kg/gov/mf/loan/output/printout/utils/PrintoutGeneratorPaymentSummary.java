package kg.gov.mf.loan.output.printout.utils;


import com.lowagie.text.*;
import kg.gov.mf.loan.admin.org.model.Department;
import kg.gov.mf.loan.admin.org.model.Organization;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.DepartmentService;
import kg.gov.mf.loan.admin.org.service.OrganizationService;
import kg.gov.mf.loan.admin.org.service.PersonService;
import kg.gov.mf.loan.admin.org.service.StaffService;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.process.LoanSummary;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.process.LoanSummaryService;
import kg.gov.mf.loan.output.printout.model.PrintoutTemplate;
import kg.gov.mf.loan.output.report.model.DebtorView;
import kg.gov.mf.loan.output.report.model.ObjectList;
import kg.gov.mf.loan.output.report.model.ObjectListValue;
import kg.gov.mf.loan.output.report.model.PaymentView;
import kg.gov.mf.loan.output.report.service.LoanViewService;

import kg.gov.mf.loan.output.report.service.PaymentViewService;
import kg.gov.mf.loan.output.report.utils.ReportTool;
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

    @Autowired
    UserService userService;

    @Autowired
    StaffService staffService;

    @Autowired
    DebtorService debtorService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    PersonService personService;

    public void generatePrintoutByTemplate(PrintoutTemplate printoutTemplate, Date onDate, long objectId, Document document){


        try
        {

            SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

            ReportTool reportTool = new ReportTool();



            LoanSummary loanSummary = loanSummaryService.getById(objectId);



            Loan loan = loanSummary.getLoan();


            Date      tRasDate  = loanSummary.getOnDate();
            long      iCreditID = loan.getId();

            long        iPersonID = loan.getDebtor().getId();

            Debtor debtor =  debtorService.getById(iPersonID);

            User curator = new User();

            Staff curatorStaff = new Staff();

            if(loan.getSupervisorId()>0) curator = userService.findById(loan.getSupervisorId());

            if(curator.getStaff()!=null)
                curatorStaff = staffService.findById(curator.getStaff().getId());


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

            LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<String,List<String>>();


            List<String> Ids = new ArrayList<>();

            Ids.add(String.valueOf(iCreditID));


            parameterS.put("r=inv_loan_id",Ids);

            paymentViews.addAll(this.paymentViewService.findByParameter(parameterS));



            if(paymentViews.size()>0)
            {

                String debtorName = "";
                String loanNumber = "";
                Date loanDate = null;
                String loanType = "";

                double paymentsSum=0;
                double paymentsSumInCurrency=0;

                double paymentsPrincipalInCurrency=0;
                double paymentsInterestInCurrency=0;
                double paymentsPenaltyInCurrency=0;


                for (PaymentView paymentViewInLoop: paymentViews)
                {
                    debtorName = paymentViewInLoop.getV_debtor_name();
                    loanNumber = paymentViewInLoop.getV_loan_reg_number();
                    loanDate = paymentViewInLoop.getV_loan_reg_date();
                    loanType = reportTool.FormatNumber(paymentViewInLoop.getV_loan_type_id());
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

//                cell = new PdfPCell (new Paragraph ("Вид кредита:",SubTitleFont));
//                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setPadding(TitleColumnPadding);
//                cell.setBorder(TitleColumnBorder);
//                table.addCell (cell);
//
//                cell = new PdfPCell (new Paragraph (loanType,SubTitleFont));
//                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setPadding(TitleColumnPadding);
//                cell.setBorder(TitleColumnBorder);
//                table.addCell (cell);

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

                        cell = new PdfPCell(new Paragraph(reportTool.DateToString(pv.getV_payment_date()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(pv.getV_payment_total_amount()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(pv.getV_payment_exchange_rate()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(pv.getV_payment_total_amount()/pv.getV_payment_exchange_rate()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(pv.getV_payment_principal()/pv.getV_payment_exchange_rate()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(pv.getV_payment_interest()/pv.getV_payment_exchange_rate()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(pv.getV_payment_penalty()/pv.getV_payment_exchange_rate()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table.addCell (cell);

                        paymentsSum += pv.getV_payment_total_amount();
                        paymentsSumInCurrency += pv.getV_payment_total_amount()/pv.getV_payment_exchange_rate();
                        paymentsPrincipalInCurrency +=pv.getV_payment_principal()/pv.getV_payment_exchange_rate();
                        paymentsInterestInCurrency +=pv.getV_payment_interest()/pv.getV_payment_exchange_rate();
                        paymentsPenaltyInCurrency +=pv.getV_payment_penalty()/pv.getV_payment_exchange_rate();



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

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(paymentsSum),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(" ",ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                table.addCell (cell);


                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(paymentsSumInCurrency),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(paymentsPrincipalInCurrency),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(paymentsInterestInCurrency),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(paymentsPenaltyInCurrency),ColumnFont));
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

                    Staff debtorResponsible = null;
                    Staff debtorAccountant  = null;

                    if(debtor.getOwner().getOwnerType().toString()=="ORGANIZATION")
                    {
                        Organization organization = organizationService.findById(debtor.getOwner().getEntityId());

                        for (Department department: organization.getDepartment())
                        {
                            debtorResponsible = staffService.findAllByDepartment(department).get(0);
                            debtorAccountant  = staffService.findAllByDepartment(department).get(1);
                            break;
                        }


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


                    if(debtorResponsible!=null)
                    {
                        cell = new PdfPCell (new Paragraph (debtorResponsible.getName(),SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(FooterColumnBorder);
                        cell.setPadding(FooterColumnPadding);
                        table.addCell (cell);
                    }
                    else
                    {
                        cell = new PdfPCell (new Paragraph (debtor.getName(),SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(FooterColumnBorder);
                        cell.setPadding(FooterColumnPadding);
                        table.addCell (cell);

                    }

                    if(debtorAccountant!=null)
                    {
                        cell = new PdfPCell (new Paragraph ("Главный бухгалтер:",SubTitleFont));
                        cell.setFixedHeight(35);
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(FooterColumnBorder);
                        cell.setPadding(FooterColumnPadding);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph (debtorAccountant.getName(),SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(FooterColumnBorder);
                        cell.setPadding(FooterColumnPadding);
                        table.addCell (cell);

                    }
                    else
                    {
                        cell = new PdfPCell (new Paragraph ("",SubTitleFont));
                        cell.setFixedHeight(35);
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(FooterColumnBorder);
                        cell.setPadding(FooterColumnPadding);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("",SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(FooterColumnBorder);
                        cell.setPadding(FooterColumnPadding);
                        table.addCell (cell);


                    }

                    if(curatorStaff.getId()>0)
                    {
                        cell = new PdfPCell (new Paragraph ("Куратор:",SubTitleFont));
                        cell.setFixedHeight(35);
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(FooterColumnBorder);
                        cell.setPadding(FooterColumnPadding);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph (curatorStaff.getPerson().getIdentityDoc().getIdentityDocDetails().getFirstname().substring(0,1)+" "+curatorStaff.getPerson().getIdentityDoc().getIdentityDocDetails().getLastname(),SubTitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(FooterColumnPadding);
                        cell.setBorder(FooterColumnBorder);
                        table.addCell (cell);
                    }

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


    }


}
