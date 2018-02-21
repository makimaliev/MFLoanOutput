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

        Date onDate = this.getOnDate(reportTemplate);

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

        }

        long groupAtype = getGroupType(reportTemplate,1);
        long groupBtype = getGroupType(reportTemplate,2);
        long groupCtype = getGroupType(reportTemplate,3);
        long groupDtype = getGroupType(reportTemplate,4);
        long groupEtype = getGroupType(reportTemplate,5);



        // initial filter by report filter parameters

        reportData.getPaymentViews().addAll(paymentViewService.findByParameter(parameterS));


        reportData.setOnDate(new java.sql.Date(onDate.getTime()));

        Set<Long> groupAIds = new HashSet<Long>();

        ArrayList<PaymentView> allPaymentViews = new ArrayList<PaymentView>();

        for (PaymentView paymentView:reportData.getPaymentViews())
        {
            groupAIds.add(this.getIdByGroupType(groupAtype,paymentView));
            allPaymentViews.add(paymentView);
        }

        for (Long groupAIdInLoop: groupAIds)
        {
            PaymentReportData childA = reportData.addChild();
            childA.setName(groupAIdInLoop.toString()); // TODO id to name conversion
            childA.setLevel((short)1);

            LinkedHashMap<String,List<Long>> parameterA = new LinkedHashMap<String,List<Long>>();

            List<Long> groupACurrentId = new ArrayList<>();
            groupACurrentId.add(groupAIdInLoop);


            parameterA.putAll(parameterS);

            parameterA.put(getParameterTypeNameById(String.valueOf(groupAtype)),groupACurrentId);

            //parameterA.put("region",groupACurrentId);

            childA.getPaymentViews().addAll(paymentViewService.findByParameter(parameterA));

            Set<Long> groupBIds = new HashSet<Long>();

            for (PaymentView paymentView:childA.getPaymentViews())
            {
                groupBIds.add(this.getIdByGroupType(groupBtype,paymentView));
            }

            for (Long groupBId: groupBIds)
            {
                PaymentReportData childB = childA.addChild();
                childB.setName(groupBId.toString());
                childB.setLevel((short)2);

                LinkedHashMap<String,List<Long>> parameterB = new LinkedHashMap<String,List<Long>>();

                List<Long> groupBcurrentId = new ArrayList<>();
                groupBcurrentId.add(groupBId);

                parameterB.putAll(parameterA);

                parameterB.put(getParameterTypeNameById(String.valueOf(groupAtype)),groupACurrentId);
                //parameterB.put("region",groupACurrentId);

                parameterB.put(getParameterTypeNameById(String.valueOf(groupBtype)),groupBcurrentId);
                //parameterB.put("district",groupBcurrentId);


                Set<PaymentView> paymentViewsB =  new HashSet<PaymentView>();

                paymentViewsB.addAll(paymentViewService.findByParameter(parameterB));

                childB.setPaymentViews(paymentViewsB);

                Set<Long> debtorIds = new HashSet<Long>();

                for (PaymentView paymentViewD:childB.getPaymentViews())
                {
                    debtorIds.add(this.getIdByGroupType(groupCtype,paymentViewD));
                    //debtorIds.add(paymentViewD.getV_debtor_id());
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

                    parameterD.put(getParameterTypeNameById(String.valueOf(groupAtype)),groupACurrentId);
                    //parameterD.put("region",groupACurrentId);

                    parameterD.put(getParameterTypeNameById(String.valueOf(groupBtype)),groupBcurrentId);
                    //parameterD.put("district",groupBcurrentId);
                    parameterD.put("debtor",debtorDIds);



                    Set<PaymentView> paymentViewsD =  new HashSet<PaymentView>();

                    paymentViewsD.addAll(paymentViewService.findByParameter(parameterD));

                    debtor.setPaymentViews(paymentViewsD);

                    Set<Long> loanIds = new HashSet<Long>();

                    for (PaymentView paymentViewL:debtor.getPaymentViews())
                    {
                        loanIds.add(this.getIdByGroupType(groupDtype,paymentViewL));

                        //loanIds.add(paymentViewL.getV_loan_id());
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

                        //childA.setName(lv.getV_region_name());
                        //childB.setName(lv.getV_district_name());

                        childA.setName(this.getNameByGroupType(groupAtype,lv));
                        childB.setName(this.getNameByGroupType(groupBtype,lv));
                        debtor.setName(this.getNameByGroupType(groupCtype,lv));


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

                        parameterL.put(getParameterTypeNameById(String.valueOf(groupAtype)),groupACurrentId);
                        //parameterL.put("region",groupACurrentId);

                        parameterL.put(getParameterTypeNameById(String.valueOf(groupBtype)),groupBcurrentId);
                        //parameterL.put("district",groupBcurrentId);

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

                            childA.setName(this.getNameByGroupType(groupAtype,lv));
                            childB.setName(this.getNameByGroupType(groupBtype,lv));
                            debtor.setName(this.getNameByGroupType(groupCtype,lv));


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

                            parameterP.putAll(parameterL);

                            parameterP.put(getParameterTypeNameById(String.valueOf(groupAtype)),groupACurrentId);
                            //parameterL.put("region",groupACurrentId);

                            parameterP.put(getParameterTypeNameById(String.valueOf(groupBtype)),groupBcurrentId);
                            //parameterL.put("district",groupBcurrentId);

                            parameterP.put("debtor",debtorDIds);
                            parameterP.put("loan",loanLIds);
                            parameterP.put("payment",paymentPIds);



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


    public long getIdByGroupType(long groupType, PaymentView paymentView)
    {
        long idByGroupType=0;

        switch((short)groupType)
        {
            case 1:
                idByGroupType = paymentView.getV_debtor_region_id();
                break;
            case 2:
                idByGroupType = paymentView.getV_debtor_district_id();
                break;
            case 3:
                idByGroupType = paymentView.getV_debtor_id();
                break;
            case 4:
                idByGroupType = paymentView.getV_loan_id();
                break;
            case 5:
                idByGroupType = paymentView.getV_payment_id();
                break;
            case 6:
                idByGroupType = paymentView.getV_debtor_work_sector_id();
                break;






        }

        return  idByGroupType;
    }

    public String getNameByGroupType(long groupType, PaymentView paymentView)
    {
        String nameByGroupType="";

        switch((short)groupType)
        {
            case 1:
                nameByGroupType = paymentView.getV_region_name();
                break;
            case 2:
                nameByGroupType = paymentView.getV_district_name();
                break;
            case 3:
                nameByGroupType = paymentView.getV_debtor_name();
                break;
            case 4:
                nameByGroupType =paymentView.getV_credit_order_regNumber()
                                + " от "
                                + paymentView.getV_credit_order_regDate()
                                + ", "
                                + paymentView.getV_loan_reg_number()
                                + " от "
                                + paymentView.getV_loan_reg_date();
                break;
            case 5:
                nameByGroupType = " погашение № "+ paymentView.getV_payment_number()+ " " + paymentView.getV_payment_date();
                break;
            case 6:
                nameByGroupType = paymentView.getV_work_sector_name();
                break;






        }

        return  nameByGroupType;
    }

}
