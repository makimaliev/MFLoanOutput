package kg.gov.mf.loan.output.report.converter;

import kg.gov.mf.loan.output.report.model.GenerationParameterType;
import kg.gov.mf.loan.output.report.service.GenerationParameterTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Locale;

@Service
public class GenerationParameterTypeFormatter implements Formatter<GenerationParameterType> {

    @Autowired
    GenerationParameterTypeService generationParameterTypeService;//Service -> DB

    @Override
    public String print(GenerationParameterType object, Locale locale) {
    	
        return (object != null ? (String.valueOf(object.getId())) : "");
    }

	@Override
	public GenerationParameterType parse(String text, Locale locale) throws ParseException {
		
		Integer id = Integer.valueOf(text);
		
        return this.generationParameterTypeService.findById(id);
		
		
	}
}