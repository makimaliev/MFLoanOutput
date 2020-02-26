package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.admin.org.model.*;
import kg.gov.mf.loan.admin.org.repository.PersonRepository;
import kg.gov.mf.loan.admin.org.service.*;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.collateral.*;
import kg.gov.mf.loan.manage.model.collection.*;
import kg.gov.mf.loan.manage.model.debtor.*;
import kg.gov.mf.loan.manage.model.loan.*;
import kg.gov.mf.loan.manage.model.order.CreditOrder;
import kg.gov.mf.loan.manage.model.order.CreditOrderState;
import kg.gov.mf.loan.manage.model.order.CreditOrderType;
import kg.gov.mf.loan.manage.model.orderterm.*;
import kg.gov.mf.loan.manage.repository.debtor.DebtorRepository;
import kg.gov.mf.loan.manage.repository.debtor.OwnerRepository;
import kg.gov.mf.loan.manage.repository.loan.CreditTermRepository;
import kg.gov.mf.loan.manage.repository.loan.LoanRepository;
import kg.gov.mf.loan.manage.repository.loan.PaymentRepository;
import kg.gov.mf.loan.manage.repository.loan.PaymentScheduleRepository;
import kg.gov.mf.loan.manage.service.collateral.*;
import kg.gov.mf.loan.manage.service.collection.*;
import kg.gov.mf.loan.manage.service.debtor.*;
import kg.gov.mf.loan.manage.service.loan.*;
import kg.gov.mf.loan.manage.service.order.CreditOrderService;
import kg.gov.mf.loan.manage.service.order.CreditOrderStateService;
import kg.gov.mf.loan.manage.service.order.CreditOrderTypeService;
import kg.gov.mf.loan.manage.service.orderterm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

public class SecondMigrationToolVersion2 {

    //region service

    @Autowired
    GoodTypeService goodTypeService;

    @Autowired
    AdditionalAgreementService additionalAgreementService;

    @Autowired
    PhaseDetailsService phaseDetailsService;

    @Autowired
    WriteOffService writeOffService;


    @Autowired
    CollateralItemInspectionResultService collateralItemInspectionResultService;

    @Autowired
    CollateralItemDetailsService collateralItemDetailsService;

    @Autowired
    CollateralItemService collateralItemService;

    @Autowired
    CollateralItemArrestFreeService collateralItemArrestFreeService;

    @Autowired
    CollectionProcedureService collectionProcedureService;

    @Autowired
    CollectionPhaseService collectionPhaseService;

    @Autowired
    CollectionEventService collectionEventService;


    @Autowired
    PhaseTypeService phaseTypeService;

    @Autowired
    PhaseStatusService phaseStatusService;

    @Autowired
    ProcedureStatusService procedureStatusService;

    @Autowired
    ProcedureTypeService procedureTypeService;

    @Autowired
    EventTypeService eventTypeService;

    @Autowired
    EventStatusService eventStatusService;



    @Autowired
    CollateralAgreementService collateralAgreementService;

    @Autowired
    LoanGoodsService loanGoodsService;

    @Autowired
    SupervisorPlanService supervisorPlanService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    CreditTermService creditTermService;

    @Autowired
    CreditTermRepository creditTermRepository;

    @Autowired
    OrderTermTransactionOrderService orderTermTransactionOrderService;

    @Autowired
    OrderTermRatePeriodService orderTermRatePeriodService;

    @Autowired
    PaymentScheduleService paymentScheduleService;

    @Autowired
    PaymentScheduleRepository paymentScheduleRepository;

    @Autowired
    InstallmentStateService installmentStateService;

    @Autowired
    LoanService loanService;


    @Autowired
    DebtorService debtorService;


    @Autowired
    CreditOrderService creditOrderService;

    @Autowired
    OrganizationFormService organizationFormService;


    @Autowired
    DebtorTypeService debtorTypeService;

    @Autowired
    LoanTypeService loanTypeService;

    @Autowired
    ItemTypeService itemTypeService;

    @Autowired
    QuantityTypeService quantityTypeService;

    @Autowired
    LoanStateService loanStateService;

    @Autowired
    LoanFinGroupService loanFinGroupService;


    @Autowired
    WorkSectorService workSectorService;

    @Autowired
    PaymentTypeService paymentTypeService;

    @Autowired
    ConditionTypeService conditionTypeService;

    @Autowired
    InspectionResultTypeService inspectionResultTypeService;

    @Autowired
    OrderTermFloatingRateTypeService orderTermFloatingRateTypeService;

    @Autowired
    OrderTermDaysMethodService orderTermDaysMethodService;

    @Autowired
    OrderTermCurrencyService orderTermCurrencyService;

    @Autowired
    OrderTermFundService orderTermFundService;

    @Autowired
    CreditOrderStateService creditOrderStateService;

    @Autowired
    CreditOrderTypeService creditOrderTypeService;

    @Autowired
    PersonService personService;

    @Autowired
    StaffService staffService;

    @Autowired
    IdentityDocGivenByService identityDocGivenByService;

    @Autowired
    IdentityDocTypeService identityDocTypeService;

    @Autowired
    VillageService villageService;

    @Autowired
    AokmotuService aokmotuService;

    @Autowired
    OrgFormService orgFormService;

    @Autowired
    UserService userService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    PositionService positionService;

    @Autowired
    RegionService regionService;

    @Autowired
    DistrictService districtService;


    @Autowired
    CurrencyRateService currencyRateService;

    @Autowired
    FloatingRateService floatingRateService;

    @Autowired
    OwnerService ownerService;

    @Autowired
    DebtTransferService debtTransferService;

    @Autowired
    BankruptService bankruptService;

    @Autowired
    DestinationAccountService destinationAccountService;

    @Autowired
    DebtorGroupService debtorGroupService;

    @Autowired
    DebtorSubGroupService debtorSubGroupService;

    //endregion
    @Autowired
    PersonRepository personRepository;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    DebtorRepository debtorRepository;

    @Autowired
    LoanRepository loanRepository;

    @PersistenceContext
    private EntityManager entityManager;


    //region hashMaps
    Set<String> errorList = new HashSet<String>();

    Map<Long, OrgForm> organizationFormMap = new HashMap<Long,OrgForm>();

    Map<Long, Region> regionMap = new HashMap<Long,Region>();
    Map<Long, District> districtMap = new HashMap<Long,District>();
    Map<Long, Aokmotu> aokmotuMap = new HashMap<Long,Aokmotu>();
    Map<Long, Village> villageMap = new HashMap<Long,Village>();

    Map<Long,IdentityDocType> identityDocTypeMap = new HashMap<Long,IdentityDocType>();
    Map<Long,IdentityDocGivenBy> identityDocGivenByMap = new HashMap<Long,IdentityDocGivenBy>();
    Map<Long, OrderTermFund> fundMap = new HashMap<Long,OrderTermFund>();
    Map<Long, OrderTermCurrency> currencyMap = new HashMap<Long,OrderTermCurrency>();

    Map<Long, OrderTermDaysMethod> daysMethodMap = new HashMap<Long,OrderTermDaysMethod>();

    Map<Long, OrderTermFloatingRateType> rateTypeMap = new HashMap<Long,OrderTermFloatingRateType>();

    Map<Long, DebtorType> debtorTypeMap = new HashMap<Long,DebtorType>();
    Map<Long,LoanType> loanTypeMap = new HashMap<Long,LoanType>();
    Map<Long, ItemType> itemTypeMap = new HashMap<Long,ItemType>();
    Map<Long, QuantityType> quantityTypeMap = new HashMap<Long,QuantityType>();
    Map<Long, PaymentType> paymentTypeMap = new HashMap<Long,PaymentType>();

    Map<Long, InspectionResultType> inspectionResultTypeMap = new HashMap<Long,InspectionResultType>();
    Map<Long,LoanState> loanStateMap = new HashMap<Long,LoanState>();
    Map<Long,LoanFinGroup> loanFinGroupMap = new HashMap<Long,LoanFinGroup>();
    Map<Long, WorkSector> workSectorMap = new HashMap<Long,WorkSector>();

    Map<Long, CreditOrder> crditOrderMap = new HashMap<Long,CreditOrder>();


    Map<Long, PhaseType> phaseTypeMap = new HashMap<Long,PhaseType>();
    Map<Long, PhaseStatus> phaseStatusMap = new HashMap<Long,PhaseStatus>();
    Map<Long, ProcedureType> procedureTypeMap = new HashMap<Long,ProcedureType>();
    Map<Long, ProcedureStatus> procedureStatusMap = new HashMap<Long,ProcedureStatus>();

    Map<Long, User> userMap = new HashMap<Long,User>();

    Map<Long, Debtor> debtorMap = new HashMap<Long,Debtor>();

    Map<Long, CollateralItem> collateralItemMap = new HashMap<Long,CollateralItem>();

    Map<Long,Long> debtorToBelinkedDebtTransferMap = new HashMap<>();

    Map<Long,Long> debtorToBelinkedCollateralItemMap = new HashMap<>();

    Map<Long, GoodType> goodTypeMap = new HashMap<Long,GoodType>();

    Map<Long, CPerson> c_personMap = new HashMap<>();

    Map<Long, Debtor> debtorToBeMigratedMap = new HashMap<>();

    Map<Long, Loan> loanMap = new HashMap<>();
    Map<Long, Loan> debtorLoanMap = new HashMap<>();

    Map<Long, PaymentSchedule> scheduleMap = new HashMap<>();
    Map<Long, Payment> paymentMap = new HashMap<>();
    Map<Long, CreditTerm> termMap = new HashMap<>();

    Map<Long, CollateralAgreement> agreementMap = new HashMap<>();

    Map<Long, CollateralItemArrestFree> arrestFreeMap = new HashMap<>();

    Map<Long, CollateralItemInspectionResult> inspectionMap = new HashMap<>();

    Map<Long, CollateralItem> itemMap = new HashMap<>();



    OrganizationForm organizationForm ;
    OrganizationForm organizationForm2;

    InstallmentState installmentState ;

    OrderTermTransactionOrder transactionOrder1;
    OrderTermTransactionOrder transactionOrder3;

    LoanFinGroup finGroup9;
    LoanFinGroup finGroup1;

    DebtorGroup debtorGroup ;
    DebtorSubGroup debtorSubGroup ;

    OrderTermRatePeriod ratePeriod;

    //endregion

    private Connection getSourceConnection(String ip,String database,String username,String password)
    {
        Connection connection = null;
        try
        {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e)
        {
            errorList.add(" Class for name error"+e);
            return null;
        }

        try
        {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://"+ip+":5432/"+database, username,
                    password);
        }
        catch (SQLException e)
        {
            errorList.add("connection error"+e);
            return null;
        }

        return connection;
    }

    private class CPerson {

        private Address address;
        private Contact contact;
        private IdentityDoc identityDoc;
        private Person person;
        private Debtor debtor;

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Contact getContact() {
            return contact;
        }

        public void setContact(Contact contact) {
            this.contact = contact;
        }

        public IdentityDoc getIdentityDoc() {
            return identityDoc;
        }

        public void setIdentityDoc(IdentityDoc identityDoc) {
            this.identityDoc = identityDoc;
        }

        public Person getPerson() {
            return person;
        }

        public void setPerson(Person person) {
            this.person = person;
        }

        public Debtor getDebtor() {
            return debtor;
        }

        public void setDebtor(Debtor debtor) {
            this.debtor = debtor;
        }

        @Override
        public String toString() {
            return "CPerson{" +

                    ", person=" + person.getName() +
                                        '}';
        }
    }



    public void loanMigrate(Long oldLoansId,String ip,String database,String username,String password){

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        organizationForm = this.organizationFormService.getById((long)1);
        organizationForm2 = this.organizationFormService.getById((long)2);

        installmentState = this.installmentStateService.getById((long)1);

        transactionOrder1 = this.orderTermTransactionOrderService.getById((long)1);
        transactionOrder3 = this.orderTermTransactionOrderService.getById((long)1);

        ratePeriod = this.orderTermRatePeriodService.getById((long)1);

        debtorGroup = this.debtorGroupService.getById(1L);
        debtorSubGroup = this.debtorSubGroupService.getById(1L);

        finGroup9 = loanFinGroupService.getById(9L);
        finGroup1 = loanFinGroupService.getById(1L);

        Connection connection = this.getSourceConnection(ip,database,username,password);

        boolean orgFormMigrationSuccess = false;

        boolean light = false;

        boolean orgFormMigrationDone = light;
        if(!orgFormMigrationDone) orgFormMigrationDone = this.orgFormMigrate(connection);

        boolean regionMigrationDone = light;
        if(!regionMigrationDone) regionMigrationDone = this.regionMigrate(connection);

        boolean districtMigrationDone = light;
        if(!districtMigrationDone) districtMigrationDone = this.districtMigrate(connection);


        boolean aokmotuMigrationDone = light;
        if(!aokmotuMigrationDone) aokmotuMigrationDone = this.aokmotuMigrate(connection);

        boolean villageMigrationDone = light;
        if(!villageMigrationDone) villageMigrationDone = this.villageMigrate(connection);

        boolean idDocTypeMigrationDone = light;
        if(!idDocTypeMigrationDone) idDocTypeMigrationDone = this.idDocTypeMigrate(connection);

        boolean idDocGivenByMigrationDone = light;
        if(!idDocGivenByMigrationDone) idDocGivenByMigrationDone = this.idDocGivenByMigrate(connection); // CREATE NEW

        boolean gaubkMigrationDone = light;
        if(!gaubkMigrationDone) gaubkMigrationDone = this.gaubkMigrate(connection); // CREATE NEW

        boolean departmentMigrationDone = light;
        if(!departmentMigrationDone) departmentMigrationDone = this.departmentMigrate(connection);

        boolean positionMigrationDone = light;
        if(!positionMigrationDone) positionMigrationDone = this.positionMigrate(connection);

        boolean userMigrationDone = light;
        if(!userMigrationDone) userMigrationDone = this.migrateUsers(connection);

        boolean orderStateMigrationDone = light;
        if(!orderStateMigrationDone) orderStateMigrationDone = this.orderStateMigrate(connection);

        boolean orderTypeMigrationDone = light;
        if(!orderTypeMigrationDone) orderTypeMigrationDone = this.orderTypeMigrate(connection);

        boolean loanFundMigrationDone = light;
        if(!loanFundMigrationDone) loanFundMigrationDone = this.loanFundMigrate(connection);

        boolean loanCurrencyMigrationDone = light;
        if(!loanCurrencyMigrationDone) loanCurrencyMigrationDone = this.loanCurrencyMigrate(connection);

        boolean daysCalcMethodMigrationDone = light;
        if(!daysCalcMethodMigrationDone) daysCalcMethodMigrationDone = this.daysCalcMethodMigrate(connection);

        boolean rateTypeMigrationDone = light;
        if(!rateTypeMigrationDone) rateTypeMigrationDone = this.rateTypeMigrate(connection);

        boolean debtorTypeMigrationDone = light;
        if(!debtorTypeMigrationDone) debtorTypeMigrationDone = this.debtorTypeMigrate(connection);

        boolean loanTypeMigrationDone = light;
        if(!loanTypeMigrationDone) loanTypeMigrationDone = this.loanTypeMigrate(connection);

        boolean itemTypeMigrationDone = light;
        if(!itemTypeMigrationDone) itemTypeMigrationDone = this.itemTypeMigrate(connection);

        boolean quantityTypeMigrationDone = light;
        if(!quantityTypeMigrationDone) quantityTypeMigrationDone = this.quantityTypeMigrate(connection);

        boolean paymentTypeMigrationDone = light;
        if(!paymentTypeMigrationDone) paymentTypeMigrationDone = this.paymentTypeMigrate(connection);

        boolean inspectionResultTypeMigrationDone = light;
        if(!inspectionResultTypeMigrationDone) inspectionResultTypeMigrationDone = this.inspectionResultTypeMigrate(connection);

        boolean loanStatusMigrationDone = light;
        if(!loanStatusMigrationDone) loanStatusMigrationDone = this.loanStatusMigrate(connection);

//        boolean loanFinGroupMigrationDone = light;
//        if(!loanFinGroupMigrationDone) loanFinGroupMigrationDone = this.loanFinGroupMigrate(connection);


        boolean workSectorMigrationDone = light;
        if(!workSectorMigrationDone) workSectorMigrationDone = this.workSectorMigrate(connection);

        boolean creditOrderMigrationDone = light;
        if(!creditOrderMigrationDone) creditOrderMigrationDone = this.creditOrderMigrate(connection);

        boolean collectionMigrateDone = light;
        if(!collectionMigrateDone) collectionMigrateDone = this.collectionPhaseTypeMigrate(connection);


        boolean goodTypeMigrateDone = light;
        if(!goodTypeMigrateDone) goodTypeMigrateDone = this.goodsTypeMigrate(connection);

        boolean currencyRateMigrateDone = light;
        if(!currencyRateMigrateDone) currencyRateMigrateDone = this.currencyRateMigrate(connection);

        boolean floatingRateMigrateDone = light;
        if(!floatingRateMigrateDone) floatingRateMigrateDone = this.floatingRateMigrate(connection);
        // sql code get person data



        Statement st = null;

        try {
            st = connection.createStatement();

            ResultSet rs = st.executeQuery("select (select aokmotu.id from aokmotu where aokmotu.region =  address.region_code and aokmotu.district = address.district_code and aokmotu.aokmotu = address.a_okmotu_code limit 1) as aokmotu_id,\n" +
                    "      (select selo.id from selo where selo.region = address.region_code and selo.district = address.district_code and selo.aokmotu = address.a_okmotu_code and selo.selo_code = address.selo limit 1) as selo_id,\n" +
                    "\n" +
                    "      * from person, person_details,address,phone\n" +
                    "where person.id = person_details.person_id AND \n" +
                    "      address.user_id = person.id AND address.contact_type = 2 AND \n" +
                    "      phone.user_id = person.id and phone.contact_type = 2");


            generateCPersonMap(connection,rs,st);

            // save Debtor
            try
            {

                for (Map.Entry<Long,CPerson> entry : c_personMap.entrySet())
                {

                    try
                    {
                        CPerson c_person = entry.getValue();

                        Person person = c_person.getPerson();

                        if(person!=null)
                        {

                            try
                            {
                                this.personRepository.save(person);

                                Owner owner = new Owner();

                                owner.setName(person.getName());
                                owner.setOwnerType(OwnerType.PERSON);
                                owner.setEntityId(person.getId());
                                owner.setAddress(person.getAddress());

                                this.ownerService.add(owner);

                                Debtor debtor = new Debtor();

                                debtor.setName(owner.getName());
                                debtor.setOwner(owner);

                                debtor.setDebtorType(debtorTypeMap.get((long)1));

                                debtor.setOrgForm(organizationForm2);
                                debtor.setVersion(person.getVersion());

                                debtor.setWorkSector(workSectorMap.get((long)13));

                                debtor.setRecord_status(1);
                                debtor.setAddress_id(person.getAddress().getId());

                                debtor.setDebtorGroup(debtorGroup);
                                debtor.setDebtorSubGroup(debtorSubGroup);

                                this.debtorService.add(debtor);

                                debtorMap.put(entry.getKey(),debtor);
                            }
                            catch (Exception ex)
                            {
                                System.out.println(" SAVE DEBTOR ERROR "+ ex+ c_person.getPerson().getName());
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        System.out.println(" SAVE DEBTOR ERROR "+ ex);
                    }
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            generateLoanMap(connection);



            // save Loan
            try
            {

                for (Map.Entry<Long,Loan> entry : loanMap.entrySet())
                {

                    try
                    {
                        Loan loan= entry.getValue();

                        if(loan!=null)
                        {

                            try
                            {
                                if(loan.getDebtor()!=null)
                                {
                                    this.loanRepository.save(loan);
                                }
                                else
                                {
                                    errorList.add(" LOAN WITHOUT DEBTOR ==>>"+ entry.getKey());
                                }

                            }
                            catch (Exception ex)
                            {
                                System.out.println(" SAVE DEBTOR ERROR "+ ex+ loan.getDebtor().getName());
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        System.out.println(" SAVE DEBTOR ERROR "+ ex);
                    }
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            generateScheduleMap(connection);
            generatePaymentMap(connection);
            generateTermMap(connection);

            // save Schedule
            try
            {
                for (Map.Entry<Long,PaymentSchedule> entry : scheduleMap.entrySet())
                {

                    try
                    {
                        PaymentSchedule paymentSchedule = entry.getValue();

                        if(paymentSchedule!=null)
                        {
                            try
                            {
                                if(paymentSchedule.getLoan()!=null)
                                {
                                    this.paymentScheduleRepository.save(paymentSchedule);
                                }
                            }
                            catch (Exception ex)
                            {
                                System.out.println(" SAVE SCHEDULE ERROR "+ ex);
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        System.out.println(" SAVE DEBTOR ERROR "+ ex);
                    }
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            // save Payment
            try
            {
                for (Map.Entry<Long,Payment> entry : paymentMap.entrySet())
                {

                    try
                    {
                        Payment payment = entry.getValue();

                        if(payment!=null)
                        {
                            try
                            {
                                if(payment.getLoan()!=null)
                                {
                                    this.paymentRepository.save(payment);
                                }
                            }
                            catch (Exception ex)
                            {
                                System.out.println(" SAVE PAYMENT ERROR "+ ex);
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        System.out.println(" SAVE DEBTOR ERROR "+ ex);
                    }
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            // save Term
            try
            {
                for (Map.Entry<Long,CreditTerm> entry : termMap.entrySet())
                {

                    try
                    {
                        CreditTerm creditTerm = entry.getValue();

                        if(creditTerm!=null)
                        {
                            try
                            {
                                if(creditTerm.getLoan()!=null)
                                {
                                    this.creditTermRepository.save(creditTerm);
                                }
                            }
                            catch (Exception ex)
                            {
                                System.out.println(" SAVE TERM  ERROR "+ ex);
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        System.out.println(" SAVE DEBTOR ERROR "+ ex);
                    }
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            generateAgreementMap(connection);

            generateItemMap(connection);

            generateArrestFreeMap(connection);

            generateInspectionMap(connection);

            try
            {
                for (Map.Entry<Long,CollateralItemInspectionResult> entry : inspectionMap.entrySet())
                {
                    CollateralItemInspectionResult collateralItemInspectionResult = entry.getValue();

                    if(collateralItemInspectionResult!=null)
                    {
                        try
                        {
                            if(collateralItemInspectionResult.getCollateralItem()!=null)
                            {
                                collateralItemInspectionResult.getCollateralItem().getCollateralItemInspectionResults().add( collateralItemInspectionResult);
                            }
                        }
                        catch (Exception ex)
                        {
                            System.out.println(" SAVE AGREEMENT  ERROR "+ ex);
                        }
                    }

                }

            }
            catch (Exception ex)
            {

            }

            generateAgreementLoanMap(connection);

            // save agreement
            try
            {
                for (Map.Entry<Long,CollateralAgreement> entry : agreementMap.entrySet())
                {
                    try
                    {
                        CollateralAgreement collateralAgreement = entry.getValue();

                        if(collateralAgreement!=null)
                        {
                            try
                            {
                                if(collateralAgreement.getOwner()!=null)
                                {
                                    this.collateralAgreementService.add(collateralAgreement);
                                }
                            }
                            catch (Exception ex)
                            {
                                System.out.println(" SAVE AGREEMENT  ERROR "+ ex);
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        System.out.println(" SAVE DEBTOR ERROR "+ ex);
                    }
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }



/*



            // save inspection
            try
            {
                for (Map.Entry<Long,CollateralItemInspectionResult> entry : inspectionMap.entrySet())
                {
                    try
                    {
                        CollateralItemInspectionResult collateralItemInspectionResult = entry.getValue();

                        if(collateralItemInspectionResult!=null)
                        {
                            try
                            {
                                if(collateralItemInspectionResult.getCollateralItem()!=null)
                                {

                                    CollateralItem item = this.collateralItemService.getById(collateralItemInspectionResult.getCollateralItem().getVersion());

                                    collateralItemInspectionResult.setCollateralItem(item);

                                    this.collateralItemInspectionResultService.add(collateralItemInspectionResult);
                                }
                            }
                            catch (Exception ex)
                            {
                                System.out.println(" SAVE AGREEMENT  ERROR "+ ex);
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        System.out.println(" SAVE DEBTOR ERROR "+ ex);
                    }
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }



*/





        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private boolean generateCPersonMap (Connection connection,ResultSet rs,Statement st)
    {
        try
        {
            int counter=0;

            if (connection != null)
            {
                if(rs != null)
                {
                    while (rs.next())
                    {
                        counter++;

                        CPerson c_person = new CPerson();

                        Address address = new Address();

                        try
                        {
                            Region region = regionMap.get((long)rs.getInt("region"));
                            District district = districtMap.get((long)rs.getInt("district"));
                            Aokmotu aokmotu = (rs.getInt("aokmotu_id")>0) ? aokmotuMap.get((long)rs.getInt("aokmotu_id")) : aokmotuMap.get((long)1);
                            Village village = (rs.getInt("selo_id")>0) ? villageMap.get((long)rs.getInt("selo_id")) : villageMap.get((long)1);

                            address.setRegion(region);
                            address.setDistrict(district);
                            address.setAokmotu(aokmotu);
                            address.setVillage(village);
                            address.setLine(rs.getString("address_line1"));

                        }
                        catch (Exception ex)
                        {
                            System.out.println(" ADDRESS ERROR ==>"+rs.getLong("person_id")+ " --> "+rs.getString("title"));
                        }

                        Contact contact = new Contact();

                        try
                        {
                            //contact
                            if(!(rs.getString("number")=="" || rs.getString("number")==null)) contact.setName(rs.getString("number"));
                            if(!(rs.getString("mobile")=="" || rs.getString("mobile")==null)) contact.setName(contact.getName()+", "+rs.getString("mobile"));

                        }
                        catch (Exception ex)
                        {
                            System.out.println(" CONTACT ERROR ==>"+rs.getLong("person_id")+ " --> "+rs.getString("title"));
                        }

                        IdentityDoc identityDoc = new IdentityDoc();

                        try
                        {
                            // id doc
                            identityDoc.setIdentityDocGivenBy(identityDocGivenByMap.get((long)1)); //MKK
                            identityDoc.setIdentityDocType(identityDocTypeMap.get(rs.getLong("document_type"))); // Passport
                            identityDoc.setEnabled(true);
                            identityDoc.setDate((rs.getDate("of_reg_date")!=null) ? rs.getDate("of_reg_date"):new java.util.Date());
                            identityDoc.setPin((rs.getString("of_reg_id")!=null)? rs.getString("of_reg_id"):"-");
                            identityDoc.setNumber((rs.getString("of_reg_series")!=null) ? (rs.getString("of_reg_series")!=null) ? rs.getString("of_reg_series")+rs.getString("of_reg_number"): "-" : "-");

                            identityDoc.setName(identityDoc.getIdentityDocType().getName() + " (" +rs.getString("of_reg_let")+")");

                            IdentityDocDetails identityDocDetails = new IdentityDocDetails();

                            identityDocDetails.setFirstname(rs.getString("title"));
                            identityDocDetails.setLastname(rs.getString("title"));
                            identityDocDetails.setFullname(rs.getString("title"));
                            identityDocDetails.setMidname(rs.getString("title"));
                            identityDoc.setIdentityDocDetails(identityDocDetails);
                        }
                        catch (Exception ex)
                        {
                            System.out.println(" ID DOC ERROR ==>"+rs.getLong("person_id")+ " --> "+rs.getString("title"));
                        }


                        try
                        {
                            // person
                            Person person = new Person();

                            person.setName(rs.getString("title"));
                            person.setEnabled(true);
                            person.setAddress(address);
                            person.setContact(contact);
                            person.setIdentityDoc(identityDoc);

                            if(rs.getString("details")!=null) person.setDescription(rs.getString("details"));
                            person.setVersion(rs.getLong("person_id"));

                            c_person.setAddress(address);
                            c_person.setContact(contact);
                            c_person.setIdentityDoc(identityDoc);
                            c_person.setPerson(person);

                            c_personMap.put(rs.getLong("person_id"),c_person);
                        }
                        catch (Exception ex)
                        {
                            System.out.println(" PERSON ERROR ==>"+rs.getLong("person_id")+ " --> "+rs.getString("title"));
                        }



                    }
                }
                else
                {
                    System.out.println(" RESULT_SET IS NULL ==>"+counter);
                    return false;
                }

            }
            else
            {
                System.out.println(" CONNECTION IS NULL ==>"+counter);
                return false;
            }
        }
        catch (Exception ex)
        {
            System.out.println(" MAIN ERROR "+ ex);
            return false;
        }

        return true;
    }

    private boolean generateLoanMap (Connection connection)
    {
        try
        {
            DestinationAccount destinationAccount = destinationAccountService.getById(1L);

            Statement stLoan = connection.createStatement();
            ResultSet rsLoan = stLoan.executeQuery("select * from credit,credit_details where credit.id = credit_details.credit_id and credit.person_id in (select id from person) order by id");
            if(rsLoan != null)
            {
                while (rsLoan.next())
                {
                    try
                    {
                        // loan
                        Loan loan = new NormalLoan();

                        String details = rsLoan.getString("details");

                        if(details.contains("(=погашен=)")||details.contains("(=банкрот=)"))
                        {
                            try
                            {
                                SimpleDateFormat DateFormatShort = new SimpleDateFormat("dd.MM.yyyy");

                                java.util.Date date = DateFormatShort.parse(details.substring(details.indexOf("дата==")+6,details.length()-2));

                                loan.setCloseDate(date);

                            }
                            catch(Exception ex)
                            {
                                System.out.println((" close date error")+ " == "+ rsLoan.getInt("credit_id"));
                            }

                        }

                        loan.setDestinationAccount(destinationAccount);

                        loan.setAmount(rsLoan.getDouble("cost"));
                        loan.setCreditOrder(crditOrderMap.get((long)rsLoan.getInt("credit_order_id")));
                        loan.setSupervisorId(1L);
                        loan.setLoanType(loanTypeMap.get((long)1));
                        loan.setCurrency(currencyMap.get((long)1));
                        loan.setRegDate(rsLoan.getDate("date"));
                        loan.setRegNumber(rsLoan.getString("number"));

                        loan.setLoanState(loanStateMap.get((long)rsLoan.getInt("status")));
                        loan.setFund(fundMap.get(rsLoan.getLong("credit_line")));

                        loan.setBankDataId(rsLoan.getLong("payment_requsite_id"));

                        try
                        {
                            if(rsLoan.getInt("fin_group")==9){
                                loan.setLoanFinGroup(finGroup9);
                            }
                            else{
                                loan.setLoanFinGroup(finGroup1);
                            }
                        }
                        catch(Exception ex)
                        {
                            loan.setLoanFinGroup(finGroup1);
                            errorList.add(" group error == credit_id == "+rsLoan.getInt("credit_id")+ " error == "+ex);
                        }

                        loan.setVersion(Long.valueOf(rsLoan.getInt("credit_id")));

                        if(debtorMap.get((long)rsLoan.getInt("person_id"))!=null)
                        {
                            System.out.println(" DEBTOR FOUND ");
                            loan.setDebtor(debtorMap.get((long)rsLoan.getInt("person_id")));
                            loanMap.put(rsLoan.getLong("id"),loan);
                        }
                        else
                        {
                            errorList.add(" LOAN WITHOUT DEBTOR ==>>"+ rsLoan.getInt("credit_id"));
                        }





                    }
                    catch (Exception ex)
                    {
                        System.out.println(" LOAN ERROR "+ ex);
                    }
                }
            }


        }
        catch (Exception ex)
        {
            System.out.println(" MAIN ERROR "+ ex);
            return false;
        }

        return true;
    }

    private boolean generateScheduleMap (Connection connection)
    {
        try
        {
            Statement stSchedule = connection.createStatement();
            ResultSet rsSchedule = stSchedule.executeQuery("select * from obligation where credit_id in (select id from credit)");

            if(rsSchedule != null)
            {
                while (rsSchedule.next())
                {
                    try
                    {
                        if(loanMap.get((long)rsSchedule.getInt("credit_id"))!=null)
                        {
                            PaymentSchedule paymentSchedule = new PaymentSchedule();

                            paymentSchedule.setDisbursement(rsSchedule.getDouble("profit"));
                            paymentSchedule.setPrincipalPayment(rsSchedule.getDouble("debt_payment"));
                            paymentSchedule.setInterestPayment(rsSchedule.getDouble("debt_percent"));
                            paymentSchedule.setCollectedInterestPayment(rsSchedule.getDouble("collected_debt_percent"));
                            paymentSchedule.setCollectedPenaltyPayment(rsSchedule.getDouble("collected_debt_penalty"));
                            paymentSchedule.setExpectedDate(rsSchedule.getDate("date"));
                            paymentSchedule.setInstallmentState(installmentState);

                            if(rsSchedule.getInt("record_status")==2)
                                paymentSchedule.setRecord_status(rsSchedule.getInt("record_status"));

                            paymentSchedule.setLoan(loanMap.get((long)rsSchedule.getInt("credit_id")));
                            scheduleMap.put((long)rsSchedule.getInt("id"),paymentSchedule);
                        }
                        else
                        {
//                            errorList.add(" SCHEDULE WITHOUT LOAN==>>"+ rsSchedule.getInt("credit_id"));
                        }

                    }
                    catch (Exception ex)
                    {
                        System.out.println(" SCHEDULE  ERROR "+ ex);
                    }
                }
            }


        }
        catch (Exception ex)
        {
            System.out.println(" MAIN ERROR "+ ex);
            return false;
        }

        return true;
    }

    private boolean generatePaymentMap (Connection connection)
    {
        try
        {
            Statement stPayment = connection.createStatement();
            ResultSet rsPayment = stPayment.executeQuery("select * from payments");

            if(rsPayment != null)
            {
                while (rsPayment.next())
                {
                    try
                    {

                        if(loanMap.get((long)rsPayment.getInt("credit_id"))!=null)
                        {
                            Payment payment = new Payment();

                            Loan loan = loanMap.get((long)rsPayment.getInt("credit_id"));

                            payment.setLoan(loanMap.get((long)rsPayment.getInt("credit_id")));

                            if(rsPayment.getString("payment_doc_number")!=null) {
                                if (rsPayment.getString("payment_doc_number").length()<100)
                                    payment.setNumber(rsPayment.getString("payment_doc_number"));
                                else payment.setNumber(rsPayment.getString("payment_doc_number").substring(0,99));
                            }
                            else payment.setNumber("");

                            payment.setPaymentDate(rsPayment.getDate("payment_date"));
                            payment.setPrincipal(rsPayment.getDouble("main"));
                            payment.setInterest(rsPayment.getDouble("percent"));
                            payment.setPenalty(rsPayment.getDouble("penalty"));
                            payment.setFee((double)0);
                            payment.setTotalAmount(rsPayment.getDouble("payments_sum"));
                            payment.setExchange_rate(rsPayment.getDouble("exchange_rate"));
                            payment.setPaymentType( paymentTypeMap.get((long)rsPayment.getInt("payment_type")));


                            if(loan.getCurrency().getId()==1)
                                payment.setIn_loan_currency(true);
                            else
                            {
                                if(rsPayment.getInt("currency_type")==1)
                                {
                                    payment.setIn_loan_currency(false);
                                }
                                else payment.setIn_loan_currency(true);
                            }

                            if(rsPayment.getString("decr_type")!=null) {
                                if (rsPayment.getString("decr_type").length()<100)
                                    payment.setDetails(rsPayment.getString("decr_type"));
                                else payment.setDetails(rsPayment.getString("decr_type").substring(0,99));
                            }
                            else payment.setDetails("");


                            if(rsPayment.getInt("record_status")==2)
                                payment.setRecord_status(rsPayment.getInt("record_status"));

                            paymentMap.put((long)rsPayment.getInt("id"),payment);
                        }
                        else
                        {
//                            errorList.add(" PAYMENT WITHOUT LOAN==>>"+ rsPayment.getInt("credit_id"));
                        }


                    }
                    catch (Exception ex)
                    {
                        System.out.println(" SCHEDULE  ERROR "+ ex);
                    }
                }
            }


        }
        catch (Exception ex)
        {
            System.out.println(" MAIN ERROR "+ ex);
            return false;
        }

        return true;
    }

    private boolean generateTermMap (Connection connection)
    {
        try
        {
            Statement stTerm = connection.createStatement();
            ResultSet rsTerm = stTerm.executeQuery("select * from credit_rule");

            if(rsTerm != null)
            {
                while (rsTerm.next())
                {
                    try
                    {

                        if(loanMap.get((long)rsTerm.getInt("credit_id"))!=null)
                        {
                            CreditTerm term = new CreditTerm();

                            Loan loan = loanMap.get((long)rsTerm.getInt("credit_id"));

                            term.setLoan(loan);

                            term.setStartDate(rsTerm.getDate("start_date"));

                            term.setInterestRateValue(rsTerm.getDouble("percent_rate"));

                            term.setPenaltyOnPrincipleOverdueRateValue(rsTerm.getDouble("penalty_main_debt"));

                            term.setPenaltyOnInterestOverdueRateValue(rsTerm.getDouble("penalty_percent"));

                            term.setGraceDaysPrincipal(rsTerm.getInt("expiry_main"));
                            term.setGraceDaysInterest(rsTerm.getInt("expiry_percent"));


                            if(rsTerm.getInt("method_days_period")<3)
                                term.setDaysInMonthMethod((OrderTermDaysMethod)daysMethodMap.get((long)rsTerm.getInt("method_days_period")));
                            else
                            {
                                term.setDaysInMonthMethod((OrderTermDaysMethod)daysMethodMap.get((long)2));
                                errorList.add(" credit term error 1 "+loan.getId());
                            }

                            if(rsTerm.getInt("method_days")<4)
                                term.setDaysInYearMethod((OrderTermDaysMethod)daysMethodMap.get((long)rsTerm.getInt("method_days")));
                            else
                            {
                                term.setDaysInYearMethod((OrderTermDaysMethod)daysMethodMap.get((long)2));
                                errorList.add(" credit term error 2 "+loan.getId()+" "+rsTerm.getInt("method_days"));
                            }

                            if(rsTerm.getInt("rate_type")>0)
                                term.setFloatingRateType((OrderTermFloatingRateType)rateTypeMap.get((long)rsTerm.getInt("rate_type")));
                            else term.setFloatingRateType((OrderTermFloatingRateType)rateTypeMap.get((long)2));

                            if(rsTerm.getInt("plus_penalty")>0)
                                term.setPenaltyOnPrincipleOverdueRateType((OrderTermFloatingRateType)rateTypeMap.get((long)rsTerm.getInt("plus_penalty")));
                            else term.setPenaltyOnPrincipleOverdueRateType((OrderTermFloatingRateType)rateTypeMap.get((long)2));

                            if(rsTerm.getInt("plus_penalty")>0)
                                term.setPenaltyOnInterestOverdueRateType((OrderTermFloatingRateType)rateTypeMap.get((long)rsTerm.getInt("plus_percent")));
                            else term.setPenaltyOnInterestOverdueRateType((OrderTermFloatingRateType)rateTypeMap.get((long)2));

                            if(rsTerm.getInt("repayment_main_debt")==1)
                                term.setTransactionOrder((OrderTermTransactionOrder)transactionOrder1);
                            else term.setTransactionOrder((OrderTermTransactionOrder)transactionOrder3);

                            term.setRatePeriod(ratePeriod);


                            term.setPenaltyLimitPercent((double)20);


                            if(rsTerm.getInt("record_status")==2)
                                term.setRecord_status(rsTerm.getInt("record_status"));
                            termMap.put((long)rsTerm.getInt("id"),term);
                        }
                        else
                        {
//                            errorList.add(" TERM WITHOUT LOAN==>>"+ rsTerm.getInt("credit_id"));
                        }


                    }
                    catch (Exception ex)
                    {
                        System.out.println(" SCHEDULE  ERROR "+ ex);
                    }
                }
            }


        }
        catch (Exception ex)
        {
            System.out.println(" MAIN ERROR "+ ex);
            return false;
        }

        return true;
    }

    private boolean generateAgreementMap (Connection connection)
    {
        try
        {
            Statement stCollateralAgreement = connection.createStatement();
            ResultSet rsCollateralAgreement =
                    stCollateralAgreement.executeQuery("select dc.*, c.person_id as p_id " +
                            "from deposit_contract dc " +
                            "INNER JOIN credit_deposit cd on cd.deposit_id = dc.id " +
                            "INNER JOIN credit c on c.id = cd.credit_id");

            if(rsCollateralAgreement != null)
            {
                while (rsCollateralAgreement.next())
                {
                    try
                    {

                        if(debtorMap.get((long)rsCollateralAgreement.getInt("p_id"))!=null)
                        {
                            CollateralAgreement collateralAgreement = new CollateralAgreement();

                            Debtor debtor = debtorMap.get((long)rsCollateralAgreement.getInt("p_id"));

                            collateralAgreement.setOwner(debtor.getOwner());

                            collateralAgreement.setVersion((long)rsCollateralAgreement.getInt("id"));

                            if(rsCollateralAgreement.getDate("official_reg_date")!=null)
                            {
                                collateralAgreement.setAgreementDate(rsCollateralAgreement.getDate("official_reg_date"));
                                collateralAgreement.setNotaryOfficeRegDate(rsCollateralAgreement.getDate("official_reg_date"));
                            }

                            collateralAgreement.setAgreementNumber(rsCollateralAgreement.getString("official_reg_number"));
                            collateralAgreement.setNotaryOfficeRegNumber(rsCollateralAgreement.getString("official_reg_number"));

                            if(rsCollateralAgreement.getDate("arest_date")!=null)
                                collateralAgreement.setArrestRegDate(rsCollateralAgreement.getDate("arest_date"));

                            collateralAgreement.setArrestRegNumber(rsCollateralAgreement.getString("arest_number"));

                            if(rsCollateralAgreement.getDate("official_reg_doc_date")!=null)
                                collateralAgreement.setCollateralOfficeRegDate(rsCollateralAgreement.getDate("official_reg_doc_date"));

                            if(rsCollateralAgreement.getString("official_reg_doc_number")!=null)
                                collateralAgreement.setCollateralOfficeRegNumber(rsCollateralAgreement.getString("official_reg_doc_number"));
                            else collateralAgreement.setCollateralOfficeRegNumber("");

                            // agreement loans

                            // items

                            // inspections

                            // releases

                            agreementMap.put((long)rsCollateralAgreement.getInt("id"),collateralAgreement);

                        }
                        else
                        {
//                            errorList.add(" AGREEMENT WITHOUT DEBTOR==>>"+ rsCollateralAgreement.getInt("credit.person_id"));
                        }


                    }
                    catch (Exception ex)
                    {
                        System.out.println(" SCHEDULE  ERROR "+ ex);
                    }
                }
            }


        }
        catch (Exception ex)
        {
            System.out.println(" MAIN ERROR "+ ex);
            return false;
        }

        return true;
    }

    private boolean generateAgreementLoanMap (Connection connection)
    {
        try
        {

            Statement stCollateralAgreementLoan = connection.createStatement();
            ResultSet rsCollateralAgreementLoan = stCollateralAgreementLoan.executeQuery("select * from credit_deposit where credit_deposit.credit_id in (select id from credit)");

            if(rsCollateralAgreementLoan != null)
            {
                while (rsCollateralAgreementLoan.next())
                {
                    try
                    {
                        if(agreementMap.get((long)rsCollateralAgreementLoan.getInt("deposit_id"))!=null)
                        {
                            CollateralAgreement collateralAgreement = agreementMap.get((long)rsCollateralAgreementLoan.getInt("deposit_id"));

                            Long creditId = rsCollateralAgreementLoan.getLong("credit_id");

                            if(creditId>0)
                            {
                                Loan collateralAgreementLoan = loanMap.get(creditId);

                                if(collateralAgreementLoan!=null)
                                    collateralAgreement.getLoans().add(collateralAgreementLoan);
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        System.out.println(" SCHEDULE  ERROR "+ ex);
                    }
                }
            }


        }
        catch (Exception ex)
        {
            System.out.println(" MAIN ERROR "+ ex);
            return false;
        }

        return true;
    }

    private boolean generateItemMap (Connection connection)
    {
        try
        {
            Statement stCollateralItem = connection.createStatement();

            ResultSet rsCollateralItem = stCollateralItem.executeQuery("select * from deposit_goods,deposit_goods_details, deposit_goods_type where " +
                    " deposit_goods.id = deposit_goods_type.deposit_goods_id and " +
                    " deposit_goods.id = deposit_goods_details.deposit_id and " +
                    " deposit_goods.contract_id in (select cd.deposit_id from credit_deposit cd where  cd.credit_id in (select id from credit))");

            if(rsCollateralItem != null)
            {
                while (rsCollateralItem.next())
                {
                    try
                    {
                        if(agreementMap.get((long)rsCollateralItem.getInt("contract_id"))!=null)
                        {
                            CollateralAgreement collateralAgreement = agreementMap.get((long)rsCollateralItem.getInt("contract_id"));

                            Debtor debtor = debtorMap.get(rsCollateralItem.getLong("person_id"));

                            if (debtor==null) {
                                Map.Entry<Long,Debtor> entry = debtorMap.entrySet().iterator().next();
                                debtor = entry.getValue();
                            }

                            Owner owner = debtor.getOwner();

                            CollateralItem collateralItem = new CollateralItem();

                            collateralItem.setCollateralAgreement(collateralAgreement);
                            collateralItem.setVersion((long)rsCollateralItem.getInt("id"));
                            collateralItem.setOwner(owner);

                            if(rsCollateralItem.getString("deposit_name").length()<200)
                                collateralItem.setName(rsCollateralItem.getString("deposit_name"));
                            else collateralItem.setName(rsCollateralItem.getString("deposit_name").substring(0,199));

                            collateralItem.setQuantity(rsCollateralItem.getDouble("quantity"));
                            collateralItem.setCollateralValue(rsCollateralItem.getDouble("cost"));
                            collateralItem.setDescription("");

                            collateralItem.setRisk_rate(rsCollateralItem.getDouble("risk_rate"));
                            collateralItem.setDemand_rate(rsCollateralItem.getDouble("demand_rate"));
                            collateralItem.setEstimatedValue(rsCollateralItem.getDouble("cost")*rsCollateralItem.getDouble("risk_rate")*rsCollateralItem.getDouble("demand_rate"));

                            collateralItem.setItemType(itemTypeMap.get((long)rsCollateralItem.getLong("deposit_type")));
                            collateralItem.setQuantityType(quantityTypeMap.get((long)rsCollateralItem.getLong("quantity_type")));

                            collateralItem.setStatus(rsCollateralItem.getShort("status"));

                            CollateralItemDetails collateralItemDetails = new CollateralItemDetails();
                            collateralItemDetails.setDetails1(rsCollateralItem.getString("goods_details1"));
                            collateralItemDetails.setDetails2(rsCollateralItem.getString("goods_details2"));
                            collateralItemDetails.setDetails3(rsCollateralItem.getString("goods_details3"));
                            collateralItemDetails.setDetails4(rsCollateralItem.getString("goods_details4"));
                            collateralItemDetails.setDetails5(rsCollateralItem.getString("goods_details5"));

                            collateralItemDetails.setDetails6(rsCollateralItem.getString("details")==null?"":rsCollateralItem.getString("details"));
                            collateralItemDetails.setExplDate(rsCollateralItem.getDate("expl_date"));
                            collateralItemDetails.setProdDate(rsCollateralItem.getDate("prod_date"));
                            collateralItemDetails.setDocument(rsCollateralItem.getString("document"));
                            collateralItemDetails.setArrest_by((long)rsCollateralItem.getInt("arested_by"));
                            collateralItemDetails.setIncomplete_reason(rsCollateralItem.getString("incomplete_reason")==null?"":rsCollateralItem.getString("incomplete_reason"));
                            collateralItemDetails.setGoods_type(rsCollateralItem.getString("goods_type"));
                            collateralItemDetails.setGoods_id(rsCollateralItem.getString("goods_id"));
                            collateralItemDetails.setGoods_address(rsCollateralItem.getString("goods_address"));

                            collateralItem.setCollateralItemDetails(collateralItemDetails);

                            collateralAgreement.getCollateralItems().add(collateralItem);

                            itemMap.put((long)rsCollateralItem.getInt("id"), collateralItem);
                        }
                    }
                    catch (Exception ex)
                    {
                        System.out.println(" SCHEDULE  ERROR "+ ex);
                    }
                }
            }


        }
        catch (Exception ex)
        {
            System.out.println(" MAIN ERROR "+ ex);
            return false;
        }

        return true;
    }

    private boolean generateArrestFreeMap (Connection connection)
    {
        try
        {
            Statement stCollateralItemArestFree = connection.createStatement();

            ResultSet rsCollateralItemArestFree =rsCollateralItemArestFree =
                    stCollateralItemArestFree.executeQuery("select * from deposit_release_details dr " +
                            "where dr.deposit_id in ( select id from deposit_goods where contract_id in " +
                            "((select cd.deposit_id from credit_deposit cd where  cd.credit_id in " +
                            "(select id from credit))))");

            if(rsCollateralItemArestFree != null)
            {
                while (rsCollateralItemArestFree.next())
                {
                    try
                    {
                        if(itemMap.get((long)rsCollateralItemArestFree.getInt("deposit_id"))!=null)
                        {
                            CollateralItem item = itemMap.get((long)rsCollateralItemArestFree.getInt("deposit_id"));

                            CollateralItemArrestFree collateralItemArrestFree = new CollateralItemArrestFree();

                            collateralItemArrestFree.setArrestFreeBy(userMap.get(1L).getId());
                            collateralItemArrestFree.setOnDate(rsCollateralItemArestFree.getDate("release_date"));

                            String details = "";

                            if(rsCollateralItemArestFree.getString("release_reason")!=null && rsCollateralItemArestFree.getString("release_reason")!="")
                                details+=rsCollateralItemArestFree.getString("release_reason");

                            if(rsCollateralItemArestFree.getString("release_details")!=null && rsCollateralItemArestFree.getString("release_details")!="")
                                details=details+ " ( "+rsCollateralItemArestFree.getString("release_details")+" )";

                            collateralItemArrestFree.setDetails(details);

                            item.setCollateralItemArrestFree(collateralItemArrestFree);

                            arrestFreeMap.put(rsCollateralItemArestFree.getLong("deposit_id"), collateralItemArrestFree);

                        }
                    }
                    catch (Exception ex)
                    {
                        System.out.println(" SCHEDULE  ERROR "+ ex);
                    }
                }
            }


        }
        catch (Exception ex)
        {
            System.out.println(" MAIN ERROR "+ ex);
            return false;
        }

        return true;
    }


    private boolean generateInspectionMap (Connection connection)
    {
        try
        {

            Statement stCollateralItemInspection = connection.createStatement();
            ResultSet rsCollateralItemInspection =
                    stCollateralItemInspection.executeQuery("select * from deposit_act da " +
                            "where da.deposit_id in " +
                            "( select id from deposit_goods " +
                            "where contract_id in " +
                            "((select cd.deposit_id " +
                            "from credit_deposit cd " +
                            "where  cd.credit_id in " +
                            "(select id from credit))))");

            if(rsCollateralItemInspection != null)
            {
                while (rsCollateralItemInspection.next())
                {
                    try
                    {
                        if(itemMap.get((long)rsCollateralItemInspection.getInt("deposit_id"))!=null)
                        {
                            CollateralItem item = itemMap.get((long)rsCollateralItemInspection.getInt("deposit_id"));

                            CollateralItemInspectionResult collateralItemInspectionResult = new CollateralItemInspectionResult();

                            collateralItemInspectionResult.setCollateralItem(item);
                            if(rsCollateralItemInspection.getDate("date")!=null)
                                collateralItemInspectionResult.setOnDate(rsCollateralItemInspection.getDate("date"));
                            else collateralItemInspectionResult.setOnDate(new Date());

                            collateralItemInspectionResult.setInspectionResultType(inspectionResultTypeMap.get(rsCollateralItemInspection.getLong("status")));
                            collateralItemInspectionResult.setDetails(rsCollateralItemInspection.getString("details"));
                            collateralItemInspectionResult.setVersion(rsCollateralItemInspection.getLong("id"));

                            inspectionMap.put(rsCollateralItemInspection.getLong("id"), collateralItemInspectionResult);
                        }

                    }
                    catch (Exception ex)
                    {
                        System.out.println(" INSPECTION  ERROR "+ ex);
                    }
                }
            }


        }
        catch (Exception ex)
        {
            System.out.println(" MAIN ERROR "+ ex);
            return false;
        }

        return true;
    }

    private boolean debtorMigrate(Connection connection,ResultSet rs,Statement st)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {


            }
            else
            {
                System.out.println("Failed to make connection!");
                errorList.add("debtor migration error: connection");
            }
        }
        catch(Exception ex)
        {
            errorList.add("debtor migration error: connection"+ex);
        }

        return migrationSuccess;
    }

    private boolean collectionPhaseTypeMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {

                    ProcedureType procedureType = new ProcedureType();
                    procedureType.setName("Взыскание задолженности");

                    procedureTypeService.add(procedureType);

                    procedureTypeMap.put((long)1,procedureType);

                    EventStatus eventStatus = new EventStatus();
                    eventStatus.setName("status1");
                    String baseQuesry="";
//                    eventStatusService.add(eventStatus);

                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 36 order by system_type.type_id");
                    HashMap<String,PhaseType> newMap=new HashMap<>();
                    for(PhaseType phaseType:phaseTypeService.list()){
                        newMap.put(phaseType.getName(),phaseType);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            PhaseType phaseType = newMap.get(rs.getString("type_name"));

                            phaseTypeMap.put(rs.getLong("type_id"),phaseType);
                        }

                        migrationSuccess = true;

                    }


                    rs = st.executeQuery("select * from system_type where system_type.group_id = 38 order by system_type.type_id");
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            PhaseStatus phaseStatus = new PhaseStatus();
                            phaseStatus.setName(rs.getString("type_name"));

//                            phaseStatusService.add(phaseStatus);

                            phaseStatusMap.put(rs.getLong("type_id"),phaseStatus);

                        }

                        migrationSuccess = true;

                    }



                    rs = st.executeQuery("select * from system_type where system_type.group_id = 37 order by system_type.type_id");
                    if(rs != null)
                    {
                        while (rs.next())
                        {

                            ProcedureStatus procedureStatus = new ProcedureStatus();
                            procedureStatus.setName(rs.getString("type_name"));
//                            procedureStatusService.add(procedureStatus);

                            procedureStatusMap.put(rs.getLong("type_id"),procedureStatus);

                        }

                        migrationSuccess = true;

                    }



                    rs = st.executeQuery("select * from system_type where system_type.group_id = 44 order by system_type.type_id");
                    if(rs != null)
                    {
                        while (rs.next())
                        {

                            EventType eventType = new EventType();

                            eventType.setName(rs.getString("type_name"));

//                            eventTypeService.add(eventType);
                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }

                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();

                    errorList.add(ex.toString());

                    return false;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean creditOrderMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from credit_order ORDER BY id");
                    HashMap<String,CreditOrder> newMap=new HashMap<>();
                    for(CreditOrder creditOrder:creditOrderService.list()){
                        newMap.put(creditOrder.getRegNumber(),creditOrder);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            CreditOrder creditOrder=null;
                            if(newMap.get(rs.getString("number"))==null) {
                                creditOrder = new CreditOrder();

                                if (rs.getString("number").length() > 100)
                                    creditOrder.setRegNumber(rs.getString("number").substring(0, 100));
                                else creditOrder.setRegNumber(rs.getString("number"));

                                if (rs.getDate("date") != null)
                                    creditOrder.setRegDate(rs.getDate("date"));
                                else creditOrder.setRegDate(new Date());

                                CreditOrderType orderType = this.creditOrderTypeService.getById((long) rs.getInt("type"));
                                if (orderType != null) creditOrder.setCreditOrderType(orderType);
                                else creditOrder.setCreditOrderType(this.creditOrderTypeService.getById((long) 1));

                                CreditOrderState orderState = this.creditOrderStateService.getById((long) rs.getInt("record_status"));
                                if (orderState != null) creditOrder.setCreditOrderState(orderState);

                                this.creditOrderService.add(creditOrder);
                            }
                            else{
                                creditOrder=newMap.get(rs.getString("number"));

                            }
                            crditOrderMap.put(rs.getLong("id"), creditOrder);

                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    errorList.add(ex.toString());
                    return false;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
            errorList.add(ex.toString());
            return false;

        }

        return migrationSuccess;
    }

    private boolean currencyRateMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from currency_rates ORDER BY id");
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            CurrencyRate currencyRate = new CurrencyRate();

                            if(rs.getDate("date")!=null)
                                currencyRate.setDate(rs.getDate("date"));
//                            else errorList.add(" currency with no date"+rs.getLong("id"));


                            OrderTermCurrency currencyType = currencyMap.get((long)rs.getInt("currency_type"));



                            if(currencyType!=null) currencyRate.setCurrency(currencyType);

                            currencyRate.setRate(rs.getDouble("exchange_rate"));
                            currencyRate.setStatus(rs.getShort("record_status"));

//                            this.currencyRateService.create(currencyRate);

                        }

                        migrationSuccess = true;

                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    errorList.add(ex.toString());
                    return false;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in currency rate migration "+ex);
            errorList.add(ex.toString());
            return false;

        }

        return migrationSuccess;
    }

    private boolean floatingRateMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from percent_rates ORDER BY id");
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            FloatingRate floatingRate = new FloatingRate();

                            if(rs.getDate("date")!=null)
                                floatingRate.setDate(rs.getDate("date"));
//                            else errorList.add(" rate with no date"+rs.getLong("id"));


                            OrderTermFloatingRateType rateType = rateTypeMap.get((long)rs.getInt("type"));



                            if(rateType!=null) floatingRate.setRateType(rateType);

                            floatingRate.setRate(rs.getDouble("rate"));
                            floatingRate.setStatus(rs.getShort("record_status"));

//                            this.floatingRateService.create(floatingRate);

                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    errorList.add(ex.toString());
                    return false;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in currency rate migration "+ex);
            errorList.add(ex.toString());
            return false;

        }

        return migrationSuccess;
    }


    private boolean workSectorMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 24 order by system_type.type_id");
                    HashMap<String,WorkSector> newMap=new HashMap<>();
                    for(WorkSector workSector:workSectorService.list()){
                        newMap.put(workSector.getName(),workSector);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            WorkSector newEntity = newMap.get(rs.getString("type_name"));

                            workSectorMap.put(rs.getLong("type_id"),newEntity);
                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean loanStatusMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 23 order by system_type.type_id");
                    HashMap<String,LoanState> newMap=new HashMap<>();
                    for(LoanState loanState:loanStateService.list()){
                        newMap.put(loanState.getName(),loanState);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            LoanState newEntity = newMap.get(rs.getString("type_name"));

                            loanStateMap.put(rs.getLong("type_id"),newEntity);

                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }


                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean inspectionResultTypeMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 42 order by system_type.type_id");
                    HashMap<String,InspectionResultType> newMap=new HashMap<>();
                    for(InspectionResultType inspectionResultType:inspectionResultTypeService.list()){
                        newMap.put(inspectionResultType.getName(),inspectionResultType);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            InspectionResultType newEntity = newMap.get(rs.getString("type_name"));

                            inspectionResultTypeMap.put(rs.getLong("type_id"),newEntity);
                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean paymentTypeMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 41 order by system_type.type_id");
                    HashMap<String,PaymentType> newMap=new HashMap<>();
                    for(PaymentType paymentType:paymentTypeService.list()){
                        newMap.put(paymentType.getName(),paymentType);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            PaymentType newEntity = newMap.get(rs.getString("type_name"));

                            paymentTypeMap.put(rs.getLong("type_id"),newEntity);
                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean quantityTypeMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 22 order by system_type.type_id");
                    HashMap<String,QuantityType> newMap=new HashMap<>();
                    for(QuantityType quantityType:quantityTypeService.list()){
                        newMap.put(quantityType.getName(),quantityType);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            QuantityType newEntity = newMap.get(rs.getString("type_name"));

                            quantityTypeMap.put(rs.getLong("type_id"),newEntity);
                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean goodsTypeMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from goods_type");
                    HashMap<String,GoodType> newMap=new HashMap<>();
                    for(GoodType goodType:goodTypeService.list()){
                        newMap.put(goodType.getName(),goodType);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            GoodType goodType = newMap.get(rs.getString("code"));

                            goodTypeMap.put(rs.getLong("id"),goodType);

                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in goodType migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean itemTypeMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 17 order by system_type.type_id");
                    HashMap<String,ItemType> newMap=new HashMap<>();
                    for(ItemType itemType:itemTypeService.list()){
                        newMap.put(itemType.getName(),itemType);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            ItemType newEntity = newMap.get(rs.getString("type_name"));

                            itemTypeMap.put(rs.getLong("type_id"),newEntity);
                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean loanTypeMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 16 order by system_type.type_id");
                    HashMap<String,LoanType> newMap=new HashMap<>();
                    for(LoanType loanType:loanTypeService.list()){
                        newMap.put(loanType.getName(),loanType);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            LoanType newEntity = newMap.get(rs.getString("type_name"));

                            loanTypeMap.put(rs.getLong("type_id"),newEntity);
                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean debtorTypeMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 15 order by system_type.type_id");
                    HashMap<String,DebtorType> newMap=new HashMap<>();
                    for(DebtorType debtorType:debtorTypeService.list()){
                        newMap.put(debtorType.getName(),debtorType);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            DebtorType newEntity = newMap.get(rs.getString("type_name"));

                            debtorTypeMap.put(rs.getLong("type_id"),newEntity);
                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean rateTypeMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 32 order by system_type.type_id");
                    HashMap<String,OrderTermFloatingRateType> newMap=new HashMap<>();
                    for(OrderTermFloatingRateType orderTermFloatingRateType:orderTermFloatingRateTypeService.list()){
                        newMap.put(orderTermFloatingRateType.getName(),orderTermFloatingRateType);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            OrderTermFloatingRateType newEntity = newMap.get(rs.getString("type_name"));

                            rateTypeMap.put(rs.getLong("type_id"),newEntity);
                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean daysCalcMethodMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 31 order by system_type.type_id");
                    HashMap<String,OrderTermDaysMethod> newMap=new HashMap<>();
                    for(OrderTermDaysMethod orderTermDaysMethod:orderTermDaysMethodService.list()){
                        newMap.put(orderTermDaysMethod.getName(),orderTermDaysMethod);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            OrderTermDaysMethod newEntity = newMap.get(rs.getString("type_name"));

                            daysMethodMap.put(rs.getLong("type_id"),newEntity);
                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }

                    OrderTermDaysMethod newEntity = new OrderTermDaysMethod();


                    newEntity.setName(" Календарный 366");

//                    orderTermDaysMethodService.add(newEntity);
                    daysMethodMap.put(Long.valueOf(3),newEntity);


                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean loanCurrencyMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 25 order by system_type.type_id");
                    HashMap<String,OrderTermCurrency> newMap=new HashMap<>();
                    for(OrderTermCurrency orderTermCurrency:orderTermCurrencyService.list()){
                        newMap.put(orderTermCurrency.getName(),orderTermCurrency);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            OrderTermCurrency newEntity = newMap.get(rs.getString("type_name"));

                            currencyMap.put(rs.getLong("type_id"),newEntity);
                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean loanFundMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 47 order by system_type.type_id");
                    HashMap<String,OrderTermFund> newMap=new HashMap<>();
                    for(OrderTermFund orderTermFund:orderTermFundService.list()){
                        newMap.put(orderTermFund.getName(),orderTermFund);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            OrderTermFund newEntity = newMap.get(rs.getString("type_name"));

                            fundMap.put(rs.getLong("type_id"),newEntity );
                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean orderTypeMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 27 order by system_type.type_id");
                    HashMap<String,CreditOrderType> newMap=new HashMap<>();
                    for(CreditOrderType creditOrderType:creditOrderTypeService.list()){
                        newMap.put(creditOrderType.getName(),creditOrderType);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            CreditOrderType newEntity = newMap.get(rs.getString("type_name"));

                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean orderStateMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 4 order by system_type.type_id");
                    HashMap<String,CreditOrderState> newMap=new HashMap<>();
                    for(CreditOrderState creditOrderState:creditOrderStateService.list()){
                        newMap.put(creditOrderState.getName(),creditOrderState);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            CreditOrderState newEntity = newMap.get(rs.getString("type_name"));

                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean migrateUsers(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("SELECT *,\n" +
                            "  (SELECT system_type.type_name FROM system_type WHERE system_type.group_id = 11 AND system_type.type_id = users.type) AS position_name,\n" +
                            "  (SELECT phone.number FROM phone WHERE phone.user_id = users.id AND phone.contact_type = 1) AS phone_number\n" +
                            "FROM users,profile,address WHERE  users.id = profile.user_id AND address.contact_type = 1 AND address.user_id = users.id\n" +
                            "ORDER BY users.id");
                    HashMap<String,User> newMap=new HashMap<>();
                    for(User user:userService.findAll()){
                        newMap.put(user.getUsername(),user);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            User user = newMap.get(rs.getString("login"));

//                            if(user.isEnabled())
//                            {
//                                Staff staff = staffService.findById(user.getStaff().getId());
//
//                                if(staff.getPosition()==null)
//                                    staff.setPosition(this.positionService.findById(1));
//
//                                Person person = personService.findById(staff.getPerson().getId());
//
//                            }


                            userMap.put(rs.getLong("id"),user);
                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    errorList.add("user migration error: query error"+ex);
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
            errorList.add("user migration error: query error"+ex);
        }

        return migrationSuccess;
    }

    private boolean positionMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("SELECT distinct \n" +
                            "  (SELECT system_type.type_name FROM system_type WHERE system_type.group_id = 10 AND system_type.type_id = users.department) AS department_name,\n" +
                            "  (SELECT system_type.type_name FROM system_type WHERE system_type.group_id = 11 AND system_type.type_id = users.type) AS position_name,\n" +
                            "  users.department\n" +
                            "FROM users where department<>20 ORDER BY department_name,position_name");
                    HashMap<String,Position> newMap=new HashMap<>();
                    for(Position position:positionService.findAll()){
                        newMap.put(position.getName(),position);
                    }
                    if(rs != null)
                    {
                        while (rs.next() )
                        {
                            if(rs.getInt("department")!=20)
                            {
                                Position position = newMap.get(positionService.findByDepartment(departmentService.findById((long) rs.getInt("department"))));

                            }
                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    errorList.add("position migration error: query error"+ex);
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
            errorList.add("position migration error: query error"+ex);
        }

        return migrationSuccess;
    }

    private boolean departmentMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 10 order by system_type.type_id");
                    HashMap<String,Department> newMap=new HashMap<>();
                    for(Department department:departmentService.findAll()){
                        newMap.put(department.getName(),department);
                    }
                    if(rs != null)
                    {
                        Organization gaubk = organizationService.findById((long)1);

                        while (rs.next())
                        {
                            Department department = newMap.get(rs.getString("type_name"));

                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    errorList.add("department migration error: query error"+ex);
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
                errorList.add("department migration error: query error");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
            errorList.add("department migration error: query error"+ex);
        }

        return migrationSuccess;
    }

    private boolean gaubkMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Organization gaubk = new Organization();

                    Contact contact = new Contact();
                    contact.setName("0312 666033");

                    Address address = new Address();
                    address.setRegion(this.regionService.findById((long)2)); // Bishkek
                    address.setDistrict(this.districtService.findById(48)); // Pervomayskiy raion
                    address.setLine("б.Эркиндик 58-а");

                    IdentityDoc identityDoc = new IdentityDoc();
                    identityDoc.setIdentityDocGivenBy(identityDocGivenByService.findById(2)); //MinJust
                    identityDoc.setIdentityDocType(identityDocTypeService.findById(1)); // Svid. o reg.
                    identityDoc.setEnabled(true);
                    identityDoc.setName(" данные ГАУБК");
                    identityDoc.setPin("inn");
                    identityDoc.setNumber("number");

                    IdentityDocDetails identityDocDetails = new IdentityDocDetails();
                    identityDocDetails.setFirstname("");
                    identityDocDetails.setLastname("");
                    identityDocDetails.setLastname("");
                    identityDocDetails.setFullname("");
                    identityDocDetails.setMidname("");

                    identityDoc.setIdentityDocDetails(identityDocDetails);



                    gaubk.setName("ГАУБК при МФ КР");
                    gaubk.setDescription("Государственное агентство по управлению бюджетными кредитами при МФ КР");
                    gaubk.setEnabled(true);
                    gaubk.setContact(contact);
                    gaubk.setAddress(address);
                    gaubk.setIdentityDoc(identityDoc);
                    gaubk.setOrgForm(this.orgFormService.findById(1)); // Jur

                    try
                    {

                        Set<BankData> bankDatas = new HashSet<>();

                        Statement st = connection.createStatement();
                        rs = st.executeQuery("select * from requisites");
                        if(rs != null)
                        {
                            while (rs.next())
                            {
                            }

                            rs.close();
                            st.close();
                        }


                        if(bankDatas.size()>0)
                            gaubk.setBankData(bankDatas);
                    }
                    catch (SQLException ex)
                    {
                        System.out.println(ex);
                    }


//                    this.organizationService.create(gaubk);
                    migrationSuccess = true;
                }
                catch (Exception ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    errorList.add("gaubk migration error: query error"+ex);
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
            errorList.add("gaubk migration error: query error"+ex);
        }

        return migrationSuccess;
    }

    private boolean idDocGivenByMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    IdentityDocGivenBy identityDocGivenBy1 = identityDocGivenByService.findById(1);


                    identityDocGivenByMap.put((long)1,identityDocGivenBy1);


                    IdentityDocGivenBy identityDocGivenBy2 = identityDocGivenByService.findById(1);


                    identityDocGivenByMap.put((long)2,identityDocGivenBy2);

                    migrationSuccess = true;
                }
                catch (Exception ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean idDocTypeMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {

                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 18 order by system_type.type_id");
                    HashMap<String,IdentityDocType> newMap=new HashMap<>();
                    for(IdentityDocType identityDocType:identityDocTypeService.findAll()){
                        newMap.put(identityDocType.getName(),identityDocType);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            IdentityDocType identityDocType = newMap.get(rs.getString("type_name"));


                            identityDocTypeMap.put(rs.getLong("type_id"),identityDocType);

                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean villageMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select  selo.*,\n" +
                            "      (select id from aokmotu where aokmotu.district = selo.district and selo.aokmotu = aokmotu.aokmotu and selo.region = aokmotu.region limit 1) as aokmotu_id\n" +
                            "from selo order by id");
                    HashMap<String,Village> newVillageMap=new HashMap<>();
                    for(Village village:villageService.findAll()){
                        newVillageMap.put(village.getName(),village);
                    }
                    if(rs != null)
                    {
                        int count =0;
                        while (rs.next())
                        {
                            count++;
                            try
                            {
                                Village village  = newVillageMap.get(rs.getString("title"));

                                Aokmotu aokmotu = new Aokmotu();

                                if(rs.getLong("aokmotu_id")>0)
                                {
                                    aokmotu = aokmotuMap.get(rs.getLong("aokmotu_id"));
                                }
                                else aokmotu = aokmotuMap.get((long)1);

                                village.setAokmotu(aokmotu);

//                                villageService.create(village);

                                if(village!=null)
                                villageMap.put(rs.getLong("id"),village);

                            }
                            catch (Exception ex)
                            {
                                System.out.println(ex);
                                System.out.println(count);
                            }
                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean aokmotuMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select\n" +
                            "  *,\n" +
                            "  (\n" +
                            "    SELECT DISTINCT district_codes.district_id from district_codes\n" +
                            "    where district_codes.district_code = aokmotu.district AND\n" +
                            "        district_codes.region_code = aokmotu.region limit 1\n" +
                            "     ) as district_id\n" +
                            "from aokmotu order by id");
                    HashMap<String,Aokmotu> newAokmotuMap=new HashMap<>();
                    for(Aokmotu aokmotu:aokmotuService.findAll()){
                        newAokmotuMap.put(aokmotu.getName(),aokmotu);
                    }
                    if(rs != null)
                    {
                        long counter=0;
                        while (rs.next())
                        {
                            counter++;

                            Aokmotu aokmotu = newAokmotuMap.get(rs.getString("title"));

                            District district = districtMap.get(rs.getLong("district_id"));
//                            if(rs.getInt("district_id")==11) district = districtMap.get((long)63);
                            if(rs.getInt("district_id")==15) district = districtMap.get((long)30);
                            if(rs.getInt("district_id")==47) district = districtMap.get((long)36);
                            if(rs.getInt("district_id")==18) district = districtMap.get((long)35);
                            if(rs.getInt("district_id")==19) district = districtMap.get((long)52);




                            if(aokmotu!=null)
                            {
                                aokmotu.setDistrict(district);
                                aokmotuMap.put(rs.getLong("id"),aokmotu);
                            }


                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean districtMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 13 order by system_type.type_id");
                    HashMap<String,District> newDistrictMap=new HashMap<>();
                    for(District district:districtService.findAll()){
                        newDistrictMap.put(district.getName(),district);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            District district = newDistrictMap.get(rs.getString("type_name"));


                            if(rs.getInt("details_id")>0 && rs.getInt("details_id")<10)
                            {
                                district.setRegion(regionMap.get(rs.getLong("details_id")));
                            }
                            else
                            {
                                district.setRegion(regionMap.get((long)2));
                            }

                            if(rs.getInt("details_id")==0 )
                            {
                                district.setRegion(regionMap.get((long)9));
                            }

//                            districtService.create(district);

                            if (district!=null)
                            districtMap.put(rs.getLong("type_id"),district);
                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
        }

        return migrationSuccess;
    }

    private boolean regionMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null)
            {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 12 order by system_type.type_id");
                    HashMap<String,Region> newRegionMap=new HashMap<>();
                    for(Region region:regionService.findAll()){
                        newRegionMap.put(region.getName(),region);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            Region region = newRegionMap.get(rs.getString("type_name"));

                            if(region!=null)
                            regionMap.put(rs.getLong("type_id"),region);
                        }

                        migrationSuccess = true;
                        rs.close();
                        st.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();

                    errorList.add("region migration error: query"+ex);
                    return false;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
                errorList.add("region migration error: connection");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in User migration "+ex);
            errorList.add("region migration error: connection"+ex);
        }

        return migrationSuccess;
    }

    private boolean orgFormMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null)
            {
                ResultSet rs = null;

                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 20 order by system_type.type_id");
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            OrgForm orgForm = new OrgForm();

                            orgForm.setName(rs.getString("type_name"));
                            orgForm.setEnabled(rs.getShort("status")==1 ? true : false);

//                            orgFormService.create(orgForm);

                            organizationFormMap.put(rs.getLong("type_id"),orgForm);
                        }

                        migrationSuccess = true;
                    }
                }
                catch (SQLException ex)
                {
                    errorList.add("orgFrom migration error: query error"+ex);
                    return migrationSuccess;
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
                errorList.add("orgFrom migration error: no connection");
            }
        }
        catch(Exception ex)
        {
            System.out.println(" Error in OrgForm migration "+ex);
            errorList.add("orgFrom migration error"+ex);
        }

        return migrationSuccess;
    }


}
