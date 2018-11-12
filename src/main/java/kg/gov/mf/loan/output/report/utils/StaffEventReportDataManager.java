package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.StaffEventReportData;
import kg.gov.mf.loan.output.report.model.StaffEventView;
import kg.gov.mf.loan.output.report.model.ReportTemplate;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import kg.gov.mf.loan.output.report.service.StaffEventViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.LinkedHashMap;
import java.util.List;


public class StaffEventReportDataManager {
	
	/**
     * Report GENERATION TOOL
     */

    @Autowired
    LoanViewService loanViewService;

    @Autowired
    StaffEventViewService staffEventViewService;

    public StaffEventReportData getReportDataGrouped(StaffEventReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        ReportTool reportTool = new ReportTool();

        LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<>();

        parameterS.putAll(reportTool.getParametersByTemplate(reportTemplate));

        reportData.getStaffEventViews().addAll(staffEventViewService.findByParameter(parameterS));

        for (StaffEventView staffEventView: reportData.getStaffEventViews())
        {
            System.out.println(staffEventView.getV_s_name());
        }

        return groupifyData(reportData, reportTemplate, reportTool );
    }

    public StaffEventReportData groupifyData(StaffEventReportData reportData, ReportTemplate reportTemplate, ReportTool reportTool)
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

        StaffEventReportData childA = null;
        StaffEventReportData childB = null;
        StaffEventReportData childC = null;
        StaffEventReportData childD = null;
        StaffEventReportData childE = null;
        StaffEventReportData childF = null;



        for (StaffEventView staffEventView:reportData.getStaffEventViews())
        {
            if(reportTool.getIdByGroupType(reportTemplate.getGroupType1(),staffEventView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType1(),staffEventView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(reportTemplate.getGroupType1(),staffEventView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType2(),staffEventView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType2(),staffEventView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(reportTemplate.getGroupType2(),staffEventView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType3(),staffEventView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType3(),staffEventView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                currentgroupCid=reportTool.getIdByGroupType(reportTemplate.getGroupType3(),staffEventView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType4(),staffEventView)!=currentgroupDid)
            {
                StaffEventView lv = staffEventView;

                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType4(),staffEventView));
                childD.setLevel((short)4);

                childD.setStaffEventCount(1);
                childC.setStaffEventCount(childC.getStaffEventCount()+1);
                childA.setStaffEventCount(childA.getStaffEventCount()+1);
                childB.setStaffEventCount(childB.getStaffEventCount()+1);
                reportData.setStaffEventCount(reportData.getStaffEventCount()+1);

                childD.setStaffEventID(lv.getV_se_id());

                childD.setStaffEventName(lv.getV_se_name());
                childD.setStaffEventDate(new java.sql.Date(lv.getV_se_date().getTime()));
                childD.setStaffEventTypeID(lv.getV_se_type_id().shortValue());

                currentgroupDid=reportTool.getIdByGroupType(reportTemplate.getGroupType4(),staffEventView);
            }

        }


        return reportData;
    }


}
