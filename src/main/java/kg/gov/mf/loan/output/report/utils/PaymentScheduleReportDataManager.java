package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import kg.gov.mf.loan.output.report.service.PaymentScheduleViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class PaymentScheduleReportDataManager {
	
	/**
     * Report GENERATION TOOL
     */

    @Autowired
    LoanViewService loanViewService;

    @Autowired
    PaymentScheduleViewService paymentScheduleViewService;

    public PaymentScheduleReportData getReportDataGrouped(PaymentScheduleReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        ReportTool reportTool = new ReportTool();

        LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<>();

        parameterS.putAll(reportTool.getParametersByTemplate(reportTemplate));

        reportData.getPaymentScheduleViews().addAll(paymentScheduleViewService.findByParameter(parameterS));



        return groupifyData(reportData, reportTemplate, reportTool );


    }

    public PaymentScheduleReportData groupifyData(PaymentScheduleReportData reportData, ReportTemplate reportTemplate, ReportTool reportTool)
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


        PaymentScheduleReportData childA = null;
        PaymentScheduleReportData childB = null;
        PaymentScheduleReportData childC = null;
        PaymentScheduleReportData childD = null;
        PaymentScheduleReportData childE = null;
        PaymentScheduleReportData childF = null;



        double lastLoanAmount = 0;

        double lastDisbursement = 0;
        double lastPrincipalPayment = 0;
        double lastInterestPayment = 0;
        double lastCollectedInterestPayment = 0;
        double lastCollectedPenaltyPayment = 0;

        for (PaymentScheduleView paymentScheduleView:reportData.getPaymentScheduleViews())
        {
            if(reportTool.getIdByGroupType(reportTemplate.getGroupType1(),paymentScheduleView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType1(),paymentScheduleView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(reportTemplate.getGroupType1(),paymentScheduleView);
                currentgroupBid=-1;

            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType2(),paymentScheduleView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType2(),paymentScheduleView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(reportTemplate.getGroupType2(),paymentScheduleView);
                currentgroupCid=-1;

            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType3(),paymentScheduleView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType3(),paymentScheduleView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                currentgroupCid=reportTool.getIdByGroupType(reportTemplate.getGroupType3(),paymentScheduleView);
                currentgroupDid=-1;

            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType4(),paymentScheduleView)!=currentgroupDid)
            {

                PaymentScheduleView lv = paymentScheduleView;

                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType4(),paymentScheduleView));
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

                currentgroupDid=reportTool.getIdByGroupType(reportTemplate.getGroupType4(),paymentScheduleView);

                lastLoanAmount = 0;

                lastDisbursement =0;
                lastDisbursement = 0;
                lastPrincipalPayment = 0;
                lastInterestPayment = 0;
                lastCollectedInterestPayment = 0;
                lastCollectedPenaltyPayment = 0;


            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType5(),paymentScheduleView)!=currentgroupEid)
            {

                PaymentScheduleView pv = paymentScheduleView;

                double rate=1;
                double rate2=1;


                if(pv.getV_loan_currency_id()>1)
                {
                    try {
                        rate = reportTool.getCurrencyRateValueByDateAndCurrencyTypeId(reportTemplate.getOnDate(),pv.getV_loan_currency_id());

                        if(reportTemplate.getAdditionalDate()!=null)
                        {
                            rate2 = reportTool.getCurrencyRateValueByDateAndCurrencyTypeId(reportTemplate.getAdditionalDate(),pv.getV_loan_currency_id());
                        }

                    }
                    catch (Exception ex)
                    {
                    }

                }

                childE = childD.addChild();
                childE.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType5(),paymentScheduleView)+" в тыс "+reportTool.getNameByMapName("currency_type",pv.getV_loan_currency_id()) + " по курсу "+ rate);

                childE.setLevel((short)5);

                childE.setPaymentCount(1);
                childD.setPaymentCount(childD.getPaymentCount()+1);
                childC.setPaymentCount(childC.getPaymentCount()+1);
                childA.setPaymentCount(childA.getPaymentCount()+1);
                childB.setPaymentCount(childB.getPaymentCount()+1);
                reportData.setPaymentCount(reportData.getPaymentCount()+1);


                pv.setV_ps_disbursement(pv.getV_ps_disbursement()*rate);
                pv.setV_ps_principal_payment(pv.getV_ps_principal_payment()*rate);
                pv.setV_ps_interest_payment(pv.getV_ps_interest_payment()*rate);
                pv.setV_ps_collected_interest_payment(pv.getV_ps_collected_interest_payment()*rate);
                pv.setV_ps_collected_penalty_payment(pv.getV_ps_collected_penalty_payment()*rate);

                childE.setExpectedDate(new java.sql.Date(pv.getV_ps_expected_date().getTime()));

                childE.setInstallment_state_id(pv.getV_ps_installment_state_id());

                if(pv.getV_ps_disbursement()>=0)
                {

                    reportData.setDisbursement(reportData.getDisbursement()-lastDisbursement+pv.getV_ps_disbursement());

                    childA.setDisbursement(childA.getDisbursement()-lastDisbursement+pv.getV_ps_disbursement());


                    childB.setDisbursement(childB.getDisbursement()-lastDisbursement+pv.getV_ps_disbursement());


                    childC.setDisbursement(childC.getDisbursement()-lastDisbursement+pv.getV_ps_disbursement());

                    childD.setDisbursement(childD.getDisbursement()-lastDisbursement+pv.getV_ps_disbursement());

                    childE.setDisbursement(pv.getV_ps_disbursement()/rate);

                    lastDisbursement  =  0;


                }

                if(pv.getV_ps_principal_payment()>=0)
                {

                    reportData.setPrincipal_payment(reportData.getPrincipal_payment()-lastPrincipalPayment+pv.getV_ps_principal_payment());

                    childA.setPrincipal_payment(childA.getPrincipal_payment()-lastPrincipalPayment+pv.getV_ps_principal_payment());


                    childB.setPrincipal_payment(childB.getPrincipal_payment()-lastPrincipalPayment+pv.getV_ps_principal_payment());


                    childC.setPrincipal_payment(childC.getPrincipal_payment()-lastPrincipalPayment+pv.getV_ps_principal_payment());

                    childD.setPrincipal_payment(childD.getPrincipal_payment()-lastPrincipalPayment+pv.getV_ps_principal_payment());

                    childE.setPrincipal_payment(pv.getV_ps_principal_payment()/rate);

                    lastPrincipalPayment  = 0;


                }

                if(pv.getV_ps_interest_payment()>=0)
                {

                    reportData.setInterest_payment(reportData.getInterest_payment()-lastInterestPayment+pv.getV_ps_interest_payment());

                    childA.setInterest_payment(childA.getInterest_payment()-lastInterestPayment+pv.getV_ps_interest_payment());


                    childB.setInterest_payment(childB.getInterest_payment()-lastInterestPayment+pv.getV_ps_interest_payment());


                    childC.setInterest_payment(childC.getInterest_payment()-lastInterestPayment+pv.getV_ps_interest_payment());

                    childD.setInterest_payment(childD.getInterest_payment()-lastInterestPayment+pv.getV_ps_interest_payment());

                    childE.setInterest_payment(pv.getV_ps_interest_payment()/rate);

                    lastInterestPayment  = 0;


                }

                if(pv.getV_ps_collected_interest_payment()>=0)
                {

                    reportData.setCollected_interest_payment(reportData.getCollected_interest_payment()-lastCollectedInterestPayment+pv.getV_ps_collected_interest_payment());

                    childA.setCollected_interest_payment(childA.getCollected_interest_payment()-lastCollectedInterestPayment+pv.getV_ps_collected_interest_payment());


                    childB.setCollected_interest_payment(childB.getCollected_interest_payment()-lastCollectedInterestPayment+pv.getV_ps_collected_interest_payment());


                    childC.setCollected_interest_payment(childC.getCollected_interest_payment()-lastCollectedInterestPayment+pv.getV_ps_collected_interest_payment());

                    childD.setCollected_interest_payment(childD.getCollected_interest_payment()-lastCollectedInterestPayment+pv.getV_ps_collected_interest_payment());

                    childE.setCollected_interest_payment(pv.getV_ps_collected_interest_payment()/rate);

                    lastCollectedInterestPayment  = 0;


                }

                if(pv.getV_ps_collected_penalty_payment()>=0)
                {

                    reportData.setCollected_penalty_payment(reportData.getCollected_penalty_payment()-lastCollectedPenaltyPayment+pv.getV_ps_collected_penalty_payment());

                    childA.setCollected_penalty_payment(childA.getCollected_penalty_payment()-lastCollectedPenaltyPayment+pv.getV_ps_collected_penalty_payment());


                    childB.setCollected_penalty_payment(childB.getCollected_penalty_payment()-lastCollectedPenaltyPayment+pv.getV_ps_collected_penalty_payment());


                    childC.setCollected_penalty_payment(childC.getCollected_penalty_payment()-lastCollectedPenaltyPayment+pv.getV_ps_collected_penalty_payment());

                    childD.setCollected_penalty_payment(childD.getCollected_penalty_payment()-lastCollectedPenaltyPayment+pv.getV_ps_collected_penalty_payment());

                    childE.setCollected_penalty_payment(pv.getV_ps_collected_penalty_payment()/rate);

                    lastCollectedPenaltyPayment  = 0;


                }


                reportData.setTotalPayment(reportData.getPrincipal_payment()+reportData.getInterest_payment()+reportData.getCollected_interest_payment()+reportData.getCollected_penalty_payment());

                childA.setTotalPayment(childA.getPrincipal_payment()+childA.getInterest_payment()+childA.getCollected_interest_payment()+childA.getCollected_penalty_payment());

                childB.setTotalPayment(childB.getPrincipal_payment()+childB.getInterest_payment()+childB.getCollected_interest_payment()+childB.getCollected_penalty_payment());

                childC.setTotalPayment(childC.getPrincipal_payment()+childC.getInterest_payment()+childC.getCollected_interest_payment()+childC.getCollected_penalty_payment());

                childD.setTotalPayment(childD.getPrincipal_payment()+childD.getInterest_payment()+childD.getCollected_interest_payment()+childD.getCollected_penalty_payment());

                childE.setTotalPayment(childE.getPrincipal_payment()+childE.getInterest_payment()+childE.getCollected_interest_payment()+childE.getCollected_penalty_payment());

                currentgroupEid=reportTool.getIdByGroupType(reportTemplate.getGroupType5(),paymentScheduleView);
            }




        }


        return reportData;
    }




}
