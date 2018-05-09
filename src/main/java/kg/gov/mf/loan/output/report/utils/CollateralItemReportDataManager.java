package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.CollateralItemViewService;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class CollateralItemReportDataManager {
	
	/**
     * Report GENERATION TOOL
     */

    @Autowired
    LoanViewService loanViewService;

    @Autowired
    CollateralItemViewService collateralItemViewService;

    public CollateralItemReportData getReportDataGrouped(CollateralItemReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        Set<CollateralItemView> collateralItemViews =  new HashSet<CollateralItemView>();

        Date onDate = this.getOnDate(reportTemplate);

        LinkedHashMap<String,List<Long>> parameterS = new LinkedHashMap<String,List<Long>>();

        List<Long> groupIds = new ArrayList<>();

        groupIds.add(getGroupType(reportTemplate,1));
        groupIds.add(getGroupType(reportTemplate,2));
        groupIds.add(getGroupType(reportTemplate,3));
        groupIds.add((long) 10);
        groupIds.add((long) 11);

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

                parameterS.put(getParameterTypeNameById(String.valueOf(objectList.getObjectTypeId())),Ids);
            }

            if(filterParameter.getFilterParameterType().name()=="CONTENT_COMPARE")
            {
                List<Long> Ids = new ArrayList<>();

                String string = filterParameter.getComparedValue();
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);

                try
                {
                    Date date = format.parse(string);
                    Ids.add(date.getTime());
                }
                catch ( Exception ex )
                {
                    System.out.println(ex);
                }

                if(Ids.size()>0)
                {
                    if(filterParameter.getComparator().toString()=="AFTER")
                        parameterS.put("paymentDateFrom",Ids);
                    if(filterParameter.getComparator().toString()=="BEFORE")
                        parameterS.put("paymentDateTo",Ids);
                }
            }



            parameterS.put("orderBy",groupIds);

        }

        long groupAtype = getGroupType(reportTemplate,1);
        long groupBtype = getGroupType(reportTemplate,2);
        long groupCtype = getGroupType(reportTemplate,3);
        long groupDtype = getGroupType(reportTemplate,10);
        long groupEtype = getGroupType(reportTemplate,11);



        // initial filter by report filter parameters

        reportData.getCollateralItemViews().addAll(collateralItemViewService.findByParameter(parameterS));


        reportData.setOnDate(new java.sql.Date(onDate.getTime()));

        Set<Long> groupAIds = new HashSet<Long>();

        ArrayList<CollateralItemView> allCollateralItemViews = new ArrayList<CollateralItemView>();

        for (CollateralItemView collateralItemViewInLoop: reportData.getCollateralItemViews()
             )
        {
//            System.out.println(
//                    collateralItemViewInLoop.getV_debtor_region_id() +" "+
//                    collateralItemViewInLoop.getV_debtor_district_id()+" "+
//                    collateralItemViewInLoop.getV_debtor_id()+" "+
//                    collateralItemViewInLoop.getV_ca_id()+" "+
//                            collateralItemViewInLoop.getV_ci_id());


//            System.out.println(
//                    collateralItemViewInLoop.getV_region_name() +" "+
//                            collateralItemViewInLoop.getV_district_name()+" "+
//                            collateralItemViewInLoop.getV_debtor_name()+" "+
//                            collateralItemViewInLoop.getV_credit_order_regNumber()+ " от "+
//                            collateralItemViewInLoop.getV_credit_order_regDate()+ ", "+
//                            collateralItemViewInLoop.getV_loan_reg_number()+ " от " +
//                            collateralItemViewInLoop.getV_loan_reg_date()+" "+
//                            collateralItemViewInLoop.getV_payment_number()+" "+
//                            collateralItemViewInLoop.getV_payment_date()+" "+
//                            collateralItemViewInLoop.getV_payment_total_amount());
        }

        return groupifyData(reportData,groupIds);
    }

    public CollateralItemReportData groupifyData(CollateralItemReportData reportData, List<Long> groupIds)
    {

        long groupAid=0;
        long groupBid=0;
        long groupCid=0;
        long groupDid=0;
        long groupEid=0;
        long groupFid=0;

        CollateralItemReportData childA = null;
        CollateralItemReportData childB = null;
        CollateralItemReportData childC = null;
        CollateralItemReportData childD = null;
        CollateralItemReportData childE = null;
        CollateralItemReportData childF = null;


        for (CollateralItemView collateralItemView:reportData.getCollateralItemViews())
        {
            if(this.getIdByGroupType(groupIds.get(0),collateralItemView)!=groupAid)
            {
                childA = reportData.addChild();
                childA.setName(this.getNameByGroupType(1,collateralItemView));
                childA.setLevel((short)1);

                groupAid=this.getIdByGroupType(groupIds.get(0),collateralItemView);
            }

            if(this.getIdByGroupType(groupIds.get(1),collateralItemView)!=groupBid)
            {
                childB = childA.addChild();
                childB.setName(this.getNameByGroupType(2,collateralItemView));
                childB.setLevel((short)2);

                groupBid=this.getIdByGroupType(groupIds.get(1),collateralItemView);
            }

            if(this.getIdByGroupType(groupIds.get(2),collateralItemView)!=groupCid)
            {
                childC = childB.addChild();
                childC.setName(this.getNameByGroupType(3,collateralItemView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                groupCid=this.getIdByGroupType(groupIds.get(2),collateralItemView);
            }

            if(this.getIdByGroupType(groupIds.get(3),collateralItemView)!=groupDid)
            {
                CollateralItemView lv = collateralItemView;

                childD = childC.addChild();
                childD.setName(this.getNameByGroupType(10,collateralItemView));
                childD.setLevel((short)4);


                childD.setLevel((short)4);

                childD.setDetailsCount(1);
                childC.setDetailsCount(childC.getDetailsCount()+1);
                childA.setDetailsCount(childA.getDetailsCount()+1);
                childB.setDetailsCount(childB.getDetailsCount()+1);
                reportData.setDetailsCount(reportData.getDetailsCount()+1);


                childD.setID(lv.getV_ca_id());

                if(lv.getV_ca_agreementDate()!=null)
                    childD.setName("Договор о залоге "+lv.getV_ca_agreementNumber()+" от "+lv.getV_ca_agreementDate());
                else childD.setName("Договор о залоге "+lv.getV_ca_agreementNumber());


                if(lv.getV_ca_agreementDate()!=null)
                childD.setCollateralAgreementDate(new java.sql.Date(lv.getV_ca_agreementDate().getTime()));
                childD.setCollateralAgreementNumber(lv.getV_ca_agreementNumber());

                if(lv.getV_ca_arrestRegDate()!=null)
                childD.setCollateralArrestRegDate(new java.sql.Date(lv.getV_ca_arrestRegDate().getTime()));
                childD.setCollateralArrestRegNumber(lv.getV_ca_arrestRegNumber());

                if(lv.getV_ca_collateralOfficeRegDate()!=null)
                childD.setCollateralOfficeRegDate(new java.sql.Date(lv.getV_ca_collateralOfficeRegDate().getTime()));
                childD.setCollateralOfficeRegNumber(lv.getV_ca_collateralOfficeRegNumber());

                if(lv.getV_ca_notaryOfficeRegDate()!=null)
                childD.setCollateralNotaryOfficeRegDate(new java.sql.Date(lv.getV_ca_notaryOfficeRegDate().getTime()));
                else
                    {
                        System.out.println("asdf=="+lv.getV_ci_id());
                }
                childD.setCollateralNotaryOfficeRegNumber(lv.getV_ca_notaryOfficeRegNumber());








                groupDid=this.getIdByGroupType(groupIds.get(3),collateralItemView);
            }

            if(this.getIdByGroupType(groupIds.get(4),collateralItemView)!=groupEid)
            {
                CollateralItemView pv = collateralItemView;

                childE = childD.addChild();
                childE.setName(this.getNameByGroupType(11,collateralItemView));
                childE.setLevel((short)5);


                if(pv.getV_ca_agreementDate()!=null)
                    childE.setCollateralAgreementDate(new java.sql.Date(pv.getV_ca_agreementDate().getTime()));
                childE.setCollateralAgreementNumber(pv.getV_ca_agreementNumber());

                if(pv.getV_ca_arrestRegDate()!=null)
                    childE.setCollateralArrestRegDate(new java.sql.Date(pv.getV_ca_arrestRegDate().getTime()));
                childE.setCollateralArrestRegNumber(pv.getV_ca_arrestRegNumber());

                if(pv.getV_ca_collateralOfficeRegDate()!=null)
                    childE.setCollateralOfficeRegDate(new java.sql.Date(pv.getV_ca_collateralOfficeRegDate().getTime()));
                childE.setCollateralOfficeRegNumber(pv.getV_ca_collateralOfficeRegNumber());

                if(pv.getV_ca_notaryOfficeRegDate()!=null)
                    childE.setCollateralNotaryOfficeRegDate(new java.sql.Date(pv.getV_ca_notaryOfficeRegDate().getTime()));
                else
                {
                    System.out.println("asdf=="+pv.getV_ci_id());
                }
                childE.setCollateralNotaryOfficeRegNumber(pv.getV_ca_notaryOfficeRegNumber());

                childE.setCollateralItemName(pv.getV_ci_name());
                childE.setCollateralItemQuantity(pv.getV_ci_quantity());
                childE.setCollateralItemQuantityTypeId(pv.getV_ci_quantityTypeId());
                childE.setCollateralItemTypeId(pv.getV_ci_itemTypeId());
                childE.setCollateralItemCollateralValue(pv.getV_ci_collateralValue());
                childE.setCollateralItemEstimatedValue(pv.getV_ci_estimatedValue());
                childE.setCollateralItemDescription(pv.getV_ci_description());

                groupEid=this.getIdByGroupType(groupIds.get(4),collateralItemView);
            }


        }


        return reportData;
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
                return "debtor";
            case "4":
                return "loan";
            case "5":
                return "payment";
            case "6":
                return "work_sector";
            case "7":
                return "supervisor";
            case "8":
                return "loan_type";
            case "9":
                return "department";

            case "10":
                return "collateral_agreement";


            case "11":
                return "collateral_item";




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
            }
        }

        return  groupType;
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
            case 3:
                idByGroupType = collateralItemView.getV_debtor_id();
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                idByGroupType = collateralItemView.getV_debtor_work_sector_id();
                break;

            case 10:
                idByGroupType = collateralItemView.getV_ca_id();
                break;

            case 11:
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
            case 3:
                nameByGroupType = collateralItemView.getV_debtor_name();
                break;
            case 4:

                break;
            case 5:
                break;
            case 6:
                nameByGroupType = collateralItemView.getV_work_sector_name();
                break;

            case 10:
                nameByGroupType = collateralItemView.getV_ca_arrestRegNumber()+" ot "+collateralItemView.getV_ca_agreementDate();
                break;

            case 11:
                nameByGroupType = collateralItemView.getV_ci_name();
                break;






        }

        return  nameByGroupType;
    }

}
