package kg.gov.mf.loan.output.report.converter;

import kg.gov.mf.loan.output.report.model.GenerationParameter;
import kg.gov.mf.loan.output.report.service.GenerationParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Locale;

@Service
public class GenerationParameterFormatter implements Formatter<GenerationParameter> {

    @Autowired
    GenerationParameterService generationParameterService;//Service -> DB

    @Override
    public String print(GenerationParameter object, Locale locale) {
    	
        return (object != null ? (String.valueOf(object.getId())) : "");
    }

	@Override
	public GenerationParameter parse(String text, Locale locale) throws ParseException {
		
		Integer id = Integer.valueOf(text);
		
        return this.generationParameterService.findById(id);
		
		
	}


}