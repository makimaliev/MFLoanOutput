package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.ReportTemplate;
import kg.gov.mf.loan.output.report.model.LoanAccrueReportData;
import kg.gov.mf.loan.output.report.model.LoanAccrueView;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import kg.gov.mf.loan.output.report.service.LoanAccrueViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.LinkedHashMap;
import java.util.List;


public class LoanAccrueReportDataManager {
	
	/**
     * Report GENERATION TOOL
     */

    @Autowired
    LoanViewService loanViewService;

    @Autowired
    LoanAccrueViewService loanAccrueViewService;

    public LoanAccrueReportData getReportDataGrouped(LoanAccrueReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        ReportTool reportTool = new ReportTool();

        LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<>();

        parameterS.putAll(reportTool.getParametersByTemplate(reportTemplate));

        reportData.getLoanAccrueViews().addAll(loanAccrueViewService.findByParameter(parameterS));

        for (LoanAccrueView loanAccrueView: reportData.getLoanAccrueViews())
        {
            System.out.println(loanAccrueView.getV_debtor_name());
        }

        return groupifyData(reportData, reportTemplate, reportTool );


    }

    public LoanAccrueReportData groupifyData(LoanAccrueReportData reportData, ReportTemplate reportTemplate, ReportTool reportTool)
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


        LoanAccrueReportData childA = null;
        LoanAccrueReportData childB = null;
        LoanAccrueReportData childC = null;
        LoanAccrueReportData childD = null;
        LoanAccrueReportData childE = null;
        LoanAccrueReportData childF = null;



        double lastLoanAmount = 0;

        double lastDisbursement = 0;
        double lastPrincipalPayment = 0;
        double lastInterestPayment = 0;
        double lastCollectedInterestPayment = 0;
        double lastCollectedPenaltyPayment = 0;

        for (LoanAccrueView loanAccrueView:reportData.getLoanAccrueViews())
        {
            if(reportTool.getIdByGroupType(reportTemplate.getGroupType1(),loanAccrueView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType1(),loanAccrueView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(reportTemplate.getGroupType1(),loanAccrueView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType2(),loanAccrueView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType2(),loanAccrueView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(reportTemplate.getGroupType2(),loanAccrueView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType3(),loanAccrueView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType3(),loanAccrueView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                currentgroupCid=reportTool.getIdByGroupType(reportTemplate.getGroupType3(),loanAccrueView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType4(),loanAccrueView)!=currentgroupDid)
            {

                LoanAccrueView lv = loanAccrueView;

                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType4(),loanAccrueView));
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

                currentgroupDid=reportTool.getIdByGroupType(reportTemplate.getGroupType4(),loanAccrueView);

                lastLoanAmount = 0;

                lastDisbursement =0;
                lastDisbursement = 0;
                lastPrincipalPayment = 0;
                lastInterestPayment = 0;
                lastCollectedInterestPayment = 0;
                lastCollectedPenaltyPayment = 0;


            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType5(),loanAccrueView)!=currentgroupEid)
            {

                LoanAccrueView pv = loanAccrueView;

                childE = childD.addChild();
                childE.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType5(),loanAccrueView));
                childE.setLevel((short)5);

                childE.setPaymentCount(1);
                childD.setPaymentCount(childD.getPaymentCount()+1);
                childC.setPaymentCount(childC.getPaymentCount()+1);
                childA.setPaymentCount(childA.getPaymentCount()+1);
                childB.setPaymentCount(childB.getPaymentCount()+1);
                reportData.setPaymentCount(reportData.getPaymentCount()+1);


                childE.setV_acc_fromDate(new java.sql.Date(pv.getV_acc_fromDate().getTime()));
                childE.setV_acc_toDate(new java.sql.Date(pv.getV_acc_toDate().getTime()));


                childE.setV_acc_daysInPeriod(pv.getV_acc_dayInPeriod());

                childE.setV_acc_lastInstPassed(pv.isV_acc_lastInstPassed());


                if(pv.getV_acc_interestAccrued()>=0)
                {
                    reportData.setV_acc_interestAccrued(reportData.getV_acc_interestAccrued()+pv.getV_acc_interestAccrued());
                    childA.setV_acc_interestAccrued(childA.getV_acc_interestAccrued()+pv.getV_acc_interestAccrued());
                    childB.setV_acc_interestAccrued(childB.getV_acc_interestAccrued()+pv.getV_acc_interestAccrued());
                    childC.setV_acc_interestAccrued(childC.getV_acc_interestAccrued()+pv.getV_acc_interestAccrued());
                    childD.setV_acc_interestAccrued(childD.getV_acc_interestAccrued()+pv.getV_acc_interestAccrued());
                    childE.setV_acc_interestAccrued(pv.getV_acc_interestAccrued());
                }

                if(pv.getV_acc_penaltyAccrued()>=0)
                {
                    reportData.setV_acc_penaltyAccrued(reportData.getV_acc_penaltyAccrued()+pv.getV_acc_penaltyAccrued());
                    childA.setV_acc_penaltyAccrued(childA.getV_acc_penaltyAccrued()+pv.getV_acc_penaltyAccrued());
                    childB.setV_acc_penaltyAccrued(childB.getV_acc_penaltyAccrued()+pv.getV_acc_penaltyAccrued());
                    childC.setV_acc_penaltyAccrued(childC.getV_acc_penaltyAccrued()+pv.getV_acc_penaltyAccrued());
                    childD.setV_acc_penaltyAccrued(childD.getV_acc_penaltyAccrued()+pv.getV_acc_penaltyAccrued());
                    childE.setV_acc_penaltyAccrued(pv.getV_acc_penaltyAccrued());
                }


                if(pv.getV_acc_penaltyOnPrincipalOverdue()>=0)
                {
                    reportData.setV_acc_penaltyOnPrincipalOverdue(reportData.getV_acc_penaltyOnPrincipalOverdue()+pv.getV_acc_penaltyOnPrincipalOverdue());
                    childA.setV_acc_penaltyOnPrincipalOverdue(childA.getV_acc_penaltyOnPrincipalOverdue()+pv.getV_acc_penaltyOnPrincipalOverdue());
                    childB.setV_acc_penaltyOnPrincipalOverdue(childB.getV_acc_penaltyOnPrincipalOverdue()+pv.getV_acc_penaltyOnPrincipalOverdue());
                    childC.setV_acc_penaltyOnPrincipalOverdue(childC.getV_acc_penaltyOnPrincipalOverdue()+pv.getV_acc_penaltyOnPrincipalOverdue());
                    childD.setV_acc_penaltyOnPrincipalOverdue(childD.getV_acc_penaltyOnPrincipalOverdue()+pv.getV_acc_penaltyOnPrincipalOverdue());
                    childE.setV_acc_penaltyOnPrincipalOverdue(pv.getV_acc_penaltyOnPrincipalOverdue());
                }


                if(pv.getV_acc_penaltyOnInterestOverdue()>=0)
                {
                    reportData.setV_acc_penaltyOnInterestOverdue(reportData.getV_acc_penaltyOnInterestOverdue()+pv.getV_acc_penaltyOnInterestOverdue());
                    childA.setV_acc_penaltyOnInterestOverdue(childA.getV_acc_penaltyOnInterestOverdue()+pv.getV_acc_penaltyOnInterestOverdue());
                    childB.setV_acc_penaltyOnInterestOverdue(childB.getV_acc_penaltyOnInterestOverdue()+pv.getV_acc_penaltyOnInterestOverdue());
                    childC.setV_acc_penaltyOnInterestOverdue(childC.getV_acc_penaltyOnInterestOverdue()+pv.getV_acc_penaltyOnInterestOverdue());
                    childD.setV_acc_penaltyOnInterestOverdue(childD.getV_acc_penaltyOnInterestOverdue()+pv.getV_acc_penaltyOnInterestOverdue());
                    childE.setV_acc_penaltyOnInterestOverdue(pv.getV_acc_penaltyOnInterestOverdue());
                }



                currentgroupEid=reportTool.getIdByGroupType(reportTemplate.getGroupType5(),loanAccrueView);
            }




        }


        return reportData;
    }




}
