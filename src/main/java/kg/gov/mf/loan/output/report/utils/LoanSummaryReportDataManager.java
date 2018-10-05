package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.manage.model.process.LoanSummary;
import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import kg.gov.mf.loan.output.report.service.LoanSummaryViewService;
import kg.gov.mf.loan.output.report.service.ReferenceViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class LoanSummaryReportDataManager {
	
	/**
     * Report GENERATION TOOL
     */

    @Autowired
    LoanViewService loanViewService;

    @Autowired
    LoanSummaryViewService loanSummaryViewService;

    @Autowired
    ReferenceViewService referenceViewService;

    public LoanSummaryReportData getReportDataGrouped(LoanSummaryReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);




        ReportTool reportTool = new ReportTool();

        LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<>();

        parameterS.putAll(reportTool.getParametersByTemplate(reportTemplate));

        reportData.getLoanSummaryViews().addAll(loanSummaryViewService.findByParameter(parameterS));

        for (LoanSummaryView loanSummaryView: reportData.getLoanSummaryViews())
        {
            System.out.println(loanSummaryView.getV_debtor_name());
        }

        return groupifyData(reportData, reportTemplate, reportTool );

    }

    public LoanSummaryReportData groupifyData(LoanSummaryReportData reportData,ReportTemplate reportTemplate, ReportTool reportTool)
    {

        reportTool.initReference();



        long groupAid=0;
        long groupBid=0;
        long groupCid=0;
        long groupDid=0;
        long groupEid=0;
        long groupFid=0;

        long currentgroupAid=-1;
        long currentgroupBid=-1;
        long currentgroupCid=-1;
        long currentgroupDid=-1;
        long currentgroupEid=-1;
        long currentgroupFid=-1;

        LoanSummaryReportData childA = null;
        LoanSummaryReportData childB = null;
        LoanSummaryReportData childC = null;
        LoanSummaryReportData childD = null;
        LoanSummaryReportData childE = null;
        LoanSummaryReportData childF = null;



        double lastLoanAmount = 0;

        double lastDisbursement = 0;
        double lastTotalPaid = 0;
        double lastTotalOustanding = 0;
        double lastPrincipleOutstanding = 0;
        double lastInteresOutstanding = 0;
        double lastPenaltyOutstanding = 0;

        double lastTotalOverdue = 0;
        double lastPrincipleOverdue = 0;
        double lastInteresOverdue = 0;
        double lastPenaltyOverdue = 0;

        for (LoanSummaryView loanSummaryView:reportData.getLoanSummaryViews())
        {
            if(reportTool.getIdByGroupType(reportTemplate.getGroupType1(),loanSummaryView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType1(),loanSummaryView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(reportTemplate.getGroupType1(),loanSummaryView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType2(),loanSummaryView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType2(),loanSummaryView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(reportTemplate.getGroupType2(),loanSummaryView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType3(),loanSummaryView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType3(),loanSummaryView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                currentgroupCid=reportTool.getIdByGroupType(reportTemplate.getGroupType3(),loanSummaryView);
            }



            if(reportTool.getIdByGroupType(reportTemplate.getGroupType4(),loanSummaryView)!=currentgroupDid)
            {
                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType4(),loanSummaryView));
                childD.setLevel((short)4);

                childD.setDetailsCount(1);
                childC.setDetailsCount(childC.getDetailsCount()+1);
                childA.setDetailsCount(childA.getDetailsCount()+1);
                childB.setDetailsCount(childB.getDetailsCount()+1);
                reportData.setDetailsCount(reportData.getDetailsCount()+1);

                childD.setID(loanSummaryView.getV_loan_id());
                childD.setName(loanSummaryView.getV_credit_order_reg_number()+loanSummaryView.getV_loan_reg_number()+" от "+loanSummaryView.getV_loan_reg_date());
                childD.setCurrency(loanSummaryView.getV_loan_currency_id().shortValue());
                childD.setLoanRegNumber(loanSummaryView.getV_loan_reg_number());
                childD.setOrderRegNumber(loanSummaryView.getV_credit_order_reg_number());
                childD.setLoanRegDate(new java.sql.Date(loanSummaryView.getV_loan_reg_date().getTime()));

                childD.setLoanType(loanSummaryView.getV_loan_type_id().shortValue());

                lastLoanAmount = 0;

                lastDisbursement =0;
                lastTotalPaid = 0;
                lastTotalOustanding = 0;
                lastPrincipleOutstanding = 0;
                lastInteresOutstanding = 0;
                lastPenaltyOutstanding = 0;

                lastTotalOverdue = 0;
                lastPrincipleOverdue = 0;
                lastInteresOverdue = 0;
                lastPenaltyOverdue = 0;

                currentgroupDid=reportTool.getIdByGroupType(reportTemplate.getGroupType4(),loanSummaryView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType5(),loanSummaryView)!=currentgroupEid)
            {
                childE = childD.addChild();
                childE.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType5(),loanSummaryView));
                childE.setLevel((short)5);

                LoanSummaryView pv = loanSummaryView;

                childE = childD.addChild();
                childE.setName(" расчет на "+pv.getV_ls_on_date()+" : ");
                childE.setLevel((short)5);

                childE.setLevel((short)5);


                childE.setPaymentCount(1);
                childD.setPaymentCount(childD.getPaymentCount()+1);
                childC.setPaymentCount(childC.getPaymentCount()+1);
                childA.setPaymentCount(childA.getPaymentCount()+1);
                childB.setPaymentCount(childB.getPaymentCount()+1);
                reportData.setPaymentCount(reportData.getPaymentCount()+1);


                childE.setOnDate(new java.sql.Date(pv.getV_ls_on_date().getTime()));

                if(pv.getV_loan_amount()>=0)
                {

                    reportData.setLoanAmount(reportData.getLoanAmount()-lastLoanAmount+pv.getV_loan_amount());

                    childA.setLoanAmount(childA.getLoanAmount()-lastLoanAmount+pv.getV_loan_amount());


                    childB.setLoanAmount(childB.getLoanAmount()-lastLoanAmount+pv.getV_loan_amount());


                    childC.setLoanAmount(childC.getLoanAmount()-lastLoanAmount+pv.getV_loan_amount());

                    childD.setLoanAmount(childD.getLoanAmount()-lastLoanAmount+pv.getV_loan_amount());

                    childE.setLoanAmount(pv.getV_loan_amount());

                    lastLoanAmount  = pv.getV_loan_amount();


                }


                if(pv.getV_ls_total_disbursed()>=0)
                {

                    reportData.setTotalDisbursment(reportData.getTotalDisbursment()-lastDisbursement+pv.getV_ls_total_disbursed());

                    childA.setTotalDisbursment(childA.getTotalDisbursment()-lastDisbursement+pv.getV_ls_total_disbursed());


                    childB.setTotalDisbursment(childB.getTotalDisbursment()-lastDisbursement+pv.getV_ls_total_disbursed());


                    childC.setTotalDisbursment(childC.getTotalDisbursment()-lastDisbursement+pv.getV_ls_total_disbursed());

                    childD.setTotalDisbursment(childD.getTotalDisbursment()-lastDisbursement+pv.getV_ls_total_disbursed());

                    childE.setTotalDisbursment(pv.getV_ls_total_disbursed());

                    lastDisbursement = pv.getV_ls_total_disbursed();


                }

                if(pv.getV_ls_total_paid()>=0)
                {

                    reportData.setTotalPaid(reportData.getTotalPaid()-lastTotalPaid+pv.getV_ls_total_paid());

                    childA.setTotalPaid(childA.getTotalPaid()-lastTotalPaid+pv.getV_ls_total_paid());


                    childB.setTotalPaid(childB.getTotalPaid()-lastTotalPaid+pv.getV_ls_total_paid());


                    childC.setTotalPaid(childC.getTotalPaid()-lastTotalPaid+pv.getV_ls_total_paid());

                    childD.setTotalPaid(childD.getTotalPaid()-lastTotalPaid+pv.getV_ls_total_paid());

                    childE.setTotalPaid(pv.getV_ls_total_paid());

                    lastTotalPaid = pv.getV_ls_total_paid();


                }

                if(pv.getV_ls_total_outstanding()>=0)
                {

                    reportData.setRemainingSum(reportData.getRemainingSum()-lastTotalOustanding+pv.getV_ls_total_outstanding());

                    childA.setRemainingSum(childA.getRemainingSum()-lastTotalOustanding+pv.getV_ls_total_outstanding());


                    childB.setRemainingSum(childB.getRemainingSum()-lastTotalOustanding+pv.getV_ls_total_outstanding());


                    childC.setRemainingSum(childC.getRemainingSum()-lastTotalOustanding+pv.getV_ls_total_outstanding());

                    childD.setRemainingSum(childD.getRemainingSum()-lastTotalOustanding+pv.getV_ls_total_outstanding());

                    childE.setRemainingSum(pv.getV_ls_total_outstanding());

                    lastTotalOustanding = pv.getV_ls_total_outstanding();


                }


                if(pv.getV_ls_outstading_principal()>=0)
                {

                    reportData.setRemainingPrincipal(reportData.getRemainingPrincipal()-lastPrincipleOutstanding+pv.getV_ls_outstading_principal());

                    childA.setRemainingPrincipal(childA.getRemainingPrincipal()-lastPrincipleOutstanding+pv.getV_ls_outstading_principal());


                    childB.setRemainingPrincipal(childB.getRemainingPrincipal()-lastPrincipleOutstanding+pv.getV_ls_outstading_principal());


                    childC.setRemainingPrincipal(childC.getRemainingPrincipal()-lastPrincipleOutstanding+pv.getV_ls_outstading_principal());

                    childD.setRemainingPrincipal(childD.getRemainingPrincipal()-lastPrincipleOutstanding+pv.getV_ls_outstading_principal());

                    childE.setRemainingPrincipal(pv.getV_ls_outstading_principal());

                    lastPrincipleOutstanding = pv.getV_ls_outstading_principal();


                }


                if(pv.getV_ls_outstading_interest()>=0)
                {

                    reportData.setRemainingInterest(reportData.getRemainingInterest()-lastInteresOutstanding+pv.getV_ls_outstading_interest());

                    childA.setRemainingInterest(childA.getRemainingInterest()-lastInteresOutstanding+pv.getV_ls_outstading_interest());


                    childB.setRemainingInterest(childB.getRemainingInterest()-lastInteresOutstanding+pv.getV_ls_outstading_interest());


                    childC.setRemainingInterest(childC.getRemainingInterest()-lastInteresOutstanding+pv.getV_ls_outstading_interest());

                    childD.setRemainingInterest(childD.getRemainingInterest()-lastInteresOutstanding+pv.getV_ls_outstading_interest());

                    childE.setRemainingInterest(pv.getV_ls_outstading_interest());

                    lastInteresOutstanding = pv.getV_ls_outstading_interest();


                }

                if(pv.getV_ls_outstading_penalty()>=0)
                {

                    reportData.setRemainingPenalty(reportData.getRemainingPenalty()-lastPenaltyOutstanding+pv.getV_ls_outstading_penalty());

                    childA.setRemainingPenalty(childA.getRemainingPenalty()-lastPenaltyOutstanding+pv.getV_ls_outstading_penalty());


                    childB.setRemainingPenalty(childB.getRemainingPenalty()-lastPenaltyOutstanding+pv.getV_ls_outstading_penalty());


                    childC.setRemainingPenalty(childC.getRemainingPenalty()-lastPenaltyOutstanding+pv.getV_ls_outstading_penalty());

                    childD.setRemainingPenalty(childD.getRemainingPenalty()-lastPenaltyOutstanding+pv.getV_ls_outstading_penalty());

                    childE.setRemainingPenalty(pv.getV_ls_outstading_penalty());

                    lastPenaltyOutstanding = pv.getV_ls_outstading_penalty();


                }



                if(pv.getV_ls_total_overdue()>=0)
                {

                    reportData.setOverdueAll(reportData.getOverdueAll()-lastTotalOverdue+pv.getV_ls_total_overdue());

                    childA.setOverdueAll(childA.getOverdueAll()-lastTotalOverdue+pv.getV_ls_total_overdue());


                    childB.setOverdueAll(childB.getOverdueAll()-lastTotalOverdue+pv.getV_ls_total_overdue());


                    childC.setOverdueAll(childC.getOverdueAll()-lastTotalOverdue+pv.getV_ls_total_overdue());

                    childD.setOverdueAll(childD.getOverdueAll()-lastTotalOverdue+pv.getV_ls_total_overdue());

                    childE.setOverdueAll(pv.getV_ls_total_overdue());

                    lastTotalOverdue = pv.getV_ls_total_overdue();


                }


                if(pv.getV_ls_overdue_principal()>=0)
                {

                    reportData.setOverduePrincipal(reportData.getOverduePrincipal()-lastPrincipleOverdue+pv.getV_ls_overdue_principal());

                    childA.setOverduePrincipal(childA.getOverduePrincipal()-lastPrincipleOverdue+pv.getV_ls_overdue_principal());


                    childB.setOverduePrincipal(childB.getOverduePrincipal()-lastPrincipleOverdue+pv.getV_ls_overdue_principal());


                    childC.setOverduePrincipal(childC.getOverduePrincipal()-lastPrincipleOverdue+pv.getV_ls_overdue_principal());

                    childD.setOverduePrincipal(childD.getOverduePrincipal()-lastPrincipleOverdue+pv.getV_ls_overdue_principal());

                    childE.setOverduePrincipal(pv.getV_ls_overdue_principal());

                    lastPrincipleOverdue = pv.getV_ls_overdue_principal();


                }

                if(pv.getV_ls_overdue_interest()>=0)
                {

                    reportData.setOverdueInterest(reportData.getOverdueInterest()-lastInteresOverdue+pv.getV_ls_overdue_interest());

                    childA.setOverdueInterest(childA.getOverdueInterest()-lastInteresOverdue+pv.getV_ls_overdue_interest());


                    childB.setOverdueInterest(childB.getOverdueInterest()-lastInteresOverdue+pv.getV_ls_overdue_interest());


                    childC.setOverdueInterest(childC.getOverdueInterest()-lastInteresOverdue+pv.getV_ls_overdue_interest());

                    childD.setOverdueInterest(childD.getOverdueInterest()-lastInteresOverdue+pv.getV_ls_overdue_interest());

                    childE.setOverdueInterest(pv.getV_ls_overdue_interest());

                    lastInteresOverdue = pv.getV_ls_overdue_interest();


                }


                if(pv.getV_ls_overdue_penalty()>=0)
                {

                    reportData.setOverduePenalty(reportData.getOverduePenalty()-lastPenaltyOverdue+pv.getV_ls_overdue_penalty());

                    childA.setOverduePenalty(childA.getOverduePenalty()-lastPenaltyOverdue+pv.getV_ls_overdue_penalty());


                    childB.setOverduePenalty(childB.getOverduePenalty()-lastPenaltyOverdue+pv.getV_ls_overdue_penalty());


                    childC.setOverduePenalty(childC.getOverduePenalty()-lastPenaltyOverdue+pv.getV_ls_overdue_penalty());

                    childD.setOverduePenalty(childD.getOverduePenalty()-lastPenaltyOverdue+pv.getV_ls_overdue_penalty());

                    childE.setOverduePenalty(pv.getV_ls_overdue_penalty());

                    lastPenaltyOverdue = pv.getV_ls_overdue_penalty();


                }



                currentgroupEid=reportTool.getIdByGroupType(reportTemplate.getGroupType5(),loanSummaryView);
            }



        }


        return reportData;
    }




}
