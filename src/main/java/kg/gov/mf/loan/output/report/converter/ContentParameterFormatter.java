package kg.gov.mf.loan.output.report.converter;

import kg.gov.mf.loan.output.report.model.ContentParameter;
import kg.gov.mf.loan.output.report.service.ContentParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Locale;

@Service
public class ContentParameterFormatter implements Formatter<ContentParameter> {

    @Autowired
    ContentParameterService contentParameterService;//Service -> DB

    @Override
    public String print(ContentParameter object, Locale locale) {
    	
        return (object != null ? (String.valueOf(object.getId())) : "");
    }

	@Override
	public ContentParameter parse(String text, Locale locale) throws ParseException {
		
		Integer id = Integer.valueOf(text);
		
        return this.contentParameterService.findById(id);
		
		
	}
}