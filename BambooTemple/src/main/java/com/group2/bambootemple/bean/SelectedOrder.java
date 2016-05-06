package com.group2.bambootemple.bean;

import com.group2.bambootemple.bean.entity.Order;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Mehdi Moodi
 */
@Named
@SessionScoped
public class SelectedOrder implements Serializable{
    
    private Order order;

    public SelectedOrder() {
        order = new Order();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {        
        this.order = order;
    }    
}
