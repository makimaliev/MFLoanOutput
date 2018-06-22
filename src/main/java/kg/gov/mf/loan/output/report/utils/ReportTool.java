package kg.gov.mf.loan.output.report.utils;

import org.springframework.cglib.core.Local;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReportTool {

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





}
