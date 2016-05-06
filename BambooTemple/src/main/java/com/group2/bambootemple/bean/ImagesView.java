package com.group2.bambootemple.bean;

import com.group2.bambootemple.bean.entity.Inventory;
import com.group2.bambootemple.persistence.InventoryDAOImpl;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;

/**
 * This class is used to provide the carousels on the index.xhtml page their content. 
 * It loads the appropriate data from the database and assigns them into variables
 * which index.xhtml accesses and displays. It loads a list of Inventory beans on sale,
 * a list of the 3 most recent Inventory rows in the database and if the user is logged on
 * or has a cookie, a list of Inventory beans of the user's last visited genre. 
 * 
 * @author Derek Herbert
 */

@Named
@RequestScoped
public class ImagesView {
    private List<Inventory> specials;
    private List<Inventory> suggested;
    private List<Inventory> recent;
    
    @Inject
    private InventoryDAOImpl dao;
    
    @Inject
    private Login login;
     
    /**
     * Load all the Inventory lists with data from the database. 
     */
    @PostConstruct
    public void init() {             
        try {
            String genre;
            //Load a list of Inventory beans on sale (sale price is not 0)
            specials = dao.findBySalePrice(new BigDecimal("0.01"), new BigDecimal("9999.99"));
            
            //Load a list of Inventory beans based on the user's last visited genre (assuming there is one)  
            if(login.getUser() == null) {
                Object genreCookie = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().get("GenreCookie");
                if(genreCookie != null) {
                    System.out.println("COOKIE VALUE: " + ((Cookie)genreCookie).getValue());
                    suggested = dao.findByGenre(((Cookie)genreCookie).getValue());
                } 
            }
            else if((genre = login.getUser().getGenre()) != null && genre.isEmpty()){
                suggested = dao.findByGenre(login.getUser().getGenre());
            }
            
            //Load a list of the last 3 Inventory beans added to the database
            recent = dao.findRecentlyAdded(3);
                        
            
        } catch (SQLException ex) {
            Logger.getLogger(ImagesView.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
 
    public List<Inventory> getSpecials() {              
        return specials;
    }
    
    public List<Inventory> getSuggested() {              
        return suggested;
    }
    
    public List<Inventory> getRecent() {
        return recent;
    }
}