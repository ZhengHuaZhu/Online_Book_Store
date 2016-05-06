/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group2.bambootemple.bean;

/**
 *
 * @author Mehdi
 */
public class DbConfigBean {
    private String url;
	private String user;
	private String password;
	
	/**
	 * Default Constructor
	 */
	public DbConfigBean() {
		super();
		this.url = "";
		this.user = "";
		this.password = "";
	}

	/**
	 * @param url
	 * @param user
	 * @param password
	 */
	public DbConfigBean(final String url, final String user, final String password) {
		super();
		this.url = url;
		this.user = user;
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
