package kg.gov.mf.loan.output.report.utils;

import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermCurrency;
import kg.gov.mf.loan.manage.service.orderterm.CurrencyRateService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermCurrencyService;
import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.FilterParameterService;
import kg.gov.mf.loan.output.report.service.GroupTypeService;
import kg.gov.mf.loan.output.report.service.OutputParameterService;
import kg.gov.mf.loan.output.report.service.ReferenceViewService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.*;
import java.util.*;

public class ReportTool
{

    @Autowired
    ReferenceViewService referenceViewService;

    @Autowired
    GroupTypeService groupTypeService;

    @Autowired
    OrderTermCurrencyService orderTermCurrencyService;

    @Autowired
    CurrencyRateService currencyRateService;

    @Autowired
    FilterParameterService filterParameterService;

    @Autowired
    UserService userService;



    Map<String,Map<Long,String>> referenceMap      = new HashMap<>();

    Map<Date,Map<Long,Double>> CurrencyRateMap      = new HashMap<>();

    Map<String, HSSFFont> fontMap = new HashMap<>();

    Map<String, HSSFColor> colorMap = new HashMap<>();

    Map<String, HSSFCellStyle> cellStyleMap = new HashMap<>();

    Map<String, GroupType> groupTypeMap = new HashMap<>();

    HSSFDataFormat   format         = null;

    int RowCount = 0;
    short ColumnCount=0;

    int lastRowCount = 0;

    int maxColumnCount = 0;

    Map<Integer,Float> rowHeightMap = new HashMap<Integer,Float>();

    HSSFRow          Row            = null;
    HSSFCell         Cell           = null;



    HSSFFont         FontTitle      = null;
    HSSFFont         FontHeader     = null;
    HSSFFont         FontSum        = null;
    HSSFFont         FontFooter     = null;
    HSSFFont         FontGroup1     = null;
    HSSFFont         FontGroup2     = null;
    HSSFFont         FontGroup3     = null;
    HSSFFont         FontGroup4     = null;
    HSSFFont         FontGroup5     = null;
    HSSFFont         FontGroup6     = null;

    HSSFColor        ColorTitle     = null;
    HSSFColor        ColorHeader    = null;
    HSSFColor        ColorSum       = null;
    HSSFColor        ColorFooter    = null;
    HSSFColor        ColorGroup1    = null;
    HSSFColor        ColorGroup2    = null;
    HSSFColor        ColorGroup3    = null;
    HSSFColor        ColorGroup4    = null;
    HSSFColor        ColorGroup5    = null;
    HSSFColor        ColorGroup6    = null;

    short            BorderTitle    = 0;
    short            BorderHeader   = 0;
    short            BorderSum      = 0;
    short            BorderFooter   = 0;
    short            BorderGroup1   = 0;
    short            BorderGroup2   = 0;
    short            BorderGroup3   = 0;
    short            BorderGroup4   = 0;
    short            BorderGroup5   = 0;
    short            BorderGroup6   = 0;

    HSSFCellStyle    TitleString    = null;
    HSSFCellStyle    TitleDate      = null;
    HSSFCellStyle    TitleInt       = null;
    HSSFCellStyle    TitleDouble    = null;

    HSSFCellStyle    HeaderString   = null;
    HSSFCellStyle    HeaderDate     = null;
    HSSFCellStyle    HeaderInt      = null;
    HSSFCellStyle    HeaderDouble   = null;


    HSSFCellStyle    SumString      = null;
    HSSFCellStyle    SumDate        = null;
    HSSFCellStyle    SumInt         = null;
    HSSFCellStyle    SumDouble      = null;

    HSSFCellStyle    FooterString    = null;
    HSSFCellStyle    FooterDate      = null;
    HSSFCellStyle    FooterInt       = null;
    HSSFCellStyle    FooterDouble    = null;


    HSSFCellStyle    Group1String    = null;
    HSSFCellStyle    Group1Date      = null;
    HSSFCellStyle    Group1Int       = null;
    HSSFCellStyle    Group1Double    = null;

    HSSFCellStyle    Group2String    = null;
    HSSFCellStyle    Group2Date      = null;
    HSSFCellStyle    Group2Int       = null;
    HSSFCellStyle    Group2Double    = null;

    HSSFCellStyle    Group3String    = null;
    HSSFCellStyle    Group3Date      = null;
    HSSFCellStyle    Group3Int       = null;
    HSSFCellStyle    Group3Double    = null;


    HSSFCellStyle    Group4String    = null;
    HSSFCellStyle    Group4Date      = null;
    HSSFCellStyle    Group4Int       = null;
    HSSFCellStyle    Group4Double    = null;


    HSSFCellStyle    Group5String    = null;
    HSSFCellStyle    Group5Date      = null;
    HSSFCellStyle    Group5Int       = null;
    HSSFCellStyle    Group5Double    = null;

    HSSFCellStyle    Group6String    = null;
    HSSFCellStyle    Group6Date      = null;
    HSSFCellStyle    Group6Int       = null;
    HSSFCellStyle    Group6Double    = null;




    // =========== _INIT ACTIONS =================================

    public void initReference()
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        for (ReferenceView referenceView:this.referenceViewService.findAll())
        {
            if(referenceMap.get(referenceView.getList_type())==null)
            {
                Map<Long,String> newMap      = new HashMap<>();

                newMap.put(referenceView.getId(),referenceView.getName());

                referenceMap.put(referenceView.getList_type(), newMap);
            }
            else
            {
                referenceMap.get(referenceView.getList_type()).put(referenceView.getId(),referenceView.getName());
            }


        }
    }

    public void initCurrencyRatesMap(ReportTemplate reportTemplate)
    {

        for (OrderTermCurrency orderTermCurrency:this.orderTermCurrencyService.list())
        {
            Date date = new Date();
            Date date2 = new Date(date.getTime());

            if(reportTemplate.getOnDate()!=null) date= reportTemplate.getOnDate();

            if(reportTemplate.getAdditionalDate()!=null) date2= reportTemplate.getAdditionalDate();

            Double currencyRateValue = this.currencyRateService.findByDateAndType(date, orderTermCurrency ).getRate();
            Double currencyRateValue2 = this.currencyRateService.findByDateAndType(date2, orderTermCurrency ).getRate();


            if(CurrencyRateMap.get(date)==null)
            {
                Map<Long,Double> newMap      = new HashMap<>();
                newMap.put(orderTermCurrency.getId(),currencyRateValue);
                CurrencyRateMap.put(date, newMap);
            }
            else
            {
                CurrencyRateMap.get(date).put(orderTermCurrency.getId(),currencyRateValue);
            }

            if(CurrencyRateMap.get(date2)==null)
            {
                Map<Long,Double> newMap      = new HashMap<>();
                newMap.put(orderTermCurrency.getId(),currencyRateValue2);
                CurrencyRateMap.put(date2, newMap);
            }
            else
            {
                CurrencyRateMap.get(date2).put(orderTermCurrency.getId(),currencyRateValue2);
            }



        }
    }

    public void initReportSetup(ReportTemplate reportTemplate, HSSFWorkbook WorkBook)
    {
        initFonts(reportTemplate,WorkBook);
        initBorders(reportTemplate);
        initColors(reportTemplate);
        initFormat(reportTemplate,WorkBook);
        initCellStyles(reportTemplate,WorkBook);
    }

    public void initFonts(ReportTemplate reportTemplate,HSSFWorkbook Workbook )
    {
        FontTitle   = getFontByLevel(10,reportTemplate,Workbook);
        FontHeader  = getFontByLevel(11,reportTemplate,Workbook);
        FontSum     = getFontByLevel(12,reportTemplate,Workbook);
        FontFooter  = getFontByLevel(13,reportTemplate,Workbook);

        FontGroup1  = getFontByLevel(1,reportTemplate,Workbook);
        FontGroup2  = getFontByLevel(2,reportTemplate,Workbook);
        FontGroup3  = getFontByLevel(3,reportTemplate,Workbook);
        FontGroup4  = getFontByLevel(4,reportTemplate,Workbook);
        FontGroup5  = getFontByLevel(5,reportTemplate,Workbook);
        FontGroup6  = getFontByLevel(6,reportTemplate,Workbook);
    }

    public void initColors(ReportTemplate reportTemplate)
    {
        ColorTitle  = getColorByLevel(10,reportTemplate);
        ColorHeader = getColorByLevel(11,reportTemplate);
        ColorSum    = getColorByLevel(12,reportTemplate);
        ColorFooter = getColorByLevel(13,reportTemplate);

        ColorGroup1 = getColorByLevel(1,reportTemplate);
        ColorGroup2 = getColorByLevel(2,reportTemplate);
        ColorGroup3 = getColorByLevel(3,reportTemplate);
        ColorGroup4 = getColorByLevel(4,reportTemplate);
        ColorGroup5 = getColorByLevel(5,reportTemplate);
        ColorGroup6 = getColorByLevel(6,reportTemplate);
    }

    public void initBorders(ReportTemplate reportTemplate)
    {
        BorderTitle  = getBorderTypeByLevel(10,reportTemplate);
        BorderHeader = getBorderTypeByLevel(11,reportTemplate);
        BorderSum    = getBorderTypeByLevel(12,reportTemplate);
        BorderFooter = getBorderTypeByLevel(13,reportTemplate);

        BorderGroup1 = getBorderTypeByLevel(1,reportTemplate);
        BorderGroup2 = getBorderTypeByLevel(2,reportTemplate);
        BorderGroup3 = getBorderTypeByLevel(3,reportTemplate);
        BorderGroup4 = getBorderTypeByLevel(4,reportTemplate);
        BorderGroup5 = getBorderTypeByLevel(5,reportTemplate);
        BorderGroup6 = getBorderTypeByLevel(6,reportTemplate);
    }

    public void initFormat(ReportTemplate reportTemplate, HSSFWorkbook WorkBook)
    {
        format         = WorkBook.createDataFormat();
    }

    public void initCellStyles(ReportTemplate reportTemplate,HSSFWorkbook WorkBook)
    {
        TitleString    = getCellStyleByLevel(10,"string",reportTemplate,WorkBook);
        TitleDate      = getCellStyleByLevel(10,"date",reportTemplate,WorkBook);
        TitleInt       = getCellStyleByLevel(10,"int",reportTemplate,WorkBook);
        TitleDouble    = getCellStyleByLevel(10,"double",reportTemplate,WorkBook);

        HeaderString   = getCellStyleByLevel(11,"string",reportTemplate,WorkBook);
        HeaderDate     = getCellStyleByLevel(11,"date",reportTemplate,WorkBook);
        HeaderInt      = getCellStyleByLevel(11,"int",reportTemplate,WorkBook);
        HeaderDouble   = getCellStyleByLevel(11,"double",reportTemplate,WorkBook);


        SumString      = getCellStyleByLevel(12,"string",reportTemplate,WorkBook);
        SumDate        = getCellStyleByLevel(12,"date",reportTemplate,WorkBook);
        SumInt         = getCellStyleByLevel(12,"int",reportTemplate,WorkBook);
        SumDouble      = getCellStyleByLevel(12,"double",reportTemplate,WorkBook);

        FooterString    = getCellStyleByLevel(13,"string",reportTemplate,WorkBook);
        FooterDate      = getCellStyleByLevel(13,"date",reportTemplate,WorkBook);
        FooterInt       = getCellStyleByLevel(13,"int",reportTemplate,WorkBook);
        FooterDouble    = getCellStyleByLevel(13,"double",reportTemplate,WorkBook);


        Group1String    = getCellStyleByLevel(1,"string",reportTemplate,WorkBook);
        Group1Date      = getCellStyleByLevel(1,"date",reportTemplate,WorkBook);
        Group1Int       = getCellStyleByLevel(1,"int",reportTemplate,WorkBook);
        Group1Double    = getCellStyleByLevel(1,"double",reportTemplate,WorkBook);

        Group2String    = getCellStyleByLevel(2,"string",reportTemplate,WorkBook);
        Group2Date      = getCellStyleByLevel(2,"date",reportTemplate,WorkBook);
        Group2Int       = getCellStyleByLevel(2,"int",reportTemplate,WorkBook);
        Group2Double    = getCellStyleByLevel(2,"double",reportTemplate,WorkBook);

        Group3String    = getCellStyleByLevel(3,"string",reportTemplate,WorkBook);
        Group3Date      = getCellStyleByLevel(3,"date",reportTemplate,WorkBook);
        Group3Int       = getCellStyleByLevel(3,"int",reportTemplate,WorkBook);
        Group3Double    = getCellStyleByLevel(3,"double",reportTemplate,WorkBook);


        Group4String    = getCellStyleByLevel(4,"string",reportTemplate,WorkBook);
        Group4Date      = getCellStyleByLevel(4,"date",reportTemplate,WorkBook);
        Group4Int       = getCellStyleByLevel(4,"int",reportTemplate,WorkBook);
        Group4Double    = getCellStyleByLevel(4,"double",reportTemplate,WorkBook);


        Group5String    = getCellStyleByLevel(5,"string",reportTemplate,WorkBook);
        Group5Date      = getCellStyleByLevel(5,"date",reportTemplate,WorkBook);
        Group5Int       = getCellStyleByLevel(5,"int",reportTemplate,WorkBook);
        Group5Double    = getCellStyleByLevel(5,"double",reportTemplate,WorkBook);

        Group6String    = getCellStyleByLevel(6,"string",reportTemplate,WorkBook);
        Group6Date      = getCellStyleByLevel(6,"date",reportTemplate,WorkBook);
        Group6Int       = getCellStyleByLevel(6,"int",reportTemplate,WorkBook);
        Group6Double    = getCellStyleByLevel(6,"double",reportTemplate,WorkBook);
    }

    public void setupSheetSettings(HSSFSheet Sheet, ReportTemplate reportTemplate)
    {

        try
        {
            Sheet.getPrintSetup().setLandscape(true);
            Sheet.setMargin(HSSFSheet.BottomMargin,0.5);
            Sheet.setMargin(HSSFSheet.TopMargin,0.5);
            Sheet.setMargin(HSSFSheet.LeftMargin,0);
            Sheet.setMargin(HSSFSheet.RightMargin,0);

            Sheet.setAutobreaks(true);
            HSSFPrintSetup ps = Sheet.getPrintSetup();
            ps.setFitWidth((short)1);
            ps.setFitHeight((short)0);
            ps.setHeaderMargin(0);
            ps.setFooterMargin(0);

            HSSFFooter footer = Sheet.getFooter();
            HSSFHeader header = Sheet.getHeader();

            for (OutputParameter outputParameter: reportTemplate.getOutputParameters())
            {
                switch (outputParameter.getOutputParameterType().toString())
                {
                    case "PAGE_ORIENTATION":
                        Sheet.getPrintSetup().setLandscape((outputParameter.getValue()==1)? true:false);
                        break;

                    case "SHEET_BOTTOM_MARGIN":
                        Sheet.setMargin(HSSFSheet.BottomMargin,outputParameter.getValue());
                        break;
                    case "SHEET_TOP_MARGIN":
                        Sheet.setMargin(HSSFSheet.TopMargin,outputParameter.getValue());
                        break;
                    case "SHEET_RIGHT_MARGIN":
                        Sheet.setMargin(HSSFSheet.RightMargin,outputParameter.getValue());
                        break;
                    case "SHEET_LEFT_MARGIN":
                        Sheet.setMargin(HSSFSheet.LeftMargin,outputParameter.getValue());
                        break;
                    case "SHEET_AUTOBREAKS":
                        Sheet.setAutobreaks((outputParameter.getValue()==1) ? true: false);
                        break;
                    case "SHEET_FIT_WIDTH":
                        ps.setFitWidth((short)outputParameter.getValue());
                        break;
                    case "SHEET_FIT_HEIGHT":
                        ps.setFitHeight((short)outputParameter.getValue());
                        break;
                    case "SHEET_FOOTER_MARGIN":
                        ps.setFooterMargin((short)outputParameter.getValue());
                        break;
                    case "SHEET_HEADER_MARGIN":
                        ps.setHeaderMargin((short)outputParameter.getValue());
                        break;

                    case "SHEET_FOOTER_TEXT":

                        String footer_text = outputParameter.getText();

                        if(footer_text.contains("page()")) footer_text.replace("page()",HSSFFooter.page());
                        if(footer_text.contains("numPages()")) footer_text.replace("numPages()",HSSFFooter.numPages());

                        if(outputParameter.getPosition()==2)
                        {
                            footer.setCenter(footer_text);
                        }
                        else
                        if(outputParameter.getPosition()==1)
                        {
                            footer.setLeft(footer_text);
                        }
                        else
                        {
                            footer.setRight(footer_text);
                        }



                        break;

                    case "SHEET_HEADER_TEXT":

                        String header_text = outputParameter.getText();

                        if(header_text.contains("page()")) header_text.replace("page()",HSSFFooter.page());
                        if(header_text.contains("numPages()")) header_text.replace("numPages()",HSSFFooter.numPages());

                        if(outputParameter.getPosition()==2)
                        {
                            header.setCenter(header_text);
                        }
                        else
                        if(outputParameter.getPosition()==1)
                        {
                            header.setLeft(header_text);
                        }
                        else
                        {
                            header.setRight(header_text);
                        }


                        break;

                    case "DEFAULT_COLUMN_WIDTH":
                        Sheet.setDefaultColumnWidth((int)outputParameter.getValue());
                        break;

                    case "COLUMN_WIDTH":
                        Sheet.setColumnWidth((int)outputParameter.getPosition()-1,(int)outputParameter.getValue()*256);
                        break;

                    case "FONT_HEIGHT":
                        getFontByLevel(outputParameter.getPosition()).setFontHeightInPoints((short)outputParameter.getValue());
                        break;

                    case "FONT_BOLD":
                        getFontByLevel(outputParameter.getPosition()).setBoldweight((outputParameter.getValue()==1)? (short)700:(short)400);
                        break;

                    case "FONT_COLOR":
                        getFontByLevel(outputParameter.getPosition()).setColor((short)outputParameter.getValue());
                        break;

                    case "CELL_FONT":
                        getCellStyleByLevel(outputParameter.getPosition(),outputParameter.getText()).setFont(getFontByLevel((long)outputParameter.getValue()));
                        break;

                    case "CELL_ALIGNMENT":
                        getCellStyleByLevel(outputParameter.getPosition(),outputParameter.getText()).setAlignment((short)outputParameter.getValue());
                        break;

                    case "CELL_BORDER":
                        getCellStyleByLevel(outputParameter.getPosition(),outputParameter.getText()).setBorderBottom((short)outputParameter.getValue());
                        getCellStyleByLevel(outputParameter.getPosition(),outputParameter.getText()).setBorderTop((short)outputParameter.getValue());
                        getCellStyleByLevel(outputParameter.getPosition(),outputParameter.getText()).setBorderLeft((short)outputParameter.getValue());
                        getCellStyleByLevel(outputParameter.getPosition(),outputParameter.getText()).setBorderRight((short)outputParameter.getValue());
                        break;

                    case "CELL_VERTICAL_ALIGNMENT":
                        getCellStyleByLevel(outputParameter.getPosition(),outputParameter.getText()).setVerticalAlignment((short)outputParameter.getValue());
                        break;

                    case "CELL_WRAP_TEXT":
                        getCellStyleByLevel(outputParameter.getPosition(),outputParameter.getText()).setWrapText((outputParameter.getValue() == 1) ? true : false);
                        break;

                    case "CELL_DATA_FORMAT":

                        String cellStyleType = "string";

                        if(outputParameter.getValue() ==2) cellStyleType = "date";
                        else if(outputParameter.getValue() ==3) cellStyleType = "int";
                        else if(outputParameter.getValue() ==4) cellStyleType = "double";
                        else cellStyleType = "string";

                        getCellStyleByLevel(outputParameter.getPosition(),cellStyleType).setDataFormat(format.getFormat(outputParameter.getText()));
                        break;

                    case "CELL_FOREGROUND_COLOR":
                        getCellStyleByLevel(outputParameter.getPosition(),outputParameter.getText()).setFillForegroundColor((short)outputParameter.getValue());
                        break;

                    case "CELL_PATTERN":
                        getCellStyleByLevel(outputParameter.getPosition(),outputParameter.getText()).setFillPattern((short)outputParameter.getValue());
                        break;

                    case "ROW_HEIGHT":
                        rowHeightMap.put((int)outputParameter.getPosition()-1,(float)outputParameter.getValue());
                        break;

                }

            }

        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }

    }








    // =========== _FORMAT ACTIONS =====================================

    public String FormatNumber(double number)
        {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
            symbols.setDecimalSeparator(',');
            symbols.setGroupingSeparator(' ');

            String pattern = ",##0.00";
            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);

            return decimalFormat.format(number);
        }

        public String DateToString(Date date)
        {

            SimpleDateFormat DateFormatShort = new SimpleDateFormat("dd.MM.yyyy");

            if(date == null)
                return "";
            else
                return DateFormatShort.format(date);
        }

    public Date StringToDate(String dateString)
    {

        SimpleDateFormat DateFormatShort = new SimpleDateFormat("dd.MM.yyyy");

        if(dateString == null || dateString =="")
            return null;
        else
        {
            try
            {
                Date date = DateFormatShort.parse(dateString);
                return date;
            }
            catch (java.text.ParseException e)
            {
                return null;
            }
        }

    }

    public String formatText(String originText, ReportTemplate reportTemplate)
    {
        if(originText.contains("(=onDate=)")) return originText.replace("(=onDate=)",DateToString(getOnDate(reportTemplate)));

        return originText;
    }

    public Date getOnDate(ReportTemplate reportTemplate)
    {

        Date date = new Date();
        for (GenerationParameter generationParameter: reportTemplate.getGenerationParameters())
        {
            if(generationParameter.getGenerationParameterType().getId()==1)
            {
                date = generationParameter.getDate();
            }
        }

        if(reportTemplate.getOnDate()!=null)
        {
            date = reportTemplate.getOnDate();
        }

        return date;
    }

    public Double getCurrencyRateValueByDateAndCurrencyTypeId(Date date, Long currencyRateTypeId)
    {
        double currencyRateValue = 1;

        if(!CurrencyRateMap.get(date).isEmpty())
        {
            if(CurrencyRateMap.get(date).get(currencyRateTypeId)>0)
                currencyRateValue = CurrencyRateMap.get(date).get(currencyRateTypeId);
        }

        return Double.valueOf(currencyRateValue);
    }




















    // ============ _CELL STYLE ACTIONS =====================================


    public HSSFCellStyle getCellStyleByLevel(long level, String DataType)
    {

        long DataTypeToLong = 0;

        if(DataType.equals("string"))
        {
            DataTypeToLong = 100;
        }
        if(DataType.equals("date"))
        {
            DataTypeToLong = 200;
        }
        if(DataType.equals("int"))
        {
            DataTypeToLong = 300;
        }
        if(DataType.equals("double"))
        {
            DataTypeToLong = 400;
        }

        level+=DataTypeToLong;


        switch (String.valueOf(level))
        {

            // TITLE
            case "110" : return TitleString;
            case "210" : return TitleDate;
            case "310" : return TitleInt;
            case "410" : return TitleDouble;


            // HEADER
            case "111" : return HeaderString;
            case "211" : return HeaderDate;
            case "321" : return HeaderInt;
            case "411" : return HeaderDouble;


            // SUMMARY
            case "112" : return SumString;
            case "212" : return SumDate;
            case "312" : return SumInt;
            case "412" : return SumDouble;

            // FOOTER
            case "113" : return FooterString;

            case "101" : return Group1String;
            case "201" : return Group1Date;
            case "301" : return Group1Int;
            case "401" : return Group1Double;

            case "102" : return Group2String;
            case "202" : return Group2Date;
            case "302" : return Group2Int;
            case "402" : return Group2Double;

            case "103" : return Group3String;
            case "203" : return Group3Date;
            case "303" : return Group3Int;
            case "403" : return Group3Double;


            case "104" : return Group4String;
            case "204" : return Group4Date;
            case "304" : return Group4Int;
            case "404" : return Group4Double;


            case "105" : return Group5String;
            case "205" : return Group5Date;
            case "305" : return Group5Int;
            case "405" : return Group5Double;


            case "106" : return Group6String;
            case "206" : return Group6Date;
            case "306" : return Group6Int;
            case "406" : return Group6Double;

            default:
                return Group6String;

        }

    }

    public HSSFCellStyle getCellStyleByLevel(long level, String DataType, ReportTemplate reportTemplate, HSSFWorkbook WorkBook)
    {

        long DataTypeToLong = 0;

        if(DataType.equals("string"))
        {
            DataTypeToLong = 100;
        }
        if(DataType.equals("date"))
        {
            DataTypeToLong = 200;
        }
        if(DataType.equals("int"))
        {
            DataTypeToLong = 300;
        }
        if(DataType.equals("double"))
        {
            DataTypeToLong = 400;
        }

        level+=DataTypeToLong;


        switch (String.valueOf(level))
        {

            // TITLE
            case "110" :
                HSSFCellStyle    CellStyleTitleString     = WorkBook.createCellStyle();
                CellStyleTitleString.setFont(FontTitle);
                CellStyleTitleString.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                return CellStyleTitleString;

            // HEADER
            case "111" :
                HSSFCellStyle    HeaderStringString    = WorkBook.createCellStyle();
                HeaderStringString.setFont(FontHeader);
                HeaderStringString.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                HeaderStringString.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
                HeaderStringString.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                HeaderStringString.setBorderBottom(BorderHeader);
                HeaderStringString.setBorderTop(BorderHeader);
                HeaderStringString.setBorderRight(BorderHeader);
                HeaderStringString.setBorderLeft(BorderHeader);
                HeaderStringString.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                HeaderStringString.setWrapText(true);

                return HeaderStringString;

            // SUMMARY
            case "112" :
                HSSFCellStyle    CellStyleDataSumString    = WorkBook.createCellStyle();
                CellStyleDataSumString.setFont(FontSum);
                CellStyleDataSumString.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                CellStyleDataSumString.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumString.setBorderTop(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumString.setBorderRight(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumString.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumString.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleDataSumString.setWrapText(true);
                CellStyleDataSumString.setFillForegroundColor(HSSFColor.AQUA.index);
                CellStyleDataSumString.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleDataSumString;

            case "312" :

                HSSFCellStyle    CellStyleDataSumInt    = WorkBook.createCellStyle();
                CellStyleDataSumInt.setFont(FontSum);
                CellStyleDataSumInt.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleDataSumInt.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumInt.setBorderTop(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumInt.setBorderRight(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumInt.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumInt.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleDataSumInt.setDataFormat(format.getFormat("#,#0"));
                CellStyleDataSumInt.setWrapText(true);
                CellStyleDataSumInt.setFillForegroundColor(HSSFColor.AQUA.index);
                CellStyleDataSumInt.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleDataSumInt;


            case "412" :
                HSSFCellStyle    CellStyleDataSumDouble    = WorkBook.createCellStyle();
                CellStyleDataSumDouble.setFont(FontSum);
                CellStyleDataSumDouble.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleDataSumDouble.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumDouble.setBorderTop(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumDouble.setBorderRight(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumDouble.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                CellStyleDataSumDouble.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleDataSumDouble.setDataFormat(format.getFormat("#,#0.00"));
                CellStyleDataSumDouble.setWrapText(true);
                CellStyleDataSumDouble.setFillForegroundColor(HSSFColor.AQUA.index);
                CellStyleDataSumDouble.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleDataSumDouble;

            // FOOTER
            case "113" :
                HSSFCellStyle    CellStyleFooterString    = WorkBook.createCellStyle();
                CellStyleFooterString.setFont(FontFooter);
                CellStyleFooterString.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                CellStyleFooterString.setFillBackgroundColor(HSSFColor.AQUA.index);
                return CellStyleFooterString;

            case "101" :
                HSSFCellStyle    CellStyleGroup1String    = WorkBook.createCellStyle();
                CellStyleGroup1String.setFont(FontGroup1);
                CellStyleGroup1String.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                CellStyleGroup1String.setBorderBottom(BorderGroup1);
                CellStyleGroup1String.setBorderTop(BorderGroup1);
                CellStyleGroup1String.setBorderRight(HSSFCellStyle.BORDER_THIN);
                CellStyleGroup1String.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                CellStyleGroup1String.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup1String.setWrapText(true);
                CellStyleGroup1String.setFillForegroundColor(ColorGroup1.getIndex());
                CellStyleGroup1String.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup1String;

            case "301" :
                HSSFCellStyle    CellStyleGroup1Int    = WorkBook.createCellStyle();
                CellStyleGroup1Int.setFont(FontGroup1);
                CellStyleGroup1Int.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup1Int.setBorderBottom(BorderGroup1);
                CellStyleGroup1Int.setBorderTop(BorderGroup1);
                CellStyleGroup1Int.setBorderRight(BorderGroup1);
                CellStyleGroup1Int.setBorderLeft(BorderGroup1);
                CellStyleGroup1Int.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup1Int.setDataFormat(format.getFormat("#,#0"));
                CellStyleGroup1Int.setWrapText(true);
                CellStyleGroup1Int.setFillForegroundColor(ColorGroup1.getIndex());
                CellStyleGroup1Int.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup1Int;

            case "401" :

                HSSFCellStyle    CellStyleGroup1Double    = WorkBook.createCellStyle();
                CellStyleGroup1Double.setFont(FontGroup1);
                CellStyleGroup1Double.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup1Double.setBorderBottom(BorderGroup1);
                CellStyleGroup1Double.setBorderTop(BorderGroup1);
                CellStyleGroup1Double.setBorderRight(BorderGroup1);
                CellStyleGroup1Double.setBorderLeft(BorderGroup1);
                CellStyleGroup1Double.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup1Double.setDataFormat(format.getFormat("#,#0.00"));
                CellStyleGroup1Double.setWrapText(true);
                CellStyleGroup1Double.setFillForegroundColor(ColorGroup1.getIndex());
                CellStyleGroup1Double.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup1Double;



            case "102" :
                HSSFCellStyle    CellStyleGroup2String    = WorkBook.createCellStyle();
                CellStyleGroup2String.setFont(FontGroup1);
                CellStyleGroup2String.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                CellStyleGroup2String.setBorderBottom(BorderGroup2);
                CellStyleGroup2String.setBorderTop(BorderGroup2);
                CellStyleGroup2String.setBorderRight(BorderGroup2);
                CellStyleGroup2String.setBorderLeft(BorderGroup2);
                CellStyleGroup2String.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup2String.setWrapText(true);
                CellStyleGroup2String.setFillForegroundColor(ColorGroup2.getIndex());
                CellStyleGroup2String.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup2String;

            case "302" :
                HSSFCellStyle    CellStyleGroup2Int    = WorkBook.createCellStyle();
                CellStyleGroup2Int.setFont(FontGroup2);
                CellStyleGroup2Int.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup2Int.setBorderBottom(BorderGroup2);
                CellStyleGroup2Int.setBorderTop(BorderGroup2);
                CellStyleGroup2Int.setBorderRight(BorderGroup2);
                CellStyleGroup2Int.setBorderLeft(BorderGroup2);
                CellStyleGroup2Int.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup2Int.setDataFormat(format.getFormat("#,#0"));
                CellStyleGroup2Int.setWrapText(true);
                CellStyleGroup2Int.setFillForegroundColor(ColorGroup2.getIndex());
                CellStyleGroup2Int.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup2Int;

            case "402" :

                HSSFCellStyle    CellStyleGroup2Double    = WorkBook.createCellStyle();
                CellStyleGroup2Double.setFont(FontGroup2);
                CellStyleGroup2Double.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup2Double.setBorderBottom(BorderGroup2);
                CellStyleGroup2Double.setBorderTop(BorderGroup2);
                CellStyleGroup2Double.setBorderRight(BorderGroup2);
                CellStyleGroup2Double.setBorderLeft(BorderGroup2);
                CellStyleGroup2Double.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup2Double.setDataFormat(format.getFormat("#,#0.00"));
                CellStyleGroup2Double.setWrapText(true);
                CellStyleGroup2Double.setFillForegroundColor(ColorGroup2.getIndex());
                CellStyleGroup2Double.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup2Double;













            case "103" :
                HSSFCellStyle    CellStyleGroup3String    = WorkBook.createCellStyle();
                CellStyleGroup3String.setFont(FontGroup3);
                CellStyleGroup3String.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                CellStyleGroup3String.setBorderBottom(BorderGroup3);
                CellStyleGroup3String.setBorderTop(BorderGroup3);
                CellStyleGroup3String.setBorderRight(BorderGroup3);
                CellStyleGroup3String.setBorderLeft(BorderGroup3);
                CellStyleGroup3String.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup3String.setWrapText(true);
                CellStyleGroup3String.setFillForegroundColor(ColorGroup3.getIndex());
                CellStyleGroup3String.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup3String;

            case "303" :
                HSSFCellStyle    CellStyleGroup3Int    = WorkBook.createCellStyle();
                CellStyleGroup3Int.setFont(FontGroup3);
                CellStyleGroup3Int.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup3Int.setBorderBottom(BorderGroup3);
                CellStyleGroup3Int.setBorderTop(BorderGroup3);
                CellStyleGroup3Int.setBorderRight(BorderGroup3);
                CellStyleGroup3Int.setBorderLeft(BorderGroup3);
                CellStyleGroup3Int.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup3Int.setDataFormat(format.getFormat("#,#0"));
                CellStyleGroup3Int.setWrapText(true);
                CellStyleGroup3Int.setFillForegroundColor(ColorGroup3.getIndex());
                CellStyleGroup3Int.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup3Int;

            case "403" :

                HSSFCellStyle    CellStyleGroup3Double    = WorkBook.createCellStyle();
                CellStyleGroup3Double.setFont(FontGroup3);
                CellStyleGroup3Double.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup3Double.setBorderBottom(BorderGroup3);
                CellStyleGroup3Double.setBorderTop(BorderGroup3);
                CellStyleGroup3Double.setBorderRight(BorderGroup3);
                CellStyleGroup3Double.setBorderLeft(BorderGroup3);
                CellStyleGroup3Double.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup3Double.setDataFormat(format.getFormat("#,#0.00"));
                CellStyleGroup3Double.setWrapText(true);
                CellStyleGroup3Double.setFillForegroundColor(ColorGroup3.getIndex());
                CellStyleGroup3Double.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup3Double;







            case "104" :
                HSSFCellStyle    CellStyleGroup4String    = WorkBook.createCellStyle();
                CellStyleGroup4String.setFont(FontGroup4);
                CellStyleGroup4String.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                CellStyleGroup4String.setBorderBottom(BorderGroup4);
                CellStyleGroup4String.setBorderTop(BorderGroup4);
                CellStyleGroup4String.setBorderRight(BorderGroup4);
                CellStyleGroup4String.setBorderLeft(BorderGroup4);
                CellStyleGroup4String.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup4String.setWrapText(true);
                CellStyleGroup4String.setFillForegroundColor(ColorGroup4.getIndex());
                CellStyleGroup4String.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup4String;

            case "304" :
                HSSFCellStyle    CellStyleGroup4Int    = WorkBook.createCellStyle();
                CellStyleGroup4Int.setFont(FontGroup4);
                CellStyleGroup4Int.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup4Int.setBorderBottom(BorderGroup4);
                CellStyleGroup4Int.setBorderTop(BorderGroup4);
                CellStyleGroup4Int.setBorderRight(BorderGroup4);
                CellStyleGroup4Int.setBorderLeft(BorderGroup4);
                CellStyleGroup4Int.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup4Int.setDataFormat(format.getFormat("#,#0"));
                CellStyleGroup4Int.setWrapText(true);
                CellStyleGroup4Int.setFillForegroundColor(ColorGroup4.getIndex());
                CellStyleGroup4Int.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup4Int;

            case "404" :

                HSSFCellStyle    CellStyleGroup4Double    = WorkBook.createCellStyle();
                CellStyleGroup4Double.setFont(FontGroup4);
                CellStyleGroup4Double.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup4Double.setBorderBottom(BorderGroup4);
                CellStyleGroup4Double.setBorderTop(BorderGroup4);
                CellStyleGroup4Double.setBorderRight(BorderGroup4);
                CellStyleGroup4Double.setBorderLeft(BorderGroup4);
                CellStyleGroup4Double.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup4Double.setDataFormat(format.getFormat("#,#0.00"));
                CellStyleGroup4Double.setWrapText(true);
                CellStyleGroup4Double.setFillForegroundColor(ColorGroup4.getIndex());
                CellStyleGroup4Double.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup4Double;

            case "105" :
                HSSFCellStyle    CellStyleGroup5String    = WorkBook.createCellStyle();
                CellStyleGroup5String.setFont(FontGroup5);
                CellStyleGroup5String.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                CellStyleGroup5String.setBorderBottom(BorderGroup5);
                CellStyleGroup5String.setBorderTop(BorderGroup5);
                CellStyleGroup5String.setBorderRight(BorderGroup5);
                CellStyleGroup5String.setBorderLeft(BorderGroup5);
                CellStyleGroup5String.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup5String.setWrapText(true);
                CellStyleGroup5String.setFillForegroundColor(ColorGroup5.getIndex());
                CellStyleGroup5String.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup5String;

            case "305" :
                HSSFCellStyle    CellStyleGroup5Int    = WorkBook.createCellStyle();
                CellStyleGroup5Int.setFont(FontGroup5);
                CellStyleGroup5Int.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup5Int.setBorderBottom(BorderGroup5);
                CellStyleGroup5Int.setBorderTop(BorderGroup5);
                CellStyleGroup5Int.setBorderRight(BorderGroup5);
                CellStyleGroup5Int.setBorderLeft(BorderGroup5);
                CellStyleGroup5Int.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup5Int.setDataFormat(format.getFormat("#,#0"));
                CellStyleGroup5Int.setWrapText(true);
                CellStyleGroup5Int.setFillForegroundColor(ColorGroup5.getIndex());
                CellStyleGroup5Int.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup5Int;

            case "405" :

                HSSFCellStyle    CellStyleGroup5Double    = WorkBook.createCellStyle();
                CellStyleGroup5Double.setFont(FontGroup5);
                CellStyleGroup5Double.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup5Double.setBorderBottom(BorderGroup5);
                CellStyleGroup5Double.setBorderTop(BorderGroup5);
                CellStyleGroup5Double.setBorderRight(BorderGroup5);
                CellStyleGroup5Double.setBorderLeft(BorderGroup5);
                CellStyleGroup5Double.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup5Double.setDataFormat(format.getFormat("#,#0.00"));
                CellStyleGroup5Double.setWrapText(true);
                CellStyleGroup5Double.setFillForegroundColor(ColorGroup5.getIndex());
                CellStyleGroup5Double.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup5Double;

            case "106" :
                HSSFCellStyle    CellStyleGroup6String    = WorkBook.createCellStyle();
                CellStyleGroup6String.setFont(FontGroup6);
                CellStyleGroup6String.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                CellStyleGroup6String.setBorderBottom(BorderGroup6);
                CellStyleGroup6String.setBorderTop(BorderGroup6);
                CellStyleGroup6String.setBorderRight(BorderGroup6);
                CellStyleGroup6String.setBorderLeft(BorderGroup6);
                CellStyleGroup6String.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup6String.setWrapText(true);
                CellStyleGroup6String.setFillForegroundColor(ColorGroup6.getIndex());
                CellStyleGroup6String.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup6String;

            case "306" :
                HSSFCellStyle    CellStyleGroup6Int    = WorkBook.createCellStyle();
                CellStyleGroup6Int.setFont(FontGroup6);
                CellStyleGroup6Int.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup6Int.setBorderBottom(BorderGroup6);
                CellStyleGroup6Int.setBorderTop(BorderGroup6);
                CellStyleGroup6Int.setBorderRight(BorderGroup6);
                CellStyleGroup6Int.setBorderLeft(BorderGroup6);
                CellStyleGroup6Int.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup6Int.setDataFormat(format.getFormat("#,#0"));
                CellStyleGroup6Int.setWrapText(true);
                CellStyleGroup6Int.setFillForegroundColor(ColorGroup6.getIndex());
                CellStyleGroup6Int.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup6Int;

            case "406" :

                HSSFCellStyle    CellStyleGroup6Double    = WorkBook.createCellStyle();
                CellStyleGroup6Double.setFont(FontGroup6);
                CellStyleGroup6Double.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyleGroup6Double.setBorderBottom(BorderGroup6);
                CellStyleGroup6Double.setBorderTop(BorderGroup6);
                CellStyleGroup6Double.setBorderRight(BorderGroup6);
                CellStyleGroup6Double.setBorderLeft(BorderGroup6);
                CellStyleGroup6Double.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyleGroup6Double.setDataFormat(format.getFormat("#,#0.00"));
                CellStyleGroup6Double.setWrapText(true);
                CellStyleGroup6Double.setFillForegroundColor(ColorGroup6.getIndex());
                CellStyleGroup6Double.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyleGroup6Double;

            default:
                HSSFCellStyle    CellStyle    = WorkBook.createCellStyle();
                CellStyle.setFont(FontGroup6);
                CellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                CellStyle.setBorderBottom(BorderGroup6);
                CellStyle.setBorderTop(BorderGroup6);
                CellStyle.setBorderRight(BorderGroup6);
                CellStyle.setBorderLeft(BorderGroup6);
                CellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                CellStyle.setDataFormat(format.getFormat("#,#0.00"));
                CellStyle.setWrapText(true);
                CellStyle.setFillForegroundColor(ColorGroup6.getIndex());
                CellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                return CellStyle;

        }

    }

    public HSSFColor getColorByLevel(long level, ReportTemplate reportTemplate)
    {
        switch (String.valueOf(level))
        {
            case "1" : return new HSSFColor.PALE_BLUE();
            case "2" : return new HSSFColor.YELLOW();
            case "3" : return new HSSFColor.WHITE();
            case "4" : return new HSSFColor.WHITE();
            case "5" : return new HSSFColor.WHITE();
            case "6" : return new HSSFColor.WHITE();
            default:return new HSSFColor.WHITE();

        }

    }

    public short getBorderTypeByLevel(long level, ReportTemplate reportTemplate)
    {
        switch (String.valueOf(level))
        {
            case "1" : return HSSFCellStyle.BORDER_THIN;
            case "2" : return HSSFCellStyle.BORDER_THIN;
            case "3" : return HSSFCellStyle.BORDER_THIN;
            case "4" : return HSSFCellStyle.BORDER_THIN;
            case "5" : return HSSFCellStyle.BORDER_THIN;
            case "6" : return HSSFCellStyle.BORDER_THIN;
            default  : return HSSFCellStyle.BORDER_THIN;
        }

    }

    public HSSFFont getFontByLevel(long level, ReportTemplate reportTemplate, HSSFWorkbook WorkBook)
    {
        switch (String.valueOf(level))
        {

            // TITLE
            case "10" :
                HSSFFont         Font10     = WorkBook.createFont();
                Font10.setFontHeightInPoints((short)12);
                Font10.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                Font10.setColor(HSSFColor.BLUE.index);
                return Font10;

            // HEADER
            case "11" :
                HSSFFont         Font11     = WorkBook.createFont();
                Font11.setFontHeightInPoints((short)8);
                Font11.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                return Font11;

            // SUMMARY
            case "12" :
                HSSFFont         Font12     = WorkBook.createFont();
                Font12.setFontHeightInPoints((short)9);
                Font12.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                Font12.setColor(HSSFColor.RED.index);
                return Font12;

            // FOOTER
            case "13" :
                HSSFFont         Font13     = WorkBook.createFont();
                Font13.setFontHeightInPoints((short)12);
                Font13.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                return Font13;

            case "1" :
                HSSFFont         FontGroup1     = WorkBook.createFont();
                FontGroup1.setFontHeightInPoints((short)8);
                FontGroup1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                return FontGroup1;

            case "2" :
                HSSFFont         FontGroup2     = WorkBook.createFont();
                FontGroup2.setFontHeightInPoints((short)8);
                FontGroup2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                return FontGroup2;

            case "3" :
                HSSFFont         FontGroup3     = WorkBook.createFont();
                FontGroup3.setFontHeightInPoints((short)8);
                FontGroup3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                return FontGroup3;

            case "4" :
                HSSFFont         FontGroup4     = WorkBook.createFont();
                FontGroup4.setFontHeightInPoints((short)8);
                return FontGroup4;

            case "5" :
                HSSFFont         FontGroup5     = WorkBook.createFont();
                FontGroup5.setFontHeightInPoints((short)7);
                return FontGroup5;

            case "6" :
                HSSFFont         FontGroup6     = WorkBook.createFont();
                FontGroup6.setFontHeightInPoints((short)7);
                return FontGroup6;

            default:
                HSSFFont         FontGroup     = WorkBook.createFont();
                FontGroup.setFontHeightInPoints((short)8);
                return FontGroup;

        }

    }

    public HSSFFont getFontByLevel(long level)
    {
        switch (String.valueOf(level))
        {

            // TITLE
            case "10" :
                return FontTitle;

            // HEADER
            case "11" :
                return FontHeader;

            // SUMMARY
            case "12" :
                return FontSum;

            // FOOTER
            case "13" :
                return FontFooter;

            case "1" :
                return FontGroup1;

            case "2" :
                return FontGroup2;

            case "3" :
                return FontGroup3;

            case "4" :
                return FontGroup4;

            case "5" :
                return FontGroup5;

            case "6" :
                return FontGroup6;

            default:
                return FontGroup6;

        }

    }



    // ============ _FILTER ACTIONS =========================================



    public LinkedHashMap<String,List<String>> getParametersByTemplate(ReportTemplate reportTemplate)
    {
        LinkedHashMap<String,List<String>> parameterS = new LinkedHashMap<>();

        for (FilterParameter filterParameter: reportTemplate.getFilterParameters())
        {
            drawParameter(parameterS,filterParameter);
        }

        if(reportTemplate.getOnDate()!=null)
        {
            List<String> Ids = new ArrayList<>();

            try
            {
                Ids.add(String.valueOf(reportTemplate.getOnDate().getTime()));
            }
            catch ( Exception ex )
            {
                System.out.println(ex);
            }

            parameterS.put("onDate",Ids);
        }

        List<String> groupFieldNames = new ArrayList<>();

        groupFieldNames.add(reportTemplate.getGroupType1().getField_name());
        groupFieldNames.add(reportTemplate.getGroupType2().getField_name());
        groupFieldNames.add(reportTemplate.getGroupType3().getField_name());
        groupFieldNames.add(reportTemplate.getGroupType4().getField_name());
        groupFieldNames.add(reportTemplate.getGroupType5().getField_name());

        parameterS.put("orderBy",groupFieldNames);

        return parameterS;
    }


    public void drawParameter(LinkedHashMap<String,List<String>> parameters, FilterParameter filterParameter)
    {

        if(filterParameter.getFilterParameterType().name()=="OBJECT_LIST")
        {
            ObjectList objectList = filterParameter.getObjectList();

            List<String> Ids = new ArrayList<>();

            for (ObjectListValue objectListValue: objectList.getObjectListValues())
            {
                Ids.add(objectListValue.getName());
            }

            parameters.put("r=in"+objectList.getGroupType().getField_name(),Ids);
        }

        if(filterParameter.getFilterParameterType().name()=="CONTENT_COMPARE")
        {
            List<String> Ids = new ArrayList<>();

            String comparedValue = filterParameter.getComparedValue();

            Ids.add(comparedValue);

            String fieldName = filterParameter.getFieldName();

            String comparatorShort = "";

            switch (filterParameter.getComparator().name())
            {
                case "EQUALS" :
                    comparatorShort = "r=eq";
                    break;

                case "NOT_EQUALS" :
                    comparatorShort = "r=ne";
                    break;

                case "GREATER_THEN" :
                    comparatorShort = "r=gt";
                    break;

                case "GREATER_THEN_OR_EQUALS" :
                    comparatorShort = "r=ge";
                    break;

                case "LESS_THEN" :
                    comparatorShort = "r=lt";
                    break;

                case "LESS_THEN_OR_EQUALS" :
                    comparatorShort = "r=le";
                    break;

                case "CONTAINS" :
                    comparatorShort = "r=yc";
                    break;

                case "DOES_NOT_CONTAIN" :
                    comparatorShort = "r=nc";
                    break;

                case "BEGINS" :
                    comparatorShort = "r=bs";
                    break;

                case "FINISHES" :
                    comparatorShort = "r=fs";
                    break;

                case "AFTER_DATE" :
                    comparatorShort = "r=ad";
                    break;

                case "AFTER_OR_ON_DATE" :
                    comparatorShort = "r=ao";
                    break;

                case "BEFORE_DATE" :
                    comparatorShort = "r=bd";
                    break;

                case "BEFORE_OR_ON_DATE" :
                    comparatorShort = "r=bo";
                    break;

                case "ON_DATE" :
                    comparatorShort = "r=od";
                    break;
            }

            parameters.put(comparatorShort+fieldName,Ids);

        }

    }


    public void applyParameters(LinkedHashMap<String,List<String>> parameters, Criteria criteria)
    {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String currentUserName = authentication.getName();

        User currentUser = this.userService.findByUsername(currentUserName);

        if(currentUser!=null)
        {

            if(this.filterParameterService.findByUser(currentUser).size()>0)
            {
                LinkedHashMap<String,List<String>> userParameters = new LinkedHashMap<>();

                for (FilterParameter filterParameter: this.filterParameterService.findByUser(currentUser))
                {
                    drawParameter(userParameters,filterParameter);
                }

                if(userParameters.size()>0)
                    parameters.putAll(userParameters);
            }

        }


        for (Map.Entry<String, List<String>> parameterInLoop : parameters.entrySet())
        {
            String parameterType = parameterInLoop.getKey();

            List<String> ids = parameterInLoop.getValue();

            String criteriaType = parameterType.substring(0,4);

            String propertyName = parameterType.substring(4,parameterType.length());

            switch (criteriaType)
            {
                case "r=in" :

                    List<Long> longIds = new ArrayList<>();

                    for (String id:ids)
                    {
                        longIds.add(Long.parseLong(id));
                    }

                    criteria.add(Restrictions.in(propertyName,longIds));

                    break;



                case "r=eq" :
                    criteria.add(Restrictions.eq(propertyName,Long.parseLong(ids.get(0))));
                    break;

                case "r=ne" :
                    criteria.add(Restrictions.ne(propertyName,ids.get(0)));
                    break;

                case "r=gt" :
                    criteria.add(Restrictions.gt(propertyName,Double.valueOf(ids.get(0))));
                    break;

                case "r=ge" :
                    criteria.add(Restrictions.ge(propertyName,Double.valueOf(ids.get(0))));
                    break;

                case "r=lt" :
                    criteria.add(Restrictions.lt(propertyName,Double.valueOf(ids.get(0))));
                    break;

                case "r=le" :
                    criteria.add(Restrictions.le(propertyName,Double.valueOf(ids.get(0))));
                    break;

                case "r=yc" :
                    criteria.add(Restrictions.like(propertyName,"%"+ids.get(0)+"%"));
                    break;

                case "r=nc" :
                    criteria.add(Restrictions.not(Restrictions.like(propertyName,"%"+ids.get(0)+"%")));
                    break;

                case "r=bs" :
                    criteria.add(Restrictions.like(propertyName,"%"+ids.get(0)))
                    ;
                    break;

                case "r=fs" :
                    criteria.add(Restrictions.like(propertyName,ids.get(0)+"%"));
                    break;

                case "r=ad" :
                    criteria.add(Restrictions.gt(propertyName, new Date(Long.parseLong(ids.get(0)))));
                    break;

                case "r=ao" :
                    criteria.add(Restrictions.ge(propertyName, new Date(Long.parseLong(ids.get(0)))));

                    break;

                case "r=bd" :
                    criteria.add(Restrictions.lt(propertyName, new Date(Long.parseLong(ids.get(0)))));
                    break;

                case "r=bo" :
                    criteria.add(Restrictions.le(propertyName, new Date(Long.parseLong(ids.get(0)))));
                    break;

                case "r=od" :
                    criteria.add(Restrictions.eq(propertyName, new Date(Long.parseLong(ids.get(0)))));
                    break;
            }


            if(parameterType.contains("orderBy"))
            {

                for (String fieldName:ids)
                {
                    criteria.addOrder(Order.asc(fieldName));
                }
            }
        }

    }




    // =========== _DRAW ACTIONS =============================================


    public void createRow(HSSFSheet Sheet, ContentParameter contentParameter)
    {
        try
        {
            int row_from = (contentParameter.getRow_from()>0) ? contentParameter.getRow_from()-1 : RowCount;
            int row_to   = (contentParameter.getRow_to()>0) ? contentParameter.getRow_to()-1 : RowCount;

            for( int rowCount=row_from; rowCount <= row_to; rowCount++ )
            {
                if(Sheet.getRow(rowCount)==null)
                {
                    Row = Sheet.createRow(rowCount);
                    Row.setRowNum(( short ) rowCount);

                    if(rowHeightMap.get(rowCount)!=null)
                    {
                        Row.setHeightInPoints(rowHeightMap.get(rowCount).floatValue());
                    }
                }

            }


        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }


    }

    public void drawCell(ReportTemplate reportTemplate, HSSFSheet Sheet, ContentParameter contentParameter , ReportData reportData)
    {
        try
        {
            int row_from = (contentParameter.getRow_from()>0) ? contentParameter.getRow_from()-1 : RowCount;
            int row_to   = (contentParameter.getRow_to()>0) ? contentParameter.getRow_to()-1 : RowCount;
            int col_from = (contentParameter.getCol_from()>0) ? contentParameter.getCol_from()-1 : ColumnCount;
            int col_to   = (contentParameter.getCol_to()>0) ? contentParameter.getCol_to()-1 : ColumnCount;

            if(col_to>=maxColumnCount) maxColumnCount=col_to;

            if(contentParameter.getRowType().name()=="TABLE_HEADER")
                if(row_to > lastRowCount) lastRowCount = row_to;

            if(!(row_from == row_to && col_from == col_to))
            {
                Sheet.addMergedRegion(new CellRangeAddress(row_from,row_to,col_from,col_to));
            }

            if(contentParameter.getRowType().name()=="PAGE_TITLE" || contentParameter.getRowType().name()=="TABLE_HEADER")
            {
                Cell = Sheet.getRow(row_from).createCell((int)col_from, HSSFCell.CELL_TYPE_STRING);

                if(contentParameter.getRowType().name()=="PAGE_TITLE")
                        Cell.setCellStyle(TitleString);
                else    Cell.setCellStyle(HeaderString);

                Cell.setCellValue(contentParameter.getConstantText().contains("(=") ? formatText(contentParameter.getConstantText(),reportTemplate) : contentParameter.getConstantText());

                ColumnCount++;
            }
            else
            {

                long level=1;

                if(contentParameter.getPosition()>0)
                {
                    Cell = Row.createCell(contentParameter.getPosition()-1);
                }
                else
                {
                    Cell = Row.createCell(ColumnCount);
                }


                Object object = null;

                if(contentParameter.getContentType().name()!="CONSTANT")
                {
                    String sMethodName = "get"+contentParameter.getFieldName().substring(0, 1).toUpperCase()+contentParameter.getFieldName().substring(1, contentParameter.getFieldName().length());

                    reportData.getClass().cast(reportData);
                    object = reportData.getClass().getMethod(sMethodName,null).invoke(reportData);


                }

                switch (contentParameter.getRowType().toString())
                {
                    case "TABLE_SUM": level = 12;break;
                    case "TABLE_GROUP1": level = 1;break;
                    case "TABLE_GROUP2": level = 2;break;
                    case "TABLE_GROUP3": level = 3;break;
                    case "TABLE_GROUP4": level = 4;break;
                    case "TABLE_GROUP5": level = 5;break;
                    case "TABLE_GROUP6": level = 6;break;
                }



                switch (contentParameter.getCellType().toString())
                {
                    case "TEXT" :
                        Cell.setCellStyle(getCellStyleByLevel(level,"string"));
                        if(object==null) object = contentParameter.getConstantText();
                        Cell.setCellValue(String.valueOf(object));

                        if(object.toString().matches("[0-9]+")&&contentParameter.getConstantInt()>0)
                        {
                            Cell.setCellValue(getNameByMapName(contentParameter.getConstantText(), Long.parseLong(object.toString())));
                        }

                        break;
                    case "DATE" :
                        Cell.setCellStyle(getCellStyleByLevel(level,"date"));
                        if(object==null) object = contentParameter.getConstantDate();
                        Cell.setCellValue(this.DateToString((Date)object));
                        break;
                    case "INTEGER" :
                        Cell.setCellStyle(getCellStyleByLevel(level,"int"));
                        if(object==null) object = contentParameter.getConstantInt();
                        Cell.setCellValue(Integer.valueOf(object.toString()));
                        break;
                    case "DOUBLE" :
                        Cell.setCellStyle(getCellStyleByLevel(level,"double"));
                        if(object==null) object = contentParameter.getConstantValue();
                        Cell.setCellValue(Double.valueOf(object.toString()));
                        break;
                    default :
                        Cell.setCellStyle(getCellStyleByLevel(level,"string"));
                        if(object==null) object = contentParameter.getConstantText();
                        Cell.setCellValue(String.valueOf(object));
                        break;
                }

                ColumnCount++;


            }


        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }


    }

    public void drawTitle(ReportTemplate reportTemplate, HSSFSheet Sheet, ReportData reportData )
    {

        RowCount = 0;
        ColumnCount = 0;

        try
        {
            for (ContentParameter contentParameter: reportTemplate.getContentParameters())
            {
                if(contentParameter.getRowType().name()=="PAGE_TITLE")
                {
                    createRow(Sheet,contentParameter);
                    drawCell(reportTemplate,Sheet,contentParameter,reportData);
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }

        RowCount = lastRowCount;
        ColumnCount = 0;
    }

    public void drawHeader(ReportTemplate reportTemplate, HSSFSheet Sheet, ReportData reportData )
    {
        RowCount = lastRowCount;
        ColumnCount = 0;

        try
        {
            for (ContentParameter contentParameter: reportTemplate.getContentParameters())
            {
                if(contentParameter.getRowType().name()=="TABLE_HEADER")
                {
                    createRow(Sheet,contentParameter);

                    drawCell(reportTemplate,Sheet,contentParameter,reportData);
                }

            }

        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }


        RowCount = lastRowCount;
        ColumnCount = 0;


    }

    public void drawSumRow(ReportTemplate reportTemplate, HSSFSheet Sheet, ReportData reportData )
    {

        ColumnCount=0;
        RowCount++;

        Row = Sheet.createRow(RowCount);
        Row.setRowNum(( short ) RowCount);
        Row.setHeightInPoints(20);

        try
        {
            for (ContentParameter contentParameter: reportTemplate.getContentParameters())
            {
                if(contentParameter.getRowType().name()=="TABLE_SUM")
                {
                    drawCell(reportTemplate,Sheet,contentParameter,reportData);
                }
            }

        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }

        if(Cell!=null)
            drawBlankCellsInRow(Sheet,RowCount,Cell.getCellStyle());
    }

    public void drawGroupRow(ReportTemplate reportTemplate, HSSFSheet Sheet, ReportData reportData, long level )
    {

        ColumnCount=0;
        RowCount++;

        Row = Sheet.createRow(RowCount);
        Row.setRowNum(( short ) RowCount);

        try
        {
            for (ContentParameter contentParameter: reportTemplate.getContentParameters())
            {
                if(contentParameter.getRowType().name().contains("TABLE_GROUP"+String.valueOf(level)))
                {
                    drawCell(reportTemplate,Sheet,contentParameter,reportData);
                }
            }

        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }

        if(Cell!=null)
        drawBlankCellsInRow(Sheet,RowCount,Cell.getCellStyle());

    }

    public void drawBlankCellsInRow(HSSFSheet Sheet, int rowNumber, HSSFCellStyle cellStyle)
    {
        try
        {
            for(int colNumber=0; colNumber <= maxColumnCount; colNumber++)
            {
                HSSFRow activeRow = Sheet.getRow(rowNumber);

                if(activeRow!=null)
                {
                    HSSFCell activeCell = activeRow.getCell(colNumber);

                    if(activeCell==null)
                    {
                        Cell = activeRow.createCell(colNumber);
                        Cell.setCellStyle(cellStyle);
                    }
                }



            }



        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }

    }

    public void drawSheet(ReportTemplate reportTemplate, HSSFSheet Sheet, ReportData reportData, HSSFWorkbook WorkBook, ReportTool reportTool)
    {

        reportTool.initReportSetup(reportTemplate,WorkBook);
        reportTool.setupSheetSettings(Sheet, reportTemplate);

        reportTool.drawTitle(reportTemplate,Sheet, reportData);
        reportTool.drawHeader(reportTemplate,Sheet,reportData);

        reportTool.drawSumRow(reportTemplate,Sheet,reportData);

        ReportData GroupData1[] = reportData.getChilds();

        boolean drawGroup1 = reportTemplate.getShowGroup1();
        boolean drawGroup2 = reportTemplate.getShowGroup2();
        boolean drawGroup3 = reportTemplate.getShowGroup3();
        boolean drawGroup4 = reportTemplate.getShowGroup4();
        boolean drawGroup5 = reportTemplate.getShowGroup5();
        boolean drawGroup6 = reportTemplate.getShowGroup6();

        for(int x=0;x<GroupData1.length;x++)
        {
            if(drawGroup1)
            reportTool.drawGroupRow(reportTemplate,Sheet, GroupData1[x],1);

            ReportData GroupData2[] = GroupData1[x].getChilds();

            for(int y=0;y<GroupData2.length;y++)
            {
                if(drawGroup2)
                reportTool.drawGroupRow(reportTemplate,Sheet, GroupData2[y],2);

                ReportData GroupData3[] = GroupData2[y].getChilds();

                for(int z=0;z<GroupData3.length;z++)
                {
                    if(drawGroup3)
                    reportTool.drawGroupRow(reportTemplate,Sheet, GroupData3[z],3);

                    ReportData GroupData4[] = GroupData3[z].getChilds();

                    for(int a=0;a<GroupData4.length;a++)
                    {
                        if(drawGroup4)
                        reportTool.drawGroupRow(reportTemplate,Sheet, GroupData4[a],4);

                        ReportData GroupData5[] = GroupData4[a].getChilds();

                        for(int b=0;b<GroupData5.length;b++)
                        {
                            if(drawGroup5)
                            reportTool.drawGroupRow(reportTemplate,Sheet, GroupData5[b],5);

                            ReportData GroupData6[] = GroupData5[b].getChilds();

                            for(int c=0;c<GroupData6.length;c++)
                            {
                                if(drawGroup6)
                                    reportTool.drawGroupRow(reportTemplate,Sheet, GroupData6[c],6);

                            }


                        }


                    }

                }

            }

        }

//        reportTool.drawSumRow(reportTemplate,Sheet,reportData);
    }





    // ============ _GROUP TYPE ACTIONS =======================================

    public long getIdByGroupType(GroupType groupType, ReportView reportView)
    {
        long idByGroupType=0;

        Object object = null;

        try
        {
            String sMethodName = "get"+groupType.getField_name().substring(0, 1).toUpperCase()+groupType.getField_name().substring(1, groupType.getField_name().length());

            reportView.getClass().cast(reportView);
            object = reportView.getClass().getMethod(sMethodName,null).invoke(reportView);
            idByGroupType = Long.parseLong(object.toString());

        }
        catch (Exception ex)
        {

        }

        return  idByGroupType;
    }

    public String getNameByGroupType(GroupType groupType, ReportView reportView)
    {
        String row_name = groupType.getRow_name();

        int loopCount = 0;

        if(row_name.contains("(="))
        {
            do {
                loopCount++;
                row_name = generateNameOfRow(row_name,groupType,reportView);
                if(loopCount>10) break;
            }
            while(row_name.contains("(="));
        }


        return  row_name;
    }

    public String getNameByMapName(String mapName, long id)
    {

        String nameByGroupType="";


        nameByGroupType = referenceMap.get(mapName).get(id);

        return  nameByGroupType;
    }

    public String generateNameOfRow(String row_name, GroupType groupType, ReportView reportView)
    {
        Object object = null;


        int firstPositionOfSpecialChar = row_name.lastIndexOf("(=");
        int lastPositionOfSpecialChar = row_name.lastIndexOf("=)");

        if(firstPositionOfSpecialChar>=0)
        {
            String variableString = row_name.substring(firstPositionOfSpecialChar+2,lastPositionOfSpecialChar);

            if(variableString.contains("Map"))
            {
                String mapName = variableString.substring(0,variableString.lastIndexOf("Map"));

                String returnText = "";

                if(this.getIdByGroupType(groupType,reportView)>0) returnText = row_name.replace("(="+variableString+"=)",referenceMap.get(mapName).get(this.getIdByGroupType(groupType,reportView)));

                return returnText;
            }
            else
            {
                try
                {
                    String sMethodName = "get"+variableString.substring(0, 1).toUpperCase()+variableString.substring(1, variableString.length());

                    reportView.getClass().cast(reportView);
                    object = reportView.getClass().getMethod(sMethodName,null).invoke(reportView);

                    return row_name.replace("(="+variableString+"=)",object.toString());

                }
                catch (Exception ex)
                {

                }
            }

        }

        return row_name;
    }

    public String getMapNameOfGroupType(GroupType groupType)
    {
        String row_name = groupType.getRow_name();

        int firstPositionOfSpecialChar = row_name.lastIndexOf("(=");
        int lastPositionOfSpecialChar = row_name.lastIndexOf("=)");

        if(firstPositionOfSpecialChar>=0)
        {
            String variableString = row_name.substring(firstPositionOfSpecialChar+2,lastPositionOfSpecialChar);

            if(variableString.contains("Map"))
            {
                return variableString.substring(0,variableString.lastIndexOf("Map"));
            }
            else
            {
                return "";
            }

        }

        return "";
    }

}

