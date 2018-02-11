package kg.gov.mf.loan.output.report.converter;

import kg.gov.mf.loan.output.report.model.ObjectList;
import kg.gov.mf.loan.output.report.service.ObjectListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Locale;

@Service
public class ObjectListFormatter implements Formatter<ObjectList> {

    @Autowired
    ObjectListService objectListService;//Service -> DB

    @Override
    public String print(ObjectList object, Locale locale) {
    	
        return (object != null ? (String.valueOf(object.getId())) : "");
    }

	@Override
	public ObjectList parse(String text, Locale locale) throws ParseException {
		
		Integer id = Integer.valueOf(text);
		
        return this.objectListService.findById(id);
		
		
	}
}