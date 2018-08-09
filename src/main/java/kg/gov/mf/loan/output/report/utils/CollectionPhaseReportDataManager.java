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



        LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<>();

        parameterS.putAll(reportTool.getParametersByTemplate(reportTemplate));

        reportData.getCollectionPhaseViews().addAll(collectionPhaseViewService.findByParameter(parameterS));

        for (CollectionPhaseView collectionPhaseView: reportData.getCollectionPhaseViews())
        {
            System.out.println(collectionPhaseView.getV_debtor_name());
        }

        return groupifyData(reportData,reportTemplate, reportTool);
    }

    public CollectionPhaseReportData groupifyData(CollectionPhaseReportData reportData,ReportTemplate reportTemplate, ReportTool reportTool)
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

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType1(),collectionPhaseView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType1(),collectionPhaseView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(reportTemplate.getGroupType1(),collectionPhaseView);
            }


            if(reportTool.getIdByGroupType(reportTemplate.getGroupType2(),collectionPhaseView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType2(),collectionPhaseView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(reportTemplate.getGroupType2(),collectionPhaseView);
            }




            if(reportTool.getIdByGroupType(reportTemplate.getGroupType3(),collectionPhaseView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType3(),collectionPhaseView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                currentgroupCid=reportTool.getIdByGroupType(reportTemplate.getGroupType3(),collectionPhaseView);
            }



            if(reportTool.getIdByGroupType(reportTemplate.getGroupType4(),collectionPhaseView)!=currentgroupDid)
            {
                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType4(),collectionPhaseView));
                childD.setLevel((short)4);

                childD.setLevel((short)4);

                childD.setDetailsCount(1);
                childC.setDetailsCount(childC.getDetailsCount()+1);
                childA.setDetailsCount(childA.getDetailsCount()+1);
                childB.setDetailsCount(childB.getDetailsCount()+1);
                reportData.setDetailsCount(reportData.getDetailsCount()+1);





                if(collectionPhaseView.getV_cp_startDate()!=null)
                    childD.setCollection_phase_start_date(new java.sql.Date(collectionPhaseView.getV_cp_startDate().getTime()));

                currentgroupDid=reportTool.getIdByGroupType(reportTemplate.getGroupType4(),collectionPhaseView);
            }


            if(reportTool.getIdByGroupType(reportTemplate.getGroupType5(),collectionPhaseView)!=currentgroupEid)
            {
                childE = childD.addChild();
                childE.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType5(),collectionPhaseView));
                childE.setLevel((short)5);

                if(collectionPhaseView.getV_cph_startDate()!=null)
                    childE.setCollection_phase_start_date(new java.sql.Date(collectionPhaseView.getV_cph_startDate().getTime()));

                if(collectionPhaseView.getV_cph_closeDate()!=null)
                    childE.setCollection_phase_close_date(new java.sql.Date(collectionPhaseView.getV_cph_closeDate().getTime()));

                reportData.setPhaseCount(reportData.getPhaseCount()+1);
                childA.setPhaseCount(childA.getPhaseCount()+1);
                childB.setPhaseCount(childB.getPhaseCount()+1);
                childC.setPhaseCount(childC.getPhaseCount()+1);
                childD.setPhaseCount(childD.getPhaseCount()+1);

                if(collectionPhaseView.getV_cph_phaseStatusId()>1)
                {
                    reportData.setResultCount(reportData.getResultCount()+1);
                    childA.setResultCount(childA.getResultCount()+1);
                    childB.setResultCount(childB.getResultCount()+1);
                    childC.setResultCount(childC.getResultCount()+1);
                    childD.setResultCount(childD.getResultCount()+1);
                }





                childE.setCollection_phase_type_id(collectionPhaseView.getV_cph_phaseTypeId());

                childE.setCollection_phase_status_id(collectionPhaseView.getV_cph_phaseStatusId());

                childE.setCollection_phase_type_name(phaseTypeMap.get(collectionPhaseView.getV_cph_phaseTypeId()).getName());


                reportData.setCollection_phase_start_total_amount(reportData.getCollection_phase_start_total_amount()+collectionPhaseView.getV_cph_start_total_amount());
                childA.setCollection_phase_start_total_amount(childA.getCollection_phase_start_total_amount()+collectionPhaseView.getV_cph_start_total_amount());
                childB.setCollection_phase_start_total_amount(childB.getCollection_phase_start_total_amount()+collectionPhaseView.getV_cph_start_total_amount());
                childC.setCollection_phase_start_total_amount(childC.getCollection_phase_start_total_amount()+collectionPhaseView.getV_cph_start_total_amount());
                childD.setCollection_phase_start_total_amount(childD.getCollection_phase_start_total_amount()+collectionPhaseView.getV_cph_start_total_amount());
                childE.setCollection_phase_start_total_amount(collectionPhaseView.getV_cph_start_total_amount());

                if(collectionPhaseView.getV_cph_close_total_amount()>0)
                {
                    reportData.setCollection_phase_close_total_amount(reportData.getCollection_phase_close_total_amount()+collectionPhaseView.getV_cph_close_total_amount());
                    childA.setCollection_phase_close_total_amount(childA.getCollection_phase_close_total_amount()+collectionPhaseView.getV_cph_close_total_amount());
                    childB.setCollection_phase_close_total_amount(childB.getCollection_phase_close_total_amount()+collectionPhaseView.getV_cph_close_total_amount());
                    childC.setCollection_phase_close_total_amount(childC.getCollection_phase_close_total_amount()+collectionPhaseView.getV_cph_close_total_amount());
                    childD.setCollection_phase_close_total_amount(childD.getCollection_phase_close_total_amount()+collectionPhaseView.getV_cph_close_total_amount());
                    childE.setCollection_phase_close_total_amount(collectionPhaseView.getV_cph_close_total_amount());
                }


                childE.setCollection_procedure_status_id(collectionPhaseView.getV_cp_procedureStatusId());

                currentgroupEid=reportTool.getIdByGroupType(reportTemplate.getGroupType5(),collectionPhaseView);
            }

        }


        return reportData;
    }


}
