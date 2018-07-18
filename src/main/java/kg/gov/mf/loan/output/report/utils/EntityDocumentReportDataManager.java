package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.EntityDocumentViewService;
import kg.gov.mf.loan.output.report.service.ReferenceViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.*;


public class EntityDocumentReportDataManager {

    @Autowired
    EntityDocumentViewService entityDocumentViewService;

    @Autowired
    ReferenceViewService referenceViewService;

    public EntityDocumentReportData getReportDataGrouped(EntityDocumentReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        ReportTool reportTool = new ReportTool();

        Date onDate = reportTool.getOnDate(reportTemplate);

        reportData.setOnDate(new java.sql.Date(onDate.getTime()));

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

                parameterS.put(reportTool.getParameterTypeNameById(String.valueOf(objectList.getObjectTypeId())),Ids);
            }

            if(onDate!=null)
            {
                List<Long> Ids = new ArrayList<>();

                try
                {

                    Ids.add(onDate.getTime());
                }
                catch ( Exception ex )
                {
                    System.out.println(ex);
                }

                parameterS.put("onDate",Ids);
            }

            parameterS.put("orderBy",groupIds);


        }

        reportData.getEntityDocumentViews().addAll(entityDocumentViewService.findByParameter(parameterS));

        for (EntityDocumentView entityDocumentViewInLoop: reportData.getEntityDocumentViews())
        {
            System.out.println(
                    entityDocumentViewInLoop.getV_co_id() +" "+
                            entityDocumentViewInLoop.getV_ael_id()+" "+
                            entityDocumentViewInLoop.getV_applied_entity_id()+" "+
                            entityDocumentViewInLoop.getV_document_package_id()+" "+
                            entityDocumentViewInLoop.getV_entity_document_id());
        }

        return groupifyData(reportData,groupIds, reportTemplate );

    }


    public EntityDocumentReportData groupifyData(EntityDocumentReportData reportData, List<Long> groupIds, ReportTemplate reportTemplate)
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

        EntityDocumentReportData childA = null;
        EntityDocumentReportData childB = null;
        EntityDocumentReportData childC = null;
        EntityDocumentReportData childD = null;
        EntityDocumentReportData childE = null;
        EntityDocumentReportData childF = null;


        for (EntityDocumentView entityDocumentView:reportData.getEntityDocumentViews())
        {
            if(reportTool.getIdByGroupType(groupAid,entityDocumentView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(groupAid,entityDocumentView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(groupAid,entityDocumentView);
            }

            if(reportTool.getIdByGroupType(groupBid,entityDocumentView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(groupBid,entityDocumentView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(groupBid,entityDocumentView);
            }

            if(reportTool.getIdByGroupType(groupCid,entityDocumentView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(groupCid,entityDocumentView));
                childC.setLevel((short)3);

                childC.setEntityCount(1);
                childB.setEntityCount(childB.getEntityCount()+1);
                childA.setEntityCount(childA.getEntityCount()+1);
                reportData.setEntityCount(reportData.getEntityCount()+1);

                currentgroupCid=reportTool.getIdByGroupType(groupCid,entityDocumentView);
            }

            if(reportTool.getIdByGroupType(groupDid,entityDocumentView)!=currentgroupDid)
            {
                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(groupDid,entityDocumentView));
                childD.setLevel((short)4);

                currentgroupDid=reportTool.getIdByGroupType(groupDid,entityDocumentView);
            }

            if(reportTool.getIdByGroupType(groupEid,entityDocumentView)!=currentgroupEid)
            {
                childE = childD.addChild();
                childE.setName(reportTool.getNameByGroupType(groupEid,entityDocumentView));
                childE.setLevel((short)5);

                childE.setEntityDocumentCount(1);
                childD.setEntityDocumentCount(childD.getEntityDocumentCount()+1);
                childC.setEntityDocumentCount(childC.getEntityDocumentCount()+1);
                childB.setEntityDocumentCount(childB.getEntityDocumentCount()+1);
                childA.setEntityDocumentCount(childA.getEntityDocumentCount()+1);
                reportData.setEntityDocumentCount(reportData.getEntityDocumentCount()+1);

                childE.setEntityDocumentCompletedCount((int)entityDocumentView.getV_entity_document_completed_count());
                childD.setEntityDocumentCompletedCount(childD.getEntityDocumentCompletedCount()+(int)entityDocumentView.getV_entity_document_completed_count());
                childC.setEntityDocumentCompletedCount(childC.getEntityDocumentCompletedCount()+(int)entityDocumentView.getV_entity_document_completed_count());
                childB.setEntityDocumentCompletedCount(childB.getEntityDocumentCompletedCount()+(int)entityDocumentView.getV_entity_document_completed_count());
                childA.setEntityDocumentCompletedCount(childA.getEntityDocumentCompletedCount()+(int)entityDocumentView.getV_entity_document_completed_count());
                reportData.setEntityDocumentCompletedCount(reportData.getEntityDocumentCompletedCount()+(int)entityDocumentView.getV_entity_document_completed_count());

                childE.setEntityDocumentNotCompletedCount((int)entityDocumentView.getV_entity_document_not_completed_count());
                childD.setEntityDocumentNotCompletedCount(childD.getEntityDocumentNotCompletedCount()+(int)entityDocumentView.getV_entity_document_not_completed_count());
                childC.setEntityDocumentNotCompletedCount(childC.getEntityDocumentNotCompletedCount()+(int)entityDocumentView.getV_entity_document_not_completed_count());
                childB.setEntityDocumentNotCompletedCount(childB.getEntityDocumentNotCompletedCount()+(int)entityDocumentView.getV_entity_document_not_completed_count());
                childA.setEntityDocumentNotCompletedCount(childA.getEntityDocumentNotCompletedCount()+(int)entityDocumentView.getV_entity_document_not_completed_count());
                reportData.setEntityDocumentNotCompletedCount(reportData.getEntityDocumentNotCompletedCount()+(int)entityDocumentView.getV_entity_document_not_completed_count());

                childE.setEntityDocumentApprovedCount((int)entityDocumentView.getV_entity_document_approved_count());
                childD.setEntityDocumentApprovedCount(childD.getEntityDocumentApprovedCount()+(int)entityDocumentView.getV_entity_document_approved_count());
                childC.setEntityDocumentApprovedCount(childC.getEntityDocumentApprovedCount()+(int)entityDocumentView.getV_entity_document_approved_count());
                childB.setEntityDocumentApprovedCount(childB.getEntityDocumentApprovedCount()+(int)entityDocumentView.getV_entity_document_approved_count());
                childA.setEntityDocumentApprovedCount(childA.getEntityDocumentApprovedCount()+(int)entityDocumentView.getV_entity_document_approved_count());
                reportData.setEntityDocumentApprovedCount(reportData.getEntityDocumentApprovedCount()+(int)entityDocumentView.getV_entity_document_approved_count());

                childE.setEntityDocumentNotApprovedCount((int)entityDocumentView.getV_entity_document_not_approved_count());
                childD.setEntityDocumentNotApprovedCount(childD.getEntityDocumentNotApprovedCount()+(int)entityDocumentView.getV_entity_document_not_approved_count());
                childC.setEntityDocumentNotApprovedCount(childC.getEntityDocumentNotApprovedCount()+(int)entityDocumentView.getV_entity_document_not_approved_count());
                childB.setEntityDocumentNotApprovedCount(childB.getEntityDocumentNotApprovedCount()+(int)entityDocumentView.getV_entity_document_not_approved_count());
                childA.setEntityDocumentNotApprovedCount(childA.getEntityDocumentNotApprovedCount()+(int)entityDocumentView.getV_entity_document_not_approved_count());
                reportData.setEntityDocumentNotApprovedCount(reportData.getEntityDocumentNotApprovedCount()+(int)entityDocumentView.getV_entity_document_not_approved_count());

                currentgroupEid=reportTool.getIdByGroupType(groupEid,entityDocumentView);
            }

            if(reportTool.getIdByGroupType(groupFid,entityDocumentView)!=currentgroupFid)
            {
                childF = childE.addChild();
                childF.setName(reportTool.getNameByGroupType(groupFid,entityDocumentView));
                childF.setLevel((short)6);

                currentgroupFid=reportTool.getIdByGroupType(groupFid,entityDocumentView);
            }


        }


        return reportData;
    }



}