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

        List<Long> groupIds = new ArrayList<>();

        groupIds.add(getGroupType(reportTemplate,1));
        groupIds.add(getGroupType(reportTemplate,2));
        groupIds.add(getGroupType(reportTemplate,3));
        groupIds.add(getGroupType(reportTemplate,4));
        groupIds.add(getGroupType(reportTemplate,5));

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



            parameterS.put("orderBy",groupIds);

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

        for (PaymentView paymentViewInLoop: reportData.getPaymentViews()
             )
        {
            System.out.println(
                    paymentViewInLoop.getV_debtor_region_id() +" "+
                    paymentViewInLoop.getV_debtor_district_id()+" "+
                    paymentViewInLoop.getV_debtor_id()+" "+
                    paymentViewInLoop.getV_loan_id()+" "+
                            paymentViewInLoop.getV_payment_id());


//            System.out.println(
//                    paymentViewInLoop.getV_region_name() +" "+
//                            paymentViewInLoop.getV_district_name()+" "+
//                            paymentViewInLoop.getV_debtor_name()+" "+
//                            paymentViewInLoop.getV_credit_order_regNumber()+ " от "+
//                            paymentViewInLoop.getV_credit_order_regDate()+ ", "+
//                            paymentViewInLoop.getV_loan_reg_number()+ " от " +
//                            paymentViewInLoop.getV_loan_reg_date()+" "+
//                            paymentViewInLoop.getV_payment_number()+" "+
//                            paymentViewInLoop.getV_payment_date()+" "+
//                            paymentViewInLoop.getV_payment_total_amount());
        }

        return groupifyData(reportData,groupIds);
    }

    public PaymentReportData groupifyData(PaymentReportData reportData, List<Long> groupIds)
    {

        long groupAid=0;
        long groupBid=0;
        long groupCid=0;
        long groupDid=0;
        long groupEid=0;
        long groupFid=0;

        PaymentReportData childA = null;
        PaymentReportData childB = null;
        PaymentReportData childC = null;
        PaymentReportData childD = null;
        PaymentReportData childE = null;
        PaymentReportData childF = null;


        for (PaymentView paymentView:reportData.getPaymentViews())
        {
            if(this.getIdByGroupType(groupIds.get(0),paymentView)!=groupAid)
            {
                childA = reportData.addChild();
                childA.setName(this.getNameByGroupType(1,paymentView));
                childA.setLevel((short)1);

                groupAid=this.getIdByGroupType(groupIds.get(0),paymentView);
            }

            if(this.getIdByGroupType(groupIds.get(1),paymentView)!=groupBid)
            {
                childB = childA.addChild();
                childB.setName(this.getNameByGroupType(2,paymentView));
                childB.setLevel((short)2);

                groupBid=this.getIdByGroupType(groupIds.get(1),paymentView);
            }

            if(this.getIdByGroupType(groupIds.get(2),paymentView)!=groupCid)
            {
                childC = childB.addChild();
                childC.setName(this.getNameByGroupType(3,paymentView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                groupCid=this.getIdByGroupType(groupIds.get(2),paymentView);
            }

            if(this.getIdByGroupType(groupIds.get(3),paymentView)!=groupDid)
            {
                PaymentView lv = paymentView;

                childD = childC.addChild();
                childD.setName(this.getNameByGroupType(4,paymentView));
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

                groupDid=this.getIdByGroupType(groupIds.get(3),paymentView);
            }

            if(this.getIdByGroupType(groupIds.get(4),paymentView)!=groupEid)
            {
                PaymentView pv = paymentView;

                childE = childD.addChild();
                childE.setName(this.getNameByGroupType(5,paymentView));
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



                groupEid=this.getIdByGroupType(groupIds.get(4),paymentView);
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
                nameByGroupType = " погашение №"+ paymentView.getV_payment_number()+ " от " + paymentView.getV_payment_date();
                break;
            case 6:
                nameByGroupType = paymentView.getV_work_sector_name();
                break;






        }

        return  nameByGroupType;
    }

}
