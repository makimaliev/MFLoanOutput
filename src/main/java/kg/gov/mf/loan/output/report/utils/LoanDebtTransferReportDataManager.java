package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.ReportTemplate;
import kg.gov.mf.loan.output.report.model.LoanDebtTransferReportData;
import kg.gov.mf.loan.output.report.model.LoanDebtTransferView;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import kg.gov.mf.loan.output.report.service.LoanDebtTransferViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.LinkedHashMap;
import java.util.List;


public class LoanDebtTransferReportDataManager {
	
	/**
     * Report GENERATION TOOL
     */

    @Autowired
    LoanViewService loanViewService;

    @Autowired
    LoanDebtTransferViewService loanDebtTransferViewService;

    public LoanDebtTransferReportData getReportDataGrouped(LoanDebtTransferReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        ReportTool reportTool = new ReportTool();

        LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<>();

        parameterS.putAll(reportTool.getParametersByTemplate(reportTemplate));

        reportData.getLoanDebtTransferViews().addAll(loanDebtTransferViewService.findByParameter(parameterS));

        for (LoanDebtTransferView loanDebtTransferView: reportData.getLoanDebtTransferViews())
        {
            System.out.println(loanDebtTransferView.getV_debtor_name());
        }

        return groupifyData(reportData, reportTemplate, reportTool );


    }

    public LoanDebtTransferReportData groupifyData(LoanDebtTransferReportData reportData, ReportTemplate reportTemplate, ReportTool reportTool)
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


        LoanDebtTransferReportData childA = null;
        LoanDebtTransferReportData childB = null;
        LoanDebtTransferReportData childC = null;
        LoanDebtTransferReportData childD = null;
        LoanDebtTransferReportData childE = null;
        LoanDebtTransferReportData childF = null;



        double lastLoanAmount = 0;

        double lastDisbursement = 0;
        double lastPrincipalPayment = 0;
        double lastInterestPayment = 0;
        double lastCollectedInterestPayment = 0;
        double lastCollectedPenaltyPayment = 0;

        for (LoanDebtTransferView loanDebtTransferView:reportData.getLoanDebtTransferViews())
        {
            if(reportTool.getIdByGroupType(reportTemplate.getGroupType1(),loanDebtTransferView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType1(),loanDebtTransferView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(reportTemplate.getGroupType1(),loanDebtTransferView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType2(),loanDebtTransferView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType2(),loanDebtTransferView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(reportTemplate.getGroupType2(),loanDebtTransferView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType3(),loanDebtTransferView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType3(),loanDebtTransferView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                currentgroupCid=reportTool.getIdByGroupType(reportTemplate.getGroupType3(),loanDebtTransferView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType4(),loanDebtTransferView)!=currentgroupDid)
            {

                LoanDebtTransferView lv = loanDebtTransferView;

                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType4(),loanDebtTransferView));
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

                currentgroupDid=reportTool.getIdByGroupType(reportTemplate.getGroupType4(),loanDebtTransferView);

                lastLoanAmount = 0;

                lastDisbursement =0;
                lastDisbursement = 0;
                lastPrincipalPayment = 0;
                lastInterestPayment = 0;
                lastCollectedInterestPayment = 0;
                lastCollectedPenaltyPayment = 0;


            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType5(),loanDebtTransferView)!=currentgroupEid)
            {

                LoanDebtTransferView pv = loanDebtTransferView;

                childE = childD.addChild();
                childE.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType5(),loanDebtTransferView));
                childE.setLevel((short)5);

                childE.setDt_Count(1);
                childD.setDt_Count(childD.getDt_Count()+1);
                childC.setDt_Count(childC.getDt_Count()+1);
                childA.setDt_Count(childA.getDt_Count()+1);
                childB.setDt_Count(childB.getDt_Count()+1);
                reportData.setDt_Count(reportData.getDt_Count()+1);


                childE.setDt_date(new java.sql.Date(pv.getV_dt_date().getTime()));


                childE.setDt_number(pv.getV_dt_number());
                childE.setDt_goodsTypeId(pv.getV_dt_goodsTypeId());
                childE.setDt_pricePerUnit(pv.getV_dt_pricePerUnit());
                childE.setDt_quantity(pv.getV_dt_quantity());
                childE.setDt_unitTypeId(pv.getV_dt_unitTypeId());
                childE.setDt_transferPersonId(pv.getV_dt_transferPersonId());



                if(pv.getV_dt_totalCost()>=0)
                {
                    reportData.setDt_totalCost(reportData.getDt_totalCost()+pv.getV_dt_totalCost());
                    childA.setDt_totalCost(childA.getDt_totalCost()+pv.getV_dt_totalCost());
                    childB.setDt_totalCost(childB.getDt_totalCost()+pv.getV_dt_totalCost());
                    childC.setDt_totalCost(childC.getDt_totalCost()+pv.getV_dt_totalCost());
                    childD.setDt_totalCost(childD.getDt_totalCost()+pv.getV_dt_totalCost());
                    childE.setDt_totalCost(pv.getV_dt_totalCost());
                }




                currentgroupEid=reportTool.getIdByGroupType(reportTemplate.getGroupType5(),loanDebtTransferView);
            }




        }


        return reportData;
    }




}
