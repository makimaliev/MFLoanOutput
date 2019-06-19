package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.manage.model.collection.PhaseType;
import kg.gov.mf.loan.manage.model.loan.Payment;
import kg.gov.mf.loan.manage.model.orderterm.CurrencyRate;
import kg.gov.mf.loan.manage.service.collection.PhaseTypeService;
import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.CollectionPhaseViewService;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import kg.gov.mf.loan.output.report.service.PaymentViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.*;


public class CollectionPhaseSpecialReportDataManager {
	
	/**
     * Report GENERATION TOOL
     */

    @Autowired
    LoanViewService loanViewService;

    @Autowired
    PhaseTypeService phaseTypeService;

    @Autowired
    CollectionPhaseViewService collectionPhaseViewService;

    @Autowired
    PaymentViewService paymentViewService;


    @Autowired
    EntityManager entityManager;


    Map<Long,PhaseType> phaseTypeMap = new HashMap<Long,PhaseType>();

    Set<String> paymentPeriod = new LinkedHashSet<>();

    Set<PaymentView> phasePayments = new HashSet<>();

    Set<Long> loanIds = new HashSet<>();

    Set<Long> phaseIds = new HashSet<>();

    Map<Long, LinkedList<MonthlyData>> paymentsInPeriod = new LinkedHashMap<>();

    public CollectionPhaseReportData getReportDataGrouped(CollectionPhaseReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        Set<CollectionPhaseView> collectionPhaseViews =  new HashSet<CollectionPhaseView>();

        ReportTool reportTool = new ReportTool();

        Date onDate = reportTool.getOnDate(reportTemplate);

        for (PhaseType phaseType:this.phaseTypeService.list()
                )
        {
            phaseTypeMap.put(phaseType.getId(),phaseType);
        }



        LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<>();

        parameterS.putAll(reportTool.getParametersByTemplate(reportTemplate));

        reportData.getCollectionPhaseViews().addAll(collectionPhaseViewService.findByParameter(parameterS));

        for (CollectionPhaseView collectionPhaseView: reportData.getCollectionPhaseViews())
        {
            loanIds.add( collectionPhaseView.getV_loan_id());
            phaseIds.add(collectionPhaseView.getV_cph_id());
        }


        try {

            String idList = phaseIds.toString().replace("[", "").replace("]", "");

            String baseQuery = "select l.collectionPhaseId, " +
                    "sum(p.totalAmount), " +
                    "year(p.paymentDate) as year " +

                    "from payment p, loanCollectionPhase l, collectionPhase ph\n" +
                    "where p.loanId = l.loanId  and p.record_status = 1 and \n" +
                    "      ph.id = l.collectionPhaseId and\n" +
                    "      ph.id in (" + idList + ") and\n" +
                    "      p.paymentDate > ifnull(ph.paymentFromDate, ph.startDate) AND\n" +
                    "      p.paymentDate < ifnull(ph.closeDate, current_date)\n" +
                    "group by year, l.collectionPhaseId \n" +
                    "order by year ";

            Query query = entityManager.createNativeQuery(baseQuery);

            List<Object[]> res = query.getResultList();

            for (Object[] a : res) {
                MonthlyData monthlyData = new MonthlyData();

                monthlyData.setClassName("Payment");
                monthlyData.setName("Погашение в период");
                monthlyData.setAmount((double) a[1]);
                monthlyData.setObject_id(Long.valueOf(String.valueOf(a[0])));
                monthlyData.setYear(Long.valueOf(String.valueOf(a[2])));
                monthlyData.setMonth((long) 0);

                LinkedList<MonthlyData> tempList = null;

                if(paymentsInPeriod.get(Long.valueOf(String.valueOf(a[0])))==null)
                {
                    tempList = new LinkedList<>();
                }
                else
                {
                    tempList = paymentsInPeriod.get(Long.valueOf(String.valueOf(a[0])));
                }

                if(tempList!=null)
                tempList.add(monthlyData);

                paymentsInPeriod.put(Long.valueOf(String.valueOf(a[0])), tempList);


            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }




        return groupifyData(reportData,reportTemplate, reportTool);
    }

    public CollectionPhaseReportData groupifyData(CollectionPhaseReportData reportData,ReportTemplate reportTemplate, ReportTool reportTool)
    {

        reportTool.initReference();

        int minPeriod = reportTemplate.getOnDate().getYear()+1900;

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

        CollectionPhaseReportData childA = null;
        CollectionPhaseReportData childB = null;
        CollectionPhaseReportData childC = null;
        CollectionPhaseReportData childD = null;
        CollectionPhaseReportData childE = null;
        CollectionPhaseReportData childF = null;

        for (CollectionPhaseView collectionPhaseView:reportData.getCollectionPhaseViews())
        {

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType1(),collectionPhaseView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType1(),collectionPhaseView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(reportTemplate.getGroupType1(),collectionPhaseView);
                currentgroupBid=-1;
                currentgroupCid=-1;
                currentgroupDid=-1;

            }


            if(reportTool.getIdByGroupType(reportTemplate.getGroupType2(),collectionPhaseView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType2(),collectionPhaseView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(reportTemplate.getGroupType2(),collectionPhaseView);
                currentgroupCid=-1;
                currentgroupDid=-1;

            }




            if(reportTool.getIdByGroupType(reportTemplate.getGroupType3(),collectionPhaseView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType3(),collectionPhaseView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                currentgroupCid=reportTool.getIdByGroupType(reportTemplate.getGroupType3(),collectionPhaseView);
                currentgroupDid=-1;

            }



            if(reportTool.getIdByGroupType(reportTemplate.getGroupType4(),collectionPhaseView)!=currentgroupDid)
            {
                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType4(),collectionPhaseView));
                childD.setLevel((short)4);

                childD.setLevel((short)4);

                childD.setDetailsCount(1);
                childC.setDetailsCount(childC.getDetailsCount()+1);
                childA.setDetailsCount(childA.getDetailsCount()+1);
                childB.setDetailsCount(childB.getDetailsCount()+1);
                reportData.setDetailsCount(reportData.getDetailsCount()+1);





                if(collectionPhaseView.getV_cp_startDate()!=null)
                    childD.setCollection_phase_start_date(new java.sql.Date(collectionPhaseView.getV_cp_startDate().getTime()));

                currentgroupDid=reportTool.getIdByGroupType(reportTemplate.getGroupType4(),collectionPhaseView);
            }


            if(reportTool.getIdByGroupType(reportTemplate.getGroupType5(),collectionPhaseView)!=currentgroupEid)
            {
                childE = childD.addChild();
                childE.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType5(),collectionPhaseView));
                childE.setLevel((short)5);

                if(collectionPhaseView.getV_cph_startDate()!=null)
                    childE.setCollection_phase_start_date(new java.sql.Date(collectionPhaseView.getV_cph_startDate().getTime()));

                if(collectionPhaseView.getV_cph_closeDate()!=null)
                    childE.setCollection_phase_close_date(new java.sql.Date(collectionPhaseView.getV_cph_closeDate().getTime()));

                reportData.setPhaseCount(reportData.getPhaseCount()+1);
                childA.setPhaseCount(childA.getPhaseCount()+1);
                childB.setPhaseCount(childB.getPhaseCount()+1);
                childC.setPhaseCount(childC.getPhaseCount()+1);
                childD.setPhaseCount(childD.getPhaseCount()+1);

                if(collectionPhaseView.getV_cph_phaseStatusId()>1)
                {
                    reportData.setResultCount(reportData.getResultCount()+1);
                    childA.setResultCount(childA.getResultCount()+1);
                    childB.setResultCount(childB.getResultCount()+1);
                    childC.setResultCount(childC.getResultCount()+1);
                    childD.setResultCount(childD.getResultCount()+1);
                }





                childE.setCollection_phase_type_id(collectionPhaseView.getV_cph_phaseTypeId());

                childE.setCollection_phase_status_id(collectionPhaseView.getV_cph_phaseStatusId());

                childE.setCollection_phase_group_id(collectionPhaseView.getV_cph_group_id());
                childE.setCollection_phase_index_id(collectionPhaseView.getV_cph_index_id());
                childE.setCollection_phase_sub_index_id(collectionPhaseView.getV_cph_sub_index_id());

                childE.setCollection_phase_description(collectionPhaseView.getV_cph_description());





                childE.setCollection_phase_type_name(phaseTypeMap.get(collectionPhaseView.getV_cph_phaseTypeId()).getName());

                if(collectionPhaseView.getV_cph_start_total_amount() != null)
                    if(collectionPhaseView.getV_cph_start_total_amount()>0)
                    {
                        reportData.setCollection_phase_start_total_amount(reportData.getCollection_phase_start_total_amount()+collectionPhaseView.getV_cph_start_total_amount());
                        childA.setCollection_phase_start_total_amount(childA.getCollection_phase_start_total_amount()+collectionPhaseView.getV_cph_start_total_amount());
                        childB.setCollection_phase_start_total_amount(childB.getCollection_phase_start_total_amount()+collectionPhaseView.getV_cph_start_total_amount());
                        childC.setCollection_phase_start_total_amount(childC.getCollection_phase_start_total_amount()+collectionPhaseView.getV_cph_start_total_amount());
                        childD.setCollection_phase_start_total_amount(childD.getCollection_phase_start_total_amount()+collectionPhaseView.getV_cph_start_total_amount());
                        childE.setCollection_phase_start_total_amount(collectionPhaseView.getV_cph_start_total_amount());

                    }

                if(collectionPhaseView.getV_cph_close_total_amount() != null)
                    if(collectionPhaseView.getV_cph_close_total_amount()>0)
                    {
                        reportData.setCollection_phase_close_total_amount(reportData.getCollection_phase_close_total_amount()+collectionPhaseView.getV_cph_close_total_amount());
                        childA.setCollection_phase_close_total_amount(childA.getCollection_phase_close_total_amount()+collectionPhaseView.getV_cph_close_total_amount());
                        childB.setCollection_phase_close_total_amount(childB.getCollection_phase_close_total_amount()+collectionPhaseView.getV_cph_close_total_amount());
                        childC.setCollection_phase_close_total_amount(childC.getCollection_phase_close_total_amount()+collectionPhaseView.getV_cph_close_total_amount());
                        childD.setCollection_phase_close_total_amount(childD.getCollection_phase_close_total_amount()+collectionPhaseView.getV_cph_close_total_amount());
                        childE.setCollection_phase_close_total_amount(collectionPhaseView.getV_cph_close_total_amount());
                    }

                if(collectionPhaseView.getV_cph_paid_total_amount() != null)
                    if(collectionPhaseView.getV_cph_paid_total_amount()>0)
                    {
                        reportData.setCollection_phase_paid_total_amount(reportData.getCollection_phase_paid_total_amount()+collectionPhaseView.getV_cph_paid_total_amount());
                        childA.setCollection_phase_paid_total_amount(childA.getCollection_phase_paid_total_amount()+collectionPhaseView.getV_cph_paid_total_amount());
                        childB.setCollection_phase_paid_total_amount(childB.getCollection_phase_paid_total_amount()+collectionPhaseView.getV_cph_paid_total_amount());
                        childC.setCollection_phase_paid_total_amount(childC.getCollection_phase_paid_total_amount()+collectionPhaseView.getV_cph_paid_total_amount());
                        childD.setCollection_phase_paid_total_amount(childD.getCollection_phase_paid_total_amount()+collectionPhaseView.getV_cph_paid_total_amount());
                        childE.setCollection_phase_paid_total_amount(collectionPhaseView.getV_cph_paid_total_amount());
                    }

                double outstanding_total_amount = 0;

                if(childE.getCollection_phase_start_total_amount()>0)
                {
                    outstanding_total_amount=childE.getCollection_phase_start_total_amount();

                    if(childE.getCollection_phase_paid_total_amount()>0)
                    {
                        outstanding_total_amount=outstanding_total_amount-childE.getCollection_phase_paid_total_amount();

                        LinkedList<MonthlyData> listofMonthlyData = paymentsInPeriod.get(collectionPhaseView.getV_cph_id());

                        if(listofMonthlyData!=null)
                        for (MonthlyData monthlyData:listofMonthlyData)
                        {
                            if(monthlyData.getYear()<minPeriod)
                                minPeriod = monthlyData.getYear().intValue();

                            this.addMonthlyData(childE, monthlyData, false);
                        }

                        LinkedHashMap<Long,LinkedList<MonthlyData>> phasePaymentsByPeriod = new LinkedHashMap<>();

                        phasePaymentsByPeriod.put((long)1, listofMonthlyData);

                        childE.setPhasePaymentsByPeriod(phasePaymentsByPeriod);

                        for (ContentParameter contentParameter: reportTemplate.getContentParameters())
                        {
                            if(contentParameter.getConstantText().contains("dynamic"))
                            {
                                contentParameter.setConstantInt(minPeriod);
                            }

                        }

                    }

                    if(outstanding_total_amount<0) outstanding_total_amount = 0;

                    reportData.setCollection_phase_outstanding_total_amount(reportData.getCollection_phase_outstanding_total_amount()+outstanding_total_amount);
                    childA.setCollection_phase_outstanding_total_amount(childA.getCollection_phase_outstanding_total_amount()+outstanding_total_amount);
                    childB.setCollection_phase_outstanding_total_amount(childB.getCollection_phase_outstanding_total_amount()+outstanding_total_amount);
                    childC.setCollection_phase_outstanding_total_amount(childC.getCollection_phase_outstanding_total_amount()+outstanding_total_amount);
                    childD.setCollection_phase_outstanding_total_amount(childD.getCollection_phase_outstanding_total_amount()+outstanding_total_amount);
                    childE.setCollection_phase_outstanding_total_amount(outstanding_total_amount);

                }
                else
                    {
                        childE.setCollection_phase_outstanding_total_amount(outstanding_total_amount);
                        childE.setCollection_phase_paid_total_amount(0);
                    }

                childE.setCollection_procedure_status_id(collectionPhaseView.getV_cp_procedureStatusId());

                currentgroupEid=reportTool.getIdByGroupType(reportTemplate.getGroupType5(),collectionPhaseView);
            }

        }


        return reportData;
    }


    public void addMonthlyData(CollectionPhaseReportData reportData, MonthlyData monthlyData, boolean add)
    {

        if(add)
        {
            LinkedList<MonthlyData> listofMonthlyData = new LinkedList<>();

            if(reportData.getPhasePaymentsByPeriod().get((long)1)!=null)
            {
                listofMonthlyData = reportData.getPhasePaymentsByPeriod().get((long)1);

                LinkedList<MonthlyData> newListofMonthlyData = new LinkedList<>();

                newListofMonthlyData.addAll(listofMonthlyData);

//                listofMonthlyData.clear();

                boolean dataFound = false;

                for (MonthlyData monthlyDatainLoop: newListofMonthlyData)
                {
                    if(monthlyData.getYear().equals(monthlyDatainLoop.getYear()))
                    {
                        MonthlyData tempMonthlyData = new MonthlyData();

                        tempMonthlyData.setMonth(monthlyDatainLoop.getMonth());
                        tempMonthlyData.setYear(monthlyDatainLoop.getYear());
                        tempMonthlyData.setObject_id(monthlyDatainLoop.getObject_id());
                        tempMonthlyData.setName(monthlyDatainLoop.getName());
                        tempMonthlyData.setClassName(monthlyData.getClassName());
                        tempMonthlyData.setAmount(monthlyDatainLoop.getAmount()+monthlyData.getAmount());

                        listofMonthlyData.remove(monthlyDatainLoop);
                        listofMonthlyData.add(tempMonthlyData);

                        dataFound = true;
                    }
                }

                if(!dataFound) listofMonthlyData.add(monthlyData);
            }
            else
            {
                listofMonthlyData.add(monthlyData);
            }






            LinkedHashMap<Long,LinkedList<MonthlyData>> phasePaymentsByPeriod = new LinkedHashMap<>();

            phasePaymentsByPeriod.put((long)1, orderPaymentsINPeriod(listofMonthlyData));

            reportData.setPhasePaymentsByPeriod(phasePaymentsByPeriod);

        }

        if(reportData.getParent()!=null)
        {
            addMonthlyData(reportData.getParent(), monthlyData, true);
        }

    }

    public LinkedList<MonthlyData> orderPaymentsINPeriod (LinkedList<MonthlyData> paymentsInPeriod)
    {
        LinkedList<MonthlyData> orderedPayments = new LinkedList<>();

        ArrayList<MonthlyData> listOfPayment = new ArrayList<>(paymentsInPeriod);

        Collections.sort(listOfPayment);

        orderedPayments.addAll(listOfPayment);

        return orderedPayments;

    }


}
