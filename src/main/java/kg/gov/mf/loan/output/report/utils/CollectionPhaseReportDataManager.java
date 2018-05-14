package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.CollectionPhaseViewService;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class CollectionPhaseReportDataManager {
	
	/**
     * Report GENERATION TOOL
     */

    @Autowired
    LoanViewService loanViewService;

    @Autowired
    CollectionPhaseViewService collectionPhaseViewService;

    public CollectionPhaseReportData getReportDataGrouped(CollectionPhaseReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        Set<CollectionPhaseView> collectionPhaseViews =  new HashSet<CollectionPhaseView>();

        Date onDate = this.getOnDate(reportTemplate);

        LinkedHashMap<String,List<Long>> parameterS = new LinkedHashMap<String,List<Long>>();

        List<Long> groupIds = new ArrayList<>();

        groupIds.add(getGroupType(reportTemplate,1));
        groupIds.add(getGroupType(reportTemplate,2));
        groupIds.add(getGroupType(reportTemplate,3));
        groupIds.add((long) 14);
        groupIds.add((long) 15);

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
        long groupDtype = getGroupType(reportTemplate,14);
        long groupEtype = getGroupType(reportTemplate,15);



        // initial filter by report filter parameters

        reportData.getCollectionPhaseViews().addAll(collectionPhaseViewService.findByParameter(parameterS));


        reportData.setOnDate(new java.sql.Date(onDate.getTime()));

        Set<Long> groupAIds = new HashSet<Long>();

        ArrayList<CollectionPhaseView> allCollectionPhaseViews = new ArrayList<CollectionPhaseView>();

        for (CollectionPhaseView collectionPhaseViewInLoop: reportData.getCollectionPhaseViews()
             )
        {
//            System.out.println(
//                    collectionPhaseViewInLoop.getV_debtor_region_id() +" "+
//                    collectionPhaseViewInLoop.getV_debtor_district_id()+" "+
//                    collectionPhaseViewInLoop.getV_debtor_id()+" "+
//                    collectionPhaseViewInLoop.getV_ca_id()+" "+
//                            collectionPhaseViewInLoop.getV_ci_id());


//            System.out.println(
//                    collectionPhaseViewInLoop.getV_region_name() +" "+
//                            collectionPhaseViewInLoop.getV_district_name()+" "+
//                            collectionPhaseViewInLoop.getV_debtor_name()+" "+
//                            collectionPhaseViewInLoop.getV_credit_order_regNumber()+ " от "+
//                            collectionPhaseViewInLoop.getV_credit_order_regDate()+ ", "+
//                            collectionPhaseViewInLoop.getV_loan_reg_number()+ " от " +
//                            collectionPhaseViewInLoop.getV_loan_reg_date()+" "+
//                            collectionPhaseViewInLoop.getV_payment_number()+" "+
//                            collectionPhaseViewInLoop.getV_payment_date()+" "+
//                            collectionPhaseViewInLoop.getV_payment_total_amount());
        }

        return groupifyData(reportData,groupIds);
    }

    public CollectionPhaseReportData groupifyData(CollectionPhaseReportData reportData, List<Long> groupIds)
    {

        long groupAid=0;
        long groupBid=0;
        long groupCid=0;
        long groupDid=0;
        long groupEid=0;
        long groupFid=0;

        CollectionPhaseReportData childA = null;
        CollectionPhaseReportData childB = null;
        CollectionPhaseReportData childC = null;
        CollectionPhaseReportData childD = null;
        CollectionPhaseReportData childE = null;
        CollectionPhaseReportData childF = null;


        for (CollectionPhaseView collectionPhaseView:reportData.getCollectionPhaseViews())
        {
            if(this.getIdByGroupType(groupIds.get(0),collectionPhaseView)!=groupAid)
            {
                childA = reportData.addChild();
                childA.setName(this.getNameByGroupType(1,collectionPhaseView));
                childA.setLevel((short)1);

                groupAid=this.getIdByGroupType(groupIds.get(0),collectionPhaseView);
            }

            if(this.getIdByGroupType(groupIds.get(1),collectionPhaseView)!=groupBid)
            {
                childB = childA.addChild();
                childB.setName(this.getNameByGroupType(2,collectionPhaseView));
                childB.setLevel((short)2);

                groupBid=this.getIdByGroupType(groupIds.get(1),collectionPhaseView);
            }

            if(this.getIdByGroupType(groupIds.get(2),collectionPhaseView)!=groupCid)
            {
                childC = childB.addChild();
                childC.setName(this.getNameByGroupType(3,collectionPhaseView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                groupCid=this.getIdByGroupType(groupIds.get(2),collectionPhaseView);
            }

            if(this.getIdByGroupType(groupIds.get(3),collectionPhaseView)!=groupDid)
            {
                CollectionPhaseView lv = collectionPhaseView;

                childD = childC.addChild();
                childD.setName(this.getNameByGroupType(14,collectionPhaseView));
                childD.setLevel((short)4);


                childD.setLevel((short)4);

                childD.setDetailsCount(1);
                childC.setDetailsCount(childC.getDetailsCount()+1);
                childA.setDetailsCount(childA.getDetailsCount()+1);
                childB.setDetailsCount(childB.getDetailsCount()+1);
                reportData.setDetailsCount(reportData.getDetailsCount()+1);





                if(lv.getV_cp_startDate()!=null)
                childD.setCollection_phase_start_date(new java.sql.Date(lv.getV_cp_startDate().getTime()));





                groupDid=this.getIdByGroupType(groupIds.get(3),collectionPhaseView);
            }

            if(this.getIdByGroupType(groupIds.get(4),collectionPhaseView)!=groupEid)
            {
                CollectionPhaseView pv = collectionPhaseView;

                childE = childD.addChild();
                childE.setName(this.getNameByGroupType(15,collectionPhaseView));
                childE.setLevel((short)5);

                if(pv.getV_cph_startDate()!=null)
                childE.setCollection_phase_start_date(new java.sql.Date(pv.getV_cph_startDate().getTime()));
                childE.setCollection_phase_type_id(pv.getV_cph_phaseTypeId());






                groupEid=this.getIdByGroupType(groupIds.get(4),collectionPhaseView);
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


            case "14":
                return "collection_procedure";

            case "15":
                return "collection_phase";


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
            case 3:
                idByGroupType = collectionPhaseView.getV_debtor_id();
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                idByGroupType = collectionPhaseView.getV_debtor_work_sector_id();
                break;

            case 14:
                idByGroupType = collectionPhaseView.getV_cp_id();
                break;

            case 15:
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
            case 3:
                nameByGroupType = collectionPhaseView.getV_debtor_name();
                break;
            case 4:

                break;
            case 5:
                break;
            case 6:
                nameByGroupType = collectionPhaseView.getV_work_sector_name();
                break;

            case 14:
                nameByGroupType = "Процедура взыскания";
                break;

            case 15:
                nameByGroupType = "Стадия взыскания";
                break;






        }

        return  nameByGroupType;
    }

}
