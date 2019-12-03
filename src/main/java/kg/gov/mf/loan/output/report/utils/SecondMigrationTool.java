package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.admin.org.model.*;
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
import kg.gov.mf.loan.manage.repository.loan.CreditTermRepository;
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

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SecondMigrationTool {

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

    //endregion


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

    public void loanMigrate(Long oldLoansId,String ip,String database,String username,String password){
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        Connection connection = this.getSourceConnection(ip,database,username,password);
        boolean orgFormMigrationSuccess = false;

        boolean done = false;

        boolean inProcess = false;

        boolean light = false;

        // DONE

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
            ResultSet creditsResultSet=st.executeQuery("select credit.person_id as id from credit where credit.id="+oldLoansId);
            int oldPersonId=0;
            while (creditsResultSet.next())
            {
                oldPersonId=creditsResultSet.getInt("id");
            }
            ResultSet rs = st.executeQuery("select (select aokmotu.id from aokmotu where aokmotu.region =  address.region_code and aokmotu.district = address.district_code limit 1) as aokmotu_id,\n" +
                    "      (select selo.id from selo where selo.region = address.region_code and selo.district = address.district_code limit 1) as selo_id,\n" +
                    "\n" +
                    "      * from person, person_details,address,phone\n" +
                    "where person.id = person_details.person_id AND \n" +
                    "      address.user_id = person.id AND address.contact_type = 2 AND \n" +
                    "      phone.user_id = person.id and phone.contact_type = 2 and person.id="+oldPersonId+" order by person.id ");
            debtorMigrate(connection,rs,st);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(orgFormMigrationSuccess) System.out.println(" ORG FORM MIGRATION SUCCESS");

        for (String text: errorList)
        {
            System.out.println(text);
        }

    }

    private boolean debtorMigrate(Connection connection,ResultSet rs,Statement st)
    {
        boolean migrationSuccess = false;

        OrganizationForm organizationForm = this.organizationFormService.getById((long)1);
        OrganizationForm organizationForm2 = this.organizationFormService.getById((long)2);

        InstallmentState installmentState = this.installmentStateService.getById((long)1);

        OrderTermTransactionOrder transactionOrder1 = this.orderTermTransactionOrderService.getById((long)1);
        OrderTermTransactionOrder transactionOrder3 = this.orderTermTransactionOrderService.getById((long)1);

        OrderTermRatePeriod ratePeriod = this.orderTermRatePeriodService.getById((long)1);

        try
        {
            if (connection != null) {
                try
                {
                    if(rs != null)
                    {
                        int counter=0;
                        while (rs.next())
                        {
                            counter++;



                            boolean isPerson = true;

                            //address
                            Address address = new Address();

                            Region region = new Region();

                            if(rs.getInt("region")==0) errorList.add(" debtor region error "+rs.getInt("person_id"));
                            else region = regionMap.get((long)rs.getInt("region"));

                            address.setRegion(region);

                            District district = new District();

                            if(rs.getInt("district")==0)
                                errorList.add(" debtor district error "+rs.getInt("person_id"));
                            else
                                district = districtMap.get((long)rs.getInt("district"));

                            address.setDistrict(district);

                            Aokmotu aokmotu = new Aokmotu();
                            if(rs.getInt("aokmotu_id")>0)
                            {
                                aokmotu = aokmotuMap.get((long)rs.getInt("aokmotu_id"));
                                address.setAokmotu(aokmotu);
                            }
                            else
                            {
                                aokmotu = aokmotuMap.get((long)1);
                                address.setAokmotu(aokmotu);
                            }


                            Village village = new Village();
                            if(rs.getInt("selo_id")>0)
                            {
                                village = villageMap.get((long)rs.getInt("selo_id"));
                                address.setVillage(village);
                            }
                            else
                            {
                                village = villageMap.get((long)1);
                                address.setVillage(village);
                            }

                            address.setLine(rs.getString("address_line1"));

//                            AddressDetails addressDetails = new AddressDetails();

                            //                          addressDetails.setName(String.valueOf(rs.getLong("person_id")));

//                            address.setAddressDetails(addressDetails);

                            //contact
                            // contact
                            //contact

                            Contact contact = new Contact();

                            if(!(rs.getString("number")=="" || rs.getString("number")==null))
                                contact.setName(rs.getString("number"));

                            if(!(rs.getString("mobile")=="" || rs.getString("mobile")==null))
                                contact.setName(contact.getName()+", "+rs.getString("mobile"));

                            // id doc
                            IdentityDoc identityDoc = new IdentityDoc();

                            if(rs.getShort("type")==2) isPerson =false; // isOrganization

                            if(rs.getShort("document_type")==1)
                            {
                                identityDoc.setIdentityDocGivenBy(identityDocGivenByMap.get((long)1)); //MKK
                            }
                            else
                            {
                                identityDoc.setIdentityDocGivenBy(identityDocGivenByMap.get((long)2)); //MinJust
                                isPerson = false;
                            }

                            if(rs.getString("title").contains("ов")||
                                    rs.getString("title").contains("ова")||
                                    rs.getString("title").contains("ев")||
                                    rs.getString("title").contains("ева")||
                                    rs.getString("title").contains("уулу")||
                                    rs.getString("title").contains("кызы")
                            )
                            {
                                isPerson = true;
                            }

                            if(rs.getString("title").contains("\"")) isPerson = false;

                            identityDoc.setIdentityDocType(identityDocTypeMap.get(rs.getLong("document_type"))); // Passport


                            identityDoc.setEnabled(true);

                            if(rs.getDate("of_reg_date")!=null)
                                identityDoc.setDate(rs.getDate("of_reg_date"));
                            else identityDoc.setDate(new java.util.Date());

                            if(rs.getString("of_reg_id")!=null)
                                identityDoc.setPin(rs.getString("of_reg_id"));
                            else identityDoc.setPin("-");

                            identityDoc.setNumber(rs.getString("of_reg_series")+rs.getString("of_reg_number"));

                            identityDoc.setName(identityDoc.getIdentityDocType().getName() + " (" +rs.getString("of_reg_let")+")");

                            IdentityDocDetails identityDocDetails = new IdentityDocDetails();

                            identityDocDetails.setFirstname(rs.getString("title"));
                            identityDocDetails.setLastname(rs.getString("title"));
                            identityDocDetails.setFullname(rs.getString("title"));
                            identityDocDetails.setMidname(rs.getString("title"));

                            identityDoc.setIdentityDocDetails(identityDocDetails);

                            // person and organization

                            Person person = new Person();
                            Organization organization = new Organization();

                            if(isPerson)
                            {

                                person.setName(rs.getString("title"));
                                person.setEnabled(true);
                                person.setAddress(address);
                                person.setContact(contact);
                                person.setIdentityDoc(identityDoc);

                                if(rs.getString("details")!=null) person.setDescription(rs.getString("details"));
                                person.setVersion(rs.getLong("person_id"));


                                try
                                {
                                    this.personService.create(person);
                                }
                                catch( Exception ex)
                                {
                                    System.out.println(ex+person.getName());
                                }




                            }
                            else
                            {


                                organization.setName(rs.getString("title"));
                                organization.setAddress(address);
                                organization.setContact(contact);
                                organization.setIdentityDoc(identityDoc);
                                organization.setOrgForm(organizationFormMap.get((long)2));
                                organization.setEnabled(true);

                                if(rs.getString("details")!=null) organization.setDescription(rs.getString("details"));

                                organization.setVersion(rs.getLong("person_id"));

                                Department chief = new Department();
                                chief.setOrganization(organization);
                                chief.setName("Руководство");
                                chief.setDescription("");
                                chief.setEnabled(true);


                                Position responsible = new Position();
                                responsible.setName("Руководитель");
                                responsible.setDepartment(chief);

                                Position accountant = new Position();
                                accountant.setName("Гл. бухгалтер");
                                accountant.setDepartment(chief);

                                Set<Position> positions = new HashSet<Position>();
                                positions.add(responsible);
                                positions.add(accountant);

                                chief.setPosition(positions);

                                Set<Department> departments = new HashSet<Department>();
                                departments.add(chief);
                                organization.setDepartment(departments);


                                try
                                {
                                    this.organizationService.create(organization);
                                }
                                catch( Exception ex)
                                {
                                    System.out.println(ex+organization.getName());

                                }



//                                Responsible and Accountant Migration
                                try {
                                    System.out.println(organization.getId());

                                    String responsibleName = "";
                                    String accountantName = "";

                                    if(rs.getString("responsible")!=null) responsibleName = rs.getString("responsible");
                                    if(rs.getString("accountant")!=null) accountantName = rs.getString("accountant");

                                    Address addressOrganization = organization.getAddress();

                                    Address addressResponsible = new Address();
                                    addressResponsible.setRegion(addressOrganization.getRegion());
                                    addressResponsible.setDistrict(addressOrganization.getDistrict());
                                    addressResponsible.setVillage(addressOrganization.getVillage());
                                    addressResponsible.setAokmotu(addressOrganization.getAokmotu());
                                    addressResponsible.setLine(addressOrganization.getLine());

                                    Contact contactResponsible = new Contact();

                                    contactResponsible.setName("");

                                    IdentityDoc identityDocResponsible = new IdentityDoc();

                                    identityDocResponsible.setIdentityDocGivenBy(identityDocGivenByMap.get((long)1)); //MKK
                                    identityDocResponsible.setIdentityDocType(identityDocTypeMap.get(rs.getLong("document_type"))); // Passport
                                    identityDocResponsible.setEnabled(true);
                                    identityDocResponsible.setDate(new java.util.Date());
                                    identityDocResponsible.setPin("-");
                                    identityDocResponsible.setNumber("");
                                    identityDocResponsible.setName("");

                                    IdentityDocDetails identityDocDetailsResp = new IdentityDocDetails();

                                    identityDocDetailsResp.setFirstname(responsibleName);
                                    identityDocDetailsResp.setLastname(responsibleName);
                                    identityDocDetailsResp.setFullname(responsibleName);
                                    identityDocDetailsResp.setMidname(responsibleName);

                                    identityDocResponsible.setIdentityDocDetails(identityDocDetailsResp);


                                    Person personResponsible = new Person();

                                    personResponsible.setName(responsibleName);
                                    personResponsible.setEnabled(true);

                                    personResponsible.setAddress(addressResponsible);
                                    personResponsible.setContact(contactResponsible);
                                    personResponsible.setIdentityDoc(identityDocResponsible);

                                    this.personService.create(personResponsible);

                                    EmploymentHistory employmentHistoryResponsible = new EmploymentHistory();

                                    Staff chiefStaffResponsible = new Staff();

                                    chiefStaffResponsible.setOrganization(organization);
                                    chiefStaffResponsible.setDepartment(chief);
                                    chiefStaffResponsible.setPosition(responsible);
                                    chiefStaffResponsible.setEnabled(true);
                                    chiefStaffResponsible.setName(personResponsible.getName());
                                    chiefStaffResponsible.setEmploymentHistory(employmentHistoryResponsible);
                                    chiefStaffResponsible.setPerson(personResponsible);

                                    this.staffService.create(chiefStaffResponsible);












                                    Address addressAccountant = new Address();
                                    addressAccountant.setRegion(addressOrganization.getRegion());
                                    addressAccountant.setDistrict(addressOrganization.getDistrict());
                                    addressAccountant.setVillage(addressOrganization.getVillage());
                                    addressAccountant.setAokmotu(addressOrganization.getAokmotu());
                                    addressAccountant.setLine(addressOrganization.getLine());

                                    Contact contactAccountant = new Contact();

                                    contactAccountant.setName("");

                                    IdentityDoc identityDocAccountant = new IdentityDoc();

                                    identityDocAccountant.setIdentityDocGivenBy(identityDocGivenByMap.get((long)1)); //MKK
                                    identityDocAccountant.setIdentityDocType(identityDocTypeMap.get(rs.getLong("document_type"))); // Passport
                                    identityDocAccountant.setEnabled(true);
                                    identityDocAccountant.setDate(new java.util.Date());
                                    identityDocAccountant.setPin("-");
                                    identityDocAccountant.setNumber("");
                                    identityDocAccountant.setName("");

                                    IdentityDocDetails identityDocDetailsAcc = new IdentityDocDetails();

                                    identityDocDetailsAcc.setFirstname(accountantName);
                                    identityDocDetailsAcc.setLastname(accountantName);
                                    identityDocDetailsAcc.setFullname(accountantName);
                                    identityDocDetailsAcc.setMidname(accountantName);

                                    identityDocAccountant.setIdentityDocDetails(identityDocDetailsAcc);


                                    Person personAccountant = new Person();

                                    personAccountant.setName(accountantName);
                                    personAccountant.setEnabled(true);

                                    personAccountant.setAddress(addressAccountant);
                                    personAccountant.setContact(contactAccountant);
                                    personAccountant.setIdentityDoc(identityDocAccountant);

                                    this.personService.create(personAccountant);

                                    EmploymentHistory employmentHistoryAccountant= new EmploymentHistory();

                                    Staff chiefStaffAccountant = new Staff();

                                    chiefStaffAccountant.setOrganization(organization);
                                    chiefStaffAccountant.setDepartment(chief);
                                    chiefStaffAccountant.setPosition(accountant);
                                    chiefStaffAccountant.setEnabled(true);
                                    chiefStaffAccountant.setName(personAccountant.getName());
                                    chiefStaffAccountant.setEmploymentHistory(employmentHistoryAccountant);
                                    chiefStaffAccountant.setPerson(personAccountant);

                                    this.staffService.create(chiefStaffAccountant);

                                }
                                catch (Exception ex)
                                {
                                    errorList.add(" ogranization responsible and accountant error == "+organization.getName() + " == "+ ex);
                                }





                            }
                            // owner
                            Owner owner = new Owner();
                            if(isPerson)
                            {
                                owner.setName(person.getName());
                                owner.setOwnerType(OwnerType.PERSON);
                                owner.setEntityId(person.getId());
                                owner.setAddress(address);


                            }
                            else
                            {
                                owner.setName(organization.getName());
                                owner.setOwnerType(OwnerType.ORGANIZATION);
                                owner.setEntityId(organization.getId());
                                owner.setAddress(address);
                            }

                            this.ownerService.add(owner);

                            //debtor
                            Debtor debtor = new Debtor();

                            debtor.setName(owner.getName());
                            debtor.setOwner(owner);

                            if(rs.getInt("fin_status")>0)
                            {
                                debtor.setDebtorType(debtorTypeMap.get((long)rs.getInt("fin_status")));
                            }
                            else
                            {
                                debtor.setDebtorType(debtorTypeMap.get((long)1));
                            }








                            if(isPerson)
                            {
                                debtor.setOrgForm(organizationForm2);
                                debtor.setVersion(person.getVersion());
                            }
                            else
                            {
                                debtor.setOrgForm(organizationForm);
                                debtor.setVersion(organization.getVersion());
                            }


                            debtor.setWorkSector(workSectorMap.get((long)rs.getInt("work_sector")));

                            debtor.setRecord_status(1);
                            debtor.setAddress_id(address.getId());

                            this.debtorService.add(debtor);

                            debtorMap.put(rs.getLong("person_id"),debtor);


                            Map<Long,Loan> debtorLoans = new HashMap<Long,Loan>();

                            boolean debtorHasSubloan = false;

                            Map<Long,Loan> loansToBeLinked = new HashMap<>();

                            try
                            {
                                if (connection != null) {
                                    ResultSet rsLoan = null;
                                    try
                                    {
                                        Statement stLoan = connection.createStatement();
                                        rsLoan = stLoan.executeQuery("select * from credit,credit_details where credit.id = credit_details.credit_id and credit.person_id = "+rs.getInt("person_id")+ " order by id");
                                        if(rsLoan != null)
                                        {
                                            while (rsLoan.next())
                                            {
                                                // loan
                                                Loan loan = new NormalLoan();

                                                Loan loanCopy = new NormalLoan();

                                                boolean reconstructed = false;

                                                boolean tranchee = false;

                                                String details = rsLoan.getString("details");

                                                if(details.contains("(=reconstructed=)"))
                                                {
                                                    reconstructed = true;
                                                    debtorHasSubloan = true;

                                                    loan = new RestructuredLoan();

                                                    Set<Loan> children = new HashSet<>();

                                                    loanCopy.setAmount(rsLoan.getDouble("cost"));
                                                    loanCopy.setCreditOrder(crditOrderMap.get((long)rsLoan.getInt("credit_order_id")));
                                                    loanCopy.setSupervisorId(userMap.get(rsLoan.getLong("curator")).getId());
                                                    loanCopy.setLoanType(loanTypeMap.get((long)rsLoan.getInt("credit_type")));
                                                    loanCopy.setCurrency(currencyMap.get((long)rsLoan.getInt("currency")));
                                                    loanCopy.setRegDate(rsLoan.getDate("date"));
                                                    loanCopy.setRegNumber(rsLoan.getString("number"));
                                                    loanCopy.setDebtor(debtor);
                                                    loanCopy.setLoanState(loanStateMap.get((long)1));

                                                    children.add(loanCopy);

                                                    loan.setChildren(children);

                                                    loanCopy.setParent(loan);


                                                    ResultSet rsReconstructedList = null;
                                                    Statement stReconstructedList = connection.createStatement();
                                                    rsReconstructedList = stReconstructedList.executeQuery("select * from reconstructed_list where reconstructed_list.credit_id_active <> reconstructed_list.credit_id_cancelled and credit_id_active = "+ rsLoan.getInt("credit_id") +" order by date desc limit 1");
                                                    if(rsReconstructedList != null) {
                                                        if(rsReconstructedList.next())
                                                        {
                                                            loansToBeLinked.put(Long.valueOf(rsReconstructedList.getLong("credit_id_cancelled")),loan);
                                                        }

                                                    }



                                                }
                                                else if(details.contains("id="))
                                                {
                                                    tranchee = true;
                                                    debtorHasSubloan = true;

                                                    loan = new TrancheeLoan();

                                                    Set<Loan> children = new HashSet<>();

                                                    loanCopy.setAmount(rsLoan.getDouble("cost"));
                                                    loanCopy.setCreditOrder(crditOrderMap.get((long)rsLoan.getInt("credit_order_id")));
                                                    loanCopy.setSupervisorId(userMap.get(rsLoan.getLong("curator")).getId());
                                                    loanCopy.setLoanType(loanTypeMap.get((long)rsLoan.getInt("credit_type")));
                                                    loanCopy.setCurrency(currencyMap.get((long)rsLoan.getInt("currency")));
                                                    loanCopy.setRegDate(rsLoan.getDate("date"));
                                                    loanCopy.setRegNumber(rsLoan.getString("number"));
                                                    loanCopy.setDebtor(debtor);
                                                    loanCopy.setLoanState(loanStateMap.get((long)1));

                                                    children.add(loanCopy);

                                                    loan.setChildren(children);
                                                    loanCopy.setParent(loan);
//                                                    loanCopy.setParent(loan);


                                                    String id = details.substring(details.indexOf("id=")+3, details.lastIndexOf(")"));

                                                    Long subCreditID= Long.parseLong(id);

                                                    loansToBeLinked.put(subCreditID,loan);

                                                }


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










                                                loan.setAmount(rsLoan.getDouble("cost"));
                                                loan.setCreditOrder(crditOrderMap.get((long)rsLoan.getInt("credit_order_id")));
//                                                loan.setSupervisorId(rsLoan.getLong("curator"));
                                                loan.setSupervisorId(userMap.get(9L).getId());
                                                loan.setLoanType(loanTypeMap.get((long)rsLoan.getInt("credit_type")));
                                                loan.setCurrency(currencyMap.get((long)rsLoan.getInt("currency")));
                                                loan.setRegDate(rsLoan.getDate("date"));
                                                loan.setRegNumber(rsLoan.getString("number"));
                                                loan.setDebtor(debtor);
                                                loan.setLoanState(loanStateMap.get((long)rsLoan.getInt("status")));
                                                loan.setFund(fundMap.get(rsLoan.getLong("credit_line")));

                                                loan.setBankDataId(rsLoan.getLong("payment_requsite_id"));

                                                try
                                                {
                                                    if(rsLoan.getInt("fin_group")==9){
                                                        loan.setLoanFinGroup(loanFinGroupService.getById(9L));
                                                    }
                                                    else{
                                                        loan.setLoanFinGroup(loanFinGroupService.getById(1L));
                                                    }
                                                }
                                                catch(Exception ex)
                                                {
                                                    errorList.add(" group error == credit_id == "+rsLoan.getInt("credit_id")+ " error == "+ex);
                                                }



                                                loan.setVersion(Long.valueOf(rsLoan.getInt("credit_id")));

                                                if(debtorHasSubloan && loansToBeLinked.size()>0)
                                                {
                                                    if(loansToBeLinked.get(Long.valueOf(rsLoan.getInt("credit_id")))!=null)
                                                    {
                                                        loan.setParent(this.loanService.getById(loansToBeLinked.get(Long.valueOf(rsLoan.getInt("credit_id"))).getId()));


                                                    }
                                                }

                                                this.loanService.add(loan);

                                                debtorLoans.put(rsLoan.getLong("id"),loan);

                                                Boolean penalyLimit20 = true;

                                                if(rsLoan.getShort("limit_penalty_20")==0)
                                                {
                                                    penalyLimit20 = false;
                                                }
                                                else
                                                {
                                                    penalyLimit20 = true;
                                                }

                                                if(details.contains("(=списание=)"))
                                                {
                                                    try {
                                                        WriteOff writeOff = new WriteOff();

                                                        SimpleDateFormat DateFormatShort = new SimpleDateFormat("dd.MM.yyyy");

                                                        java.util.Date date = DateFormatShort.parse(details.substring(details.indexOf("дата==")+6,details.indexOf("(дата)")));

                                                        writeOff.setDate(date);
                                                        writeOff.setPrincipal(Double.parseDouble(details.substring(details.indexOf("сумма==")+7,details.indexOf("(сумма)"))));
                                                        writeOff.setFee((double)0);
                                                        writeOff.setInterest((double)0);
                                                        writeOff.setPenalty((double)0);
                                                        writeOff.setDescription("");
                                                        writeOff.setTotalAmount(Double.parseDouble(details.substring(details.indexOf("сумма==")+7,details.indexOf("(сумма)"))));

                                                        writeOff.setLoan(loan);

                                                        this.writeOffService.add(writeOff);
                                                    }
                                                    catch (Exception ex)
                                                    {
                                                        errorList.add(" write off error == "+ex);
                                                    }



                                                }



                                                // schedule migration
                                                try
                                                {
                                                    if (connection != null) {
                                                        ResultSet rsSchedule = null;
                                                        try
                                                        {
                                                            Statement stSchedule = connection.createStatement();
                                                            rsSchedule = stSchedule.executeQuery("select * from obligation where obligation.credit_id = "+rsLoan.getInt("credit_id"));
                                                            if(rsSchedule != null)
                                                            {
                                                                while (rsSchedule.next())
                                                                {
                                                                    PaymentSchedule paymentSchedule = new PaymentSchedule();

                                                                    if(tranchee || reconstructed)
                                                                    {
                                                                        paymentSchedule.setLoan(loanCopy);

                                                                        paymentSchedule.setDisbursement(rsSchedule.getDouble("profit"));
                                                                        paymentSchedule.setPrincipalPayment(rsSchedule.getDouble("debt_payment"));
                                                                        paymentSchedule.setInterestPayment(rsSchedule.getDouble("debt_percent"));
                                                                        paymentSchedule.setCollectedInterestPayment(rsSchedule.getDouble("collected_debt_percent"));
                                                                        paymentSchedule.setCollectedPenaltyPayment(rsSchedule.getDouble("collected_debt_penalty"));
                                                                        paymentSchedule.setExpectedDate(rsSchedule.getDate("date"));
                                                                        paymentSchedule.setInstallmentState(installmentState);

                                                                        if(rsSchedule.getInt("record_status")==2)
                                                                            paymentSchedule.setRecord_status(rsSchedule.getInt("record_status"));

                                                                        this.paymentScheduleRepository.save(paymentSchedule);

                                                                        PaymentSchedule paymentScheduleParent = new PaymentSchedule();

                                                                        paymentScheduleParent.setLoan(loan);

                                                                        paymentScheduleParent.setDisbursement(rsSchedule.getDouble("profit"));
                                                                        paymentScheduleParent.setPrincipalPayment(rsSchedule.getDouble("debt_payment"));
                                                                        paymentScheduleParent.setInterestPayment(rsSchedule.getDouble("debt_percent"));
                                                                        paymentScheduleParent.setCollectedInterestPayment(rsSchedule.getDouble("collected_debt_percent"));
                                                                        paymentScheduleParent.setCollectedPenaltyPayment(rsSchedule.getDouble("collected_debt_penalty"));
                                                                        paymentScheduleParent.setExpectedDate(rsSchedule.getDate("date"));
                                                                        paymentScheduleParent.setInstallmentState(installmentState);

                                                                        if(rsSchedule.getInt("record_status")==2)
                                                                            paymentScheduleParent.setRecord_status(rsSchedule.getInt("record_status"));

                                                                        this.paymentScheduleRepository.save(paymentScheduleParent);

                                                                    }
                                                                    else {
                                                                        paymentSchedule.setLoan(loan);
                                                                        paymentSchedule.setDisbursement(rsSchedule.getDouble("profit"));
                                                                        paymentSchedule.setPrincipalPayment(rsSchedule.getDouble("debt_payment"));
                                                                        paymentSchedule.setInterestPayment(rsSchedule.getDouble("debt_percent"));
                                                                        paymentSchedule.setCollectedInterestPayment(rsSchedule.getDouble("collected_debt_percent"));
                                                                        paymentSchedule.setCollectedPenaltyPayment(rsSchedule.getDouble("collected_debt_penalty"));
                                                                        paymentSchedule.setExpectedDate(rsSchedule.getDate("date"));
                                                                        paymentSchedule.setInstallmentState(installmentState);

                                                                        if(rsSchedule.getInt("record_status")==2)
                                                                            paymentSchedule.setRecord_status(rsSchedule.getInt("record_status"));

                                                                        this.paymentScheduleRepository.save(paymentSchedule);

                                                                    }

                                                                    if(debtorHasSubloan && loansToBeLinked.size()>0)
                                                                    {
                                                                        if(loansToBeLinked.get(Long.valueOf(rsLoan.getInt("credit_id")))!=null)
                                                                        {

                                                                            PaymentSchedule paymentScheduleParent = new PaymentSchedule();

                                                                            paymentScheduleParent.setLoan(this.loanService.getById(loansToBeLinked.get(Long.valueOf(rsLoan.getInt("credit_id"))).getId()));

                                                                            paymentScheduleParent.setDisbursement(rsSchedule.getDouble("profit"));
                                                                            paymentScheduleParent.setPrincipalPayment(rsSchedule.getDouble("debt_payment"));
                                                                            paymentScheduleParent.setInterestPayment(rsSchedule.getDouble("debt_percent"));
                                                                            paymentScheduleParent.setCollectedInterestPayment(rsSchedule.getDouble("collected_debt_percent"));
                                                                            paymentScheduleParent.setCollectedPenaltyPayment(rsSchedule.getDouble("collected_debt_penalty"));
                                                                            paymentScheduleParent.setExpectedDate(rsSchedule.getDate("date"));
                                                                            paymentScheduleParent.setInstallmentState(installmentState);

                                                                            if(rsSchedule.getInt("record_status")==2)
                                                                                paymentScheduleParent.setRecord_status(rsSchedule.getInt("record_status"));

                                                                            this.paymentScheduleRepository.save(paymentScheduleParent);

                                                                        }
                                                                    }




                                                                }

                                                                migrationSuccess = true;
                                                                stSchedule.close();
                                                                rsSchedule.close();
                                                            }
                                                        }
                                                        catch (SQLException ex)
                                                        {
                                                            System.out.println("payment schedule eroor " + ex);
                                                            ex.printStackTrace();
                                                            errorList.add(" scehdule error" + ex);
                                                        }

                                                    }
                                                    else
                                                    {
                                                        errorList.add(" scehdule error - no connection");

                                                    }
                                                }
                                                catch(Exception ex)
                                                {
                                                    System.out.println("payment schedule eroor " + ex);
                                                    ex.printStackTrace();
                                                    errorList.add(" scehdule error " + ex+ " loan id == " +loan.getId());
                                                }

                                                // credit term migration

                                                try
                                                {
                                                    if (connection != null) {
                                                        ResultSet rsTerm = null;
                                                        try
                                                        {
                                                            Statement stTerm = connection.createStatement();
                                                            rsTerm = stTerm.executeQuery("select * from credit_rule where credit_rule.credit_id = "+rsLoan.getInt("credit_id"));
                                                            if(rsTerm != null)
                                                            {
                                                                while (rsTerm.next())
                                                                {
                                                                    CreditTerm term = new CreditTerm();

                                                                    if(tranchee || reconstructed)
                                                                    {
                                                                        term.setLoan(loanCopy);

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

                                                                        if(penalyLimit20)
                                                                            term.setPenaltyLimitPercent((double)20);
                                                                        else term.setPenaltyLimitPercent((double)0);

                                                                        if(rsTerm.getInt("record_status")==2)
                                                                            term.setRecord_status(rsTerm.getInt("record_status"));

                                                                        this.creditTermRepository.save(term);

                                                                        CreditTerm termParent = new CreditTerm();

                                                                        termParent.setLoan(loan);

                                                                        termParent.setStartDate(rsTerm.getDate("start_date"));

                                                                        termParent.setInterestRateValue(rsTerm.getDouble("percent_rate"));

                                                                        termParent.setPenaltyOnPrincipleOverdueRateValue(rsTerm.getDouble("penalty_main_debt"));

                                                                        termParent.setPenaltyOnInterestOverdueRateValue(rsTerm.getDouble("penalty_percent"));

                                                                        if(rsTerm.getInt("method_days_period")<3)
                                                                            termParent.setDaysInMonthMethod((OrderTermDaysMethod)daysMethodMap.get((long)rsTerm.getInt("method_days_period")));
                                                                        else
                                                                        {
                                                                            termParent.setDaysInMonthMethod((OrderTermDaysMethod)daysMethodMap.get((long)2));
                                                                            errorList.add(" credit term error 1 "+loan.getId());
                                                                        }

                                                                        if(rsTerm.getInt("method_days")<4)
                                                                            termParent.setDaysInYearMethod((OrderTermDaysMethod)daysMethodMap.get((long)rsTerm.getInt("method_days")));
                                                                        else
                                                                        {
                                                                            termParent.setDaysInYearMethod((OrderTermDaysMethod)daysMethodMap.get((long)2));
                                                                            errorList.add(" credit term error 2 "+loan.getId()+" "+rsTerm.getInt("method_days"));
                                                                        }

                                                                        if(rsTerm.getInt("rate_type")>0)
                                                                            termParent.setFloatingRateType((OrderTermFloatingRateType)rateTypeMap.get((long)rsTerm.getInt("rate_type")));
                                                                        else termParent.setFloatingRateType((OrderTermFloatingRateType)rateTypeMap.get((long)2));

                                                                        if(rsTerm.getInt("plus_penalty")>0)
                                                                            termParent.setPenaltyOnPrincipleOverdueRateType((OrderTermFloatingRateType)rateTypeMap.get((long)rsTerm.getInt("plus_penalty")));
                                                                        else termParent.setPenaltyOnPrincipleOverdueRateType((OrderTermFloatingRateType)rateTypeMap.get((long)2));

                                                                        if(rsTerm.getInt("plus_penalty")>0)
                                                                            termParent.setPenaltyOnInterestOverdueRateType((OrderTermFloatingRateType)rateTypeMap.get((long)rsTerm.getInt("plus_percent")));
                                                                        else termParent.setPenaltyOnInterestOverdueRateType((OrderTermFloatingRateType)rateTypeMap.get((long)2));

                                                                        if(rsTerm.getInt("repayment_main_debt")==1)
                                                                            termParent.setTransactionOrder((OrderTermTransactionOrder)transactionOrder1);
                                                                        else termParent.setTransactionOrder((OrderTermTransactionOrder)transactionOrder3);

                                                                        termParent.setRatePeriod(ratePeriod);

                                                                        if(penalyLimit20)
                                                                            termParent.setPenaltyLimitPercent((double)20);
                                                                        else termParent.setPenaltyLimitPercent((double)0);

                                                                        if(rsTerm.getInt("record_status")==2)
                                                                            termParent.setRecord_status(rsTerm.getInt("record_status"));

                                                                        this.creditTermRepository.save(termParent);


                                                                    }
                                                                    else
                                                                    {
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

                                                                        if(penalyLimit20)
                                                                            term.setPenaltyLimitPercent((double)20);
                                                                        else term.setPenaltyLimitPercent((double)0);

                                                                        if(rsTerm.getInt("record_status")==2)
                                                                            term.setRecord_status(rsTerm.getInt("record_status"));

                                                                        this.creditTermRepository.save(term);
                                                                    }


                                                                    if(debtorHasSubloan && loansToBeLinked.size()>0)
                                                                    {
                                                                        if(loansToBeLinked.get(Long.valueOf(rsLoan.getInt("credit_id")))!=null)
                                                                        {

                                                                            CreditTerm termParent = new CreditTerm();

                                                                            termParent.setLoan(this.loanService.getById(loansToBeLinked.get(Long.valueOf(rsLoan.getInt("credit_id"))).getId()));

                                                                            termParent.setStartDate(rsTerm.getDate("start_date"));

                                                                            termParent.setInterestRateValue(rsTerm.getDouble("percent_rate"));

                                                                            termParent.setPenaltyOnPrincipleOverdueRateValue(rsTerm.getDouble("penalty_main_debt"));

                                                                            termParent.setPenaltyOnInterestOverdueRateValue(rsTerm.getDouble("penalty_percent"));

                                                                            termParent.setGraceDaysPrincipal(rsTerm.getInt("expiry_main"));
                                                                            termParent.setGraceDaysInterest(rsTerm.getInt("expiry_percent"));


                                                                            if(rsTerm.getInt("method_days_period")<3)
                                                                                termParent.setDaysInMonthMethod((OrderTermDaysMethod)daysMethodMap.get((long)rsTerm.getInt("method_days_period")));
                                                                            else
                                                                            {
                                                                                termParent.setDaysInMonthMethod((OrderTermDaysMethod)daysMethodMap.get((long)2));
                                                                                errorList.add(" credit term error 1 "+loan.getId());
                                                                            }

                                                                            if(rsTerm.getInt("method_days")<4)
                                                                                termParent.setDaysInYearMethod((OrderTermDaysMethod)daysMethodMap.get((long)rsTerm.getInt("method_days")));
                                                                            else
                                                                            {
                                                                                termParent.setDaysInYearMethod((OrderTermDaysMethod)daysMethodMap.get((long)2));
                                                                                errorList.add(" credit term error 2 "+loan.getId()+" "+rsTerm.getInt("method_days"));
                                                                            }

                                                                            if(rsTerm.getInt("rate_type")>0)
                                                                                termParent.setFloatingRateType((OrderTermFloatingRateType)rateTypeMap.get((long)rsTerm.getInt("rate_type")));
                                                                            else termParent.setFloatingRateType((OrderTermFloatingRateType)rateTypeMap.get((long)2));

                                                                            if(rsTerm.getInt("plus_penalty")>0)
                                                                                termParent.setPenaltyOnPrincipleOverdueRateType((OrderTermFloatingRateType)rateTypeMap.get((long)rsTerm.getInt("plus_penalty")));
                                                                            else termParent.setPenaltyOnPrincipleOverdueRateType((OrderTermFloatingRateType)rateTypeMap.get((long)2));

                                                                            if(rsTerm.getInt("plus_penalty")>0)
                                                                                termParent.setPenaltyOnInterestOverdueRateType((OrderTermFloatingRateType)rateTypeMap.get((long)rsTerm.getInt("plus_percent")));
                                                                            else termParent.setPenaltyOnInterestOverdueRateType((OrderTermFloatingRateType)rateTypeMap.get((long)2));

                                                                            if(rsTerm.getInt("repayment_main_debt")==1)
                                                                                termParent.setTransactionOrder((OrderTermTransactionOrder)transactionOrder1);
                                                                            else termParent.setTransactionOrder((OrderTermTransactionOrder)transactionOrder3);

                                                                            termParent.setRatePeriod(ratePeriod);

                                                                            if(penalyLimit20)
                                                                                termParent.setPenaltyLimitPercent((double)20);
                                                                            else termParent.setPenaltyLimitPercent((double)0);

                                                                            if(rsTerm.getInt("record_status")==2)
                                                                                termParent.setRecord_status(rsTerm.getInt("record_status"));

                                                                            this.creditTermRepository.save(termParent);

                                                                        }
                                                                    }

                                                                }

                                                                migrationSuccess = true;
                                                                stTerm.close();
                                                                rsTerm.close();
                                                            }
                                                        }
                                                        catch (SQLException ex)
                                                        {
                                                            System.out.println("Connection Failed! Check output console");
                                                            ex.printStackTrace();
                                                            errorList.add(" credit term error 0" + ex);

                                                        }

                                                    }
                                                    else
                                                    {
                                                        System.out.println("Failed to make connection!");
                                                    }
                                                }
                                                catch(Exception ex)
                                                {
                                                    System.out.println("credit term error " + ex);
                                                    ex.printStackTrace();
                                                    errorList.add(" credit term error " + ex + " loan id == " +loan.getId());
                                                }


                                                // payment migration

                                                try
                                                {
                                                    if (connection != null) {
                                                        ResultSet rsPayment = null;
                                                        try
                                                        {
                                                            Statement stPayment = connection.createStatement();
                                                            rsPayment = stPayment.executeQuery("select * from payments where payments.credit_id = "+rsLoan.getInt("credit_id"));
                                                            if(rsPayment != null)
                                                            {
                                                                while (rsPayment.next())
                                                                {
                                                                    Payment payment = new Payment();

                                                                    if(tranchee || reconstructed)
                                                                    {
                                                                        payment.setLoan(loanCopy);


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

                                                                        this.paymentService.add(payment);

                                                                        Payment paymentParent = new Payment();

                                                                        paymentParent.setLoan(loan);


                                                                        if(rsPayment.getString("payment_doc_number")!=null) {
                                                                            if (rsPayment.getString("payment_doc_number").length()<100)
                                                                                paymentParent.setNumber(rsPayment.getString("payment_doc_number"));
                                                                            else paymentParent.setNumber(rsPayment.getString("payment_doc_number").substring(0,99));
                                                                        }
                                                                        else paymentParent.setNumber("");

                                                                        paymentParent.setPaymentDate(rsPayment.getDate("payment_date"));
                                                                        paymentParent.setPrincipal(rsPayment.getDouble("main"));
                                                                        paymentParent.setInterest(rsPayment.getDouble("percent"));
                                                                        paymentParent.setPenalty(rsPayment.getDouble("penalty"));
                                                                        paymentParent.setFee((double)0);
                                                                        paymentParent.setTotalAmount(rsPayment.getDouble("payments_sum"));
                                                                        paymentParent.setExchange_rate(rsPayment.getDouble("exchange_rate"));
                                                                        paymentParent.setPaymentType( paymentTypeMap.get((long)rsPayment.getInt("payment_type")));


                                                                        if(loan.getCurrency().getId()==1)
                                                                            paymentParent.setIn_loan_currency(true);
                                                                        else
                                                                        {
                                                                            if(rsPayment.getInt("currency_type")==1)
                                                                            {
                                                                                paymentParent.setIn_loan_currency(false);
                                                                            }
                                                                            else paymentParent.setIn_loan_currency(true);
                                                                        }

                                                                        if(rsPayment.getString("decr_type")!=null) {
                                                                            if (rsPayment.getString("decr_type").length()<100)
                                                                                paymentParent.setDetails(rsPayment.getString("decr_type"));
                                                                            else paymentParent.setDetails(rsPayment.getString("decr_type").substring(0,99));
                                                                        }
                                                                        else paymentParent.setDetails("");

                                                                        if(rsPayment.getInt("record_status")==2)
                                                                            paymentParent.setRecord_status(rsPayment.getInt("record_status"));

                                                                        this.paymentService.add(paymentParent);

                                                                    }
                                                                    else
                                                                    {
                                                                        payment.setLoan(loan);


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

                                                                        this.paymentService.add(payment);
                                                                    }

                                                                    if(debtorHasSubloan && loansToBeLinked.size()>0)
                                                                    {
                                                                        if(loansToBeLinked.get(Long.valueOf(rsLoan.getInt("credit_id")))!=null)
                                                                        {

                                                                            Payment paymentParent = new Payment();

                                                                            paymentParent.setLoan(this.loanService.getById(loansToBeLinked.get(Long.valueOf(rsLoan.getInt("credit_id"))).getId()));


                                                                            if(rsPayment.getString("payment_doc_number")!=null) {
                                                                                if (rsPayment.getString("payment_doc_number").length()<100)
                                                                                    paymentParent.setNumber(rsPayment.getString("payment_doc_number"));
                                                                                else paymentParent.setNumber(rsPayment.getString("payment_doc_number").substring(0,99));
                                                                            }
                                                                            else paymentParent.setNumber("");

                                                                            paymentParent.setPaymentDate(rsPayment.getDate("payment_date"));
                                                                            paymentParent.setPrincipal(rsPayment.getDouble("main"));
                                                                            paymentParent.setInterest(rsPayment.getDouble("percent"));
                                                                            paymentParent.setPenalty(rsPayment.getDouble("penalty"));
                                                                            paymentParent.setFee((double)0);
                                                                            paymentParent.setTotalAmount(rsPayment.getDouble("payments_sum"));
                                                                            paymentParent.setExchange_rate(rsPayment.getDouble("exchange_rate"));
                                                                            paymentParent.setPaymentType( paymentTypeMap.get((long)rsPayment.getInt("payment_type")));


                                                                            if(this.loanService.getById(loansToBeLinked.get(Long.valueOf(rsLoan.getInt("credit_id"))).getId()).getCurrency().getId()==1)
                                                                                paymentParent.setIn_loan_currency(true);
                                                                            else
                                                                            {
                                                                                if(rsPayment.getInt("currency_type")==1)
                                                                                {
                                                                                    paymentParent.setIn_loan_currency(false);
                                                                                }
                                                                                else paymentParent.setIn_loan_currency(true);
                                                                            }

                                                                            if(rsPayment.getString("decr_type")!=null) {
                                                                                if (rsPayment.getString("decr_type").length()<100)
                                                                                    paymentParent.setDetails(rsPayment.getString("decr_type"));
                                                                                else paymentParent.setDetails(rsPayment.getString("decr_type").substring(0,99));
                                                                            }
                                                                            else paymentParent.setDetails("");



                                                                            if(rsPayment.getInt("record_status")==2)
                                                                                paymentParent.setRecord_status(rsPayment.getInt("record_status"));

                                                                            this.paymentService.add(paymentParent);

                                                                        }
                                                                    }

                                                                }

                                                                migrationSuccess = true;
                                                                stPayment.close();
                                                                rsPayment.close();
                                                            }
                                                        }
                                                        catch (SQLException ex)
                                                        {
                                                            System.out.println("Connection Failed! Check output console");
                                                            ex.printStackTrace();
                                                            errorList.add(" payment error 0" + ex);
                                                        }

                                                    }
                                                    else
                                                    {
                                                        System.out.println("Failed to make connection!");
                                                    }
                                                }
                                                catch(Exception ex)
                                                {
                                                    System.out.println(ex);
                                                    ex.printStackTrace();
                                                    errorList.add(" payment error" + ex + " loan id == " +loan.getId());
                                                }


                                                // plan  migration

                                                try
                                                {
                                                    if (connection != null) {
                                                        ResultSet rsPlan = null;
                                                        try
                                                        {
                                                            Statement stPlan = connection.createStatement();
                                                            rsPlan = stPlan.executeQuery("select * from plan where plan.credit_id = "+rsLoan.getInt("credit_id"));
                                                            if(rsPlan != null)
                                                            {
                                                                while (rsPlan.next())
                                                                {

                                                                    if(rsPlan.getInt("status")==1)
                                                                    {

                                                                        java.util.Date jan = new java.util.Date();
                                                                        java.util.Date feb = new java.util.Date();
                                                                        java.util.Date mar = new java.util.Date();
                                                                        java.util.Date apr = new java.util.Date();
                                                                        java.util.Date may = new java.util.Date();
                                                                        java.util.Date jun = new java.util.Date();
                                                                        java.util.Date jul = new java.util.Date();
                                                                        java.util.Date aug = new java.util.Date();
                                                                        java.util.Date sep = new java.util.Date();
                                                                        java.util.Date oct = new java.util.Date();
                                                                        java.util.Date nov = new java.util.Date();
                                                                        java.util.Date dec = new java.util.Date();

                                                                        jan.setDate(30);
                                                                        feb.setDate(28);
                                                                        mar.setDate(30);
                                                                        apr.setDate(30);
                                                                        may.setDate(30);
                                                                        jun.setDate(30);
                                                                        jul.setDate(30);
                                                                        aug.setDate(30);
                                                                        sep.setDate(30);
                                                                        oct.setDate(30);
                                                                        nov.setDate(30);
                                                                        dec.setDate(30);

                                                                        jan.setMonth(1);
                                                                        feb.setMonth(2);
                                                                        mar.setMonth(3);
                                                                        apr.setMonth(4);
                                                                        may.setMonth(5);
                                                                        jun.setMonth(6);
                                                                        jul.setMonth(7);
                                                                        aug.setMonth(8);
                                                                        sep.setMonth(9);
                                                                        oct.setMonth(10);
                                                                        nov.setMonth(11);
                                                                        dec.setMonth(12);

                                                                        jan.setYear(rsPlan.getInt("year")-1900);
                                                                        feb.setYear(rsPlan.getInt("year")-1900);
                                                                        mar.setYear(rsPlan.getInt("year")-1900);
                                                                        apr.setYear(rsPlan.getInt("year")-1900);
                                                                        may.setYear(rsPlan.getInt("year")-1900);
                                                                        jun.setYear(rsPlan.getInt("year")-1900);
                                                                        jul.setYear(rsPlan.getInt("year")-1900);
                                                                        aug.setYear(rsPlan.getInt("year")-1900);
                                                                        sep.setYear(rsPlan.getInt("year")-1900);
                                                                        oct.setYear(rsPlan.getInt("year")-1900);
                                                                        nov.setYear(rsPlan.getInt("year")-1900);
                                                                        dec.setYear(rsPlan.getInt("year")-1900);

                                                                        // plan January

                                                                        SupervisorPlan supervisorPlanJan = new SupervisorPlan();

                                                                        supervisorPlanJan.setLoan(loan);
                                                                        supervisorPlanJan.setPrincipal(rsPlan.getDouble("m01_main"));
                                                                        supervisorPlanJan.setInterest(rsPlan.getDouble("m01_percent"));
                                                                        supervisorPlanJan.setPenalty(rsPlan.getDouble("m01_penalty"));
                                                                        supervisorPlanJan.setFee((double)0);
                                                                        supervisorPlanJan.setAmount(rsPlan.getDouble("m01_main")+rsPlan.getDouble("m01_percent")+rsPlan.getDouble("m01_penalty"));
                                                                        supervisorPlanJan.setDescription("");


                                                                        supervisorPlanJan.setDate(jan);

                                                                        supervisorPlanJan.setReg_by_id(userMap.get(rsPlan.getLong("reg_by")).getId());
                                                                        supervisorPlanJan.setReg_date(rsPlan.getDate("reg_date"));

                                                                        // plan Feb

                                                                        SupervisorPlan supervisorPlanFeb = new SupervisorPlan();

                                                                        supervisorPlanFeb.setLoan(loan);
                                                                        supervisorPlanFeb.setPrincipal(rsPlan.getDouble("m02_main"));
                                                                        supervisorPlanFeb.setInterest(rsPlan.getDouble("m02_percent"));
                                                                        supervisorPlanFeb.setPenalty(rsPlan.getDouble("m02_penalty"));
                                                                        supervisorPlanFeb.setFee((double)0);
                                                                        supervisorPlanFeb.setAmount(rsPlan.getDouble("m02_main")+rsPlan.getDouble("m02_percent")+rsPlan.getDouble("m02_penalty"));
                                                                        supervisorPlanFeb.setDescription("");


                                                                        supervisorPlanFeb.setDate(feb);

                                                                        supervisorPlanFeb.setReg_by_id(userMap.get(rsPlan.getLong("reg_by")).getId());
                                                                        supervisorPlanFeb.setReg_date(rsPlan.getDate("reg_date"));


                                                                        // plan Mar

                                                                        SupervisorPlan supervisorPlanMar = new SupervisorPlan();

                                                                        supervisorPlanMar.setLoan(loan);
                                                                        supervisorPlanMar.setPrincipal(rsPlan.getDouble("m03_main"));
                                                                        supervisorPlanMar.setInterest(rsPlan.getDouble("m03_percent"));
                                                                        supervisorPlanMar.setPenalty(rsPlan.getDouble("m03_penalty"));
                                                                        supervisorPlanMar.setFee((double)0);
                                                                        supervisorPlanMar.setAmount(rsPlan.getDouble("m03_main")+rsPlan.getDouble("m03_percent")+rsPlan.getDouble("m03_penalty"));
                                                                        supervisorPlanMar.setDescription("");


                                                                        supervisorPlanMar.setDate(mar);

                                                                        supervisorPlanMar.setReg_by_id(userMap.get(rsPlan.getLong("reg_by")).getId());
                                                                        supervisorPlanMar.setReg_date(rsPlan.getDate("reg_date"));

                                                                        // plan Apr

                                                                        SupervisorPlan supervisorPlanApr = new SupervisorPlan();

                                                                        supervisorPlanApr.setLoan(loan);
                                                                        supervisorPlanApr.setPrincipal(rsPlan.getDouble("m04_main"));
                                                                        supervisorPlanApr.setInterest(rsPlan.getDouble("m04_percent"));
                                                                        supervisorPlanApr.setPenalty(rsPlan.getDouble("m04_penalty"));
                                                                        supervisorPlanApr.setFee((double)0);
                                                                        supervisorPlanApr.setAmount(rsPlan.getDouble("m04_main")+rsPlan.getDouble("m04_percent")+rsPlan.getDouble("m04_penalty"));
                                                                        supervisorPlanApr.setDescription("");


                                                                        supervisorPlanApr.setDate(apr);

                                                                        supervisorPlanApr.setReg_by_id(userMap.get(rsPlan.getLong("reg_by")).getId());
                                                                        supervisorPlanApr.setReg_date(rsPlan.getDate("reg_date"));

                                                                        // plan May

                                                                        SupervisorPlan supervisorPlanMay = new SupervisorPlan();

                                                                        supervisorPlanMay.setLoan(loan);
                                                                        supervisorPlanMay.setPrincipal(rsPlan.getDouble("m05_main"));
                                                                        supervisorPlanMay.setInterest(rsPlan.getDouble("m05_percent"));
                                                                        supervisorPlanMay.setPenalty(rsPlan.getDouble("m05_penalty"));
                                                                        supervisorPlanMay.setFee((double)0);
                                                                        supervisorPlanMay.setAmount(rsPlan.getDouble("m05_main")+rsPlan.getDouble("m05_percent")+rsPlan.getDouble("m05_penalty"));
                                                                        supervisorPlanMay.setDescription("");


                                                                        supervisorPlanMay.setDate(may);

                                                                        supervisorPlanMay.setReg_by_id(userMap.get(rsPlan.getLong("reg_by")).getId());
                                                                        supervisorPlanMay.setReg_date(rsPlan.getDate("reg_date"));




                                                                        // plan jun

                                                                        SupervisorPlan supervisorPlanJun = new SupervisorPlan();

                                                                        supervisorPlanJun.setLoan(loan);
                                                                        supervisorPlanJun.setPrincipal(rsPlan.getDouble("m06_main"));
                                                                        supervisorPlanJun.setInterest(rsPlan.getDouble("m06_percent"));
                                                                        supervisorPlanJun.setPenalty(rsPlan.getDouble("m06_penalty"));
                                                                        supervisorPlanJun.setFee((double)0);
                                                                        supervisorPlanJun.setAmount(rsPlan.getDouble("m06_main")+rsPlan.getDouble("m06_percent")+rsPlan.getDouble("m06_penalty"));
                                                                        supervisorPlanJun.setDescription("");


                                                                        supervisorPlanJun.setDate(jun);

                                                                        supervisorPlanJun.setReg_by_id(userMap.get(rsPlan.getLong("reg_by")).getId());
                                                                        supervisorPlanJun.setReg_date(rsPlan.getDate("reg_date"));



                                                                        // plan jul

                                                                        SupervisorPlan supervisorPlanJul = new SupervisorPlan();

                                                                        supervisorPlanJul.setLoan(loan);
                                                                        supervisorPlanJul.setPrincipal(rsPlan.getDouble("m07_main"));
                                                                        supervisorPlanJul.setInterest(rsPlan.getDouble("m07_percent"));
                                                                        supervisorPlanJul.setPenalty(rsPlan.getDouble("m07_penalty"));
                                                                        supervisorPlanJul.setFee((double)0);
                                                                        supervisorPlanJul.setAmount(rsPlan.getDouble("m07_main")+rsPlan.getDouble("m07_percent")+rsPlan.getDouble("m07_penalty"));
                                                                        supervisorPlanJul.setDescription("");


                                                                        supervisorPlanJul.setDate(jul);

                                                                        supervisorPlanJul.setReg_by_id(userMap.get(rsPlan.getLong("reg_by")).getId());
                                                                        supervisorPlanJul.setReg_date(rsPlan.getDate("reg_date"));


                                                                        // plan aug

                                                                        SupervisorPlan supervisorPlanAug = new SupervisorPlan();

                                                                        supervisorPlanAug.setLoan(loan);
                                                                        supervisorPlanAug.setPrincipal(rsPlan.getDouble("m08_main"));
                                                                        supervisorPlanAug.setInterest(rsPlan.getDouble("m08_percent"));
                                                                        supervisorPlanAug.setPenalty(rsPlan.getDouble("m08_penalty"));
                                                                        supervisorPlanAug.setFee((double)0);
                                                                        supervisorPlanAug.setAmount(rsPlan.getDouble("m08_main")+rsPlan.getDouble("m08_percent")+rsPlan.getDouble("m08_penalty"));
                                                                        supervisorPlanAug.setDescription("");


                                                                        supervisorPlanAug.setDate(aug);

                                                                        supervisorPlanAug.setReg_by_id(userMap.get(rsPlan.getLong("reg_by")).getId());
                                                                        supervisorPlanAug.setReg_date(rsPlan.getDate("reg_date"));


                                                                        // plan Sep

                                                                        SupervisorPlan supervisorPlanSep = new SupervisorPlan();

                                                                        supervisorPlanSep.setLoan(loan);
                                                                        supervisorPlanSep.setPrincipal(rsPlan.getDouble("m09_main"));
                                                                        supervisorPlanSep.setInterest(rsPlan.getDouble("m09_percent"));
                                                                        supervisorPlanSep.setPenalty(rsPlan.getDouble("m09_penalty"));
                                                                        supervisorPlanSep.setFee((double)0);
                                                                        supervisorPlanSep.setAmount(rsPlan.getDouble("m09_main")+rsPlan.getDouble("m09_percent")+rsPlan.getDouble("m09_penalty"));
                                                                        supervisorPlanSep.setDescription("");


                                                                        supervisorPlanSep.setDate(sep);

                                                                        supervisorPlanSep.setReg_by_id(userMap.get(rsPlan.getLong("reg_by")).getId());
                                                                        supervisorPlanSep.setReg_date(rsPlan.getDate("reg_date"));


                                                                        // plan Oct

                                                                        SupervisorPlan supervisorPlanOct = new SupervisorPlan();

                                                                        supervisorPlanOct.setLoan(loan);
                                                                        supervisorPlanOct.setPrincipal(rsPlan.getDouble("m10_main"));
                                                                        supervisorPlanOct.setInterest(rsPlan.getDouble("m10_percent"));
                                                                        supervisorPlanOct.setPenalty(rsPlan.getDouble("m10_penalty"));
                                                                        supervisorPlanOct.setFee((double)0);
                                                                        supervisorPlanOct.setAmount(rsPlan.getDouble("m10_main")+rsPlan.getDouble("m10_percent")+rsPlan.getDouble("m10_penalty"));
                                                                        supervisorPlanOct.setDescription("");


                                                                        supervisorPlanOct.setDate(oct);

                                                                        supervisorPlanOct.setReg_by_id(userMap.get(rsPlan.getLong("reg_by")).getId());
                                                                        supervisorPlanOct.setReg_date(rsPlan.getDate("reg_date"));


                                                                        // plan Nov

                                                                        SupervisorPlan supervisorPlanNov = new SupervisorPlan();

                                                                        supervisorPlanNov.setLoan(loan);
                                                                        supervisorPlanNov.setPrincipal(rsPlan.getDouble("m11_main"));
                                                                        supervisorPlanNov.setInterest(rsPlan.getDouble("m11_percent"));
                                                                        supervisorPlanNov.setPenalty(rsPlan.getDouble("m11_penalty"));
                                                                        supervisorPlanNov.setFee((double)0);
                                                                        supervisorPlanNov.setAmount(rsPlan.getDouble("m11_main")+rsPlan.getDouble("m11_percent")+rsPlan.getDouble("m11_penalty"));
                                                                        supervisorPlanNov.setDescription("");


                                                                        supervisorPlanNov.setDate(nov);

                                                                        supervisorPlanNov.setReg_by_id(userMap.get(rsPlan.getLong("reg_by")).getId());
                                                                        supervisorPlanNov.setReg_date(rsPlan.getDate("reg_date"));


                                                                        // plan Dec

                                                                        SupervisorPlan supervisorPlanDec = new SupervisorPlan();

                                                                        supervisorPlanDec.setLoan(loan);
                                                                        supervisorPlanDec.setPrincipal(rsPlan.getDouble("m12_main"));
                                                                        supervisorPlanDec.setInterest(rsPlan.getDouble("m12_percent"));
                                                                        supervisorPlanDec.setPenalty(rsPlan.getDouble("m12_penalty"));
                                                                        supervisorPlanDec.setFee((double)0);
                                                                        supervisorPlanDec.setAmount(rsPlan.getDouble("m12_main")+rsPlan.getDouble("m12_percent")+rsPlan.getDouble("m12_penalty"));
                                                                        supervisorPlanDec.setDescription("");


                                                                        supervisorPlanDec.setDate(dec);

                                                                        supervisorPlanDec.setReg_by_id(userMap.get(rsPlan.getLong("reg_by")).getId());
                                                                        supervisorPlanDec.setReg_date(rsPlan.getDate("reg_date"));



                                                                        if(supervisorPlanJan.getAmount()>1) this.supervisorPlanService.add(supervisorPlanJan);
                                                                        if(supervisorPlanFeb.getAmount()>1) this.supervisorPlanService.add(supervisorPlanFeb);
                                                                        if(supervisorPlanMar.getAmount()>1) this.supervisorPlanService.add(supervisorPlanMar);
                                                                        if(supervisorPlanApr.getAmount()>1) this.supervisorPlanService.add(supervisorPlanApr);
                                                                        if(supervisorPlanMay.getAmount()>1) this.supervisorPlanService.add(supervisorPlanMay);
                                                                        if(supervisorPlanJun.getAmount()>1) this.supervisorPlanService.add(supervisorPlanJun);
                                                                        if(supervisorPlanJul.getAmount()>1) this.supervisorPlanService.add(supervisorPlanJul);
                                                                        if(supervisorPlanAug.getAmount()>1) this.supervisorPlanService.add(supervisorPlanAug);
                                                                        if(supervisorPlanSep.getAmount()>1) this.supervisorPlanService.add(supervisorPlanSep);
                                                                        if(supervisorPlanOct.getAmount()>1) this.supervisorPlanService.add(supervisorPlanOct);
                                                                        if(supervisorPlanNov.getAmount()>1) this.supervisorPlanService.add(supervisorPlanNov);
                                                                        if(supervisorPlanDec.getAmount()>1) this.supervisorPlanService.add(supervisorPlanDec);


                                                                    }




                                                                }

                                                                migrationSuccess = true;
                                                                stPlan.close();
                                                                rsPlan.close();
                                                            }
                                                        }
                                                        catch (SQLException ex)
                                                        {
                                                            System.out.println("Connection Failed! Check output console");
                                                            ex.printStackTrace();
                                                            errorList.add(" credit term error 0" + ex);
                                                        }

                                                    }
                                                    else
                                                    {
                                                        System.out.println("Failed to make connection!");
                                                    }
                                                }
                                                catch(Exception ex)
                                                {
                                                    errorList.add(" plan error" + ex+ " loan id == " +loan.getId());
                                                }


                                                // credit goods migration
                                                try
                                                {
                                                    if (connection != null) {
                                                        ResultSet rsCreditGoods = null;
                                                        try
                                                        {
                                                            Statement stCreditGoods = connection.createStatement();
                                                            rsCreditGoods = stCreditGoods.executeQuery("select * from credit_goods where credit_goods.credit_id = "+rsLoan.getInt("credit_id"));
                                                            if(rsCreditGoods != null)
                                                            {
                                                                while (rsCreditGoods.next())
                                                                {
                                                                    LoanGoods loanGoods = new LoanGoods();

                                                                    loanGoods.setLoan(loan);
//                                                                    loanGoods.setGoodsTypeId(rsCreditGoods.getLong("goods_type_id"));

                                                                    loanGoods.setGoodType(goodTypeMap.get(rsCreditGoods.getLong("goods_type_id")));

                                                                    loanGoods.setQuantityType(quantityTypeMap.get(rsCreditGoods.getLong("quantity_type")));
                                                                    loanGoods.setQuantity(rsCreditGoods.getDouble("quantity"));

                                                                    this.loanGoodsService.add(loanGoods);

                                                                }

                                                                migrationSuccess = true;
                                                                stCreditGoods.close();
                                                                rsCreditGoods.close();
                                                            }
                                                        }
                                                        catch (SQLException ex)
                                                        {
                                                            System.out.println("Connection Failed! Check output console");
                                                            ex.printStackTrace();
                                                            errorList.add(" credit term error 0" + ex);
                                                        }

                                                    }
                                                    else
                                                    {
                                                        System.out.println("Failed to make connection!");
                                                    }
                                                }
                                                catch(Exception ex)
                                                {
                                                    System.out.println("Connection Failed! Check output console");
                                                    ex.printStackTrace();
                                                    errorList.add(" goods error" + ex+ " loan id == " +loan.getId());
                                                }

                                                // debt transfer migration
                                                try
                                                {
                                                    if (connection != null) {
                                                        ResultSet rsDebtTransfer = null;
                                                        try
                                                        {
                                                            Statement stDebtTransfer = connection.createStatement();
                                                            rsDebtTransfer = stDebtTransfer.executeQuery("select * from transfer_deal where transfer_deal.credit_id = "+rsLoan.getInt("credit_id"));
                                                            if(rsDebtTransfer != null)
                                                            {
                                                                while (rsDebtTransfer.next())
                                                                {
                                                                    DebtTransfer debtTransfer = new DebtTransfer();

                                                                    debtTransfer.setLoan(loan);

                                                                    if(rsDebtTransfer.getDate("deal_date")!=null)
                                                                        debtTransfer.setDate(rsDebtTransfer.getDate("deal_date"));
                                                                    else debtTransfer.setDate(new Date());

                                                                    if(rsDebtTransfer.getString("deal_number")!=null)
                                                                        debtTransfer.setNumber(rsDebtTransfer.getString("deal_number"));
                                                                    else debtTransfer.setNumber("");
                                                                    debtTransfer.setQuantity(rsDebtTransfer.getDouble("quantity"));
                                                                    debtTransfer.setPricePerUnit(rsDebtTransfer.getDouble("price"));
                                                                    debtTransfer.setTotalCost(rsDebtTransfer.getDouble("cost"));
                                                                    debtTransfer.setQuantity(rsDebtTransfer.getDouble("quantity"));

                                                                    debtTransfer.setGoodType(goodTypeMap.get(rsDebtTransfer.getLong("goods_type_id")));

                                                                    debtTransfer.setQuantityType(quantityTypeMap.get((long)1));

                                                                    this.debtTransferService.add(debtTransfer);

                                                                    debtorToBelinkedDebtTransferMap.put(Long.valueOf(debtTransfer.getId()),rsDebtTransfer.getLong("person_id_from"));

                                                                }

                                                                migrationSuccess = true;
                                                                stDebtTransfer.close();
                                                                rsDebtTransfer.close();
                                                            }
                                                        }
                                                        catch (SQLException ex)
                                                        {
                                                            System.out.println("Connection Failed! Check output console");
                                                            ex.printStackTrace();
                                                            errorList.add(" credit term error 0" + ex);
                                                        }

                                                    }
                                                    else
                                                    {
                                                        System.out.println("Failed to make connection!");
                                                    }
                                                }
                                                catch(Exception ex)
                                                {
                                                    System.out.println(ex);
                                                    ex.printStackTrace();
                                                    errorList.add(" goods error" + ex+ " loan id == " +loan.getId());
                                                }


                                            }

                                            migrationSuccess = true;
                                            rsLoan.close();
                                            stLoan.close();
                                        }
                                    }
                                    catch (SQLException ex)
                                    {
                                        System.out.println("Connection Failed! Check output console");
                                        ex.printStackTrace();
                                        errorList.add("loan error "+ex+ " debtor id = "+debtor.getId());
                                    }

                                }
                                else
                                {

                                    errorList.add("loan error , debtor id = "+debtor.getId());
                                }
                            }
                            catch(Exception ex)
                            {
                                System.out.println("Connection Failed! Check output console");
                                ex.printStackTrace();
                                errorList.add("loan error1 "+ex+ " debtor id = "+debtor.getId());
                            }

                            // COLLATERAL MIGRATION

                            try
                            {
                                if (connection != null) {
                                    ResultSet rsCollateralAgreement = null;
                                    try
                                    {
                                        Statement stCollateralAgreement = connection.createStatement();
                                        rsCollateralAgreement = stCollateralAgreement.executeQuery("select * from deposit_contract\n" +
                                                "where deposit_contract.id in\n" +
                                                "      ( select credit_deposit.deposit_id from credit_deposit,credit where credit_deposit.credit_id = credit.id and credit.person_id = "+rs.getInt("person_id")+")");
                                        if(rsCollateralAgreement != null)
                                        {
                                            while (rsCollateralAgreement.next())
                                            {

                                                CollateralAgreement collateralAgreement = new CollateralAgreement();

//                                                Owner guarantor = new Owner();
//                                                guarantor.setOwnerType(debtor.getOwner().getOwnerType());
//                                                guarantor.setName(debtor.getOwner().getName());
//                                                guarantor.setEntityId(debtor.getOwner().getEntityId());


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




                                                // collateral loan
                                                Set<Loan> collateralAgreementLoans = new HashSet<Loan>();

                                                try
                                                {
                                                    if (connection != null) {
                                                        ResultSet rsCollateralAgreementLoan = null;
                                                        try
                                                        {
                                                            Statement stCollateralAgreementLoan = connection.createStatement();
                                                            rsCollateralAgreementLoan = stCollateralAgreementLoan.executeQuery("select credit_id from credit_deposit where credit_deposit.deposit_id ="+rsCollateralAgreement.getInt("id"));
                                                            if(rsCollateralAgreementLoan != null)
                                                            {
                                                                while (rsCollateralAgreementLoan.next())
                                                                {

                                                                    Long creditId = rsCollateralAgreementLoan.getLong("credit_id");

                                                                    if(creditId>0)
                                                                    {
                                                                        Loan collateralAgreementLoan = debtorLoans.get(creditId);

                                                                        if(collateralAgreementLoan!=null)

                                                                            collateralAgreementLoans.add(collateralAgreementLoan);
                                                                        else {
                                                                            errorList.add(" collateral loan error no loan by id"  + " debtor id = "+debtor.getId());
                                                                        }

                                                                    }


                                                                }

                                                                stCollateralAgreementLoan.close();
                                                                rsCollateralAgreementLoan.close();
                                                            }
                                                        }
                                                        catch (SQLException ex)
                                                        {
                                                            System.out.println("Connection Failed! Check output console");
                                                            ex.printStackTrace();
                                                            errorList.add(" collateral loan error" + ex + " debtor id = "+debtor.getId());
                                                        }

                                                    }
                                                    else
                                                    {
                                                        System.out.println("Failed to make connection!");
                                                    }
                                                }
                                                catch(Exception ex)
                                                {
                                                    System.out.println("Connection Failed! Check output console");
                                                    ex.printStackTrace();
                                                    errorList.add(" collateral loan error" + ex + " debtor id = "+debtor.getId());
                                                }



                                                try
                                                {
                                                    if (connection != null) {
                                                        ResultSet rsCollateralAgreementLoan = null;
                                                        try
                                                        {
                                                            Statement stCollateralAgreementLoan = connection.createStatement();
                                                            rsCollateralAgreementLoan = stCollateralAgreementLoan.executeQuery("select * from deposit_contract_editions dce where dce.contract_id ="+rsCollateralAgreement.getInt("id"));
                                                            if(rsCollateralAgreementLoan != null)
                                                            {
                                                                while (rsCollateralAgreementLoan.next())
                                                                {
                                                                    AdditionalAgreement additionalAgreement = new AdditionalAgreement();

                                                                    additionalAgreement.setCollateralAgreement(collateralAgreement);

                                                                    if(rsCollateralAgreementLoan.getDate("date")!=null)
                                                                        additionalAgreement.setDate(rsCollateralAgreementLoan.getDate("date"));

                                                                    if(rsCollateralAgreementLoan.getString("number").length()<150)
                                                                        additionalAgreement.setNumber(rsCollateralAgreementLoan.getString("number"));
                                                                    else additionalAgreement.setNumber(rsCollateralAgreementLoan.getString("number").substring(0,149));

                                                                    additionalAgreement.setDescription(rsCollateralAgreementLoan.getString("details"));

                                                                    this.additionalAgreementService.add(additionalAgreement);


                                                                }

                                                                stCollateralAgreementLoan.close();
                                                                rsCollateralAgreementLoan.close();
                                                            }
                                                        }
                                                        catch (SQLException ex)
                                                        {
                                                            System.out.println("Connection Failed! Check output console");
                                                            ex.printStackTrace();
                                                            errorList.add(" collateral loan error" + ex + " debtor id = "+debtor.getId());
                                                        }

                                                    }
                                                    else
                                                    {
                                                        System.out.println("Failed to make connection!");
                                                    }
                                                }
                                                catch(Exception ex)
                                                {
                                                    System.out.println("Connection Failed! Check output console");
                                                    ex.printStackTrace();
                                                    errorList.add(" collateral loan error" + ex + " debtor id = "+debtor.getId());
                                                }



                                                // collateral item migration

                                                Set<CollateralItem> collateralItems = new HashSet<CollateralItem>();
                                                try
                                                {
                                                    if (connection != null) {
                                                        ResultSet rsCollateralItem = null;
                                                        try
                                                        {
                                                            Statement stCollateralItem = connection.createStatement();
                                                            rsCollateralItem = stCollateralItem.executeQuery("select * from deposit_goods,deposit_goods_details, deposit_goods_type where " +
                                                                    " deposit_goods.id = deposit_goods_type.deposit_goods_id and " +
                                                                    " deposit_goods.id = deposit_goods_details.deposit_id and " +
                                                                    " deposit_goods.contract_id = "+rsCollateralAgreement.getInt("id"));
                                                            if(rsCollateralItem != null)
                                                            {
                                                                while (rsCollateralItem.next())
                                                                {

                                                                    CollateralItem collateralItem = new CollateralItem();

                                                                    collateralItem.setCollateralAgreement(collateralAgreement);

                                                                    collateralItem.setVersion((long)rsCollateralItem.getInt("id"));

                                                                    if(rsCollateralItem.getLong("person_id")==debtor.getVersion())
                                                                        collateralItem.setOwner(owner);
                                                                    else
                                                                    {
                                                                        debtorToBelinkedCollateralItemMap.put(rsCollateralItem.getLong("id"),rsCollateralItem.getLong("person_id"));
                                                                    }



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


                                                                    Set<CollateralItemInspectionResult> collateralItemInspectionResults = new HashSet<CollateralItemInspectionResult>();

                                                                    try
                                                                    {
                                                                        if (connection != null) {
                                                                            ResultSet rsCollateralItemInspection = null;
                                                                            try
                                                                            {
                                                                                Statement stCollateralItemInspection = connection.createStatement();
                                                                                rsCollateralItemInspection = stCollateralItemInspection.executeQuery("select * from deposit_Act where " +
                                                                                        " deposit_act.deposit_id = "+rsCollateralItem.getInt("id"));
                                                                                if(rsCollateralItemInspection != null)
                                                                                {
                                                                                    while (rsCollateralItemInspection.next())
                                                                                    {
                                                                                        CollateralItemInspectionResult collateralItemInspectionResult = new CollateralItemInspectionResult();


                                                                                        if(rsCollateralItemInspection.getDate("date")!=null)
                                                                                            collateralItemInspectionResult.setOnDate(rsCollateralItemInspection.getDate("date"));
                                                                                        else
                                                                                        {
                                                                                            System.out.println(" asdf " + rsCollateralItemInspection.getLong("id"));
                                                                                        }
                                                                                        collateralItemInspectionResult.setInspectionResultType(inspectionResultTypeMap.get(rsCollateralItemInspection.getLong("status")));
                                                                                        collateralItemInspectionResult.setDetails(rsCollateralItemInspection.getString("details"));
                                                                                        collateralItemInspectionResult.setCollateralItem(collateralItem);
                                                                                        collateralItemInspectionResult.setVersion(rsCollateralItemInspection.getLong("id"));


                                                                                        collateralItemInspectionResults.add(collateralItemInspectionResult);

                                                                                    }

                                                                                    stCollateralItemInspection.close();
                                                                                    rsCollateralItemInspection.close();
                                                                                }
                                                                            }
                                                                            catch (SQLException ex)
                                                                            {
                                                                                System.out.println("Connection Failed! Check output console");
                                                                                ex.printStackTrace();
                                                                                errorList.add(" collateral item inspection error" + ex+ " debtor id = " + debtor.getId());
                                                                            }
                                                                        }
                                                                        else
                                                                        {
                                                                            System.out.println("Failed to make connection!");
                                                                        }
                                                                    }
                                                                    catch(Exception ex)
                                                                    {
                                                                        System.out.println("Connection Failed! Check output console");
                                                                        ex.printStackTrace();
                                                                        errorList.add(" collateral item inspection error" + ex+ " debtor id = " + debtor.getId());
                                                                    }



                                                                    try
                                                                    {
                                                                        if (connection != null) {
                                                                            ResultSet rsCollateralItemArestFree = null;
                                                                            try
                                                                            {
                                                                                Statement stCollateralItemArestFree = connection.createStatement();
                                                                                rsCollateralItemArestFree = stCollateralItemArestFree.executeQuery("select * from deposit_release_details where " +
                                                                                        " deposit_release_details.deposit_id = "+rsCollateralItem.getInt("id"));
                                                                                if(rsCollateralItemArestFree != null)
                                                                                {
                                                                                    while (rsCollateralItemArestFree.next())
                                                                                    {
                                                                                        CollateralItemArrestFree collateralItemArrestFree = new CollateralItemArrestFree();

                                                                                        collateralItemArrestFree.setArrestFreeBy(userMap.get(rsCollateralItemArestFree.getLong("release_by")).getId());
                                                                                        collateralItem.setCollateralItemArrestFree(collateralItemArrestFree);

                                                                                        collateralItemArrestFree.setOnDate(rsCollateralItemArestFree.getDate("release_date"));

                                                                                        String details = "";

                                                                                        if(rsCollateralItemArestFree.getString("release_reason")!=null && rsCollateralItemArestFree.getString("release_reason")!="")
                                                                                            details+=rsCollateralItemArestFree.getString("release_reason");

                                                                                        if(rsCollateralItemArestFree.getString("release_details")!=null && rsCollateralItemArestFree.getString("release_details")!="")
                                                                                            details=details+ " ( "+rsCollateralItemArestFree.getString("release_details")+" )";

                                                                                        collateralItemArrestFree.setDetails(details);

                                                                                        collateralItem.setCollateralItemArrestFree(collateralItemArrestFree);

                                                                                    }

                                                                                    stCollateralItemArestFree.close();
                                                                                    rsCollateralItemArestFree.close();
                                                                                }
                                                                            }
                                                                            catch (SQLException ex)
                                                                            {
                                                                                errorList.add(" collateral item inspection error" + ex+ " debtor id = " + debtor.getId());
                                                                            }
                                                                        }
                                                                        else
                                                                        {
                                                                            System.out.println("Failed to make connection!");
                                                                        }
                                                                    }
                                                                    catch(Exception ex)
                                                                    {
                                                                        errorList.add(" collateral item inspection error" + ex+ " debtor id = " + debtor.getId());
                                                                    }

                                                                    collateralItem.setCollateralItemDetails(collateralItemDetails);
                                                                    collateralItem.setCollateralItemInspectionResults(collateralItemInspectionResults);

//                                                                    if(collateralItem.getCollateralItemArrestFree().getId()>0)
//                                                                    {
//                                                                        System.out.println(" Arrest Free == " + collateralItem.getCollateralItemArrestFree().getDetails());
//                                                                    }



                                                                    collateralItems.add(collateralItem);





                                                                }

                                                                stCollateralItem.close();
                                                                rsCollateralItem.close();
                                                            }
                                                        }
                                                        catch (SQLException ex)
                                                        {
                                                            errorList.add(" collateral item error" + ex+ " debtor id = " + debtor.getId());
                                                        }
                                                    }
                                                    else
                                                    {
                                                        System.out.println("Failed to make connection!");
                                                    }
                                                }
                                                catch(Exception ex)
                                                {
                                                    errorList.add(" collateral item error" + ex+ " debtor id = " + debtor.getId());
                                                }

                                                if(collateralAgreementLoans.size()>0)
                                                    collateralAgreement.setLoans(collateralAgreementLoans);

                                                if(collateralItems.size()>0)
                                                    collateralAgreement.setCollateralItems(collateralItems);

                                                if(collateralAgreementLoans.size()>0 && collateralItems.size()>0)
                                                {
                                                    this.collateralAgreementService.add(collateralAgreement);

                                                    for(CollateralItem item :collateralAgreement.getCollateralItems())
                                                    {
                                                        collateralItemMap.put(item.getVersion(),item);
                                                    }



                                                }
                                                else
                                                {
//                                                    errorList.add(" no loan or item for collateral id ="+rsCollateralAgreement.getLong("id"));
                                                }


                                            }

                                            migrationSuccess = true;
                                            stCollateralAgreement.close();
                                            rsCollateralAgreement.close();
                                        }
                                    }
                                    catch (SQLException ex)
                                    {
                                        System.out.println("Connection Failed! Check output console");
                                        ex.printStackTrace();
                                        errorList.add(" collateral agreement error " + ex+ " debtor id = " + debtor.getId());
                                    }

                                }
                                else
                                {
                                    System.out.println("Failed to make connection!");
                                }
                            }
                            catch(Exception ex)
                            {
                                System.out.println("Connection Failed! Check output console");
                                ex.printStackTrace();
                                errorList.add(" collateral agreement error " + ex+ " debtor id = " + debtor.getId());
                            }


                            // Collection migration
                            try
                            {

                                if (connection != null) {
                                    ResultSet rsCollectionProcedure = null;
                                    try
                                    {
                                        Statement stCollectionProcedure = connection.createStatement();
                                        rsCollectionProcedure = stCollectionProcedure.executeQuery("select * from legal_process where legal_process.person_id ="
                                                +rs.getInt("person_id"));
                                        if(rsCollectionProcedure != null)
                                        {

                                            while (rsCollectionProcedure.next())
                                            {

                                                CollectionProcedure collectionProcedure = new CollectionProcedure();
                                                collectionProcedure.setProcedureType(procedureTypeMap.get((long)1));
                                                collectionProcedure.setVersion((long)rsCollectionProcedure.getInt("id"));


                                                Set<CollectionPhase> listOfPhases = new HashSet<CollectionPhase>();

                                                try
                                                {

                                                    if (connection != null) {
                                                        ResultSet rsCollection = null;
                                                        try
                                                        {
                                                            Statement stCollection = connection.createStatement();
                                                            rsCollection = stCollection.executeQuery("select * from legal_process_state where " +
                                                                    "legal_process_state.legal_process_id ="
                                                                    +rsCollectionProcedure.getInt("id")+
                                                                    " order by legal_process_state.state_date");
                                                            if(rsCollection != null)
                                                            {

                                                                while (rsCollection.next())
                                                                {

                                                                    if(rsCollection.getLong("state")==1 && collectionProcedure.getStartDate()==null)
                                                                    {
                                                                        collectionProcedure.setStartDate(rsCollection.getDate("state_date"));
                                                                    }

                                                                    collectionProcedure.setProcedureStatus(procedureStatusMap.get(rsCollection.getLong("status")));

                                                                    if(rsCollection.getLong("status")==2)
                                                                        if(rsCollection.getDate("status_date")!=null)
                                                                            collectionProcedure.setCloseDate(rsCollection.getDate("status_date"));

                                                                    CollectionPhase collectionPhase = new CollectionPhase();

                                                                    Set<Loan> phaseLoans = new HashSet<Loan>();

                                                                    collectionPhase.setCollectionProcedure(collectionProcedure);
//
                                                                    if(rsCollection.getDate("state_date")!=null)
                                                                        collectionPhase.setStartDate(rsCollection.getDate("state_date"));

                                                                    if(rsCollection.getLong("result")>0)
                                                                        collectionPhase.setPhaseStatus(phaseStatusMap.get(rsCollection.getLong("result")));

                                                                    if(rsCollection.getLong("state")>0)
                                                                        collectionPhase.setPhaseType(phaseTypeMap.get(rsCollection.getLong("state")));

                                                                    if(rsCollection.getLong("result")>1)
                                                                        if(rsCollection.getDate("result_date")!=null)
                                                                            collectionPhase.setCloseDate(rsCollection.getDate("result_date"));

                                                                    collectionPhase.setDocNumber(rsCollection.getString("doc_number"));
                                                                    collectionPhase.setResultDocNumber(rsCollection.getString("result_doc_number"));

                                                                    Set<PhaseDetails> allPhaseDetails = new HashSet<PhaseDetails>();

                                                                    try
                                                                    {
                                                                        Statement stCollectionDetails = connection.createStatement();
                                                                        ResultSet rsCollectionDetails = stCollectionDetails.executeQuery("select * from legal_process_state_details where " +
                                                                                "legal_process_state_details.state_id = "
                                                                                +rsCollection.getInt("id") +
                                                                                " order by credit_id");
                                                                        if(rsCollectionDetails != null)
                                                                        {

                                                                            PhaseDetails phaseDetails = new PhaseDetails();

                                                                            while (rsCollectionDetails.next())
                                                                            {

                                                                                if(rsCollectionDetails.getInt("record_type")==1)
                                                                                {
                                                                                    Loan phaseLoan = debtorLoans.get(rsCollectionDetails.getLong("credit_id"));
                                                                                    if(phaseLoan!= null)
                                                                                    {
                                                                                        phaseLoans.add(phaseLoan);

                                                                                        phaseDetails.setLoan_id(phaseLoan.getId());
                                                                                        phaseDetails.setStartTotalAmount(rsCollectionDetails.getDouble("debt_main")+rsCollectionDetails.getDouble("debt_percent")+rsCollectionDetails.getDouble("debt_penalty"));
                                                                                        phaseDetails.setStartPrincipal(rsCollectionDetails.getDouble("debt_main"));
                                                                                        phaseDetails.setStartInterest(rsCollectionDetails.getDouble("debt_percent"));
                                                                                        phaseDetails.setStartPenalty(rsCollectionDetails.getDouble("debt_penalty"));
                                                                                        phaseDetails.setStartFee((double)0);
                                                                                        allPhaseDetails.add(phaseDetails);
                                                                                    }

                                                                                }
                                                                                else
                                                                                {
                                                                                    Loan phaseLoan = debtorLoans.get(rsCollectionDetails.getLong("credit_id"));
                                                                                    if(phaseLoan!= null)
                                                                                    {
                                                                                        phaseLoans.add(phaseLoan);

                                                                                        phaseDetails.setLoan_id(phaseLoan.getId());
                                                                                        phaseDetails.setCloseTotalAmount(rsCollectionDetails.getDouble("debt_main")+rsCollectionDetails.getDouble("debt_percent")+rsCollectionDetails.getDouble("debt_penalty"));
                                                                                        phaseDetails.setClosePrincipal(rsCollectionDetails.getDouble("debt_main"));
                                                                                        phaseDetails.setCloseInterest(rsCollectionDetails.getDouble("debt_percent"));
                                                                                        phaseDetails.setClosePenalty(rsCollectionDetails.getDouble("debt_penalty"));
                                                                                        phaseDetails.setCloseFee((double)0);
                                                                                        allPhaseDetails.add(phaseDetails);

                                                                                    }

                                                                                }



                                                                            }

                                                                            phaseDetails.setCollectionPhase(collectionPhase);
                                                                            collectionPhase.setPhaseDetails(allPhaseDetails);
                                                                            collectionPhase.setVersion((long)rsCollection.getInt("id"));

                                                                            rsCollectionDetails.close();
                                                                            stCollectionDetails.close();
                                                                        }
                                                                    }
                                                                    catch (SQLException ex)
                                                                    {
                                                                        System.out.println("Connection Failed! Check output console");
                                                                        ex.printStackTrace();
                                                                        errorList.add(" collection phase details error " + ex + " debtor id = " +debtor.getId());
                                                                    }

                                                                    collectionPhase.setLoans(phaseLoans);
                                                                    listOfPhases.add(collectionPhase);

                                                                }

                                                                stCollection.close();
                                                                rsCollection.close();
                                                            }
                                                        }
                                                        catch (SQLException ex)
                                                        {
                                                            System.out.println("Connection Failed! Check output console");
                                                            ex.printStackTrace();
                                                            errorList.add(" collection error " + ex + " debtor id == "+debtor.getId());
                                                        }


                                                        collectionProcedure.setCollectionPhases(listOfPhases);

                                                        this.collectionProcedureService.add(collectionProcedure);


                                                    }
                                                    else
                                                    {
                                                        System.out.println("Failed to make connection!");
                                                    }
                                                }
                                                catch(Exception ex)
                                                {
                                                    System.out.println("Connection Failed! Check output console");
                                                    ex.printStackTrace();
                                                    errorList.add(" collection error " + ex + " debtor id == "+debtor.getId());
                                                }




                                            }

                                            stCollectionProcedure.close();
                                            rsCollectionProcedure.close();
                                        }
                                    }
                                    catch (SQLException ex)
                                    {
                                        System.out.println("Connection Failed! Check output console");
                                        ex.printStackTrace();
                                        errorList.add(" collection error " + ex + " debtor id == "+debtor.getId());
                                    }

                                }
                                else
                                {
                                    System.out.println("Failed to make connection!");
                                }
                            }
                            catch(Exception ex)
                            {
                                System.out.println("Connection Failed! Check output console");
                                ex.printStackTrace();
                                errorList.add(" collection error " + ex + " debtor id == "+debtor.getId());
                            }

                            try
                            {
                                if(rs.getInt("fin_status")==7 && (rs.getDate("fin_status_date")!=null))
                                {
                                    for (Loan loan:debtorLoans.values())
                                    {
                                        Bankrupt bankrupt = new Bankrupt();
                                        bankrupt.setLoan(loan);

                                        if(rs.getDate("fin_status_date")!=null)
                                        {
                                            if(loan.getCloseDate()!=null)
                                                bankrupt.setFinishedOnDate(loan.getCloseDate());

                                            bankrupt.setStartedOnDate(rs.getDate("fin_status_date"));
                                        }

                                        bankruptService.add(bankrupt);

                                    }


                                }

                            }
                            catch(Exception ex)
                            {
                                System.out.println(" bankrupt error");
                            }

                        }

                        rs.close();
                        st.close();
                    }


                    try {

                        for (DebtTransfer debtTransfer : this.debtTransferService.list())
                        {
                            if(debtorToBelinkedDebtTransferMap.get(debtTransfer.getId())!=null)
                            {

                                if(debtorMap.get(debtorToBelinkedDebtTransferMap.get(debtTransfer.getId()))!=null)
                                {
                                    debtTransfer.setTransferPersonId(debtorMap.get(debtorToBelinkedDebtTransferMap.get(debtTransfer.getId())).getId());
                                    this.debtTransferService.update(debtTransfer);
                                }


                            }

                        }

                    }
                    catch (Exception ex)
                    {
                        System.out.println(" debt transfer person migration error");
                        System.out.println(" debt transfer person migration error");
                        System.out.println(" debt transfer person migration error");
                        System.out.println(" debt transfer person migration error");
                        System.out.println(" debt transfer person migration error");
                        System.out.println(" debt transfer person migration error");
                    }



                    try {
                        for (Map.Entry<Long, Long> entry : debtorToBelinkedCollateralItemMap.entrySet())
                        {

                            try {
                                CollateralItem item = collateralItemMap.get(entry.getKey());

                                Debtor debtor = debtorMap.get(entry.getValue());

                                if(debtor!=null)
                                {
                                    Owner guarantor = debtor.getOwner();

                                    item.setOwner(guarantor);

                                    this.collateralItemService.update(item);
                                }

                            }
                            catch(Exception ex)
                            {
                                errorList.add("debtorToBelinkedCollateralItemMap "+ entry.getKey() + " "+entry.getValue()+" error == "+ ex);
                            }



                        }


                    }
                    catch (Exception ex)
                    {
                        System.out.println(" collateral item person migration error");
                        System.out.println(" collateral item person migration error");
                        System.out.println(" collateral item person migration error");
                        System.out.println(" collateral item person migration error");
                        System.out.println(" collateral item person migration error");
                        System.out.println(" collateral item person migration error");

                    }




                }
                catch (SQLException ex)
                {
                    System.out.println("Connection Failed! Check output console");
                    ex.printStackTrace();
                    errorList.add("debtor migration error: query ="+ex);
                }

            }
            else
            {
                System.out.println("Failed to make connection!");
                errorList.add("debtor migration error: connection");
            }
        }
        catch(Exception ex)
        {

            System.out.println(" Error in User migration "+ex);
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

    /*
    private boolean loanFinGroupMigrate(Connection connection)
    {
        boolean migrationSuccess = false;

        try
        {
            if (connection != null) {
                ResultSet rs = null;
                try
                {
                    Statement st = connection.createStatement();
                    rs = st.executeQuery("select * from system_type where system_type.group_id = 40 order by system_type.type_id");
                    HashMap<String,LoanFinGroup> newMap=new HashMap<>();
                    for(LoanFinGroup loanFinGroup:loanFinGroupService.list()){
                        newMap.put(loanFinGroup.getName(),loanFinGroup);
                    }
                    if(rs != null)
                    {
                        while (rs.next())
                        {
                            LoanFinGroup newEntity = newMap.get(rs.getString("type_id"));

                            loanFinGroupMap.put(rs.getLong("type_id"),newEntity);

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

    */

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

                            aokmotu.setDistrict(district);


                            if(aokmotu!=null)
                            aokmotuMap.put(rs.getLong("id"),aokmotu);

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
