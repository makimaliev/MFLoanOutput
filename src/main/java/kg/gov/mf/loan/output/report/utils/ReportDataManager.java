package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.*;


public class ReportDataManager {
	
	/**
     * Report GENERATION TOOL
     */

    @Autowired
    LoanViewService loanViewService;


    public LoanReportData getReportDataGrouped(LoanReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);


        Set<LoanView> loanViews =  new HashSet<LoanView>();

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

        reportData.getLoanViews().addAll(loanViewService.findByParameter(parameterS));


        reportData.setOnDate(new java.sql.Date(onDate.getTime()));

        Set<Long> groupAIds = new HashSet<Long>();

        ArrayList<LoanView> allLoanViews = new ArrayList<LoanView>();

        for (LoanView loanView:reportData.getLoanViews())
        {
            groupAIds.add(loanView.getV_debtor_region_id());
            allLoanViews.add(loanView);
        }

        for (Long groupAId: groupAIds)
        {
            LoanReportData childA = reportData.addChild();
            childA.setName(groupAId.toString());
            childA.setLevel((short)1);

            LinkedHashMap<String,List<Long>> parameterA = new LinkedHashMap<String,List<Long>>();

            List<Long> regionIds = new ArrayList<>();
            regionIds.add(groupAId);


            parameterA.putAll(parameterS);
            parameterA.put("region",regionIds);

            childA.getLoanViews().addAll(loanViewService.findByParameter(parameterA));

            Set<Long> groupBIds = new HashSet<Long>();

            for (LoanView loanView:childA.getLoanViews())
            {
                groupBIds.add(loanView.getV_debtor_district_id());
            }

            for (Long groupBId: groupBIds)
            {
                LoanReportData childB = childA.addChild();
                childB.setName(groupBId.toString());
                childB.setLevel((short)2);

                LinkedHashMap<String,List<Long>> parameterB = new LinkedHashMap<String,List<Long>>();

                List<Long> districtIds = new ArrayList<>();
                districtIds.add(groupBId);

                parameterB.putAll(parameterA);
                parameterB.put("region",regionIds);
                parameterB.put("district",districtIds);


                Set<LoanView> loanViewsB =  new HashSet<LoanView>();

                loanViewsB.addAll(loanViewService.findByParameter(parameterB));

                childB.setLoanViews(loanViewsB);

                Set<Long> debtorIds = new HashSet<Long>();

                for (LoanView loanViewD:childB.getLoanViews())
                {
                    debtorIds.add(loanViewD.getV_debtor_id());
                }

                for (Long debtorId: debtorIds)
                {
                    LoanReportData debtor = childB.addChild();
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



                    Set<LoanView> loanViewsD =  new HashSet<LoanView>();

                    loanViewsD.addAll(loanViewService.findByParameter(parameterD));

                    debtor.setLoanViews(loanViewsD);

                    Set<Long> loanIds = new HashSet<Long>();

                    for (LoanView loanViewL:debtor.getLoanViews())
                    {
                        loanIds.add(loanViewL.getV_loan_id());
                    }

                    for (Long loanId: loanIds)
                    {

                        LoanView lv = null;

                        for (LoanView loanViewL:allLoanViews)
                        {
                            if(loanViewL.getV_loan_id()==loanId)
                            {
                                lv=loanViewL;
                            }
                        }


                        LoanReportData loan = debtor.addChild();

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

                        parameterL.put("region",regionIds);
                        parameterL.put("district",districtIds);
                        parameterL.put("debtor",debtorDIds);
                        parameterL.put("loan",loanLIds);
                        parameterL.putAll(parameterD);


                        Set<LoanView> loanViewsL =  new HashSet<LoanView>();

                        loanViewsD.addAll(loanViewService.findByParameter(parameterL));

                        debtor.setLoanViews(loanViewsL);

                    }
                }
            }
        }

        return reportData;
    }
}
