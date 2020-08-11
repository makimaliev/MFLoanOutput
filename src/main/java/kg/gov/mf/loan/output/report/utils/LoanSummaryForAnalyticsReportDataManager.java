package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.manage.model.loan.NormalLoan;
import kg.gov.mf.loan.manage.model.process.LoanDetailedSummary;
import kg.gov.mf.loan.manage.model.process.LoanSummary;
import kg.gov.mf.loan.output.report.model.LoanSummaryReportData;
import kg.gov.mf.loan.output.report.model.LoanSummaryView;
import kg.gov.mf.loan.output.report.model.LoanView;
import kg.gov.mf.loan.output.report.model.ReportTemplate;
import kg.gov.mf.loan.output.report.service.LoanSummaryViewService;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import kg.gov.mf.loan.output.report.service.ReferenceViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class LoanSummaryForAnalyticsReportDataManager {
	
	/**
     * Report GENERATION TOOL
     */

    @Autowired
    LoanViewService loanViewService;

    @Autowired
    LoanSummaryViewService loanSummaryViewService;

    @Autowired
    ReferenceViewService referenceViewService;

    private LinkedHashSet<LoanSummaryView> calculatedSummaryViews = new LinkedHashSet<LoanSummaryView>(0);

    private LinkedHashSet<LoanView> loanViews = new LinkedHashSet<LoanView>(0);

    private LinkedHashMap<Long,LoanDetailedSummary> loanDetailedSummaryList = new LinkedHashMap<>();

    String errorStringGlobal = "";

    public LoanSummaryReportData getReportDataGrouped(LoanSummaryReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);




        ReportTool reportTool = new ReportTool();

        LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<>();

        parameterS.putAll(reportTool.getParametersByTemplate(reportTemplate));

        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        Date today = new Date();

        Date today2 = null;
        try
        {
            today2 = formatter.parse(formatter.format(today));
        } catch (ParseException e)
        {
            e.printStackTrace();
        }

        if(reportTemplate.getOnDate()!=null)
        {
            List<String> Ids = new ArrayList<>();

            try
            {
                Ids.add(String.valueOf(reportTemplate.getOnDate().getTime()));
            }
            catch ( Exception ex )
            {

            }

            parameterS.put("r=odv_ls_on_date",Ids);
            parameterS.put("r=bdv_loan_reg_date",Ids);
        }
        else
        {
            reportTemplate.setOnDate(today2);
        }




        int equals = reportTemplate.getOnDate().compareTo(today2);


        if(reportTemplate.getName().contains("фикс"))
        {
            reportData.getLoanSummaryViews().addAll(loanSummaryViewService.findByParameter(parameterS));

            return groupifyData(reportData, reportTemplate, reportTool );

        }



//
//
//
        CalculationTool calculationTool = new CalculationTool();
//
//        for (LoanSummaryView loanSummaryView: reportData.getLoanSummaryViews())
//        {
//
//                checkCalculation( loanSummaryView, calculationTool);
//

//        }
//
//        if(errorStringGlobal.length()>0)
//        {
//        }


        loanViews.addAll(loanViewService.findByParameter(parameterS));

        loanDetailedSummaryList.putAll(calculationTool.getAllLoanDetailedSummariesByLoanViewList(loanViews, reportTemplate.getOnDate() ));

        for (LoanView loanView: loanViews)
        {
            try
            {
                LoanSummaryView lsv = new LoanSummaryView();

                LoanSummary ls = new LoanSummary();

                ls = calculationTool.getLoanSummaryCaluculatedByLoanIdAndOnDate(loanView, loanView.getV_loan_id(), reportTemplate.getOnDate(), loanDetailedSummaryList.get(loanView.getV_loan_id()));

                if(ls!=null)
                {

                    if(calculationTool.checkFilterOptions(ls,reportTemplate))
                    {
                        lsv = convertLoanView(loanView, ls);

                        if(lsv!=null)
                        {
                            calculatedSummaryViews.add(lsv);
                        }
                    }
                }
            }
            catch (Exception ex)
            {

            }

        }


        reportData.getLoanSummaryViews().addAll(calculatedSummaryViews);

        return groupifyData(reportData, reportTemplate, reportTool );

    }

    public LoanSummaryReportData groupifyData(LoanSummaryReportData reportData,ReportTemplate reportTemplate, ReportTool reportTool)
    {

        reportTool.initReference();

        reportTool.initCurrencyRatesMap(reportTemplate);

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

        LoanSummaryReportData childA = null;
        LoanSummaryReportData childB = null;
        LoanSummaryReportData childC = null;
        LoanSummaryReportData childD = null;
        LoanSummaryReportData childE = null;
        LoanSummaryReportData childF = null;



        double lastLoanAmount = 0;

        double lastDisbursement = 0;
        double lastTotalPaid = 0;
        double lastTotalPaidKGS = 0;
        double lastTotalOustanding = 0;
        double lastPrincipleOutstanding = 0;
        double lastInteresOutstanding = 0;
        double lastPenaltyOutstanding = 0;

        double lastTotalOverdue = 0;
        double lastPrincipleOverdue = 0;
        double lastInteresOverdue = 0;
        double lastPenaltyOverdue = 0;

        double lastTotalOustandingDiff = 0;
        double lastTotalOverdueDiff = 0;


        for (LoanSummaryView loanSummaryView:reportData.getLoanSummaryViews())
        {
            if(reportTool.getIdByGroupType(reportTemplate.getGroupType1(),loanSummaryView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType1(),loanSummaryView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(reportTemplate.getGroupType1(),loanSummaryView);
                currentgroupBid=-1;
                currentgroupCid=-1;
                currentgroupDid=-1;
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType2(),loanSummaryView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType2(),loanSummaryView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(reportTemplate.getGroupType2(),loanSummaryView);
                currentgroupCid=-1;
                currentgroupDid=-1;

            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType3(),loanSummaryView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType3(),loanSummaryView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                if(loanSummaryView.getV_debtor_group_id()!=null)
                {
                    childC.setDebtorGroup(loanSummaryView.getV_debtor_group_id());
                }

                if(loanSummaryView.getV_debtor_subGroup_id()!=null)
                {
                    childC.setDebtorSubGroup(loanSummaryView.getV_debtor_subGroup_id());
                }

                currentgroupCid=reportTool.getIdByGroupType(reportTemplate.getGroupType3(),loanSummaryView);
                currentgroupDid=-1;
            }



            if(reportTool.getIdByGroupType(reportTemplate.getGroupType4(),loanSummaryView)!=currentgroupDid)
            {
                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType4(),loanSummaryView));
                childD.setLevel((short)4);

                childD.setDetailsCount(1);
                childC.setDetailsCount(childC.getDetailsCount()+1);
                childA.setDetailsCount(childA.getDetailsCount()+1);
                childB.setDetailsCount(childB.getDetailsCount()+1);
                reportData.setDetailsCount(reportData.getDetailsCount()+1);

                childD.setID(loanSummaryView.getV_loan_id());
                childD.setName(loanSummaryView.getV_credit_order_reg_number()+", "+loanSummaryView.getV_loan_reg_number()+" от "+loanSummaryView.getV_loan_reg_date()+" в тыс. сомах");

                childD.setDetails(" test");
                childD.setDetails(loanSummaryView.getV_loan_description());

                childD.setDebtorName(loanSummaryView.getV_debtor_name());
                childD.setRegion(loanSummaryView.getV_debtor_region_id().shortValue());
                childD.setDistrict(loanSummaryView.getV_debtor_district_id().shortValue());
                childD.setWorkSector(loanSummaryView.getV_debtor_work_sector_id().shortValue());
                childD.setCreditLine(loanSummaryView.getV_loan_fund_id() != null ? loanSummaryView.getV_loan_fund_id().shortValue() : 30); // if null setting default value


                childD.setCurrency(loanSummaryView.getV_loan_currency_id().shortValue());
                childD.setLoanRegNumber(loanSummaryView.getV_loan_reg_number());
                childD.setOrderRegNumber(loanSummaryView.getV_credit_order_reg_number());
                childD.setLoanRegDate(new java.sql.Date(loanSummaryView.getV_loan_reg_date().getTime()));

                if(!(loanSummaryView.getV_last_date()==null))
                childD.setLastDate(new java.sql.Date(loanSummaryView.getV_last_date().getTime()));

                childD.setLoanType(loanSummaryView.getV_loan_type_id().shortValue());

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

                lastTotalOustandingDiff = 0;
                lastTotalOverdueDiff = 0;


                currentgroupDid=reportTool.getIdByGroupType(reportTemplate.getGroupType4(),loanSummaryView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType5(),loanSummaryView)!=currentgroupEid)
            {
                double thousands = 1000;
                double rate = 1;
                double rate2 = 1;

                try{
                    if(reportTemplate.getInThousands()!=null)
                    {
                        thousands = reportTemplate.getInThousands();
                    }
                }
                catch (Exception ex)
                {

                }


                if(loanSummaryView.getV_loan_currency_id()>1)
                {

                    rate = reportTool.getCurrencyRateValueByDateAndCurrencyTypeId(reportTemplate.getOnDate(),loanSummaryView.getV_loan_currency_id());


                    if(reportTemplate.getAdditionalDate()!=null)
                    {
                        rate2 = reportTool.getCurrencyRateValueByDateAndCurrencyTypeId(reportTemplate.getAdditionalDate(),loanSummaryView.getV_loan_currency_id());
                    }

                    if(loanSummaryView.getV_loan_close_rate()!=null )
                    {
                        rate = loanSummaryView.getV_loan_close_rate();
                        rate2= loanSummaryView.getV_loan_close_rate();
                    }


                }

                childD.setExchangeRate(rate);
                childD.getChildDataList().removeAll(childD.getChildDataList());

                childE = childD.addChild();
                childE.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType5(),loanSummaryView));
                childE.setLevel((short)5);

                LoanSummaryView pv = loanSummaryView;

                childE.setPaymentCount(1);
                childD.setPaymentCount(childD.getPaymentCount()+1);
                childC.setPaymentCount(childC.getPaymentCount()+1);
                childA.setPaymentCount(childA.getPaymentCount()+1);
                childB.setPaymentCount(childB.getPaymentCount()+1);
                reportData.setPaymentCount(reportData.getPaymentCount()+1);


                childE.setOnDate(new java.sql.Date(pv.getV_ls_on_date().getTime()));

                if(pv.getV_loan_amount()>=0)
                {

                    reportData.setLoanAmount(reportData.getLoanAmount()-lastLoanAmount+pv.getV_loan_amount()*rate/thousands);
                    childA.setLoanAmount(childA.getLoanAmount()-lastLoanAmount+pv.getV_loan_amount()*rate/thousands);
                    childB.setLoanAmount(childB.getLoanAmount()-lastLoanAmount+pv.getV_loan_amount()*rate/thousands);
                    childC.setLoanAmount(childC.getLoanAmount()-lastLoanAmount+pv.getV_loan_amount()*rate/thousands);
                    childD.setLoanAmount(childD.getLoanAmount()-lastLoanAmount+pv.getV_loan_amount()*rate/thousands);
                    childE.setLoanAmount(pv.getV_loan_amount()/thousands);

                    childD.setLoanAmountInCurrency(pv.getV_loan_amount()/thousands);

                    lastLoanAmount  = pv.getV_loan_amount()*rate/thousands;
                }
                else
                {
                    if(lastLoanAmount>=0)
                    {
                        reportData.setLoanAmount(reportData.getLoanAmount()-lastLoanAmount);
                        childA.setLoanAmount(childA.getLoanAmount()-lastLoanAmount);
                        childB.setLoanAmount(childB.getLoanAmount()-lastLoanAmount);
                        childC.setLoanAmount(childC.getLoanAmount()-lastLoanAmount);
                        childD.setLoanAmount(childD.getLoanAmount()-lastLoanAmount);

                        lastLoanAmount=0;
                    }

                }




                if(pv.getV_ls_total_disbursed()>=0)
                {
                    reportData.setTotalDisbursment(reportData.getTotalDisbursment()-lastDisbursement+pv.getV_ls_total_disbursed()*rate/thousands);
                    childA.setTotalDisbursment(childA.getTotalDisbursment()-lastDisbursement+pv.getV_ls_total_disbursed()*rate/thousands);
                    childB.setTotalDisbursment(childB.getTotalDisbursment()-lastDisbursement+pv.getV_ls_total_disbursed()*rate/thousands);
                    childC.setTotalDisbursment(childC.getTotalDisbursment()-lastDisbursement+pv.getV_ls_total_disbursed()*rate/thousands);
                    childD.setTotalDisbursment(childD.getTotalDisbursment()-lastDisbursement+pv.getV_ls_total_disbursed()*rate/thousands);
                    childE.setTotalDisbursment(pv.getV_ls_total_disbursed()/thousands);

                    childD.setTotalDisbursmentInCurrency(pv.getV_ls_total_disbursed()/thousands);

                    lastDisbursement = pv.getV_ls_total_disbursed()*rate/thousands;
                }

                else
                {
                    if(lastDisbursement>=0)
                    {
                        reportData.setTotalDisbursment(reportData.getTotalDisbursment()-lastDisbursement);
                        childA.setTotalDisbursment(childA.getTotalDisbursment()-lastDisbursement);
                        childB.setTotalDisbursment(childB.getTotalDisbursment()-lastDisbursement);
                        childC.setTotalDisbursment(childC.getTotalDisbursment()-lastDisbursement);
                        childD.setTotalDisbursment(childD.getTotalDisbursment()-lastDisbursement);

                        lastDisbursement=0;
                    }

                }

                if(pv.getV_ls_total_paid_kgs()>=0)
                {
                    reportData.setTotalPaid(reportData.getTotalPaid()-lastTotalPaid+pv.getV_ls_total_paid_kgs()/thousands);
                    childA.setTotalPaid(childA.getTotalPaid()-lastTotalPaid+pv.getV_ls_total_paid_kgs()/thousands);
                    childB.setTotalPaid(childB.getTotalPaid()-lastTotalPaid+pv.getV_ls_total_paid_kgs()/thousands);
                    childC.setTotalPaid(childC.getTotalPaid()-lastTotalPaid+pv.getV_ls_total_paid_kgs()/thousands);
                    childD.setTotalPaid(childD.getTotalPaid()-lastTotalPaid+pv.getV_ls_total_paid_kgs()/thousands);
                    childE.setTotalPaid(pv.getV_ls_total_paid()/thousands);

                    childD.setTotalPaidInCurrency(pv.getV_ls_total_paid()/thousands);

                    lastTotalPaid = pv.getV_ls_total_paid_kgs()/thousands;
                }


                if(pv.getV_ls_total_outstanding()>=0)
                {
                    reportData.setRemainingSum(reportData.getRemainingSum()-lastTotalOustanding+pv.getV_ls_total_outstanding()*rate/thousands);
                    childA.setRemainingSum(childA.getRemainingSum()-lastTotalOustanding+pv.getV_ls_total_outstanding()*rate/thousands);
                    childB.setRemainingSum(childB.getRemainingSum()-lastTotalOustanding+pv.getV_ls_total_outstanding()*rate/thousands);
                    childC.setRemainingSum(childC.getRemainingSum()-lastTotalOustanding+pv.getV_ls_total_outstanding()*rate/thousands);
                    childD.setRemainingSum(childD.getRemainingSum()-lastTotalOustanding+pv.getV_ls_total_outstanding()*rate/thousands);
                    childE.setRemainingSum(pv.getV_ls_total_outstanding()/thousands);

                    childD.setRemainingSumInCurrency(pv.getV_ls_total_outstanding()/thousands);

                    lastTotalOustanding = pv.getV_ls_total_outstanding()*rate/thousands;

                    reportData.setRemainingDiff(reportData.getRemainingDiff()-lastTotalOustandingDiff+pv.getV_ls_total_outstanding()*(rate-rate2)/thousands);
                    childA.setRemainingDiff(childA.getRemainingDiff()-lastTotalOustandingDiff+pv.getV_ls_total_outstanding()*(rate-rate2)/thousands);
                    childB.setRemainingDiff(childB.getRemainingDiff()-lastTotalOustandingDiff+pv.getV_ls_total_outstanding()*(rate-rate2)/thousands);
                    childC.setRemainingDiff(childC.getRemainingDiff()-lastTotalOustandingDiff+pv.getV_ls_total_outstanding()*(rate-rate2)/thousands);
                    childD.setRemainingDiff(childD.getRemainingDiff()-lastTotalOustandingDiff+pv.getV_ls_total_outstanding()*(rate-rate2)/thousands);
                    childE.setRemainingDiff(0);
                    lastTotalOustandingDiff = pv.getV_ls_total_outstanding()*(rate-rate2)/thousands;
                }
                else
                {
                    if(lastTotalOustanding>=0)
                    {
                        reportData.setRemainingSum(reportData.getRemainingSum()-lastTotalOustanding);
                        childA.setRemainingSum(childA.getRemainingSum()-lastTotalOustanding);
                        childB.setRemainingSum(childB.getRemainingSum()-lastTotalOustanding);
                        childC.setRemainingSum(childC.getRemainingSum()-lastTotalOustanding);
                        childD.setRemainingSum(childD.getRemainingSum()-lastTotalOustanding);

                        reportData.setRemainingDiff(reportData.getRemainingDiff()-lastTotalOustandingDiff);
                        childA.setRemainingDiff(childA.getRemainingDiff()-lastTotalOustandingDiff);
                        childB.setRemainingDiff(childB.getRemainingDiff()-lastTotalOustandingDiff);
                        childC.setRemainingDiff(childC.getRemainingDiff()-lastTotalOustandingDiff);
                        childD.setRemainingDiff(childD.getRemainingDiff()-lastTotalOustandingDiff);

                        lastTotalOustanding=0;
                        lastTotalOustandingDiff=0;
                    }
                }

                if(pv.getV_ls_outstading_principal()>=0)
                {
                    reportData.setRemainingPrincipal(reportData.getRemainingPrincipal()-lastPrincipleOutstanding+pv.getV_ls_outstading_principal()*rate/thousands);
                    childA.setRemainingPrincipal(childA.getRemainingPrincipal()-lastPrincipleOutstanding+pv.getV_ls_outstading_principal()*rate/thousands);
                    childB.setRemainingPrincipal(childB.getRemainingPrincipal()-lastPrincipleOutstanding+pv.getV_ls_outstading_principal()*rate/thousands);
                    childC.setRemainingPrincipal(childC.getRemainingPrincipal()-lastPrincipleOutstanding+pv.getV_ls_outstading_principal()*rate/thousands);
                    childD.setRemainingPrincipal(childD.getRemainingPrincipal()-lastPrincipleOutstanding+pv.getV_ls_outstading_principal()*rate/thousands);
                    childE.setRemainingPrincipal(pv.getV_ls_outstading_principal()/thousands);

                    childD.setRemainingPrincipalInCurrency(pv.getV_ls_outstading_principal()/thousands);

                    lastPrincipleOutstanding = pv.getV_ls_outstading_principal()*rate/thousands;
                }
                else
                {
                    if(lastPrincipleOutstanding>=0)
                    {
                        reportData.setRemainingPrincipal(reportData.getRemainingPrincipal()-lastPrincipleOutstanding);
                        childA.setRemainingPrincipal(childA.getRemainingPrincipal()-lastPrincipleOutstanding);
                        childB.setRemainingPrincipal(childB.getRemainingPrincipal()-lastPrincipleOutstanding);
                        childC.setRemainingPrincipal(childC.getRemainingPrincipal()-lastPrincipleOutstanding);
                        childD.setRemainingPrincipal(childD.getRemainingPrincipal()-lastPrincipleOutstanding);

                        lastPrincipleOutstanding=0;
                    }
                }


                if(pv.getV_ls_outstading_interest()>=0)
                {
                    reportData.setRemainingInterest(reportData.getRemainingInterest()-lastInteresOutstanding+pv.getV_ls_outstading_interest()*rate/thousands);
                    childA.setRemainingInterest(childA.getRemainingInterest()-lastInteresOutstanding+pv.getV_ls_outstading_interest()*rate/thousands);
                    childB.setRemainingInterest(childB.getRemainingInterest()-lastInteresOutstanding+pv.getV_ls_outstading_interest()*rate/thousands);
                    childC.setRemainingInterest(childC.getRemainingInterest()-lastInteresOutstanding+pv.getV_ls_outstading_interest()*rate/thousands);
                    childD.setRemainingInterest(childD.getRemainingInterest()-lastInteresOutstanding+pv.getV_ls_outstading_interest()*rate/thousands);
                    childE.setRemainingInterest(pv.getV_ls_outstading_interest()/thousands);

                    childD.setRemainingInterestInCurrency(pv.getV_ls_outstading_interest()/thousands);

                    lastInteresOutstanding = pv.getV_ls_outstading_interest()*rate/thousands;
                }
                else
                {
                    if(lastInteresOutstanding>=0)
                    {
                        reportData.setRemainingInterest(reportData.getRemainingInterest()-lastInteresOutstanding);
                        childA.setRemainingInterest(childA.getRemainingInterest()-lastInteresOutstanding);
                        childB.setRemainingInterest(childB.getRemainingInterest()-lastInteresOutstanding);
                        childC.setRemainingInterest(childC.getRemainingInterest()-lastInteresOutstanding);
                        childD.setRemainingInterest(childD.getRemainingInterest()-lastInteresOutstanding);

                        lastInteresOutstanding=0;
                    }
                }

                if(pv.getV_ls_outstading_penalty()>=0)
                {
                    reportData.setRemainingPenalty(reportData.getRemainingPenalty()-lastPenaltyOutstanding+pv.getV_ls_outstading_penalty()*rate/thousands);
                    childA.setRemainingPenalty(childA.getRemainingPenalty()-lastPenaltyOutstanding+pv.getV_ls_outstading_penalty()*rate/thousands);
                    childB.setRemainingPenalty(childB.getRemainingPenalty()-lastPenaltyOutstanding+pv.getV_ls_outstading_penalty()*rate/thousands);
                    childC.setRemainingPenalty(childC.getRemainingPenalty()-lastPenaltyOutstanding+pv.getV_ls_outstading_penalty()*rate/thousands);
                    childD.setRemainingPenalty(childD.getRemainingPenalty()-lastPenaltyOutstanding+pv.getV_ls_outstading_penalty()*rate/thousands);
                    childE.setRemainingPenalty(pv.getV_ls_outstading_penalty()/thousands);

                    childD.setRemainingPenaltyInCurrency(pv.getV_ls_outstading_penalty()/thousands);

                    lastPenaltyOutstanding = pv.getV_ls_outstading_penalty()*rate/thousands;
                }
                else
                {
                    if(lastPenaltyOutstanding>=0)
                    {
                        reportData.setRemainingPenalty(reportData.getRemainingPenalty()-lastPenaltyOutstanding);
                        childA.setRemainingPenalty(childA.getRemainingPenalty()-lastPenaltyOutstanding);
                        childB.setRemainingPenalty(childB.getRemainingPenalty()-lastPenaltyOutstanding);
                        childC.setRemainingPenalty(childC.getRemainingPenalty()-lastPenaltyOutstanding);
                        childD.setRemainingPenalty(childD.getRemainingPenalty()-lastPenaltyOutstanding);

                        lastPenaltyOutstanding=0;
                    }
                }



                if(pv.getV_ls_total_overdue()>=0)
                {
                    reportData.setOverdueAll(reportData.getOverdueAll()-lastTotalOverdue+pv.getV_ls_total_overdue()*rate/thousands);
                    childA.setOverdueAll(childA.getOverdueAll()-lastTotalOverdue+pv.getV_ls_total_overdue()*rate/thousands);
                    childB.setOverdueAll(childB.getOverdueAll()-lastTotalOverdue+pv.getV_ls_total_overdue()*rate/thousands);
                    childC.setOverdueAll(childC.getOverdueAll()-lastTotalOverdue+pv.getV_ls_total_overdue()*rate/thousands);
                    childD.setOverdueAll(childD.getOverdueAll()-lastTotalOverdue+pv.getV_ls_total_overdue()*rate/thousands);
                    childE.setOverdueAll(pv.getV_ls_total_overdue()/thousands);

                    childD.setOverdueAllInCurrency(pv.getV_ls_total_overdue()/thousands);

                    lastTotalOverdue = pv.getV_ls_total_overdue()*rate/thousands;

                    reportData.setOverdueDiff(reportData.getOverdueDiff()-lastTotalOverdueDiff+pv.getV_ls_total_overdue()*(rate-rate2)/thousands);
                    childA.setOverdueDiff(childA.getOverdueDiff()-lastTotalOverdueDiff+pv.getV_ls_total_overdue()*(rate-rate2)/thousands);
                    childB.setOverdueDiff(childB.getOverdueDiff()-lastTotalOverdueDiff+pv.getV_ls_total_overdue()*(rate-rate2)/thousands);
                    childC.setOverdueDiff(childC.getOverdueDiff()-lastTotalOverdueDiff+pv.getV_ls_total_overdue()*(rate-rate2)/thousands);
                    childD.setOverdueDiff(childD.getOverdueDiff()-lastTotalOverdueDiff+pv.getV_ls_total_overdue()*(rate-rate2)/thousands);
                    childE.setOverdueDiff(0);
                    lastTotalOverdueDiff = pv.getV_ls_total_overdue()*(rate-rate2)/thousands;
                }
                else
                {
                    if(lastTotalOverdue>=0)
                    {
                        reportData.setOverdueAll(reportData.getOverdueAll()-lastTotalOverdue);
                        childA.setOverdueAll(childA.getOverdueAll()-lastTotalOverdue);
                        childB.setOverdueAll(childB.getOverdueAll()-lastTotalOverdue);
                        childC.setOverdueAll(childC.getOverdueAll()-lastTotalOverdue);
                        childD.setOverdueAll(childD.getOverdueAll()-lastTotalOverdue);

                        reportData.setOverdueDiff(reportData.getOverdueDiff()-lastTotalOverdueDiff);
                        childA.setOverdueDiff(childA.getOverdueDiff()-lastTotalOverdueDiff);
                        childB.setOverdueDiff(childB.getOverdueDiff()-lastTotalOverdueDiff);
                        childC.setOverdueDiff(childC.getOverdueDiff()-lastTotalOverdueDiff);
                        childD.setOverdueDiff(childD.getOverdueDiff()-lastTotalOverdueDiff);

                        lastTotalOverdue=0;
                        lastTotalOverdueDiff=0;
                    }
                }


                if(pv.getV_ls_overdue_principal()>=0)
                {
                    reportData.setOverduePrincipal(reportData.getOverduePrincipal()-lastPrincipleOverdue+pv.getV_ls_overdue_principal()*rate/thousands);
                    childA.setOverduePrincipal(childA.getOverduePrincipal()-lastPrincipleOverdue+pv.getV_ls_overdue_principal()*rate/thousands);
                    childB.setOverduePrincipal(childB.getOverduePrincipal()-lastPrincipleOverdue+pv.getV_ls_overdue_principal()*rate/thousands);
                    childC.setOverduePrincipal(childC.getOverduePrincipal()-lastPrincipleOverdue+pv.getV_ls_overdue_principal()*rate/thousands);
                    childD.setOverduePrincipal(childD.getOverduePrincipal()-lastPrincipleOverdue+pv.getV_ls_overdue_principal()*rate/thousands);
                    childE.setOverduePrincipal(pv.getV_ls_overdue_principal()/thousands);

                    childD.setOverduePrincipalInCurrency(pv.getV_ls_overdue_principal()/thousands);

                    lastPrincipleOverdue = pv.getV_ls_overdue_principal()*rate/thousands;
                }
                else
                {
                    if(lastPrincipleOverdue>=0)
                    {
                        reportData.setOverduePrincipal(reportData.getOverduePrincipal()-lastPrincipleOverdue);
                        childA.setOverduePrincipal(childA.getOverduePrincipal()-lastPrincipleOverdue);
                        childB.setOverduePrincipal(childB.getOverduePrincipal()-lastPrincipleOverdue);
                        childC.setOverduePrincipal(childC.getOverduePrincipal()-lastPrincipleOverdue);
                        childD.setOverduePrincipal(childD.getOverduePrincipal()-lastPrincipleOverdue);

                        lastPrincipleOverdue=0;
                    }
                }

                if(pv.getV_ls_overdue_interest()>=0)
                {
                    reportData.setOverdueInterest(reportData.getOverdueInterest()-lastInteresOverdue+pv.getV_ls_overdue_interest()*rate/thousands);
                    childA.setOverdueInterest(childA.getOverdueInterest()-lastInteresOverdue+pv.getV_ls_overdue_interest()*rate/thousands);
                    childB.setOverdueInterest(childB.getOverdueInterest()-lastInteresOverdue+pv.getV_ls_overdue_interest()*rate/thousands);
                    childC.setOverdueInterest(childC.getOverdueInterest()-lastInteresOverdue+pv.getV_ls_overdue_interest()*rate/thousands);
                    childD.setOverdueInterest(childD.getOverdueInterest()-lastInteresOverdue+pv.getV_ls_overdue_interest()*rate/thousands);
                    childE.setOverdueInterest(pv.getV_ls_overdue_interest()/thousands);

                    childD.setOverdueInterestInCurrency(pv.getV_ls_overdue_interest()/thousands);

                    lastInteresOverdue = pv.getV_ls_overdue_interest()*rate/thousands;
                }
                else
                {
                    if(lastInteresOverdue>=0)
                    {
                        reportData.setOverdueInterest(reportData.getOverdueInterest()-lastInteresOverdue);
                        childA.setOverdueInterest(childA.getOverdueInterest()-lastInteresOverdue);
                        childB.setOverdueInterest(childB.getOverdueInterest()-lastInteresOverdue);
                        childC.setOverdueInterest(childC.getOverdueInterest()-lastInteresOverdue);
                        childD.setOverdueInterest(childD.getOverdueInterest()-lastInteresOverdue);

                        lastInteresOverdue=0;
                    }
                }


                if(pv.getV_ls_overdue_penalty()>=0)
                {
                    reportData.setOverduePenalty(reportData.getOverduePenalty()-lastPenaltyOverdue+pv.getV_ls_overdue_penalty()*rate/thousands);
                    childA.setOverduePenalty(childA.getOverduePenalty()-lastPenaltyOverdue+pv.getV_ls_overdue_penalty()*rate/thousands);
                    childB.setOverduePenalty(childB.getOverduePenalty()-lastPenaltyOverdue+pv.getV_ls_overdue_penalty()*rate/thousands);
                    childC.setOverduePenalty(childC.getOverduePenalty()-lastPenaltyOverdue+pv.getV_ls_overdue_penalty()*rate/thousands);
                    childD.setOverduePenalty(childD.getOverduePenalty()-lastPenaltyOverdue+pv.getV_ls_overdue_penalty()*rate/thousands);
                    childE.setOverduePenalty(pv.getV_ls_overdue_penalty()/thousands);

                    childD.setOverduePenaltyInCurrency(pv.getV_ls_overdue_penalty()/thousands);

                    lastPenaltyOverdue = pv.getV_ls_overdue_penalty()*rate/thousands;
                }
                else
                {
                    if(lastPenaltyOverdue>=0)
                    {
                        reportData.setOverduePenalty(reportData.getOverduePenalty()-lastPenaltyOverdue);
                        childA.setOverduePenalty(childA.getOverduePenalty()-lastPenaltyOverdue);
                        childB.setOverduePenalty(childB.getOverduePenalty()-lastPenaltyOverdue);
                        childC.setOverduePenalty(childC.getOverduePenalty()-lastPenaltyOverdue);
                        childD.setOverduePenalty(childD.getOverduePenalty()-lastPenaltyOverdue);

                        lastPenaltyOverdue=0;
                    }
                }



                currentgroupEid=reportTool.getIdByGroupType(reportTemplate.getGroupType5(),loanSummaryView);

                if(loanSummaryView.getV_loan_currency_id()==1)
                {
                    childD.getChildDataList().removeAll(childD.getChildDataList());
                }
                else
                {
                    childE.setName(" в тыс "+reportTool.getNameByMapName("currency_type",loanSummaryView.getV_loan_currency_id()) + " по курсу "+ rate);
                }
            }



        }


        return reportData;
    }

    public void checkCalculation(LoanSummaryView lsv, CalculationTool calculationTool)
    {

//        LoanSummary lsOrigin = convertLoanSummaryView(lsv);
//
//        LoanSummary ls = calculationTool.getLoanSummaryCaluculatedByLoanIdAndOnDate(lsOrigin, lsv.getV_loan_id(), lsv.getV_loan_close_date() );
//
//
//
//        errorStringGlobal+= compareCalculation(lsOrigin,ls);



    }

    public LoanSummary convertLoanSummaryView( LoanSummaryView lsv)
    {
        LoanSummary convertedLoanSummary = new LoanSummary();

        convertedLoanSummary.setOnDate(lsv.getV_ls_on_date());
        convertedLoanSummary.setLoanAmount(lsv.getV_loan_amount());
        convertedLoanSummary.setTotalPaid(lsv.getV_ls_total_paid());
        convertedLoanSummary.setTotalPaidKGS(lsv.getV_ls_total_paid_kgs());
        convertedLoanSummary.setTotalDisbursed(lsv.getV_ls_total_disbursed());
        convertedLoanSummary.setOutstadingPrincipal(lsv.getV_ls_outstading_principal());
        convertedLoanSummary.setOutstadingInterest(lsv.getV_ls_outstading_interest());
        convertedLoanSummary.setOutstadingPenalty(lsv.getV_ls_outstading_penalty());
        convertedLoanSummary.setOverduePrincipal(lsv.getV_ls_overdue_principal());
        convertedLoanSummary.setOverdueInterest(lsv.getV_ls_overdue_interest());
        convertedLoanSummary.setOverduePenalty(lsv.getV_ls_overdue_penalty());
        convertedLoanSummary.setTotalOutstanding(lsv.getV_ls_total_outstanding());
        convertedLoanSummary.setTotalDisbursed(lsv.getV_ls_total_disbursed());
        convertedLoanSummary.setTotalOverdue(lsv.getV_ls_total_overdue());
        convertedLoanSummary.setTotalPaid(lsv.getV_ls_total_paid());
        convertedLoanSummary.setTotalPaidKGS(lsv.getV_ls_total_paid_kgs());

        NormalLoan loan = new NormalLoan();
        loan.setId(lsv.getV_loan_id() );
        convertedLoanSummary.setLoan(loan);

        return convertedLoanSummary;
    }

    public String compareCalculation(LoanSummary first, LoanSummary second)
    {

        String errorString = "";

        if(!(Math.round(first.getLoanAmount())==Math.round(second.getLoanAmount())))
        {
            errorString+=" amount == loan id == "+first.getLoan().getId().toString()+ " error "+first.getLoanAmount().toString()+ " <> "+second.getLoanAmount().toString()+" \n";

        }
        if(!(Math.round(first.getTotalDisbursed())==Math.round(second.getTotalDisbursed()))) {
            errorString+=" disbursement == loan id == "+first.getLoan().getId().toString()+ " error "+first.getTotalDisbursed().toString()+ " <> "+second.getTotalDisbursed().toString()+" \n";
        }
        if(!(Math.round(first.getTotalPaid())==Math.round(second.getTotalPaid()))) {
            errorString+=" total paid == loan id == "+first.getLoan().getId().toString()+ " error "+first.getTotalPaid().toString()+ " <> "+second.getTotalPaid().toString()+" \n";
        }

//        if(!(first.getTotalPaidKGS().intValue()==second.getTotalPaidKGS().intValue())) {
//            errorString+=" total paid kgs == loan id == "+first.getLoan().getId().toString()+ " error "+first.getTotalPaidKGS().toString()+ " <> "+second.getTotalPaidKGS().toString()+" \n";
//
//        }
        if(!(Math.round(first.getTotalOutstanding())==Math.round(second.getTotalOutstanding()))) {
            errorString+=" outstanding == loan id == "+first.getLoan().getId().toString()+ " error "+Math.round(first.getTotalOutstanding())+ " <> "+Math.round(second.getTotalOutstanding())+" \n";
        }

        if(!(Math.round(first.getOutstadingPrincipal())==Math.round(second.getOutstadingPrincipal()))) {
            errorString+=" outstanding principal == loan id == "+first.getLoan().getId().toString()+ " error "+Math.round(first.getOutstadingPrincipal())+ " <> "+Math.round(second.getOutstadingPrincipal())+" \n";
        }

        if(!(Math.round(first.getOutstadingInterest())==Math.round(second.getOutstadingInterest()))) {
            errorString+=" outstanding interest == loan id == "+first.getLoan().getId().toString()+ " error "+Math.round(first.getOutstadingInterest())+ " <> "+Math.round(second.getOutstadingInterest())+" \n";
        }

        if(!(Math.round(first.getOutstadingPenalty())==Math.round(second.getOutstadingPenalty()))) {
            errorString+=" outstanding penalty == loan id == "+first.getLoan().getId().toString()+ " error "+Math.round(first.getOutstadingPenalty())+ " <> "+Math.round(second.getOutstadingPenalty())+" \n";
        }
//

          if(!(Math.round(first.getTotalOverdue())==Math.round(second.getTotalOverdue()))) {
            errorString+=" overdue == loan id == "+first.getLoan().getId().toString()+ " error "+first.getTotalOverdue().toString()+ " <> "+second.getTotalOverdue().toString()+" \n";
        }

        if(!(Math.round(first.getOverduePrincipal())==Math.round(second.getOverduePrincipal()))) {
            errorString += " overdue == loan id == " + first.getLoan().getId().toString() + " error " + first.getOverduePrincipal().toString() + " <> " + second.getOverduePrincipal().toString() + " \n";
        }

        if(!(Math.round(first.getOverdueInterest())==Math.round(second.getOverdueInterest()))) {
            errorString += " overdue interest == loan id == " + first.getLoan().getId().toString() + " error " + first.getOverdueInterest().toString() + " <> " + second.getOverdueInterest().toString() + " \n";
        }

        if(!(Math.round(first.getOverduePenalty())==Math.round(second.getOverduePenalty()))) {
            errorString += " overdue penalty == loan id == " + first.getLoan().getId().toString() + " error " + first.getOverduePenalty().toString() + " <> " + second.getOverduePenalty().toString() + " \n";
        }


        return errorString;
    }


    public LoanSummaryView convertLoanView( LoanView lv, LoanSummary ls)
    {
        LoanSummaryView convertedLoanSummaryView = new LoanSummaryView();

        convertedLoanSummaryView.setV_loan_amount(lv.getV_loan_amount());
        convertedLoanSummaryView.setV_loan_reg_date(lv.getV_loan_reg_date());
        convertedLoanSummaryView.setV_loan_reg_number(lv.getV_loan_reg_number());
        convertedLoanSummaryView.setV_loan_type_id(lv.getV_loan_type_id());
        convertedLoanSummaryView.setV_loan_currency_id(lv.getV_loan_currency_id());
        convertedLoanSummaryView.setV_loan_fin_group_id(lv.getV_loan_fin_group_id());
        convertedLoanSummaryView.setV_loan_state_id(lv.getV_loan_state_id());
        convertedLoanSummaryView.setV_loan_fund_id(lv.getV_loan_fund_id());
        convertedLoanSummaryView.setV_loan_id(lv.getV_loan_id());
        convertedLoanSummaryView.setV_loan_supervisor_id(lv.getV_loan_supervisor_id());
        convertedLoanSummaryView.setV_credit_order_reg_date(lv.getV_credit_order_reg_date());
        convertedLoanSummaryView.setV_credit_order_reg_number(lv.getV_credit_order_reg_number());
        convertedLoanSummaryView.setV_credit_order_type_id(lv.getV_credit_order_type_id());
        convertedLoanSummaryView.setV_loan_close_date(lv.getV_loan_close_date());
        convertedLoanSummaryView.setV_loan_close_rate(lv.getV_loan_close_rate());
        convertedLoanSummaryView.setV_debtor_id(lv.getV_debtor_id());
        convertedLoanSummaryView.setV_debtor_name(lv.getV_debtor_name());
        convertedLoanSummaryView.setV_debtor_region_id(lv.getV_debtor_region_id());
        convertedLoanSummaryView.setV_debtor_district_id(lv.getV_debtor_district_id());
        convertedLoanSummaryView.setV_debtor_aokmotu_id(lv.getV_debtor_aokmotu_id());
        convertedLoanSummaryView.setV_debtor_village_id(lv.getV_debtor_village_id());
        convertedLoanSummaryView.setV_debtor_work_sector_id(lv.getV_debtor_work_sector_id());
        convertedLoanSummaryView.setV_debtor_org_form_id(lv.getV_debtor_org_form_id());
        convertedLoanSummaryView.setV_debtor_type_id(lv.getV_debtor_type_id());
        convertedLoanSummaryView.setV_ls_total_disbursed(ls.getTotalDisbursed());
        convertedLoanSummaryView.setV_ls_id(ls.getId());
        convertedLoanSummaryView.setV_ls_on_date(ls.getOnDate());
        convertedLoanSummaryView.setV_ls_total_paid(ls.getTotalPaid());
        convertedLoanSummaryView.setV_ls_total_paid_kgs(ls.getTotalPaidKGS());
        convertedLoanSummaryView.setV_ls_total_outstanding(ls.getTotalOutstanding());
        convertedLoanSummaryView.setV_ls_outstading_principal(ls.getOutstadingPrincipal());
        convertedLoanSummaryView.setV_ls_outstading_interest(ls.getOutstadingInterest());
        convertedLoanSummaryView.setV_ls_outstading_penalty(ls.getOutstadingPenalty());
        convertedLoanSummaryView.setV_ls_total_overdue(ls.getTotalOverdue());
        convertedLoanSummaryView.setV_ls_overdue_principal(ls.getOverduePrincipal());
        convertedLoanSummaryView.setV_ls_overdue_interest(ls.getOverdueInterest());
        convertedLoanSummaryView.setV_ls_overdue_penalty(ls.getOverduePenalty());
        convertedLoanSummaryView.setV_last_date(ls.getCreateDate());

        convertedLoanSummaryView.setV_debtor_group_id(lv.getV_debtor_group_id());
        convertedLoanSummaryView.setV_debtor_subGroup_id(lv.getV_debtor_subGroup_id());

        convertedLoanSummaryView.setV_loan_description(lv.getV_loan_description());

        return convertedLoanSummaryView;
    }






}
