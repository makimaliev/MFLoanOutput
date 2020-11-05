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
import kg.gov.mf.loan.admin.org.service.*;
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

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;


public class PrintoutGeneratorCreditClaim {

    /**
     * Printout GENERATION TOOL
     */


    @Autowired
    EntityManager entityManager;

    @Autowired
    PaymentViewService paymentViewService;

    @Autowired
    LoanSummaryService loanSummaryService;

    @Autowired
    LoanService loanService;

    @Autowired
    AddressService addressService;
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


            BaseFont   myfont        = BaseFont.createFont("//timesNewRoman.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

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
//            LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<String,List<String>>();
//
//            List<String> Ids = new ArrayList<>();
//
//
//            Ids.add(String.valueOf(objectId));
//
//            parameterS.put("r=inv_cph_id",Ids);
//
//            CollectionPhaseView collectionPhaseView = this.collectionPhaseViewService.findById(objectId);
//
//            SumProsAll     = collectionPhaseView.getV_cph_start_total_amount();
//
//            sPersonTitle   =  collectionPhaseView.getV_debtor_name();
//
//            long workSector = 1;
//
//            if(collectionPhaseView.getV_debtor_work_sector_id()>0)
//            {
//                workSector = collectionPhaseView.getV_debtor_work_sector_id();
//            }


            CollectionPhase collectionPhase = collectionPhaseService.getById(objectId);

            Long debtorId = 1L;

            Loan loanTemp = null;

            for (Loan loan :collectionPhase.getLoans())
            {
                loanTemp = loan;
            }

            loanTemp = loanService.getById(loanTemp.getId());

            Debtor debtor = debtorService.getById(loanTemp.getDebtor().getId());

            SumProsAll     = collectionPhase.getStart_amount();

            sPersonTitle   =  debtor.getName();

            long workSector = 1;

            if(debtor.getWorkSector().getId()>0)
            {
                workSector = debtor.getWorkSector().getId();
            }







            sCuratorPhone  =  "";
            sCurator       =  "_________________________";

            try
            {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                String currentUserName = authentication.getName();
                User currentUser = this.userService.findByUsername(currentUserName);
                Staff staff = this.staffService.findById(currentUser.getStaff().getId());
                Person person = this.personService.findById(staff.getPerson().getId());

                sCuratorPhone = person.getContact().getName();
                sCurator = person.getIdentityDoc().getIdentityDocDetails().getFirstname().substring(0,1)+" "+person.getIdentityDoc().getIdentityDocDetails().getLastname();
            }
            catch(Exception ex)
            {
                sCuratorPhone  =  "";
                sCurator       =  "_________________________";
            }



            ReportTool reportTool = new ReportTool();
            String RegionName = "";
            String DistrictName = "";
            Address address = new Address();

            sRasDate = reportTool.DateToString(collectionPhase.getStartDate());




            sAdres         =  "";

            try
            {

                address = addressService.findById(debtor.getAddress_id());

                RegionName = address.getRegion().getName();
                DistrictName = address.getDistrict().getName();
                sAdres         =  address.getLine();
                sAokmotu = address.getAokmotu().getName();


            }
            catch (Exception ex)
            {
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

            if(!RegionName.contains("г."))
                RegionName += " облусу ";


            if(!DistrictName.contains("г."))
                DistrictName += " району, ";
            sRecipient+=RegionName+"\n";
            sRecipient+=DistrictName+"\n";

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

            cell = new RtfCell (new Paragraph ("Доо-Талап                              ",TitleFont));
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

            cell = new RtfCell (new Paragraph (reportTool.FormatNumber(SumProsAll)+" с.",TitleFont));
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            cell.setBorder(TitleColumnBorder);
            table.addCell (cell);


            String sText1 = "";
            String sText2 = "";
            if(workSector==1 || workSector==12)
            {
                sText1= "товардык кредит(тер)";
                sText2= "төлөө мөөнөтү өткөн ";

            }
            else
            {
                sText1= "бюджеттик ссуда(лар)";
                sText2= "";
            }




            cell = new RtfCell (new Paragraph ("        Кыргыз Республикасынын Финансы министирлигине караштуу Бюджеттик насыяларды башкаруу боюнча мамлекеттик агенттиги, төмөндө көрсөтүлгөн  "+sText1+" боюнча "+sRasDate+"-ж. карата, Сиздин суммасы "+reportTool.FormatNumber(SumProsAll)+" сом болгон, "+sText2+" карызыңыз бар экендигин билдирет:",ColumnFont));
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

            if(workSector==1 || workSector==12)
            {
                cell = new RtfCell (new Paragraph ("Товардык кредит",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBorders(TbBorder);
                table.addCell (cell);
            }
            else
            {
                cell = new RtfCell (new Paragraph ("Бюджеттик ссуда",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBorders(TbBorder);
                table.addCell (cell);
            }

            cell = new RtfCell (new Paragraph (" Карыз (сом)",TitleFont2));
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorders(TbBorder);
            table.addCell (cell);

            cell = new RtfCell (new Paragraph (" Банктын реквизиттери",TitleFont2));
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorders(TbBorder);
            table.addCell (cell);

            int x=0;


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

                Loan loan = loanService.getById(iCreditID);

                iCreditType = (short)loan.getLoanType().getId();

                if(workSector==1 || workSector==12)
                {
                    iResDepartment = 1;
                }
                else
                {
                    iResDepartment = 2;
                }

                sCreditNumber=loan.getRegNumber();

                sCreditDate=loan.getRegDate().toString();

                sOrderNumber=loan.getCreditOrder().getRegNumber();

                iDebtAll = phaseDetails.getStartTotalAmount();

                BankData bankData =null;

                switch (iCreditType)
                {
                    case 1: sNachOtdela="Туркбаев Б.К.";      break;
                    case 2: sNachOtdela="Туркбаев Б.К.";      break;
                    case 3: sNachOtdela="________________";    break;
                    case 4: sNachOtdela="Гр. Швейцарии";    break;
                    case 5: sNachOtdela="Туркбаев Б.К.";      break;
                    case 6: sNachOtdela="________________";   break;
                    case 7: sNachOtdela="________________";   break;
                    case 8: sNachOtdela="________________";     break;
                    case 9: sNachOtdela="Туркбаев Б.К.";      break;

                }

                try {
                    String baseQuery="select bank_data.* from bank_data,loan where bank_data.organization_id = 1 and loan.bankDataId = bank_data.id and loan.id = "+String.valueOf(phaseDetails.getLoan_id());

                    Query query=entityManager.createNativeQuery(baseQuery, BankData.class);

                    bankData =(BankData) query.getSingleResult();

                }
                catch (Exception ex)
                {

                }

                if(iResDepartment==2 && (workSector==13||workSector==134|iCreditType==10||iCreditType==11||iCreditType==12))
                {
                    sPaymentRequsites  = "Алуучу: Түздөн-түз кирешелер - БНБМА";
                    sPaymentRequsites += "\nБик: 440001"+" ";
                    sPaymentRequsites += "\nАлуучу банк: КР ФМ Борбордук казынасы"+" ";
                    sPaymentRequsites += "\nЭсеп: 4400011001000240"+" ";
                    sPaymentRequsites += "\nТөлөө коду: негизги сумма үчүн - 32142130";
                }
                else
                {
                    if(bankData!=null)
                    {
                        sPaymentRequsites  = bankData.getRecipient();
                        sPaymentRequsites += bankData.getRecipient_bank()+" ";
                        sPaymentRequsites += "\nБик: "+bankData.getBik()+" ";
                        sPaymentRequsites += "\nРасчетный счет: "+bankData.getAccount_number();

                    }
                    else
                    {
                        sPaymentRequsites  = "Центральное казначейство МФ КР"+" ";
                        sPaymentRequsites += "Национальный банк КР"+" ";
                        sPaymentRequsites += "\nБик: 101001"+" ";
                        sPaymentRequsites += "\nРасчетный счет: 1013710000000102";
                    }
                }



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

                cell = new RtfCell (new Paragraph (reportTool.FormatNumber(iDebtAll/(1)),HeaderFont));
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


            cell = new RtfCell (new Paragraph (" Жыйынтык сумма:   ",TitleFont2));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorders(TbBorder);
            table.addCell (cell);

            cell = new RtfCell (new Paragraph (reportTool.FormatNumber(SumProsAll/(1)),TitleFont2));
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

            cell = new RtfCell (new Paragraph ("       Кыргыз Республикасынын Жарандык кодексинин 299-беренесине ылайык Сиз кредитке алган сумманы, келишимдин шарттары боюнча көрсөтүлгөн мөөнөттө кайтарууга милдеттүүсүз. \n" +
                    "       Ошол себептен, аталган доо-талапты алган күндөн баштап 15 күндүн ичинде жогоруда көрсөтүлгөн карыздын суммасын толук көлөмдө төлөп кутулууңуз зарыл. \n" +
                    "       Көрсөтүлгөн мөөнөттө карыз кайтарылбаса, Бюджеттик кредиттерди башкаруу боюнча мамлекеттик агенттиги, Кыргыз Республикасынын Жарандык-процессуалдык кодексинин 4-беренесин жетекчиликке алып, карызды күрөөгө коюлган мүлктүн эсебинен мажбурлап өндүрүү үчүн, доо арыз менен сотко кайрылууга мажбур болот.\n" +
                    "       Бардык соттук чыгымдар төлөмөрдүн эсебинен жүргүзүлөт.",ColumnFont));

            cell.setHorizontalAlignment (Element.ALIGN_JUSTIFIED);
            cell.setVerticalAlignment(Element.ALIGN_TOP);

            cell.setBorder(TitleColumnBorder);
            cell.setColspan(4);
            table.addCell (cell);

            String stirkeme="\nТиркеме:  "+sRasDate+" жылдагы карыздын эсеп-кысабы.";
//            if(currentUser == 203)
//            {
//                stirkeme = "  \nАгенттиктин Ош облусундагы өкүлчүлүгүнүн дареги: Ош ш., Ленин көчөсү №289. \nСурап билүү телефондору : 03222 7-07-51, 03222 2-57-31, 0770 197-000, 0770 077-749 ";
//                PerformerFont=ColumnFont;
//            }
            cell = new RtfCell (new Paragraph (stirkeme,PerformerFont));
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


            if( iResDepartment==2)
            {
                cell = new RtfCell (new Paragraph ("\nДиректордун орун басары",TitleFont));
            }
            else
            {
                cell = new RtfCell (new Paragraph ("\nДиректор",TitleFont));
            }

            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);

            cell.setBorder(TitleColumnBorder);
            table.addCell (cell);

            cell = new RtfCell (new Paragraph ("",TitleFont));
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);

            cell.setBorder(TitleColumnBorder);
            table.addCell (cell);


            if( iResDepartment==2)
            {
                cell = new RtfCell (new Paragraph ("\nДж. Сагынов",TitleFont));
            }
            else
            {
                cell = new RtfCell (new Paragraph ("\n_________________",TitleFont));
            }


            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(TitleColumnBorder);
            table.addCell (cell);




            // Согласовано

            cell = new RtfCell (new Paragraph ("",TitleFont));
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);

            cell.setBorder(TitleColumnBorder);
            table.addCell (cell);

            cell = new RtfCell (new Paragraph ("",TitleFont));
            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);

            cell.setBorder(TitleColumnBorder);
            table.addCell (cell);

            if( iResDepartment==2)
            {
                cell = new RtfCell (new Paragraph ("\nМакулдашылды:",HeaderFont));
            }
            else
            {
                cell = new RtfCell (new Paragraph ("\nМакулдашылды:",HeaderFont));
            }


            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(TitleColumnBorder);
            table.addCell (cell);

            cell = new RtfCell (new Paragraph ("",TitleFont));
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);

            cell.setBorder(TitleColumnBorder);
            table.addCell (cell);


            // Согласовано



//            cell = new RtfCell (new Paragraph ("",TitleFont));
//            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
//            cell.setVerticalAlignment(Element.ALIGN_TOP);
//
//            cell.setBorder(TitleColumnBorder);
//            table.addCell (cell);
//
//            cell = new RtfCell (new Paragraph ("",TitleFont));
//            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
//            cell.setVerticalAlignment(Element.ALIGN_TOP);
//
//            cell.setBorder(TitleColumnBorder);
//            table.addCell (cell);
//
//            cell = new RtfCell (new Paragraph ("",TitleFont));
//            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
//            cell.setVerticalAlignment(Element.ALIGN_TOP);
//
//            cell.setBorder(TitleColumnBorder);
//            table.addCell (cell);
//
//
//
//            cell = new RtfCell (new Paragraph ("\nДоолбеков Э.Б.",HeaderFont));
//
//
//
//            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
//            cell.setVerticalAlignment(Element.ALIGN_TOP);
//            cell.setBorder(TitleColumnBorder);
//            table.addCell (cell);

            cell = new RtfCell (new Paragraph ("",TitleFont));
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);

            cell.setBorder(TitleColumnBorder);
            table.addCell (cell);

            cell = new RtfCell (new Paragraph ("",TitleFont));
            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);

            cell.setBorder(TitleColumnBorder);
            table.addCell (cell);

            cell = new RtfCell (new Paragraph ("",TitleFont));
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);

            cell.setBorder(TitleColumnBorder);
            table.addCell (cell);


            if( iResDepartment==2)
            {
                cell = new RtfCell (new Paragraph ("\nАсеков К.Т.",HeaderFont));
            }
            else
            {
                cell = new RtfCell (new Paragraph ("\nЮсупов А.Б.",HeaderFont));
            }


            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(TitleColumnBorder);
            table.addCell (cell);




            if(iResDepartment==2 && (workSector==13||workSector==134|iCreditType==10||iCreditType==11||iCreditType==12))
            {
                cell = new RtfCell (new Paragraph ("",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_TOP);

                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

//                        cell = new RtfCell (new Paragraph ("\nЗаведующий отделом кредитов предпринимательства и ф.л.",TitleFont2));
                cell = new RtfCell (new Paragraph ("\n",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);

                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new RtfCell (new Paragraph ("",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_TOP);

                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);


                cell = new RtfCell (new Paragraph ("\nОзонов А.К.",HeaderFont));

                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

            }
            else if (iResDepartment==2)
            {
                cell = new RtfCell (new Paragraph ("",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_TOP);

                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new RtfCell (new Paragraph ("\n",HeaderFont));
//                        cell = new RtfCell (new Paragraph ("\nЗаведующий отделом кредитов промышленности",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);

                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new RtfCell (new Paragraph ("",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);

                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);


                cell = new RtfCell (new Paragraph ("\nДжумагулова Ж.",HeaderFont));

                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);
            }
            else if (iResDepartment==1 && (workSector==12||iCreditType==3||iCreditType==6||iCreditType==7||iCreditType==8))
            {
                cell = new RtfCell (new Paragraph ("",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_TOP);

                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new RtfCell (new Paragraph ("\n",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);

                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new RtfCell (new Paragraph ("",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);

                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);


                cell = new RtfCell (new Paragraph ("\n___________________",HeaderFont));

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

                cell = new RtfCell (new Paragraph ("\n",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);

                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new RtfCell (new Paragraph ("",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_TOP);

                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);


                cell = new RtfCell (new Paragraph ("\nАзимов А.",HeaderFont));

                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);


            }


            if(sCurator!=null)
            {
                HeaderFooter footer = new HeaderFooter(new Paragraph("Аткар.: "+sCurator+"  тел: "+sCuratorPhone,PerformerFont),false);
                document.setFooter(footer);
            }

            document.add(table);



            document.close();





        }

        catch(Exception Ex)
        {

        }


    }


}

