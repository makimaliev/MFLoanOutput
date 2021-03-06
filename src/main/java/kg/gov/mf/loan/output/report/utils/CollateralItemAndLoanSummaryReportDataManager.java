package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.manage.model.collateral.CollateralItem;
import kg.gov.mf.loan.manage.model.collateral.ItemType;
import kg.gov.mf.loan.manage.model.process.LoanDetailedSummary;
import kg.gov.mf.loan.manage.model.process.LoanSummary;
import kg.gov.mf.loan.manage.service.collateral.ItemTypeService;
import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.CollateralItemViewService;
import kg.gov.mf.loan.output.report.service.LoanSummaryViewService;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.sql.Date;
import java.util.*;


public class CollateralItemAndLoanSummaryReportDataManager {
	
	/**
     * Report GENERATION TOOL
     */

    @Autowired
    ItemTypeService itemTypeService;

    @Autowired
    CollateralItemViewService collateralItemViewService;

    @Autowired
    LoanViewService loanViewService;

    @Autowired
    LoanSummaryViewService loanSummaryViewService;

    Map<Long,ItemType> itemTypeMap = new HashMap<Long,ItemType>();

    Map<Long,LoanSummary> loanSummaryMap = new HashMap<>();

    private LinkedHashSet<LoanSummaryView> calculatedSummaryViews = new LinkedHashSet<LoanSummaryView>(0);

    private LinkedHashSet<LoanView> loanViews = new LinkedHashSet<LoanView>(0);

    private LinkedHashMap<Long,LoanDetailedSummary> loanDetailedSummaryList = new LinkedHashMap<>();



    public CollateralItemReportData getReportDataGrouped(CollateralItemReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        ReportTool reportTool = new ReportTool();

        reportTool.initReference();
        reportTool.initCurrencyRatesMap(reportTemplate);

        LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<>();

        parameterS.putAll(reportTool.getParametersByTemplate(reportTemplate));

        reportData.getCollateralItemViews().addAll(collateralItemViewService.findByParameter(parameterS));


        Set<Long> itemLoanIds = new HashSet<>();
        Set<Long> viewLoanIds = new HashSet<>();



        CalculationTool calculationTool = new CalculationTool();

        loanViews.addAll(loanViewService.findByParameter(parameterS));

        for (CollateralItemView collateralItemView: reportData.getCollateralItemViews())
        {
            itemLoanIds.add(collateralItemView.getV_loan_id());
        }

        for (LoanView loanView: loanViews)
        {
            viewLoanIds.add(loanView.getV_loan_id());
        }

        for (Iterator<CollateralItemView> it = reportData.getCollateralItemViews().iterator(); it.hasNext();)
        {
            CollateralItemView s = it.next();

            if(!viewLoanIds.contains(s.getV_loan_id()))
            {
                it.remove();
            }
        }

        for (Iterator<LoanView> it = loanViews.iterator(); it.hasNext();)
        {
            LoanView s = it.next();

            if(!itemLoanIds.contains(s.getV_loan_id()))
            {
                it.remove();
            }
        }



        loanDetailedSummaryList.putAll(calculationTool.getAllLoanDetailedSummariesByLoanViewList(loanViews, reportTemplate.getOnDate() ));

        for (LoanView loanView: loanViews)
        {
            try
            {
                LoanSummary ls = calculationTool.getLoanSummaryCaluculatedByLoanIdAndOnDate(loanView, loanView.getV_loan_id(), reportTemplate.getOnDate(), loanDetailedSummaryList.get(loanView.getV_loan_id()));

                if(ls!=null)
                {
                    if(calculationTool.checkFilterOptions(ls,reportTemplate))
                    {

                        double thousands = 1;
                        double rate = 1;
//                        double rate2 = 1;
//
//                        try{
//                            if(reportTemplate.getInThousands()!=null)
//                            {
//                                thousands = reportTemplate.getInThousands();
//                            }
//                        }
//                        catch (Exception ex)
//                        {
//
//                        }

                        if(loanView.getV_loan_currency_id()>1)
                        {

                            rate = reportTool.getCurrencyRateValueByDateAndCurrencyTypeId(reportTemplate.getOnDate(),loanView.getV_loan_currency_id());


                            if(loanView.getV_loan_close_rate()!=null )
                            {
                                rate = loanView.getV_loan_close_rate();
                            }
                        }

                        ls.setTotalOutstanding(ls.getTotalOutstanding()*rate);

                        loanSummaryMap.put(loanView.getV_loan_id(),ls);
                    }
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex);
            }
        }


        for (Iterator<CollateralItemView> it = reportData.getCollateralItemViews().iterator(); it.hasNext();)
        {
            CollateralItemView s = it.next();

            if(loanSummaryMap.get(s.getV_loan_id())==null)
            {
                it.remove();
            }
        }


        return groupifyData(reportData, reportTemplate, reportTool );
    }

    public CollateralItemReportData groupifyData(CollateralItemReportData reportData, ReportTemplate reportTemplate, ReportTool reportTool)
    {





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

        CollateralItemReportData childA = null;
        CollateralItemReportData childB = null;
        CollateralItemReportData childC = null;
        CollateralItemReportData childD = null;
        CollateralItemReportData childE = null;
        CollateralItemReportData childF = null;

        for (CollateralItemView collateralItemView:reportData.getCollateralItemViews())
        {


            if(reportTool.getIdByGroupType(reportTemplate.getGroupType1(),collateralItemView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType1(),collateralItemView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(reportTemplate.getGroupType1(),collateralItemView);
                currentgroupBid=-1;
                currentgroupCid=-1;
                currentgroupDid=-1;
                currentgroupEid=-1;
                currentgroupFid=-1;

            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType2(),collateralItemView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType2(),collateralItemView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(reportTemplate.getGroupType2(),collateralItemView);
                currentgroupCid=-1;
                currentgroupDid=-1;
                currentgroupEid=-1;
                currentgroupFid=-1;


            }


            if(reportTool.getIdByGroupType(reportTemplate.getGroupType3(),collateralItemView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType3(),collateralItemView));
                childC.setLevel((short)3);

                childC.setCount(1);




                Integer debtorId = collateralItemView.getV_debtor_id().intValue();

                if(!reportData.getDebtorIds().contains(debtorId))
                {
                    reportData.getDebtorIds().add(debtorId);
                    reportData.setCount(reportData.getCount()+1);
                }

                if(!childA.getDebtorIds().contains(debtorId))
                {
                    childA.getDebtorIds().add(debtorId);
                    childA.setCount(childA.getCount()+1);
                }

                if(!childB.getDebtorIds().contains(debtorId))
                {
                    childB.getDebtorIds().add(debtorId);
                    childB.setCount(childB.getCount()+1);
                }



                currentgroupCid=reportTool.getIdByGroupType(reportTemplate.getGroupType3(),collateralItemView);
                currentgroupDid=-1;
                currentgroupEid=-1;
                currentgroupFid=-1;

            }


            if(reportTool.getIdByGroupType(reportTemplate.getGroupType4(),collateralItemView)!=currentgroupDid)
            {

                CollateralItemView lv = collateralItemView;

                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType4(),collateralItemView));
                childD.setLevel((short)4);

                childD.setDetailsCount(1);

                Integer agreementId = collateralItemView.getV_ca_id().intValue();

                if(!reportData.getAgreementIds().contains(agreementId))
                {
                    reportData.getAgreementIds().add(agreementId);
                    reportData.setDetailsCount(reportData.getDetailsCount()+1);
                }

                if(!childA.getAgreementIds().contains(agreementId))
                {
                    childA.getAgreementIds().add(agreementId);
                    childA.setDetailsCount(childA.getDetailsCount()+1);
                }

                if(!childB.getAgreementIds().contains(agreementId))
                {
                    childB.getAgreementIds().add(agreementId);
                    childB.setDetailsCount(childB.getDetailsCount()+1);
                }

                if(!childC.getAgreementIds().contains(agreementId))
                {
                    childC.getAgreementIds().add(agreementId);
                    childC.setDetailsCount(childC.getDetailsCount()+1);
                }


                childD.setID(lv.getV_ca_id());

                if(lv.getV_ca_agreementDate()!=null)
                    childD.setName("Договор о залоге "+lv.getV_ca_agreementNumber()+" от "+lv.getV_ca_agreementDate());
                else childD.setName("Договор о залоге "+lv.getV_ca_agreementNumber());


                if(lv.getV_ca_agreementDate()!=null)
                    childD.setCollateralAgreementDate(new java.sql.Date(lv.getV_ca_agreementDate().getTime()));
                childD.setCollateralAgreementNumber(lv.getV_ca_agreementNumber());

                if(lv.getV_ca_arrestRegDate()!=null)
                    childD.setCollateralArrestRegDate(new java.sql.Date(lv.getV_ca_arrestRegDate().getTime()));
                childD.setCollateralArrestRegNumber(lv.getV_ca_arrestRegNumber());

                if(lv.getV_ca_collateralOfficeRegDate()!=null)
                    childD.setCollateralOfficeRegDate(new java.sql.Date(lv.getV_ca_collateralOfficeRegDate().getTime()));
                childD.setCollateralOfficeRegNumber(lv.getV_ca_collateralOfficeRegNumber());

                if(lv.getV_ca_notaryOfficeRegDate()!=null)
                    childD.setCollateralNotaryOfficeRegDate(new java.sql.Date(lv.getV_ca_notaryOfficeRegDate().getTime()));
                else
                {

                }
                childD.setCollateralNotaryOfficeRegNumber(lv.getV_ca_notaryOfficeRegNumber());


                if(lv.getV_ci_arestPlace()!=null)
                    childD.setCollateralItemArrestPlace(lv.getV_ci_arestPlace());

                currentgroupDid=reportTool.getIdByGroupType(reportTemplate.getGroupType4(),collateralItemView);
                currentgroupEid=-1;
                currentgroupFid=-1;

            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType5(),collateralItemView)!=currentgroupEid)
            {

                CollateralItemView lv = collateralItemView;

                childE = childD.addChild();
                childE.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType5(),collateralItemView));

                childE.setName(collateralItemView.getV_credit_order_reg_number()+ " "+lv.getV_loan_reg_number()+ " от "+ lv.getV_loan_reg_date());
                childE.setLevel((short)5);

//                childE.setDetailsCount(1);
//                childD.setDetailsCount(childD.getDetailsCount()+1);
//                childC.setDetailsCount(childC.getDetailsCount()+1);
//                childA.setDetailsCount(childA.getDetailsCount()+1);
//                childB.setDetailsCount(childB.getDetailsCount()+1);
//                reportData.setDetailsCount(reportData.getDetailsCount()+1);


                childE.setID(lv.getV_loan_id());

                LoanSummary ls = new LoanSummary();

                ls = loanSummaryMap.get(lv.getV_loan_id());

                if(ls!=null)
                {
                    if(ls.getLoanAmount()!=null)
                    {
                        childE.setLoanAmount(ls.getLoanAmount());
                        childD.setLoanAmount(childD.getLoanAmount()+ls.getLoanAmount());
                        childC.setLoanAmount(childC.getLoanAmount()+ls.getLoanAmount());
                        childB.setLoanAmount(childB.getLoanAmount()+ls.getLoanAmount());
                        childA.setLoanAmount(childA.getLoanAmount()+ls.getLoanAmount());
                        reportData.setLoanAmount(reportData.getLoanAmount()+ls.getLoanAmount());
                    }


                    if(ls.getTotalDisbursed()!=null)
                    {
                        childE.setTotalDisbursment(ls.getTotalDisbursed());
                        childD.setTotalDisbursment(childD.getTotalDisbursment()+ls.getTotalDisbursed());
                        childC.setTotalDisbursment(childC.getTotalDisbursment()+ls.getTotalDisbursed());
                        childB.setTotalDisbursment(childB.getTotalDisbursment()+ls.getTotalDisbursed());
                        childA.setTotalDisbursment(childA.getTotalDisbursment()+ls.getTotalDisbursed());
                        reportData.setTotalDisbursment(reportData.getTotalDisbursment()+ls.getTotalDisbursed());
                    }

                    if(ls.getTotalPaidKGS()!=null)
                    {
                        childE.setTotalPaidInCurrency(ls.getTotalPaidKGS());
                        childD.setTotalPaidInCurrency(childD.getTotalPaidInCurrency()+ls.getTotalPaidKGS());
                        childC.setTotalPaidInCurrency(childC.getTotalPaidInCurrency()+ls.getTotalPaidKGS());
                        childB.setTotalPaidInCurrency(childB.getTotalPaidInCurrency()+ls.getTotalPaidKGS());
                        childA.setTotalPaidInCurrency(childA.getTotalPaidInCurrency()+ls.getTotalPaidKGS());
                        reportData.setTotalPaidInCurrency(reportData.getTotalPaidInCurrency()+ls.getTotalPaidKGS());
                    }

                    if(ls.getOnDate()!=null)
                    childE.setOnDate(new java.sql.Date(ls.getOnDate().getTime()));


                    if(ls.getTotalOutstanding()!=null)
                    {
                        childE.setRemainingSum(ls.getTotalOutstanding());
//                        childD.setRemainingSum(childD.getRemainingSum()+ls.getTotalOutstanding());
//                        childC.setRemainingSum(childC.getRemainingSum()+ls.getTotalOutstanding());
//                        childB.setRemainingSum(childB.getRemainingSum()+ls.getTotalOutstanding());
//                        childA.setRemainingSum(childA.getRemainingSum()+ls.getTotalOutstanding());
//                        reportData.setRemainingSum(reportData.getRemainingSum()+ls.getTotalOutstanding());

                        sumOperation(reportData, childA, childB, childC , childD,null, null, childD, true, lv.getV_loan_id().intValue(), "loanIds", "RemainingSum", ls.getTotalOutstanding());
                    }

                    if(ls.getOutstadingPrincipal()!=null)
                    {
                        childE.setRemainingPrincipal(ls.getOutstadingPrincipal());
                        childD.setRemainingPrincipal(childD.getRemainingPrincipal()+ls.getOutstadingPrincipal());
                        childC.setRemainingPrincipal(childC.getRemainingPrincipal()+ls.getOutstadingPrincipal());
                        childB.setRemainingPrincipal(childB.getRemainingPrincipal()+ls.getOutstadingPrincipal());
                        childA.setRemainingPrincipal(childA.getRemainingPrincipal()+ls.getOutstadingPrincipal());
                        reportData.setRemainingPrincipal(reportData.getRemainingPrincipal()+ls.getOutstadingPrincipal());
                    }



                    if(ls.getOutstadingInterest()!=null)
                    {
                        childE.setRemainingInterest(ls.getOutstadingInterest());
                        childD.setRemainingInterest(childD.getRemainingInterest()+ls.getOutstadingInterest());
                        childC.setRemainingInterest(childC.getRemainingInterest()+ls.getOutstadingInterest());
                        childB.setRemainingInterest(childB.getRemainingInterest()+ls.getOutstadingInterest());
                        childA.setRemainingInterest(childA.getRemainingInterest()+ls.getOutstadingInterest());
                        reportData.setRemainingInterest(reportData.getRemainingInterest()+ls.getOutstadingInterest());
                    }

                    if(ls.getOutstadingPenalty()!=null)
                    {
                        childE.setRemainingPenalty(ls.getOutstadingPenalty());
                        childD.setRemainingPenalty(childD.getRemainingPenalty()+ls.getOutstadingPenalty());
                        childC.setRemainingPenalty(childC.getRemainingPenalty()+ls.getOutstadingPenalty());
                        childB.setRemainingPenalty(childB.getRemainingPenalty()+ls.getOutstadingPenalty());
                        childA.setRemainingPenalty(childA.getRemainingPenalty()+ls.getOutstadingPenalty());
                        reportData.setRemainingPenalty(reportData.getRemainingPenalty()+ls.getOutstadingPenalty());
                    }

                    if(ls.getTotalOverdue()!=null)
                    {
                        childE.setOverdueAll(ls.getTotalOverdue());
                        childD.setOverdueAll(childD.getOverdueAll()+ls.getTotalOverdue());
                        childC.setOverdueAll(childC.getOverdueAll()+ls.getTotalOverdue());
                        childB.setOverdueAll(childB.getOverdueAll()+ls.getTotalOverdue());
                        childA.setOverdueAll(childA.getOverdueAll()+ls.getTotalOverdue());
                        reportData.setOverdueAll(reportData.getOverdueAll()+ls.getTotalOverdue());
                    }

                    if(ls.getOutstadingPrincipal()!=null)
                    {
                        childE.setOverduePrincipal(ls.getOutstadingPrincipal());
                        childD.setOverduePrincipal(childD.getOverduePrincipal()+ls.getOutstadingPrincipal());
                        childC.setOverduePrincipal(childC.getOverduePrincipal()+ls.getOutstadingPrincipal());
                        childB.setOverduePrincipal(childB.getOverduePrincipal()+ls.getOutstadingPrincipal());
                        childA.setOverduePrincipal(childA.getOverduePrincipal()+ls.getOutstadingPrincipal());
                        reportData.setOverduePrincipal(reportData.getOverduePrincipal()+ls.getOutstadingPrincipal());
                    }



                    if(ls.getOutstadingInterest()!=null)
                    {
                        childE.setOverdueInterest(ls.getOutstadingInterest());
                        childD.setOverdueInterest(childD.getOverdueInterest()+ls.getOutstadingInterest());
                        childC.setOverdueInterest(childC.getOverdueInterest()+ls.getOutstadingInterest());
                        childB.setOverdueInterest(childB.getOverdueInterest()+ls.getOutstadingInterest());
                        childA.setOverdueInterest(childA.getOverdueInterest()+ls.getOutstadingInterest());
                        reportData.setOverdueInterest(reportData.getOverdueInterest()+ls.getOutstadingInterest());
                    }

                    if(ls.getOutstadingPenalty()!=null)
                    {
                        childE.setOverduePenalty(ls.getOutstadingPenalty());
                        childD.setOverduePenalty(childD.getOverduePenalty()+ls.getOutstadingPenalty());
                        childC.setOverduePenalty(childC.getOverduePenalty()+ls.getOutstadingPenalty());
                        childB.setOverduePenalty(childB.getOverduePenalty()+ls.getOutstadingPenalty());
                        childA.setOverduePenalty(childA.getOverduePenalty()+ls.getOutstadingPenalty());
                        reportData.setOverduePenalty(reportData.getOverduePenalty()+ls.getOutstadingPenalty());
                    }

                }
                else
                {

                }





                currentgroupEid=reportTool.getIdByGroupType(reportTemplate.getGroupType5(),collateralItemView);

            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType6(),collateralItemView)!=currentgroupFid)
            {

                CollateralItemView pv = collateralItemView;

                childF = childE.addChild();
                childF.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType5(),collateralItemView));
                childF.setLevel((short)6);

                if(pv.getV_ca_agreementDate()!=null)
                    childF.setCollateralAgreementDate(new java.sql.Date(pv.getV_ca_agreementDate().getTime()));
                childE.setCollateralAgreementNumber(pv.getV_ca_agreementNumber());

                if(pv.getV_ca_arrestRegDate()!=null)
                    childF.setCollateralArrestRegDate(new java.sql.Date(pv.getV_ca_arrestRegDate().getTime()));
                childE.setCollateralArrestRegNumber(pv.getV_ca_arrestRegNumber());

                if(pv.getV_ca_collateralOfficeRegDate()!=null)
                    childF.setCollateralOfficeRegDate(new java.sql.Date(pv.getV_ca_collateralOfficeRegDate().getTime()));
                childF.setCollateralOfficeRegNumber(pv.getV_ca_collateralOfficeRegNumber());

                if(pv.getV_ca_notaryOfficeRegDate()!=null)
                    childF.setCollateralNotaryOfficeRegDate(new java.sql.Date(pv.getV_ca_notaryOfficeRegDate().getTime()));
                else
                {

                }
                childF.setCollateralNotaryOfficeRegNumber(pv.getV_ca_notaryOfficeRegNumber());

                childF.setCollateralItemName(pv.getV_ci_name());
                childF.setCollateralItemQuantity(pv.getV_ci_quantity());
                childF.setCollateralItemQuantityTypeId(pv.getV_ci_quantityTypeId());
                childF.setCollateralItemTypeId(pv.getV_ci_itemTypeId());


                if(pv.getV_ci_ownerName()!=null)
                    childF.setCollateralItemOwner(pv.getV_ci_ownerName());

                if(pv.getV_ci_inspection_date()!=null)
                    childF.setCollateralItemInspectionDate(new java.sql.Date(pv.getV_ci_inspection_date().getTime()));

                if(pv.getV_ci_last_condition()!=null)
                    childF.setCollateralItemLastCondition(pv.getV_ci_last_condition());

                if(pv.getV_ci_details()!=null)
                    childF.setCollateralItemDetails(pv.getV_ci_details());

                if(pv.getV_ci_collateral_description()!=null)
                    childF.setCollateralItemCollateralDescription(pv.getV_ci_collateral_description());




                if(pv.getV_ci_quantity()>=0)
                {
//                    childE.setCollateralItemQuantity(childE.getCollateralItemQuantity()+pv.getV_ci_quantity());
//                    childA.setCollateralItemQuantity(childA.getCollateralItemQuantity()+pv.getV_ci_quantity());
//                    childB.setCollateralItemQuantity(childB.getCollateralItemQuantity()+pv.getV_ci_quantity());
//                    childC.setCollateralItemQuantity(childC.getCollateralItemQuantity()+pv.getV_ci_quantity());
//                    childD.setCollateralItemQuantity(childD.getCollateralItemQuantity()+pv.getV_ci_quantity());
//                    reportData.setCollateralItemQuantity(reportData.getCollateralItemQuantity()+pv.getV_ci_quantity());


                    this.sumOperation(reportData, childA, childB, childC , childD,childE, null, childE, false, pv.getV_ci_id().intValue(), "itemIds", "collateralItemQuantity", pv.getV_ci_quantity());
                }

                if(pv.getV_ci_collateralValue()>=0)
                {
                    childF.setCollateralItemCollateralValue(childF.getCollateralItemCollateralValue()+pv.getV_ci_collateralValue());
//                    childE.setCollateralItemCollateralValue(childE.getCollateralItemCollateralValue()+pv.getV_ci_collateralValue());
//                    childA.setCollateralItemCollateralValue(childA.getCollateralItemCollateralValue()+pv.getV_ci_collateralValue());
//                    childB.setCollateralItemCollateralValue(childB.getCollateralItemCollateralValue()+pv.getV_ci_collateralValue());
//                    childC.setCollateralItemCollateralValue(childC.getCollateralItemCollateralValue()+pv.getV_ci_collateralValue());
//                    childD.setCollateralItemCollateralValue(childD.getCollateralItemCollateralValue()+pv.getV_ci_collateralValue());
//                    reportData.setCollateralItemCollateralValue(reportData.getCollateralItemCollateralValue()+pv.getV_ci_collateralValue());

                    this.sumOperation(reportData, childA, childB, childC , childD,childE, null , childE,false,  pv.getV_ci_id().intValue(), "itemIds", "collateralItemCollateralValue", pv.getV_ci_collateralValue());
                }

                if(pv.getV_ci_estimatedValue()>=0)
                {
                    childF.setCollateralItemEstimatedValue(childF.getCollateralItemEstimatedValue()+pv.getV_ci_estimatedValue());
//                    childE.setCollateralItemEstimatedValue(childE.getCollateralItemEstimatedValue()+pv.getV_ci_estimatedValue());
//                    childA.setCollateralItemEstimatedValue(childA.getCollateralItemEstimatedValue()+pv.getV_ci_estimatedValue());
//                    childB.setCollateralItemEstimatedValue(childB.getCollateralItemEstimatedValue()+pv.getV_ci_estimatedValue());
//                    childC.setCollateralItemEstimatedValue(childC.getCollateralItemEstimatedValue()+pv.getV_ci_estimatedValue());
//                    childD.setCollateralItemEstimatedValue(childD.getCollateralItemEstimatedValue()+pv.getV_ci_estimatedValue());
//                    reportData.setCollateralItemEstimatedValue(reportData.getCollateralItemEstimatedValue()+pv.getV_ci_estimatedValue());

                    this.sumOperation(reportData, childA, childB, childC , childD,childE, null, childE, true,pv.getV_ci_id().intValue(), "itemIds", "collateralItemEstimatedValue", pv.getV_ci_estimatedValue());
                }


                childF.setCollateralItemEstimatedValue(pv.getV_ci_estimatedValue());

                childF.setCollateralItemDescription(pv.getV_ci_description());


                childF.setCollateralItemInspectionStatusId(pv.getV_ci_inspection_status());

                childF.setCollateralItemArrestFreeStatusId(pv.getV_ci_arrestFree_status());

                childF.setCollateralItemConditionTypeId((pv.getV_ci_condition_type()));




                currentgroupFid=reportTool.getIdByGroupType(reportTemplate.getGroupType6(),collateralItemView);
            }
            else
            {
                for (CollateralItemReportData itemReportData: childD.getChildDataList())
                {
                    if(childE!=itemReportData)
                    if(itemReportData.getChildDataList().size()>0)
                    {
                        childE.setChildDataList(new LinkedList<>(itemReportData.getChildDataList()));
                        itemReportData.getChildDataList().clear();
                    }

                }

            }


        }


        return reportData;
    }


    private void sumOperation(CollateralItemReportData reportData,
                              CollateralItemReportData childA,
                              CollateralItemReportData childB,
                              CollateralItemReportData childC,
                              CollateralItemReportData childD,
                              CollateralItemReportData childE,
                              CollateralItemReportData childF,
                              CollateralItemReportData lastChild,
                              Boolean isLast,
                              Integer uniqueId,
                              String checkedField,
                              String sumField,
                              Double amount)
    {

        if(reportData!=null) checkForExistense(reportData, checkedField,sumField, uniqueId, amount, lastChild, isLast);
        if(childA!=null) checkForExistense(childA, checkedField,sumField, uniqueId, amount, lastChild, isLast);
        if(childB!=null) checkForExistense(childB, checkedField,sumField, uniqueId, amount, lastChild, isLast);
        if(childC!=null) checkForExistense(childC, checkedField,sumField, uniqueId, amount, lastChild, isLast);
        if(childD!=null) checkForExistense(childD, checkedField,sumField, uniqueId, amount, lastChild, isLast);
        if(childE!=null) checkForExistense(childE, checkedField,sumField, uniqueId, amount, lastChild, isLast);
        if(childF!=null) checkForExistense(childF, checkedField,sumField, uniqueId, amount, lastChild, isLast);


    }

    private void checkForExistense(CollateralItemReportData checkedObject,
                                   String checkedField,
                                   String sumField,
                                   Integer uniqueId,
                                   Double amount,
                                   CollateralItemReportData lastChild,
                                   Boolean isLast)
    {
        try {

            Object object;

            if(checkedObject!=null)
            {
                PropertyAccessor myAccessor = PropertyAccessorFactory.forDirectFieldAccess(checkedObject);

                Set<Integer> Ids = (HashSet<Integer>)myAccessor.getPropertyValue(checkedField);

//                Double amountOld = (Double)myAccessor.getPropertyValue(sumField);

                String sMethodName = "get"+sumField.substring(0, 1).toUpperCase()+sumField.substring(1, sumField.length());

                checkedObject.getClass().cast(checkedObject);
                object = checkedObject.getClass().getMethod(sMethodName,null).invoke(checkedObject);

                Double amountOld = (Double)object;



                Double amountNew = amount + amountOld;

                if (!Ids.contains(uniqueId))
                {
//                    if(checkedObject.equals(lastChild))
                    if(isLast)
                        Ids.add(uniqueId);
                    myAccessor.setPropertyValue(sumField, amountNew);
                }

            }

        }
        catch (Exception ex)
        {
System.out.println(ex);
        }

    }



}
