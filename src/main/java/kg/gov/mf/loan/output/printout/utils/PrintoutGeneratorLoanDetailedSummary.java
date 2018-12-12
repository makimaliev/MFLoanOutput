package kg.gov.mf.loan.output.printout.utils;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import kg.gov.mf.loan.admin.org.model.Address;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.AddressService;
import kg.gov.mf.loan.admin.org.service.StaffService;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.orderterm.CurrencyRate;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermCurrency;
import kg.gov.mf.loan.manage.model.process.LoanDetailedSummary;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.loan.LoanTypeService;
import kg.gov.mf.loan.manage.service.orderterm.CurrencyRateService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermCurrencyService;
import kg.gov.mf.loan.manage.service.process.LoanDetailedSummaryService;
import kg.gov.mf.loan.output.printout.model.PrintoutTemplate;
import kg.gov.mf.loan.output.report.utils.ReportTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class PrintoutGeneratorLoanDetailedSummary {

    @Autowired
    LoanDetailedSummaryService loanDetailedSummaryService;

    @Autowired
    LoanService loanService;

    @Autowired
    AddressService addressService;

    @Autowired
    LoanTypeService loanTypeService;

    @Autowired
    OrderTermCurrencyService orderTermCurrencyService;

    @Autowired
    CurrencyRateService currencyRateService;

    @Autowired
    DebtorService debtorService;

    @Autowired
    UserService userService;

    @Autowired
    StaffService staffService;

    @PersistenceContext
    private EntityManager entityManager;


    public void generatePrintoutByTemplate(PrintoutTemplate printoutTemplate, Date onDate, long objectId, Document document){

        try{
            SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
            ReportTool reportTool = new ReportTool();

            Double sumOfDisbursement=0.0;
            Double sumOfPrincipalPaid=0.0;
            Double sumOfDaysInPeriod=0.0;
            Double sumOfInterestAccrued=0.0;
            Double sumOfInterestPaid=0.0;
            Double sumOfPenaltyAccrued=0.0;
            Double sumOfPenaltyPaid=0.0;
            Double sumOfPrincipalInterestPenaltyPaid=0.0;

            Double f1=0.0;
            Double f2=0.0;
            Double f5=0.0;
            Double g1=0.0;
            Double g2=0.0;
            Double g5=0.0;


            LoanDetailedSummary loanDetailedSummary=loanDetailedSummaryService.getById(objectId);



            Loan loan= this.loanService.getById(loanDetailedSummary.getLoan().getId());

            Debtor debtor= this.debtorService.getById(loan.getDebtor().getId());
            Owner owner=debtor.getOwner();
            Address address= this.addressService.findById(owner.getAddress().getId());

            User curator = new User();

            Staff curatorStaff = new Staff();

            if(loan.getSupervisorId()>0) curator = userService.findById(loan.getSupervisorId());

            if(curator.getStaff()!=null)
                curatorStaff = staffService.findById(curator.getStaff().getId());

            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

            String debtorName=debtor.getName();
            String loanRegNumberAndDate=loan.getRegNumber()+" / "+formatter.format(loan.getRegDate());
            String worksector=debtor.getWorkSector().getName();
            String regionAndDistrict=address.getRegion().getName()+" / "+address.getDistrict().getName();
            String loanType=loan.getLoanType().getName();
            String amountAndCurrency=reportTool.FormatNumber(loan.getAmount())+" / "+loan.getCurrency().getName();

            String date = formatter.format(loanDetailedSummary.getOnDate());
            String supervisor=curatorStaff.getName();


            //*********** Document ********************************************************************************
            //*******************************************************************************************

            PdfPTable table = null;
            PdfPTable table2 = null;
            PdfPCell cell  = null;

            float TitleColumnPadding  = 3;
            int   TitleColumnBorder   = 0;
            float FooterColumnPadding = 3;
            int   FooterColumnBorder  = 0;

            BaseFont myfont        = BaseFont.createFont("//ArialCyr.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);


//            BaseFont myfont = BaseFont.createFont("//ARIAL.TTF","UTF-8", BaseFont.EMBEDDED);


            Font HeaderFont   = new  Font(myfont,7,Font.NORMAL, CMYKColor.BLACK);
            Font      ColumnFont   = new  Font(myfont,7,Font.NORMAL,CMYKColor.BLACK);
            Font      TitleFont    = new  Font(myfont,11,Font.BOLD,CMYKColor.BLACK);
            Font      SubTitleFont = new  Font(myfont,9,Font.BOLD,CMYKColor.BLACK);

            CMYKColor HeaderColor = new CMYKColor(2,0,0,22);
            CMYKColor ColumnColor = new CMYKColor (0,0,0,0);
            CMYKColor FooterColor = new CMYKColor (2,0,0,22);

            document.open();

            Set<LoanDetailedSummary> loanDetailedSummaries=loan.getLoanDetailedSummaries();

            if (loanDetailedSummaries.size()>0 ){


                //***********Title********************************************************************************
                //*******************************************************************************************
                table=new PdfPTable(4);
                table.setWidthPercentage(90);
                table.setHorizontalAlignment(Element.ALIGN_LEFT);

                cell = new PdfPCell (new Paragraph ("Расчет показателей задолженности по государственным заемным средствам",TitleFont));
                cell.setFixedHeight(40);
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                cell.setColspan(4);
                table.addCell(cell);

                cell = new PdfPCell (new Paragraph ("Наименование предприятия:",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                table.addCell(cell);

                cell = new PdfPCell (new Paragraph (debtorName,TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                table.addCell(cell);

                cell = new PdfPCell (new Paragraph ("№ договора / Дата :",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (loanRegNumberAndDate,TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph ("Отрасль:",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (worksector,TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph ("Вид кредита:",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (loanType,TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph ("Область/Район:",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (regionAndDistrict,TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph ("Сумма / Валюта :",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (amountAndCurrency,TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph ("Дата расчета:",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (date,TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph ("Куратор:",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (supervisor,TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (" ",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                cell.setColspan(4);
                table.addCell (cell);

                document.add(table);

                //************ Header *******************************************************************
                //*******************************************************************************
                table2=new PdfPTable(22);
                table2.setWidthPercentage(100);
                table2.setHorizontalAlignment(Element.ALIGN_LEFT);

                int iColWindth[] = new int[22];

                for(int i=0;i<22;i++)
                    iColWindth[i] = 6;

                iColWindth[0] = 3;
                iColWindth[8] = 5;

                table2.setWidths(iColWindth);

                cell = new PdfPCell(new Paragraph("№",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(3);
                table2.addCell (cell);


                cell = new PdfPCell(new Paragraph("Дата платежа",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(3);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("Расчет задолженности по основному долгу",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setColspan(6);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("Расчет задолженности по процентам",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setColspan(7);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("Расчет задолженности по штрафам",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setColspan(6);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("Всего погашено",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(3);
                table2.addCell (cell);
//**************************************************************

                cell = new PdfPCell(new Paragraph("+Получено (-снято) средств",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("Остаток задолженности по основной сумме",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("Фактическое погашение основной суммы",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("Погашение основной суммы нарастающей",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setColspan(2);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("Просроченная по основному долгу",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("Количество дней",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("Начислено процентов",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("Погашение процентов факт",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("Погашение процентов по графику нараст",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setColspan(2);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("Погашение процентов нарастающим",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("Просроченная по процентам",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("Начисление штрафов",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("Погашение штрафов",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("Погашение штрафов нараст",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setColspan(2);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("Штрафы нарастающим",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("Остаток по штрафам",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph(" по графику",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph(" по факту",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("текущих",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("накопленных",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("текущих",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph("накопленных",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                table2.addCell (cell);


                LoanDetailedSummary detailedSummary1=new LoanDetailedSummary();
                int counter=0;
                for(LoanDetailedSummary detailedSummary:loanDetailedSummaries)
                {
                    if(true)
                    {
                        counter+=1;
                        cell = new PdfPCell(new Paragraph(String.valueOf(counter),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.DateToString(detailedSummary.getOnDate()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        cell.setNoWrap(true);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getDisbursement()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getPrincipalOutstanding()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getPrincipalPaid()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getTotalPrincipalPayment()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getTotalPrincipalPaid()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getPrincipalOverdue()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getDaysInPeriod()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getInterestAccrued()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getInterestPaid()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getTotalInterestAccrued()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getTotalCollectedInterestPayment()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getTotalInterestPaid()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getInterestOverdue()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getPenaltyAccrued()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getPenaltyPaid()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getTotalPenaltyAccrued()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getTotalCollectedPenaltyPayment()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getTotalPenaltyPaid()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getPenaltyOutstanding()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor (ColumnColor);
                        table2.addCell (cell);

                        cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getPrincipalPaid()+detailedSummary.getInterestPaid()+detailedSummary.getPenaltyPaid()),ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBackgroundColor(FooterColor);
                        table2.addCell (cell);




                        detailedSummary1=detailedSummary;

                        sumOfDaysInPeriod+=detailedSummary.getDaysInPeriod();

                    }



                }





                //************************************************************************
                //************************************************************************
                cell = new PdfPCell(new Paragraph("Итого",ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                cell.setColspan(2);
                table2.addCell (cell);


                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalDisbursement()),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor(FooterColor);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph(" ",ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor(FooterColor);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalPrincipalPaid()),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph(" ",ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor(FooterColor);
                cell.setColspan(3);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(sumOfDaysInPeriod),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalInterestAccrued()),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalInterestPaid()),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph(" ",ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor(FooterColor);
                cell.setColspan(4);
                table2.addCell (cell);


                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalPenaltyAccrued()),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalPenaltyPaid()),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph(" ",ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor(FooterColor);
                cell.setColspan(4);
                table2.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalPrincipalPaid()+detailedSummary1.getTotalInterestPaid()+detailedSummary1.getTotalPenaltyPaid()),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                table2.addCell (cell);
//                table2.setHeaderRows(3);


                document.add(table2);



                cell = new PdfPCell(new Paragraph(" ",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                table=new PdfPTable(1);
                table.setWidthPercentage(100);
                cell = new PdfPCell(new Paragraph("Всего на: "+reportTool.DateToString(loanDetailedSummary.getOnDate()),TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                document.add(table);

                //************************** Summary **************************************************************
                //****************************************************************************************

                double A1=detailedSummary1.getTotalDisbursement();
                double A3=detailedSummary1.getCollectedInterestDisbursed();
                double A6=detailedSummary1.getCollectedPenaltyDisbursed();
                double A8=A1+A3+A6;


                double B2=detailedSummary1.getTotalInterestAccrued();
                double B4=B2;
                double B5=detailedSummary1.getTotalPenaltyAccrued();
                double B7=B5;
                double B8=B2+B5;


                double C1=detailedSummary1.getTotalDisbursement();
                double C3=A3;
                double C4=B4;
                double C2=C3+C4;
                double C6=A6;
                double C7=B7;
                double C5=C6+C7;
                double C8=C1+C2+C5;

                double D1=detailedSummary1.getTotalPrincipalPayment();
                double D3=detailedSummary1.getTotalCollectedInterestPayment();
                double D4=detailedSummary1.getTotalInterestAccruedOnInterestPayment();
                double D2=D3+D4;
                double D6=detailedSummary1.getTotalCollectedPenaltyPayment();
                double D7=detailedSummary1.getTotalPenaltyAccrued();
                double D5=D6+D7;
                double D8=D1+D2+D5;

                double E1=detailedSummary1.getTotalPrincipalPaid();
                double E2=detailedSummary1.getTotalInterestPaid();
                double E5=detailedSummary1.getTotalPenaltyPaid();
                double E8=E1+E2+E5;

                double F1=detailedSummary1.getPrincipalOutstanding();
                double F2=detailedSummary1.getInterestOutstanding();
                double F5=detailedSummary1.getPenaltyOutstanding();


                double F8=0;

                if(F1>0) F8+=F1;
                if(F2>0) F8+=F2;
                if(F5>0) F8+=F5;

                double G1=detailedSummary1.getPrincipalOverdue();
                double G2=detailedSummary1.getInterestOverdue();
                double G5=detailedSummary1.getPenaltyOverdue();

                double G8=0;

                if(G1>0) G8+=G1;
                if(G2>0) G8+=G2;
                if(G5>0) G8+=G5;


                table=new PdfPTable(8);
                table.setWidthPercentage(100);

                cell = new PdfPCell (new Paragraph ("Показатели",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table.addCell(cell);

                cell = new PdfPCell (new Paragraph ("Всего получено",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table.addCell(cell);

                cell = new PdfPCell (new Paragraph ("Всего начислено текущих",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table.addCell(cell);

                cell = new PdfPCell (new Paragraph ("Подлежит погашению",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (HeaderColor);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell (new Paragraph ("Фактически погашено на дату расчета",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table.addCell(cell);

                cell = new PdfPCell (new Paragraph ("Остаток задолженности",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table.addCell(cell);

                cell = new PdfPCell (new Paragraph ("в т.ч. просроченная",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table.addCell(cell);

                cell = new PdfPCell (new Paragraph ("Всего",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (HeaderColor);
                table.addCell(cell);

                cell = new PdfPCell (new Paragraph ("На дату расчета",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (HeaderColor);
                table.addCell(cell);

                //*********Columns*****************************************************

                cell = new PdfPCell(new Paragraph("Основной долг",ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(A1),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(C1),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(D1),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(E1),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(F1),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(G1),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Проценты всего, в т.ч.",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);


                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(B2),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(C2),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(D2),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(E2),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(F2),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(G2),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Накопленные проценты",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(A3),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(C3),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(D3),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);


                cell = new PdfPCell(new Paragraph("Текущие проценты",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(B4),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(C4),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(D4),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);


                cell = new PdfPCell (new Paragraph ("Штрафы всего, в т.ч.",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(B5),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(C5),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(D5),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(E5),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(F5),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(G5),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);


                cell = new PdfPCell (new Paragraph ("Накопленные штрафы",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(A6),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(C6),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(D6),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);


                cell = new PdfPCell (new Paragraph ("Текущие штрафы",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(B7),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(C7),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(D7),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);

                cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (ColumnColor);
                table.addCell (cell);


                cell = new PdfPCell(new Paragraph("ИТОГО",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(A8),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(B8),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(C8),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(D8),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(E8),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                table.addCell (cell);

                if(detailedSummary1.getPrincipalOutstanding()>0){
                    f1=detailedSummary1.getPrincipalOutstanding();
                }
                if(detailedSummary1.getInterestOutstanding()>0){
                    f2=detailedSummary1.getInterestOutstanding();
                }
                if(detailedSummary1.getPenaltyOutstanding()>0){
                    f5=detailedSummary1.getPenaltyOutstanding();
                }
                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(F8),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                table.addCell (cell);

                if(detailedSummary1.getPrincipalOverdue()>0){
                    g1=detailedSummary1.getPrincipalOverdue();
                }
                if(detailedSummary1.getInterestOverdue()>0){
                    g2=detailedSummary1.getInterestOverdue();
                }
                if(detailedSummary1.getPenaltyOverdue()>0){
                    g5=detailedSummary1.getPenaltyOverdue();
                }
                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(G8),ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor (FooterColor);
                table.addCell (cell);

                document.add(table);




            }

            document.close();



        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
