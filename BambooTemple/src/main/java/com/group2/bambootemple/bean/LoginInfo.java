package com.group2.bambootemple.bean;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * - This class only serves as a backing bean to interact UI components.
 * 
 * @author 1334262
 */
@Named
@SessionScoped
public class LoginInfo implements Serializable {

    private String password;
    private String email;

    public LoginInfo() {
        password = "";
        email = "";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
