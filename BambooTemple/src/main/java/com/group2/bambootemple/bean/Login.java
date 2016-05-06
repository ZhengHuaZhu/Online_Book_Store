package com.group2.bambootemple.bean;

import com.group2.bambootemple.bean.entity.User;
import com.group2.bambootemple.persistence.UserDAOImpl;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * - This class serves as a backing bean(controller) for interacting with UI components and navigation business logic.
 * 
 * @author 1334262
 * @author Mehdi Moodi
 */
@Named
@SessionScoped
public class Login implements Serializable {

    private User user;
    private String prevPage = "";
    private String nextPage;
    private boolean invalidCredential = false;
    @Inject
    private UserDAOImpl userDAOImpl;
    @Inject
    private ShoppingCart shoppingCart;

    public String login() throws SQLException {
        // check if user credential is existing in the DB
        user = userDAOImpl.findByEmailPassword();
        if (user == null) {
            invalidCredential = true;
            nextPage = null;
        } else if (!prevPage.isEmpty()) {
            nextPage = prevPage;
            prevPage = "";
        } else {
            nextPage = "index";
        }
        return nextPage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isInvalidCredential() {
        return invalidCredential;
    }

    // nullify the user object accross the application
    public String logout() throws IOException, SQLException {
        setUser(null);
//        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        setPrevPage("");
        invalidCredential = false;
        // when a user logout, empty the shopping cart
        shoppingCart.emptyCartItems();
        return "index";

    }

    public void setPrevPage(String prevPage) {
        this.prevPage = prevPage;
    }

    public void isAdmin(ComponentSystemEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (user == null || user.getIsAdmin() == 0) {
            ConfigurableNavigationHandler nav
                    = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("access-denied");
        }
    }
}
