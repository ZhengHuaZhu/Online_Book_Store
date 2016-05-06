                                                                                            /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group2.bambootemple.bean.entity;

/**
 *
 * @author Zhu, Zheng Hua
 */
public class Item {
    
    private int itemID;
    private int orderID;    
    private int bookID;
    private double price;
    private double pst;
    private double gst;
    private double hst;
    
    public Item(){
        this(-1, -1, -1, 0.0, 0.0, 0.0, 0.0);
    }
    
    public Item(final int itemID, final int orderID, final int bookID, 
            final double price, final double pst, final double gst, 
            final double hst){
        super();
        this.itemID = itemID;
        this.orderID = orderID;
        this.bookID = bookID;
        this.price = price;
        this.pst = pst;
        this.gst = gst;
        this.hst = hst;
    }

    public final int getItemID() {
        return itemID;
    }

    public void setItemID(final int itemID) {
        this.itemID = itemID;
    }

    public final int getOrderID() {
        return orderID;
    }

    public void setOrderID(final int orderID) {
        this.orderID = orderID;
    }

    public final int getBookID() {
        return bookID;
    }

    public void setBookID(final int bookID) {
        this.bookID = bookID;
    }

    public final double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public final double getPst() {
        return pst;
    }

    public void setPst(final double pst) {
        this.pst = pst;
    }

    public final double getGst() {
        return gst;
    }

    public void setGst(final double gst) {
        this.gst = gst;
    }

    public final double getHst() {
        return hst;
    }

    public void setHst(final double hst) {
        this.hst = hst;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + this.itemID;
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
        final Item other = (Item) obj;
        if (this.itemID != other.itemID) {
            return false;
        }
        if (this.orderID != other.orderID) {
            return false;
        }
        if (this.bookID != other.bookID) {
            return false;
        }
        if (Double.doubleToLongBits(this.price) != 
                Double.doubleToLongBits(other.price)) {
            return false;
        }
        if (Double.doubleToLongBits(this.pst) != 
                Double.doubleToLongBits(other.pst)) {
            return false;
        }
        if (Double.doubleToLongBits(this.gst) != 
                Double.doubleToLongBits(other.gst)) {
            return false;
        }
        if (Double.doubleToLongBits(this.hst) != 
                Double.doubleToLongBits(other.hst)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Item{" + "itemID=" + itemID + ", orderID=" + orderID + ", "
                + "bookID=" + bookID + ", price=" + price + ", pst=" + pst + ", "
                + "gst=" + gst + ", hst=" + hst + '}';
    }

    
}
