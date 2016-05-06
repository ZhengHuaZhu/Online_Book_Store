package com.group2.bambootemple.bean;

import com.group2.bambootemple.bean.entity.Inventory;
import com.group2.bambootemple.bean.entity.Item;
import com.group2.bambootemple.bean.entity.Order;
import com.group2.bambootemple.persistence.ItemDAOImpl;
import com.group2.bambootemple.persistence.OrderDAOImpl;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * 
 * The purpose of this backing bean is to create an order.
 * 
 * @author Marjorie Morales
 */
@Named
@RequestScoped
public class ProceedOrders implements Serializable {

    @Inject
    private Login login;
    @Inject
    private ItemDAOImpl itemDAO;
    @Inject
    private Invoice invoice;
    @Inject
    private OrderDAOImpl orderDAO;
    @Inject
    private ShoppingCart shoppingCart;
    private Double hst, gst, pst;
    private MailBean mailBean;
    private String cardNumber = "";
    private Date date = new Date();

//    public void sendInvoice() {
//        mailBean = new MailBean();
//        mailBean.getTo().add(login.getUser().getEmail());
//        mailBean.setSubject("Bamboo Temple Order Confirmation");
//        mailBean.setTextMessageField("hello");
//        String messageId = SendInvoice.sendEmail(mailBean);
//    }
    
    /**
     * Create an order.
     * @return
     * @throws SQLException 
     */
    public String addOrder() throws SQLException {
        //before creating a new order, check if the shopping cart is not empty
        if (!shoppingCart.getCartItems().isEmpty()) {
            //check the creditcard information
            Order newOrder = new Order();
            newOrder.setUserID(login.getUser().getUser_id());
            int result = orderDAO.create(newOrder);
            int id = orderDAO.getLastId();

            // after creating an order, create items
            List<Inventory> cartItems = shoppingCart.getCartItems();
            for (int i = 0; i < cartItems.size(); i++) {
                Item item = new Item();
                Inventory inventory = cartItems.get(i);
                calculateTaxes(inventory);
                item.setOrderID(id);
                item.setBookID(inventory.getBookId());
                item.setPrice(inventory.getLPrice().doubleValue());
                item.setGst(gst);
                item.setPst(pst);
                item.setHst(hst);
                int resultItem = itemDAO.create(item);
            }
            // empty the shopping cart
            shoppingCart.emptyCartItems();
            // set the order_id, in order to diplay the invoice
            invoice.setOrder_id(id);
            // send the invoice to the customer's user.
            //sendInvoice();
            return "invoice";
        }
        return "shoppingCart";
    }
    
    /**
     * Calculate the order's total taxes.
     * @param inventory 
     */
    private void calculateTaxes(Inventory inventory) {
        double price = inventory.getLPrice().doubleValue();
        gst = price * shoppingCart.getGstTax().doubleValue();
        pst = price * shoppingCart.getPstTax().doubleValue();
        hst = price * shoppingCart.getHstTax().doubleValue();
    }

    public void setCardNumber(String newValue) {
        cardNumber = newValue;
    }

    public String getCardNumber() {
        return cardNumber;
    }

}
