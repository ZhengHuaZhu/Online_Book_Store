package com.group2.bambootemple.bean;

import com.group2.bambootemple.bean.entity.Inventory;
import com.group2.bambootemple.bean.entity.Item;
import com.group2.bambootemple.bean.entity.Order;
import com.group2.bambootemple.bean.entity.User;
import com.group2.bambootemple.persistence.InventoryDAOImpl;
import com.group2.bambootemple.persistence.ItemDAOImpl;
import com.group2.bambootemple.persistence.OrderDAOImpl;
import com.group2.bambootemple.persistence.UserDAOImpl;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * 
 * The purpose of this backing bean is to display the an order information.
 * 
 * @author Marjorie Morales
 */
@Named
@RequestScoped
public class Invoice implements Serializable{
    
    private int order_id;
    private Order order;
    private double totalOrderTaxes = 0.00;
    private double subTotal = 0.00;
    private double orderTotal = 0.00;
    private double itemTotalPrice = 0.00;
    @Inject
    private OrderDAOImpl orderDAO;
    @Inject
    private UserDAOImpl userDAO;
    @Inject
    private ItemDAOImpl itemDAO;
    @Inject
    private InventoryDAOImpl inventoryDAO;
    private List<Item> items;
    
    
    public int getOrder_id() {
        return order_id;
    }
    
    public void setOrder_id(int order_id) {
        this.order_id =  order_id;
    }
    /**
     * Get an order.
     * @return Order bean
     * @throws SQLException 
     */
    public Order getOrder() throws SQLException{
        order = orderDAO.findByID(order_id);
        return order;
    }
    /**
     * Get the customer(user) who made the order.
     * @return User bean
     * @throws SQLException 
     */
    public User getUser() throws SQLException{
        User user = userDAO.findByID(order.getUserID());
        return user;
    }
    /**
     * Get all the books bought in an order.
     * @return List<Item> items
     * @throws SQLException 
     */
    public List<Item> getOrderItems() throws SQLException{
        items = itemDAO.findByOrderID(order_id);
        return items;
    }
    /**
     * Get he book title of an item.
     * @param item
     * @return book title
     * @throws SQLException 
     */
    public String getBookTitle(Item item) throws SQLException{
        Inventory book = inventoryDAO.findByBookId(item.getBookID());
        return book.getTitle();
    }
    /**
     * Get the total taxes of an item.
     * @param item
     * @return total taxes
     */
    public double getItemTotalTaxes(Item item){
        double gst = item.getGst();
        double hst = item.getHst();
        double pst = item.getPst();
        
        double totalTaxes = gst + hst + pst;
            totalOrderTaxes += totalTaxes;
        return totalTaxes;
    }
    
    public double getTotalOrderTaxes (){
        return totalOrderTaxes;
    }
    /**
     * Get the subtotal of an order(total price of all the books in the order, 
     * not included the taxes.)
     * @return 
     */
    public double getSubTotal(){
        
        for(Item item : items)
            subTotal += item.getPrice();
        return subTotal;
    }
    
    /**
     * Get the total amount price of an order(taxes included).
     * @return 
     */
    public double getOrderTotal(){
        
        orderTotal = subTotal + totalOrderTaxes;
        return orderTotal;
    }
    
    /**
     *  Get the total price of a book(price + taxes)
     * @param item
     * @return 
     */
    public double getItemTotalPrice(Item item){
        double gst = item.getGst();
        double hst = item.getHst();
        double pst = item.getPst();
        
        double totalTaxes = gst + hst + pst;
        
        itemTotalPrice = item.getPrice() + totalTaxes;
        return itemTotalPrice;
    }
}
