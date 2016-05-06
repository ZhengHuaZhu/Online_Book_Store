package com.group2.bambootemple.bean;

import com.group2.bambootemple.bean.entity.Order;
import com.group2.bambootemple.bean.entity.Inventory;
import com.group2.bambootemple.bean.entity.Item;
import com.group2.bambootemple.bean.entity.Tax;
import com.group2.bambootemple.bean.entity.User;
import com.group2.bambootemple.persistence.InventoryDAOImpl;
import com.group2.bambootemple.persistence.ItemDAOImpl;
import com.group2.bambootemple.persistence.OrderDAOImpl;
import com.group2.bambootemple.persistence.TaxDAOImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This class is a shopping cart backing bean.
 * The purpose of this backing bean is to an a book to shopping cart 
 * and an remove or remove all items from the shopping cart. 
 * 
 * @author Marjorie Morales
 */
@Named
@SessionScoped
public class ShoppingCart implements Serializable {

    @Inject
    private TaxDAOImpl taxDAO;
    @Inject
    private Login login;
    @Inject
    private InventoryAction inventoryAction;
    @Inject
    private InventoryDAOImpl inventoryDAO;
    @Inject
    private OrderDAOImpl orderDAO;
    @Inject
    private ItemDAOImpl itemDAO;
    private BigDecimal subTotal;
    private BigDecimal totalPrice;
    private List<Inventory> shoppingCartItems = new ArrayList<>();
    private BigDecimal HST;
    private BigDecimal GST;
    private BigDecimal PST;
    private BigDecimal shoppingCartHstTax;
    private BigDecimal shoppingCartGstTax;
    private BigDecimal shoppingCartPstTax;
    private Tax tax;

    /**
     *
     * This method gets all shoppingCartItems(books) in the shopping cart.
     *
     * @return shoppingCartItems - which is a list of inventory bean
     */
    public List<Inventory> getCartItems() throws SQLException {
        //check if someone is log in
        if (login.getUser() != null) {
            //make sure that all the items in the cart has not yet been bought
            removeBoughtItems();
            return shoppingCartItems;
        } else {
            return shoppingCartItems;
        }
    }
    
    /**
     * 
     * Empty the shopping cart.
     * 
     * @throws SQLException 
     */
    public void emptyCartItems() throws SQLException {
        this.shoppingCartItems = new ArrayList<>();
    }

    /**
     * This method checks if all the books on the shopping cart were not yet
     * bought by the user. If a book on the shopping cart was already bought by
     * that user, the book is remove from the shoppingCartItems.
     *
     * @throws SQLException
     */
    public void removeBoughtItems() throws SQLException {
        int user_id = login.getUser().getUser_id();
        List<Order> orders = orderDAO.findByUserID(user_id);
        for (Order order : orders) {
            List<Item> items = itemDAO.findByOrderID(order.getOrderID());
            for (Inventory shoppingCartItem : shoppingCartItems) {
                for (Item item : items) {
                    if (shoppingCartItem.getBookId() == item.getBookID()) {
                        shoppingCartItems.remove(shoppingCartItem);
                        break;
                    }
                }
            }
        }
    }
    /**
     * Get the shopping cart total HST taxes.
     * @return 
     */
    public BigDecimal getShoppingCartHstTax() {
        shoppingCartHstTax = new BigDecimal(0.00);
        shoppingCartHstTax = subTotal.multiply(HST);

        return shoppingCartHstTax;
    }

    /**
     * Get the shopping cart total GST taxes.
     * @return 
     */
    public BigDecimal getShoppingCartGstTax() {
        shoppingCartGstTax = new BigDecimal(0.00);
        shoppingCartGstTax = subTotal.multiply(GST);
        return shoppingCartGstTax;
    }

    /**
     * Get the shopping cart total PST taxes.
     * @return 
     */
    public BigDecimal getShoppingCartPstTax() {
        shoppingCartPstTax = new BigDecimal(0.00);
        shoppingCartPstTax = subTotal.multiply(PST);
        return shoppingCartPstTax;
    }

    /**
     * Set the taxes a customer should pay, depends on his/her province.
     * @throws SQLException 
     */
    public void setTaxInCustomerProvince() throws SQLException {
        User logginUser = login.getUser();
        
        this.tax = taxDAO.findByProvince(logginUser.getProvince());
        
        setHstTax();
        setGstTax();
        setPstTax();
    }

    public BigDecimal getHstTax() {
        return HST;
    }

    public void setHstTax() {
        this.HST = new BigDecimal(tax.getHst()/100);
    }

    public BigDecimal getGstTax() {
        return GST;
    }

    public void setGstTax() {
        this.GST = new BigDecimal(tax.getGst() / 100);
    }

    public BigDecimal getPstTax() {
        return PST;
    }

    public void setPstTax() {
        this.PST = new BigDecimal(tax.getPst()/100);
    }

    /**
     * This method add a book to the shopping cart(List shoppingCartItems).
     * 
     *
     * @param bookId
     * @return shoppingCart - the xhtml of the shopping cart
     * @throws SQLException
     */
    public String addItem(int bookId) throws SQLException {
        Inventory inventory = inventoryDAO.findByBookId(bookId);
        //check if the inventory is null
        if (inventory == null) {
            return null;
        }
        // the cart is not empty, check if the cart does not aready have the book.
        else if (!isAlreadyAdded(inventory)) {
              shoppingCartItems.add(inventory);
        }
        return "shoppingCart";
    }
    /**
     * This method checks if the book(inventory) is already added in the 
     * shopping cart.
     * 
     * @param inventory
     * @return 
     */
    public boolean isAlreadyAdded(Inventory inventory){
        if(shoppingCartItems.contains(inventory)){
            return true;
        }else
            return false;
    }

    /**
     *
     * This method removes a book(an item) from the shopping cart(List
     * shoppingCartItems).
     *
     * @param inventory - an inventory bean
     * @return string - xhtml page
     */
    public String deleteItem(Inventory inventory) {
        this.shoppingCartItems.remove(inventory);
        // if the shoppingCartItems list is empty go back to the shopping cart xhtml.
        if (shoppingCartItems.isEmpty()) {
            return "index";
        } // else don't rederict to other page.
        else {
            return "";
        }

    }

    /**
     **
     * This method calculates the total price of all books in the shopping cart.
     *
     * @return subTotal - which is the total price of the shoppingCartItems
     * before tax.
     */
    public BigDecimal getSubTotal() throws SQLException {
        if(login.getUser() != null)
            setTaxInCustomerProvince();
        
        BigDecimal bookPrice = new BigDecimal(0.00);
        if (shoppingCartItems != null) {
            subTotal = new BigDecimal(0.00);
            for (int i = 0; i < shoppingCartItems.size(); i++) {
                bookPrice = shoppingCartItems.get(i).getLPrice();
                subTotal = subTotal.add(bookPrice);
            }
            return subTotal;
        }
        return subTotal;
    }
    /**
     * Get the shopping cart total prices(items price + taxes)
     * @return 
     */
    public BigDecimal getTotalPrice() {
        totalPrice = new BigDecimal(0.00);
        totalPrice = subTotal.add(shoppingCartGstTax).add(shoppingCartHstTax).add(shoppingCartPstTax);
        return totalPrice;
    }

    /**
     * This method checks if the customer had bought the book.This method is use 
     * to display a dowload button instead of add to cart on the bookProfile 
     * when the customer had already bought the book he is viewing.
     *
     * @return
     * @throws java.sql.SQLException
     */
    public boolean isBookBought() throws SQLException {
        if (login.getUser() != null) {
            int user_id = login.getUser().getUser_id();
            // get all the the orders of that customer
            List<Order> orders = orderDAO.findByUserID(user_id);
            for (Order order : orders) {
                //get the items of an order
                List<Item> items = itemDAO.findByOrderID(order.getOrderID());
                for (Item item : items) {
                    if (inventoryAction.getBookId() == item.getBookID()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
