package kg.gov.mf.loan.output.report.converter;

import kg.gov.mf.loan.output.report.model.OutputParameter;
import kg.gov.mf.loan.output.report.service.OutputParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Locale;

@Service
public class OutputParameterFormatter implements Formatter<OutputParameter> {

    @Autowired
    OutputParameterService outputParameterService;//Service -> DB

    @Override
    public String print(OutputParameter object, Locale locale) {
    	
        return (object != null ? (String.valueOf(object.getId())) : "");
    }

	@Override
	public OutputParameter parse(String text, Locale locale) throws ParseException {
		
		Integer id = Integer.valueOf(text);
		
        return this.outputParameterService.findById(id);
		
		
	}
}