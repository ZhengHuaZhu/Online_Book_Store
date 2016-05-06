/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group2.bambootemple.bean.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Zhu, Zheng Hua
 */
public class Order implements Serializable {
    
    private int orderID;
    private int userID;
    private LocalDateTime orderedON;
    
    public Order(){
        this(-1, -1, LocalDateTime.now());
    }
    
    public Order(final int orderID, final int userID, 
                      final LocalDateTime orderedON){
        super();
        this.orderID = orderID;
        this.userID = userID;
        this.orderedON = orderedON;
    }

    public final int getOrderID() {
        return orderID;
    }

    public void setOrderID(final int orderID) {
        this.orderID = orderID;
    }

    public final int getUserID() {
        return userID;
    }

    public void setUserID(final int userID) {
        this.userID = userID;
    }

    public final LocalDateTime getOrderedON() {
        return orderedON;
    }

    public void setOrderedON(final LocalDateTime orderedON) {
        this.orderedON = orderedON;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.orderID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderID != other.orderID) {
            return false;
        }
        if (this.userID != other.userID) {
            return false;
        }
        if (!Objects.equals(this.orderedON, other.orderedON)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Order{" + "orderID=" + orderID + ", userID=" + userID + 
                ", orderedON=" + orderedON + '}';
    }
      
}
