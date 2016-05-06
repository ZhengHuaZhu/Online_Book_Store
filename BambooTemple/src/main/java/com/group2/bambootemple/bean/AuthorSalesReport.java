package com.group2.bambootemple.bean;

import java.math.BigDecimal;

/**
 * - This class only serves as a backing bean for rendering components values.
 * - This class is an embedded entity in the Report class.
 * 
 * 
 * @author zhu zhenghua
 */
public class AuthorSalesReport {
    private String author;
    private String quantity;
    private BigDecimal sales;
    private BigDecimal cost;
    private BigDecimal profit;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String name) {
        this.author = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSales() {
        return sales;
    }

    public void setSales(BigDecimal sales) {
        this.sales = sales;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }
    
    
}
