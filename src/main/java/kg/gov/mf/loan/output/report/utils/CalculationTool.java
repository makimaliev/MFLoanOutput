package kg.gov.mf.loan.output.report.utils;


import kg.gov.mf.loan.manage.model.loan.*;
import kg.gov.mf.loan.manage.model.process.LoanDetailedSummary;
import kg.gov.mf.loan.manage.model.process.LoanSummary;
import kg.gov.mf.loan.manage.repository.process.LoanDetailedSummaryRepository;
import kg.gov.mf.loan.manage.service.loan.CreditTermService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.process.LoanDetailedSummaryService;
import kg.gov.mf.loan.manage.util.DateUtils;
import kg.gov.mf.loan.output.report.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


public class CalculationTool
{

    @Autowired
    EntityManager entityManager;

    @Autowired
    LoanDetailedSummaryService loanDetailedSummaryService;

    @Autowired
    CreditTermService creditTermService;

    @Autowired
    LoanService loanService;

    public Date fromDate;

    public Date toDate;

    public Long rate_type;

    public Long daysInYearMethod;

    public Long daysInPeriodMethod;

    public Long loanID;

    public Long daysInPeriod;

    public Double interestCalculatedInPeriod;

    public Boolean afterSrok;

    public Double penalty_limit;

    public Double penalty_paid_after_spec_date;

    public Double penalty_limit_amount;

    public Double grace_days_on_principal;
    public Double grace_days_on_interest;

    public Double judgement_principal;
    public Double judgement_interest;

    public HashMap<Long, Set<Loan>> childLoanMap = new HashMap<>();

    public HashMap<Long, CreditTerm> termMap = new HashMap<>();








    public void doCalculate()
    {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        try
        {
            SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

            List<LoanDetailedSummary> result = new ArrayList<>();


            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            java.util.Date startDate = new Date();
            System.out.println(" START == "+dateFormat.format(startDate)); //2016/11/16 12:08:43

            String baseQuery="select * from loanDetailedSummary";

            Query query=entityManager.createNativeQuery(baseQuery,LoanDetailedSummary.class);
            result=query.getResultList();

//            for (LoanDetailedSummary lds: result)
//            {
//                System.out.println(lds.getOnDate()+ " "+ lds.getTotalDisbursement());
//
//            }

            java.util.Date finishDate = new Date();
            System.out.println(" FINISH == "+String.valueOf(finishDate.getTime()-startDate.getTime()));
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }


    public LoanSummary getLoanSummaryCaluculatedByLoanIdAndOnDate(LoanView lv, Long loan_id,Date onDate, LoanDetailedSummary loanDetailedSummary)
    {


        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        LoanSummary returnLoanSummary = new LoanSummary();

        try {

            loanID = loan_id;
            toDate = onDate;

            if(loanID==3680)
            {
                System.out.println("asdfasdfa");
            }


            LoanDetailedSummary lds = null;

            if(loanDetailedSummary==null)
            {
                lds = loanDetailedSummaryService.getLastSummaryByLoanIdAndLTEOnDate(loanID,onDate);
            }
            else
            {
                lds = loanDetailedSummary;
            }



            returnLoanSummary.setLoanAmount(lv.getV_loan_amount());
            returnLoanSummary.setOnDate(onDate);
            returnLoanSummary.setCreateDate(lv.getV_last_date());

            if(lds==null)
            {
                List<Loan> listOfChildren = getChildrenByLoanId(loanID);


                if(listOfChildren.size()==0)
                {
                    LoanSummary convertedLoanSummary = new LoanSummary();

                    convertedLoanSummary.setOnDate(onDate);
                    convertedLoanSummary.setLoanAmount((double)0);
                    convertedLoanSummary.setTotalPaid((double)0);
                    convertedLoanSummary.setTotalPaidKGS((double)0);
                    convertedLoanSummary.setTotalDisbursed((double)0);
                    convertedLoanSummary.setOutstadingPrincipal((double)0);
                    convertedLoanSummary.setOutstadingInterest((double)0);
                    convertedLoanSummary.setOutstadingPenalty((double)0);
                    convertedLoanSummary.setOverduePrincipal((double)0);
                    convertedLoanSummary.setOverdueInterest((double)0);
                    convertedLoanSummary.setOverduePenalty((double)0);
                    convertedLoanSummary.setTotalOutstanding((double)0);
                    convertedLoanSummary.setTotalDisbursed((double)0);
                    convertedLoanSummary.setTotalOverdue((double)0);
                    convertedLoanSummary.setTotalPaid((double)0);
                    convertedLoanSummary.setTotalPaidKGS((double)0);

                    NormalLoan loan = new NormalLoan();
                    loan.setId(1L);
                    convertedLoanSummary.setLoan(loan);

                    return convertedLoanSummary;
                }

                for (Loan loanChildren: listOfChildren)
                {
                    if(loanChildren.getLoanState().getId()!=3)
                    {
                        CalculationTool calculationToolChildren = new CalculationTool();

                        LoanSummary childrenLoanSummary = calculationToolChildren.getLoanSummaryCaluculatedByLoanIdAndOnDate(lv, loanChildren.getId(), onDate, loanDetailedSummary);

                        returnLoanSummary.setId(childrenLoanSummary.getId());



                        Long classOfLoan = 2L;

                        if(lv.getV_loan_class_id()!=null)
                        {
                            classOfLoan = lv.getV_loan_class_id().longValue();
                        }
                        else
                        {
                            classOfLoan = calculationToolChildren.getClassOfLoan(loanID);
                        }



                        if(classOfLoan==2)
                        {
                            double principalPayment = 0;
//                            for (PaymentSchedule schedule : loanChildren.getPaymentSchedules())
//                            {
//                                if(schedule.getPrincipalPayment()>0) principalPayment+=schedule.getPrincipalPayment();
//                            }

                            if(childrenLoanSummary.getTotalDisbursed()!=0 && loanChildren.getAmount()==0 && childrenLoanSummary.getTotalFeePaid()==0)
                            {
                                childrenLoanSummary.setTotalDisbursed(0.0);
                                childrenLoanSummary.setTotalOutstanding(childrenLoanSummary.getTotalOutstanding()-childrenLoanSummary.getOutstadingPrincipal());
                                childrenLoanSummary.setOutstadingPrincipal(0.0);
                            }
                        }

                        if(returnLoanSummary.getTotalDisbursed()==null)
                        {
                            returnLoanSummary.setTotalDisbursed(childrenLoanSummary.getTotalDisbursed());

                            returnLoanSummary.setTotalPaid(childrenLoanSummary.getTotalPaid());
                            returnLoanSummary.setTotalPaidKGS(childrenLoanSummary.getTotalPaidKGS());

                            returnLoanSummary.setTotalOutstanding(childrenLoanSummary.getTotalOutstanding());
                            returnLoanSummary.setOutstadingPrincipal(childrenLoanSummary.getOutstadingPrincipal());
                            returnLoanSummary.setOutstadingInterest(childrenLoanSummary.getOutstadingInterest());
                            returnLoanSummary.setOutstadingPenalty(childrenLoanSummary.getOutstadingPenalty());

                            returnLoanSummary.setTotalOutstanding(returnLoanSummary.getOutstadingPrincipal()+returnLoanSummary.getOutstadingInterest()+returnLoanSummary.getOutstadingPenalty());

                            returnLoanSummary.setTotalOverdue(childrenLoanSummary.getTotalOverdue());
                            returnLoanSummary.setOverduePrincipal(childrenLoanSummary.getOverduePrincipal());
                            returnLoanSummary.setOverdueInterest(childrenLoanSummary.getOverdueInterest());
                            returnLoanSummary.setOverduePenalty(childrenLoanSummary.getOverduePenalty());


                            double total_outstanding= 0;

                            if(returnLoanSummary.getOutstadingPrincipal()>0)
                                total_outstanding+=returnLoanSummary.getOutstadingPrincipal();

                            if(returnLoanSummary.getOutstadingInterest()>0)
                                total_outstanding+=returnLoanSummary.getOutstadingInterest();

                            if(returnLoanSummary.getOutstadingPenalty()>0)
                                total_outstanding+=returnLoanSummary.getOutstadingPenalty();


                            double total_overdue = 0;

                            if(returnLoanSummary.getOverduePrincipal()>0)
                                total_overdue+=returnLoanSummary.getOverduePrincipal();

                            if(returnLoanSummary.getOverdueInterest()>0)
                                total_overdue+=returnLoanSummary.getOverdueInterest();

                            if(returnLoanSummary.getOverduePenalty()>0)
                                total_overdue+=returnLoanSummary.getOverduePenalty();

                            if(total_outstanding<0) total_outstanding = 0;
                            if(total_overdue < 0) total_overdue = 0;


                            returnLoanSummary.setTotalOutstanding(total_outstanding);
                            returnLoanSummary.setTotalOverdue(total_overdue);


                            returnLoanSummary.setTotalOverdue(returnLoanSummary.getOverduePrincipal()+returnLoanSummary.getOverdueInterest()+returnLoanSummary.getOverduePenalty());


                        }
                        else
                        {
                            returnLoanSummary.setTotalDisbursed(returnLoanSummary.getTotalDisbursed()+childrenLoanSummary.getTotalDisbursed());

                            returnLoanSummary.setTotalPaid(returnLoanSummary.getTotalPaid()+childrenLoanSummary.getTotalPaid());
                            returnLoanSummary.setTotalPaidKGS(returnLoanSummary.getTotalPaidKGS()+childrenLoanSummary.getTotalPaidKGS());

                            returnLoanSummary.setTotalOutstanding(returnLoanSummary.getTotalOutstanding()+childrenLoanSummary.getTotalOutstanding());
                            returnLoanSummary.setOutstadingPrincipal(returnLoanSummary.getOutstadingPrincipal()+childrenLoanSummary.getOutstadingPrincipal());
                            returnLoanSummary.setOutstadingInterest(returnLoanSummary.getOutstadingInterest()+childrenLoanSummary.getOutstadingInterest());
                            returnLoanSummary.setOutstadingPenalty(returnLoanSummary.getOutstadingPenalty()+childrenLoanSummary.getOutstadingPenalty());

                            returnLoanSummary.setTotalOverdue(returnLoanSummary.getTotalOverdue()+childrenLoanSummary.getTotalOverdue());
                            returnLoanSummary.setOverduePrincipal(returnLoanSummary.getOverduePrincipal()+childrenLoanSummary.getOverduePrincipal());
                            returnLoanSummary.setOverdueInterest(returnLoanSummary.getOverdueInterest()+childrenLoanSummary.getOverdueInterest());
                            returnLoanSummary.setOverduePenalty(returnLoanSummary.getOverduePenalty()+childrenLoanSummary.getOverduePenalty());

                            returnLoanSummary.setTotalOutstanding(returnLoanSummary.getOutstadingPrincipal()+returnLoanSummary.getOutstadingInterest()+returnLoanSummary.getOutstadingPenalty());
                            returnLoanSummary.setTotalOverdue(returnLoanSummary.getOverduePrincipal()+returnLoanSummary.getOverdueInterest()+returnLoanSummary.getOverduePenalty());

                            double total_outstanding= 0;

                            if(returnLoanSummary.getOutstadingPrincipal()>0)
                                total_outstanding+=returnLoanSummary.getOutstadingPrincipal();

                            if(returnLoanSummary.getOutstadingInterest()>0)
                                total_outstanding+=returnLoanSummary.getOutstadingInterest();

                            if(returnLoanSummary.getOutstadingPenalty()>0)
                                total_outstanding+=returnLoanSummary.getOutstadingPenalty();


                            double total_overdue = 0;

                            if(returnLoanSummary.getOverduePrincipal()>0)
                                total_overdue+=returnLoanSummary.getOverduePrincipal();

                            if(returnLoanSummary.getOverdueInterest()>0)
                                total_overdue+=returnLoanSummary.getOverdueInterest();

                            if(returnLoanSummary.getOverduePenalty()>0)
                                total_overdue+=returnLoanSummary.getOverduePenalty();

                            if(total_outstanding<0) total_outstanding = 0;
                            if(total_overdue < 0) total_overdue = 0;


                            returnLoanSummary.setTotalOutstanding(total_outstanding);
                            returnLoanSummary.setTotalOverdue(total_overdue);


                        }


                    }


                }

                return returnLoanSummary;


            }

            if(lds!=null)
            returnLoanSummary.setId(lds.getId());

            fromDate = lds.getOnDate();

            daysInPeriod = Long.valueOf(DateUtils.getDifferenceDays(onDate, lds.getOnDate()));

            returnLoanSummary.setOutstadingPrincipal(lds.getPrincipalOutstanding());
            returnLoanSummary.setOverduePrincipal(lds.getPrincipalOverdue());


            returnLoanSummary.setOutstadingInterest(lds.getInterestOutstanding());
            returnLoanSummary.setOverdueInterest(lds.getInterestOverdue());


            returnLoanSummary.setOutstadingPenalty(lds.getPenaltyOutstanding());
            returnLoanSummary.setOverduePenalty(lds.getPenaltyOverdue());

            returnLoanSummary.setTotalFeePaid(lds.getTotalPrincipalPayment());

            if(lds.getPrincipalOutstanding()>1 || lds.getPrincipalOverdue()>1 || lds.getInterestOverdue()>1 || lds.getPenaltyOutstanding()>1)
            {
                CreditTerm term;

                if(termMap.get(loan_id)==null)
                {
                    term = creditTermService.getRecentTermByLoanIdAndOnDate(loanID, lds.getOnDate());
                }
                else
                {
                    term = termMap.get(loan_id);
                }


                daysInPeriod = getCalculatedDays(lds.getOnDate(),onDate,term.getDaysInMonthMethod().getId());

                daysInYearMethod = term.getDaysInYearMethod().getId();
                daysInPeriodMethod = term.getDaysInMonthMethod().getId();

                rate_type = term.getFloatingRateType().getId();

                penalty_limit = term.getPenaltyLimitPercent()/100;

                penalty_limit_amount = lds.getTotalDisbursement()*penalty_limit-lds.getTotalInterestAccruedOnInterestPayment();

                afterSrok =false;
                if(lds.getVersion()==1)
                {
                    afterSrok=true;
                }

                interestCalculatedInPeriod=0.0;

                if(lds.getPrincipalOutstanding()>0)
                {
                    if(term.getInterestRateValue()>0 || term.getFloatingRateType().getId()!=2)
                    {
                        interestCalculatedInPeriod =  calculateInterest(lds.getPrincipalOutstanding(), daysInPeriod, term);

                        returnLoanSummary.setOutstadingInterest(lds.getInterestOutstanding() + interestCalculatedInPeriod);

                        if(afterSrok)
                        returnLoanSummary.setOverdueInterest(lds.getInterestOverdue() + interestCalculatedInPeriod);
                    }

                }

                double penaltyCalculatedInPeriod = 0;

                if(lds.getPrincipalOverdue()>0)
                {
                    if(term.getPenaltyOnPrincipleOverdueRateValue()>0 || term.getPenaltyOnPrincipleOverdueRateType().getId()!=2)
                    {
                        penaltyCalculatedInPeriod+=calculatePenalty(lds.getPrincipalOverdue(), term, (short)1);
                    }
                }

                if(lds.getInterestOverdue()>0)
                {
                    if(term.getPenaltyOnInterestOverdueRateValue()>0 || term.getPenaltyOnInterestOverdueRateType().getId()!=2)
                    {
                        penaltyCalculatedInPeriod+=calculatePenalty(lds.getInterestOverdue(), term, (short)2);
                    }
                }


                returnLoanSummary.setOutstadingPenalty(lds.getPenaltyOutstanding() + penaltyCalculatedInPeriod);
                returnLoanSummary.setOverduePenalty(lds.getPenaltyOverdue() + penaltyCalculatedInPeriod);

                if(penalty_limit>0 && (returnLoanSummary.getOutstadingPenalty()>penalty_limit_amount))
                {
                    returnLoanSummary.setOutstadingPenalty(penalty_limit_amount);
                }

                if(penalty_limit>0 && (returnLoanSummary.getOverduePenalty()>penalty_limit_amount))
                {
                    returnLoanSummary.setOverduePenalty(penalty_limit_amount);
                }

            }




            returnLoanSummary.setTotalDisbursed(lds.getTotalDisbursement());




            double total_outstanding= 0;

            if(returnLoanSummary.getOutstadingPrincipal()>0)
                total_outstanding+=returnLoanSummary.getOutstadingPrincipal();

            if(returnLoanSummary.getOutstadingInterest()>0)
                total_outstanding+=returnLoanSummary.getOutstadingInterest();

            if(returnLoanSummary.getOutstadingPenalty()>0)
                total_outstanding+=returnLoanSummary.getOutstadingPenalty();


            double total_overdue = 0;

            if(returnLoanSummary.getOverduePrincipal()>0)
                total_overdue+=returnLoanSummary.getOverduePrincipal();

            if(returnLoanSummary.getOverdueInterest()>0)
                total_overdue+=returnLoanSummary.getOverdueInterest();

            if(returnLoanSummary.getOverduePenalty()>0)
                total_overdue+=returnLoanSummary.getOverduePenalty();

            if(total_outstanding<0) total_outstanding = 0;
            if(total_overdue < 0) total_overdue = 0;


            returnLoanSummary.setTotalOutstanding(total_outstanding);
            returnLoanSummary.setTotalOverdue(total_overdue);
            returnLoanSummary.setTotalPaid(lds.getTotalPrincipalPaid()+lds.getTotalInterestPaid()+lds.getTotalPenaltyPaid());
            returnLoanSummary.setTotalPaidKGS(lds.getTotalPrincipalWriteOff());

            if(lv.getV_loan_close_date()!=null)
            if(lv.getV_loan_close_date().before(returnLoanSummary.getOnDate()))
            {
                returnLoanSummary.setTotalOutstanding((double)0);
                returnLoanSummary.setOutstadingPrincipal((double)0);
                returnLoanSummary.setOutstadingInterest((double)0);
                returnLoanSummary.setOutstadingPenalty((double)0);

                returnLoanSummary.setTotalOverdue((double)0);
                returnLoanSummary.setOverduePrincipal((double)0);
                returnLoanSummary.setOverdueInterest((double)0);
                returnLoanSummary.setOverduePenalty((double)0);
            }



        }
        catch (Exception ex)
        {
            System.out.println(ex+lv.getV_loan_id().toString());
        }






        return  returnLoanSummary;
    }

    private Double calculateInterest(Double principalOutstanding, long daysInPeriod, CreditTerm term)
    {

        double rate = term.getInterestRateValue();

        int daysInYear = getDaysInYearByType((short)0, term);

        double interestCalculated = 0;

        interestCalculated = principalOutstanding*(rate/100)*daysInPeriod/daysInYear;

        if(term.getFloatingRateType().getId()!=2)
        {
            interestCalculated+=calculateLibor(principalOutstanding,term,(short)0);
        }

        return interestCalculated;
    }

    private Double calculateLibor(Double amount, CreditTerm term, short type)
    {

        double calculatedAmount = 0;

        Query q = entityManager.createNativeQuery("SELECT calculateLibor(?1,?2,?3,?4,?5,?6,?7,?8)");

        q.setParameter(1, amount);
        q.setParameter(2, fromDate);
        q.setParameter(3, toDate);
        q.setParameter(4, getRateTypeByType(type, term));
        q.setParameter(5, daysInYearMethod);
        q.setParameter(6, daysInPeriodMethod);
        q.setParameter(7, loanID);
        q.setParameter(8, 1);

        calculatedAmount = (double) q.getSingleResult();

        return calculatedAmount;
    }

    private Double calculatePenalty(Double overdue, CreditTerm term, short type)
    {

        double rate = getRateByType(type, term);

        int daysInYear = getDaysInYearByType(type,term);

        double penaltyCalculated = 0;

        if(overdue>0)
        penaltyCalculated = overdue*(rate/100)*daysInPeriod/daysInYear;

        if(getRateTypeByType(type,term)!=2)
        {
            penaltyCalculated+=calculateLibor(overdue,term, (short)type);
        }

        if(afterSrok && type==2 && interestCalculatedInPeriod>0 && (rate>0 || getRateTypeByType(type,term)!=2))
        {
            penaltyCalculated+= ((interestCalculatedInPeriod*(daysInPeriod-1)/(2*daysInPeriod)))*(rate/100)*daysInPeriod/daysInYear;

            if(getRateTypeByType(type,term)!=2)
            {
                penaltyCalculated+=calculateLibor((interestCalculatedInPeriod*(daysInPeriod-1)/(2*daysInPeriod)),term, (short)type);
            }

        }

        return penaltyCalculated;
    }




    private double getRateByType(short type, CreditTerm term)
    {
        switch ( type)
        {
            case 0: return term.getInterestRateValue();
            case 1: return term.getPenaltyOnPrincipleOverdueRateValue();
            case 2: return term.getPenaltyOnInterestOverdueRateValue();
        }
        return 0;
    }

    private long getRateTypeByType(short type, CreditTerm term)
    {
        switch ( type)
        {
            case 0: return term.getFloatingRateType().getId();
            case 1: return term.getPenaltyOnPrincipleOverdueRateType().getId();
            case 2: return term.getPenaltyOnPrincipleOverdueRateType().getId();
        }
        return 2;
    }

    private int getDaysInYearByType(short type, CreditTerm term)
    {
        switch ( (short)term.getDaysInYearMethod().getId())
        {
            case 1: return (int)365;
            case 2: return (int)360;
            case 3: return (int)366;
        }

        return 360;
    }

    private boolean isAfterSrok()
    {
        return true;
    }

    public long getCalculatedDays( Date DateFrom, Date DateTo, long Method )
    {
        Date tDateFrom = new Date(DateFrom.getTime());
        Date tDateTo = new Date(DateTo.getTime());

        if (Method==1)
        {
            long   iOneDay = 1 * 24 * 60 * 60 * 1000;
            long   Days=(tDateTo.getTime()-tDateFrom.getTime())/iOneDay;
            if (Days==0) return (0); else return Days;
        }
        else
        {
            if(tDateTo.getDate()==31) tDateTo.setDate(30);
            if(tDateFrom.getDate()==31) tDateFrom.setDate(30);
            if (tDateTo.getYear()-tDateFrom.getYear()==0)
            {
                return (((tDateTo.getMonth()-tDateFrom.getMonth())-1)*30+(30+tDateTo.getDate()-tDateFrom.getDate()));
            }
            else    return ((30-tDateFrom.getDate())+(12-tDateFrom.getMonth())*30+(tDateTo.getMonth()-1)*30+(tDateTo.getDate())+(tDateTo.getYear()-tDateFrom.getYear()-1)*360);
        }
    }

    private List<Loan> getChildrenByLoanId(long loanId)
    {
        List<Loan> result = new ArrayList<>();


        result.addAll(childLoanMap.get(loanId));

//        Loan loan = loanService.getById(loanId);
//        for(Loan childd: loan.getChildren())
//        {
//            Loan child=loanService.getById(childd.getId());
//            result.add(child);
//        }



        return result;
    }

    private Long getClassOfLoan(Long loanId)
    {
        String baseQuery="select loan.loan_class_id+10 from loan where id ="+loanId.toString();

        Query query=entityManager.createNativeQuery(baseQuery);

        Long returnValue=Long.parseLong(query.getSingleResult().toString());

        returnValue=returnValue-10L;

        return returnValue;
    }

    public LinkedHashMap<Long, LoanDetailedSummary> getAllLoanDetailedSummariesByLoanViewList(LinkedHashSet<LoanView> loanViews, Date onDate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        LinkedHashMap<Long, LoanDetailedSummary> ldsMap = new LinkedHashMap<>();

        if(loanViews.size()==0)
        {
            return ldsMap;
        }

        List<Long> loanIdList = new ArrayList<>();

        for (LoanView lv: loanViews)
        {
            loanIdList.add(lv.getV_loan_id());
        }

        String idList = loanIdList.toString().replace("[","").replace("]","");

        String baseQuery="select l.id as loan_id, 0 as parent_id, l.amount as loanAmount,  l.loanStateId as state_id, ld.id,  ld.onDate,  ld.version,  ld.totalDisbursement,  ld.totalPrincipalPaid,  ld.totalInterestPaid,  ld.totalPenaltyPaid,  ld.principalOutstanding,  ld.interestOutstanding,  ld.penaltyOutstanding,  ld.totalPrincipalWriteOff,  ld.totalInterestAccruedOnInterestPayment, ld.principalOverdue, ld.interestOverdue, ld.penaltyOverdue " +
                "from loan l,loanDetailedSummary ld where l.id = ld.loanId AND ld.id =" +
                "( select lds.id from loanDetailedSummary lds where lds.loanId = l.id AND lds.onDate < :onDate AND lds.loanId in ("+ idList+") order by lds.onDate DESC, lds.id desc limit 1 ) order by l.id";

        Query query=entityManager.createNativeQuery(baseQuery, LoanDetailedSummaryTempModel.class);
        query.setParameter("onDate", onDate);

        List<LoanDetailedSummaryTempModel> objectList = (List<LoanDetailedSummaryTempModel>) query.getResultList();


        for (LoanDetailedSummaryTempModel object : objectList)
        {
            if(loanIdList.contains(object.getLoan_id()))
                loanIdList.remove(object.getLoan_id());
        }

        if(loanIdList.size()>0)
        {
            String idList2 = loanIdList.toString().replace("[","").replace("]","");
            String baseQuery2="select l.id as loan_id,  l.parent_id, l.amount as loanAmount, l.loanStateId as state_id, ld.id,  ld.onDate,  ld.version,  ld.totalDisbursement,  ld.totalPrincipalPaid,  ld.totalInterestPaid,  ld.totalPenaltyPaid,  ld.principalOutstanding,  ld.interestOutstanding,  ld.penaltyOutstanding,  ld.totalPrincipalWriteOff,  ld.totalInterestAccruedOnInterestPayment, ld.principalOverdue, ld.interestOverdue, ld.penaltyOverdue " +
                    "from loan l,loanDetailedSummary ld where l.id = ld.loanId AND ld.id =" +
                    "( select lds.id from loanDetailedSummary lds where lds.loanId = l.id AND lds.onDate < :onDate AND l.parent_id in ("+ idList2+") order by lds.onDate DESC limit 1 ) order by l.id";

            Query query2=entityManager.createNativeQuery(baseQuery2, LoanDetailedSummaryTempModel.class);
            query2.setParameter("onDate", onDate);

            List<LoanDetailedSummaryTempModel> objectList2 = (List<LoanDetailedSummaryTempModel>) query2.getResultList();

            for (LoanDetailedSummaryTempModel modelInLoop: objectList2)
            {
                NormalLoan childLoan = new NormalLoan();
                childLoan.setId(modelInLoop.getLoan_id());
                childLoan.setAmount(modelInLoop.getLoanAmount());

                LoanState state = new LoanState();
                state.setId(modelInLoop.getState_id());
                childLoan.setLoanState(state);

                if(modelInLoop.getState_id()!=3)
                if(childLoanMap.get(modelInLoop.getParent_id())==null)
                {
                    Set<Loan> newChildList = new HashSet<>();
                    newChildList.add(childLoan);
                    childLoanMap.put(modelInLoop.getParent_id(),newChildList);
                    loanIdList.remove(modelInLoop.getParent_id());
                }
                else
                {
                    Set<Loan> existedChildList = new HashSet<>();
                    existedChildList.addAll(childLoanMap.get(modelInLoop.getParent_id()));
                    existedChildList.add(childLoan);

                    childLoanMap.put(modelInLoop.getParent_id(), existedChildList);
                }
            }

            objectList.addAll(objectList2);

        }

        if(loanIdList.size()>0)
        {
            System.out.println(loanIdList.toString());
        }





        return convertObjectListToMap(objectList);
    }

    public LinkedHashMap<Long, LoanDetailedSummary> convertObjectListToMap(List<LoanDetailedSummaryTempModel> objectList)
    {
        LinkedHashMap<Long, LoanDetailedSummary> ldsMap = new LinkedHashMap<>();

        Date onDate = new Date();

        List<Long> loanIdList = new ArrayList<>();
        List<Long> termIdList = new ArrayList<>();

        for (LoanDetailedSummaryTempModel object : objectList)
        {
            loanIdList.add(object.getLoan_id());

            LoanDetailedSummary lds = new LoanDetailedSummary();

            NormalLoan loan = new NormalLoan();
            loan.setId(object.getLoan_id());

            lds.setLoan(loan);
            lds.setId(object.getId());
            lds.setOnDate(object.getOnDate());
            lds.setVersion(object.getVersion());
            lds.setTotalDisbursement(object.getTotalDisbursement());
            lds.setTotalPrincipalPaid(object.getTotalPrincipalPaid());
            lds.setTotalInterestPaid(object.getTotalInterestPaid());
            lds.setTotalPenaltyPaid(object.getTotalPenaltyPaid());
            lds.setPrincipalOutstanding(object.getPrincipalOutstanding());
            lds.setInterestOutstanding(object.getInterestOutstanding());
            lds.setPenaltyOutstanding(object.getPenaltyOutstanding());
            lds.setTotalPrincipalWriteOff(object.getTotalPrincipalWriteOff());
            lds.setTotalInterestAccruedOnInterestPayment(object.getTotalInterestAccruedOnInterestPayment());
            lds.setPrincipalOverdue(object.getPrincipalOverdue());
            lds.setInterestOverdue(object.getInterestOverdue());
            lds.setPenaltyOverdue(object.getPenaltyOverdue());

            ldsMap.put(object.getLoan_id(), lds);

            onDate = object.getOnDate();

        }



//
//
//        String idList = loanIdList.toString().replace("[","").replace("]","");
//
//        String baseQuery="select * " +
//                "from creditTerm ct where ct.record_status = 1 AND ct.startDate < :onDate AND ct.loanId in ("+ idList+")";
//
//
//        Query query=entityManager.createNativeQuery(baseQuery, CreditTerm.class);
//        query.setParameter("onDate", onDate);
//
//        List<CreditTerm> termList = (List<CreditTerm>) query.getResultList();
//
//        for (CreditTerm term :termList)
//        {
//            termMap.put(term.getLoan().getId(), term);
//        }
//
//
//



        return ldsMap;
    }

    public Boolean checkFilterOptions(LoanSummary loanSummary, ReportTemplate reportTemplate)
    {
        boolean filtered = true;

        try
        {
            for ( FilterParameter filterParameter: reportTemplate.getFilterParameters())
            {
                if(filterParameter.getFilterParameterType().name().contains("COMPARE"))
                {
                    if(filterParameter.getFieldName().contains("v_ls"))
                    {

                        LoanSummaryView lsv = null;

                        lsv = this.convertLoanView(new LoanView(), loanSummary);

                        Object object = null;

                        try
                        {

                            String sMethodName = "get"+filterParameter.getFieldName().substring(0, 1).toUpperCase()+filterParameter.getFieldName().substring(1, filterParameter.getFieldName().length());

                            object = LoanSummaryView.class.getMethod(sMethodName,null).invoke(lsv);

                        }
                        catch (Exception ex)
                        {

                        }


                        if(object!=null)
                            switch (filterParameter.getComparator().name())
                            {
                                case "GREATER_THEN" :

                                    double comparedDoubleValue = 0;
                                    comparedDoubleValue = Double.parseDouble(filterParameter.getComparedValue());

                                    double doubleValue = 0;
                                    doubleValue = Double.parseDouble(object.toString());

                                    if(doubleValue>comparedDoubleValue || comparedDoubleValue<0)
                                    {
                                        filtered=true;
                                    }
                                    else return false;

                                    break;

                                case "GREATER_THEN_OR_EQUALS" :

                                    comparedDoubleValue = Double.parseDouble(filterParameter.getComparedValue());

                                    doubleValue = Double.parseDouble(object.toString());

                                    if(doubleValue>=comparedDoubleValue ||comparedDoubleValue<0)
                                    {
                                        filtered=true;
                                    }
                                    else return false;

                                    break;

                                case "LESS_THEN" :

                                    comparedDoubleValue = Double.parseDouble(filterParameter.getComparedValue());

                                    doubleValue = Double.parseDouble(object.toString());

                                    if(doubleValue<comparedDoubleValue ||comparedDoubleValue<0)
                                    {
                                        filtered=true;
                                    }
                                    else return false;

                                    break;

                                case "LESS_THEN_OR_EQUALS" :

                                    comparedDoubleValue = Double.parseDouble(filterParameter.getComparedValue());

                                    doubleValue = Double.parseDouble(object.toString());

                                    if(doubleValue<=comparedDoubleValue ||comparedDoubleValue<0)
                                    {
                                        filtered=true;
                                    }
                                    else return false;

                                    break;
                            }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            return true;
        }




        return filtered;
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

        return convertedLoanSummaryView;
    }


}

