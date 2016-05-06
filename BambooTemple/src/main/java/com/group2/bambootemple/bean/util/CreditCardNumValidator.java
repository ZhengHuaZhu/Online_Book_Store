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
 * @author Marjo
 */
@FacesValidator("creditCardNumValidator")

public class CreditCardNumValidator implements Validator, ClientValidator {

    private Pattern visaCardNumPattern;
    private Pattern masterCardNumPattern;

    private FacesMessage message;

    private static final String VISACARDNUMBER_PATTERN = "^4[0-9]{12}(?:[0-9]{3})?$";
    private static final String MASTERCARDNUMBER_PATTERN = "^5[1-5][0-9]{14}$";

    public CreditCardNumValidator() {
        visaCardNumPattern = Pattern.compile(VISACARDNUMBER_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }
        if (!visaCardNumPattern.matcher(value.toString()).matches() 
                || !masterCardNumPattern.matcher(value.toString()).matches()) {
            message = Messages.getMessage(
                    "com.group2.bambootemple.bundles.messages", "Invalid Card Number", null);
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
        return "creditCardNumValidator";
    }
}
