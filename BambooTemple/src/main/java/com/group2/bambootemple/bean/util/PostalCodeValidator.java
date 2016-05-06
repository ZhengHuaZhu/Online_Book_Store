package com.group2.bambootemple.bean.util;

import java.util.Map;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.primefaces.validate.ClientValidator;

/**
 *
 * @author zhu zhenghua
 */
@FacesValidator("postalCodeValidator")
public class PostalCodeValidator implements Validator, ClientValidator {
 
    private Pattern pattern;
    private FacesMessage message;
  
    private static final String POSTALCODE_PATTERN = 
            "[ABCEGHJKLMNPRSTVXY][0-9][ABCEGHJKLMNPRSTVWXYZ] ?[0-9][ABCEGHJKLMNPRSTVWXYZ][0-9]";
    
    public PostalCodeValidator() {
        pattern = Pattern.compile(POSTALCODE_PATTERN);
    }
 
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(value == null) {
            return;
        }       
        if(!pattern.matcher(value.toString().toUpperCase()).matches()) {
            message = Messages.getMessage(
                    "com.group2.bambootemple.bundles.messages", "PostalVal", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
 
    @Override
    public Map<String, Object> getMetadata() {
        return null;
    }
 
    @Override
    public String getValidatorId() {
        return "postalCodeValidator";
    }
}
