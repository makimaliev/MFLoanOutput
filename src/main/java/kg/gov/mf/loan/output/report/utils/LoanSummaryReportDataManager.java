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

        reportTool.initCurrencyRatesMap(reportTemplate);

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
        double lastTotalPaidKGS = 0;
        double lastTotalOustanding = 0;
        double lastPrincipleOutstanding = 0;
        double lastInteresOutstanding = 0;
        double lastPenaltyOutstanding = 0;

        double lastTotalOverdue = 0;
        double lastPrincipleOverdue = 0;
        double lastInteresOverdue = 0;
        double lastPenaltyOverdue = 0;

        double lastTotalOustandingDiff = 0;
        double lastTotalOverdueDiff = 0;


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
                childD.setName(loanSummaryView.getV_credit_order_reg_number()+", "+loanSummaryView.getV_loan_reg_number()+" от "+loanSummaryView.getV_loan_reg_date()+" в тыс. сомах");



                childD.setCurrency(loanSummaryView.getV_loan_currency_id().shortValue());
                childD.setLoanRegNumber(loanSummaryView.getV_loan_reg_number());
                childD.setOrderRegNumber(loanSummaryView.getV_credit_order_reg_number());
                childD.setLoanRegDate(new java.sql.Date(loanSummaryView.getV_loan_reg_date().getTime()));

                if(!(loanSummaryView.getV_last_date()==null))
                childD.setLastDate(new java.sql.Date(loanSummaryView.getV_last_date().getTime()));

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

                lastTotalOustandingDiff = 0;
                lastTotalOverdueDiff = 0;


                currentgroupDid=reportTool.getIdByGroupType(reportTemplate.getGroupType4(),loanSummaryView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType5(),loanSummaryView)!=currentgroupEid)
            {
                double thousands = 1000;
                double rate = 1;
                double rate2 = 1;

                if(loanSummaryView.getV_loan_currency_id()>1)
                {
                    rate = reportTool.getCurrencyRateValueByDateAndCurrencyTypeId(reportTemplate.getOnDate(),loanSummaryView.getV_loan_currency_id());

                    if(reportTemplate.getAdditionalDate()!=null)
                    {
                        rate2 = reportTool.getCurrencyRateValueByDateAndCurrencyTypeId(reportTemplate.getAdditionalDate(),loanSummaryView.getV_loan_currency_id());
                    }

                }
                childD.getChildDataList().removeAll(childD.getChildDataList());

                childE = childD.addChild();
                childE.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType5(),loanSummaryView));
                childE.setLevel((short)5);

                LoanSummaryView pv = loanSummaryView;

                childE.setPaymentCount(1);
                childD.setPaymentCount(childD.getPaymentCount()+1);
                childC.setPaymentCount(childC.getPaymentCount()+1);
                childA.setPaymentCount(childA.getPaymentCount()+1);
                childB.setPaymentCount(childB.getPaymentCount()+1);
                reportData.setPaymentCount(reportData.getPaymentCount()+1);


                childE.setOnDate(new java.sql.Date(pv.getV_ls_on_date().getTime()));

                if(pv.getV_loan_amount()>=0)
                {

                    reportData.setLoanAmount(reportData.getLoanAmount()-lastLoanAmount+pv.getV_loan_amount()*rate/thousands);

                    childA.setLoanAmount(childA.getLoanAmount()-lastLoanAmount+pv.getV_loan_amount()*rate/thousands);


                    childB.setLoanAmount(childB.getLoanAmount()-lastLoanAmount+pv.getV_loan_amount()*rate/thousands);


                    childC.setLoanAmount(childC.getLoanAmount()-lastLoanAmount+pv.getV_loan_amount()*rate/thousands);

                    childD.setLoanAmount(childD.getLoanAmount()-lastLoanAmount+pv.getV_loan_amount()*rate/thousands);

                    childE.setLoanAmount(pv.getV_loan_amount()/thousands);

                    lastLoanAmount  = pv.getV_loan_amount()*rate/thousands;


                }


                if(pv.getV_ls_total_disbursed()>=0)
                {

                    reportData.setTotalDisbursment(reportData.getTotalDisbursment()-lastDisbursement+pv.getV_ls_total_disbursed()*rate/thousands);

                    childA.setTotalDisbursment(childA.getTotalDisbursment()-lastDisbursement+pv.getV_ls_total_disbursed()*rate/thousands);


                    childB.setTotalDisbursment(childB.getTotalDisbursment()-lastDisbursement+pv.getV_ls_total_disbursed()*rate/thousands);


                    childC.setTotalDisbursment(childC.getTotalDisbursment()-lastDisbursement+pv.getV_ls_total_disbursed()*rate/thousands);

                    childD.setTotalDisbursment(childD.getTotalDisbursment()-lastDisbursement+pv.getV_ls_total_disbursed()*rate/thousands);

                    childE.setTotalDisbursment(pv.getV_ls_total_disbursed()/thousands);

                    lastDisbursement = pv.getV_ls_total_disbursed()*rate/thousands;


                }

                if(pv.getV_ls_total_paid_kgs()>=0)
                {

                    reportData.setTotalPaid(reportData.getTotalPaid()-lastTotalPaid+pv.getV_ls_total_paid_kgs()/thousands);

                    childA.setTotalPaid(childA.getTotalPaid()-lastTotalPaid+pv.getV_ls_total_paid_kgs()/thousands);


                    childB.setTotalPaid(childB.getTotalPaid()-lastTotalPaid+pv.getV_ls_total_paid_kgs()/thousands);


                    childC.setTotalPaid(childC.getTotalPaid()-lastTotalPaid+pv.getV_ls_total_paid_kgs()/thousands);

                    childD.setTotalPaid(childD.getTotalPaid()-lastTotalPaid+pv.getV_ls_total_paid_kgs()/thousands);

                    childE.setTotalPaid(pv.getV_ls_total_paid()/thousands);

                    lastTotalPaid = pv.getV_ls_total_paid_kgs()/thousands;


                }


                if(pv.getV_ls_total_outstanding()>=0)
                {

                    reportData.setRemainingSum(reportData.getRemainingSum()-lastTotalOustanding+pv.getV_ls_total_outstanding()*rate/thousands);

                    childA.setRemainingSum(childA.getRemainingSum()-lastTotalOustanding+pv.getV_ls_total_outstanding()*rate/thousands);


                    childB.setRemainingSum(childB.getRemainingSum()-lastTotalOustanding+pv.getV_ls_total_outstanding()*rate/thousands);


                    childC.setRemainingSum(childC.getRemainingSum()-lastTotalOustanding+pv.getV_ls_total_outstanding()*rate/thousands);

                    childD.setRemainingSum(childD.getRemainingSum()-lastTotalOustanding+pv.getV_ls_total_outstanding()*rate/thousands);

                    childE.setRemainingSum(pv.getV_ls_total_outstanding()/thousands);

                    lastTotalOustanding = pv.getV_ls_total_outstanding()*rate/thousands;



                    reportData.setRemainingDiff(reportData.getRemainingDiff()-lastTotalOustandingDiff+pv.getV_ls_total_outstanding()*(rate-rate2)/thousands);

                    childA.setRemainingDiff(childA.getRemainingDiff()-lastTotalOustandingDiff+pv.getV_ls_total_outstanding()*(rate-rate2)/thousands);


                    childB.setRemainingDiff(childB.getRemainingDiff()-lastTotalOustandingDiff+pv.getV_ls_total_outstanding()*(rate-rate2)/thousands);


                    childC.setRemainingDiff(childC.getRemainingDiff()-lastTotalOustandingDiff+pv.getV_ls_total_outstanding()*(rate-rate2)/thousands);

                    childD.setRemainingDiff(childD.getRemainingDiff()-lastTotalOustandingDiff+pv.getV_ls_total_outstanding()*(rate-rate2)/thousands);

                    childE.setRemainingDiff(0);

                    lastTotalOustandingDiff = pv.getV_ls_total_outstanding()*(rate-rate2)/thousands;


                }


                if(pv.getV_ls_outstading_principal()>=0)
                {

                    reportData.setRemainingPrincipal(reportData.getRemainingPrincipal()-lastPrincipleOutstanding+pv.getV_ls_outstading_principal()*rate/thousands);

                    childA.setRemainingPrincipal(childA.getRemainingPrincipal()-lastPrincipleOutstanding+pv.getV_ls_outstading_principal()*rate/thousands);


                    childB.setRemainingPrincipal(childB.getRemainingPrincipal()-lastPrincipleOutstanding+pv.getV_ls_outstading_principal()*rate/thousands);


                    childC.setRemainingPrincipal(childC.getRemainingPrincipal()-lastPrincipleOutstanding+pv.getV_ls_outstading_principal()*rate/thousands);

                    childD.setRemainingPrincipal(childD.getRemainingPrincipal()-lastPrincipleOutstanding+pv.getV_ls_outstading_principal()*rate/thousands);

                    childE.setRemainingPrincipal(pv.getV_ls_outstading_principal()/thousands);

                    lastPrincipleOutstanding = pv.getV_ls_outstading_principal()*rate/thousands;


                }


                if(pv.getV_ls_outstading_interest()>=0)
                {

                    reportData.setRemainingInterest(reportData.getRemainingInterest()-lastInteresOutstanding+pv.getV_ls_outstading_interest()*rate/thousands);

                    childA.setRemainingInterest(childA.getRemainingInterest()-lastInteresOutstanding+pv.getV_ls_outstading_interest()*rate/thousands);


                    childB.setRemainingInterest(childB.getRemainingInterest()-lastInteresOutstanding+pv.getV_ls_outstading_interest()*rate/thousands);


                    childC.setRemainingInterest(childC.getRemainingInterest()-lastInteresOutstanding+pv.getV_ls_outstading_interest()*rate/thousands);

                    childD.setRemainingInterest(childD.getRemainingInterest()-lastInteresOutstanding+pv.getV_ls_outstading_interest()*rate/thousands);

                    childE.setRemainingInterest(pv.getV_ls_outstading_interest()/thousands);

                    lastInteresOutstanding = pv.getV_ls_outstading_interest()*rate/thousands;


                }

                if(pv.getV_ls_outstading_penalty()>=0)
                {

                    reportData.setRemainingPenalty(reportData.getRemainingPenalty()-lastPenaltyOutstanding+pv.getV_ls_outstading_penalty()*rate/thousands);

                    childA.setRemainingPenalty(childA.getRemainingPenalty()-lastPenaltyOutstanding+pv.getV_ls_outstading_penalty()*rate/thousands);


                    childB.setRemainingPenalty(childB.getRemainingPenalty()-lastPenaltyOutstanding+pv.getV_ls_outstading_penalty()*rate/thousands);


                    childC.setRemainingPenalty(childC.getRemainingPenalty()-lastPenaltyOutstanding+pv.getV_ls_outstading_penalty()*rate/thousands);

                    childD.setRemainingPenalty(childD.getRemainingPenalty()-lastPenaltyOutstanding+pv.getV_ls_outstading_penalty()*rate/thousands);

                    childE.setRemainingPenalty(pv.getV_ls_outstading_penalty()/thousands);

                    lastPenaltyOutstanding = pv.getV_ls_outstading_penalty()*rate/thousands;


                }



                if(pv.getV_ls_total_overdue()>=0)
                {

                    reportData.setOverdueAll(reportData.getOverdueAll()-lastTotalOverdue+pv.getV_ls_total_overdue()*rate/thousands);

                    childA.setOverdueAll(childA.getOverdueAll()-lastTotalOverdue+pv.getV_ls_total_overdue()*rate/thousands);


                    childB.setOverdueAll(childB.getOverdueAll()-lastTotalOverdue+pv.getV_ls_total_overdue()*rate/thousands);


                    childC.setOverdueAll(childC.getOverdueAll()-lastTotalOverdue+pv.getV_ls_total_overdue()*rate/thousands);

                    childD.setOverdueAll(childD.getOverdueAll()-lastTotalOverdue+pv.getV_ls_total_overdue()*rate/thousands);

                    childE.setOverdueAll(pv.getV_ls_total_overdue()/thousands);

                    lastTotalOverdue = pv.getV_ls_total_overdue()*rate/thousands;




                    reportData.setOverdueDiff(reportData.getOverdueDiff()-lastTotalOverdueDiff+pv.getV_ls_total_overdue()*(rate-rate2)/thousands);

                    childA.setOverdueDiff(childA.getOverdueDiff()-lastTotalOverdueDiff+pv.getV_ls_total_overdue()*(rate-rate2)/thousands);


                    childB.setOverdueDiff(childB.getOverdueDiff()-lastTotalOverdueDiff+pv.getV_ls_total_overdue()*(rate-rate2)/thousands);


                    childC.setOverdueDiff(childC.getOverdueDiff()-lastTotalOverdueDiff+pv.getV_ls_total_overdue()*(rate-rate2)/thousands);

                    childD.setOverdueDiff(childD.getOverdueDiff()-lastTotalOverdueDiff+pv.getV_ls_total_overdue()*(rate-rate2)/thousands);

                    childE.setOverdueDiff(0);

                    lastTotalOverdueDiff = pv.getV_ls_total_overdue()*(rate-rate2)/thousands;


                }


                if(pv.getV_ls_overdue_principal()>=0)
                {

                    reportData.setOverduePrincipal(reportData.getOverduePrincipal()-lastPrincipleOverdue+pv.getV_ls_overdue_principal()*rate/thousands);

                    childA.setOverduePrincipal(childA.getOverduePrincipal()-lastPrincipleOverdue+pv.getV_ls_overdue_principal()*rate/thousands);


                    childB.setOverduePrincipal(childB.getOverduePrincipal()-lastPrincipleOverdue+pv.getV_ls_overdue_principal()*rate/thousands);


                    childC.setOverduePrincipal(childC.getOverduePrincipal()-lastPrincipleOverdue+pv.getV_ls_overdue_principal()*rate/thousands);

                    childD.setOverduePrincipal(childD.getOverduePrincipal()-lastPrincipleOverdue+pv.getV_ls_overdue_principal()*rate/thousands);

                    childE.setOverduePrincipal(pv.getV_ls_overdue_principal()/thousands);

                    lastPrincipleOverdue = pv.getV_ls_overdue_principal()*rate/thousands;


                }

                if(pv.getV_ls_overdue_interest()>=0)
                {

                    reportData.setOverdueInterest(reportData.getOverdueInterest()-lastInteresOverdue+pv.getV_ls_overdue_interest()*rate/thousands);

                    childA.setOverdueInterest(childA.getOverdueInterest()-lastInteresOverdue+pv.getV_ls_overdue_interest()*rate/thousands);


                    childB.setOverdueInterest(childB.getOverdueInterest()-lastInteresOverdue+pv.getV_ls_overdue_interest()*rate/thousands);


                    childC.setOverdueInterest(childC.getOverdueInterest()-lastInteresOverdue+pv.getV_ls_overdue_interest()*rate/thousands);

                    childD.setOverdueInterest(childD.getOverdueInterest()-lastInteresOverdue+pv.getV_ls_overdue_interest()*rate/thousands);

                    childE.setOverdueInterest(pv.getV_ls_overdue_interest()/thousands);

                    lastInteresOverdue = pv.getV_ls_overdue_interest()*rate/thousands;


                }


                if(pv.getV_ls_overdue_penalty()>=0)
                {

                    reportData.setOverduePenalty(reportData.getOverduePenalty()-lastPenaltyOverdue+pv.getV_ls_overdue_penalty()*rate/thousands);

                    childA.setOverduePenalty(childA.getOverduePenalty()-lastPenaltyOverdue+pv.getV_ls_overdue_penalty()*rate/thousands);


                    childB.setOverduePenalty(childB.getOverduePenalty()-lastPenaltyOverdue+pv.getV_ls_overdue_penalty()*rate/thousands);


                    childC.setOverduePenalty(childC.getOverduePenalty()-lastPenaltyOverdue+pv.getV_ls_overdue_penalty()*rate/thousands);

                    childD.setOverduePenalty(childD.getOverduePenalty()-lastPenaltyOverdue+pv.getV_ls_overdue_penalty()*rate/thousands);

                    childE.setOverduePenalty(pv.getV_ls_overdue_penalty()/thousands);

                    lastPenaltyOverdue = pv.getV_ls_overdue_penalty()*rate/thousands;


                }



                currentgroupEid=reportTool.getIdByGroupType(reportTemplate.getGroupType5(),loanSummaryView);

                if(loanSummaryView.getV_loan_currency_id()==1)
                {
                    childD.getChildDataList().removeAll(childD.getChildDataList());
                }
                else
                {
                    childE.setName(" в тыс "+reportTool.getNameByMapName("currency_type",loanSummaryView.getV_loan_currency_id()) + " по курсу "+ rate);
                }
            }



        }


        return reportData;
    }




}
