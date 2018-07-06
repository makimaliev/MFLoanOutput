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

        Date onDate = reportTool.getOnDate(reportTemplate);

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

        // initial filter by report filter parameters

        reportData.getPaymentScheduleViews().addAll(paymentScheduleViewService.findByParameter(parameterS));


        reportData.setOnDate(new java.sql.Date(onDate.getTime()));

        Set<Long> groupAIds = new HashSet<Long>();

        ArrayList<PaymentScheduleView> allPaymentScheduleViews = new ArrayList<PaymentScheduleView>();

        for (PaymentScheduleView paymentScheduleViewInLoop: reportData.getPaymentScheduleViews()
             )
        {
            System.out.println(
                    paymentScheduleViewInLoop.getV_debtor_region_id() +" "+
                    paymentScheduleViewInLoop.getV_debtor_district_id()+" "+
                    paymentScheduleViewInLoop.getV_debtor_id()+" "+
                    paymentScheduleViewInLoop.getV_loan_id()+" "+
                            paymentScheduleViewInLoop.getV_ps_id());


//            System.out.println(
//                    paymentScheduleViewInLoop.getV_region_name() +" "+
//                            paymentScheduleViewInLoop.getV_district_name()+" "+
//                            paymentScheduleViewInLoop.getV_debtor_name()+" "+
//                            paymentScheduleViewInLoop.getV_credit_order_regNumber()+ " от "+
//                            paymentScheduleViewInLoop.getV_credit_order_regDate()+ ", "+
//                            paymentScheduleViewInLoop.getV_loan_reg_number()+ " от " +
//                            paymentScheduleViewInLoop.getV_loan_reg_date()+" "+
//                            paymentScheduleViewInLoop.getV_payment_number()+" "+
//                            paymentScheduleViewInLoop.getV_payment_date()+" "+
//                            paymentScheduleViewInLoop.getV_payment_total_amount());
        }

        return groupifyData(reportData,groupIds,reportTemplate);
    }

    public PaymentScheduleReportData groupifyData(PaymentScheduleReportData reportData, List<Long> groupIds,ReportTemplate reportTemplate)
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


            if(reportTool.getIdByGroupType(groupAid,paymentScheduleView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(groupAid,paymentScheduleView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(groupAid,paymentScheduleView);
            }

            if(reportTool.getIdByGroupType(groupBid,paymentScheduleView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(groupBid,paymentScheduleView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(groupBid,paymentScheduleView);
            }

            if(reportTool.getIdByGroupType(groupCid,paymentScheduleView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(3,paymentScheduleView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                currentgroupCid=reportTool.getIdByGroupType(groupCid,paymentScheduleView);
            }

            if(reportTool.getIdByGroupType(groupDid,paymentScheduleView)!=currentgroupDid)
            {
                PaymentScheduleView lv = paymentScheduleView;

                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(groupDid,paymentScheduleView));
                childD.setLevel((short)4);


                childD.setLevel((short)4);

                childD.setDetailsCount(1);
                childC.setDetailsCount(childC.getDetailsCount()+1);
                childA.setDetailsCount(childA.getDetailsCount()+1);
                childB.setDetailsCount(childB.getDetailsCount()+1);
                reportData.setDetailsCount(reportData.getDetailsCount()+1);


                childD.setID(lv.getV_loan_id());
                childD.setName(lv.getV_credit_order_regNumber()+lv.getV_loan_reg_number()+" от "+lv.getV_loan_reg_date());
                childD.setCurrency((short)lv.getV_loan_currency_id());
                childD.setLoanRegNumber(lv.getV_loan_reg_number());
                childD.setOrderRegNumber(lv.getV_credit_order_regNumber());
                childD.setLoanRegDate(new java.sql.Date(lv.getV_loan_reg_date().getTime()));

                childD.setLoanType((short)lv.getV_loan_type_id());

                currentgroupDid=reportTool.getIdByGroupType(groupDid,paymentScheduleView);

                lastLoanAmount = 0;

                lastDisbursement =0;
                lastDisbursement = 0;
                lastPrincipalPayment = 0;
                lastInterestPayment = 0;
                lastCollectedInterestPayment = 0;
                lastCollectedPenaltyPayment = 0;
            }

            if(reportTool.getIdByGroupType(groupEid,paymentScheduleView)!=currentgroupEid)
            {
                PaymentScheduleView pv = paymentScheduleView;

                childE = childD.addChild();
                childE.setName(" график на "+pv.getV_ps_expectedDate()+" : ");
                childE.setLevel((short)5);


                childE.setPaymentCount(1);
                childD.setPaymentCount(childD.getPaymentCount()+1);
                childC.setPaymentCount(childC.getPaymentCount()+1);
                childA.setPaymentCount(childA.getPaymentCount()+1);
                childB.setPaymentCount(childB.getPaymentCount()+1);
                reportData.setPaymentCount(reportData.getPaymentCount()+1);


                childE.setExpectedDate(new java.sql.Date(pv.getV_ps_expectedDate().getTime()));

                childE.setInstallment_state_id(pv.getV_ps_installmentStateId());

                if(pv.getV_ps_disbursement()>=0)
                {

                    reportData.setDisbursement(reportData.getDisbursement()-lastDisbursement+pv.getV_ps_disbursement());

                    childA.setDisbursement(childA.getDisbursement()-lastDisbursement+pv.getV_ps_disbursement());


                    childB.setDisbursement(childB.getDisbursement()-lastDisbursement+pv.getV_ps_disbursement());


                    childC.setDisbursement(childC.getDisbursement()-lastDisbursement+pv.getV_ps_disbursement());

                    childD.setDisbursement(childD.getDisbursement()-lastDisbursement+pv.getV_ps_disbursement());

                    childE.setDisbursement(pv.getV_ps_disbursement());

                    lastDisbursement  =  0;


                }

                if(pv.getV_ps_principalPayment()>=0)
                {

                    reportData.setPrincipal_payment(reportData.getPrincipal_payment()-lastPrincipalPayment+pv.getV_ps_principalPayment());

                    childA.setPrincipal_payment(childA.getPrincipal_payment()-lastPrincipalPayment+pv.getV_ps_principalPayment());


                    childB.setPrincipal_payment(childB.getPrincipal_payment()-lastPrincipalPayment+pv.getV_ps_principalPayment());


                    childC.setPrincipal_payment(childC.getPrincipal_payment()-lastPrincipalPayment+pv.getV_ps_principalPayment());

                    childD.setPrincipal_payment(childD.getPrincipal_payment()-lastPrincipalPayment+pv.getV_ps_principalPayment());

                    childE.setPrincipal_payment(pv.getV_ps_principalPayment());

                    lastPrincipalPayment  = 0;


                }

                if(pv.getV_ps_interestPayment()>=0)
                {

                    reportData.setInterest_payment(reportData.getInterest_payment()-lastInterestPayment+pv.getV_ps_interestPayment());

                    childA.setInterest_payment(childA.getInterest_payment()-lastInterestPayment+pv.getV_ps_interestPayment());


                    childB.setInterest_payment(childB.getInterest_payment()-lastInterestPayment+pv.getV_ps_interestPayment());


                    childC.setInterest_payment(childC.getInterest_payment()-lastInterestPayment+pv.getV_ps_interestPayment());

                    childD.setInterest_payment(childD.getInterest_payment()-lastInterestPayment+pv.getV_ps_interestPayment());

                    childE.setInterest_payment(pv.getV_ps_interestPayment());

                    lastInterestPayment  = 0;


                }

                if(pv.getV_ps_collectedInterestPayment()>=0)
                {

                    reportData.setCollected_interest_payment(reportData.getCollected_interest_payment()-lastCollectedInterestPayment+pv.getV_ps_collectedInterestPayment());

                    childA.setCollected_interest_payment(childA.getCollected_interest_payment()-lastCollectedInterestPayment+pv.getV_ps_collectedInterestPayment());


                    childB.setCollected_interest_payment(childB.getCollected_interest_payment()-lastCollectedInterestPayment+pv.getV_ps_collectedInterestPayment());


                    childC.setCollected_interest_payment(childC.getCollected_interest_payment()-lastCollectedInterestPayment+pv.getV_ps_collectedInterestPayment());

                    childD.setCollected_interest_payment(childD.getCollected_interest_payment()-lastCollectedInterestPayment+pv.getV_ps_collectedInterestPayment());

                    childE.setCollected_interest_payment(pv.getV_ps_collectedInterestPayment());

                    lastCollectedInterestPayment  = 0;


                }

                if(pv.getV_ps_collectedPenaltyPayment()>=0)
                {

                    reportData.setCollected_penalty_payment(reportData.getCollected_penalty_payment()-lastCollectedPenaltyPayment+pv.getV_ps_collectedPenaltyPayment());

                    childA.setCollected_penalty_payment(childA.getCollected_penalty_payment()-lastCollectedPenaltyPayment+pv.getV_ps_collectedPenaltyPayment());


                    childB.setCollected_penalty_payment(childB.getCollected_penalty_payment()-lastCollectedPenaltyPayment+pv.getV_ps_collectedPenaltyPayment());


                    childC.setCollected_penalty_payment(childC.getCollected_penalty_payment()-lastCollectedPenaltyPayment+pv.getV_ps_collectedPenaltyPayment());

                    childD.setCollected_penalty_payment(childD.getCollected_penalty_payment()-lastCollectedPenaltyPayment+pv.getV_ps_collectedPenaltyPayment());

                    childE.setCollected_penalty_payment(pv.getV_ps_collectedPenaltyPayment());

                    lastCollectedPenaltyPayment  = 0;


                }


                reportData.setTotalPayment(reportData.getPrincipal_payment()+reportData.getInterest_payment()+reportData.getCollected_interest_payment()+reportData.getCollected_penalty_payment());

                childA.setTotalPayment(childA.getPrincipal_payment()+childA.getInterest_payment()+childA.getCollected_interest_payment()+childA.getCollected_penalty_payment());

                childB.setTotalPayment(childB.getPrincipal_payment()+childB.getInterest_payment()+childB.getCollected_interest_payment()+childB.getCollected_penalty_payment());

                childC.setTotalPayment(childC.getPrincipal_payment()+childC.getInterest_payment()+childC.getCollected_interest_payment()+childC.getCollected_penalty_payment());

                childD.setTotalPayment(childD.getPrincipal_payment()+childD.getInterest_payment()+childD.getCollected_interest_payment()+childD.getCollected_penalty_payment());

                childE.setTotalPayment(childE.getPrincipal_payment()+childE.getInterest_payment()+childE.getCollected_interest_payment()+childE.getCollected_penalty_payment());









                currentgroupEid=reportTool.getIdByGroupType(groupEid,paymentScheduleView);
            }


        }


        return reportData;
    }




}
