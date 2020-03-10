package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.EntityDocumentViewService;
import kg.gov.mf.loan.output.report.service.ReferenceViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

        LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<>();

        parameterS.putAll(reportTool.getParametersByTemplate(reportTemplate));

        reportData.getEntityDocumentViews().addAll(entityDocumentViewService.findByParameter(parameterS));



        return groupifyData(reportData, reportTemplate, reportTool );
    }


    public EntityDocumentReportData groupifyData(EntityDocumentReportData reportData, ReportTemplate reportTemplate, ReportTool reportTool)
    {
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

        EntityDocumentReportData childA = null;
        EntityDocumentReportData childB = null;
        EntityDocumentReportData childC = null;
        EntityDocumentReportData childD = null;
        EntityDocumentReportData childE = null;
        EntityDocumentReportData childF = null;


        for (EntityDocumentView entityDocumentView:reportData.getEntityDocumentViews())
        {
            if(reportTool.getIdByGroupType(reportTemplate.getGroupType1(),entityDocumentView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType1(),entityDocumentView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(reportTemplate.getGroupType1(),entityDocumentView);
                currentgroupBid=-1;
                currentgroupCid=-1;
                currentgroupDid=-1;

            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType2(),entityDocumentView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType2(),entityDocumentView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(reportTemplate.getGroupType2(),entityDocumentView);
                currentgroupCid=-1;
                currentgroupDid=-1;

            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType3(),entityDocumentView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType3(),entityDocumentView));
                childC.setLevel((short)3);

                childC.setV_applied_entity_appliedEntityStateId(entityDocumentView.getV_applied_entity_appliedEntityStateId());

                currentgroupCid=reportTool.getIdByGroupType(reportTemplate.getGroupType3(),entityDocumentView);
                currentgroupDid=-1;

            }





            if(reportTool.getIdByGroupType(reportTemplate.getGroupType4(),entityDocumentView)!=currentgroupDid)
            {
                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType4(),entityDocumentView));
                childD.setLevel((short)4);

                childD.setV_document_package_documentPackageStateId(entityDocumentView.getV_document_package_documentPackageStateId());

                childD.setEntityCount(1);
                childC.setEntityCount(childC.getEntityCount()+1);
                childB.setEntityCount(childB.getEntityCount()+1);
                childA.setEntityCount(childA.getEntityCount()+1);
                reportData.setEntityCount(reportData.getEntityCount()+1);


                currentgroupDid=reportTool.getIdByGroupType(reportTemplate.getGroupType4(),entityDocumentView);
            }




            if(reportTool.getIdByGroupType(reportTemplate.getGroupType5(),entityDocumentView)!=currentgroupEid)
            {
                childE = childD.addChild();
                childE.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType5(),entityDocumentView));
                childE.setLevel((short)5);

                childE.setEntityDocumentCount(1);
                childD.setEntityDocumentCount(childD.getEntityDocumentCount()+1);
                childC.setEntityDocumentCount(childC.getEntityDocumentCount()+1);
                childB.setEntityDocumentCount(childB.getEntityDocumentCount()+1);
                childA.setEntityDocumentCount(childA.getEntityDocumentCount()+1);
                reportData.setEntityDocumentCount(reportData.getEntityDocumentCount()+1);

                childE.setEntityDocumentCompletedCount(entityDocumentView.getV_entity_document_completed_count().shortValue());
                childD.setEntityDocumentCompletedCount(childD.getEntityDocumentCompletedCount()+entityDocumentView.getV_entity_document_completed_count().shortValue());
                childC.setEntityDocumentCompletedCount(childC.getEntityDocumentCompletedCount()+entityDocumentView.getV_entity_document_completed_count().shortValue());
                childB.setEntityDocumentCompletedCount(childB.getEntityDocumentCompletedCount()+entityDocumentView.getV_entity_document_completed_count().shortValue());
                childA.setEntityDocumentCompletedCount(childA.getEntityDocumentCompletedCount()+entityDocumentView.getV_entity_document_completed_count().shortValue());
                reportData.setEntityDocumentCompletedCount(reportData.getEntityDocumentCompletedCount()+entityDocumentView.getV_entity_document_completed_count().shortValue());

                childE.setEntityDocumentNotCompletedCount(entityDocumentView.getV_entity_document_not_completed_count().shortValue());
                childD.setEntityDocumentNotCompletedCount(childD.getEntityDocumentNotCompletedCount()+entityDocumentView.getV_entity_document_not_completed_count().shortValue());
                childC.setEntityDocumentNotCompletedCount(childC.getEntityDocumentNotCompletedCount()+entityDocumentView.getV_entity_document_not_completed_count().shortValue());
                childB.setEntityDocumentNotCompletedCount(childB.getEntityDocumentNotCompletedCount()+entityDocumentView.getV_entity_document_not_completed_count().shortValue());
                childA.setEntityDocumentNotCompletedCount(childA.getEntityDocumentNotCompletedCount()+entityDocumentView.getV_entity_document_not_completed_count().shortValue());
                reportData.setEntityDocumentNotCompletedCount(reportData.getEntityDocumentNotCompletedCount()+entityDocumentView.getV_entity_document_not_completed_count().shortValue());

                childE.setEntityDocumentApprovedCount(entityDocumentView.getV_entity_document_approved_count().shortValue());
                childD.setEntityDocumentApprovedCount(childD.getEntityDocumentApprovedCount()+entityDocumentView.getV_entity_document_approved_count().shortValue());
                childC.setEntityDocumentApprovedCount(childC.getEntityDocumentApprovedCount()+entityDocumentView.getV_entity_document_approved_count().shortValue());
                childB.setEntityDocumentApprovedCount(childB.getEntityDocumentApprovedCount()+entityDocumentView.getV_entity_document_approved_count().shortValue());
                childA.setEntityDocumentApprovedCount(childA.getEntityDocumentApprovedCount()+entityDocumentView.getV_entity_document_approved_count().shortValue());
                reportData.setEntityDocumentApprovedCount(reportData.getEntityDocumentApprovedCount()+entityDocumentView.getV_entity_document_approved_count().shortValue());

                childE.setEntityDocumentNotApprovedCount(entityDocumentView.getV_entity_document_not_approved_count().shortValue());
                childD.setEntityDocumentNotApprovedCount(childD.getEntityDocumentNotApprovedCount()+entityDocumentView.getV_entity_document_not_approved_count().shortValue());
                childC.setEntityDocumentNotApprovedCount(childC.getEntityDocumentNotApprovedCount()+entityDocumentView.getV_entity_document_not_approved_count().shortValue());
                childB.setEntityDocumentNotApprovedCount(childB.getEntityDocumentNotApprovedCount()+entityDocumentView.getV_entity_document_not_approved_count().shortValue());
                childA.setEntityDocumentNotApprovedCount(childA.getEntityDocumentNotApprovedCount()+entityDocumentView.getV_entity_document_not_approved_count().shortValue());
                reportData.setEntityDocumentNotApprovedCount(reportData.getEntityDocumentNotApprovedCount()+entityDocumentView.getV_entity_document_not_approved_count().shortValue());


                childE.setV_entity_document_entityDocumentStateId(entityDocumentView.getV_entity_document_entityDocumentStateId());

                childE.setV_entity_document_completedBy(entityDocumentView.getV_entity_document_completedBy());

                if(entityDocumentView.getV_entity_document_completedDate()!=null)
                    childE.setV_entity_document_completedDate(new java.sql.Date(entityDocumentView.getV_entity_document_completedDate().getTime()));
                childE.setV_entity_document_completedDescription(entityDocumentView.getV_entity_document_completedDescription());

                childE.setV_entity_document_approvedBy(entityDocumentView.getV_entity_document_approvedBy());

                if(entityDocumentView.getV_entity_document_approvedDate()!=null)
                    childE.setV_entity_document_approvedDate(new java.sql.Date(entityDocumentView.getV_entity_document_approvedDate().getTime()));
                childE.setV_entity_document_approvedDescription(entityDocumentView.getV_entity_document_approvedDescription());

                currentgroupEid=reportTool.getIdByGroupType(reportTemplate.getGroupType5(),entityDocumentView);
            }

        }



        return reportData;
    }



}
