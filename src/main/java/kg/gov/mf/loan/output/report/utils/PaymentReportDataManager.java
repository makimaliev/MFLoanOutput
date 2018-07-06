package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.manage.model.loan.Payment;
import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.PaymentViewService;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class PaymentReportDataManager {
	
	/**
     * Report GENERATION TOOL
     */

    @Autowired
    LoanViewService loanViewService;

    @Autowired
    PaymentViewService paymentViewService;

    public PaymentReportData getReportDataGrouped(PaymentReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        Set<PaymentView> paymentViews =  new HashSet<PaymentView>();

        ReportTool reportTool = new ReportTool();

        Date onDate = this.getOnDate(reportTemplate);

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



            parameterS.put("orderBy",groupIds);

        }


        // initial filter by report filter parameters

        reportData.getPaymentViews().addAll(paymentViewService.findByParameter(parameterS));


        reportData.setOnDate(new java.sql.Date(onDate.getTime()));

        Set<Long> groupAIds = new HashSet<Long>();

        ArrayList<PaymentView> allPaymentViews = new ArrayList<PaymentView>();

        for (PaymentView paymentViewInLoop: reportData.getPaymentViews()
             )
        {
            System.out.println(
                    paymentViewInLoop.getV_debtor_region_id() +" "+
                    paymentViewInLoop.getV_debtor_district_id()+" "+
                    paymentViewInLoop.getV_debtor_id()+" "+
                    paymentViewInLoop.getV_loan_id()+" "+
                            paymentViewInLoop.getV_payment_id());

        }

        return groupifyData(reportData,groupIds,reportTemplate);
    }

    public PaymentReportData groupifyData(PaymentReportData reportData, List<Long> groupIds,ReportTemplate reportTemplate)
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

        PaymentReportData childA = null;
        PaymentReportData childB = null;
        PaymentReportData childC = null;
        PaymentReportData childD = null;
        PaymentReportData childE = null;
        PaymentReportData childF = null;


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



        for (PaymentView paymentView:reportData.getPaymentViews())
        {
            if(reportTool.getIdByGroupType(groupAid,paymentView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(groupAid,paymentView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(groupAid,paymentView);
            }



            if(reportTool.getIdByGroupType(groupBid,paymentView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(groupBid,paymentView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(groupBid,paymentView);
            }

            if(reportTool.getIdByGroupType(groupCid,paymentView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(groupCid,paymentView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                currentgroupCid=reportTool.getIdByGroupType(groupCid,paymentView);
            }

            if(reportTool.getIdByGroupType(groupDid,paymentView)!=currentgroupDid)
            {
                PaymentView lv = paymentView;

                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(groupDid,paymentView));
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

                if(lv.getV_loan_amount()>0)
                {
                    childD.setLoanAmount(lv.getV_loan_amount());
                    childC.setLoanAmount(childC.getLoanAmount()+lv.getV_loan_amount());
                    childB.setLoanAmount(childB.getLoanAmount()+lv.getV_loan_amount());
                    childA.setLoanAmount(childA.getLoanAmount()+lv.getV_loan_amount());
                    reportData.setLoanAmount(reportData.getLoanAmount()+lv.getV_loan_amount());
                }

                currentgroupDid=reportTool.getIdByGroupType(groupDid,paymentView);
            }

            if(reportTool.getIdByGroupType(groupEid,paymentView)!=currentgroupEid)
            {
                PaymentView pv = paymentView;

                childE = childD.addChild();
                childE.setName(reportTool.getNameByGroupType(groupEid,paymentView));
                childE.setLevel((short)5);

                childE.setLevel((short)5);


                childE.setPaymentCount(1);
                childD.setPaymentCount(childD.getPaymentCount()+1);
                childC.setPaymentCount(childC.getPaymentCount()+1);
                childA.setPaymentCount(childA.getPaymentCount()+1);
                childB.setPaymentCount(childB.getPaymentCount()+1);
                reportData.setPaymentCount(reportData.getPaymentCount()+1);

                childE.setPaymentID(pv.getV_payment_id());

                childE.setName(" погашение №"+pv.getV_payment_number()+" от "+pv.getV_payment_date());
                childE.setPaymentDate(new java.sql.Date(pv.getV_payment_date().getTime()));
                childE.setPaymentNumber(pv.getV_payment_number());
                childE.setPaymentTypeName(pv.getV_payment_type_name());

                long loanCurrencyId = pv.getV_loan_currency_id();

                if(pv.getV_loan_currency_id()==1)
                {
                    childE.setCurrency((short)1);
                    childE.setExchangeRate((double)1);
                }
                else
                {
                    if(pv.isV_payment_in_loan_currency())
                    {
                        childE.setCurrency((short)loanCurrencyId);
                        childE.setExchangeRate(pv.getV_payment_exchange_rate());
                    }
                }


                if(pv.getV_payment_total_amount()>0)
                {
                    childE.setPaymentTotalAmount(pv.getV_payment_total_amount());
                    childD.setPaymentTotalAmount(childD.getPaymentTotalAmount()+pv.getV_payment_total_amount());
                    childC.setPaymentTotalAmount(childC.getPaymentTotalAmount()+pv.getV_payment_total_amount());
                    childB.setPaymentTotalAmount(childB.getPaymentTotalAmount()+pv.getV_payment_total_amount());
                    childA.setPaymentTotalAmount(childA.getPaymentTotalAmount()+pv.getV_payment_total_amount());
                    reportData.setPaymentTotalAmount(reportData.getPaymentTotalAmount()+pv.getV_payment_total_amount());
                }

                if(pv.getV_payment_principal()>0)
                {
                    childE.setPaymentPrincipal(pv.getV_payment_principal());
                    childD.setPaymentPrincipal(childD. getPaymentPrincipal()+pv.getV_payment_principal());
                    childC.setPaymentPrincipal(childC. getPaymentPrincipal()+pv.getV_payment_principal());
                    childB.setPaymentPrincipal(childB. getPaymentPrincipal()+pv.getV_payment_principal());
                    childA.setPaymentPrincipal(childA. getPaymentPrincipal()+pv.getV_payment_principal());
                    reportData.setPaymentPrincipal(reportData. getPaymentPrincipal()+pv.getV_payment_principal());
                }

                if(pv.getV_payment_interest()>0)
                {
                    childE.setPaymentInterest(pv.getV_payment_interest());
                    childD.setPaymentInterest(childD. getPaymentInterest()+pv.getV_payment_interest());
                    childC.setPaymentInterest(childC. getPaymentInterest()+pv.getV_payment_interest());
                    childB.setPaymentInterest(childB. getPaymentInterest()+pv.getV_payment_interest());
                    childA.setPaymentInterest(childA. getPaymentInterest()+pv.getV_payment_interest());
                    reportData.setPaymentInterest(reportData. getPaymentInterest()+pv.getV_payment_interest());
                }

                if(pv.getV_payment_penalty()>0)
                {
                    childE.setPaymentPenalty(pv.getV_payment_penalty());
                    childD.setPaymentPenalty(childD. getPaymentInterest()+pv.getV_payment_penalty());
                    childC.setPaymentPenalty(childC. getPaymentInterest()+pv.getV_payment_penalty());
                    childB.setPaymentPenalty(childB. getPaymentInterest()+pv.getV_payment_penalty());
                    childA.setPaymentPenalty(childA. getPaymentInterest()+pv.getV_payment_penalty());
                    reportData.setPaymentPenalty(reportData. getPaymentInterest()+pv.getV_payment_penalty());
                }



                currentgroupEid=reportTool.getIdByGroupType(groupEid,paymentView);
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




}
