package com.group2.bambootemple.bean;

import java.math.BigDecimal;

/**
 *
 * @author zhu zhenghua
 */
public class ClientSalesReport {
    private String email;
    private String name;
    private BigDecimal sales;
    private BigDecimal cost;
    private BigDecimal profit;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
