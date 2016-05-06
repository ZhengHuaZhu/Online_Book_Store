package com.group2.bambootemple.bean;

import com.group2.bambootemple.bean.entity.Inventory;
import com.group2.bambootemple.persistence.InventoryDAOImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;

/**
 * - This class contains methods to read and write cookies Cookies can be read at
 * any time but can only be written to before any HTML is delivered to the
 * browser. For that reason creating cookies is always a preRenderView type
 * event
 * 
 * - This bean is to create a cookie that records the last browsed book genre for a visitor who may come back late again to the bookstore.
 *
 * @author Ken, Zheng Hua Zhu
 */
@Named
@RequestScoped
public class PreRenderViewBean {    
    @Inject
    private Login login;
    
    @Inject
    private SearchQuery sq;

    /**
     * Look for a cookie
     */
    public void checkCookies() {
        FacesContext context = FacesContext.getCurrentInstance();

        // Retrieve a specific cookie
        Object lastBrowsedGenre = context.getExternalContext().getRequestCookieMap().get("GenreCookie");
        if (lastBrowsedGenre != null) {
            System.out.println("Name: " + ((Cookie) lastBrowsedGenre).getName());
            System.out.println("Value: " + ((Cookie) lastBrowsedGenre).getValue());
        }
        writeCookie();
    }

    /**
     * Let's write a cookie!
     * http://docs.oracle.com/javaee/7/api/javax/faces/context/ExternalContext.html#addResponseCookie(java.lang.String,
     * java.lang.String, java.util.Map)
     */
    public void writeCookie() {
        FacesContext context = FacesContext.getCurrentInstance();
        String whichGenre = sq.getKeyword();
        context.getExternalContext().addResponseCookie("GenreCookie", whichGenre, null);
        if(login.getUser() != null) {
            login.getUser().setGenre(whichGenre);
            System.out.println("USER GENRE ADDED");
            //System.out.println(login.)
        }
    }
}
