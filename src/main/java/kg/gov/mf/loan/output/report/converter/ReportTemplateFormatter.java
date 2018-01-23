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
public class ReportTemplateFormatter implements Formatter<ReportTemplate> {

    @Autowired
    ReportTemplateService reportTemplateService;//Service -> DB

    @Override
    public String print(ReportTemplate object, Locale locale) {
    	
        return (object != null ? (String.valueOf(object.getId())) : "");
    }

	@Override
	public ReportTemplate parse(String text, Locale locale) throws ParseException {
		
		Integer id = Integer.valueOf(text);
		
        return this.reportTemplateService.findById(id);
		
		
	}
}