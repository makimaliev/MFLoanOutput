package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.ReportTemplate;
import kg.gov.mf.loan.output.report.model.LoanWriteOffReportData;
import kg.gov.mf.loan.output.report.model.LoanWriteOffView;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import kg.gov.mf.loan.output.report.service.LoanWriteOffViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.LinkedHashMap;
import java.util.List;


public class LoanWriteOffReportDataManager {
	
	/**
     * Report GENERATION TOOL
     */

    @Autowired
    LoanViewService loanViewService;

    @Autowired
    LoanWriteOffViewService LoanWriteOffViewService;

    public LoanWriteOffReportData getReportDataGrouped(LoanWriteOffReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        ReportTool reportTool = new ReportTool();

        LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<>();

        parameterS.putAll(reportTool.getParametersByTemplate(reportTemplate));

        reportData.getLoanWriteOffViews().addAll(LoanWriteOffViewService.findByParameter(parameterS));

        for (LoanWriteOffView LoanWriteOffView: reportData.getLoanWriteOffViews())
        {
            System.out.println(LoanWriteOffView.getV_debtor_name());
        }

        return groupifyData(reportData, reportTemplate, reportTool );


    }

    public LoanWriteOffReportData groupifyData(LoanWriteOffReportData reportData, ReportTemplate reportTemplate, ReportTool reportTool)
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


        LoanWriteOffReportData childA = null;
        LoanWriteOffReportData childB = null;
        LoanWriteOffReportData childC = null;
        LoanWriteOffReportData childD = null;
        LoanWriteOffReportData childE = null;
        LoanWriteOffReportData childF = null;



        double lastLoanAmount = 0;

        double lastDisbursement = 0;
        double lastPrincipalPayment = 0;
        double lastInterestPayment = 0;
        double lastCollectedInterestPayment = 0;
        double lastCollectedPenaltyPayment = 0;

        for (LoanWriteOffView LoanWriteOffView:reportData.getLoanWriteOffViews())
        {
            if(reportTool.getIdByGroupType(reportTemplate.getGroupType1(),LoanWriteOffView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType1(),LoanWriteOffView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(reportTemplate.getGroupType1(),LoanWriteOffView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType2(),LoanWriteOffView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType2(),LoanWriteOffView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(reportTemplate.getGroupType2(),LoanWriteOffView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType3(),LoanWriteOffView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType3(),LoanWriteOffView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                currentgroupCid=reportTool.getIdByGroupType(reportTemplate.getGroupType3(),LoanWriteOffView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType4(),LoanWriteOffView)!=currentgroupDid)
            {

                LoanWriteOffView lv = LoanWriteOffView;

                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType4(),LoanWriteOffView));
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

                currentgroupDid=reportTool.getIdByGroupType(reportTemplate.getGroupType4(),LoanWriteOffView);

                lastLoanAmount = 0;

                lastDisbursement =0;
                lastDisbursement = 0;
                lastPrincipalPayment = 0;
                lastInterestPayment = 0;
                lastCollectedInterestPayment = 0;
                lastCollectedPenaltyPayment = 0;


            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType5(),LoanWriteOffView)!=currentgroupEid)
            {

                LoanWriteOffView pv = LoanWriteOffView;

                childE = childD.addChild();
                childE.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType5(),LoanWriteOffView));
                childE.setLevel((short)5);

                childE.setWriteOffCount(1);
                childD.setWriteOffCount(childD.getWriteOffCount()+1);
                childC.setWriteOffCount(childC.getWriteOffCount()+1);
                childA.setWriteOffCount(childA.getWriteOffCount()+1);
                childB.setWriteOffCount(childB.getWriteOffCount()+1);
                reportData.setWriteOffCount(reportData.getWriteOffCount()+1);


                childE.setWo_date(new java.sql.Date(pv.getV_wo_date().getTime()));

                childE.setWo_description(pv.getV_wo_description());



                if(pv.getV_wo_totalAmount()>=0)
                {
                    reportData.setWo_totalAmount(reportData.getWo_totalAmount()+pv.getV_wo_totalAmount());
                    childA.setWo_totalAmount(childA.getWo_totalAmount()+pv.getV_wo_totalAmount());
                    childB.setWo_totalAmount(childB.getWo_totalAmount()+pv.getV_wo_totalAmount());
                    childC.setWo_totalAmount(childC.getWo_totalAmount()+pv.getV_wo_totalAmount());
                    childD.setWo_totalAmount(childD.getWo_totalAmount()+pv.getV_wo_totalAmount());
                    childE.setWo_totalAmount(pv.getV_wo_totalAmount());
                }

                if(pv.getV_wo_principal()>=0)
                {
                    reportData.setWo_principal(reportData.getWo_principal()+pv.getV_wo_principal());
                    childA.setWo_principal(childA.getWo_principal()+pv.getV_wo_principal());
                    childB.setWo_principal(childB.getWo_principal()+pv.getV_wo_principal());
                    childC.setWo_principal(childC.getWo_principal()+pv.getV_wo_principal());
                    childD.setWo_principal(childD.getWo_principal()+pv.getV_wo_principal());
                    childE.setWo_principal(pv.getV_wo_principal());
                }

                if(pv.getV_wo_interest()>=0)
                {
                    reportData.setWo_interest(reportData.getWo_interest()+pv.getV_wo_interest());
                    childA.setWo_interest(childA.getWo_interest()+pv.getV_wo_interest());
                    childB.setWo_interest(childB.getWo_interest()+pv.getV_wo_interest());
                    childC.setWo_interest(childC.getWo_interest()+pv.getV_wo_interest());
                    childD.setWo_interest(childD.getWo_interest()+pv.getV_wo_interest());
                    childE.setWo_interest(pv.getV_wo_interest());
                }

                if(pv.getV_wo_penalty()>=0)
                {
                    reportData.setWo_penalty(reportData.getWo_penalty()+pv.getV_wo_penalty());
                    childA.setWo_penalty(childA.getWo_penalty()+pv.getV_wo_penalty());
                    childB.setWo_penalty(childB.getWo_penalty()+pv.getV_wo_penalty());
                    childC.setWo_penalty(childC.getWo_penalty()+pv.getV_wo_penalty());
                    childD.setWo_penalty(childD.getWo_penalty()+pv.getV_wo_penalty());
                    childE.setWo_penalty(pv.getV_wo_penalty());
                }


                currentgroupEid=reportTool.getIdByGroupType(reportTemplate.getGroupType5(),LoanWriteOffView);
            }




        }


        return reportData;
    }




}
