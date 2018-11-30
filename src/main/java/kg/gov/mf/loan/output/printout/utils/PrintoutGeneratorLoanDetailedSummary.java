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
            String loanQuery="select l.id,l.amount,l.regDate,l.regNumber,l.supervisorId,l.restructureDate,l.restructureDescription,\n" +
                    "       l.creditOrderId,l.currencyId,l.debtorId,l.fundId,l.loanStateId,l.loanTypeId,l.parent_id,\n" +
                    "       l.closeDate,l.bankDataId from loan l, loanDetailedSummary lDS where lDS.id=\""+objectId+"\" and lDS.loanId=l.id";
            Query loanQuer=entityManager.createNativeQuery(loanQuery,Loan.class);

            Loan loan= (Loan) loanQuer.getSingleResult();
            Debtor debtor=loan.getDebtor();
            Owner owner=debtor.getOwner();
            Address address=owner.getAddress();

            User curator = new User();

            Staff curatorStaff = new Staff();

            if(loan.getSupervisorId()>0) curator = userService.findById(loan.getSupervisorId());

            if(curator.getStaff()!=null)
                curatorStaff = staffService.findById(curator.getStaff().getId());

            String debtorName=debtor.getName();
            String loanRegNumberAndDate=loan.getRegNumber()+" / "+loan.getRegDate();
            String worksector=debtor.getWorkSector().getName();
            String regionAndDistrict=address.getRegion()+" / "+address.getDistrict();
            String loanType=loan.getLoanType().getName();
            String amountAndCurrency=String.valueOf(loan.getAmount())+" / "+loan.getCurrency().getName();
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            String date = formatter.format(loanDetailedSummary.getOnDate());
            String supervisor=curatorStaff.getName();


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


            Font HeaderFont   = new  Font(myfont,8,Font.NORMAL, CMYKColor.BLACK);
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

            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy.MM.dd");
            String strDate = formatter.format(loanDetailedSummary.getOnDate());
            String baseQuery="select * from loanDetailedSummary where onDate<=\""+strDate+"\"";
            Query query=entityManager.createNativeQuery(baseQuery,LoanDetailedSummary.class);
            List<LoanDetailedSummary> loanDetailedSummaries=query.getResultList();

            if (loanDetailedSummaries.size()>0){


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
                table=new PdfPTable(22);
                table.setWidthPercentage(100);
                table.setHorizontalAlignment(Element.ALIGN_LEFT);

                cell = new PdfPCell(new Paragraph("№",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(3);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Дата платежа",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(3);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Расчет задолженности по основному долгу",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setColspan(6);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Расчет задолженности по процентам",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setColspan(7);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Расчет задолженности по штрафам",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setColspan(6);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Всего погашено",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(3);
                table.addCell (cell);
                //**************************************************************

                cell = new PdfPCell(new Paragraph("+Получено (-снято) средств",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Остаток задолженности по основной сумме",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Фактическое погашение основной суммы",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Погашение основной суммы нарастающей",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setColspan(2);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Просроченная по основному долгу",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Количество дней",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Начислено процентов",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Погашение процентов факт",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Погашение процентов по графику нараст",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setColspan(2);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Погашение процентов нарастающим",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Просроченная по процентам",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Начисление штрафов",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Погашение штрафов",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Погашение штрафов нараст",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setColspan(2);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Штрафы нарастающим",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("Остаток по штрафам",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                cell.setRowspan(2);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(" по графику",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph(" по факту",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("текущих",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("накопленных",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("текущих",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                table.addCell (cell);

                cell = new PdfPCell(new Paragraph("накопленных",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor (HeaderColor);
                table.addCell (cell);

                int iColWindth[] = new int[22];

                for(int i=0;i<22;i++)
                    iColWindth[i] = 6;

                iColWindth[0] = 3;
                iColWindth[8] = 5;

                table.setWidths(iColWindth);




                //************** Column **************************************************************************
                //****************************************************************************************



                LoanDetailedSummary detailedSummary1=new LoanDetailedSummary();
                int counter=0;
                for(LoanDetailedSummary detailedSummary:loanDetailedSummaries)
                {

                    counter+=1;
                    cell = new PdfPCell(new Paragraph(String.valueOf(counter),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.DateToString(detailedSummary.getOnDate()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    cell.setNoWrap(true);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getDisbursement()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getPrincipalOutstanding()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getPrincipalPaid()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getTotalPrincipalPayment()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getTotalPrincipalPaid()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getPrincipalOverdue()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getDaysInPeriod()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getInterestAccrued()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getInterestPaid()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getTotalInterestAccrued()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getTotalCollectedInterestPayment()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getTotalInterestPaid()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getInterestOverdue()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getPenaltyAccrued()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getPenaltyPaid()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getTotalPenaltyAccrued()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getTotalCollectedPenaltyPayment()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getTotalPenaltyPaid()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getPenaltyOutstanding()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getPrincipalPaid()+detailedSummary.getInterestPaid()+detailedSummary.getPenaltyPaid()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor(FooterColor);
                    table.addCell (cell);



                    //************************************************************************
                    //************************************************************************
                    cell = new PdfPCell(new Paragraph("Итого",ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (FooterColor);
                    cell.setColspan(2);
                    table.addCell (cell);


                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(sumOfDisbursement+detailedSummary.getDisbursement()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor(FooterColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(" ",ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor(FooterColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getPrincipalPaid()+sumOfPrincipalPaid),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (FooterColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(" ",ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor(FooterColor);
                    cell.setColspan(3);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getDaysInPeriod()+sumOfDaysInPeriod),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (FooterColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getInterestAccrued()+sumOfInterestAccrued),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (FooterColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getInterestPaid()+sumOfInterestPaid),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (FooterColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(" ",ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor(FooterColor);
                    cell.setColspan(4);
                    table.addCell (cell);


                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getPenaltyAccrued()+sumOfPenaltyAccrued),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (FooterColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getPenaltyPaid()+sumOfPenaltyPaid),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (FooterColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(" ",ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor(FooterColor);
                    cell.setColspan(4);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary.getPrincipalPaid()+detailedSummary.getInterestPaid()+detailedSummary.getPenaltyPaid()+sumOfPrincipalInterestPenaltyPaid),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (FooterColor);
                    table.addCell (cell);
                    table.setHeaderRows(3);

                    document.add(table);
                    detailedSummary1=detailedSummary;

                }
                    //************ Details *******************************************************************
                    //*******************************************************************************
                    cell = new PdfPCell(new Paragraph(" ",TitleFont));
                    cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setPadding(TitleColumnPadding);
                    cell.setBorder(TitleColumnBorder);
                    table.addCell (cell);

                    table=new PdfPTable(1);
                    table.setWidthPercentage(100);
                    cell = new PdfPCell(new Paragraph("Всего на:"+reportTool.DateToString(loanDetailedSummary.getOnDate()),TitleFont));
                    cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setPadding(TitleColumnPadding);
                    cell.setBorder(TitleColumnBorder);
                    table.addCell (cell);

                    document.add(table);

                    //************************** Summary **************************************************************
                    //****************************************************************************************

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

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(sumOfDisbursement),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalDisbursement()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalPrincipalPayment()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalPrincipalPaid()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getPrincipalOutstanding()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getPrincipalOverdue()),ColumnFont));
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


                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalInterestAccrued()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getCollectedInterestDisbursed()+detailedSummary1.getTotalInterestAccrued()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalCollectedInterestPayment()+detailedSummary1.getTotalInterestAccruedOnInterestPayment()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalInterestPaid()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getInterestOutstanding()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getInterestOverdue()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph("Накопленные проценты",HeaderFont));
                    cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getCollectedInterestDisbursed()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getCollectedInterestDisbursed()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalCollectedInterestPayment()),ColumnFont));
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

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalInterestAccrued()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalInterestAccrued()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalInterestAccruedOnInterestPayment()),ColumnFont));
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

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalPenaltyAccrued()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getCollectedPenaltyDisbursed()+detailedSummary1.getTotalPenaltyAccrued()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalCollectedPenaltyPayment()+detailedSummary1.getTotalPenaltyAccrued()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalPenaltyPaid()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getPenaltyOutstanding()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getPenaltyOverdue()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);


                    cell = new PdfPCell (new Paragraph ("Накопленные штрафы",HeaderFont));
                    cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getCollectedPenaltyDisbursed()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell (new Paragraph (" ",HeaderFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getCollectedPenaltyDisbursed()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalCollectedPenaltyPayment()),ColumnFont));
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

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalPenaltyAccrued()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalPenaltyAccrued()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (ColumnColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalPenaltyAccrued()),ColumnFont));
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

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalDisbursement()+detailedSummary1.getCollectedInterestDisbursed()+detailedSummary1.getCollectedPenaltyDisbursed()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (FooterColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalInterestAccrued()+detailedSummary1.getTotalPenaltyAccrued()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (FooterColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalDisbursement()+detailedSummary1.getCollectedInterestDisbursed()+detailedSummary1.getTotalInterestAccrued()+detailedSummary1.getCollectedPenaltyDisbursed()+detailedSummary1.getTotalPenaltyAccrued()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (FooterColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(detailedSummary1.getTotalPrincipalPayment()+detailedSummary1.getTotalCollectedInterestPayment()+detailedSummary1.getTotalInterestAccruedOnInterestPayment()+detailedSummary1.getPenaltyAccrued()),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (FooterColor);
                    table.addCell (cell);

                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(2*(detailedSummary1.getTotalPrincipalPaid()+detailedSummary1.getTotalInterestPaid())),ColumnFont));
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
                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(f1+f2+f5),ColumnFont));
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
                    cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(g1+g2+g5),ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (FooterColor);
                    table.addCell (cell);

                    document.add(table);
            document.close();
        }





        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
