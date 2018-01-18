package kg.gov.mf.loan.output.report.converter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.*;
 
@Service
public class ReportFormatter implements Formatter<Report> {

    @Autowired
    ReportService reportService;//Service -> DB

    @Override
    public String print(Report object, Locale locale) {
    	
        return (object != null ? (String.valueOf(object.getId())) : "");
    }

	@Override
	public Report parse(String text, Locale locale) throws ParseException {
		
		Integer id = Integer.valueOf(text);
		
        return this.reportService.findById(id);
		
		
	}
}