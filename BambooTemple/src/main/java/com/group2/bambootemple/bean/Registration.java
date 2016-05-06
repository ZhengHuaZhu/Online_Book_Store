package com.group2.bambootemple.bean;

import com.group2.bambootemple.bean.entity.User;
import com.group2.bambootemple.persistence.UserDAOImpl;
import java.sql.SQLException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

/**
 * This is a backing bean serves as a controller that not only interacts with UI components but also give out navigation string when the form submitted.
 * 
 * @author  Zheng Hua Zhu
 */
@Named
@RequestScoped
public class Registration {
    private User user;
    private boolean existingUser;

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

    @Inject
    private UserDAOImpl instance;

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
   
    public boolean isExistingUser() {
        return existingUser;
    }

    public String register() throws SQLException {
        String nextPage;
        user = new User(-1, email, password, fname, lname, company, address1, address2,
                city, province, country, postalcode, homephone, cellphone, genre);     
        
        if (!instance.checkExistingUserByEmail(user.getEmail())) {
            instance.create(user);
            nextPage = "index";
        } else {
            existingUser=true;
            nextPage=null;
        }
        return nextPage;
    }

}
