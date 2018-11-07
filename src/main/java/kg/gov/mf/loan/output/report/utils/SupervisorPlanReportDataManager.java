package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.SupervisorPlanReportData;
import kg.gov.mf.loan.output.report.model.SupervisorPlanView;
import kg.gov.mf.loan.output.report.model.ReportTemplate;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import kg.gov.mf.loan.output.report.service.SupervisorPlanViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.LinkedHashMap;
import java.util.List;


public class SupervisorPlanReportDataManager {
	
	/**
     * Report GENERATION TOOL
     */

    @Autowired
    LoanViewService loanViewService;

    @Autowired
    SupervisorPlanViewService supervisorPlanViewService;

    public SupervisorPlanReportData getReportDataGrouped(SupervisorPlanReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        ReportTool reportTool = new ReportTool();

        LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<>();

        parameterS.putAll(reportTool.getParametersByTemplate(reportTemplate));

        reportData.getSupervisorPlanViews().addAll(supervisorPlanViewService.findByParameter(parameterS));

        for (SupervisorPlanView supervisorPlanView: reportData.getSupervisorPlanViews())
        {
            System.out.println(supervisorPlanView.getV_debtor_name());
        }

        return groupifyData(reportData, reportTemplate, reportTool );


    }

    public SupervisorPlanReportData groupifyData(SupervisorPlanReportData reportData, ReportTemplate reportTemplate, ReportTool reportTool)
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


        SupervisorPlanReportData childA = null;
        SupervisorPlanReportData childB = null;
        SupervisorPlanReportData childC = null;
        SupervisorPlanReportData childD = null;
        SupervisorPlanReportData childE = null;
        SupervisorPlanReportData childF = null;



        double lastLoanAmount = 0;

        double lastDisbursement = 0;
        double lastPrincipalPayment = 0;
        double lastInterestPayment = 0;
        double lastCollectedInterestPayment = 0;
        double lastCollectedPenaltyPayment = 0;

        for (SupervisorPlanView supervisorPlanView:reportData.getSupervisorPlanViews())
        {
            if(reportTool.getIdByGroupType(reportTemplate.getGroupType1(),supervisorPlanView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType1(),supervisorPlanView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(reportTemplate.getGroupType1(),supervisorPlanView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType2(),supervisorPlanView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType2(),supervisorPlanView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(reportTemplate.getGroupType2(),supervisorPlanView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType3(),supervisorPlanView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType3(),supervisorPlanView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                currentgroupCid=reportTool.getIdByGroupType(reportTemplate.getGroupType3(),supervisorPlanView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType4(),supervisorPlanView)!=currentgroupDid)
            {

                SupervisorPlanView lv = supervisorPlanView;

                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType4(),supervisorPlanView));
                childD.setLevel((short)4);

                childD.setDetailsCount(1);
                childC.setDetailsCount(childC.getDetailsCount()+1);
                childA.setDetailsCount(childA.getDetailsCount()+1);
                childB.setDetailsCount(childB.getDetailsCount()+1);
                reportData.setDetailsCount(reportData.getDetailsCount()+1);


                childD.setID(lv.getV_loan_id());
                childD.setName(lv.getV_credit_order_reg_number()+lv.getV_loan_reg_number()+" от "+lv.getV_loan_reg_date());
                childD.setCurrency(lv.getV_loan_currency_id().shortValue());
                childD.setLoanRegNumber(lv.getV_loan_reg_number());
                childD.setOrderRegNumber(lv.getV_credit_order_reg_number());
                childD.setLoanRegDate(new java.sql.Date(lv.getV_loan_reg_date().getTime()));

                childD.setLoanType(lv.getV_loan_type_id().shortValue());

                currentgroupDid=reportTool.getIdByGroupType(reportTemplate.getGroupType4(),supervisorPlanView);

                lastLoanAmount = 0;

                lastDisbursement =0;
                lastDisbursement = 0;
                lastPrincipalPayment = 0;
                lastInterestPayment = 0;
                lastCollectedInterestPayment = 0;
                lastCollectedPenaltyPayment = 0;


            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType5(),supervisorPlanView)!=currentgroupEid)
            {

                SupervisorPlanView pv = supervisorPlanView;

                childE = childD.addChild();
                childE.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType5(),supervisorPlanView));
                childE.setLevel((short)5);

                childE.setSp_Count(1);
                childD.setSp_Count(childD.getSp_Count()+1);
                childC.setSp_Count(childC.getSp_Count()+1);
                childA.setSp_Count(childA.getSp_Count()+1);
                childB.setSp_Count(childB.getSp_Count()+1);
                reportData.setSp_Count(reportData.getSp_Count()+1);

                childE.setSp_date(new java.sql.Date(pv.getV_sp_date().getTime()));
                childE.setSp_reg_date(new java.sql.Date(pv.getV_sp_reg_date().getTime()));
                childE.setSp_reg_by_id(pv.getV_sp_reg_by_id());
                childE.setSp_description(pv.getV_sp_description());



                if(pv.getV_sp_amount()>=0)
                {
                    reportData.setSp_amount(reportData.getSp_amount()+pv.getV_sp_amount());
                    childA.setSp_amount(childA.getSp_amount()+pv.getV_sp_amount());
                    childB.setSp_amount(childB.getSp_amount()+pv.getV_sp_amount());
                    childC.setSp_amount(childC.getSp_amount()+pv.getV_sp_amount());
                    childD.setSp_amount(childD.getSp_amount()+pv.getV_sp_amount());
                    childE.setSp_amount(pv.getV_sp_amount());
                }

                if(pv.getV_sp_principal()>=0)
                {
                    reportData.setSp_principal(reportData.getSp_principal()+pv.getV_sp_principal());
                    childA.setSp_principal(childA.getSp_principal()+pv.getV_sp_principal());
                    childB.setSp_principal(childB.getSp_principal()+pv.getV_sp_principal());
                    childC.setSp_principal(childC.getSp_principal()+pv.getV_sp_principal());
                    childD.setSp_principal(childD.getSp_principal()+pv.getV_sp_principal());
                    childE.setSp_principal(pv.getV_sp_principal());
                }

                if(pv.getV_sp_interest()>=0)
                {
                    reportData.setSp_interest(reportData.getSp_interest()+pv.getV_sp_interest());
                    childA.setSp_interest(childA.getSp_interest()+pv.getV_sp_interest());
                    childB.setSp_interest(childB.getSp_interest()+pv.getV_sp_interest());
                    childC.setSp_interest(childC.getSp_interest()+pv.getV_sp_interest());
                    childD.setSp_interest(childD.getSp_interest()+pv.getV_sp_interest());
                    childE.setSp_interest(pv.getV_sp_interest());
                }

                if(pv.getV_sp_penalty()>=0)
                {
                    reportData.setSp_penalty(reportData.getSp_penalty()+pv.getV_sp_penalty());
                    childA.setSp_penalty(childA.getSp_penalty()+pv.getV_sp_penalty());
                    childB.setSp_penalty(childB.getSp_penalty()+pv.getV_sp_penalty());
                    childC.setSp_penalty(childC.getSp_penalty()+pv.getV_sp_penalty());
                    childD.setSp_penalty(childD.getSp_penalty()+pv.getV_sp_penalty());
                    childE.setSp_penalty(pv.getV_sp_penalty());
                }


                currentgroupEid=reportTool.getIdByGroupType(reportTemplate.getGroupType5(),supervisorPlanView);
            }




        }


        return reportData;
    }




}
