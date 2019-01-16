package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.manage.model.collateral.ItemType;
import kg.gov.mf.loan.manage.service.collateral.ItemTypeService;
import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.CollateralItemViewService;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class CollateralItemReportDataManager {
	
	/**
     * Report GENERATION TOOL
     */

    @Autowired
    ItemTypeService itemTypeService;

    @Autowired
    CollateralItemViewService collateralItemViewService;

    Map<Long,ItemType> itemTypeMap = new HashMap<Long,ItemType>();

    public CollateralItemReportData getReportDataGrouped(CollateralItemReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        ReportTool reportTool = new ReportTool();

        LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<>();

        parameterS.putAll(reportTool.getParametersByTemplate(reportTemplate));

        reportData.getCollateralItemViews().addAll(collateralItemViewService.findByParameter(parameterS));

        for (CollateralItemView collateralItemView: reportData.getCollateralItemViews())
        {
            System.out.println(collateralItemView.getV_ci_name());
        }

        return groupifyData(reportData, reportTemplate, reportTool );
    }

    public CollateralItemReportData groupifyData(CollateralItemReportData reportData, ReportTemplate reportTemplate, ReportTool reportTool)
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

            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType2(),collateralItemView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType2(),collateralItemView));
                childB.setLevel((short)2);

                currentgroupBid=reportTool.getIdByGroupType(reportTemplate.getGroupType2(),collateralItemView);
                currentgroupCid=-1;
                currentgroupDid=-1;

            }


            if(reportTool.getIdByGroupType(reportTemplate.getGroupType3(),collateralItemView)!=currentgroupCid)
            {
                childC = childB.addChild();
                childC.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType3(),collateralItemView));
                childC.setLevel((short)3);

                childC.setCount(1);
                childB.setCount(childB.getCount()+1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                currentgroupCid=reportTool.getIdByGroupType(reportTemplate.getGroupType3(),collateralItemView);
                currentgroupDid=-1;
            }


            if(reportTool.getIdByGroupType(reportTemplate.getGroupType4(),collateralItemView)!=currentgroupDid)
            {

                CollateralItemView lv = collateralItemView;

                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType4(),collateralItemView));
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
                    System.out.println("asdf=="+lv.getV_ci_id());
                }
                childD.setCollateralNotaryOfficeRegNumber(lv.getV_ca_notaryOfficeRegNumber());


                currentgroupDid=reportTool.getIdByGroupType(reportTemplate.getGroupType4(),collateralItemView);
            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType5(),collateralItemView)!=currentgroupEid)
            {

                CollateralItemView pv = collateralItemView;

                childE = childD.addChild();
                childE.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType5(),collateralItemView));
                childE.setLevel((short)5);

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
                    System.out.println("asdf=="+pv.getV_ci_id());
                }
                childE.setCollateralNotaryOfficeRegNumber(pv.getV_ca_notaryOfficeRegNumber());

                childE.setCollateralItemName(pv.getV_ci_name());
                childE.setCollateralItemQuantity(pv.getV_ci_quantity());
                childE.setCollateralItemQuantityTypeId(pv.getV_ci_quantityTypeId());
                childE.setCollateralItemTypeId(pv.getV_ci_itemTypeId());


                if(pv.getV_ci_collateralValue()>0)
                {
                    childE.setCollateralItemCollateralValue(childE.getCollateralItemCollateralValue()+pv.getV_ci_collateralValue());
                    childA.setCollateralItemCollateralValue(childA.getCollateralItemCollateralValue()+pv.getV_ci_collateralValue());
                    childB.setCollateralItemCollateralValue(childB.getCollateralItemCollateralValue()+pv.getV_ci_collateralValue());
                    childC.setCollateralItemCollateralValue(childC.getCollateralItemCollateralValue()+pv.getV_ci_collateralValue());
                    childD.setCollateralItemCollateralValue(childD.getCollateralItemCollateralValue()+pv.getV_ci_collateralValue());
                    reportData.setCollateralItemCollateralValue(reportData.getCollateralItemCollateralValue()+pv.getV_ci_collateralValue());
                }


                childE.setCollateralItemEstimatedValue(pv.getV_ci_estimatedValue());

                childE.setCollateralItemDescription(pv.getV_ci_description());

//                childE.setCollateralItemTypeName(itemTypeMap.get(pv.getV_ci_itemTypeId()).getName());
                currentgroupEid=reportTool.getIdByGroupType(reportTemplate.getGroupType5(),collateralItemView);
            }


        }


        return reportData;
    }



}
