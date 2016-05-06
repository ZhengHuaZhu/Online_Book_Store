package com.group2.bambootemple.bean;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * This is a small utility class to retrieve the user's current page and 
 * redirect them to the appropriate help page. 
 *
 * @author Derek Herbert
 */
@Named
@SessionScoped
public class RedirectToHelpPage implements Serializable {
    private String helpPage;
    
    public String setHelpPage(String currentPage) {
        helpPage = currentPage.split("\\.")[0] + "Help.xhtml";
        return helpPage;
    }
}
