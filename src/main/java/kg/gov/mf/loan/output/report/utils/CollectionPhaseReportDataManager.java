package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.manage.model.collateral.ItemType;
import kg.gov.mf.loan.manage.model.collection.PhaseType;
import kg.gov.mf.loan.manage.service.collection.PhaseTypeService;
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
    PhaseTypeService phaseTypeService;

    @Autowired
    CollectionPhaseViewService collectionPhaseViewService;


    Map<Long,PhaseType> phaseTypeMap = new HashMap<Long,PhaseType>();

    public CollectionPhaseReportData getReportDataGrouped(CollectionPhaseReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        Set<CollectionPhaseView> collectionPhaseViews =  new HashSet<CollectionPhaseView>();

        ReportTool reportTool = new ReportTool();

        Date onDate = reportTool.getOnDate(reportTemplate);

        for (PhaseType phaseType:this.phaseTypeService.list()
                )
        {
            phaseTypeMap.put(phaseType.getId(),phaseType);
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

        reportData.getCollectionPhaseViews().addAll(collectionPhaseViewService.findByParameter(parameterS));


        reportData.setOnDate(new java.sql.Date(onDate.getTime()));

        return groupifyData(reportData,groupIds,reportTemplate);
    }

    public CollectionPhaseReportData groupifyData(CollectionPhaseReportData reportData, List<Long> groupIds, ReportTemplate reportTemplate)
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

        CollectionPhaseReportData childA = null;
        CollectionPhaseReportData childB = null;
        CollectionPhaseReportData childC = null;
        CollectionPhaseReportData childD = null;
        CollectionPhaseReportData childE = null;
        CollectionPhaseReportData childF = null;


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

        for (CollectionPhaseView collectionPhaseView:reportData.getCollectionPhaseViews())
        {


            if(reportTool.getIdByGroupType(groupAid,collectionPhaseView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(groupAid,collectionPhaseView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(groupAid,collectionPhaseView);
            }

            if(reportTool.getIdByGroupType(groupBid,collectionPhaseView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(groupBid,collectionPhaseView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(groupBid,collectionPhaseView);
            }

            if(reportTool.getIdByGroupType(groupCid,collectionPhaseView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(groupCid,collectionPhaseView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                currentgroupCid=reportTool.getIdByGroupType(groupCid,collectionPhaseView);
            }

            if(reportTool.getIdByGroupType(groupDid,collectionPhaseView)!=currentgroupDid)
            {
                CollectionPhaseView lv = collectionPhaseView;

                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(groupDid,collectionPhaseView));
                childD.setLevel((short)4);


                childD.setLevel((short)4);

                childD.setDetailsCount(1);
                childC.setDetailsCount(childC.getDetailsCount()+1);
                childA.setDetailsCount(childA.getDetailsCount()+1);
                childB.setDetailsCount(childB.getDetailsCount()+1);
                reportData.setDetailsCount(reportData.getDetailsCount()+1);





                if(lv.getV_cp_startDate()!=null)
                childD.setCollection_phase_start_date(new java.sql.Date(lv.getV_cp_startDate().getTime()));





                currentgroupDid=reportTool.getIdByGroupType(groupDid,collectionPhaseView);
            }

            if(reportTool.getIdByGroupType(groupEid,collectionPhaseView)!=currentgroupEid)
            {
                CollectionPhaseView pv = collectionPhaseView;

                childE = childD.addChild();
                childE.setName(reportTool.getNameByGroupType(groupEid,collectionPhaseView));
                childE.setLevel((short)5);

                if(pv.getV_cph_startDate()!=null)
                childE.setCollection_phase_start_date(new java.sql.Date(pv.getV_cph_startDate().getTime()));
                childE.setCollection_phase_type_id(pv.getV_cph_phaseTypeId());

                childE.setCollection_phase_type_name(phaseTypeMap.get(pv.getV_cph_phaseTypeId()).getName());

                childE.setCollection_phase_start_total_amount(pv.getV_cph_start_total_amount());


                currentgroupEid=reportTool.getIdByGroupType(groupEid,collectionPhaseView);
            }


        }


        return reportData;
    }


}
