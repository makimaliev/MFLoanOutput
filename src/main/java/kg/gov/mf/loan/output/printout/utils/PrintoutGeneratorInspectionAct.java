package kg.gov.mf.loan.output.printout.utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.table.RtfBorder;
import com.lowagie.text.rtf.table.RtfBorderGroup;
import com.lowagie.text.rtf.table.RtfCell;
import kg.gov.mf.loan.admin.org.model.Address;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.AddressService;
import kg.gov.mf.loan.admin.org.service.StaffService;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.collateral.CollateralAgreement;
import kg.gov.mf.loan.manage.model.collateral.CollateralItem;
import kg.gov.mf.loan.manage.model.collateral.CollateralItemDetails;
import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.service.collateral.CollateralAgreementService;
import kg.gov.mf.loan.manage.service.collateral.CollateralItemService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.output.report.utils.ReportTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.awt.*;
import java.util.Date;

public class PrintoutGeneratorInspectionAct {

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


    public void generatePrintout(long objectId, Document document,String username)
    {
        String     sContentType  = "";

        try
        {

            SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);


            String    sPersonTitle  = "";

            long      iCreditID      = 0;
            long      iPersonID      = 0;

            String     district      = null;
            String     region        = null;
            int       Thousands      = 1000;
            short     iCreditType    = 1;


            String    sOrderNumber      = "";
            String    sCreditDate       = "";
            String    sCreditNumber     = "";
            String    sAdres            = "";
            String    sDistrictCurator  = "";


            String    DepositName			="";
            String    Zalogodatel			="";
            String    deposit_type			="";
            String     quantity_type		= "";

            Double     quantity		= 0.0;


            String    sIdCode			= "";
            Date      GoodsDate			= null;
            String    itemType			= "";
            long      iItemType		    = 0;
            String    GoodsType			= "";
            String    GoodsId			= "";
            String    Details1			="";
            String    Details2			="";
            String    Details3			="";
            String    Details4			="";
            String    Details5			="";
            String    Details6			="";
            String    GoodsAddress		="";


            Date      CreditDate		= null;

            //*************************************************************************************************************
            //CTemplateManager  TemplateManager  = ((CTemplateManager)application.getAttribute("TemplateManager"));
            //*************************************************************************************************************

            //*********** Document ********************************************************************************
            //*******************************************************************************************

            Table table = null;

            RtfCell cell  = null;

            int   TitleColumnBorder   = 0;

            BaseFont   myfont        = BaseFont.createFont("//ArialCyr.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font HeaderFont    = new  Font(myfont,11,Font.NORMAL);
            Font       ColumnFont    = new  Font(myfont,12,Font.NORMAL);
            Font       TitleFont     = new  Font(myfont,12,Font.BOLD);
            Font       PerformerFont = new  Font(myfont,8,Font.NORMAL);
            Color HeaderColor   = new  Color (218,218,218);

            RtfBorderGroup TbBorder  = new RtfBorderGroup(Rectangle.BOX, RtfBorder.BORDER_SINGLE, 1, new Color(75,75,75));

//            RtfWriter2.getInstance(document,response.getOutputStream());
            document.open();

            int x=0;

            //************************************************************************************************
            //
            //        GET INFORMATION
            //
            //************************************************************************************************

            int  depCount1 =0;
            int  depCount2 =0;
            int  depCount3 =0;
            int  depCount4 =0;
            int  depCount5 =0;

            String depList ="";
            String depList2 ="";
            String depList3 ="";
            String depList4 ="";
            String depList5 ="";

            ReportTool reportTool=new ReportTool();


            try
            {

                Loan loan=loanService.getById(objectId);
                Owner owner=loan.getDebtor().getOwner();
                iCreditID = objectId;

                System.out.println(" DEPOSIT ACT TEMPLATE == CREDIT ID - "+iCreditID);

//                sQuery="select address.region, address.district, address.address_line1,  credit.person_id, credit.date as credit_date, credit.number as credit_number, person.title as person_title from credit,person,address where address.user_id = person.id and credit.id = ? and person.id = credit.person_id ";
//
//                prp_st = DbConnection.prepareStatement(sQuery);
//                prp_st.setLong(1,iCreditID);
//                prp_res = prp_st.executeQuery();

                iPersonID = owner.getId();
                sPersonTitle = owner.getName();
                sCreditNumber = loan.getRegNumber();
                sCreditDate = reportTool.DateToString(loan.getRegDate());
                CreditDate = loan.getRegDate();
                Address address=addressService.findById(owner.getAddress().getId());
                region = address.getRegion().getName();
                district = address.getDistrict().getName();

                sAdres = region+ " область, " + district+" район, " +owner.getAddress().getLine();



                User user = userService.findByUsername(username);
                Staff staff=staffService.findById(user.getStaff().getId());

                sDistrictCurator = staff.getName();


                depList ="";
                for(CollateralAgreement agreement1:loan.getCollateralAgreements()) {
                    CollateralAgreement agreement = collateralAgreementService.getById(agreement1.getId());
                    for (CollateralItem item1 : agreement.getCollateralItems()) {
                        CollateralItem item=collateralItemService.getById(item1.getId());

                        CollateralItemDetails details = item.getCollateralItemDetails();
                        depCount1++;
                        iItemType=item.getItemType().getId();

                        if (iItemType == 1 || iItemType == 4 || iItemType == 5 || iItemType == 8) {

                            DepositName = item.getName();
                            deposit_type = item.getItemType().getName();
                            quantity = item.getQuantity();
                            quantity_type = item.getQuantityType().getName();
                            Zalogodatel = item.getOwner().getName();

                            GoodsAddress = details.getGoods_address();
                            GoodsDate = details.getDetailsDate();
                            Details1 = details.getDetails1();
                            Details2 = details.getDetails2();
                            Details3 = details.getDetails3();
                            Details4 = details.getDetails4();
                            Details5 = details.getDetails5();
                            Details6 = details.getDetails6();
                            GoodsType = details.getGoods_type();

                            depList += "\n";


                            if (!(DepositName == null || DepositName == "")) depList += "\n" +depCount1+ ". Наименование залога - " + DepositName;
                            if (deposit_type != null || deposit_type != "") depList += " ( " + deposit_type;
                            if (quantity != 0) depList += " " + quantity;
                            if (quantity_type != null || quantity_type != "")
                                depList += " " + quantity_type + " )," + "\n";
                            if (!(Zalogodatel == null || Zalogodatel == ""))
                                depList += " Залогодатель  - " + Zalogodatel + "\n";
                            if (!(GoodsAddress == null || GoodsAddress == "")) {
                                if (GoodsAddress == null) {
                                    System.out.println("  111111111 ");
                                }
                                if (GoodsAddress == "") {
                                    System.out.println(" 22222222222 ");
                                }

                                if ((GoodsAddress == null || GoodsAddress == "")) {
                                    System.out.println(" 33333333 ");
                                } else {
                                    System.out.println(" 4444444444444 ");
                                }


                                depList += " Адрес  - " + GoodsAddress + "\n";
                            }
                            if (GoodsDate != null)
                                depList += " Дата выпуска  - " + reportTool.DateToString(details.getDetailsDate()) + "\n";
                            if (!(Details1 == null || Details1 == "")) depList += " Зав. номер  - " + Details1 + "\n";
                            if (!(Details2 == null || Details2 == "")) depList += " Двигатель  - " + Details2 + "\n";
                            if (!(Details3 == null || Details3 == "")) depList += " номер шасси  - " + Details3 + "\n";
                            if (!(Details4 == null || Details4 == "")) depList += " Номер кузова  - " + Details4 + "\n";
                            if (!(Details5 == null || Details5 == "")) depList += " Инв. номер - " + Details5 + "\n";
                            if (!(GoodsId == null || GoodsId == "")) depList += " Гос.номер  - " + GoodsId + "\n";
                            if (!(GoodsType == null || GoodsType == ""))
                                depList += " Марка, модификация  - " + GoodsType + "\n";
                            if (DepositName != null || DepositName != "")
                                depList += " Состояние: ______________________________________________ " + "\n";

                        } else if (iItemType == 2) {
                            depList += "\n";

                            DepositName = item.getName();
                            deposit_type = item.getItemType().getName();
                            quantity = item.getQuantity();
                            quantity_type = item.getQuantityType().getName();


                            GoodsType = details.getGoods_type();
                            GoodsDate = details.getDetailsDate();
                            String Expl_date = reportTool.DateToString(details.getExplDate());
                            Details1 = details.getDocument();
                            Details2 = details.getIncomplete_reason();


                            if (!(DepositName == null || DepositName == "")) depList += "\n" +depCount1+ ". Наименование залога - " + DepositName;
                            if (GoodsDate != null) depList += " Дата выпуска  - " + GoodsDate + "\n";
                            if (!(Expl_date == null || Expl_date == ""))
                                depList += " Дата ввода в эксплуатацию  - " + Expl_date + "\n";
                            if (!(Details1 == null || Details1 == ""))
                                depList += " Правоуст. документ   - " + Details1 + "\n";
                            if (!(Details2 == null || Details2 == "")) depList += " Причина  - " + Details2 + "\n";
                            if (DepositName != null || DepositName != "")
                                depList += " Состояние: ______________________________________________ " + "\n";
                        } else if (iItemType == 3 || iItemType == 7) {

                            depList += "\n";

                            DepositName = item.getName();
                            deposit_type = item.getItemType().getName();
//                            depList += depCount1 + ". " + deposit_type;
                            quantity = item.getQuantity();
                            quantity_type = item.getQuantityType().getName();
//                            depList += " (" + quantity + " " + quantity_type + ")";


                            GoodsType = details.getGoods_type();
                            GoodsId = details.getGoods_id();
                            GoodsDate = details.getDetailsDate();
                            String Expl_date = reportTool.DateToString(details.getExplDate());
                            String Document = details.getDocument();
                            GoodsAddress = details.getGoods_address();
                            Details1 = details.getDetails1();
                            Details2 = details.getDetails2();
                            Details3 = details.getDetails3();
                            Details4 = details.getDetails4();
                            String InCompleteReason = details.getIncomplete_reason();


                            if (!(DepositName == null || DepositName == "")) depList += "\n" +depCount1+ ". Наименование залога - " + DepositName;
                            depList += " (" + quantity + " " + quantity_type + ")\n";
                            if (GoodsType != null || GoodsType != "")
                                depList += " Вид недвижимости  - " + GoodsType + "\n";
                            if (GoodsId != null) depList += " Идентификационный код  - " + GoodsId + "\n";
                            if (GoodsDate != null) depList += " Дата постройки  - " + GoodsDate + "\n";
                            if (!(Expl_date == null || Expl_date == ""))
                                depList += " Дата ввода в эксплуатацию  - " + Expl_date + "\n";
                            if (!(Document == null || Document == ""))
                                depList += " Правоуст. документ    - " + Document + "\n";
                            if (!(GoodsAddress == null || GoodsAddress == ""))
                                depList += " Адрес  - " + GoodsAddress + "\n";
                            if (!(Details1 == null || Details1 == ""))
                                depList += " Общая площадь (кв.м.)   - " + Details1 + "\n";
                            if (!(Details2 == null || Details2 == ""))
                                depList += " Жилая площадь (кв.м.)  - " + Details2 + "\n";
                            if (!(Details3 == null || Details3 == ""))
                                depList += " Площадь застройки (кв.м.)  - " + Details3 + "\n";
                            if (!(Details4 == null || Details4 == ""))
                                depList += " Зем.участок мерою (кв.м.)  - " + Details4 + "\n";
                            if (!(InCompleteReason == null || InCompleteReason == ""))
                                depList += " Причина  - " + InCompleteReason + "\n";
                            if (DepositName != null || DepositName != "")
                                depList += " Состояние: ______________________________________________ " + "\n";
                        } else {

                            depList += "\n";

                            DepositName = item.getName();
                            deposit_type = item.getItemType().getName();
//                            depList += depCount1 + ". " + deposit_type;
                            quantity = item.getQuantity();
                            quantity_type = item.getQuantityType().getName();
//                            depList += " (" + quantity + " " + quantity_type + ")";


                            String Document = details.getDocument();
                            Details1 = details.getDetails1();
                            Details2 = details.getDetails2();
                            Details3 = details.getDetails3();
                            Details4 = details.getDetails4();
                            Details5 = details.getDetails5();
                            String InCompleteReason = details.getIncomplete_reason();


                            if (!(DepositName == null || DepositName == "")) depList += "\n" +depCount1+ ". Наименование залога - " + DepositName;
                            depList += " (" + quantity + " " + quantity_type + ")";
                            if (!(Document == null || Document == ""))
                                depList += " Правоуст. документ    - " + Document + "\n";
                            if (!(Details1 == null || Details1 == "")) depList += " КРС   - " + Details1 + "\n";
                            if (!(Details2 == null || Details2 == "")) depList += " МРС  - " + Details2 + "\n";
                            if (!(Details3 == null || Details3 == "")) depList += " Лошади  - " + Details3 + "\n";
                            if (!(Details4 == null || Details4 == "")) depList += " Яки  - " + Details4 + "\n";
                            if (!(Details5 == null || Details5 == "")) depList += " Свиньи  - " + Details5 + "\n";
                            if (!(InCompleteReason == null || InCompleteReason == ""))
                                depList += " Причина  - " + InCompleteReason + "\n";
                            if (DepositName != null || DepositName != "")
                                depList += " Состояние: ______________________________________________ " + "\n";

                        }

                    }

                }
            }
            catch (Exception Ex)
            {
                System.out.println(" DEPOSIT ACT TEMPLATE == ERROR = "+Ex);
            }



            //***************************************************************************************************
            //
            //
            //
            //***************************************************************************************************

            table=new Table(2);
            table.setWidth(100);

            int iWidth[] = new int[2];
            iWidth[0] = 70;
            iWidth[1] = 30;

            table.setWidths(iWidth);

            cell = new RtfCell (new Paragraph ("АКТ",TitleFont));
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorder(TitleColumnBorder);
            cell.setColspan(2);
            table.addCell (cell);

            cell = new RtfCell (new Paragraph ("от «____»_____________ 2019 года",TitleFont));
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorder(TitleColumnBorder);
            cell.setColspan(2);
            table.addCell (cell);

            cell = new RtfCell (new Paragraph ("",TitleFont));
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorder(TitleColumnBorder);
            cell.setColspan(2);
            table.addCell (cell);


            String DepText="";
            DepText+="Обследования залогового имущества ";
            DepText+=sPersonTitle;
            DepText+=" полученного по кредитному договору (долговому обязательству, договору товарного кредита) ";
            DepText+=" от ";

            if(!CreditDate.equals(null))
            {
                DepText+=reportTool.DateToString(CreditDate);
                DepText+=" года ";
            }
            else
            {
                DepText+="_____________";
            }

            DepText+=" № ";

            if(!sCreditNumber.equals(null))
            {
                DepText+=(sCreditNumber);
            }
            else
            {
                DepText+="_______________________________";
            }

            DepText+="\n"+"Комиссия в составе:  ";
            DepText+=sDistrictCurator;
            DepText+="\n"+"                     ________________________________________";
            DepText+="\n"+"составила настоящий акт об условиях хранения, содержания, использования, наличия и фактического состояния залогового имущества, предоставленного ";
            DepText+=sPersonTitle;
            DepText+=" в  обеспечение исполнения обязательств, а именно: ";
            DepText+="\n"+"Место нахождения, хранения залогового имущества: ";
            DepText+=sAdres;
            DepText+="\n"+"При обследовании залогового имущества, представленного в качестве исполнения обязательств заемщика было установлено следующее: ";

            if(depCount1!=0) DepText+="\n" +depList;




            DepText+="\n"+" По результатам проверки комиссия сделала следующее заключение:"+"\n";
            DepText+=" ________________________________________________________________________________"+"\n";
            DepText+=" ________________________________________________________________________________"+"\n";
            DepText+=" ________________________________________________________________________________"+"\n";
            DepText+=" ________________________________________________________________________________"+"\n"+"\n"+"\n";

            DepText+=" Подписи :  ______________________________"+"\n";
            DepText+="              ______________________________"+"\n";
            DepText+="              ______________________________"+"\n";


            cell = new RtfCell (new Paragraph (DepText,ColumnFont));
            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(TitleColumnBorder);
            cell.setColspan(2);
            table.addCell (cell);

                    /*document.add(table);

                    /*
                    table=new Table(2);
                    table.setWidth(100);

                    int iWidth3[] = new int[2];
                    iWidth3[0] = 50;
                    iWidth3[1] = 50;

                    table.setWidths(iWidth3);

                    /*
                    cell = new RtfCell (new Paragraph ("\n     В соответствии со статьей 299 Гражданского кодекса Кыргызской Республики Вы обязаны возвратить Государственному фонду развития экономики при Министерстве финансов Кыргызской Республики полученную по кредиту сумму в срок и в порядке предусмотренными условиями договора." +
                                                        "\n     На основании изложенного, просим погасить вышеуказанную задолженность в полном объеме в течении 10 дней со дня получения настоящей претензии." +
                                                        "\n     В Случае не погашения задолженности в указанный срок Государственный фонд развития экономики при Министерстве финансов Кыргызской Республики, руководствуясь ст. 4 Гражданского процессуального кодекса Кыргызской Республики, будет вынужден обратиться с иском в суд для принудительного взыскания задолженности."+
                                                        "\n     Все судебные расходы относятся на Должника.",ColumnFont));

                    cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);

                    cell.setBorder(TitleColumnBorder);
                    cell.setColspan(2);
                    table.addCell (cell);
*/

                    /*
                    cell = new RtfCell (new Paragraph ("\n\n\nЗаместитель директора",TitleFont));
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);

                    cell.setBorder(TitleColumnBorder);
                    table.addCell (cell);

                    if(1 == 2)
                    {
                        cell = new RtfCell (new Paragraph ("\n\n\nБаденов Б.О",TitleFont));
                    }
                    else
                    {
                        cell = new RtfCell (new Paragraph ("\n\n\nНаспеков Д.",TitleFont));
                    }
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);

                    cell.setBorder(TitleColumnBorder);
                    table.addCell (cell);

                    */

            HeaderFooter footer = new HeaderFooter(new Paragraph("Аткар.: "+"  тел: ",PerformerFont),false);

            document.setFooter(footer);
            document.add(table);
            //****************************************************************************************
            //****************************************************************************************
            //**************************************************************************
            //**************************************************************************
            document.close();
        }
        catch(Exception Ex)
        {
            System.out.println(Ex);

        }

    }
}