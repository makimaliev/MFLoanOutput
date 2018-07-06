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

        long groupAtype = reportTool.getGroupType(reportTemplate,1);
        long groupBtype = reportTool.getGroupType(reportTemplate,2);
        long groupCtype = reportTool.getGroupType(reportTemplate,3);
        long groupDtype = reportTool.getGroupType(reportTemplate,4);
        long groupEtype = 9;



        // initial filter by report filter parameters

        reportData.getLoanSummaryViews().addAll(loanSummaryViewService.findByParameter(parameterS));


        reportData.setOnDate(new java.sql.Date(onDate.getTime()));

        Set<Long> groupAIds = new HashSet<Long>();

        ArrayList<LoanSummaryView> allLoanSummaryViews = new ArrayList<LoanSummaryView>();

        return groupifyData(reportData,groupIds,reportTemplate);
    }

    public LoanSummaryReportData groupifyData(LoanSummaryReportData reportData, List<Long> groupIds,ReportTemplate reportTemplate)
    {

        ReportTool reportTool = new ReportTool();

        reportTool.initReference();



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
            if(reportTool.getIdByGroupType(groupAid,loanSummaryView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(groupAid,loanSummaryView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(groupAid,loanSummaryView);
            }

            if(reportTool.getIdByGroupType(groupBid,loanSummaryView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(groupBid,loanSummaryView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(groupBid,loanSummaryView);
            }

            if(reportTool.getIdByGroupType(groupCid,loanSummaryView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(groupCid,loanSummaryView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                currentgroupCid=reportTool.getIdByGroupType(groupCid,loanSummaryView);
            }

            if(reportTool.getIdByGroupType(groupDid,loanSummaryView)!=currentgroupDid)
            {
                LoanSummaryView lv = loanSummaryView;

                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(groupDid,loanSummaryView));
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

                currentgroupDid=reportTool.getIdByGroupType(groupDid,loanSummaryView);

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

            if(reportTool.getIdByGroupType(groupEid,loanSummaryView)!=currentgroupEid)
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

                    reportData.setTotalDisbursment(reportData.getTotalDisbursment()-lastDisbursement+pv.getV_ls_totalDisbursed());

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





                currentgroupEid=reportTool.getIdByGroupType(groupEid,loanSummaryView);
            }


        }


        return reportData;
    }




}
