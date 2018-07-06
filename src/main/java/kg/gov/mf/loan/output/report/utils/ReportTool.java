package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.admin.org.model.District;
import kg.gov.mf.loan.admin.org.model.Region;
import kg.gov.mf.loan.admin.org.service.DistrictService;
import kg.gov.mf.loan.admin.org.service.RegionService;
import kg.gov.mf.loan.manage.model.debtor.WorkSector;
import kg.gov.mf.loan.manage.model.entitydocument.EntityDocument;
import kg.gov.mf.loan.manage.model.loan.LoanType;
import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.ReferenceViewService;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReportTool
{

    @Autowired
    ReferenceViewService referenceViewService;


    ArrayList<ReferenceView> referenceViews = new ArrayList<ReferenceView>();


    Map<Long,String> regionMap = new HashMap<Long,String>();
    Map<Long,String> districtMap = new HashMap<Long,String>();
    Map<Long,String> workSectorMap = new HashMap<Long,String>();
    Map<Long,String> loanTypeMap = new HashMap<Long,String>();
    Map<Long,String> supervisorMap = new HashMap<Long,String>();
    Map<Long,String> departmentMap = new HashMap<Long,String>();
    Map<Long,String> creditOrderMap = new HashMap<Long,String>();

    public void initReference()
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        referenceViews.addAll(this.referenceViewService.findAll());

        for (ReferenceView referenceView:referenceViews)
        {
            switch (referenceView.getList_type())
            {
                case "region": regionMap.put(referenceView.getId(),referenceView.getName()); break;
                case "district": districtMap.put(referenceView.getId(),referenceView.getName()); break;
                case "work_sector": workSectorMap.put(referenceView.getId(),referenceView.getName()); break;
                case "loan_type": loanTypeMap.put(referenceView.getId(),referenceView.getName()); break;
                case "supervisor": supervisorMap.put(referenceView.getId(),referenceView.getName()); break;
                case "department": departmentMap.put(referenceView.getId(),referenceView.getName()); break;
                case "credit_order": creditOrderMap.put(referenceView.getId(),referenceView.getName()); break;
            }

        }
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
                nameByGroupType = regionMap.get(entityDocumentView.getV_owner_region_id());
                break;
            case 2:
                nameByGroupType = districtMap.get(entityDocumentView.getV_owner_district_id());
                break;
            case 12:
                nameByGroupType = creditOrderMap.get(entityDocumentView.getV_co_id());
                break;
            case 13:
                nameByGroupType = entityDocumentView.getV_owner_name();
                break;
            case 14:
                nameByGroupType = " Список получателей № "+entityDocumentView.getV_ael_listNumber()+" от "+entityDocumentView.getV_ael_listDate();
                break;
            case 15:
                nameByGroupType = " Пакет документации == "+entityDocumentView.getV_document_package_name();
                break;
            case 16:
                nameByGroupType = " Документ == "+entityDocumentView.getV_entity_document_name();
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


    public void setupSheetSettings(HSSFSheet Sheet, ReportTemplate reportTemplate)
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

        footer.setRight( "Страница " + HSSFFooter.page() + " из " + HSSFFooter.numPages() );
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

}
