/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group2.bambootemple.bean.entity;

import java.io.Serializable;
import java.util.Objects;


/**
 *
 * @author Mehdi
 */
public class User implements Serializable{
    
    private int user_id;
    private String email;    
    private String password;
    private String fname;
    private String lname;
    private String company;
    private String address1;
    private String address2;
    private String city;
    private String province;
    private String country;
    private String postalcode;
    private String homephone;
    private String cellphone;
    private String genre;
    private int isAdmin;

    public User(int user_id, String email, String password, String fname,
            String lname, String company, String address1, String address2, 
            String city, String province, String country, String postalcode, 
            String homephone, String cellphone, String genre) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.company = company;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postalcode = postalcode;
        this.homephone = homephone;
        this.cellphone = cellphone;
        this.genre = genre;
    }
    
    public User() {
        this.user_id = 0;
        this.email = "";
        this.password = "";
        this.fname = "";
        this.lname = "";
        this.company = "";
        this.address1 = "";
        this.address2 = "";
        this.city = "";
        this.province = "";
        this.country = "";
        this.postalcode = "";
        this.homephone = "";
        this.cellphone = "";
        this.genre = "";
        this.isAdmin =0;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getHomephone() {
        return homephone;
    }

    public void setHomephone(String homephone) {
        this.homephone = homephone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getIsAdmin() {
        return isAdmin;
    }
    
    public boolean isAdmin() {
        if (isAdmin == 1)
            return true;
        return false;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }  
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.user_id;
        hash = 37 * hash + Objects.hashCode(this.email);
        hash = 37 * hash + Objects.hashCode(this.password);
        hash = 37 * hash + Objects.hashCode(this.fname);
        hash = 37 * hash + Objects.hashCode(this.lname);
        hash = 37 * hash + Objects.hashCode(this.company);
        hash = 37 * hash + Objects.hashCode(this.address1);
        hash = 37 * hash + Objects.hashCode(this.address2);
        hash = 37 * hash + Objects.hashCode(this.city);
        hash = 37 * hash + Objects.hashCode(this.province);
        hash = 37 * hash + Objects.hashCode(this.country);
        hash = 37 * hash + Objects.hashCode(this.postalcode);
        hash = 37 * hash + Objects.hashCode(this.homephone);
        hash = 37 * hash + Objects.hashCode(this.cellphone);
        hash = 37 * hash + Objects.hashCode(this.genre);
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
        final User other = (User) obj;
        if (this.user_id != other.user_id) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.fname, other.fname)) {
            return false;
        }
        if (!Objects.equals(this.lname, other.lname)) {
            return false;
        }
        if (!Objects.equals(this.company, other.company)) {
            return false;
        }
        if (!Objects.equals(this.address1, other.address1)) {
            return false;
        }
        if (!Objects.equals(this.address2, other.address2)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.province, other.province)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        if (!Objects.equals(this.postalcode, other.postalcode)) {
            return false;
        }
        if (!Objects.equals(this.homephone, other.homephone)) {
            return false;
        }
        if (!Objects.equals(this.cellphone, other.cellphone)) {
            return false;
        }
        if (!Objects.equals(this.genre, other.genre)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "email=" + email + ", fname=" + fname + ", lname=" + lname + '}';
    }
    
    
}
