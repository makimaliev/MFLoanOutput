package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.manage.model.collateral.ItemType;
import kg.gov.mf.loan.manage.service.collateral.ItemTypeService;
import kg.gov.mf.loan.output.report.model.CollateralInspectionReportData;
import kg.gov.mf.loan.output.report.model.CollateralInspectionView;
import kg.gov.mf.loan.output.report.model.ReportTemplate;
import kg.gov.mf.loan.output.report.service.CollateralInspectionViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class CollateralInspectionReportDataManager {
	
	/**
     * Report GENERATION TOOL
     */

    @Autowired
    ItemTypeService itemTypeService;

    @Autowired
    CollateralInspectionViewService collateralInspectionViewService;

    Map<Long,ItemType> itemTypeMap = new HashMap<Long,ItemType>();

    public CollateralInspectionReportData getReportDataGrouped(CollateralInspectionReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        ReportTool reportTool = new ReportTool();

        LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<>();

        parameterS.putAll(reportTool.getParametersByTemplate(reportTemplate));

        reportData.getCollateralInspectionViews().addAll(collateralInspectionViewService.findByParameter(parameterS));

//        for (CollateralInspectionView collateralInspectionView: reportData.getCollateralInspectionViews())
//        {
//
//        }

        return groupifyData(reportData, reportTemplate, reportTool );
    }

    public CollateralInspectionReportData groupifyData(CollateralInspectionReportData reportData, ReportTemplate reportTemplate, ReportTool reportTool)
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

        CollateralInspectionReportData childA = null;
        CollateralInspectionReportData childB = null;
        CollateralInspectionReportData childC = null;
        CollateralInspectionReportData childD = null;
        CollateralInspectionReportData childE = null;
        CollateralInspectionReportData childF = null;

        for (CollateralInspectionView collateralInspectionView:reportData.getCollateralInspectionViews())
        {
            if(reportTool.getIdByGroupType(reportTemplate.getGroupType1(),collateralInspectionView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType1(),collateralInspectionView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(reportTemplate.getGroupType1(),collateralInspectionView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType2(),collateralInspectionView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType2(),collateralInspectionView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(reportTemplate.getGroupType2(),collateralInspectionView);
            }


            if(reportTool.getIdByGroupType(reportTemplate.getGroupType3(),collateralInspectionView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType3(),collateralInspectionView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                currentgroupCid=reportTool.getIdByGroupType(reportTemplate.getGroupType3(),collateralInspectionView);
            }


            if(reportTool.getIdByGroupType(reportTemplate.getGroupType4(),collateralInspectionView)!=currentgroupDid)
            {

                CollateralInspectionView lv = collateralInspectionView;

                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType4(),collateralInspectionView));
                childD.setLevel((short)4);

                childD.setDetailsCount(1);
                childC.setDetailsCount(childC.getDetailsCount()+1);
                childA.setDetailsCount(childA.getDetailsCount()+1);
                childB.setDetailsCount(childB.getDetailsCount()+1);
                reportData.setDetailsCount(reportData.getDetailsCount()+1);


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


                currentgroupDid=reportTool.getIdByGroupType(reportTemplate.getGroupType4(),collateralInspectionView);
            }



            if(reportTool.getIdByGroupType(reportTemplate.getGroupType5(),collateralInspectionView)!=currentgroupEid)
            {

                CollateralInspectionView pv = collateralInspectionView;

                childE = childD.addChild();
                childE.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType5(),collateralInspectionView));
                childE.setLevel((short)5);


                childE.setPaymentCount(1);
                childD.setPaymentCount(childD.getPaymentCount()+1);
                childC.setPaymentCount(childC.getPaymentCount()+1);
                childA.setPaymentCount(childA.getPaymentCount()+1);
                childB.setPaymentCount(childB.getPaymentCount()+1);
                reportData.setPaymentCount(reportData.getPaymentCount()+1);

                if(pv.getV_ca_agreementDate()!=null)
                    childE.setCollateralAgreementDate(new java.sql.Date(pv.getV_ca_agreementDate().getTime()));
                childE.setCollateralAgreementNumber(pv.getV_ca_agreementNumber());

                if(pv.getV_ca_arrestRegDate()!=null)
                    childE.setCollateralArrestRegDate(new java.sql.Date(pv.getV_ca_arrestRegDate().getTime()));
                childE.setCollateralArrestRegNumber(pv.getV_ca_arrestRegNumber());

                if(pv.getV_ca_collateralOfficeRegDate()!=null)
                    childE.setCollateralOfficeRegDate(new java.sql.Date(pv.getV_ca_collateralOfficeRegDate().getTime()));
                childE.setCollateralOfficeRegNumber(pv.getV_ca_collateralOfficeRegNumber());

                if(pv.getV_ca_notaryOfficeRegDate()!=null)
                    childE.setCollateralNotaryOfficeRegDate(new java.sql.Date(pv.getV_ca_notaryOfficeRegDate().getTime()));
                else
                {

                }
                childE.setCollateralNotaryOfficeRegNumber(pv.getV_ca_notaryOfficeRegNumber());

                childE.setCollateralItemName(pv.getV_ci_name());
                childE.setCollateralItemQuantity(pv.getV_ci_quantity());
                childE.setCollateralItemQuantityTypeId(pv.getV_ci_quantityTypeId());
                childE.setCollateralItemTypeId(pv.getV_ci_itemTypeId());



                if(pv.getV_ci_quantity()>0)
                {
                    childA.setCollateralItemQuantity(childA.getCollateralItemQuantity()+pv.getV_ci_quantity());
                    childB.setCollateralItemQuantity(childB.getCollateralItemQuantity()+pv.getV_ci_quantity());
                    childC.setCollateralItemQuantity(childC.getCollateralItemQuantity()+pv.getV_ci_quantity());
                    childD.setCollateralItemQuantity(childD.getCollateralItemQuantity()+pv.getV_ci_quantity());
                    reportData.setCollateralItemQuantity(reportData.getCollateralItemQuantity()+pv.getV_ci_quantity());
                }


                if(pv.getV_ci_collateralValue()>0)
                {
                    childE.setCollateralItemCollateralValue(childE.getCollateralItemCollateralValue()+pv.getV_ci_collateralValue());
                    childA.setCollateralItemCollateralValue(childA.getCollateralItemCollateralValue()+pv.getV_ci_collateralValue());
                    childB.setCollateralItemCollateralValue(childB.getCollateralItemCollateralValue()+pv.getV_ci_collateralValue());
                    childC.setCollateralItemCollateralValue(childC.getCollateralItemCollateralValue()+pv.getV_ci_collateralValue());
                    childD.setCollateralItemCollateralValue(childD.getCollateralItemCollateralValue()+pv.getV_ci_collateralValue());
                    reportData.setCollateralItemCollateralValue(reportData.getCollateralItemCollateralValue()+pv.getV_ci_collateralValue());
                }

                if(pv.getV_ci_estimatedValue()>0)
                {
                    childE.setCollateralItemEstimatedValue(childE.getCollateralItemEstimatedValue()+pv.getV_ci_estimatedValue());
                    childA.setCollateralItemEstimatedValue(childA.getCollateralItemEstimatedValue()+pv.getV_ci_estimatedValue());
                    childB.setCollateralItemEstimatedValue(childB.getCollateralItemEstimatedValue()+pv.getV_ci_estimatedValue());
                    childC.setCollateralItemEstimatedValue(childC.getCollateralItemEstimatedValue()+pv.getV_ci_estimatedValue());
                    childD.setCollateralItemEstimatedValue(childD.getCollateralItemEstimatedValue()+pv.getV_ci_estimatedValue());
                    reportData.setCollateralItemEstimatedValue(reportData.getCollateralItemEstimatedValue()+pv.getV_ci_estimatedValue());
                }

                childE.setCollateralItemDescription(pv.getV_ci_description());

//                childE.setCollateralItemTypeName(itemTypeMap.get(pv.getV_ci_itemTypeId()).getName());
                currentgroupEid=reportTool.getIdByGroupType(reportTemplate.getGroupType5(),collateralInspectionView);
            }



            if(reportTool.getIdByGroupType(reportTemplate.getGroupType6(),collateralInspectionView)!=currentgroupFid)
            {

                CollateralInspectionView pv = collateralInspectionView;

                childF = childE.addChild();
                childF.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType6(),collateralInspectionView));
                childF.setLevel((short)6);


                if(pv.getV_cir_onDate()!=null)
                    childF.setCollateralInspectionOnDate(new java.sql.Date(pv.getV_cir_onDate().getTime()));

                childF.setCollateralInspectionDetails(pv.getV_cir_details());

                childF.setCollateralInspectionResultTypeId(pv.getV_cir_inspectionResultTypeId());


                reportData.setInspectionCount(reportData.getInspectionCount()+1);
                childA.setInspectionCount(childA.getInspectionCount()+1);
                childB.setInspectionCount(childB.getInspectionCount()+1);
                childC.setInspectionCount(childC.getInspectionCount()+1);
                childD.setInspectionCount(childD.getInspectionCount()+1);
                childE.setInspectionCount(childE.getInspectionCount()+1);


                currentgroupFid=reportTool.getIdByGroupType(reportTemplate.getGroupType6(),collateralInspectionView);
            }

        }


        return reportData;
    }



}
