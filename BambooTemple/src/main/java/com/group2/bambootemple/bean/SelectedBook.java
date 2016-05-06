package com.group2.bambootemple.bean;

import com.group2.bambootemple.bean.entity.Inventory;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Mehdi Moodi
 */
@Named
@SessionScoped
public class SelectedBook implements Serializable {

    private Inventory inventory;    
    
    public SelectedBook() {
        inventory = new Inventory();        
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;        
    }

    public void setBookToNew() {
        inventory = new Inventory();
    }    
}
