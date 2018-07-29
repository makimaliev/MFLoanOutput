package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.GroupTypeService;
import kg.gov.mf.loan.output.report.service.OutputParameterService;
import kg.gov.mf.loan.output.report.service.ReferenceViewService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReportTool
{

    @Autowired
    ReferenceViewService referenceViewService;

    @Autowired
    GroupTypeService groupTypeService;



    Map<String,Map<Long,String>> referenceMap      = new HashMap<>();

    Map<String, HSSFFont> fontMap = new HashMap<>();

    Map<String, HSSFColor> colorMap = new HashMap<>();

    Map<String, HSSFCellStyle> cellStyleMap = new HashMap<>();

    Map<String, GroupType> groupTypeMap = new HashMap<>();

    HSSFDataFormat   format         = null;

    int RowCount = 0;
    short ColumnCount=0;

    int lastRowCount = 0;

    Map<Integer,Float> rowHeightMap = new HashMap<Integer,Float>();

    HSSFRow          Row            = null;
    HSSFCell         Cell           = null;



    HSSFFont         FontTitle      = null;
    HSSFFont         FontHeader     = null;
    HSSFFont         FontSum        = null;
    HSSFFont         FontFooter     = null;
    HSSFFont         FontGroup1     = null;
    HSSFFont         FontGroup2     = null;
    HSSFFont         FontGroup3     = null;
    HSSFFont         FontGroup4     = null;
    HSSFFont         FontGroup5     = null;
    HSSFFont         FontGroup6     = null;

    HSSFColor        ColorTitle     = null;
    HSSFColor        ColorHeader    = null;
    HSSFColor        ColorSum       = null;
    HSSFColor        ColorFooter    = null;
    HSSFColor        ColorGroup1    = null;
    HSSFColor        ColorGroup2    = null;
    HSSFColor        ColorGroup3    = null;
    HSSFColor        ColorGroup4    = null;
    HSSFColor        ColorGroup5    = null;
    HSSFColor        ColorGroup6    = null;

    short            BorderTitle    = 0;
    short            BorderHeader   = 0;
    short            BorderSum      = 0;
    short            BorderFooter   = 0;
    short            BorderGroup1   = 0;
    short            BorderGroup2   = 0;
    short            BorderGroup3   = 0;
    short            BorderGroup4   = 0;
    short            BorderGroup5   = 0;
    short            BorderGroup6   = 0;

    HSSFCellStyle    TitleString    = null;
    HSSFCellStyle    TitleDate      = null;
    HSSFCellStyle    TitleInt       = null;
    HSSFCellStyle    TitleDouble    = null;

    HSSFCellStyle    HeaderString   = null;
    HSSFCellStyle    HeaderDate     = null;
    HSSFCellStyle    HeaderInt      = null;
    HSSFCellStyle    HeaderDouble   = null;


    HSSFCellStyle    SumString      = null;
    HSSFCellStyle    SumDate        = null;
    HSSFCellStyle    SumInt         = null;
    HSSFCellStyle    SumDouble      = null;

    HSSFCellStyle    FooterString    = null;
    HSSFCellStyle    FooterDate      = null;
    HSSFCellStyle    FooterInt       = null;
    HSSFCellStyle    FooterDouble    = null;


    HSSFCellStyle    Group1String    = null;
    HSSFCellStyle    Group1Date      = null;
    HSSFCellStyle    Group1Int       = null;
    HSSFCellStyle    Group1Double    = null;

    HSSFCellStyle    Group2String    = null;
    HSSFCellStyle    Group2Date      = null;
    HSSFCellStyle    Group2Int       = null;
    HSSFCellStyle    Group2Double    = null;

    HSSFCellStyle    Group3String    = null;
    HSSFCellStyle    Group3Date      = null;
    HSSFCellStyle    Group3Int       = null;
    HSSFCellStyle    Group3Double    = null;


    HSSFCellStyle    Group4String    = null;
    HSSFCellStyle    Group4Date      = null;
    HSSFCellStyle    Group4Int       = null;
    HSSFCellStyle    Group4Double    = null;


    HSSFCellStyle    Group5String    = null;
    HSSFCellStyle    Group5Date      = null;
    HSSFCellStyle    Group5Int       = null;
    HSSFCellStyle    Group5Double    = null;

    HSSFCellStyle    Group6String    = null;
    HSSFCellStyle    Group6Date      = null;
    HSSFCellStyle    Group6Int       = null;
    HSSFCellStyle    Group6Double    = null;






    public void initReference()
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        for (ReferenceView referenceView:this.referenceViewService.findAll())
        {
            if(referenceMap.get(referenceView.getList_type())==null)
            {
                Map<Long,String> newMap      = new HashMap<>();

                newMap.put(referenceView.getId(),referenceView.getName());

                referenceMap.put(referenceView.getList_type(), newMap);
            }
            else
            {
                referenceMap.get(referenceView.getList_type()).put(referenceView.getId(),referenceView.getName());
            }


        }
    }

    public void initReportSetup(ReportTemplate reportTemplate, HSSFWorkbook WorkBook)
    {
        initFonts(reportTemplate,WorkBook);
        initBorders(reportTemplate);
        initColors(reportTemplate);
        initFormat(reportTemplate,WorkBook);
        initCellStyles(reportTemplate,WorkBook);
    }

    public void initFonts(ReportTemplate reportTemplate,HSSFWorkbook Workbook )
    {
        FontTitle   = getFontByLevel(10,reportTemplate,Workbook);
        FontHeader  = getFontByLevel(11,reportTemplate,Workbook);
        FontSum     = getFontByLevel(12,reportTemplate,Workbook);
        FontFooter  = getFontByLevel(13,reportTemplate,Workbook);

        FontGroup1  = getFontByLevel(1,reportTemplate,Workbook);
        FontGroup2  = getFontByLevel(2,reportTemplate,Workbook);
        FontGroup3  = getFontByLevel(3,reportTemplate,Workbook);
        FontGroup4  = getFontByLevel(4,reportTemplate,Workbook);
        FontGroup5  = getFontByLevel(5,reportTemplate,Workbook);
        FontGroup6  = getFontByLevel(6,reportTemplate,Workbook);
    }

    public void initColors(ReportTemplate reportTemplate)
    {
        ColorTitle  = getColorByLevel(10,reportTemplate);
        ColorHeader = getColorByLevel(11,reportTemplate);
        ColorSum    = getColorByLevel(12,reportTemplate);
        ColorFooter = getColorByLevel(13,reportTemplate);

        ColorGroup1 = getColorByLevel(1,reportTemplate);
        ColorGroup2 = getColorByLevel(2,reportTemplate);
        ColorGroup3 = getColorByLevel(3,reportTemplate);
        ColorGroup4 = getColorByLevel(4,reportTemplate);
        ColorGroup5 = getColorByLevel(5,reportTemplate);
        ColorGroup6 = getColorByLevel(6,reportTemplate);
    }

    public void initBorders(ReportTemplate reportTemplate)
    {
        BorderTitle  = getBorderTypeByLevel(10,reportTemplate);
        BorderHeader = getBorderTypeByLevel(11,reportTemplate);
        BorderSum    = getBorderTypeByLevel(12,reportTemplate);
        BorderFooter = getBorderTypeByLevel(13,reportTemplate);

        BorderGroup1 = getBorderTypeByLevel(1,reportTemplate);
        BorderGroup2 = getBorderTypeByLevel(2,reportTemplate);
        BorderGroup3 = getBorderTypeByLevel(3,reportTemplate);
        BorderGroup4 = getBorderTypeByLevel(4,reportTemplate);
        BorderGroup5 = getBorderTypeByLevel(5,reportTemplate);
        BorderGroup6 = getBorderTypeByLevel(6,reportTemplate);
    }

    public void initFormat(ReportTemplate reportTemplate, HSSFWorkbook WorkBook)
    {
        format         = WorkBook.createDataFormat();
    }

    public void initCellStyles(ReportTemplate reportTemplate,HSSFWorkbook WorkBook)
    {
        TitleString    = getCellStyleByLevel(10,"string",reportTemplate,WorkBook);
        TitleDate      = getCellStyleByLevel(10,"date",reportTemplate,WorkBook);
        TitleInt       = getCellStyleByLevel(10,"int",reportTemplate,WorkBook);
        TitleDouble    = getCellStyleByLevel(10,"double",reportTemplate,WorkBook);

        HeaderString   = getCellStyleByLevel(11,"string",reportTemplate,WorkBook);
        HeaderDate     = getCellStyleByLevel(11,"date",reportTemplate,WorkBook);
        HeaderInt      = getCellStyleByLevel(11,"int",reportTemplate,WorkBook);
        HeaderDouble   = getCellStyleByLevel(11,"double",reportTemplate,WorkBook);


        SumString      = getCellStyleByLevel(12,"string",reportTemplate,WorkBook);
        SumDate        = getCellStyleByLevel(12,"date",reportTemplate,WorkBook);
        SumInt         = getCellStyleByLevel(12,"int",reportTemplate,WorkBook);
        SumDouble      = getCellStyleByLevel(12,"double",reportTemplate,WorkBook);

        FooterString    = getCellStyleByLevel(13,"string",reportTemplate,WorkBook);
        FooterDate      = getCellStyleByLevel(13,"date",reportTemplate,WorkBook);
        FooterInt       = getCellStyleByLevel(13,"int",reportTemplate,WorkBook);
        FooterDouble    = getCellStyleByLevel(13,"double",reportTemplate,WorkBook);


        Group1String    = getCellStyleByLevel(1,"string",reportTemplate,WorkBook);
        Group1Date      = getCellStyleByLevel(1,"date",reportTemplate,WorkBook);
        Group1Int       = getCellStyleByLevel(1,"int",reportTemplate,WorkBook);
        Group1Double    = getCellStyleByLevel(1,"double",reportTemplate,WorkBook);

        Group2String    = getCellStyleByLevel(2,"string",reportTemplate,WorkBook);
        Group2Date      = getCellStyleByLevel(2,"date",reportTemplate,WorkBook);
        Group2Int       = getCellStyleByLevel(2,"int",reportTemplate,WorkBook);
        Group2Double    = getCellStyleByLevel(2,"double",reportTemplate,WorkBook);

        Group3String    = getCellStyleByLevel(3,"string",reportTemplate,WorkBook);
        Group3Date      = getCellStyleByLevel(3,"date",reportTemplate,WorkBook);
        Group3Int       = getCellStyleByLevel(3,"int",reportTemplate,WorkBook);
        Group3Double    = getCellStyleByLevel(3,"double",reportTemplate,WorkBook);


        Group4String    = getCellStyleByLevel(4,"string",reportTemplate,WorkBook);
        Group4Date      = getCellStyleByLevel(4,"date",reportTemplate,WorkBook);
        Group4Int       = getCellStyleByLevel(4,"int",reportTemplate,WorkBook);
        Group4Double    = getCellStyleByLevel(4,"double",reportTemplate,WorkBook);


        Group5String    = getCellStyleByLevel(5,"string",reportTemplate,WorkBook);
        Group5Date      = getCellStyleByLevel(5,"date",reportTemplate,WorkBook);
        Group5Int       = getCellStyleByLevel(5,"int",reportTemplate,WorkBook);
        Group5Double    = getCellStyleByLevel(5,"double",reportTemplate,WorkBook);

        Group6String    = getCellStyleByLevel(6,"string",reportTemplate,WorkBook);
        Group6Date      = getCellStyleByLevel(6,"date",reportTemplate,WorkBook);
        Group6Int       = getCellStyleByLevel(6,"int",reportTemplate,WorkBook);
        Group6Double    = getCellStyleByLevel(6,"double",reportTemplate,WorkBook);
    }







    public String FormatNumber(double number)
        {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
            symbols.setDecimalSeparator(',');
            symbols.setGroupingSeparator(' ');

            String pattern = ",##0.00";
            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);

            return decimalFormat.format(number);
        }

        public String DateToString(Date date)
        {

            SimpleDateFormat DateFormatShort = new SimpleDateFormat("dd.MM.yyyy");

            if(date == null)
                return "";
            else
                return DateFormatShort.format(date);
        }


        public String getParameterTypeNameById(String id)
        {
            String parameterTypeName = "";


            switch(id)
            {
                case "1":
                    return "region";
                case "2":
                    return "district";
                case "3":
                    return "work_sector";
                case "4":
                    return "loan_type";
                case "5":
                    return "supervisor";
                case "6":
                    return "department";
                case "7":
                    return "debtor";
                case "8":
                    return "loan";
                case "9":
                    return "loan_summary";
                case "10":
                    return "payment";
                case "11":
                    return "schedule";
                case "12":
                    return "credit_order";
                case "13":
                    return "applied_entity";
                case "14":
                    return "applied_entity_list";
                case "15":
                    return "document_package";
                case "16":
                    return "entity_document";
                case "17":
                    return "collateral_agreement";
                case "18":
                    return "collateral_item";







            }



            return parameterTypeName;
        }





    public Date getOnDate(ReportTemplate reportTemplate)
    {

        Date date = new Date();
        for (GenerationParameter generationParameter: reportTemplate.getGenerationParameters())
        {
            if(generationParameter.getGenerationParameterType().getId()==1)
            {
                date = generationParameter.getDate();
            }
        }

        return date;
    }

    public String getParameterTypeNameById2(String id)
    {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        String parameterTypeName = "";

        if(groupTypeMap.isEmpty())
        {
            for (GroupType groupType: this.groupTypeService.findAll())
            {
                groupTypeMap.put(String.valueOf(groupType.getId()),groupType);

            }
        }

        if(!groupTypeMap.isEmpty())
        {
            return groupTypeMap.get(id).getField_name();
        }

        return parameterTypeName;
    }

    public long getGroupType(ReportTemplate reportTemplate,long level)
    {
        long groupType=0;

        for (GenerationParameter generationParameter:reportTemplate.getGenerationParameters())
        {
            if(generationParameter.getGenerationParameterType().getId()==level+3)
            {
                groupType = generationParameter.getPostionInList();
                return groupType;
            }
        }

        return  groupType;
    }

    public long getIdByGroupType(long groupType, EntityDocumentView entityDocumentView)
    {
        long idByGroupType=0;

        switch((short)groupType)
        {
            case 1:
                idByGroupType = entityDocumentView.getV_owner_region_id();
                break;
            case 2:
                idByGroupType = entityDocumentView.getV_owner_district_id();
                break;
            case 12:
                idByGroupType = entityDocumentView.getV_co_id();
                break;
            case 13:
                idByGroupType = entityDocumentView.getV_applied_entity_id();
                break;
            case 14:
                idByGroupType = entityDocumentView.getV_ael_id();
                break;
            case 15:
                idByGroupType = entityDocumentView.getV_document_package_id();
                break;
            case 16:
                idByGroupType = entityDocumentView.getV_entity_document_id();
                break;






        }

        return  idByGroupType;
    }


    public String getNameByGroupType(long groupType, EntityDocumentView entityDocumentView)
    {

        String nameByGroupType="";

        switch((short)groupType)
        {
            case 1:
                nameByGroupType = referenceMap.get("region").get(entityDocumentView.getV_owner_region_id()) ;
                break;
            case 2:
                nameByGroupType = referenceMap.get("district").get(entityDocumentView.getV_owner_district_id()) ;
                break;
            case 12:
                nameByGroupType = referenceMap.get("credit_order").get(entityDocumentView.getV_co_id()) ;
                break;
            case 13:
                nameByGroupType = entityDocumentView.getV_owner_name();
                break;
            case 14:
                nameByGroupType = " Список получателей № "+entityDocumentView.getV_ael_listNumber()+" от "+entityDocumentView.getV_ael_listDate();
                break;
            case 15:
                nameByGroupType = entityDocumentView.getV_document_package_name();
                break;
            case 16:
                nameByGroupType = entityDocumentView.getV_entity_document_name();
                break;

        }

        return  nameByGroupType;
    }


    public long getIdByGroupType(long groupType, LoanSummaryView loanSummaryView)
    {
        long idByGroupType=0;

        switch((short)groupType)
        {
            case 1:
                idByGroupType = loanSummaryView.getV_debtor_region_id();
                break;
            case 2:
                idByGroupType = loanSummaryView.getV_debtor_district_id();
                break;
            case 7:
                idByGroupType = loanSummaryView.getV_debtor_id();
                break;
            case 8:
                idByGroupType = loanSummaryView.getV_loan_id();
                break;
            case 9:
                idByGroupType = loanSummaryView.getV_ls_id();
                break;
            case 3:
                idByGroupType = loanSummaryView.getV_debtor_work_sector_id();
                break;






        }

        return  idByGroupType;
    }

    public String getNameByGroupType(long groupType, LoanSummaryView loanSummaryView)
    {
        String nameByGroupType="";

        switch((short)groupType)
        {
            case 1:
                nameByGroupType = loanSummaryView.getV_region_name();
                break;
            case 2:
                nameByGroupType = loanSummaryView.getV_district_name();
                break;
            case 7:
                nameByGroupType = loanSummaryView.getV_debtor_name();
                break;
            case 8:
                nameByGroupType =loanSummaryView.getV_credit_order_regNumber()
                        + " от "
                        + loanSummaryView.getV_credit_order_regDate()
                        + ", "
                        + loanSummaryView.getV_loan_reg_number()
                        + " от "
                        + loanSummaryView.getV_loan_reg_date();
                break;
            case 9:
                nameByGroupType = " расчет";
                break;
            case 3:
                nameByGroupType = loanSummaryView.getV_work_sector_name();
                break;






        }

        return  nameByGroupType;
    }






    public long getIdByGroupType(long groupType, PaymentView paymentView)
    {
        long idByGroupType=0;

        switch((short)groupType)
        {
            case 1:
                idByGroupType = paymentView.getV_debtor_region_id();
                break;
            case 2:
                idByGroupType = paymentView.getV_debtor_district_id();
                break;
            case 7:
                idByGroupType = paymentView.getV_debtor_id();
                break;
            case 8:
                idByGroupType = paymentView.getV_loan_id();
                break;
            case 10:
                idByGroupType = paymentView.getV_payment_id();
                break;
            case 3:
                idByGroupType = paymentView.getV_debtor_work_sector_id();
                break;






        }

        return  idByGroupType;
    }

    public String getNameByGroupType(long groupType, PaymentView paymentView)
    {
        String nameByGroupType="";

        switch((short)groupType)
        {
            case 1:
                nameByGroupType = paymentView.getV_region_name();
                break;
            case 2:
                nameByGroupType = paymentView.getV_district_name();
                break;
            case 7:
                nameByGroupType = paymentView.getV_debtor_name();
                break;
            case 8:
                nameByGroupType =paymentView.getV_credit_order_regNumber()
                        + " от "
                        + paymentView.getV_credit_order_regDate()
                        + ", "
                        + paymentView.getV_loan_reg_number()
                        + " от "
                        + paymentView.getV_loan_reg_date();
                break;
            case 10:
                nameByGroupType = " погашение №"+ paymentView.getV_payment_number()+ " от " + paymentView.getV_payment_date();
                break;
            case 3:
                nameByGroupType = paymentView.getV_work_sector_name();
                break;






        }

        return  nameByGroupType;
    }



    public long getIdByGroupType(long groupType, PaymentScheduleView paymentScheduleView)
    {
        long idByGroupType=0;

        switch((short)groupType)
        {
            case 1:
                idByGroupType = paymentScheduleView.getV_debtor_region_id();
                break;
            case 2:
                idByGroupType = paymentScheduleView.getV_debtor_district_id();
                break;
            case 7:
                idByGroupType = paymentScheduleView.getV_debtor_id();
                break;
            case 8:
                idByGroupType = paymentScheduleView.getV_loan_id();
                break;
            case 11:
                idByGroupType = paymentScheduleView.getV_ps_id();
                break;
            case 3:
                idByGroupType = paymentScheduleView.getV_debtor_work_sector_id();
                break;






        }

        return  idByGroupType;
    }

    public String getNameByGroupType(long groupType, PaymentScheduleView paymentScheduleView)
    {
        String nameByGroupType="";

        switch((short)groupType)
        {
            case 1:
                nameByGroupType = paymentScheduleView.getV_region_name();
                break;
            case 2:
                nameByGroupType = paymentScheduleView.getV_district_name();
                break;
            case 7:
                nameByGroupType = paymentScheduleView.getV_debtor_name();
                break;
            case 8:
                nameByGroupType =paymentScheduleView.getV_credit_order_regNumber()
                        + " от "
                        + paymentScheduleView.getV_credit_order_regDate()
                        + ", "
                        + paymentScheduleView.getV_loan_reg_number()
                        + " от "
                        + paymentScheduleView.getV_loan_reg_date();
                break;
            case 9:
                nameByGroupType = " расчет";
                break;
            case 3:
                nameByGroupType = paymentScheduleView.getV_work_sector_name();
                break;
            case 11:
                nameByGroupType = " графики";
                break;






        }

        return  nameByGroupType;
    }




    public long getIdByGroupType(long groupType, CollateralItemView collateralItemView)
    {
        long idByGroupType=0;

        switch((short)groupType)
        {
            case 1:
                idByGroupType = collateralItemView.getV_debtor_region_id();
                break;
            case 2:
                idByGroupType = collateralItemView.getV_debtor_district_id();
                break;
            case 7:
                idByGroupType = collateralItemView.getV_debtor_id();
                break;
            case 3:
                idByGroupType = collateralItemView.getV_debtor_work_sector_id();
                break;

            case 17:
                idByGroupType = collateralItemView.getV_ca_id();
                break;

            case 18:
                idByGroupType = collateralItemView.getV_ci_id();
                break;






        }

        return  idByGroupType;
    }

    public String getNameByGroupType(long groupType, CollateralItemView collateralItemView)
    {
        String nameByGroupType="";

        switch((short)groupType)
        {
            case 1:
                nameByGroupType = collateralItemView.getV_region_name();
                break;
            case 2:
                nameByGroupType = collateralItemView.getV_district_name();
                break;
            case 7:
                nameByGroupType = collateralItemView.getV_debtor_name();
                break;

            case 3:
                nameByGroupType = collateralItemView.getV_work_sector_name();
                break;

            case 17:
                nameByGroupType = collateralItemView.getV_ca_arrestRegNumber()+" ot "+collateralItemView.getV_ca_agreementDate();
                break;

            case 18:
                nameByGroupType = collateralItemView.getV_ci_name();
                break;






        }

        return  nameByGroupType;
    }




    public long getIdByGroupType(long groupType, CollectionPhaseView collectionPhaseView)
    {
        long idByGroupType=0;

        switch((short)groupType)
        {
            case 1:
                idByGroupType = collectionPhaseView.getV_debtor_region_id();
                break;
            case 2:
                idByGroupType = collectionPhaseView.getV_debtor_district_id();
                break;
            case 7:
                idByGroupType = collectionPhaseView.getV_debtor_id();
                break;
            case 3:
                idByGroupType = collectionPhaseView.getV_debtor_work_sector_id();
                break;

            case 19:
                idByGroupType = collectionPhaseView.getV_cp_id();
                break;

            case 20:
                idByGroupType = collectionPhaseView.getV_cph_id();
                break;





        }

        return  idByGroupType;
    }

    public String getNameByGroupType(long groupType, CollectionPhaseView collectionPhaseView)
    {
        String nameByGroupType="";

        switch((short)groupType)
        {
            case 1:
                nameByGroupType = collectionPhaseView.getV_region_name();
                break;
            case 2:
                nameByGroupType = collectionPhaseView.getV_district_name();
                break;
            case 7:
                nameByGroupType = collectionPhaseView.getV_debtor_name();
                break;
            case 3:
                nameByGroupType = collectionPhaseView.getV_work_sector_name();
                break;

            case 19:
                nameByGroupType = "Процедура взыскания";
                break;

            case 20:
                nameByGroupType = "Стадия взыскания";
                break;

        }

        return  nameByGroupType;
    }


    public String getNameByGroupType(long groupType, long id)
    {

        String nameByGroupType="";

        switch((short)groupType)
        {
            case 1:
                nameByGroupType = referenceMap.get("region").get(id) ;

                break;
            case 2:
                nameByGroupType = referenceMap.get("district").get(id);
                break;
            case 12:
                nameByGroupType = referenceMap.get("credit_order").get(id);
                break;
            case 21:
                nameByGroupType = referenceMap.get("applied_entity_status").get(id);
                break;
            case 22:
                nameByGroupType = referenceMap.get("entity_document_status").get(id);
                break;
            case 23:
                nameByGroupType = referenceMap.get("document_package_status").get(id);
                break;

            case 5:
                nameByGroupType = referenceMap.get("supervisor").get(id);
                break;





        }

        return  nameByGroupType;
    }




    public void setupSheetSettings(HSSFSheet Sheet, ReportTemplate reportTemplate)
    {

        try
        {
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

            for (OutputParameter outputParameter: reportTemplate.getOutputParameters())
            {
                switch (outputParameter.getOutputParameterType().toString())
                {
                    case "PAGE_ORIENTATION":
                        Sheet.getPrintSetup().setLandscape((outputParameter.getValue()==1)? true:false);
                        break;

                    case "SHEET_BOTTOM_MARGIN":
                        Sheet.setMargin(HSSFSheet.BottomMargin,outputParameter.getValue());
                        break;
                    case "SHEET_TOP_MARGIN":
                        Sheet.setMargin(HSSFSheet.TopMargin,outputParameter.getValue());
                        break;
                    case "SHEET_RIGHT_MARGIN":
                        Sheet.setMargin(HSSFSheet.RightMargin,outputParameter.getValue());
                        break;
                    case "SHEET_LEFT_MARGIN":
                        Sheet.setMargin(HSSFSheet.LeftMargin,outputParameter.getValue());
                        break;
                    case "SHEET_AUTOBREAKS":
                        Sheet.setAutobreaks((outputParameter.getValue()==1) ? true: false);
                        break;
                    case "SHEET_FIT_WIDTH":
                        ps.setFitWidth((short)outputParameter.getValue());
                        break;
                    case "SHEET_FIT_HEIGHT":
                        ps.setFitHeight((short)outputParameter.getValue());
                        break;
                    case "SHEET_FOOTER_MARGIN":
                        ps.setFooterMargin((short)outputParameter.getValue());
                        break;
                    case "SHEET_HEADER_MARGIN":
                        ps.setHeaderMargin((short)outputParameter.getValue());
                        break;

                    case "SHEET_FOOTER_TEXT":

                        String footer_text = outputParameter.getText();

                        if(footer_text.contains("page()")) footer_text.replace("page()",HSSFFooter.page());
                        if(footer_text.contains("numPages()")) footer_text.replace("numPages()",HSSFFooter.numPages());

                        if(outputParameter.getPosition()==2)
                        {
                            footer.setCenter(footer_text);
                        }
                        else
                        if(outputParameter.getPosition()==1)
                        {
                            footer.setLeft(footer_text);
                        }
                        else
                        {
                            footer.setRight(footer_text);
                        }



                        break;

                    case "SHEET_HEADER_TEXT":

                        String header_text = outputParameter.getText();

                        if(header_text.contains("page()")) header_text.replace("page()",HSSFFooter.page());
                        if(header_text.contains("numPages()")) header_text.replace("numPages()",HSSFFooter.numPages());

                        if(outputParameter.getPosition()==2)
                        {
                            header.setCenter(header_text);
                        }
                        else
                        if(outputParameter.getPosition()==1)
                        {
                            header.setLeft(header_text);
                        }
                        else
                        {
                            header.setRight(header_text);
                        }


                        break;

                    case "DEFAULT_COLUMN_WIDTH":
                        Sheet.setDefaultColumnWidth((int)outputParameter.getValue());
                        break;

                    case "COLUMN_WIDTH":
                        Sheet.setColumnWidth((int)outputParameter.getPosition()-1,(int)outputParameter.getValue()*256);
                        break;

                    case "FONT_HEIGHT":
                        getFontByLevel(outputParameter.getPosition()).setFontHeightInPoints((short)outputParameter.getValue());
                        break;

                    case "FONT_BOLD":
                        getFontByLevel(outputParameter.getPosition()).setBoldweight((outputParameter.getValue()==1)? (short)700:(short)400);
                        break;

                    case "FONT_COLOR":
                        getFontByLevel(outputParameter.getPosition()).setColor((short)outputParameter.getValue());
                        break;

                    case "CELL_FONT":
                        getCellStyleByLevel(outputParameter.getPosition(),outputParameter.getText()).setFont(getFontByLevel((long)outputParameter.getValue()));
                        break;

                    case "CELL_ALIGNMENT":
                        getCellStyleByLevel(outputParameter.getPosition(),outputParameter.getText()).setAlignment((short)outputParameter.getValue());
                        break;

                    case "CELL_BORDER":
                        getCellStyleByLevel(outputParameter.getPosition(),outputParameter.getText()).setBorderBottom((short)outputParameter.getValue());
                        getCellStyleByLevel(outputParameter.getPosition(),outputParameter.getText()).setBorderTop((short)outputParameter.getValue());
                        getCellStyleByLevel(outputParameter.getPosition(),outputParameter.getText()).setBorderLeft((short)outputParameter.getValue());
                        getCellStyleByLevel(outputParameter.getPosition(),outputParameter.getText()).setBorderRight((short)outputParameter.getValue());
                        break;

                    case "CELL_VERTICAL_ALIGNMENT":
                        getCellStyleByLevel(outputParameter.getPosition(),outputParameter.getText()).setVerticalAlignment((short)outputParameter.getValue());
                        break;

                    case "CELL_WRAP_TEXT":
                        getCellStyleByLevel(outputParameter.getPosition(),outputParameter.getText()).setWrapText((outputParameter.getValue() == 1) ? true : false);
                        break;

                    case "CELL_DATA_FORMAT":

                        String cellStyleType = "string";

                        if(outputParameter.getValue() ==2) cellStyleType = "date";
                        else if(outputParameter.getValue() ==3) cellStyleType = "int";
                        else if(outputParameter.getValue() ==4) cellStyleType = "double";
                        else cellStyleType = "string";

                        getCellStyleByLevel(outputParameter.getPosition(),cellStyleType).setDataFormat(format.getFormat(outputParameter.getText()));
                        break;

                    case "CELL_FOREGROUND_COLOR":
                        getCellStyleByLevel(outputParameter.getPosition(),outputParameter.getText()).setFillForegroundColor((short)outputParameter.getValue());
                        break;

                    case "CELL_PATTERN":
                        getCellStyleByLevel(outputParameter.getPosition(),outputParameter.getText()).setFillPattern((short)outputParameter.getValue());
                        break;

                    case "ROW_HEIGHT":
                        rowHeightMap.put((int)outputParameter.getPosition()-1,(float)outputParameter.getValue());
                        break;

                }

            }

        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }

    }

    public HSSFColor getColorByLevel(long level, ReportTemplate reportTemplate)
    {
        switch (String.valueOf(level))
        {
            case "1" : return new HSSFColor.PALE_BLUE();
            case "2" : return new HSSFColor.YELLOW();
            case "3" : return new HSSFColor.WHITE();
            case "4" : return new HSSFColor.WHITE();
            case "5" : return new HSSFColor.WHITE();
            case "6" : return new HSSFColor.WHITE();
            default:return new HSSFColor.WHITE();

        }

    }

    public short getBorderTypeByLevel(long level, ReportTemplate reportTemplate)
    {
        switch (String.valueOf(level))
        {
            case "1" : return HSSFCellStyle.BORDER_THIN;
            case "2" : return HSSFCellStyle.BORDER_THIN;
            case "3" : return HSSFCellStyle.BORDER_THIN;
            case "4" : return HSSFCellStyle.BORDER_THIN;
            case "5" : return HSSFCellStyle.BORDER_THIN;
            case "6" : return HSSFCellStyle.BORDER_THIN;
            default  : return HSSFCellStyle.BORDER_THIN;
        }

    }

    public HSSFFont getFontByLevel(long level, ReportTemplate reportTemplate, HSSFWorkbook WorkBook)
    {
        switch (String.valueOf(level))
        {

            // TITLE
            case "10" :
                HSSFFont         Font10     = WorkBook.createFont();
                Font10.setFontHeightInPoints((short)12);
                Font10.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                Font10.setColor(HSSFColor.BLUE.index);
                return Font10;

            // HEADER
            case "11" :
                HSSFFont         Font11     = WorkBook.createFont();
                Font11.setFontHeightInPoints((short)8);
                Font11.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                return Font11;

            // SUMMARY
            case "12" :
                HSSFFont         Font12     = WorkBook.createFont();
                Font12.setFontHeightInPoints((short)9);
                Font12.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                Font12.setColor(HSSFColor.RED.index);
                return Font12;

            // FOOTER
            case "13" :
                HSSFFont         Font13     = WorkBook.createFont();
                Font13.setFontHeightInPoints((short)12);
                Font13.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                return Font13;

            case "1" :
                HSSFFont         FontGroup1     = WorkBook.createFont();
                FontGroup1.setFontHeightInPoints((short)8);
                FontGroup1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                return FontGroup1;

            case "2" :
                HSSFFont         FontGroup2     = WorkBook.createFont();
                FontGroup2.setFontHeightInPoints((short)8);
                FontGroup2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                return FontGroup2;

            case "3" :
                HSSFFont         FontGroup3     = WorkBook.createFont();
                FontGroup3.setFontHeightInPoints((short)8);
                FontGroup3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                return FontGroup3;

            case "4" :
                HSSFFont         FontGroup4     = WorkBook.createFont();
                FontGroup4.setFontHeightInPoints((short)8);
                FontGroup4.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                return FontGroup4;

            case "5" :
                HSSFFont         FontGroup5     = WorkBook.createFont();
                FontGroup5.setFontHeightInPoints((short)8);
                FontGroup5.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                return FontGroup5;

            case "6" :
                HSSFFont         FontGroup6     = WorkBook.createFont();
                FontGroup6.setFontHeightInPoints((short)8);
                FontGroup6.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                return FontGroup6;

            default:
                HSSFFont         FontGroup     = WorkBook.createFont();
                FontGroup.setFontHeightInPoints((short)8);
                FontGroup.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                return FontGroup;

        }

    }

    public HSSFFont getFontByLevel(long level)
    {
        switch (String.valueOf(level))
        {

            // TITLE
            case "10" :
                return FontTitle;

            // HEADER
            case "11" :
                return FontHeader;

            // SUMMARY
            case "12" :
                return FontSum;

            // FOOTER
            case "13" :
                return FontFooter;

            case "1" :
                return FontGroup1;

            case "2" :
                return FontGroup2;

            case "3" :
                return FontGroup3;

            case "4" :
                return FontGroup4;

            case "5" :
                return FontGroup5;

            case "6" :
                return FontGroup6;

            default:
                return FontGroup6;

        }

    }


    public HSSFCellStyle getCellStyleByLevel(long level, String DataType, ReportTemplate reportTemplate, HSSFWorkbook WorkBook)
    {

        long DataTypeToLong = 0;

        if(DataType.equals("string"))
        {
            DataTypeToLong = 100;
        }
        if(DataType.equals("date"))
        {
            DataTypeToLong = 200;
        }
        if(DataType.equals("int"))
        {
            DataTypeToLong = 300;
        }
        if(DataType.equals("double"))
        {
            DataTypeToLong = 400;
        }

        level+=DataTypeToLong;


        switch (String.valueOf(level))
        {

            // TITLE
            case "110" :
                HSSFCellStyle    CellStyleTitleString     = WorkBook.createCellStyle();
                CellStyleTitleString.setFont(FontTitle);
                CellStyleTitleString.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                return CellStyleTitleString;

            // HEADER
            case "111" :
                HSSFCellStyle    HeaderStringString    = WorkBook.createCellStyle();
                HeaderStringString.setFont(FontHeader);
                HeaderStringString.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                HeaderStringString.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
                HeaderStringString.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                HeaderStringString.setBorderBottom(BorderHeader);
                HeaderStringString.setBorderTop(BorderHeader);
                HeaderStringString.setBorderRight(BorderHeader);
                HeaderStringString.setBorderLeft(BorderHeader);
                HeaderStringString.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                HeaderStringString.setWrapText(true);

                return HeaderStringString;

            // SUMMARY
            case "112" :
                HSSFCellStyle    CellStyleDataSumString    = WorkBook.createCellStyle();
                CellStyleDataSumString.setFont(FontSum);
                CellStyleDataSumString.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                CellStyleDataSumString.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumString.setBorderTop(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumString.setBorderRight(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumString.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumString.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleDataSumString.setWrapText(true);
                CellStyleDataSumString.setFillForegroundColor(HSSFColor.AQUA.index);
                CellStyleDataSumString.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleDataSumString;

            case "312" :

                HSSFCellStyle    CellStyleDataSumInt    = WorkBook.createCellStyle();
                CellStyleDataSumInt.setFont(FontSum);
                CellStyleDataSumInt.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleDataSumInt.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumInt.setBorderTop(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumInt.setBorderRight(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumInt.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumInt.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleDataSumInt.setDataFormat(format.getFormat("#,#0"));
                CellStyleDataSumInt.setWrapText(true);
                CellStyleDataSumInt.setFillForegroundColor(HSSFColor.AQUA.index);
                CellStyleDataSumInt.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleDataSumInt;


            case "412" :
                HSSFCellStyle    CellStyleDataSumDouble    = WorkBook.createCellStyle();
                CellStyleDataSumDouble.setFont(FontSum);
                CellStyleDataSumDouble.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleDataSumDouble.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumDouble.setBorderTop(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumDouble.setBorderRight(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumDouble.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumDouble.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleDataSumDouble.setDataFormat(format.getFormat("#,#0.00"));
                CellStyleDataSumDouble.setWrapText(true);
                CellStyleDataSumDouble.setFillForegroundColor(HSSFColor.AQUA.index);
                CellStyleDataSumDouble.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleDataSumDouble;

            // FOOTER
            case "113" :
                HSSFCellStyle    CellStyleFooterString    = WorkBook.createCellStyle();
                CellStyleFooterString.setFont(FontFooter);
                CellStyleFooterString.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                CellStyleFooterString.setFillBackgroundColor(HSSFColor.AQUA.index);
                return CellStyleFooterString;

            case "101" :
                HSSFCellStyle    CellStyleGroup1String    = WorkBook.createCellStyle();
                CellStyleGroup1String.setFont(FontGroup1);
                CellStyleGroup1String.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                CellStyleGroup1String.setBorderBottom(BorderGroup1);
                CellStyleGroup1String.setBorderTop(BorderGroup1);
                CellStyleGroup1String.setBorderRight(HSSFCellStyle.BORDER_THIN);
                CellStyleGroup1String.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                CellStyleGroup1String.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup1String.setWrapText(true);
                CellStyleGroup1String.setFillForegroundColor(ColorGroup1.getIndex());
                CellStyleGroup1String.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup1String;

            case "301" :
                HSSFCellStyle    CellStyleGroup1Int    = WorkBook.createCellStyle();
                CellStyleGroup1Int.setFont(FontGroup1);
                CellStyleGroup1Int.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup1Int.setBorderBottom(BorderGroup1);
                CellStyleGroup1Int.setBorderTop(BorderGroup1);
                CellStyleGroup1Int.setBorderRight(BorderGroup1);
                CellStyleGroup1Int.setBorderLeft(BorderGroup1);
                CellStyleGroup1Int.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup1Int.setDataFormat(format.getFormat("#,#0"));
                CellStyleGroup1Int.setWrapText(true);
                CellStyleGroup1Int.setFillForegroundColor(ColorGroup1.getIndex());
                CellStyleGroup1Int.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup1Int;

            case "401" :

                HSSFCellStyle    CellStyleGroup1Double    = WorkBook.createCellStyle();
                CellStyleGroup1Double.setFont(FontGroup1);
                CellStyleGroup1Double.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup1Double.setBorderBottom(BorderGroup1);
                CellStyleGroup1Double.setBorderTop(BorderGroup1);
                CellStyleGroup1Double.setBorderRight(BorderGroup1);
                CellStyleGroup1Double.setBorderLeft(BorderGroup1);
                CellStyleGroup1Double.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup1Double.setDataFormat(format.getFormat("#,#0.00"));
                CellStyleGroup1Double.setWrapText(true);
                CellStyleGroup1Double.setFillForegroundColor(ColorGroup1.getIndex());
                CellStyleGroup1Double.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup1Double;



            case "102" :
                HSSFCellStyle    CellStyleGroup2String    = WorkBook.createCellStyle();
                CellStyleGroup2String.setFont(FontGroup1);
                CellStyleGroup2String.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                CellStyleGroup2String.setBorderBottom(BorderGroup2);
                CellStyleGroup2String.setBorderTop(BorderGroup2);
                CellStyleGroup2String.setBorderRight(BorderGroup2);
                CellStyleGroup2String.setBorderLeft(BorderGroup2);
                CellStyleGroup2String.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup2String.setWrapText(true);
                CellStyleGroup2String.setFillForegroundColor(ColorGroup2.getIndex());
                CellStyleGroup2String.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup2String;

            case "302" :
                HSSFCellStyle    CellStyleGroup2Int    = WorkBook.createCellStyle();
                CellStyleGroup2Int.setFont(FontGroup2);
                CellStyleGroup2Int.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup2Int.setBorderBottom(BorderGroup2);
                CellStyleGroup2Int.setBorderTop(BorderGroup2);
                CellStyleGroup2Int.setBorderRight(BorderGroup2);
                CellStyleGroup2Int.setBorderLeft(BorderGroup2);
                CellStyleGroup2Int.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup2Int.setDataFormat(format.getFormat("#,#0"));
                CellStyleGroup2Int.setWrapText(true);
                CellStyleGroup2Int.setFillForegroundColor(ColorGroup2.getIndex());
                CellStyleGroup2Int.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup2Int;

            case "402" :

                HSSFCellStyle    CellStyleGroup2Double    = WorkBook.createCellStyle();
                CellStyleGroup2Double.setFont(FontGroup2);
                CellStyleGroup2Double.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup2Double.setBorderBottom(BorderGroup2);
                CellStyleGroup2Double.setBorderTop(BorderGroup2);
                CellStyleGroup2Double.setBorderRight(BorderGroup2);
                CellStyleGroup2Double.setBorderLeft(BorderGroup2);
                CellStyleGroup2Double.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup2Double.setDataFormat(format.getFormat("#,#0.00"));
                CellStyleGroup2Double.setWrapText(true);
                CellStyleGroup2Double.setFillForegroundColor(ColorGroup2.getIndex());
                CellStyleGroup2Double.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup2Double;













            case "103" :
                HSSFCellStyle    CellStyleGroup3String    = WorkBook.createCellStyle();
                CellStyleGroup3String.setFont(FontGroup3);
                CellStyleGroup3String.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                CellStyleGroup3String.setBorderBottom(BorderGroup3);
                CellStyleGroup3String.setBorderTop(BorderGroup3);
                CellStyleGroup3String.setBorderRight(BorderGroup3);
                CellStyleGroup3String.setBorderLeft(BorderGroup3);
                CellStyleGroup3String.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup3String.setWrapText(true);
                CellStyleGroup3String.setFillForegroundColor(ColorGroup3.getIndex());
                CellStyleGroup3String.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup3String;

            case "303" :
                HSSFCellStyle    CellStyleGroup3Int    = WorkBook.createCellStyle();
                CellStyleGroup3Int.setFont(FontGroup3);
                CellStyleGroup3Int.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup3Int.setBorderBottom(BorderGroup3);
                CellStyleGroup3Int.setBorderTop(BorderGroup3);
                CellStyleGroup3Int.setBorderRight(BorderGroup3);
                CellStyleGroup3Int.setBorderLeft(BorderGroup3);
                CellStyleGroup3Int.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup3Int.setDataFormat(format.getFormat("#,#0"));
                CellStyleGroup3Int.setWrapText(true);
                CellStyleGroup3Int.setFillForegroundColor(ColorGroup3.getIndex());
                CellStyleGroup3Int.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup3Int;

            case "403" :

                HSSFCellStyle    CellStyleGroup3Double    = WorkBook.createCellStyle();
                CellStyleGroup3Double.setFont(FontGroup3);
                CellStyleGroup3Double.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup3Double.setBorderBottom(BorderGroup3);
                CellStyleGroup3Double.setBorderTop(BorderGroup3);
                CellStyleGroup3Double.setBorderRight(BorderGroup3);
                CellStyleGroup3Double.setBorderLeft(BorderGroup3);
                CellStyleGroup3Double.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup3Double.setDataFormat(format.getFormat("#,#0.00"));
                CellStyleGroup3Double.setWrapText(true);
                CellStyleGroup3Double.setFillForegroundColor(ColorGroup3.getIndex());
                CellStyleGroup3Double.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup3Double;







            case "104" :
                HSSFCellStyle    CellStyleGroup4String    = WorkBook.createCellStyle();
                CellStyleGroup4String.setFont(FontGroup4);
                CellStyleGroup4String.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                CellStyleGroup4String.setBorderBottom(BorderGroup4);
                CellStyleGroup4String.setBorderTop(BorderGroup4);
                CellStyleGroup4String.setBorderRight(BorderGroup4);
                CellStyleGroup4String.setBorderLeft(BorderGroup4);
                CellStyleGroup4String.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup4String.setWrapText(true);
                CellStyleGroup4String.setFillForegroundColor(ColorGroup4.getIndex());
                CellStyleGroup4String.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup4String;

            case "304" :
                HSSFCellStyle    CellStyleGroup4Int    = WorkBook.createCellStyle();
                CellStyleGroup4Int.setFont(FontGroup4);
                CellStyleGroup4Int.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup4Int.setBorderBottom(BorderGroup4);
                CellStyleGroup4Int.setBorderTop(BorderGroup4);
                CellStyleGroup4Int.setBorderRight(BorderGroup4);
                CellStyleGroup4Int.setBorderLeft(BorderGroup4);
                CellStyleGroup4Int.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup4Int.setDataFormat(format.getFormat("#,#0"));
                CellStyleGroup4Int.setWrapText(true);
                CellStyleGroup4Int.setFillForegroundColor(ColorGroup4.getIndex());
                CellStyleGroup4Int.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup4Int;

            case "404" :

                HSSFCellStyle    CellStyleGroup4Double    = WorkBook.createCellStyle();
                CellStyleGroup4Double.setFont(FontGroup4);
                CellStyleGroup4Double.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup4Double.setBorderBottom(BorderGroup4);
                CellStyleGroup4Double.setBorderTop(BorderGroup4);
                CellStyleGroup4Double.setBorderRight(BorderGroup4);
                CellStyleGroup4Double.setBorderLeft(BorderGroup4);
                CellStyleGroup4Double.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup4Double.setDataFormat(format.getFormat("#,#0.00"));
                CellStyleGroup4Double.setWrapText(true);
                CellStyleGroup4Double.setFillForegroundColor(ColorGroup4.getIndex());
                CellStyleGroup4Double.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup4Double;

            case "105" :
                HSSFCellStyle    CellStyleGroup5String    = WorkBook.createCellStyle();
                CellStyleGroup5String.setFont(FontGroup5);
                CellStyleGroup5String.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                CellStyleGroup5String.setBorderBottom(BorderGroup5);
                CellStyleGroup5String.setBorderTop(BorderGroup5);
                CellStyleGroup5String.setBorderRight(BorderGroup5);
                CellStyleGroup5String.setBorderLeft(BorderGroup5);
                CellStyleGroup5String.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup5String.setWrapText(true);
                CellStyleGroup5String.setFillForegroundColor(ColorGroup5.getIndex());
                CellStyleGroup5String.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup5String;

            case "305" :
                HSSFCellStyle    CellStyleGroup5Int    = WorkBook.createCellStyle();
                CellStyleGroup5Int.setFont(FontGroup5);
                CellStyleGroup5Int.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup5Int.setBorderBottom(BorderGroup5);
                CellStyleGroup5Int.setBorderTop(BorderGroup5);
                CellStyleGroup5Int.setBorderRight(BorderGroup5);
                CellStyleGroup5Int.setBorderLeft(BorderGroup5);
                CellStyleGroup5Int.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup5Int.setDataFormat(format.getFormat("#,#0"));
                CellStyleGroup5Int.setWrapText(true);
                CellStyleGroup5Int.setFillForegroundColor(ColorGroup5.getIndex());
                CellStyleGroup5Int.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup5Int;

            case "405" :

                HSSFCellStyle    CellStyleGroup5Double    = WorkBook.createCellStyle();
                CellStyleGroup5Double.setFont(FontGroup5);
                CellStyleGroup5Double.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup5Double.setBorderBottom(BorderGroup5);
                CellStyleGroup5Double.setBorderTop(BorderGroup5);
                CellStyleGroup5Double.setBorderRight(BorderGroup5);
                CellStyleGroup5Double.setBorderLeft(BorderGroup5);
                CellStyleGroup5Double.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup5Double.setDataFormat(format.getFormat("#,#0.00"));
                CellStyleGroup5Double.setWrapText(true);
                CellStyleGroup5Double.setFillForegroundColor(ColorGroup5.getIndex());
                CellStyleGroup5Double.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup5Double;

            case "106" :
                HSSFCellStyle    CellStyleGroup6String    = WorkBook.createCellStyle();
                CellStyleGroup6String.setFont(FontGroup6);
                CellStyleGroup6String.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                CellStyleGroup6String.setBorderBottom(BorderGroup6);
                CellStyleGroup6String.setBorderTop(BorderGroup6);
                CellStyleGroup6String.setBorderRight(BorderGroup6);
                CellStyleGroup6String.setBorderLeft(BorderGroup6);
                CellStyleGroup6String.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup6String.setWrapText(true);
                CellStyleGroup6String.setFillForegroundColor(ColorGroup6.getIndex());
                CellStyleGroup6String.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup6String;

            case "306" :
                HSSFCellStyle    CellStyleGroup6Int    = WorkBook.createCellStyle();
                CellStyleGroup6Int.setFont(FontGroup6);
                CellStyleGroup6Int.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup6Int.setBorderBottom(BorderGroup6);
                CellStyleGroup6Int.setBorderTop(BorderGroup6);
                CellStyleGroup6Int.setBorderRight(BorderGroup6);
                CellStyleGroup6Int.setBorderLeft(BorderGroup6);
                CellStyleGroup6Int.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup6Int.setDataFormat(format.getFormat("#,#0"));
                CellStyleGroup6Int.setWrapText(true);
                CellStyleGroup6Int.setFillForegroundColor(ColorGroup6.getIndex());
                CellStyleGroup6Int.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup6Int;

            case "406" :

                HSSFCellStyle    CellStyleGroup6Double    = WorkBook.createCellStyle();
                CellStyleGroup6Double.setFont(FontGroup6);
                CellStyleGroup6Double.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup6Double.setBorderBottom(BorderGroup6);
                CellStyleGroup6Double.setBorderTop(BorderGroup6);
                CellStyleGroup6Double.setBorderRight(BorderGroup6);
                CellStyleGroup6Double.setBorderLeft(BorderGroup6);
                CellStyleGroup6Double.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup6Double.setDataFormat(format.getFormat("#,#0.00"));
                CellStyleGroup6Double.setWrapText(true);
                CellStyleGroup6Double.setFillForegroundColor(ColorGroup6.getIndex());
                CellStyleGroup6Double.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup6Double;

            default:
                HSSFCellStyle    CellStyle    = WorkBook.createCellStyle();
                CellStyle.setFont(FontGroup6);
                CellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyle.setBorderBottom(BorderGroup6);
                CellStyle.setBorderTop(BorderGroup6);
                CellStyle.setBorderRight(BorderGroup6);
                CellStyle.setBorderLeft(BorderGroup6);
                CellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyle.setDataFormat(format.getFormat("#,#0.00"));
                CellStyle.setWrapText(true);
                CellStyle.setFillForegroundColor(ColorGroup6.getIndex());
                CellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyle;

        }

    }

    public HSSFCellStyle getCellStyleByLevel(long level, String DataType)
    {

        long DataTypeToLong = 0;

        if(DataType.equals("string"))
        {
            DataTypeToLong = 100;
        }
        if(DataType.equals("date"))
        {
            DataTypeToLong = 200;
        }
        if(DataType.equals("int"))
        {
            DataTypeToLong = 300;
        }
        if(DataType.equals("double"))
        {
            DataTypeToLong = 400;
        }

        level+=DataTypeToLong;


        switch (String.valueOf(level))
        {

            // TITLE
            case "110" : return TitleString;
            case "210" : return TitleDate;
            case "310" : return TitleInt;
            case "410" : return TitleDouble;


            // HEADER
            case "111" : return HeaderString;
            case "211" : return HeaderDate;
            case "321" : return HeaderInt;
            case "411" : return HeaderDouble;


            // SUMMARY
            case "112" : return SumString;
            case "212" : return SumDate;
            case "312" : return SumInt;
            case "412" : return SumDouble;

            // FOOTER
            case "113" : return FooterString;

            case "101" : return Group1String;
            case "201" : return Group1Date;
            case "301" : return Group1Int;
            case "401" : return Group1Double;

            case "102" : return Group2String;
            case "202" : return Group2Date;
            case "302" : return Group2Int;
            case "402" : return Group2Double;

            case "103" : return Group3String;
            case "203" : return Group3Date;
            case "303" : return Group3Int;
            case "403" : return Group3Double;


            case "104" : return Group4String;
            case "204" : return Group4Date;
            case "304" : return Group4Int;
            case "404" : return Group4Double;


            case "105" : return Group5String;
            case "205" : return Group5Date;
            case "305" : return Group5Int;
            case "405" : return Group5Double;


            case "106" : return Group6String;
            case "206" : return Group6Date;
            case "306" : return Group6Int;
            case "406" : return Group6Double;

            default:
                return Group6String;

        }

    }




    public String formatText(String originText, ReportTemplate reportTemplate)
    {
        if(originText.contains("(=onDate=)")) originText.replaceAll("(=onDate=)",getOnDate(reportTemplate).toString());

        return originText;
    }








    public LinkedHashMap<String,List<Long>> getParametersByTemplate(ReportTemplate reportTemplate,List<Long> groupIds)
    {
        LinkedHashMap<String,List<Long>> parameterS = new LinkedHashMap<String,List<Long>>();

        for (FilterParameter filterParameter: reportTemplate.getFilterParameters())
        {

            if(filterParameter.getFilterParameterType().name()=="OBJECT_LIST")
            {
                ObjectList objectList = filterParameter.getObjectList();

                List<Long> Ids = new ArrayList<>();

                for (ObjectListValue objectListValue: objectList.getObjectListValues())
                {
                    Ids.add(Long.parseLong(objectListValue.getName()));
                }

                parameterS.put(this.getParameterTypeNameById(String.valueOf(objectList.getObjectTypeId())),Ids);
            }

            if(this.getOnDate(reportTemplate)!=null)
            {
                List<Long> Ids = new ArrayList<>();

                try
                {
                    Ids.add(this.getOnDate(reportTemplate).getTime());
                }
                catch ( Exception ex )
                {
                    System.out.println(ex);
                }

                parameterS.put("onDate",Ids);
            }

            parameterS.put("orderBy",groupIds);


        }

        return parameterS;
    }

    public void applyParameters(LinkedHashMap<String,List<Long>> parameters, Criteria criteria)
    {
        for (Map.Entry<String, List<Long>> parameterInLoop : parameters.entrySet())
        {
            String parameterType = parameterInLoop.getKey();

            List<Long> ids = parameterInLoop.getValue();

            if(parameterType.contains("in"))
            {
                String propertyName = parameterType.replace("in","");
                criteria.add(Restrictions.in(propertyName,ids));
            }

            if(parameterType.contains("orderBy"))
            {

                for (Long id:ids)
                {
                    criteria.addOrder(Order.asc(this.getParameterTypeNameById(id.toString())));
                }
            }
        }

    }





    public void createRow(HSSFSheet Sheet, ContentParameter contentParameter)
    {
        try
        {
            int row_from = (contentParameter.getRow_from()>0) ? contentParameter.getRow_from()-1 : RowCount;
            int row_to   = (contentParameter.getRow_to()>0) ? contentParameter.getRow_to()-1 : RowCount;

            for( int rowCount=row_from; rowCount <= row_to; rowCount++ )
            {
                Row = Sheet.createRow(rowCount);
                Row.setRowNum(( short ) rowCount);

                if(rowHeightMap.get(rowCount)!=null)
                {
                    Row.setHeightInPoints(rowHeightMap.get(rowCount).floatValue());
                }

            }


        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }


    }

    public void drawCell(ReportTemplate reportTemplate, HSSFSheet Sheet, ContentParameter contentParameter , ReportData reportData)
    {
        try
        {
            int row_from = (contentParameter.getRow_from()>0) ? contentParameter.getRow_from()-1 : RowCount;
            int row_to   = (contentParameter.getRow_to()>0) ? contentParameter.getRow_to()-1 : RowCount;
            int col_from = (contentParameter.getCol_from()>0) ? contentParameter.getCol_from()-1 : ColumnCount;
            int col_to   = (contentParameter.getCol_to()>0) ? contentParameter.getCol_to()-1 : ColumnCount;

            if(row_to > lastRowCount) lastRowCount = row_to;

            if(!(row_from == row_to && col_from == col_to))
            {
                Sheet.addMergedRegion(new CellRangeAddress(row_from,row_to,col_from,col_to));
            }

            if(contentParameter.getRowType().name()=="PAGE_TITLE" || contentParameter.getRowType().name()=="TABLE_HEADER")
            {
                Cell = Sheet.getRow(row_from).createCell((int)col_from, HSSFCell.CELL_TYPE_STRING);

                if(contentParameter.getRowType().name()=="PAGE_TITLE")
                        Cell.setCellStyle(TitleString);
                else    Cell.setCellStyle(HeaderString);

                Cell.setCellValue(contentParameter.getConstantText().contains("(=") ? formatText(contentParameter.getConstantText(),reportTemplate) : contentParameter.getConstantText());
            }
            else
            {

                long level=1;

                if(contentParameter.getPosition()>0)
                {
                    Cell = Row.createCell(contentParameter.getPosition()-1);
                }
                else
                {
                    Cell = Row.createCell(ColumnCount);
                }


                Object object = null;

                if(contentParameter.getContentType().name()!="CONSTANT")
                {
                    String sMethodName = "get"+contentParameter.getFieldName().substring(0, 1).toUpperCase()+contentParameter.getFieldName().substring(1, contentParameter.getFieldName().length());

                    reportData.getClass().cast(reportData);
                    object = reportData.getClass().getMethod(sMethodName,null).invoke(reportData);


                }

                switch (contentParameter.getRowType().toString())
                {
                    case "TABLE_SUM": level = 12;break;
                    case "TABLE_GROUP1": level = 1;break;
                    case "TABLE_GROUP2": level = 2;break;
                    case "TABLE_GROUP3": level = 3;break;
                    case "TABLE_GROUP4": level = 4;break;
                    case "TABLE_GROUP5": level = 5;break;
                    case "TABLE_GROUP6": level = 6;break;
                }

                switch (contentParameter.getCellType().toString())
                {
                    case "TEXT" :
                        Cell.setCellStyle(getCellStyleByLevel(level,"string"));
                        if(object==null) object = contentParameter.getConstantText();
                        Cell.setCellValue(String.valueOf(object));

                        if(object.toString().matches("[0-9]+")&&contentParameter.getConstantInt()>0)
                        {
                            Cell.setCellValue(getNameByGroupType((long)contentParameter.getConstantInt(), Long.parseLong(object.toString())));
                        }

                        break;
                    case "DATE" :
                        Cell.setCellStyle(getCellStyleByLevel(level,"date"));
                        if(object==null) object = contentParameter.getConstantDate();
                        Cell.setCellValue(Date.parse(object.toString()));
                        break;
                    case "INTEGER" :
                        Cell.setCellStyle(getCellStyleByLevel(level,"int"));
                        if(object==null) object = contentParameter.getConstantInt();
                        Cell.setCellValue(Integer.valueOf(object.toString()));
                        break;
                    case "DOUBLE" :
                        Cell.setCellStyle(getCellStyleByLevel(level,"double"));
                        if(object==null) object = contentParameter.getConstantValue();
                        Cell.setCellValue(Double.valueOf(object.toString()));
                        break;
                    default :
                        Cell.setCellStyle(getCellStyleByLevel(level,"string"));
                        if(object==null) object = contentParameter.getConstantText();
                        Cell.setCellValue(String.valueOf(object));
                        break;
                }

                ColumnCount++;
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }


    }


    public void drawTitle(ReportTemplate reportTemplate, HSSFSheet Sheet, ReportData reportData )
    {

        RowCount = 0;
        ColumnCount = 0;

        try
        {
            for (ContentParameter contentParameter: reportTemplate.getContentParameters())
            {
                if(contentParameter.getRowType().name()=="PAGE_TITLE")
                {
                    createRow(Sheet,contentParameter);
                    drawCell(reportTemplate,Sheet,contentParameter,reportData);
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }

        RowCount = lastRowCount;
        ColumnCount = 0;
    }

    public void drawHeader(ReportTemplate reportTemplate, HSSFSheet Sheet, ReportData reportData )
    {
        RowCount = lastRowCount;
        ColumnCount = 0;

        try
        {
            for (ContentParameter contentParameter: reportTemplate.getContentParameters())
            {
                if(contentParameter.getRowType().name()=="TABLE_HEADER")
                {
                    if(contentParameter.getPosition()==1) createRow(Sheet,contentParameter);

                    drawCell(reportTemplate,Sheet,contentParameter,reportData);
                }

            }

        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }


        RowCount = lastRowCount;
        ColumnCount = 0;


    }

    public void drawSumRow(ReportTemplate reportTemplate, HSSFSheet Sheet, ReportData reportData )
    {

        ColumnCount=0;
        RowCount++;

        Row = Sheet.createRow(RowCount);
        Row.setRowNum(( short ) RowCount);
        Row.setHeightInPoints(20);

        try
        {
            for (ContentParameter contentParameter: reportTemplate.getContentParameters())
            {
                if(contentParameter.getRowType().name()=="TABLE_SUM")
                {
                    drawCell(reportTemplate,Sheet,contentParameter,reportData);
                }
            }

        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    public void drawGroupRow(ReportTemplate reportTemplate, HSSFSheet Sheet, ReportData reportData, long level )
    {

        ColumnCount=0;
        RowCount++;

        Row = Sheet.createRow(RowCount);
        Row.setRowNum(( short ) RowCount);

        try
        {
            for (ContentParameter contentParameter: reportTemplate.getContentParameters())
            {
                if(contentParameter.getRowType().name().contains("TABLE_GROUP"+String.valueOf(level)))
                {
                    drawCell(reportTemplate,Sheet,contentParameter,reportData);
                }
            }

        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }

    }

}

