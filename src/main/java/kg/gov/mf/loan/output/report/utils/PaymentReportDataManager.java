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

        ReportTool reportTool = new ReportTool();

        LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<>();

        parameterS.putAll(reportTool.getParametersByTemplate(reportTemplate));

        reportData.getPaymentViews().addAll(paymentViewService.findByParameter(parameterS));

        for (PaymentView paymentView: reportData.getPaymentViews())
        {
            System.out.println(paymentView.getV_debtor_name());
        }

        return groupifyData(reportData, reportTemplate, reportTool );
    }

    public PaymentReportData groupifyData(PaymentReportData reportData, ReportTemplate reportTemplate, ReportTool reportTool)
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

        PaymentReportData childA = null;
        PaymentReportData childB = null;
        PaymentReportData childC = null;
        PaymentReportData childD = null;
        PaymentReportData childE = null;
        PaymentReportData childF = null;



        for (PaymentView paymentView:reportData.getPaymentViews())
        {
            if(reportTool.getIdByGroupType(reportTemplate.getGroupType1(),paymentView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType1(),paymentView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(reportTemplate.getGroupType1(),paymentView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType2(),paymentView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType2(),paymentView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(reportTemplate.getGroupType2(),paymentView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType3(),paymentView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType3(),paymentView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                currentgroupCid=reportTool.getIdByGroupType(reportTemplate.getGroupType3(),paymentView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType4(),paymentView)!=currentgroupDid)
            {
                PaymentView lv = paymentView;

                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType4(),paymentView));
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

                if(lv.getV_loan_amount()>0)
                {
                    childD.setLoanAmount(lv.getV_loan_amount());
                    childC.setLoanAmount(childC.getLoanAmount()+lv.getV_loan_amount());
                    childB.setLoanAmount(childB.getLoanAmount()+lv.getV_loan_amount());
                    childA.setLoanAmount(childA.getLoanAmount()+lv.getV_loan_amount());
                    reportData.setLoanAmount(reportData.getLoanAmount()+lv.getV_loan_amount());
                }

                currentgroupDid=reportTool.getIdByGroupType(reportTemplate.getGroupType4(),paymentView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType5(),paymentView)!=currentgroupEid)
            {

                PaymentView pv = paymentView;


                childE = childD.addChild();
                childE.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType5(),paymentView));
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
                childE.setPaymentTypeName("");
                childE.setPaymentType(pv.getV_payment_type_id());


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

                if (pv.isV_payment_in_loan_currency())
                {
                    if(pv.getV_loan_currency_id()>1)
                    {
                        double rate = pv.getV_payment_exchange_rate();

                        if(rate==0) rate = 1;

                        pv.setV_payment_total_amount(pv.getV_payment_total_amount()*rate);
                        pv.setV_payment_principal(pv.getV_payment_principal()*rate);
                        pv.setV_payment_interest(pv.getV_payment_interest()*rate);
                        pv.setV_payment_penalty(pv.getV_payment_penalty()*rate);


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
                    childD.setPaymentPenalty(childD. getPaymentPenalty()+pv.getV_payment_penalty());
                    childC.setPaymentPenalty(childC. getPaymentPenalty()+pv.getV_payment_penalty());
                    childB.setPaymentPenalty(childB. getPaymentPenalty()+pv.getV_payment_penalty());
                    childA.setPaymentPenalty(childA. getPaymentPenalty()+pv.getV_payment_penalty());
                    reportData.setPaymentPenalty(reportData.getPaymentPenalty()+pv.getV_payment_penalty());
                }

                currentgroupEid=reportTool.getIdByGroupType(reportTemplate.getGroupType5(),paymentView);
            }






        }


        return reportData;
    }


}
