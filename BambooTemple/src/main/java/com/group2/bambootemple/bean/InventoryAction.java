package com.group2.bambootemple.bean;

import com.group2.bambootemple.bean.entity.Inventory;
import com.group2.bambootemple.persistence.InventoryDAOImpl;
import java.io.Serializable;
import java.sql.SQLException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This is inventory backing bean. 
 * The purpose of this class is to display a selected book by setting the bookId 
 * and return an inventory bean.
 * @author Marjorie Morales
 */
@Named
@SessionScoped
public class InventoryAction implements Serializable {

    @Inject
    private InventoryDAOImpl inventoryDAO;
    private int bookId = 0;
    private Inventory inventory = new Inventory();
    
    public Inventory getInventory() throws SQLException {
        if (bookId != 0) {
            inventory = inventoryDAO.findByBookId(bookId);
            return inventory;
        } else {
            return null;
        }
    }

    
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
