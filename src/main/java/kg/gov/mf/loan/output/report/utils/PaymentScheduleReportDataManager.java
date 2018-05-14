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

        Set<PaymentScheduleView> paymentScheduleViews =  new HashSet<PaymentScheduleView>();

        Date onDate = this.getOnDate(reportTemplate);

        LinkedHashMap<String,List<Long>> parameterS = new LinkedHashMap<String,List<Long>>();

        List<Long> groupIds = new ArrayList<>();

        groupIds.add(getGroupType(reportTemplate,1));
        groupIds.add(getGroupType(reportTemplate,2));
        groupIds.add(getGroupType(reportTemplate,3));
        groupIds.add(getGroupType(reportTemplate,4));
        groupIds.add((long)13);

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

                parameterS.put(getParameterTypeNameById(String.valueOf(objectList.getObjectTypeId())),Ids);
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

        long groupAtype = getGroupType(reportTemplate,1);
        long groupBtype = getGroupType(reportTemplate,2);
        long groupCtype = getGroupType(reportTemplate,3);
        long groupDtype = getGroupType(reportTemplate,4);
        long groupEtype = getGroupType(reportTemplate,13);



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

        return groupifyData(reportData,groupIds);
    }

    public PaymentScheduleReportData groupifyData(PaymentScheduleReportData reportData, List<Long> groupIds)
    {

        long groupAid=0;
        long groupBid=0;
        long groupCid=0;
        long groupDid=0;
        long groupEid=0;
        long groupFid=0;

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
            if(this.getIdByGroupType(groupIds.get(0),paymentScheduleView)!=groupAid)
            {
                childA = reportData.addChild();
                childA.setName(this.getNameByGroupType(1,paymentScheduleView));
                childA.setLevel((short)1);

                groupAid=this.getIdByGroupType(groupIds.get(0),paymentScheduleView);
            }

            if(this.getIdByGroupType(groupIds.get(1),paymentScheduleView)!=groupBid)
            {
                childB = childA.addChild();
                childB.setName(this.getNameByGroupType(2,paymentScheduleView));
                childB.setLevel((short)2);

                groupBid=this.getIdByGroupType(groupIds.get(1),paymentScheduleView);
            }

            if(this.getIdByGroupType(groupIds.get(2),paymentScheduleView)!=groupCid)
            {
                childC = childB.addChild();
                childC.setName(this.getNameByGroupType(3,paymentScheduleView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                groupCid=this.getIdByGroupType(groupIds.get(2),paymentScheduleView);
            }

            if(this.getIdByGroupType(groupIds.get(3),paymentScheduleView)!=groupDid)
            {
                PaymentScheduleView lv = paymentScheduleView;

                childD = childC.addChild();
                childD.setName(this.getNameByGroupType(4,paymentScheduleView));
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

//                if(lv.getV_loan_amount()>0)
//                {
//                    childD.setLoanAmount(lv.getV_loan_amount());
//                    childC.setLoanAmount(childC.getLoanAmount()+lv.getV_loan_amount());
//                    childB.setLoanAmount(childB.getLoanAmount()+lv.getV_loan_amount());
//                    childA.setLoanAmount(childA.getLoanAmount()+lv.getV_loan_amount());
//                    reportData.setLoanAmount(reportData.getLoanAmount()+lv.getV_loan_amount());
//                }

                groupDid=this.getIdByGroupType(groupIds.get(3),paymentScheduleView);

                lastLoanAmount = 0;

                lastDisbursement =0;
                lastDisbursement = 0;
                lastPrincipalPayment = 0;
                lastInterestPayment = 0;
                lastCollectedInterestPayment = 0;
                lastCollectedPenaltyPayment = 0;
            }

            if(this.getIdByGroupType(groupIds.get(4),paymentScheduleView)!=groupEid)
            {
                PaymentScheduleView pv = paymentScheduleView;

                childE = childD.addChild();
                childE.setName(" график на "+pv.getV_ps_expectedDate()+" : ");
                childE.setLevel((short)5);

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









                groupEid=this.getIdByGroupType(groupIds.get(4),paymentScheduleView);
            }


        }


        return reportData;
    }

    public Date getOnDate(ReportTemplate reportTemplate)
    {

        Date date = new Date();
        for (GenerationParameter generationParameter: reportTemplate.getGenerationParameters())
        {
            if(generationParameter.getGenerationParameterType().getId()==1)
            {
                date = generationParameter.getDate();
            }
        }

        return date;
    }


    public String getParameterTypeNameById(String id)
    {
        String parameterTypeName = "";


        switch(id)
        {
            case "1":
                return "region";
            case "2":
                return "district";
            case "3":
                return "debtor";
            case "4":
                return "loan";
            case "5":
                return "payment";
            case "6":
                return "work_sector";
            case "7":
                return "supervisor";
            case "8":
                return "loan_type";
            case "9":
                return "department";

            case "10":
                return "collateral_agreement";

            case "11":
                return "collateral_item";

            case "12":
                return "loan_summary";

            case "13":
                return "payment_schedule";

        }



        return parameterTypeName;
    }

    public long getGroupType(ReportTemplate reportTemplate,long level)
    {
        long groupType=0;

        for (GenerationParameter generationParameter:reportTemplate.getGenerationParameters())
        {
            if(generationParameter.getGenerationParameterType().getId()==level+3)
            {
                groupType = generationParameter.getPostionInList();
            }
        }

        return  groupType;
    }


    public long getIdByGroupType(long groupType, PaymentScheduleView paymentScheduleView)
    {
        long idByGroupType=0;

        switch((short)groupType)
        {
            case 1:
                idByGroupType = paymentScheduleView.getV_debtor_region_id();
                break;
            case 2:
                idByGroupType = paymentScheduleView.getV_debtor_district_id();
                break;
            case 3:
                idByGroupType = paymentScheduleView.getV_debtor_id();
                break;
            case 4:
                idByGroupType = paymentScheduleView.getV_loan_id();
                break;
            case 13:
                idByGroupType = paymentScheduleView.getV_ps_id();
                break;
            case 6:
                idByGroupType = paymentScheduleView.getV_debtor_work_sector_id();
                break;






        }

        return  idByGroupType;
    }

    public String getNameByGroupType(long groupType, PaymentScheduleView paymentScheduleView)
    {
        String nameByGroupType="";

        switch((short)groupType)
        {
            case 1:
                nameByGroupType = paymentScheduleView.getV_region_name();
                break;
            case 2:
                nameByGroupType = paymentScheduleView.getV_district_name();
                break;
            case 3:
                nameByGroupType = paymentScheduleView.getV_debtor_name();
                break;
            case 4:
                nameByGroupType =paymentScheduleView.getV_credit_order_regNumber()
                                + " от "
                                + paymentScheduleView.getV_credit_order_regDate()
                                + ", "
                                + paymentScheduleView.getV_loan_reg_number()
                                + " от "
                                + paymentScheduleView.getV_loan_reg_date();
                break;
            case 12:
                nameByGroupType = " расчет";
                break;
            case 6:
                nameByGroupType = paymentScheduleView.getV_work_sector_name();
                break;
            case 13:
                nameByGroupType = " графики";
                break;






        }

        return  nameByGroupType;
    }

}
