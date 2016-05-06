package com.group2.bambootemple.bean.util;

import com.group2.bambootemple.bean.entity.User;
import com.group2.bambootemple.persistence.UserDAOImpl;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import org.primefaces.validate.ClientValidator;

/**
 * @author Zheng Hua Zhu
 * Custom JSF Validator for login credential
 */
@FacesValidator("credentialValidator")
public class CredentialValidator implements Validator, ClientValidator {
    private User user;
    private FacesMessage message;
   
    @Inject
    private UserDAOImpl instance;
 
    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(value == null) {
            return;
        }
        try { 
            user=instance.findByEmailPassword();
        } catch (SQLException ex) {
            Logger.getLogger(CredentialValidator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(user==null) {
            message = Messages.getMessage(
                    "com.group2.bambootemple.bundles.messages", "InvalidLoginCredential", null);
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
        return "credentialValidator";
    }
     
}