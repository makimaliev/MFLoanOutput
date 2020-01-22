package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.manage.model.loan.PaymentSchedule;
import kg.gov.mf.loan.manage.model.process.LoanDetailedSummary;
import kg.gov.mf.loan.manage.model.process.LoanSummary;
import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import kg.gov.mf.loan.output.report.service.PaymentScheduleViewService;
import kg.gov.mf.loan.output.report.service.SupervisorPlanViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.*;


public class SupervisorPlanReportDataManager {
	
	/**
     * Report GENERATION TOOL
     */

    @Autowired
    LoanViewService loanViewService;

    @Autowired
    SupervisorPlanViewService supervisorPlanViewService;

    @Autowired
    PaymentScheduleViewService paymentScheduleViewService;


    private LinkedHashSet<PaymentScheduleView> paymentScheduleList = new LinkedHashSet<>(0);

    private LinkedHashSet<Long> paymentScheduleIdList = new LinkedHashSet<>(0);

    private LinkedHashMap<Long,List<PaymentScheduleView>> paymentScheduleMapByLoanId = new LinkedHashMap<>();

    public SupervisorPlanReportData getReportDataGrouped(SupervisorPlanReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        ReportTool reportTool = new ReportTool();

        LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<>();

        LinkedHashMap<String,List<String>> parameterSchedule = new LinkedHashMap<>();

        parameterS.putAll(reportTool.getParametersByTemplate(reportTemplate));

        reportData.getSupervisorPlanViews().addAll(supervisorPlanViewService.findByParameter(parameterS));

        for (Map.Entry<String, List<String>> parameterInLoop : parameterS.entrySet())
        {

            String parameterType = parameterInLoop.getKey();

            List<String> ids = parameterInLoop.getValue();

            String propertyName = parameterType.substring(4,parameterType.length());

            if(propertyName.contains("v_sp_date"))
            {
                parameterType = parameterType.replace("v_sp_date","v_ps_expected_date");
            }

            parameterSchedule.put(parameterType,ids);
        }

        if(parameterSchedule.size()>0)
        {
            for (PaymentScheduleView scheduleInLoop:paymentScheduleViewService.findByParameter(parameterSchedule))
            {
                paymentScheduleList.add(scheduleInLoop);

                if(paymentScheduleMapByLoanId.get(scheduleInLoop.getV_loan_id())==null)
                {
                    List<PaymentScheduleView> psViews = new ArrayList<>();

                    psViews.add(scheduleInLoop);

                    paymentScheduleMapByLoanId.put(scheduleInLoop.getV_loan_id(), psViews);

                }
                else
                {
                    paymentScheduleMapByLoanId.get(scheduleInLoop.getV_loan_id()).add(scheduleInLoop);
                }
            }
        }

        return groupifyData(reportData, reportTemplate, reportTool );

    }

    public SupervisorPlanReportData groupifyData(SupervisorPlanReportData reportData, ReportTemplate reportTemplate, ReportTool reportTool)
    {

        reportTool.initReference();
        reportTool.initCurrencyRatesMap(reportTemplate);

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

        double rate=1;
        double rate2=1;

        double thousands = 1000;

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

            }

            boolean isScheduleDataSet = false;

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType5(),supervisorPlanView)!=currentgroupEid)
            {

                SupervisorPlanView pv = supervisorPlanView;



                childE = childD.addChild();
                childE.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType5(),supervisorPlanView));
                childE.setLevel((short)5);

                if(childE!=null && !isScheduleDataSet)
                {
                    try {

                        if(paymentScheduleMapByLoanId.get(pv.getV_loan_id())!=null)
                        {
                            for (PaymentScheduleView scheduleInLoop: paymentScheduleMapByLoanId.get(pv.getV_loan_id())) {

                                double totalScheduleAmount = scheduleInLoop.getV_ps_principal_payment()/thousands+
                                        scheduleInLoop.getV_ps_interest_payment()/thousands +
                                        scheduleInLoop.getV_ps_collected_interest_payment()/thousands +
                                        scheduleInLoop.getV_ps_collected_penalty_payment()/thousands;

                                if(!paymentScheduleIdList.contains(scheduleInLoop.getV_ps_id()))
                                {
                                    paymentScheduleIdList.add(scheduleInLoop.getV_ps_id());

                                    if(scheduleInLoop.getV_loan_currency_id()>1)
                                    {
                                        try {
                                            rate = reportTool.getCurrencyRateValueByDateAndCurrencyTypeId(reportTemplate.getOnDate(),scheduleInLoop.getV_loan_currency_id());

                                            totalScheduleAmount=totalScheduleAmount*rate;

                                            if(reportTemplate.getAdditionalDate()!=null)
                                            {
                                                rate2 = reportTool.getCurrencyRateValueByDateAndCurrencyTypeId(reportTemplate.getAdditionalDate(),scheduleInLoop.getV_loan_currency_id());
                                            }

                                        }
                                        catch (Exception ex)
                                        {
                                        }

                                    }

                                    switch(scheduleInLoop.getV_ps_expected_date().getMonth())
                                    {
                                        case 0:
                                            reportData.setPs_month1(reportData.getPs_month1()+totalScheduleAmount);
                                            childA.setPs_month1(childA.getPs_month1()+totalScheduleAmount);
                                            childB.setPs_month1(childB.getPs_month1()+totalScheduleAmount);
                                            childC.setPs_month1(childC.getPs_month1()+totalScheduleAmount);
                                            childD.setPs_month1(childD.getPs_month1()+totalScheduleAmount);
                                            childE.setPs_month1(totalScheduleAmount);

                                            reportData.setPs_quarter1(reportData.getPs_quarter1()+totalScheduleAmount);
                                            childA.setPs_quarter1(childA.getPs_quarter1()+totalScheduleAmount);
                                            childB.setPs_quarter1(childB.getPs_quarter1()+totalScheduleAmount);
                                            childC.setPs_quarter1(childC.getPs_quarter1()+totalScheduleAmount);
                                            childD.setPs_quarter1(childD.getPs_quarter1()+totalScheduleAmount);
                                            childE.setPs_quarter1(totalScheduleAmount);

                                            reportData.setPs_year(reportData.getPs_year()+totalScheduleAmount);
                                            childA.setPs_year(childA.getPs_year()+totalScheduleAmount);
                                            childB.setPs_year(childB.getPs_year()+totalScheduleAmount);
                                            childC.setPs_year(childC.getPs_year()+totalScheduleAmount);
                                            childD.setPs_year(childD.getPs_year()+totalScheduleAmount);
                                            childE.setPs_year(totalScheduleAmount);

                                            break;

                                        case 1:
                                            reportData.setPs_month2(reportData.getPs_month2()+totalScheduleAmount);
                                            childA.setPs_month2(childA.getPs_month2()+totalScheduleAmount);
                                            childB.setPs_month2(childB.getPs_month2()+totalScheduleAmount);
                                            childC.setPs_month2(childC.getPs_month2()+totalScheduleAmount);
                                            childD.setPs_month2(childD.getPs_month2()+totalScheduleAmount);
                                            childE.setPs_month2(totalScheduleAmount);

                                            reportData.setPs_quarter1(reportData.getPs_quarter1()+totalScheduleAmount);
                                            childA.setPs_quarter1(childA.getPs_quarter1()+totalScheduleAmount);
                                            childB.setPs_quarter1(childB.getPs_quarter1()+totalScheduleAmount);
                                            childC.setPs_quarter1(childC.getPs_quarter1()+totalScheduleAmount);
                                            childD.setPs_quarter1(childD.getPs_quarter1()+totalScheduleAmount);
                                            childE.setPs_quarter1(totalScheduleAmount);

                                            reportData.setPs_year(reportData.getPs_year()+totalScheduleAmount);
                                            childA.setPs_year(childA.getPs_year()+totalScheduleAmount);
                                            childB.setPs_year(childB.getPs_year()+totalScheduleAmount);
                                            childC.setPs_year(childC.getPs_year()+totalScheduleAmount);
                                            childD.setPs_year(childD.getPs_year()+totalScheduleAmount);
                                            childE.setPs_year(totalScheduleAmount);

                                            break;

                                        case 2:
                                            reportData.setPs_month3(reportData.getPs_month3()+totalScheduleAmount);
                                            childA.setPs_month3(childA.getPs_month3()+totalScheduleAmount);
                                            childB.setPs_month3(childB.getPs_month3()+totalScheduleAmount);
                                            childC.setPs_month3(childC.getPs_month3()+totalScheduleAmount);
                                            childD.setPs_month3(childD.getPs_month3()+totalScheduleAmount);
                                            childE.setPs_month3(totalScheduleAmount);

                                            reportData.setPs_quarter1(reportData.getPs_quarter1()+totalScheduleAmount);
                                            childA.setPs_quarter1(childA.getPs_quarter1()+totalScheduleAmount);
                                            childB.setPs_quarter1(childB.getPs_quarter1()+totalScheduleAmount);
                                            childC.setPs_quarter1(childC.getPs_quarter1()+totalScheduleAmount);
                                            childD.setPs_quarter1(childD.getPs_quarter1()+totalScheduleAmount);
                                            childE.setPs_quarter1(totalScheduleAmount);

                                            reportData.setPs_year(reportData.getPs_year()+totalScheduleAmount);
                                            childA.setPs_year(childA.getPs_year()+totalScheduleAmount);
                                            childB.setPs_year(childB.getPs_year()+totalScheduleAmount);
                                            childC.setPs_year(childC.getPs_year()+totalScheduleAmount);
                                            childD.setPs_year(childD.getPs_year()+totalScheduleAmount);
                                            childE.setPs_year(totalScheduleAmount);

                                            break;

                                        case 3:
                                            reportData.setPs_month4(reportData.getPs_month4()+totalScheduleAmount);
                                            childA.setPs_month4(childA.getPs_month4()+totalScheduleAmount);
                                            childB.setPs_month4(childB.getPs_month4()+totalScheduleAmount);
                                            childC.setPs_month4(childC.getPs_month4()+totalScheduleAmount);
                                            childD.setPs_month4(childD.getPs_month4()+totalScheduleAmount);
                                            childE.setPs_month4(totalScheduleAmount);

                                            reportData.setPs_quarter2(reportData.getPs_quarter2()+totalScheduleAmount);
                                            childA.setPs_quarter2(childA.getPs_quarter2()+totalScheduleAmount);
                                            childB.setPs_quarter2(childB.getPs_quarter2()+totalScheduleAmount);
                                            childC.setPs_quarter2(childC.getPs_quarter2()+totalScheduleAmount);
                                            childD.setPs_quarter2(childD.getPs_quarter2()+totalScheduleAmount);
                                            childE.setPs_quarter2(totalScheduleAmount);

                                            reportData.setPs_year(reportData.getPs_year()+totalScheduleAmount);
                                            childA.setPs_year(childA.getPs_year()+totalScheduleAmount);
                                            childB.setPs_year(childB.getPs_year()+totalScheduleAmount);
                                            childC.setPs_year(childC.getPs_year()+totalScheduleAmount);
                                            childD.setPs_year(childD.getPs_year()+totalScheduleAmount);
                                            childE.setPs_year(totalScheduleAmount);

                                            break;

                                        case 4:
                                            reportData.setPs_month5(reportData.getPs_month5()+totalScheduleAmount);
                                            childA.setPs_month5(childA.getPs_month5()+totalScheduleAmount);
                                            childB.setPs_month5(childB.getPs_month5()+totalScheduleAmount);
                                            childC.setPs_month5(childC.getPs_month5()+totalScheduleAmount);
                                            childD.setPs_month5(childD.getPs_month5()+totalScheduleAmount);
                                            childE.setPs_month5(totalScheduleAmount);

                                            reportData.setPs_quarter2(reportData.getPs_quarter2()+totalScheduleAmount);
                                            childA.setPs_quarter2(childA.getPs_quarter2()+totalScheduleAmount);
                                            childB.setPs_quarter2(childB.getPs_quarter2()+totalScheduleAmount);
                                            childC.setPs_quarter2(childC.getPs_quarter2()+totalScheduleAmount);
                                            childD.setPs_quarter2(childD.getPs_quarter2()+totalScheduleAmount);
                                            childE.setPs_quarter2(totalScheduleAmount);

                                            reportData.setPs_year(reportData.getPs_year()+totalScheduleAmount);
                                            childA.setPs_year(childA.getPs_year()+totalScheduleAmount);
                                            childB.setPs_year(childB.getPs_year()+totalScheduleAmount);
                                            childC.setPs_year(childC.getPs_year()+totalScheduleAmount);
                                            childD.setPs_year(childD.getPs_year()+totalScheduleAmount);
                                            childE.setPs_year(totalScheduleAmount);

                                            break;

                                        case 5:
                                            reportData.setPs_month6(reportData.getPs_month6()+totalScheduleAmount);
                                            childA.setPs_month6(childA.getPs_month6()+totalScheduleAmount);
                                            childB.setPs_month6(childB.getPs_month6()+totalScheduleAmount);
                                            childC.setPs_month6(childC.getPs_month6()+totalScheduleAmount);
                                            childD.setPs_month6(childD.getPs_month6()+totalScheduleAmount);
                                            childE.setPs_month6(totalScheduleAmount);

                                            reportData.setPs_quarter2(reportData.getPs_quarter2()+totalScheduleAmount);
                                            childA.setPs_quarter2(childA.getPs_quarter2()+totalScheduleAmount);
                                            childB.setPs_quarter2(childB.getPs_quarter2()+totalScheduleAmount);
                                            childC.setPs_quarter2(childC.getPs_quarter2()+totalScheduleAmount);
                                            childD.setPs_quarter2(childD.getPs_quarter2()+totalScheduleAmount);
                                            childE.setPs_quarter2(totalScheduleAmount);

                                            reportData.setPs_year(reportData.getPs_year()+totalScheduleAmount);
                                            childA.setPs_year(childA.getPs_year()+totalScheduleAmount);
                                            childB.setPs_year(childB.getPs_year()+totalScheduleAmount);
                                            childC.setPs_year(childC.getPs_year()+totalScheduleAmount);
                                            childD.setPs_year(childD.getPs_year()+totalScheduleAmount);
                                            childE.setPs_year(totalScheduleAmount);

                                            break;

                                        case 6:
                                            reportData.setPs_month7(reportData.getPs_month7()+totalScheduleAmount);
                                            childA.setPs_month7(childA.getPs_month7()+totalScheduleAmount);
                                            childB.setPs_month7(childB.getPs_month7()+totalScheduleAmount);
                                            childC.setPs_month7(childC.getPs_month7()+totalScheduleAmount);
                                            childD.setPs_month7(childD.getPs_month7()+totalScheduleAmount);
                                            childE.setPs_month7(totalScheduleAmount);

                                            reportData.setPs_quarter3(reportData.getPs_quarter3()+totalScheduleAmount);
                                            childA.setPs_quarter3(childA.getPs_quarter3()+totalScheduleAmount);
                                            childB.setPs_quarter3(childB.getPs_quarter3()+totalScheduleAmount);
                                            childC.setPs_quarter3(childC.getPs_quarter3()+totalScheduleAmount);
                                            childD.setPs_quarter3(childD.getPs_quarter3()+totalScheduleAmount);
                                            childE.setPs_quarter3(totalScheduleAmount);

                                            reportData.setPs_year(reportData.getPs_year()+totalScheduleAmount);
                                            childA.setPs_year(childA.getPs_year()+totalScheduleAmount);
                                            childB.setPs_year(childB.getPs_year()+totalScheduleAmount);
                                            childC.setPs_year(childC.getPs_year()+totalScheduleAmount);
                                            childD.setPs_year(childD.getPs_year()+totalScheduleAmount);
                                            childE.setPs_year(totalScheduleAmount);

                                            break;

                                        case 7:
                                            reportData.setPs_month8(reportData.getPs_month8()+totalScheduleAmount);
                                            childA.setPs_month8(childA.getPs_month8()+totalScheduleAmount);
                                            childB.setPs_month8(childB.getPs_month8()+totalScheduleAmount);
                                            childC.setPs_month8(childC.getPs_month8()+totalScheduleAmount);
                                            childD.setPs_month8(childD.getPs_month8()+totalScheduleAmount);
                                            childE.setPs_month8(totalScheduleAmount);

                                            reportData.setPs_quarter3(reportData.getPs_quarter3()+totalScheduleAmount);
                                            childA.setPs_quarter3(childA.getPs_quarter3()+totalScheduleAmount);
                                            childB.setPs_quarter3(childB.getPs_quarter3()+totalScheduleAmount);
                                            childC.setPs_quarter3(childC.getPs_quarter3()+totalScheduleAmount);
                                            childD.setPs_quarter3(childD.getPs_quarter3()+totalScheduleAmount);
                                            childE.setPs_quarter3(totalScheduleAmount);

                                            reportData.setPs_year(reportData.getPs_year()+totalScheduleAmount);
                                            childA.setPs_year(childA.getPs_year()+totalScheduleAmount);
                                            childB.setPs_year(childB.getPs_year()+totalScheduleAmount);
                                            childC.setPs_year(childC.getPs_year()+totalScheduleAmount);
                                            childD.setPs_year(childD.getPs_year()+totalScheduleAmount);
                                            childE.setPs_year(totalScheduleAmount);

                                            break;

                                        case 8:
                                            reportData.setPs_month9(reportData.getPs_month9()+totalScheduleAmount);
                                            childA.setPs_month9(childA.getPs_month9()+totalScheduleAmount);
                                            childB.setPs_month9(childB.getPs_month9()+totalScheduleAmount);
                                            childC.setPs_month9(childC.getPs_month9()+totalScheduleAmount);
                                            childD.setPs_month9(childD.getPs_month9()+totalScheduleAmount);
                                            childE.setPs_month9(totalScheduleAmount);

                                            reportData.setPs_quarter3(reportData.getPs_quarter3()+totalScheduleAmount);
                                            childA.setPs_quarter3(childA.getPs_quarter3()+totalScheduleAmount);
                                            childB.setPs_quarter3(childB.getPs_quarter3()+totalScheduleAmount);
                                            childC.setPs_quarter3(childC.getPs_quarter3()+totalScheduleAmount);
                                            childD.setPs_quarter3(childD.getPs_quarter3()+totalScheduleAmount);
                                            childE.setPs_quarter3(totalScheduleAmount);

                                            reportData.setPs_year(reportData.getPs_year()+totalScheduleAmount);
                                            childA.setPs_year(childA.getPs_year()+totalScheduleAmount);
                                            childB.setPs_year(childB.getPs_year()+totalScheduleAmount);
                                            childC.setPs_year(childC.getPs_year()+totalScheduleAmount);
                                            childD.setPs_year(childD.getPs_year()+totalScheduleAmount);
                                            childE.setPs_year(totalScheduleAmount);

                                            break;

                                        case 9:
                                            reportData.setPs_month10(reportData.getPs_month10()+totalScheduleAmount);
                                            childA.setPs_month10(childA.getPs_month10()+totalScheduleAmount);
                                            childB.setPs_month10(childB.getPs_month10()+totalScheduleAmount);
                                            childC.setPs_month10(childC.getPs_month10()+totalScheduleAmount);
                                            childD.setPs_month10(childD.getPs_month10()+totalScheduleAmount);
                                            childE.setPs_month10(totalScheduleAmount);

                                            reportData.setPs_quarter4(reportData.getPs_quarter4()+totalScheduleAmount);
                                            childA.setPs_quarter4(childA.getPs_quarter4()+totalScheduleAmount);
                                            childB.setPs_quarter4(childB.getPs_quarter4()+totalScheduleAmount);
                                            childC.setPs_quarter4(childC.getPs_quarter4()+totalScheduleAmount);
                                            childD.setPs_quarter4(childD.getPs_quarter4()+totalScheduleAmount);
                                            childE.setPs_quarter4(totalScheduleAmount);

                                            reportData.setPs_year(reportData.getPs_year()+totalScheduleAmount);
                                            childA.setPs_year(childA.getPs_year()+totalScheduleAmount);
                                            childB.setPs_year(childB.getPs_year()+totalScheduleAmount);
                                            childC.setPs_year(childC.getPs_year()+totalScheduleAmount);
                                            childD.setPs_year(childD.getPs_year()+totalScheduleAmount);
                                            childE.setPs_year(totalScheduleAmount);

                                            break;

                                        case 10:
                                            reportData.setPs_month11(reportData.getPs_month11()+totalScheduleAmount);
                                            childA.setPs_month11(childA.getPs_month11()+totalScheduleAmount);
                                            childB.setPs_month11(childB.getPs_month11()+totalScheduleAmount);
                                            childC.setPs_month11(childC.getPs_month11()+totalScheduleAmount);
                                            childD.setPs_month11(childD.getPs_month11()+totalScheduleAmount);
                                            childE.setPs_month11(totalScheduleAmount);

                                            reportData.setPs_quarter4(reportData.getPs_quarter4()+totalScheduleAmount);
                                            childA.setPs_quarter4(childA.getPs_quarter4()+totalScheduleAmount);
                                            childB.setPs_quarter4(childB.getPs_quarter4()+totalScheduleAmount);
                                            childC.setPs_quarter4(childC.getPs_quarter4()+totalScheduleAmount);
                                            childD.setPs_quarter4(childD.getPs_quarter4()+totalScheduleAmount);
                                            childE.setPs_quarter4(totalScheduleAmount);

                                            reportData.setPs_year(reportData.getPs_year()+totalScheduleAmount);
                                            childA.setPs_year(childA.getPs_year()+totalScheduleAmount);
                                            childB.setPs_year(childB.getPs_year()+totalScheduleAmount);
                                            childC.setPs_year(childC.getPs_year()+totalScheduleAmount);
                                            childD.setPs_year(childD.getPs_year()+totalScheduleAmount);
                                            childE.setPs_year(totalScheduleAmount);

                                            break;

                                        case 11:
                                            reportData.setPs_month12(reportData.getPs_month12()+totalScheduleAmount);
                                            childA.setPs_month12(childA.getPs_month12()+totalScheduleAmount);
                                            childB.setPs_month12(childB.getPs_month12()+totalScheduleAmount);
                                            childC.setPs_month12(childC.getPs_month12()+totalScheduleAmount);
                                            childD.setPs_month12(childD.getPs_month12()+totalScheduleAmount);
                                            childE.setPs_month12(totalScheduleAmount);

                                            reportData.setPs_quarter4(reportData.getPs_quarter4()+totalScheduleAmount);
                                            childA.setPs_quarter4(childA.getPs_quarter4()+totalScheduleAmount);
                                            childB.setPs_quarter4(childB.getPs_quarter4()+totalScheduleAmount);
                                            childC.setPs_quarter4(childC.getPs_quarter4()+totalScheduleAmount);
                                            childD.setPs_quarter4(childD.getPs_quarter4()+totalScheduleAmount);
                                            childE.setPs_quarter4(totalScheduleAmount);

                                            reportData.setPs_year(reportData.getPs_year()+totalScheduleAmount);
                                            childA.setPs_year(childA.getPs_year()+totalScheduleAmount);
                                            childB.setPs_year(childB.getPs_year()+totalScheduleAmount);
                                            childC.setPs_year(childC.getPs_year()+totalScheduleAmount);
                                            childD.setPs_year(childD.getPs_year()+totalScheduleAmount);
                                            childE.setPs_year(totalScheduleAmount);

                                            break;

                                    }


                                }

                            }

                        }


                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex+" ERROR");
                    }


                }

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





                if(childE.getPs_year()>0)
                {
                    childE.setSp_description(String.valueOf(childD.getPs_year()));
                }


                pv.setV_sp_amount(pv.getV_sp_amount()/thousands);
                pv.setV_sp_principal(pv.getV_sp_principal()/thousands);
                pv.setV_sp_interest(pv.getV_sp_interest()/thousands);
                pv.setV_sp_penalty(pv.getV_sp_penalty()/thousands);









                if(pv.getV_sp_amount()>=0)
                {
                    reportData.setSp_amount(reportData.getSp_amount()+pv.getV_sp_amount());
                    childA.setSp_amount(childA.getSp_amount()+pv.getV_sp_amount());
                    childB.setSp_amount(childB.getSp_amount()+pv.getV_sp_amount());
                    childC.setSp_amount(childC.getSp_amount()+pv.getV_sp_amount());
                    childD.setSp_amount(childD.getSp_amount()+pv.getV_sp_amount());
                    childE.setSp_amount(pv.getV_sp_amount());


                    switch(pv.getV_sp_date().getMonth())
                    {
                        case 0 :

                            reportData.setSp_month1(reportData.getSp_month1()+pv.getV_sp_amount());
                            childA.setSp_month1(childA.getSp_month1()+pv.getV_sp_amount());
                            childB.setSp_month1(childB.getSp_month1()+pv.getV_sp_amount());
                            childC.setSp_month1(childC.getSp_month1()+pv.getV_sp_amount());
                            childD.setSp_month1(childD.getSp_month1()+pv.getV_sp_amount());
                            childE.setSp_month1(pv.getV_sp_amount());

                            reportData.setSp_quarter1(reportData.getSp_quarter1()+pv.getV_sp_amount());
                            childA.setSp_quarter1(childA.getSp_quarter1()+pv.getV_sp_amount());
                            childB.setSp_quarter1(childB.getSp_quarter1()+pv.getV_sp_amount());
                            childC.setSp_quarter1(childC.getSp_quarter1()+pv.getV_sp_amount());
                            childD.setSp_quarter1(childD.getSp_quarter1()+pv.getV_sp_amount());
                            childE.setSp_quarter1(pv.getV_sp_amount());

                            reportData.setSp_year(reportData.getSp_year()+pv.getV_sp_amount());
                            childA.setSp_year(childA.getSp_year()+pv.getV_sp_amount());
                            childB.setSp_year(childB.getSp_year()+pv.getV_sp_amount());
                            childC.setSp_year(childC.getSp_year()+pv.getV_sp_amount());
                            childD.setSp_year(childD.getSp_year()+pv.getV_sp_amount());
                            childE.setSp_year(pv.getV_sp_amount());

                            break;


                        case 1 :

                            reportData.setSp_month2(reportData.getSp_month2()+pv.getV_sp_amount());
                            childA.setSp_month2(childA.getSp_month2()+pv.getV_sp_amount());
                            childB.setSp_month2(childB.getSp_month2()+pv.getV_sp_amount());
                            childC.setSp_month2(childC.getSp_month2()+pv.getV_sp_amount());
                            childD.setSp_month2(childD.getSp_month2()+pv.getV_sp_amount());
                            childE.setSp_month2(pv.getV_sp_amount());

                            reportData.setSp_quarter1(reportData.getSp_quarter1()+pv.getV_sp_amount());
                            childA.setSp_quarter1(childA.getSp_quarter1()+pv.getV_sp_amount());
                            childB.setSp_quarter1(childB.getSp_quarter1()+pv.getV_sp_amount());
                            childC.setSp_quarter1(childC.getSp_quarter1()+pv.getV_sp_amount());
                            childD.setSp_quarter1(childD.getSp_quarter1()+pv.getV_sp_amount());
                            childE.setSp_quarter1(pv.getV_sp_amount());

                            reportData.setSp_year(reportData.getSp_year()+pv.getV_sp_amount());
                            childA.setSp_year(childA.getSp_year()+pv.getV_sp_amount());
                            childB.setSp_year(childB.getSp_year()+pv.getV_sp_amount());
                            childC.setSp_year(childC.getSp_year()+pv.getV_sp_amount());
                            childD.setSp_year(childD.getSp_year()+pv.getV_sp_amount());
                            childE.setSp_year(pv.getV_sp_amount());

                            break;

                        case 2 :

                            reportData.setSp_month3(reportData.getSp_month3()+pv.getV_sp_amount());
                            childA.setSp_month3(childA.getSp_month3()+pv.getV_sp_amount());
                            childB.setSp_month3(childB.getSp_month3()+pv.getV_sp_amount());
                            childC.setSp_month3(childC.getSp_month3()+pv.getV_sp_amount());
                            childD.setSp_month3(childD.getSp_month3()+pv.getV_sp_amount());
                            childE.setSp_month3(pv.getV_sp_amount());

                            reportData.setSp_quarter1(reportData.getSp_quarter1()+pv.getV_sp_amount());
                            childA.setSp_quarter1(childA.getSp_quarter1()+pv.getV_sp_amount());
                            childB.setSp_quarter1(childB.getSp_quarter1()+pv.getV_sp_amount());
                            childC.setSp_quarter1(childC.getSp_quarter1()+pv.getV_sp_amount());
                            childD.setSp_quarter1(childD.getSp_quarter1()+pv.getV_sp_amount());
                            childE.setSp_quarter1(pv.getV_sp_amount());

                            reportData.setSp_year(reportData.getSp_year()+pv.getV_sp_amount());
                            childA.setSp_year(childA.getSp_year()+pv.getV_sp_amount());
                            childB.setSp_year(childB.getSp_year()+pv.getV_sp_amount());
                            childC.setSp_year(childC.getSp_year()+pv.getV_sp_amount());
                            childD.setSp_year(childD.getSp_year()+pv.getV_sp_amount());
                            childE.setSp_year(pv.getV_sp_amount());

                            break;


                        case 3 :

                            reportData.setSp_month4(reportData.getSp_month4()+pv.getV_sp_amount());
                            childA.setSp_month4(childA.getSp_month4()+pv.getV_sp_amount());
                            childB.setSp_month4(childB.getSp_month4()+pv.getV_sp_amount());
                            childC.setSp_month4(childC.getSp_month4()+pv.getV_sp_amount());
                            childD.setSp_month4(childD.getSp_month4()+pv.getV_sp_amount());
                            childE.setSp_month4(pv.getV_sp_amount());

                            reportData.setSp_quarter2(reportData.getSp_quarter2()+pv.getV_sp_amount());
                            childA.setSp_quarter2(childA.getSp_quarter2()+pv.getV_sp_amount());
                            childB.setSp_quarter2(childB.getSp_quarter2()+pv.getV_sp_amount());
                            childC.setSp_quarter2(childC.getSp_quarter2()+pv.getV_sp_amount());
                            childD.setSp_quarter2(childD.getSp_quarter2()+pv.getV_sp_amount());
                            childE.setSp_quarter2(pv.getV_sp_amount());

                            reportData.setSp_year(reportData.getSp_year()+pv.getV_sp_amount());
                            childA.setSp_year(childA.getSp_year()+pv.getV_sp_amount());
                            childB.setSp_year(childB.getSp_year()+pv.getV_sp_amount());
                            childC.setSp_year(childC.getSp_year()+pv.getV_sp_amount());
                            childD.setSp_year(childD.getSp_year()+pv.getV_sp_amount());
                            childE.setSp_year(pv.getV_sp_amount());

                            break;


                        case 4 :

                            reportData.setSp_month5(reportData.getSp_month5()+pv.getV_sp_amount());
                            childA.setSp_month5(childA.getSp_month5()+pv.getV_sp_amount());
                            childB.setSp_month5(childB.getSp_month5()+pv.getV_sp_amount());
                            childC.setSp_month5(childC.getSp_month5()+pv.getV_sp_amount());
                            childD.setSp_month5(childD.getSp_month5()+pv.getV_sp_amount());
                            childE.setSp_month5(pv.getV_sp_amount());

                            reportData.setSp_quarter2(reportData.getSp_quarter2()+pv.getV_sp_amount());
                            childA.setSp_quarter2(childA.getSp_quarter2()+pv.getV_sp_amount());
                            childB.setSp_quarter2(childB.getSp_quarter2()+pv.getV_sp_amount());
                            childC.setSp_quarter2(childC.getSp_quarter2()+pv.getV_sp_amount());
                            childD.setSp_quarter2(childD.getSp_quarter2()+pv.getV_sp_amount());
                            childE.setSp_quarter2(pv.getV_sp_amount());

                            reportData.setSp_year(reportData.getSp_year()+pv.getV_sp_amount());
                            childA.setSp_year(childA.getSp_year()+pv.getV_sp_amount());
                            childB.setSp_year(childB.getSp_year()+pv.getV_sp_amount());
                            childC.setSp_year(childC.getSp_year()+pv.getV_sp_amount());
                            childD.setSp_year(childD.getSp_year()+pv.getV_sp_amount());
                            childE.setSp_year(pv.getV_sp_amount());

                            break;

                        case 5 :

                            reportData.setSp_month6(reportData.getSp_month6()+pv.getV_sp_amount());
                            childA.setSp_month6(childA.getSp_month6()+pv.getV_sp_amount());
                            childB.setSp_month6(childB.getSp_month6()+pv.getV_sp_amount());
                            childC.setSp_month6(childC.getSp_month6()+pv.getV_sp_amount());
                            childD.setSp_month6(childD.getSp_month6()+pv.getV_sp_amount());
                            childE.setSp_month6(pv.getV_sp_amount());

                            reportData.setSp_quarter2(reportData.getSp_quarter2()+pv.getV_sp_amount());
                            childA.setSp_quarter2(childA.getSp_quarter2()+pv.getV_sp_amount());
                            childB.setSp_quarter2(childB.getSp_quarter2()+pv.getV_sp_amount());
                            childC.setSp_quarter2(childC.getSp_quarter2()+pv.getV_sp_amount());
                            childD.setSp_quarter2(childD.getSp_quarter2()+pv.getV_sp_amount());
                            childE.setSp_quarter2(pv.getV_sp_amount());

                            reportData.setSp_year(reportData.getSp_year()+pv.getV_sp_amount());
                            childA.setSp_year(childA.getSp_year()+pv.getV_sp_amount());
                            childB.setSp_year(childB.getSp_year()+pv.getV_sp_amount());
                            childC.setSp_year(childC.getSp_year()+pv.getV_sp_amount());
                            childD.setSp_year(childD.getSp_year()+pv.getV_sp_amount());
                            childE.setSp_year(pv.getV_sp_amount());

                            break;


                        case 6 :

                            reportData.setSp_month7(reportData.getSp_month7()+pv.getV_sp_amount());
                            childA.setSp_month7(childA.getSp_month7()+pv.getV_sp_amount());
                            childB.setSp_month7(childB.getSp_month7()+pv.getV_sp_amount());
                            childC.setSp_month7(childC.getSp_month7()+pv.getV_sp_amount());
                            childD.setSp_month7(childD.getSp_month7()+pv.getV_sp_amount());
                            childE.setSp_month7(pv.getV_sp_amount());

                            reportData.setSp_quarter3(reportData.getSp_quarter3()+pv.getV_sp_amount());
                            childA.setSp_quarter3(childA.getSp_quarter3()+pv.getV_sp_amount());
                            childB.setSp_quarter3(childB.getSp_quarter3()+pv.getV_sp_amount());
                            childC.setSp_quarter3(childC.getSp_quarter3()+pv.getV_sp_amount());
                            childD.setSp_quarter3(childD.getSp_quarter3()+pv.getV_sp_amount());
                            childE.setSp_quarter3(pv.getV_sp_amount());

                            reportData.setSp_year(reportData.getSp_year()+pv.getV_sp_amount());
                            childA.setSp_year(childA.getSp_year()+pv.getV_sp_amount());
                            childB.setSp_year(childB.getSp_year()+pv.getV_sp_amount());
                            childC.setSp_year(childC.getSp_year()+pv.getV_sp_amount());
                            childD.setSp_year(childD.getSp_year()+pv.getV_sp_amount());
                            childE.setSp_year(pv.getV_sp_amount());

                            break;


                        case 7 :

                            reportData.setSp_month8(reportData.getSp_month8()+pv.getV_sp_amount());
                            childA.setSp_month8(childA.getSp_month8()+pv.getV_sp_amount());
                            childB.setSp_month8(childB.getSp_month8()+pv.getV_sp_amount());
                            childC.setSp_month8(childC.getSp_month8()+pv.getV_sp_amount());
                            childD.setSp_month8(childD.getSp_month8()+pv.getV_sp_amount());
                            childE.setSp_month8(pv.getV_sp_amount());

                            reportData.setSp_quarter3(reportData.getSp_quarter3()+pv.getV_sp_amount());
                            childA.setSp_quarter3(childA.getSp_quarter3()+pv.getV_sp_amount());
                            childB.setSp_quarter3(childB.getSp_quarter3()+pv.getV_sp_amount());
                            childC.setSp_quarter3(childC.getSp_quarter3()+pv.getV_sp_amount());
                            childD.setSp_quarter3(childD.getSp_quarter3()+pv.getV_sp_amount());
                            childE.setSp_quarter3(pv.getV_sp_amount());

                            reportData.setSp_year(reportData.getSp_year()+pv.getV_sp_amount());
                            childA.setSp_year(childA.getSp_year()+pv.getV_sp_amount());
                            childB.setSp_year(childB.getSp_year()+pv.getV_sp_amount());
                            childC.setSp_year(childC.getSp_year()+pv.getV_sp_amount());
                            childD.setSp_year(childD.getSp_year()+pv.getV_sp_amount());
                            childE.setSp_year(pv.getV_sp_amount());

                            break;


                        case 8 :

                            reportData.setSp_month9(reportData.getSp_month9()+pv.getV_sp_amount());
                            childA.setSp_month9(childA.getSp_month9()+pv.getV_sp_amount());
                            childB.setSp_month9(childB.getSp_month9()+pv.getV_sp_amount());
                            childC.setSp_month9(childC.getSp_month9()+pv.getV_sp_amount());
                            childD.setSp_month9(childD.getSp_month9()+pv.getV_sp_amount());
                            childE.setSp_month9(pv.getV_sp_amount());

                            reportData.setSp_quarter3(reportData.getSp_quarter3()+pv.getV_sp_amount());
                            childA.setSp_quarter3(childA.getSp_quarter3()+pv.getV_sp_amount());
                            childB.setSp_quarter3(childB.getSp_quarter3()+pv.getV_sp_amount());
                            childC.setSp_quarter3(childC.getSp_quarter3()+pv.getV_sp_amount());
                            childD.setSp_quarter3(childD.getSp_quarter3()+pv.getV_sp_amount());
                            childE.setSp_quarter3(pv.getV_sp_amount());

                            reportData.setSp_year(reportData.getSp_year()+pv.getV_sp_amount());
                            childA.setSp_year(childA.getSp_year()+pv.getV_sp_amount());
                            childB.setSp_year(childB.getSp_year()+pv.getV_sp_amount());
                            childC.setSp_year(childC.getSp_year()+pv.getV_sp_amount());
                            childD.setSp_year(childD.getSp_year()+pv.getV_sp_amount());
                            childE.setSp_year(pv.getV_sp_amount());

                            break;



                        case 9 :

                            reportData.setSp_month10(reportData.getSp_month10()+pv.getV_sp_amount());
                            childA.setSp_month10(childA.getSp_month10()+pv.getV_sp_amount());
                            childB.setSp_month10(childB.getSp_month10()+pv.getV_sp_amount());
                            childC.setSp_month10(childC.getSp_month10()+pv.getV_sp_amount());
                            childD.setSp_month10(childD.getSp_month10()+pv.getV_sp_amount());
                            childE.setSp_month10(pv.getV_sp_amount());

                            reportData.setSp_quarter4(reportData.getSp_quarter4()+pv.getV_sp_amount());
                            childA.setSp_quarter4(childA.getSp_quarter4()+pv.getV_sp_amount());
                            childB.setSp_quarter4(childB.getSp_quarter4()+pv.getV_sp_amount());
                            childC.setSp_quarter4(childC.getSp_quarter4()+pv.getV_sp_amount());
                            childD.setSp_quarter4(childD.getSp_quarter4()+pv.getV_sp_amount());
                            childE.setSp_quarter4(pv.getV_sp_amount());

                            reportData.setSp_year(reportData.getSp_year()+pv.getV_sp_amount());
                            childA.setSp_year(childA.getSp_year()+pv.getV_sp_amount());
                            childB.setSp_year(childB.getSp_year()+pv.getV_sp_amount());
                            childC.setSp_year(childC.getSp_year()+pv.getV_sp_amount());
                            childD.setSp_year(childD.getSp_year()+pv.getV_sp_amount());
                            childE.setSp_year(pv.getV_sp_amount());

                            break;


                        case 10 :

                            reportData.setSp_month11(reportData.getSp_month11()+pv.getV_sp_amount());
                            childA.setSp_month11(childA.getSp_month11()+pv.getV_sp_amount());
                            childB.setSp_month11(childB.getSp_month11()+pv.getV_sp_amount());
                            childC.setSp_month11(childC.getSp_month11()+pv.getV_sp_amount());
                            childD.setSp_month11(childD.getSp_month11()+pv.getV_sp_amount());
                            childE.setSp_month11(pv.getV_sp_amount());

                            reportData.setSp_quarter4(reportData.getSp_quarter4()+pv.getV_sp_amount());
                            childA.setSp_quarter4(childA.getSp_quarter4()+pv.getV_sp_amount());
                            childB.setSp_quarter4(childB.getSp_quarter4()+pv.getV_sp_amount());
                            childC.setSp_quarter4(childC.getSp_quarter4()+pv.getV_sp_amount());
                            childD.setSp_quarter4(childD.getSp_quarter4()+pv.getV_sp_amount());
                            childE.setSp_quarter4(pv.getV_sp_amount());

                            reportData.setSp_year(reportData.getSp_year()+pv.getV_sp_amount());
                            childA.setSp_year(childA.getSp_year()+pv.getV_sp_amount());
                            childB.setSp_year(childB.getSp_year()+pv.getV_sp_amount());
                            childC.setSp_year(childC.getSp_year()+pv.getV_sp_amount());
                            childD.setSp_year(childD.getSp_year()+pv.getV_sp_amount());
                            childE.setSp_year(pv.getV_sp_amount());

                            break;


                        case 11 :

                            reportData.setSp_month12(reportData.getSp_month12()+pv.getV_sp_amount());
                            childA.setSp_month12(childA.getSp_month12()+pv.getV_sp_amount());
                            childB.setSp_month12(childB.getSp_month12()+pv.getV_sp_amount());
                            childC.setSp_month12(childC.getSp_month12()+pv.getV_sp_amount());
                            childD.setSp_month12(childD.getSp_month12()+pv.getV_sp_amount());
                            childE.setSp_month12(pv.getV_sp_amount());

                            reportData.setSp_quarter4(reportData.getSp_quarter4()+pv.getV_sp_amount());
                            childA.setSp_quarter4(childA.getSp_quarter4()+pv.getV_sp_amount());
                            childB.setSp_quarter4(childB.getSp_quarter4()+pv.getV_sp_amount());
                            childC.setSp_quarter4(childC.getSp_quarter4()+pv.getV_sp_amount());
                            childD.setSp_quarter4(childD.getSp_quarter4()+pv.getV_sp_amount());
                            childE.setSp_quarter4(pv.getV_sp_amount());

                            reportData.setSp_year(reportData.getSp_year()+pv.getV_sp_amount());
                            childA.setSp_year(childA.getSp_year()+pv.getV_sp_amount());
                            childB.setSp_year(childB.getSp_year()+pv.getV_sp_amount());
                            childC.setSp_year(childC.getSp_year()+pv.getV_sp_amount());
                            childD.setSp_year(childD.getSp_year()+pv.getV_sp_amount());
                            childE.setSp_year(pv.getV_sp_amount());

                            break;

                    }

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
