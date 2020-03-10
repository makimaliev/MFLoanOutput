package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.doc.model.State;
import kg.gov.mf.loan.output.report.model.ReportTemplate;
import kg.gov.mf.loan.output.report.model.DocumentReportData;
import kg.gov.mf.loan.output.report.model.DocumentView;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import kg.gov.mf.loan.output.report.service.DocumentViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



public class DocumentReportDataManager {
	
	/**
     * Report GENERATION TOOL
     */

    @Autowired
    LoanViewService loanViewService;

    @Autowired
    DocumentViewService documentViewService;

    public DocumentReportData getReportDataGrouped(DocumentReportData reportData,ReportTemplate reportTemplate)
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        ReportTool reportTool = new ReportTool();

        LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<>();

        parameterS.putAll(reportTool.getParametersByTemplate(reportTemplate));

        reportData.getDocumentViews().addAll(documentViewService.findByParameter(parameterS));



        return groupifyData(reportData, reportTemplate, reportTool );
    }

    public DocumentReportData groupifyData(DocumentReportData reportData, ReportTemplate reportTemplate, ReportTool reportTool)
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

        DocumentReportData childA = null;
        DocumentReportData childB = null;
        DocumentReportData childC = null;
        DocumentReportData childD = null;
        DocumentReportData childE = null;
        DocumentReportData childF = null;


        Map<String, String> states = new HashMap<>(0);
        for(State state : State.values())
        {
            states.put(state.name(), state.text());
        }



        for (DocumentView documentView:reportData.getDocumentViews())
        {
            if(reportTool.getIdByGroupType(reportTemplate.getGroupType1(),documentView)!=currentgroupAid)
            {
                childA = reportData.addChild();
                childA.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType1(),documentView));
                childA.setLevel((short)1);

                currentgroupAid=reportTool.getIdByGroupType(reportTemplate.getGroupType1(),documentView);
                currentgroupBid=-1;
                currentgroupCid=-1;
                currentgroupDid=-1;

            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType2(),documentView)!=currentgroupBid)
            {
                childB = childA.addChild();
                childB.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType2(),documentView));
                childB.setLevel((short)2);

                childB.setCount(1);
                childA.setCount(childA.getCount()+1);
                reportData.setCount(reportData.getCount()+1);

                currentgroupBid=reportTool.getIdByGroupType(reportTemplate.getGroupType2(),documentView);
                currentgroupCid=-1;
                currentgroupDid=-1;

            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType3(),documentView)!=currentgroupCid)
            {
                DocumentView lv = documentView;

                childC = childB.addChild();
//                childC.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType3(),documentView));

                childC.setName(lv.getV_doc_title());

                childC.setLevel((short)3);

                childC.setDocumentCount(1);
                childA.setDocumentCount(childA.getDocumentCount()+1);
                childB.setDocumentCount(childB.getDocumentCount()+1);
                reportData.setDocumentCount(reportData.getDocumentCount()+1);

                childC.setDocumentID(lv.getV_doc_id());

                if(lv.getV_doc_documentDueDate()!=null)
                childC.setDocumentDueDate(new java.sql.Date(lv.getV_doc_documentDueDate().getTime()));

                // Parse id to string



                childC.setDocumentStateID(states.get(lv.getV_doc_documentState()));


                childC.setDocumentIndexNumber(lv.getV_doc_indexNo());

                childC.setDocumentOwnerID(lv.getV_doc_owner());

                if(lv.getV_doc_receiverRegisteredDate()!=null)
                childC.setDocumentReceiverRegisteredDate(new java.sql.Date(lv.getV_doc_receiverRegisteredDate().getTime()));

                if(lv.getV_doc_receiverRegisteredNumber()!=null)
                childC.setDocumentReceiverRegisteredNumber(lv.getV_doc_receiverRegisteredNumber());

                if(lv.getV_doc_senderRegisteredDate()!=null)
                childC.setDocumentSenderRegisteredDate(new java.sql.Date(lv.getV_doc_senderRegisteredDate().getTime()));

                if(lv.getV_doc_senderRegisteredNumber()!=null)
                childC.setDocumentSenderRegisteredNumber(lv.getV_doc_senderRegisteredNumber());

                childC.setDocumentTitle(lv.getV_doc_title());

                childC.setDocumentTypeID(lv.getV_doc_documentType());
                childC.setDocumentSubTypeID(lv.getV_doc_documentSubType());

                if(lv.getV_doc_receiver_executor_name()!=null)
                childC.setDocumentReceiverExecutorName(lv.getV_doc_receiver_executor_name());


                String receiverName = "";
                short receiverType = 3;

                String senderName = "";
                short senderType = 3;



                if(lv.getV_doc_receiverResponsibleName()!=null)
                {
                    receiverName = lv.getV_doc_receiverResponsibleName();
                    try
                    {
                        receiverType = Short.valueOf(receiverName.substring(receiverName.length()-1,1));
                    }
                    catch (Exception ex)
                    {

                    }

                }

                childC.setDocumentReceiverResponsibleName(lv.getV_doc_receiver_responsible_name(), (short)receiverType);


                if(lv.getV_doc_senderResponsibleName()!=null)
                {
                    senderName = lv.getV_doc_senderResponsibleName();
                    try
                    {
                        senderType = Short.valueOf(senderName.substring(senderName.length()-1,1));
                    }
                    catch (Exception ex)
                    {

                    }

                    if(senderName.length()>2)
                    {
                        String text = lv.getV_doc_senderResponsibleName().substring(0,lv.getV_doc_senderResponsibleName().length()-1);

                        if(lv.getV_doc_sender_responsible_name()!=null)
                        {
                            text=lv.getV_doc_sender_responsible_name();
                        }

                        lv.setV_doc_sender_responsible_name(text);
                    }

                }


                childC.setDocumentSenderExecutorName(lv.getV_doc_sender_executor_name());
                childC.setDocumentSenderResponsibleName(lv.getV_doc_sender_responsible_name(),  (short)senderType);

                childC.setDocumentUserID(lv.getV_doc_document_user_id());
                childC.setDocumentDepartmentID(lv.getV_doc_document_department_id());

                currentgroupCid=reportTool.getIdByGroupType(reportTemplate.getGroupType3(),documentView);
                currentgroupDid=-1;

            }

            if(reportTool.getIdByGroupType(reportTemplate.getGroupType4(),documentView)!=currentgroupDid)
            {
                DocumentView dv = documentView;

                childD = childC.addChild();
                childD.setName(reportTool.getNameByGroupType(reportTemplate.getGroupType4(),documentView));
                childD.setLevel((short)4);

                childD.setDocumentUserID(dv.getV_doc_document_user_id());
                childD.setDocumentDepartmentID(dv.getV_doc_document_department_id());

                currentgroupDid=reportTool.getIdByGroupType(reportTemplate.getGroupType4(),documentView);
            }

        }


        return reportData;
    }


}
