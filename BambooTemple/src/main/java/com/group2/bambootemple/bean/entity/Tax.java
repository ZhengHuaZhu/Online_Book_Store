/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group2.bambootemple.bean.entity;

import java.util.Objects;

/**
 *
 * @author 1334262
 */
public class Tax {
    
    private int taxID;
    private String province;    
    private double pst;
    private double gst;
    private double hst;
    
    public Tax(){
        this(-1, "", 0.0, 0.0, 0.0);
    }
    
    public Tax(final int taxID, final String province, final double pst, 
            final double gst, final double hst){
        super();
        this.taxID = taxID;
        this.province = province;
        this.pst = pst;
        this.gst = gst;
        this.hst = hst;
    }

    public final int getTaxID() {
        return taxID;
    }

    public void setTaxID(final int taxID) {
        this.taxID = taxID;
    }

    public final String getProvince() {
        return province;
    }

    public void setProvince(final String province) {
        this.province = province;
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
        int hash = 7;
        hash = 97 * hash + this.taxID;
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
        final Tax other = (Tax) obj;
        if (this.taxID != other.taxID) {
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
        if (!Objects.equals(this.province, other.province)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tax{" + "taxID=" + taxID + ", province=" + province + ", pst=" 
                + pst + ", gst=" + gst + ", hst=" + hst + '}';
    }
    
    
}
