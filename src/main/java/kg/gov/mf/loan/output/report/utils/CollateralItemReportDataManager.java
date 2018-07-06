package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.manage.model.collateral.ItemType;
import kg.gov.mf.loan.manage.service.collateral.ItemTypeService;
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
    ItemTypeService itemTypeService;

    @Autowired
    CollateralItemViewService collateralItemViewService;

    Map<Long,ItemType> itemTypeMap = new HashMap<Long,ItemType>();

    public CollateralItemReportData getReportDataGrouped(CollateralItemReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        Set<CollateralItemView> collateralItemViews =  new HashSet<CollateralItemView>();

        ReportTool reportTool = new ReportTool();

        Date onDate = reportTool.getOnDate(reportTemplate);


        for (ItemType itemType:this.itemTypeService.list()
             )
        {
            itemTypeMap.put(itemType.getId(),itemType);
        }

        LinkedHashMap<String,List<Long>> parameterS = new LinkedHashMap<String,List<Long>>();

        List<Long> groupIds = new ArrayList<>();

        if(reportTool.getGroupType(reportTemplate,1)>0)
            groupIds.add(reportTool.getGroupType(reportTemplate,1));

        if(reportTool.getGroupType(reportTemplate,2)>0)
            groupIds.add(reportTool.getGroupType(reportTemplate,2));

        if(reportTool.getGroupType(reportTemplate,3)>0)
            groupIds.add(reportTool.getGroupType(reportTemplate,3));


        if(reportTool.getGroupType(reportTemplate,4)>0)
            groupIds.add(reportTool.getGroupType(reportTemplate,4));


        if(reportTool.getGroupType(reportTemplate,5)>0)
            groupIds.add(reportTool.getGroupType(reportTemplate,5));


        if(reportTool.getGroupType(reportTemplate,6)>0)
            groupIds.add(reportTool.getGroupType(reportTemplate,6));

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

                parameterS.put(reportTool.getParameterTypeNameById(String.valueOf(objectList.getObjectTypeId())),Ids);
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


        // initial filter by report filter parameters

        reportData.getCollateralItemViews().addAll(collateralItemViewService.findByParameter(parameterS));


        reportData.setOnDate(new java.sql.Date(onDate.getTime()));



        return groupifyData(reportData,groupIds,reportTemplate);
    }

    public CollateralItemReportData groupifyData(CollateralItemReportData reportData, List<Long> groupIds, ReportTemplate reportTemplate)
    {
        ReportTool reportTool = new ReportTool();

        reportTool.initReference();



        long groupAid=-1;
        long groupBid=-1;
        long groupCid=-1;
        long groupDid=-1;
        long groupEid=-1;
        long groupFid=-1;

        long currentgroupAid=-1;
        long currentgroupBid=-1;
        long currentgroupCid=-1;
        long currentgroupDid=-1;
        long currentgroupEid=-1;
        long currentgroupFid=-1;

        CollateralItemReportData childA = null;
        CollateralItemReportData childB = null;
        CollateralItemReportData childC = null;
        CollateralItemReportData childD = null;
        CollateralItemReportData childE = null;
        CollateralItemReportData childF = null;


        if(reportTool.getGroupType(reportTemplate,1)>0)
            groupAid = reportTool.getGroupType(reportTemplate,1);


        if(reportTool.getGroupType(reportTemplate,2)>0)
            groupBid = reportTool.getGroupType(reportTemplate,2);

        if(reportTool.getGroupType(reportTemplate,3)>0)
            groupCid = reportTool.getGroupType(reportTemplate,3);


        if(reportTool.getGroupType(reportTemplate,4)>0)
            groupDid = reportTool.getGroupType(reportTemplate,4);


        if(reportTool.getGroupType(reportTemplate,5)>0)
            groupEid = reportTool.getGroupType(reportTemplate,5);


        if(reportTool.getGroupType(reportTemplate,6)>0)
            groupFid = reportTool.getGroupType(reportTemplate,6);

        for (CollateralItemView collateralItemView:reportData.getCollateralItemViews())
        {
            if(reportTool.getIdByGroupType(groupAid,collateralItemView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(groupAid,collateralItemView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(groupAid,collateralItemView);
            }

            if(reportTool.getIdByGroupType(groupBid,collateralItemView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(groupBid,collateralItemView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(groupBid,collateralItemView);
            }

            if(reportTool.getIdByGroupType(groupCid,collateralItemView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(groupCid,collateralItemView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                currentgroupCid=reportTool.getIdByGroupType(groupCid,collateralItemView);
            }

            if(reportTool.getIdByGroupType(groupDid,collateralItemView)!=currentgroupDid)
            {
                CollateralItemView lv = collateralItemView;

                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(groupDid,collateralItemView));
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








                currentgroupDid=reportTool.getIdByGroupType(groupDid,collateralItemView);
            }

            if(reportTool.getIdByGroupType(groupEid,collateralItemView)!=currentgroupEid)
            {
                CollateralItemView pv = collateralItemView;

                childE = childD.addChild();
                childE.setName(reportTool.getNameByGroupType(groupEid,collateralItemView));
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

                childE.setCollateralItemTypeName(itemTypeMap.get(pv.getV_ci_itemTypeId()).getName());

                currentgroupEid=reportTool.getIdByGroupType(groupEid,collateralItemView);
            }


        }


        return reportData;
    }



}
