package kg.gov.mf.loan.output.printout.utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.table.RtfBorder;
import com.lowagie.text.rtf.table.RtfBorderGroup;
import com.lowagie.text.rtf.table.RtfCell;
import kg.gov.mf.loan.admin.org.model.Address;
import kg.gov.mf.loan.admin.org.model.BankData;
import kg.gov.mf.loan.admin.org.model.Person;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.*;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.collateral.CollateralAgreement;
import kg.gov.mf.loan.manage.model.collateral.CollateralItem;
import kg.gov.mf.loan.manage.model.collateral.CollateralItemDetails;
import kg.gov.mf.loan.manage.model.collection.CollectionPhase;
import kg.gov.mf.loan.manage.model.collection.PhaseDetails;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.service.collateral.CollateralAgreementService;
import kg.gov.mf.loan.manage.service.collateral.CollateralItemService;
import kg.gov.mf.loan.manage.service.collection.CollectionPhaseService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.orderterm.CurrencyRateService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermCurrencyService;
import kg.gov.mf.loan.manage.service.process.LoanSummaryService;
import kg.gov.mf.loan.output.report.model.CollateralItemView;
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
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

public class PrintoutGeneratorCollateralDocDamaged {

    @Autowired
    LoanService loanService;

    @Autowired
    UserService userService;

    @Autowired
    StaffService staffService;

    @Autowired
    AddressService addressService;

    @Autowired
    CollateralAgreementService collateralAgreementService;

    @Autowired
    CollateralItemService collateralItemService;


    @Autowired
    EntityManager entityManager;

    @Autowired
    PaymentViewService paymentViewService;

    @Autowired
    LoanSummaryService loanSummaryService;

    @Autowired
    LoanSummaryViewService loanSummaryViewService;

    @Autowired
    CurrencyRateService currencyRateService;

    @Autowired
    OrderTermCurrencyService currencyService;

    @Autowired
    PaymentScheduleViewService paymentScheduleViewService;

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


    public void generatePrintout(long objectId, Document document,String username)
    {
        String     sContentType  = "";

        {




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

                image1.scalePercent(60);
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
//                document.add(table);



                Debtor debtor = debtorService.getById(objectId);

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





                sAdres         =  "";

                try
                {

                    address = addressService.findById(debtor.getAddress_id());

                    sAdres         =  address.getLine();
                    sAokmotu = address.getAokmotu().getName();
                    RegionName = address.getRegion().getName().replace("ская", "");
                    DistrictName = address.getDistrict().getName().replace("ский", "");


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


                cell = new RtfCell (new Paragraph ("",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                String sDistrictCurator = "\nАгенттиктин "+RegionName.replace(" облусу ","")+" аймактык бөлүмүнүн "+DistrictName.replace(" району, ","")+" районундагы башкы адиси \n";


                cell = new RtfCell (new Paragraph (sDistrictCurator,TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new RtfCell (new Paragraph ("Кабарлоо\n",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBorder(TitleColumnBorder);
                cell.setColspan(2);
                table.addCell (cell);

                cell = new RtfCell (new Paragraph ("        Кыргыз Республикасынын Финансы министрлигине караштуу Бюджеттик насыяларды башкаруу боюнча мамлекеттик агенттиги (мындан ары-Агенттик), Сиздин бюджеттик кредит алгандыгыңызды жана кредитти камсыздоо үчүн күрөө келишимин түзгөндүгүңүздү маалымдайт.",ColumnFont));
                cell.setHorizontalAlignment (Element.ALIGN_JUSTIFIED);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                cell.setBorder(TitleColumnBorder);
                cell.setColspan(2);
                table.addCell (cell);

                cell = new RtfCell (new Paragraph ("        Ошону менен катары, 2019-жылдагы күрөө мүлктөрүн текшерүүнүн жыйынтыгы менен Сиздин күрөө мүлкүңүз жоготулгандыгы аныкталгандыгын билдирет.",ColumnFont));
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

                cell = new RtfCell (new Paragraph ("Күрөө предмети",TitleFont2));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBorders(TbBorder);
                cell.setColspan(3);
                table.addCell (cell);

                int x=0;

                try {

                    String baseQuery="select distinct i.* from loanCollateralAgreement lca\n" +
                            "INNER join loan l on lca.loanId = l.id\n" +
                            "INNER join collateralItem i on lca.collateralAgreementId = i.collateralAgreementId\n" +
                            "INNER join collateralItemInspectionResult Result ON i.id = Result.collateralItemId\n" +
                            "where Result.inspectionResultTypeId in (3,4,5,6,7,8) AND l.debtorId="+String.valueOf(objectId);

                    Query query=entityManager.createNativeQuery(baseQuery, CollateralItem.class);

                    List<CollateralItem> itemList = query.getResultList();

                    for (CollateralItem item: itemList)
                    {

                        String baseQuery2="select DISTINCT * from collateral_item_view v where v.v_ci_id = "+String.valueOf(item.getId())+" limit 1";

                        Query query2=entityManager.createNativeQuery(baseQuery2, CollateralItemView.class);

                        CollateralItemView itemView = (CollateralItemView)query2.getSingleResult();

                        x++;
                        cell = new RtfCell (new Paragraph (Integer.toString(x),HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setBorders(TbBorder);
                        table.addCell (cell);

                        String sItem = item.getName()+ ", " + itemView.getV_ci_details();

                        cell = new RtfCell (new Paragraph (sItem,HeaderFont));
                        cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        cell.setBorders(TbBorder);
                        cell.setColspan(3);
                        table.addCell (cell);
                    }


                }
                catch (Exception ex)
                {


                }




                document.add(table);


                table=new Table(4);
                table.setWidth(100);

                int iWidth3[] = new int[4];
                iWidth3[0] = 10;
                iWidth3[1] = 40;
                iWidth3[2] = 20;
                iWidth3[3] = 20;

                table.setWidths(iWidth3);

                Date date = new Date();
                DecimalFormat dFormat= new DecimalFormat("00");

                Calendar cal = Calendar.getInstance();
                cal.setTime(date);

                String day = dFormat.format(cal.get(Calendar.DAY_OF_MONTH));
                String month = dFormat.format(cal.get(Calendar.MONTH) + 1);
                String year = String.valueOf(cal.get(Calendar.YEAR));

                cell = new RtfCell (new Paragraph ("       Күрөө келишиминин шарттары боюнча Күрөө коюучу катары күрөө предметин сактоо жана оң абалда пайдалануу боюнча, " +
                        "эгерде, күрөө мүлкү жоголсо же жараксыз абалга келсе, " +
                        "күрөө мүлкүн ордуна коюу же карызды төлөп берүүгө милдеттенме алгандыгыңызды эскертет.\n" +
                        "       Күрөө келишиминин шарттары бузулгандыгына байланыштуу, Агенттик," +
                        day+"."+month+"."+year+"ж. га "+
                        "чейин жоголгон күрөө мүлкүн ордуна коюуну же карызды кайтарып берүүнү талап кылат.\n" +
                        "       Жогорудагы талап мөөнөтүндө аткарылбаса, Агенттик, мамлекеттик зайымдык каражаттарды кайтаруу " +
                        "максатында укук коргоо жана сот органдарына кайрылууга мажбур боло тургандыгын билдирет.\n" +
                        "\n       Экинчи дарекке маалымат иретинде.",ColumnFont));

                cell.setHorizontalAlignment (Element.ALIGN_JUSTIFIED);
                cell.setVerticalAlignment(Element.ALIGN_TOP);

                cell.setBorder(TitleColumnBorder);
                cell.setColspan(4);
                table.addCell (cell);

                cell = new RtfCell (new Paragraph ("",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_TOP);

                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);


                cell = new RtfCell (new Paragraph ("\nДиректордун орун басары",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);

                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                cell = new RtfCell (new Paragraph ("",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_TOP);

                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);



                cell = new RtfCell (new Paragraph ("\nДж. Сагынов",TitleFont));
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



                cell = new RtfCell (new Paragraph ("\nАкымбеков А.О.",HeaderFont));



                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

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


                cell = new RtfCell (new Paragraph ("\nАлыбаев К.Б.",HeaderFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

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
}