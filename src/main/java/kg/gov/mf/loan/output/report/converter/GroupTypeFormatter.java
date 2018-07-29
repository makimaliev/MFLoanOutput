package kg.gov.mf.loan.output.report.converter;

import kg.gov.mf.loan.output.report.model.GroupType;
import kg.gov.mf.loan.output.report.service.GroupTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Locale;

@Service
public class GroupTypeFormatter implements Formatter<GroupType> {

    @Autowired
    GroupTypeService groupTypeService;//Service -> DB

    @Override
    public String print(GroupType object, Locale locale) {
    	
        return (object != null ? (String.valueOf(object.getId())) : "");
    }

	@Override
	public GroupType parse(String text, Locale locale) throws ParseException {
		
		Integer id = Integer.valueOf(text);
		
        return this.groupTypeService.findById(id);
		
		
	}
}