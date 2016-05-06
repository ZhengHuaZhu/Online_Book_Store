package com.group2.bambootemple.bean;

import com.group2.bambootemple.bean.entity.User;
import com.group2.bambootemple.persistence.OrderDAOImpl;
import java.io.Serializable;
import java.sql.SQLException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Mehdi Moodi
 */
@Named
@SessionScoped
public class SelectedUser implements Serializable {

    private User user;
    @Inject
    private SelectedOrder selectedOrder;
    @Inject
    private OrderDAOImpl orderDAOImpl;
    
    public SelectedUser() {
        user = new User();        
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) throws SQLException {
        this.user = user;
        if (!orderDAOImpl.findByUserID(user.getUser_id()).isEmpty()) {
            selectedOrder.setOrder(orderDAOImpl.findByUserID(user.getUser_id()).get(0));
        }
    }

    public void setUserToNew() {
        user = new User();
    }    
}
