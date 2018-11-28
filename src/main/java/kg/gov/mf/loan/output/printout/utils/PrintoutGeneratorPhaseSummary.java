package kg.gov.mf.loan.output.printout.utils;


import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.rtf.table.RtfBorder;
import com.lowagie.text.rtf.table.RtfBorderGroup;
import com.lowagie.text.rtf.table.RtfCell;
import kg.gov.mf.loan.admin.org.model.*;
import kg.gov.mf.loan.admin.org.service.DepartmentService;
import kg.gov.mf.loan.admin.org.service.OrganizationService;
import kg.gov.mf.loan.admin.org.service.PersonService;
import kg.gov.mf.loan.admin.org.service.StaffService;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.collection.CollectionPhase;
import kg.gov.mf.loan.manage.model.collection.PhaseDetails;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.loan.PaymentSchedule;
import kg.gov.mf.loan.manage.model.process.LoanSummary;
import kg.gov.mf.loan.manage.service.collection.CollectionPhaseService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.orderterm.CurrencyRateService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermCurrencyService;
import kg.gov.mf.loan.manage.service.process.LoanSummaryService;
import kg.gov.mf.loan.output.printout.model.PrintoutTemplate;
import kg.gov.mf.loan.output.report.model.CollectionPhaseView;
import kg.gov.mf.loan.output.report.model.LoanSummaryView;
import kg.gov.mf.loan.output.report.model.LoanView;
import kg.gov.mf.loan.output.report.service.*;
import kg.gov.mf.loan.output.report.utils.ReportTool;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;


public class PrintoutGeneratorPhaseSummary {
	
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

	@Autowired
    CollectionPhaseViewService collectionPhaseViewService;

	@Autowired
    CollectionPhaseService collectionPhaseService;

	@Autowired
    LoanViewService loanViewService;

    public void generatePrintoutByTemplate(PrintoutTemplate printoutTemplate, Date onDate, long objectId, Document document){




                try
                {
                    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

                    String UPLOADED_FOLDER =  SystemUtils.IS_OS_LINUX ? "/opt/uploads/" : "c://temp//";

                    String sTitle1   = "КЫРГЫЗ РЕСПУБЛИКАСЫНЫН ФИНАНСЫ МИНИСТИРЛИГИНЕ КАРАШТУУ БЮДЖЕТТИК НАСЫЯЛАРДЫ БАШКАРУУ БОЮНЧА МАМЛЕКЕТТИК АГЕНТТИГИ \n \n";
                    String sTitle2   = "ГОСУДАРСТВЕННОЕ АГЕНТСТВО ПО УПРАВЛЕНИЮ БЮДЖЕТНЫМИ КРЕДИТАМИ ПРИ МИНИСТЕРСТВЕ ФИНАНСОВ КЫРГЫЗСКОЙ РЕСПУБЛИКИ";
                    String sTitle3   = "STATE AGENCY FOR MANAGEMENT OF BUDGET LOANS UNDER\n THE MINISTRY OF FINANCE OF THE KYRGYZ REPUBLIC";

                    String sAddress1 = "720040, Бишкек ш. Эркиндик бул. 58а\nтел:66-43-34,62-07-44\nфакс:66-40-33\nwww:minfin.kg\nE-mail: gosagentstvo@minfin.kg";
                    String sAddress2 = "58а Boulevard Erkindik Bishkek 720040\ntel: +996 312 66-43-34, 62-07-44\nfax:+996 312 66-40-33\nwww:minfin.kg\nE-mail: gosagentstvo@minfin.kg";
                    String sAddress3 = "720040, г. Бишкек бул. Эркиндик 58а\nтел:66-43-34,62-07-44\nфакс:66-40-33\nwww:minfin.kg\nE-mail: gosagentstvo@minfin.kg";
                    String sNumber   = "№ _______________________________\n\nот \"____\" _________________\" 20____ г.";

                    String    sRasDate      = "";
                    String    sPersonTitle  = "";
                    String    sCuratorPhone = "";
                    String    sCurator      = "";
                    String    sPaymentRequsites = "";

                    short     iCreditType = 0;

                    String    sNachOtdela = "";

                    long      iStateID       = 0;
                    long      iCreditID      = 0;
                    long      iRequsiteID    = 0;
                    short     iAokmotu      = 0;
                    short     iDistrict      = 0;
                    short     iRegion        = 0;
                    short     iResDepartment = 0;
                    int       Thousands      = 1000;

                    String    sOrderNumber      = "";
                    String    sCreditDate       = "";
                    String    sCreditNumber     = "";
                    String    sAdres            = "";
                    String    sAokmotu          = "";

                    double    SumProsAll           = 0;

                    iStateID = 1;

                    sRasDate = "datexxx";

                    Staff curatorStaff = null;


                    Table table = null;
                    RtfCell cell  = null;

                    int   TitleColumnBorder   = 0;


                    BaseFont   myfont        = BaseFont.createFont("//ArialCyr.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

                    Font       HeaderFont    = new  Font(myfont,11,Font.NORMAL);
                    Font       ColumnFont    = new  Font(myfont,12,Font.NORMAL);
                    Font       TitleFont     = new  Font(myfont,12,Font.BOLD);
                    Font       TitleFont2     = new  Font(myfont,10,Font.BOLD);
                    Font       PerformerFont = new  Font(myfont,8,Font.NORMAL);

                    RtfBorderGroup TbBorder  = new RtfBorderGroup(Rectangle.BOX, RtfBorder.BORDER_SINGLE, 1, new Color(75,75,75));


                    Font      AddressFont = new  com.lowagie.text.Font(myfont,7,Font.NORMAL);
                    Font      DateFont    = new  com.lowagie.text.Font(myfont,8,Font.NORMAL);
                    RtfBorderGroup             TbBorderHeader    = new RtfBorderGroup(Rectangle.TOP, RtfBorder.BORDER_SINGLE, 3, new Color(0, 0, 0));


                    document.open();

                    // header
                    table=new Table(3);
                    table.setWidth(100);

                    int iWidthHeader[] = new int[3];
                    iWidthHeader[0] = 42;
                    iWidthHeader[1] = 16;
                    iWidthHeader[2] = 42;
                    table.setWidths(iWidthHeader);

                    cell = new RtfCell (new com.lowagie.text.Paragraph (sTitle1,TitleFont));
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);
                    cell.setBorder(TitleColumnBorder);
                    table.addCell (cell);

                    String    sFileName   = UPLOADED_FOLDER+"/gaubk.png";
                    Image image1 = Image.getInstance(sFileName);

                    image1.scalePercent(70);
                    cell = new RtfCell (image1);
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);
                    cell.setBorder(TitleColumnBorder);
                    table.addCell (cell);

                    cell = new RtfCell (new com.lowagie.text.Paragraph (sTitle2,TitleFont));
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);
                    cell.setBorder(TitleColumnBorder);
                    table.addCell (cell);

//                    cell = new RtfCell (new com.lowagie.text.Paragraph (sTitle3,TitleFont));
                    cell = new RtfCell (new com.lowagie.text.Paragraph ("",TitleFont));
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBorder(TitleColumnBorder);
                    cell.setColspan(3);
                    table.addCell (cell);

                    cell = new RtfCell (new com.lowagie.text.Paragraph ("",TitleFont));
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBorders(TbBorderHeader);
                    cell.setColspan(3);
                    table.addCell (cell);

                    cell = new RtfCell (new com.lowagie.text.Paragraph (sAddress1,AddressFont));
                    cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);
                    cell.setBorder(TitleColumnBorder);
                    table.addCell (cell);

//                    cell = new RtfCell (new com.lowagie.text.Paragraph (sAddress2,AddressFont));
                    cell = new RtfCell (new com.lowagie.text.Paragraph ("",AddressFont));
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);
                    cell.setBorder(TitleColumnBorder);
                    table.addCell (cell);

                    cell = new RtfCell (new com.lowagie.text.Paragraph (sAddress3,AddressFont));
                    cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);
                    cell.setBorder(TitleColumnBorder);
                    table.addCell (cell);

                    cell = new RtfCell (new com.lowagie.text.Paragraph (sNumber,DateFont));
                    cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);
                    cell.setBorder(TitleColumnBorder);
                    cell.setColspan(3);
                    table.addCell (cell);

                    //document.setHeader(new RtfHeaderFooter(table));
                    document.add(table);

// part1
                    LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<String,List<String>>();

                    List<String> Ids = new ArrayList<>();


                    Ids.add(String.valueOf(objectId));

                    parameterS.put("r=inv_cph_id",Ids);

                    CollectionPhaseView collectionPhaseView = this.collectionPhaseViewService.findByParameter(parameterS).get(0);

                    SumProsAll     = collectionPhaseView.getV_cph_start_total_amount();

                    sPersonTitle   =  collectionPhaseView.getV_debtor_name();


                    try
                    {
                        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                        String currentUserName = authentication.getName();

                        User currentUser = this.userService.findByUsername(currentUserName);

                        Staff staff = currentUser.getStaff();

                        Person person = staff.getPerson();

                        sCuratorPhone = person.getContact().getName();

                        sCurator = staff.getPerson().getIdentityDoc().getIdentityDocDetails().getFirstname().substring(0,1)+" "+staff.getPerson().getIdentityDoc().getIdentityDocDetails().getLastname();


                    }
                    catch(Exception ex)
                    {
                        sCuratorPhone  =  "";
                        sCurator       =  "_________________________";
                    }




                    iDistrict      =  collectionPhaseView.getV_debtor_region_id().shortValue();

                    iRegion        =  collectionPhaseView.getV_debtor_district_id().shortValue();

                    ReportTool reportTool = new ReportTool();
                    reportTool.initReference();

                    String RegionName = reportTool.getNameByMapName("region",collectionPhaseView.getV_debtor_region_id());


                    String DistrictName = reportTool.getNameByMapName("district",collectionPhaseView.getV_debtor_district_id());

                    Address address = new Address();
                    sRasDate = reportTool.DateToString(collectionPhaseView.getV_cph_startDate());


                    sAdres         =  "";

                    if(collectionPhaseView.getV_debtor_owner_type().contains("PERSON"))
                    {
                        address = this.personService.findById(collectionPhaseView.getV_debtor_owner_id()).getAddress();
                        sAdres         =  address.getLine();
                        sAokmotu = address.getAokmotu().getName();
                    }
                    else
                    {
                        address = this.organizationService.findById(collectionPhaseView.getV_debtor_owner_id()).getAddress();
                        sAdres         =  address.getLine();
                        sAokmotu = address.getAokmotu().getName();
                    }


                    table=new Table(2);
                    table.setWidth(100);

                    int iWidth[] = new int[2];
                    iWidth[0] = 70;
                    iWidth[1] = 30;

                    table.setWidths(iWidth);

                    cell = new RtfCell (new Paragraph ("",TitleFont));
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBorder(TitleColumnBorder);
                    table.addCell (cell);


                    String sRecipient="";

                    sRecipient+=RegionName.replace("ая","ая область,")+"\n";
                    sRecipient+=DistrictName.replace("ий","ий район, ")+"\n";

                    if (sAdres.length()>3)
                    {
                        sRecipient+=sAdres+"\n";
                    }

                    sRecipient+=sPersonTitle;

                    cell = new RtfCell (new Paragraph (sRecipient,TitleFont));
                    cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBorder(TitleColumnBorder);
                    table.addCell (cell);

                    cell = new RtfCell (new Paragraph ("Претензия",TitleFont));
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBorder(TitleColumnBorder);
                    cell.setColspan(2);
                    table.addCell (cell);

                    cell = new RtfCell (new Paragraph ("",TitleFont));
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBorder(TitleColumnBorder);
                    table.addCell (cell);

                    cell = new RtfCell (new Paragraph ("На сумму "+reportTool.FormatNumber(SumProsAll)+" с.",TitleFont));
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    cell.setBorder(TitleColumnBorder);
                    table.addCell (cell);


                    String sText1="";

                    sText1+="     Согласно данным Государственного агентства по управлению бюджетными кредитами при Министерстве финансов Кыргызской Республики по состоянию на  "+sRasDate+"г. за Вами имеется просроченная задолженность  в сумме "+reportTool.FormatNumber(SumProsAll)+" сомов";


                    sText1+=" по ниже указанным кредитным договорам:";

                    cell = new RtfCell (new Paragraph (sText1,ColumnFont));
                    cell.setHorizontalAlignment (Element.ALIGN_JUSTIFIED);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);
                    cell.setBorder(TitleColumnBorder);
                    cell.setColspan(2);
                    table.addCell (cell);


                    document.add(table);


//table header
                    table=new Table(4);
                    table.setWidth(100);

                    int iWidth2[] = new int[4];
                    iWidth2[0] = 5;
                    iWidth2[1] = 40;
                    iWidth2[2] = 15;
                    iWidth2[3] = 40;

                    table.setWidths(iWidth2);

                    cell = new RtfCell (new Paragraph ("№",TitleFont2));
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBorders(TbBorder);
                    table.addCell (cell);

                    cell = new RtfCell (new Paragraph (" Вид кредита",TitleFont2));
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBorders(TbBorder);
                    table.addCell (cell);

                    cell = new RtfCell (new Paragraph (" Задолженность \n(тыс.сом)",TitleFont2));
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBorders(TbBorder);
                    table.addCell (cell);

                    cell = new RtfCell (new Paragraph (" Реквизиты \nдля оплаты задолженности",TitleFont2));
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBorders(TbBorder);
                    table.addCell (cell);

                    int x=0;

                    CollectionPhase collectionPhase = collectionPhaseService.getById(objectId);

                    for (PhaseDetails phaseDetails:collectionPhase.getPhaseDetails())
                    {
                        double iDebtAll = 0;
                        x++;
                        iCreditID      = phaseDetails.getLoan_id();

                        iRequsiteID    = 1;
                        iResDepartment =  1;

                        LinkedHashMap<String,List<String>> parameterL = new LinkedHashMap<String,List<String>>();

                        List<String> loanIds = new ArrayList<>();


                        loanIds.add(String.valueOf(iCreditID));

                        parameterL.put("r=inv_loan_id",loanIds);

                        LoanView loanView = loanViewService.findByParameter(parameterL).get(0);

                        iCreditType = loanView.getV_loan_type_id().shortValue();

                        if(loanView.getV_debtor_work_sector_id()==1 || loanView.getV_debtor_work_sector_id()==12)
                        {
                            iResDepartment = 1;
                        }
                        else
                        {
                            iResDepartment = 2;
                        }


                        sCreditNumber=loanView.getV_loan_reg_number();

                        sCreditDate=loanView.getV_loan_reg_date().toString();

                        sOrderNumber=loanView.getV_credit_order_reg_number();

                        iDebtAll = phaseDetails.getStartTotalAmount();

                        sPaymentRequsites  = "Центральное казначейство МФ КР"+" ";
                        sPaymentRequsites += "Национальный банк КР"+" ";
                        sPaymentRequsites += "\nБик: 101001"+" ";
                        sPaymentRequsites += "\nРасчетный счет: 1013710000000102";

                        cell = new RtfCell (new Paragraph (Integer.toString(x),HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBorders(TbBorder);
                        table.addCell (cell);

                        String Det1=sOrderNumber +
                                " №" + sCreditNumber +
                                " от " + (sCreditDate);

                        cell = new RtfCell (new Paragraph (Det1,HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBorders(TbBorder);
                        table.addCell (cell);

                        cell = new RtfCell (new Paragraph (reportTool.FormatNumber(iDebtAll/(Thousands)),HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBorders(TbBorder);
                        table.addCell (cell);

                        cell = new RtfCell (new Paragraph (sPaymentRequsites,HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        cell.setBorders(TbBorder);
                        table.addCell (cell);

                    }


                    cell = new RtfCell (new Paragraph ("  ИТОГО:   ",TitleFont2));
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setColspan(2);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cell.setBorders(TbBorder);
                    table.addCell (cell);

                    cell = new RtfCell (new Paragraph (reportTool.FormatNumber(SumProsAll/(Thousands)),TitleFont2));
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBorders(TbBorder);
                    table.addCell (cell);

                    cell = new RtfCell (new Paragraph ("",HeaderFont));
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cell.setBorders(TbBorder);
                    table.addCell (cell);
                    document.add(table);


                    table=new Table(4);
                    table.setWidth(100);

                    int iWidth3[] = new int[4];
                    iWidth3[0] = 10;
                    iWidth3[1] = 40;
                    iWidth3[2] = 20;
                    iWidth3[3] = 20;

                    table.setWidths(iWidth3);

                    cell = new RtfCell (new Paragraph ("       В соответствии со статьей 299 Гражданского кодекса Кыргызской Республики Вы обязаны возвратить Государственному агентству по управлению бюджетными кредитами при Министерстве финансов Кыргызской Республики полученную по кредиту сумму в срок и в порядке предусмотренными условиями договора." +
                            "\n       На основании изложенного, просим погасить вышеуказанную задолженность в полном объеме в течении 10 дней со дня получения настоящей претензии." +
                            "\n       В cлучае не погашения задолженности в указанный срок Государственное агентство по управлению бюджетными кредитами при Министерстве финансов Кыргызской Республики, руководствуясь ст. 4 Гражданского процессуального кодекса Кыргызской Республики, будет вынуждено обратиться с иском в суд для принудительного взыскания задолженности путем обращения взыскания на заложенное имущество."+
                            "\n       Все судебные расходы относятся на Должника.",ColumnFont));

                    cell.setHorizontalAlignment (Element.ALIGN_JUSTIFIED);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);

                    cell.setBorder(TitleColumnBorder);
                    cell.setColspan(4);
                    table.addCell (cell);

                    cell = new RtfCell (new Paragraph ("\nПриложение: Расчет задолженности от "+sRasDate+"г. \n",PerformerFont));
                    cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);

                    cell.setBorder(TitleColumnBorder);
                    cell.setColspan(4);
                    table.addCell (cell);

                    cell = new RtfCell (new Paragraph ("",TitleFont));
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);

                    cell.setBorder(TitleColumnBorder);
                    table.addCell (cell);

                    cell = new RtfCell (new Paragraph ("Заместитель директора",TitleFont));
                    cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);

                    cell.setBorder(TitleColumnBorder);
                    table.addCell (cell);

                    cell = new RtfCell (new Paragraph ("",TitleFont));
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);

                    cell.setBorder(TitleColumnBorder);
                    table.addCell (cell);


                    cell = new RtfCell (new Paragraph ("Дж. Сагынов",TitleFont));

                    cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);
                    cell.setBorder(TitleColumnBorder);
                    table.addCell (cell);



                    if( iResDepartment==2)
                    {
                        cell = new RtfCell (new Paragraph ("",TitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);

                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new RtfCell (new Paragraph ("\nЗаведующий отделом кредитов промышленности",TitleFont2));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);

                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new RtfCell (new Paragraph ("",TitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);

                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);


                        cell = new RtfCell (new Paragraph ("\nЖапаркулова Ж.",TitleFont2));

                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                    }
                    else if (iResDepartment==1 && (iCreditType==3||iCreditType==6||iCreditType==7||iCreditType==8))
                    {
                        cell = new RtfCell (new Paragraph ("",TitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);

                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new RtfCell (new Paragraph ("\nЗаведующий отдела грантов АПК",TitleFont2));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);

                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new RtfCell (new Paragraph ("",TitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);

                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);


                        cell = new RtfCell (new Paragraph ("\nСыдыков А.А.",TitleFont2));

                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);
                    }
                    else
                    {
                        cell = new RtfCell (new Paragraph ("",TitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);

                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new RtfCell (new Paragraph ("\nЗаведующий отдела кредитов АПК",TitleFont2));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);

                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);

                        cell = new RtfCell (new Paragraph ("",TitleFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);

                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);


                        cell = new RtfCell (new Paragraph ("\nАлыбаев К.",TitleFont2));

                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        cell.setBorder(TitleColumnBorder);
                        table.addCell (cell);


                    }


                    if(sCurator!=null)
                    {
                        HeaderFooter footer = new HeaderFooter(new Paragraph("Исп.: "+sCurator+"  тел: "+sCuratorPhone,PerformerFont),false);
                        document.setFooter(footer);
                    }

                    document.add(table);



                    document.close();





                }

                catch(Exception Ex)
                {
                   System.out.println(Ex);
                }


    }


}
