package kg.gov.mf.loan.output.report.utils;


import kg.gov.mf.loan.manage.model.collateral.CollateralItem;
import kg.gov.mf.loan.manage.model.collection.CollectionPhase;
import kg.gov.mf.loan.manage.model.collection.CollectionProcedure;
import kg.gov.mf.loan.manage.model.collection.PhaseDetails;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.loan.Payment;
import kg.gov.mf.loan.manage.service.collateral.CollateralItemDetailsService;
import kg.gov.mf.loan.manage.service.collateral.CollateralItemService;
import kg.gov.mf.loan.manage.service.collection.CollectionPhaseService;
import kg.gov.mf.loan.manage.service.collection.CollectionProcedureService;
import kg.gov.mf.loan.manage.service.collection.PhaseDetailsService;
import kg.gov.mf.loan.manage.service.loan.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


public class CollectionPhaseTool
{

    @Autowired
    EntityManager entityManager;

    @Autowired
    CollateralItemService itemService;

    @Autowired
    CollateralItemDetailsService detailsService;

    @Autowired
    CollectionPhaseService collectionPhaseService;

    @Autowired
    PhaseDetailsService phaseDetailsService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    CollectionProcedureService collectionProcedureService;

    Set<String> errorList = new LinkedHashSet<>();

    public Set<String> doStartPhaseDetailsUpdate()
    {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        try
        {

//            List<LoanDetailedSummary> result = new ArrayList<>();
//
//
//            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//            Date startDate = new Date();
//            System.out.println(" START == "+dateFormat.format(startDate)); //2016/11/16 12:08:43
//
//            String baseQuery="select * from loanDetailedSummary";
//
//            Query query=entityManager.createNativeQuery(baseQuery,LoanDetailedSummary.class);
//            result=query.getResultList();
//
//            Date finishDate = new Date();
//            System.out.println(" FINISH == "+String.valueOf(finishDate.getTime()-startDate.getTime()));

            List<CollectionPhase> items = new ArrayList<>();

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date startDate = new Date();
            System.out.println(" START == "+dateFormat.format(startDate)); //2016/11/16 12:08:43

            String baseQuery="select * from mfloan.collectionPhase where id = 36881 order by id";

            Query query=entityManager.createNativeQuery(baseQuery,CollectionPhase.class);
            items=query.getResultList();

            for (CollectionPhase phaseInLoop: items)
            {
                CollectionPhase phase = this.collectionPhaseService.getById(phaseInLoop.getId());


                Date startedDate=phase.getStartDate();
                Date closeedDate=new Date();
                CollectionProcedure procedure=collectionProcedureService.getById(phase.getCollectionProcedure().getId());
                if(phase.getCloseDate()!=null){
                    closeedDate=phase.getCloseDate();
                }
                else if(procedure.getCloseDate()!=null){
                    closeedDate=phase.getCollectionProcedure().getCloseDate();
                }

                String text = "";

                text+=" phase id = "+String.valueOf(phase.getId());

                Double sumFeeP=0.0;
                Double sumPrincipalP=0.0;
                Double sumInterestP=0.0;
                Double sumPenaltyP=0.0;
                Double sumTotalP=0.0;

                for (PhaseDetails details: phase.getPhaseDetails())
                {
                    PhaseDetails phaseDetails=details;

                    //                    PhaseDetails phaseDetails=phaseDetailsService.getById(details.getId());

                    List<Payment> payments=paymentService.getFromToDate(phaseDetails.getLoan_id(),startedDate,closeedDate);

                    Double sumFeeD=0.0;
                    Double sumPrincipalD=0.0;
                    Double sumInterestD=0.0;
                    Double sumPenaltyD=0.0;
                    Double sumTotalD=0.0;

                    for(Payment payment:payments)
                    {
                        sumFeeD+=payment.getFee();
                        sumPenaltyD+=payment.getPenalty();
                        sumPrincipalD+=payment.getPrincipal();
                        sumInterestD+=payment.getInterest();
                        sumTotalD+=payment.getTotalAmount();
                    }

                    phaseDetails.setPaidFee(sumFeeD);
                    phaseDetails.setPaidPrincipal(sumPrincipalD);
                    phaseDetails.setPaidPenalty(sumPenaltyD);
                    phaseDetails.setPaidInterest(sumInterestD);
                    phaseDetails.setPaidTotalAmount(sumTotalD);

                    phaseDetailsService.update(phaseDetails);

                    sumFeeP=sumFeeP+phaseDetails.getPaidFee();
                    sumPenaltyP=sumPenaltyP+phaseDetails.getPaidPenalty();
                    sumPrincipalP=sumPrincipalP+phaseDetails.getPaidPrincipal();
                    sumInterestP=sumInterestP+phaseDetails.getPaidInterest();
                    sumTotalP=sumTotalP+phaseDetails.getPaidTotalAmount();
                }


                phase.setPaid(sumFeeP+sumPenaltyP+sumInterestP+sumPrincipalP);

                if(phase.getStart_amount()!=null)
                if(phase.getStart_amount()<=phase.getPaid())
                {
                    phase.setPaid(phase.getStart_amount());
                }

                text+= " payments == "+String.valueOf(phase.getPaid());

                collectionPhaseService.update(phase);

                errorList.add(text);
            }

            errorList.add(" items count == "+items.size());
            Date finishDate = new Date();
            errorList.add(" FINISH == "+String.valueOf(Math.round((finishDate.getTime()-startDate.getTime())/(1000))));

        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }


        for (String text: errorList)
        {
            System.out.println(text);
        }

        return errorList;
    }




}

