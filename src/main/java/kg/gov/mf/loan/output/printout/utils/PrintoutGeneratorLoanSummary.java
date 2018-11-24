package kg.gov.mf.loan.output.printout.utils;


import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import kg.gov.mf.loan.admin.org.model.Department;
import kg.gov.mf.loan.admin.org.model.Organization;
import kg.gov.mf.loan.admin.org.model.Person;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.DepartmentService;
import kg.gov.mf.loan.admin.org.service.OrganizationService;
import kg.gov.mf.loan.admin.org.service.PersonService;
import kg.gov.mf.loan.admin.org.service.StaffService;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.loan.PaymentSchedule;
import kg.gov.mf.loan.manage.model.orderterm.CurrencyRate;
import kg.gov.mf.loan.manage.model.process.LoanSummary;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.loan.PaymentScheduleService;
import kg.gov.mf.loan.manage.service.orderterm.CurrencyRateService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermCurrencyService;
import kg.gov.mf.loan.manage.service.process.LoanSummaryService;
import kg.gov.mf.loan.output.printout.model.PrintoutTemplate;
import kg.gov.mf.loan.output.report.model.LoanSummaryView;
import kg.gov.mf.loan.output.report.model.PaymentScheduleView;
import kg.gov.mf.loan.output.report.model.PaymentView;
import kg.gov.mf.loan.output.report.service.LoanSummaryViewService;
import kg.gov.mf.loan.output.report.service.PaymentScheduleViewService;
import kg.gov.mf.loan.output.report.service.PaymentViewService;
import kg.gov.mf.loan.output.report.utils.ReportTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.text.SimpleDateFormat;
import java.util.*;


public class PrintoutGeneratorLoanSummary {
	
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
    LoanSummaryViewService loanSummaryViewService;

	@Autowired
    CurrencyRateService currencyRateService;

	@Autowired
    OrderTermCurrencyService currencyService;

	@Autowired
    PaymentScheduleViewService paymentScheduleViewService;

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

                    Date      tRasDate    = null;
                    Date      Srok        = null;
                    long      iPersonID   = 0;
                    long      iCreditID   = 0;
                    String    CreditID[]  = null;
                    Short     iCreditType = 0;

                    Set<Short> creditLines = new HashSet<>();

                    double    Rate=1;
                    int       Thousands = 1000;
                    String    sOrderNumber="";
                    String    sCreditDate="";
                    String    sCreditNumber="";
                    String    sPersonDetails = "";

                    String 	  sNach1 = "";
                    String 	  sNach2 = "";

                    String Curator1="";
                    String Curator2="";

                    short     iResDepartment = 0;

                    double SumCost=0;
                    double SumProfit=0;
                    double SumPayments=0;
                    double SumOstAll=0;
                    double SumOstMain=0;
                    double SumOstPercent=0;
                    double SumOstPenalty=0;
                    double SumProsAll=0;

                    double Cost             = 0;
                    double Profit           = 0;
                    double Payments         = 0;
                    double PaymentsSom      = 0;
                    double OstAll           = 0;
                    double OstMain          = 0;
                    double OstPercent       = 0;
                    double OstPenalty       = 0;
                    double ProsAll          = 0;
                    double ProsMain         = 0;
                    double ProsPercent      = 0;
                    double ProsPenalty      = 0;

                    boolean isBankrot=false;


                    double SumSpisano=0;


                    ReportTool reportTool = new ReportTool();
                    reportTool.initReference();

                    LoanSummary loanSummary = loanSummaryService.getById(objectId);
                    Loan loan = loanSummary.getLoan();


                    iPersonID = loan.getDebtor().getId();

                    Debtor debtor =  debtorService.getById(iPersonID);

                    List<LoanSummaryView> loanSummaryViews = new ArrayList<>();


                    LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<String,List<String>>();

                    List<String> Ids = new ArrayList<>();

                    Ids.add(String.valueOf(iPersonID));

//                    List<String> dates = new ArrayList<>();
//
////                    long    iOneDay        = 1 * 24 * 60 * 60 * 1000;
//
//                    dates.add(String.valueOf(loanSummary.getOnDate().getTime()));
//                    dates.add(String.valueOf(loanSummary.getOnDate().getTime()));

                    List<String> dates = new ArrayList<>();

                    dates.add(String.valueOf(new SimpleDateFormat("dd.MM.yyyy").format(loanSummary.getOnDate())));

                    parameterS.put("r=inv_debtor_id",Ids);
                    parameterS.put("r=odv_ls_on_date",dates);

                    loanSummaryViews.addAll(this.loanSummaryViewService.findByParameter(parameterS));

                    List<String> loanIds = new ArrayList<>();

                    for (LoanSummaryView loanSummaryView:loanSummaryViews)
                    {
                           loanIds.add(reportTool.FormatNumber(loanSummaryView.getV_loan_id()));
                    }



                    tRasDate  = loanSummary.getOnDate();

                    //*********** Document ********************************************************************************
                    //*******************************************************************************************

                    PdfPTable table = null;
                    PdfPCell  cell  = null;

                    float TitleColumnPadding  = 3;
                    int   TitleColumnBorder   = 0;
                    float FooterColumnPadding = 3;
                    int   FooterColumnBorder  = 0;

                    BaseFont myfont        = BaseFont.createFont("//ArialCyr.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

                    Font      HeaderFont   = new  Font(myfont,8,Font.NORMAL,CMYKColor.BLACK);
                    Font      ColumnFont   = new  Font(myfont,8,Font.NORMAL,CMYKColor.BLACK);
                    Font      FooterFont   = new  Font(myfont,8,Font.NORMAL,CMYKColor.BLACK);
                    Font      TitleFont    = new  Font(myfont,12,Font.BOLD,CMYKColor.BLACK);
                    Font      SubTitleFont = new  Font(myfont,9,Font.BOLD,CMYKColor.BLACK);

                    CMYKColor HeaderColor = new CMYKColor(2,0,0,22);

                    CMYKColor ColumnColor = new CMYKColor (0,0,0,0);
                    CMYKColor ColumnColor2 = new CMYKColor (0,0,0,0);
                    CMYKColor FooterColor = new CMYKColor (2,0,0,22);

                    document.open();



                    if(loanSummaryViews.size()>0)
                    {
                        sPersonDetails = "details";
                        iResDepartment = 1;

                        LoanSummaryView lv = loanSummaryViews.get(0);
                        //***********Title********************************************************************************
                        //*******************************************************************************************
                        table=new PdfPTable(1);
                        table.setWidthPercentage(100);

                        cell = new PdfPCell (new Paragraph ("",TitleFont));
                        cell.setFixedHeight(40);
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("Акт сверки задолженности",TitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        String regionName = "";
                        String districtName = "";

                        regionName = reportTool.getNameByMapName("region", lv.getV_debtor_region_id());
                        districtName = reportTool.getNameByMapName("district",lv.getV_debtor_district_id());

                        cell = new PdfPCell (new Paragraph (lv.getV_debtor_name() +", "+ districtName.replace("ий","ого района")+", "+regionName.replace("ая","ой области ") +" перед Государственным агентством по управлению ",TitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph (" бюджетными кредитами при Министерстве финансов Кыргызской Республики, по состоянию на "+ reportTool.DateToString(tRasDate) +" г.",TitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("",TitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPadding(TitleColumnPadding);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        document.add(table);















                        //*******************************************************************************************
                        //*******************************************************************************************
                        table=new PdfPTable(12);
                        table.setWidthPercentage(100);

                        int iWidth [] = new int[12];

                        for(int w=0;w<12;w++)
                            iWidth[w] = 8;

                        iWidth[0] = 3;
                        iWidth[1] = 25;

                        table.setWidths(iWidth);


                        cell = new PdfPCell (new Paragraph ("№",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        cell.setRowspan(2);
                        table.addCell (cell);


                        cell = new PdfPCell (new Paragraph ("Вид кредита",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        cell.setRowspan(2);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("Линия кредита",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        cell.setRowspan(2);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("Основная сумма по договору",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        cell.setRowspan(2);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("Фактически получено",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        cell.setRowspan(2);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("Срок возврата",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        cell.setRowspan(2);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("Фактически возвращено",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        cell.setRowspan(2);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("Остаток задолженности по",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        cell.setColspan(4);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("Просроченная задолженность",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        cell.setRowspan(2);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("Всего",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("основной сумме",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("процентам",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("штрафам",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (HeaderColor);
                        table.addCell (cell);


                        User curator = new User();

                        Staff curatorStaff = new Staff();

int x = 0;
                        for (LoanSummaryView lsv :loanSummaryViews)
                        {
                            x++;


                            iCreditType = lsv.getV_loan_type_id().shortValue();

                            creditLines.add(iCreditType);

                            String sCreditLine ="";

                            switch (iCreditType)
                            {
                                case 1: sCreditLine="Бюджетные ссуда"; sNach1="bs";     break;
                                case 2: sCreditLine="Иностранные кредиты"; sNach1="bs";  break;
                                case 3: sCreditLine="Гр. Японии";   sNach2="grant";        break;
                                case 4: sCreditLine="Гр. Швейцарии";         break;
                                case 5: sCreditLine="Бюджетные ссуда";sNach1="bs";      break;
                                case 6: sCreditLine="Гр. КНР";    sNach2="grant";          break;
                                case 7: sCreditLine="Гр. Японии"; sNach2="grant";          break;
                                case 8: sCreditLine="Гр. КНР";    sNach2="grant";          break;
                                case 9: sCreditLine="ГМР";        sNach1="bs";          break;

                            }

                            sCreditNumber=lsv.getV_loan_reg_number();
                            sCreditDate=lsv.getV_loan_reg_date().toString();
                            sOrderNumber=lsv.getV_loan_reg_number();
                            Short iCurrency= Short.valueOf(lsv.getV_loan_currency_id().toString());



                            Rate=this.currencyRateService.findByDateAndType(tRasDate,this.currencyService.getById(lsv.getV_loan_currency_id())).getRate();

                            Thousands = 1000;





//                            Loan currentLoan = loanService.getById(lsv.getV_loan_id());
//
//                            for (PaymentSchedule ps: loan.getPaymentSchedules())
//                            {
//                                Srok = ps.getExpectedDate();
//                            }
//
                            Srok = lv.getV_last_date();

                            if(loan.getSupervisorId()>0) curator = userService.findById(loan.getSupervisorId());

                            if(curator.getStaff()!=null)
                            curatorStaff = staffService.findById(curator.getStaff().getId());


                            Cost            = lsv.getV_loan_amount();
                            Profit          = lsv.getV_ls_total_disbursed();
                            Payments        = lsv.getV_ls_total_paid();
                            PaymentsSom     = lsv.getV_ls_total_paid_kgs();

                            OstMain         = lsv.getV_ls_outstading_principal();
                            OstPercent      = lsv.getV_ls_outstading_interest();
                            OstPenalty      = lsv.getV_ls_outstading_penalty();
                            OstAll          = OstMain+OstPercent+OstPenalty;


                            ProsMain        = lsv.getV_ls_overdue_principal();
                            ProsPercent     = lsv.getV_ls_overdue_interest();
                            ProsPenalty     = lsv.getV_ls_overdue_penalty();

                            ProsAll         = ProsMain+ProsPercent+ProsPenalty;

                            if(Cost<0) Cost=0;
                            else
                            {
                                SumCost+=Cost;
                            }
                            if(OstAll<0) OstAll=0;
                            else
                            {
                                SumOstAll+=OstAll;
                            }
                            if(OstMain<0) OstMain=0;
                            else
                            {
                                SumOstMain+=OstMain;
                            }
                            if(OstPercent<0) OstPercent=0;
                            else
                            {
                                SumOstPercent+=OstPercent;
                            }
                            if(OstPenalty<0) OstPenalty=0;
                            else
                            {
                                SumOstPenalty+=OstPenalty;
                            }
                            if(ProsAll<0) ProsAll=0;
                            else
                            {
                                SumProsAll+=ProsAll;
                            }
                            SumProfit+=Profit;
                            SumPayments+=PaymentsSom;



                            cell = new PdfPCell (new Paragraph (Integer.toString(x),HeaderFont));
                            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBackgroundColor (ColumnColor);
                            table.addCell (cell);

                            String Det1=sOrderNumber +
                                    " №" + sCreditNumber +
                                    " от " + (sCreditDate) +"г.";

                            if(iCurrency!=17)
                            {
                                Det1+=" в тыс. "+this.currencyService.getById(lsv.getV_loan_currency_id()).getName();
                            }
                            else
                            {
                                Det1+= " в тоннах ";
                            }

                            cell = new PdfPCell (new Paragraph (Det1,HeaderFont));
                            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBackgroundColor (ColumnColor);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph (sCreditLine,ColumnFont));
                            cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBackgroundColor (ColumnColor);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(Cost/(Thousands)),ColumnFont));
                            cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBackgroundColor (ColumnColor);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(Profit/(Thousands)),ColumnFont));
                            cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBackgroundColor (ColumnColor);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph (reportTool.DateToString(Srok),ColumnFont));
                            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBackgroundColor (ColumnColor);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(Payments/(Thousands)),ColumnFont));
                            cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBackgroundColor (ColumnColor);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(OstAll/(Thousands)),ColumnFont));
                            cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBackgroundColor (ColumnColor);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(OstMain/(Thousands)),ColumnFont));
                            cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBackgroundColor (ColumnColor);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(OstPercent/(Thousands)),ColumnFont));
                            cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBackgroundColor (ColumnColor);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(OstPenalty/(Thousands)),ColumnFont));
                            cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBackgroundColor (ColumnColor);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(ProsAll/(Thousands)),ColumnFont));
                            cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setBackgroundColor (ColumnColor);
                            table.addCell (cell);

                            if(iCurrency!=1)
                            {
                                Thousands=1000;

                                cell = new PdfPCell (new Paragraph ("",HeaderFont));
                                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                                cell.setBackgroundColor (ColumnColor2);
                                table.addCell (cell);

                                String Det2="";

                                if(iCurrency!=17)
                                {
                                    Det2="в тыс. сомах по курсу "+reportTool.FormatNumber(Rate);
                                }
                                else
                                {
                                    Det2="в тыс. сомах по цене "+reportTool.FormatNumber(Rate)+" за тонну";
                                }

                                cell = new PdfPCell (new Paragraph (Det2,HeaderFont));
                                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                                cell.setBackgroundColor (ColumnColor2);
                                table.addCell (cell);

                                cell = new PdfPCell (new Paragraph ("",HeaderFont));
                                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                                cell.setBackgroundColor (ColumnColor2);
                                table.addCell (cell);


                                cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(Cost*Rate/(Thousands)),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                                cell.setBackgroundColor (ColumnColor2);
                                table.addCell (cell);

                                cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(Profit*Rate/(Thousands)),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                                cell.setBackgroundColor (ColumnColor2);
                                table.addCell (cell);

                                cell = new PdfPCell (new Paragraph ("",ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                                cell.setBackgroundColor (ColumnColor2);
                                table.addCell (cell);

                                cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(PaymentsSom/(Thousands)),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                                cell.setBackgroundColor (ColumnColor2);
                                table.addCell (cell);

                                cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(OstAll*Rate/(Thousands)),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                                cell.setBackgroundColor (ColumnColor2);
                                table.addCell (cell);

                                cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(OstMain*Rate/(Thousands)),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                                cell.setBackgroundColor (ColumnColor2);
                                table.addCell (cell);

                                cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(OstPercent*Rate/(Thousands)),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                                cell.setBackgroundColor (ColumnColor2);
                                table.addCell (cell);

                                cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(OstPenalty*Rate/(Thousands)),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                                cell.setBackgroundColor (ColumnColor2);
                                table.addCell (cell);

                                cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(ProsAll*Rate/(Thousands)),ColumnFont));
                                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                                cell.setBackgroundColor (ColumnColor2);
                                table.addCell (cell);

                            }
                        }

                        cell = new PdfPCell (new Paragraph ("",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);


                        cell = new PdfPCell (new Paragraph (" ИТОГО (тыс.сом)",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("",HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);


                        cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(SumCost*Rate/(Thousands)),FooterFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(SumProfit*Rate/(Thousands)),FooterFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("",ColumnFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(SumPayments*Rate/(Thousands)),FooterFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(SumOstAll*Rate/(Thousands)),FooterFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(SumOstMain*Rate/(Thousands)),FooterFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(SumOstPercent*Rate/(Thousands)),FooterFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(SumOstPenalty*Rate/(Thousands)),FooterFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph (reportTool.FormatNumber(SumProsAll*Rate/(Thousands)),FooterFont));
                        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor (FooterColor);
                        table.addCell (cell);

                        document.add(table);


                        table=new PdfPTable(5);
                        table.setWidthPercentage(100);
                        int iWidthDet [] = new int[5];
                        iWidthDet[0] = 25;
                        iWidthDet[1] = 15;
                        iWidthDet[2] = 15;
                        iWidthDet[3] = 30;
                        iWidthDet[4] = 15;

                        table.setWidths(iWidthDet);

                        String sResTitle = "";
                        String sResName = "";

                        String sResTitle2 = "";
                        String sResName2 = "";

                        short upravlenie = 1;
                        short otdel = 1;


                        boolean debtorResponsibleDrawed= false;



                        if(debtor.getWorkSector().getId()==1 || debtor.getWorkSector().getId()==12)
                        {
                            upravlenie =1;
                            if(iCreditType==5) upravlenie=3;

                            sResTitle = "Директор:";
                            sResName  = "Ж. Торомаматов";

                            sResTitle2 = "Заместитель директора:";
                            sResName2  = "Д. Наспеков";
                        }
                        else
                        {
                            upravlenie = 2;
                            if(iCreditType==5) upravlenie=3;

                            if(OstAll>1)
                            {
                                sResTitle = "Заместитель директора:";
                                sResName  = "Дж. Сагынов";

                                debtorResponsibleDrawed = true;
                            }
                            else
                            {
                                sResTitle = "Директор:";
                                sResName  = "Ж. Торомаматов";
                            }

                            sResTitle2 = "Заместитель директора:";
                            sResName2  = "Дж. Сагынов";

                        }





                        // Директор             ФИО руководителя

                        String sResponsible="ФИО руководителя:";
                        if(isBankrot)
                        {
                            sResponsible="Спец. администратор";
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


                            cell = new PdfPCell (new Paragraph (sResTitle,TitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setFixedHeight(36);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph ("",TitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph (sResName,TitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);


                            cell = new PdfPCell (new Paragraph (sResponsible,TitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);



                        if(debtorResponsible!=null)
                        {
                            cell = new PdfPCell (new Paragraph (debtorResponsible.getName(),TitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);
                        }
                        else
                        {
                            cell = new PdfPCell (new Paragraph (debtor.getName(),TitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                        }





                        // Зам. директора                   Гдавный бухгалтер


                        if(!debtorResponsibleDrawed)
                        {
                            cell = new PdfPCell (new Paragraph (sResTitle2,TitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setFixedHeight(36);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph ("",TitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph (sResName2,TitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);
                        }
                        else
                        {
                            cell = new PdfPCell (new Paragraph ("Заведующий отделом бухгалтерского учета (в части погашения денежными средствами):",TitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setFixedHeight(51);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph ("",TitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);


                            cell = new PdfPCell (new Paragraph ("Г. Болжурова",TitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                        }


                        cell = new PdfPCell (new Paragraph ("Главный бухгалтер:",TitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(FooterColumnBorder);
                        cell.setPadding(FooterColumnPadding);
                        table.addCell (cell);

                        if(debtorAccountant!=null)
                        {
                            cell = new PdfPCell (new Paragraph (debtorAccountant.getName(),TitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);
                        }
                        else
                        {
                            cell = new PdfPCell (new Paragraph ("",TitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                        }


                        if(!debtorResponsibleDrawed)
                        {
                            cell = new PdfPCell (new Paragraph ("Заведующий отделом бухгалтерского учета (в части погашения денежными средствами):",TitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setFixedHeight(51);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph ("",TitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);


                            cell = new PdfPCell (new Paragraph ("Г. Болжурова",TitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                            cell = new PdfPCell (new Paragraph ("",TitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setBorder(FooterColumnBorder);
                            cell.setPadding(FooterColumnPadding);
                            table.addCell (cell);

                                cell = new PdfPCell (new Paragraph ("",TitleFont));
                                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cell.setBorder(FooterColumnBorder);
                                cell.setPadding(FooterColumnPadding);
                                table.addCell (cell);


                        }

                        // Нач. управ. ПРОМ



                        if(debtor.getWorkSector().getId()==1 || debtor.getWorkSector().getId()==12)
                        {
                            cell = new PdfPCell (new Paragraph ("Начальник управления кредитов АПК:",TitleFont));
                        }
                        else
                        {
                            if(iCreditType==5)
                            {
                                cell = new PdfPCell (new Paragraph ("Начальник управления обеспечения кредитов:",TitleFont));
                            }
                            else
                            {
                                cell = new PdfPCell (new Paragraph ("Начальник управления кредитов промышленности и предпринимательства:",TitleFont));
                            }

                        }



                        cell.setFixedHeight(36);
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(FooterColumnBorder);
                        cell.setPadding(FooterColumnPadding);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("",TitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(FooterColumnBorder);
                        cell.setPadding(FooterColumnPadding);
                        table.addCell (cell);


                        if(debtor.getWorkSector().getId()==1 || debtor.getWorkSector().getId()==12)
                        {
                            cell = new PdfPCell (new Paragraph ("Б. Туркбаев",TitleFont));
                        }
                        else
                        {
                            if(iCreditType==5)
                            {
                                cell = new PdfPCell (new Paragraph ("А. Акымбеков",TitleFont));
                            }
                            else
                            {
                                cell = new PdfPCell (new Paragraph ("Э. Доолбеков",TitleFont));
                            }

                        }


                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(FooterColumnBorder);
                        cell.setPadding(FooterColumnPadding);
                        cell.setColspan(3);
                        table.addCell (cell);




                       if(OstAll<=1)
                       {
                           cell.setFixedHeight(36);
                           cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                           cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cell.setBorder(FooterColumnBorder);
                           cell.setPadding(FooterColumnPadding);
                           table.addCell (cell);

                           cell = new PdfPCell (new Paragraph ("",TitleFont));
                           cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                           cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cell.setBorder(FooterColumnBorder);
                           cell.setPadding(FooterColumnPadding);
                           table.addCell (cell);


                           cell = new PdfPCell (new Paragraph ("М. Данилович",TitleFont));
                           cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                           cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                           cell.setBorder(FooterColumnBorder);
                           cell.setPadding(FooterColumnPadding);
                           cell.setColspan(3);
                           table.addCell (cell);
                       }



                        // Зав. отделом

                        String sNach ="";
                        String sNachTitle="";



                        if(debtor.getWorkSector().getId()==1)
                        {
                            if(iCreditType==1||iCreditType==2||iCreditType==5||iCreditType==9)
                            {
                                sNach="Заведующий отделом кредитов АПК:";
                                sNachTitle="К. Алыбаев";
                            }
                            else
                                {
                                    sNach="Заведующий отделом грантов АПК:";
                                    sNachTitle="А. Сыдыков";
                                }
                        }
                        else
                            {
                                sNach="Заведующий отделом кредитов промышленности:";
                                sNachTitle="Ж. Жапаркулова";
                            }


                        if(debtor.getWorkSector().getId()==1 || debtor.getWorkSector().getId()==12)
                        {
                            if(iCreditType==1||iCreditType==2||iCreditType==9)
                            {
                                sNach="Заведующий отделом кредитов АПК:";
                                sNachTitle="К. Алыбаев";
                            }
                            else
                            {
                                sNach="Заведующий отделом грантов АПК:";
                                sNachTitle="А. Сыдыков";
                            }
                        }
                        else
                        {
                            if(debtor.getWorkSector().getId()==13 || debtor.getWorkSector().getId()==14)
                            {
                                sNach="Заведующий отделом кредитов частного предпринимательства и ф.л.:";
                                sNachTitle="К. Асеков";
                            }
                            else
                            {
                                sNach="Заведующий отделом кредитов промышленности:";
                                sNachTitle="Ж. Жапаркулова";
                            }

                        }

                        if(iCreditType==5)
                        {
                            sNach="Заведующий отделом отчуждения кредитов:";
                            sNachTitle="И. Джумабаев";
                        }


                        cell = new PdfPCell (new Paragraph (sNach,TitleFont));
                        cell.setFixedHeight(36);
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(FooterColumnBorder);
                        cell.setPadding(FooterColumnPadding);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("",TitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(FooterColumnBorder);
                        cell.setPadding(FooterColumnPadding);
                        table.addCell (cell);


                        cell = new PdfPCell (new Paragraph (sNachTitle,TitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(FooterColumnBorder);
                        cell.setPadding(FooterColumnPadding);
                        cell.setColspan(3);
                        table.addCell (cell);




                        // Куратор

                        cell = new PdfPCell (new Paragraph ("Куратор:",TitleFont));
                        cell.setFixedHeight(36);
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(FooterColumnBorder);
                        cell.setPadding(FooterColumnPadding);
                        table.addCell (cell);

                        cell = new PdfPCell (new Paragraph ("",TitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(FooterColumnBorder);
                        cell.setPadding(FooterColumnPadding);
                        table.addCell (cell);

                        if(curatorStaff.getId()>0)
                        {
                            cell = new PdfPCell (new Paragraph (curatorStaff.getPerson().getIdentityDoc().getIdentityDocDetails().getFirstname().substring(0,1)+" "+curatorStaff.getPerson().getIdentityDoc().getIdentityDocDetails().getLastname(),TitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setPadding(FooterColumnPadding);
                            cell.setColspan(3);
                            cell.setBorder(FooterColumnBorder);
                            table.addCell (cell);
                            table.setKeepTogether(true);
                        }
                        else
                        {
                            cell = new PdfPCell (new Paragraph ("",TitleFont));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setPadding(FooterColumnPadding);
                            cell.setColspan(3);
                            cell.setBorder(FooterColumnBorder);
                            table.addCell (cell);
                            table.setKeepTogether(true);

                        }




                        document.add(table);

                    }





                    document.close();

                }
                catch(Exception Ex)
                {
                   System.out.println(Ex);
                }


    }


}
