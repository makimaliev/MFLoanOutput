package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import kg.gov.mf.loan.output.report.service.LoanSummaryViewService;
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

    public LoanSummaryReportData getReportDataGrouped(LoanSummaryReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        Set<LoanSummaryView> loanSummaryViews =  new HashSet<LoanSummaryView>();

        Date onDate = this.getOnDate(reportTemplate);

        LinkedHashMap<String,List<Long>> parameterS = new LinkedHashMap<String,List<Long>>();

        List<Long> groupIds = new ArrayList<>();

        groupIds.add(getGroupType(reportTemplate,1));
        groupIds.add(getGroupType(reportTemplate,2));
        groupIds.add(getGroupType(reportTemplate,3));
        groupIds.add(getGroupType(reportTemplate,4));
        groupIds.add((long)12);

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
        long groupEtype = getGroupType(reportTemplate,12);



        // initial filter by report filter parameters

        reportData.getLoanSummaryViews().addAll(loanSummaryViewService.findByParameter(parameterS));


        reportData.setOnDate(new java.sql.Date(onDate.getTime()));

        Set<Long> groupAIds = new HashSet<Long>();

        ArrayList<LoanSummaryView> allLoanSummaryViews = new ArrayList<LoanSummaryView>();

        for (LoanSummaryView loanSummaryViewInLoop: reportData.getLoanSummaryViews()
             )
        {
            System.out.println(
                    loanSummaryViewInLoop.getV_debtor_region_id() +" "+
                    loanSummaryViewInLoop.getV_debtor_district_id()+" "+
                    loanSummaryViewInLoop.getV_debtor_id()+" "+
                    loanSummaryViewInLoop.getV_loan_id()+" "+
                            loanSummaryViewInLoop.getV_ls_id());


//            System.out.println(
//                    loanSummaryViewInLoop.getV_region_name() +" "+
//                            loanSummaryViewInLoop.getV_district_name()+" "+
//                            loanSummaryViewInLoop.getV_debtor_name()+" "+
//                            loanSummaryViewInLoop.getV_credit_order_regNumber()+ " от "+
//                            loanSummaryViewInLoop.getV_credit_order_regDate()+ ", "+
//                            loanSummaryViewInLoop.getV_loan_reg_number()+ " от " +
//                            loanSummaryViewInLoop.getV_loan_reg_date()+" "+
//                            loanSummaryViewInLoop.getV_payment_number()+" "+
//                            loanSummaryViewInLoop.getV_payment_date()+" "+
//                            loanSummaryViewInLoop.getV_payment_total_amount());
        }

        return groupifyData(reportData,groupIds);
    }

    public LoanSummaryReportData groupifyData(LoanSummaryReportData reportData, List<Long> groupIds)
    {

        long groupAid=0;
        long groupBid=0;
        long groupCid=0;
        long groupDid=0;
        long groupEid=0;
        long groupFid=0;

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
            if(this.getIdByGroupType(groupIds.get(0),loanSummaryView)!=groupAid)
            {
                childA = reportData.addChild();
                childA.setName(this.getNameByGroupType(1,loanSummaryView));
                childA.setLevel((short)1);

                groupAid=this.getIdByGroupType(groupIds.get(0),loanSummaryView);
            }

            if(this.getIdByGroupType(groupIds.get(1),loanSummaryView)!=groupBid)
            {
                childB = childA.addChild();
                childB.setName(this.getNameByGroupType(2,loanSummaryView));
                childB.setLevel((short)2);

                groupBid=this.getIdByGroupType(groupIds.get(1),loanSummaryView);
            }

            if(this.getIdByGroupType(groupIds.get(2),loanSummaryView)!=groupCid)
            {
                childC = childB.addChild();
                childC.setName(this.getNameByGroupType(3,loanSummaryView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                groupCid=this.getIdByGroupType(groupIds.get(2),loanSummaryView);
            }

            if(this.getIdByGroupType(groupIds.get(3),loanSummaryView)!=groupDid)
            {
                LoanSummaryView lv = loanSummaryView;

                childD = childC.addChild();
                childD.setName(this.getNameByGroupType(4,loanSummaryView));
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

                groupDid=this.getIdByGroupType(groupIds.get(3),loanSummaryView);

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
            }

            if(this.getIdByGroupType(groupIds.get(4),loanSummaryView)!=groupEid)
            {
                LoanSummaryView pv = loanSummaryView;

                childE = childD.addChild();
                childE.setName(" расчет на "+pv.getV_ls_onDate()+" : ");
                childE.setLevel((short)5);

                childE.setLevel((short)5);


                childE.setPaymentCount(1);
                childD.setPaymentCount(childD.getPaymentCount()+1);
                childC.setPaymentCount(childC.getPaymentCount()+1);
                childA.setPaymentCount(childA.getPaymentCount()+1);
                childB.setPaymentCount(childB.getPaymentCount()+1);
                reportData.setPaymentCount(reportData.getPaymentCount()+1);


                childE.setOnDate(new java.sql.Date(pv.getV_ls_onDate().getTime()));

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


                if(pv.getV_ls_totalDisbursed()>=0)
                {

                    reportData.setTotalDisbursment(reportData.getTotalDisbursment()-childE.getTotalDisbursment()+pv.getV_ls_totalDisbursed());

                    childA.setTotalDisbursment(childA.getTotalDisbursment()-lastDisbursement+pv.getV_ls_totalDisbursed());


                    childB.setTotalDisbursment(childB.getTotalDisbursment()-lastDisbursement+pv.getV_ls_totalDisbursed());


                    childC.setTotalDisbursment(childC.getTotalDisbursment()-lastDisbursement+pv.getV_ls_totalDisbursed());

                    childD.setTotalDisbursment(childD.getTotalDisbursment()-lastDisbursement+pv.getV_ls_totalDisbursed());

                    childE.setTotalDisbursment(pv.getV_ls_totalDisbursed());

                    lastDisbursement = pv.getV_ls_totalDisbursed();


                }

                if(pv.getV_ls_totalPaid()>=0)
                {

                    reportData.setTotalPaid(reportData.getTotalPaid()-lastTotalPaid+pv.getV_ls_totalPaid());

                    childA.setTotalPaid(childA.getTotalPaid()-lastTotalPaid+pv.getV_ls_totalPaid());


                    childB.setTotalPaid(childB.getTotalPaid()-lastTotalPaid+pv.getV_ls_totalPaid());


                    childC.setTotalPaid(childC.getTotalPaid()-lastTotalPaid+pv.getV_ls_totalPaid());

                    childD.setTotalPaid(childD.getTotalPaid()-lastTotalPaid+pv.getV_ls_totalPaid());

                    childE.setTotalPaid(pv.getV_ls_totalPaid());

                    lastTotalPaid = pv.getV_ls_totalPaid();


                }

                if(pv.getV_ls_totalOutstanding()>=0)
                {

                    reportData.setRemainingSum(reportData.getRemainingSum()-lastTotalOustanding+pv.getV_ls_totalOutstanding());

                    childA.setRemainingSum(childA.getRemainingSum()-lastTotalOustanding+pv.getV_ls_totalOutstanding());


                    childB.setRemainingSum(childB.getRemainingSum()-lastTotalOustanding+pv.getV_ls_totalOutstanding());


                    childC.setRemainingSum(childC.getRemainingSum()-lastTotalOustanding+pv.getV_ls_totalOutstanding());

                    childD.setRemainingSum(childD.getRemainingSum()-lastTotalOustanding+pv.getV_ls_totalOutstanding());

                    childE.setRemainingSum(pv.getV_ls_totalOutstanding());

                    lastTotalOustanding = pv.getV_ls_totalOutstanding();


                }


                if(pv.getV_ls_outstadingPrincipal()>=0)
                {

                    reportData.setRemainingPrincipal(reportData.getRemainingPrincipal()-lastPrincipleOutstanding+pv.getV_ls_outstadingPrincipal());

                    childA.setRemainingPrincipal(childA.getRemainingPrincipal()-lastPrincipleOutstanding+pv.getV_ls_outstadingPrincipal());


                    childB.setRemainingPrincipal(childB.getRemainingPrincipal()-lastPrincipleOutstanding+pv.getV_ls_outstadingPrincipal());


                    childC.setRemainingPrincipal(childC.getRemainingPrincipal()-lastPrincipleOutstanding+pv.getV_ls_outstadingPrincipal());

                    childD.setRemainingPrincipal(childD.getRemainingPrincipal()-lastPrincipleOutstanding+pv.getV_ls_outstadingPrincipal());

                    childE.setRemainingPrincipal(pv.getV_ls_outstadingPrincipal());

                    lastPrincipleOutstanding = pv.getV_ls_outstadingPrincipal();


                }


                if(pv.getV_ls_outstadingInterest()>=0)
                {

                    reportData.setRemainingInterest(reportData.getRemainingInterest()-lastInteresOutstanding+pv.getV_ls_outstadingInterest());

                    childA.setRemainingInterest(childA.getRemainingInterest()-lastInteresOutstanding+pv.getV_ls_outstadingInterest());


                    childB.setRemainingInterest(childB.getRemainingInterest()-lastInteresOutstanding+pv.getV_ls_outstadingInterest());


                    childC.setRemainingInterest(childC.getRemainingInterest()-lastInteresOutstanding+pv.getV_ls_outstadingInterest());

                    childD.setRemainingInterest(childD.getRemainingInterest()-lastInteresOutstanding+pv.getV_ls_outstadingInterest());

                    childE.setRemainingInterest(pv.getV_ls_outstadingInterest());

                    lastInteresOutstanding = pv.getV_ls_outstadingInterest();


                }

                if(pv.getV_ls_outstadingPenalty()>=0)
                {

                    reportData.setRemainingPenalty(reportData.getRemainingPenalty()-lastPenaltyOutstanding+pv.getV_ls_outstadingPenalty());

                    childA.setRemainingPenalty(childA.getRemainingPenalty()-lastPenaltyOutstanding+pv.getV_ls_outstadingPenalty());


                    childB.setRemainingPenalty(childB.getRemainingPenalty()-lastPenaltyOutstanding+pv.getV_ls_outstadingPenalty());


                    childC.setRemainingPenalty(childC.getRemainingPenalty()-lastPenaltyOutstanding+pv.getV_ls_outstadingPenalty());

                    childD.setRemainingPenalty(childD.getRemainingPenalty()-lastPenaltyOutstanding+pv.getV_ls_outstadingPenalty());

                    childE.setRemainingPenalty(pv.getV_ls_outstadingPenalty());

                    lastPenaltyOutstanding = pv.getV_ls_outstadingPenalty();


                }



                if(pv.getV_ls_totalOverdue()>=0)
                {

                    reportData.setOverdueAll(reportData.getOverdueAll()-lastTotalOverdue+pv.getV_ls_totalOverdue());

                    childA.setOverdueAll(childA.getOverdueAll()-lastTotalOverdue+pv.getV_ls_totalOverdue());


                    childB.setOverdueAll(childB.getOverdueAll()-lastTotalOverdue+pv.getV_ls_totalOverdue());


                    childC.setOverdueAll(childC.getOverdueAll()-lastTotalOverdue+pv.getV_ls_totalOverdue());

                    childD.setOverdueAll(childD.getOverdueAll()-lastTotalOverdue+pv.getV_ls_totalOverdue());

                    childE.setOverdueAll(pv.getV_ls_totalOverdue());

                    lastTotalOverdue = pv.getV_ls_totalOverdue();


                }


                if(pv.getV_ls_overduePrincipal()>=0)
                {

                    reportData.setOverduePrincipal(reportData.getOverduePrincipal()-lastPrincipleOverdue+pv.getV_ls_overduePrincipal());

                    childA.setOverduePrincipal(childA.getOverduePrincipal()-lastPrincipleOverdue+pv.getV_ls_overduePrincipal());


                    childB.setOverduePrincipal(childB.getOverduePrincipal()-lastPrincipleOverdue+pv.getV_ls_overduePrincipal());


                    childC.setOverduePrincipal(childC.getOverduePrincipal()-lastPrincipleOverdue+pv.getV_ls_overduePrincipal());

                    childD.setOverduePrincipal(childD.getOverduePrincipal()-lastPrincipleOverdue+pv.getV_ls_overduePrincipal());

                    childE.setOverduePrincipal(pv.getV_ls_overduePrincipal());

                    lastPrincipleOverdue = pv.getV_ls_overduePrincipal();


                }

                if(pv.getV_ls_overdueInterest()>=0)
                {

                    reportData.setOverdueInterest(reportData.getOverdueInterest()-lastInteresOverdue+pv.getV_ls_overdueInterest());

                    childA.setOverdueInterest(childA.getOverdueInterest()-lastInteresOverdue+pv.getV_ls_overdueInterest());


                    childB.setOverdueInterest(childB.getOverdueInterest()-lastInteresOverdue+pv.getV_ls_overdueInterest());


                    childC.setOverdueInterest(childC.getOverdueInterest()-lastInteresOverdue+pv.getV_ls_overdueInterest());

                    childD.setOverdueInterest(childD.getOverdueInterest()-lastInteresOverdue+pv.getV_ls_overdueInterest());

                    childE.setOverdueInterest(pv.getV_ls_overdueInterest());

                    lastInteresOverdue = pv.getV_ls_overdueInterest();


                }


                if(pv.getV_ls_overduePenalty()>=0)
                {

                    reportData.setOverduePenalty(reportData.getOverduePenalty()-lastPenaltyOverdue+pv.getV_ls_overduePenalty());

                    childA.setOverduePenalty(childA.getOverduePenalty()-lastPenaltyOverdue+pv.getV_ls_overduePenalty());


                    childB.setOverduePenalty(childB.getOverduePenalty()-lastPenaltyOverdue+pv.getV_ls_overduePenalty());


                    childC.setOverduePenalty(childC.getOverduePenalty()-lastPenaltyOverdue+pv.getV_ls_overduePenalty());

                    childD.setOverduePenalty(childD.getOverduePenalty()-lastPenaltyOverdue+pv.getV_ls_overduePenalty());

                    childE.setOverduePenalty(pv.getV_ls_overduePenalty());

                    lastPenaltyOverdue = pv.getV_ls_overduePenalty();


                }





                groupEid=this.getIdByGroupType(groupIds.get(4),loanSummaryView);
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


    public long getIdByGroupType(long groupType, LoanSummaryView loanSummaryView)
    {
        long idByGroupType=0;

        switch((short)groupType)
        {
            case 1:
                idByGroupType = loanSummaryView.getV_debtor_region_id();
                break;
            case 2:
                idByGroupType = loanSummaryView.getV_debtor_district_id();
                break;
            case 3:
                idByGroupType = loanSummaryView.getV_debtor_id();
                break;
            case 4:
                idByGroupType = loanSummaryView.getV_loan_id();
                break;
            case 12:
                idByGroupType = loanSummaryView.getV_ls_id();
                break;
            case 6:
                idByGroupType = loanSummaryView.getV_debtor_work_sector_id();
                break;






        }

        return  idByGroupType;
    }

    public String getNameByGroupType(long groupType, LoanSummaryView loanSummaryView)
    {
        String nameByGroupType="";

        switch((short)groupType)
        {
            case 1:
                nameByGroupType = loanSummaryView.getV_region_name();
                break;
            case 2:
                nameByGroupType = loanSummaryView.getV_district_name();
                break;
            case 3:
                nameByGroupType = loanSummaryView.getV_debtor_name();
                break;
            case 4:
                nameByGroupType =loanSummaryView.getV_credit_order_regNumber()
                                + " от "
                                + loanSummaryView.getV_credit_order_regDate()
                                + ", "
                                + loanSummaryView.getV_loan_reg_number()
                                + " от "
                                + loanSummaryView.getV_loan_reg_date();
                break;
            case 12:
                nameByGroupType = " расчет";
                break;
            case 6:
                nameByGroupType = loanSummaryView.getV_work_sector_name();
                break;






        }

        return  nameByGroupType;
    }

}
