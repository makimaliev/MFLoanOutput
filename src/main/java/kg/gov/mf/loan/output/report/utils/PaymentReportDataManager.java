package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.PaymentViewService;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

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

        Date onDate = new Date();

        for (GenerationParameter generationParameter: reportTemplate.getGenerationParameters())
        {
            if(generationParameter.getGenerationParameterType().getId()==1)
            {
                onDate = generationParameter.getDate();
            }

        }

        LinkedHashMap<String,List<Long>> parameterS = new LinkedHashMap<String,List<Long>>();

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

                if(objectList.getObjectTypeId()==1)
                {
                    parameterS.put("region",Ids);
                }
                if(objectList.getObjectTypeId()==2)
                {
                    parameterS.put("district",Ids);
                }
            }


        }

        reportData.getPaymentViews().addAll(paymentViewService.findByParameter(parameterS));


        reportData.setOnDate(new java.sql.Date(onDate.getTime()));

        Set<Long> groupAIds = new HashSet<Long>();

        ArrayList<PaymentView> allPaymentViews = new ArrayList<PaymentView>();

        for (PaymentView paymentView:reportData.getPaymentViews())
        {
            groupAIds.add(paymentView.getV_debtor_region_id());
            allPaymentViews.add(paymentView);
        }

        for (Long groupAId: groupAIds)
        {
            PaymentReportData childA = reportData.addChild();
            childA.setName(groupAId.toString());
            childA.setLevel((short)1);

            LinkedHashMap<String,List<Long>> parameterA = new LinkedHashMap<String,List<Long>>();

            List<Long> regionIds = new ArrayList<>();
            regionIds.add(groupAId);


            parameterA.putAll(parameterS);
            parameterA.put("region",regionIds);

            childA.getPaymentViews().addAll(paymentViewService.findByParameter(parameterA));

            Set<Long> groupBIds = new HashSet<Long>();

            for (PaymentView paymentView:childA.getPaymentViews())
            {
                groupBIds.add(paymentView.getV_debtor_district_id());
            }

            for (Long groupBId: groupBIds)
            {
                PaymentReportData childB = childA.addChild();
                childB.setName(groupBId.toString());
                childB.setLevel((short)2);

                LinkedHashMap<String,List<Long>> parameterB = new LinkedHashMap<String,List<Long>>();

                List<Long> districtIds = new ArrayList<>();
                districtIds.add(groupBId);

                parameterB.putAll(parameterA);
                parameterB.put("region",regionIds);
                parameterB.put("district",districtIds);


                Set<PaymentView> paymentViewsB =  new HashSet<PaymentView>();

                paymentViewsB.addAll(paymentViewService.findByParameter(parameterB));

                childB.setPaymentViews(paymentViewsB);

                Set<Long> debtorIds = new HashSet<Long>();

                for (PaymentView paymentViewD:childB.getPaymentViews())
                {
                    debtorIds.add(paymentViewD.getV_debtor_id());
                }

                for (Long debtorId: debtorIds)
                {
                    PaymentReportData debtor = childB.addChild();
                    debtor.setName(debtorId.toString());
                    debtor.setLevel((short)3);

                    debtor.setCount(1);
                    childB.setCount(childB.getCount()+1);
                    childA.setCount(childA.getCount()+1);
                    reportData.setCount(reportData.getCount()+1);


                    LinkedHashMap<String,List<Long>> parameterD = new LinkedHashMap<String,List<Long>>();

                    List<Long> debtorDIds = new ArrayList<>();
                    debtorDIds.add(debtorId);

                    parameterD.putAll(parameterB);
                    parameterD.put("region",regionIds);
                    parameterD.put("district",districtIds);
                    parameterD.put("debtor",debtorDIds);



                    Set<PaymentView> paymentViewsD =  new HashSet<PaymentView>();

                    paymentViewsD.addAll(paymentViewService.findByParameter(parameterD));

                    debtor.setPaymentViews(paymentViewsD);

                    Set<Long> loanIds = new HashSet<Long>();

                    for (PaymentView paymentViewL:debtor.getPaymentViews())
                    {
                        loanIds.add(paymentViewL.getV_loan_id());
                    }

                    for (Long loanId: loanIds)
                    {

                        PaymentView lv = null;

                        for (PaymentView paymentViewL:allPaymentViews)
                        {
                            if(paymentViewL.getV_loan_id()==loanId)
                            {
                                lv=paymentViewL;
                            }
                        }


                        PaymentReportData loan = debtor.addChild();

                        loan.setLevel((short)4);

                        childA.setName(lv.getV_region_name());
                        childB.setName(lv.getV_district_name());
                        debtor.setName(lv.getV_debtor_name());


                        loan.setDetailsCount(1);
                        debtor.setDetailsCount(debtor.getDetailsCount()+1);
                        childA.setDetailsCount(childA.getDetailsCount()+1);
                        childB.setDetailsCount(childB.getDetailsCount()+1);
                        reportData.setDetailsCount(reportData.getDetailsCount()+1);

                        loan.setID(loanId);
                        loan.setName(lv.getV_credit_order_regNumber()+lv.getV_loan_reg_number()+" от "+lv.getV_loan_reg_date());
                        loan.setCurrency((short)lv.getV_loan_currency_id());
                        loan.setLoanRegNumber(lv.getV_loan_reg_number());
                        loan.setOrderRegNumber(lv.getV_credit_order_regNumber());
                        loan.setLoanRegDate(new java.sql.Date(lv.getV_loan_reg_date().getTime()));

                        loan.setLoanType((short)lv.getV_loan_type_id());

                        if(lv.getV_loan_amount()>0)
                        {
                            loan.setLoanAmount(lv.getV_loan_amount());
                            debtor.setLoanAmount(debtor.getLoanAmount()+lv.getV_loan_amount());
                            childB.setLoanAmount(childB.getLoanAmount()+lv.getV_loan_amount());
                            childA.setLoanAmount(childA.getLoanAmount()+lv.getV_loan_amount());
                            reportData.setLoanAmount(reportData.getLoanAmount()+lv.getV_loan_amount());
                        }






                        LinkedHashMap<String,List<Long>> parameterL = new LinkedHashMap<String,List<Long>>();

                        List<Long> loanLIds = new ArrayList<>();
                        loanLIds.add(loanId);

                        parameterL.putAll(parameterD);
                        parameterL.put("region",regionIds);
                        parameterL.put("district",districtIds);
                        parameterL.put("debtor",debtorDIds);
                        parameterL.put("loan",loanLIds);



                        Set<PaymentView> paymentViewsL =  new HashSet<PaymentView>();

                        paymentViewsL.addAll(paymentViewService.findByParameter(parameterL));

                        loan.setPaymentViews(paymentViewsL);

                        Set<Long> paymentIds = new HashSet<Long>();

                        for (PaymentView paymentViewP:loan.getPaymentViews())
                        {
                            paymentIds.add(paymentViewP.getV_payment_id());
                        }

                        for (Long paymentId: paymentIds)
                        {

                            PaymentView pv = null;

                            for (PaymentView paymentViewP:allPaymentViews)
                            {
                                if(paymentViewP.getV_payment_id()==paymentId)
                                {
                                    pv=paymentViewP;
                                }
                            }


                            PaymentReportData payment = loan.addChild();

                            payment.setLevel((short)5);

                            childA.setName(pv.getV_region_name());
                            childB.setName(pv.getV_district_name());
                            debtor.setName(pv.getV_debtor_name());

                            payment.setPaymentCount(1);
                            loan.setPaymentCount(debtor.getPaymentCount()+1);
                            debtor.setPaymentCount(debtor.getPaymentCount()+1);
                            childA.setPaymentCount(childA.getPaymentCount()+1);
                            childB.setPaymentCount(childB.getPaymentCount()+1);
                            reportData.setPaymentCount(reportData.getPaymentCount()+1);

                            payment.setPaymentID(paymentId);

                            payment.setName(" погашение №"+pv.getV_payment_number()+" от "+pv.getV_payment_date());
                            payment.setPaymentDate(new java.sql.Date(pv.getV_payment_date().getTime()));
                            payment.setPaymentNumber(pv.getV_payment_number());
                            payment.setPaymentTypeName(pv.getV_payment_type_name());

                            if(pv.getV_payment_total_amount()>0)
                            {
                                payment.setPaymentTotalAmount(pv.getV_payment_total_amount());
                                loan.setPaymentTotalAmount(loan.getPaymentTotalAmount()+pv.getV_payment_total_amount());
                                debtor.setPaymentTotalAmount(debtor.getPaymentTotalAmount()+pv.getV_payment_total_amount());
                                childB.setPaymentTotalAmount(childB.getPaymentTotalAmount()+pv.getV_payment_total_amount());
                                childA.setPaymentTotalAmount(childA.getPaymentTotalAmount()+pv.getV_payment_total_amount());
                                reportData.setPaymentTotalAmount(reportData.getPaymentTotalAmount()+pv.getV_payment_total_amount());
                            }






                            LinkedHashMap<String,List<Long>> parameterP = new LinkedHashMap<String,List<Long>>();

                            List<Long> paymentPIds = new ArrayList<>();
                            paymentPIds.add(paymentId);

                            parameterL.putAll(parameterL);
                            parameterL.put("region",regionIds);
                            parameterL.put("district",districtIds);
                            parameterL.put("debtor",debtorDIds);
                            parameterL.put("loan",loanLIds);
                            parameterL.put("payment",paymentPIds);



                            Set<PaymentView> paymentViewsP =  new HashSet<PaymentView>();

                            paymentViewsP.addAll(paymentViewService.findByParameter(parameterP));

                            payment.setPaymentViews(paymentViewsP);

                        }

                    }
                }
            }
        }

        return reportData;
    }
}
