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
 * Custom JSF Validator for Email input
 * 
 * @author PrimeFaces, Zheng Hua Zhu
 * 
 */
@FacesValidator("emailValidator")
public class EmailValidator implements Validator, ClientValidator {
 
    private Pattern pattern;
    private FacesMessage message;
  
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }
 
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(value == null) {
            return;
        }
         
        if(!pattern.matcher(value.toString()).matches()) {
            message = Messages.getMessage(
                    "com.group2.bambootemple.bundles.messages", "EmailVal", null);
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
        return "emailValidator";
    }
     
}
