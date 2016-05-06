package com.group2.bambootemple.bean;


import com.group2.bambootemple.bean.Login;
import com.group2.bambootemple.bean.entity.Inventory;
import com.group2.bambootemple.bean.entity.Item;
import com.group2.bambootemple.bean.entity.Order;
import com.group2.bambootemple.bean.entity.User;
import com.group2.bambootemple.persistence.InventoryDAOImpl;
import com.group2.bambootemple.persistence.ItemDAOImpl;
import com.group2.bambootemple.persistence.OrderDAOImpl;
import com.sun.tools.javac.jvm.Items;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Marjo
 */
@Named
@RequestScoped
public class LibraryBooks {

    @Inject
    private Login login;
    @Inject
    private ItemDAOImpl itemDAO;
    @Inject
    private OrderDAOImpl orderDAO;
    @Inject
    private InventoryDAOImpl inventoryDAO;

    /**
     * Get all the books bought by the logged in customer.
     *
     * @return
     */
    public List<Inventory> getAllCustomerBook() throws SQLException {
        User customer = login.getUser();
        List<Item> items = new ArrayList<>();
        List<Inventory> books = new ArrayList<>();

        if (customer != null) {
            // get all the order made by the logged in customer(user).
            List<Order> orders = orderDAO.findByUserID(customer.getUser_id());
            //find all the items(books) of these order
            for (Order order : orders) {
                items = itemDAO.findByOrderID(order.getOrderID());
                for(Item item : items){
                    books.add(inventoryDAO.findByBookId(item.getBookID()));
                }
            }
            return books;
        }

        return null;
    }

}