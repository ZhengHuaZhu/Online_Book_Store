package com.group2.bambootemple.bean;

import com.group2.bambootemple.persistence.UserDAOImpl;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Mehdi
 */
@Named
@SessionScoped
public class EmailAddressExistsValidator implements Validator, Serializable{
    
    @Inject
    private UserDAOImpl userDAOImp;
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
        if(value == null) {
            return;
        }
        
        try {
            if(userDAOImp.checkExistingUserByEmail(value.toString())) { 
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error",
                        value + " is already in use."));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmailAddressExistsValidator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
