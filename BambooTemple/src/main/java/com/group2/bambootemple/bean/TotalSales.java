package com.group2.bambootemple.bean;

import java.math.BigDecimal;

/**
 * - This class only serves as a backing bean for interacting with UI components values.
 * - This class is an embedded entity in the Report class.
 * 
 * @author zhu zhenghua
 */
public class TotalSales {
    private BigDecimal sales;
    private BigDecimal cost;
    private BigDecimal profit;
    private int qty;

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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    
    
}
