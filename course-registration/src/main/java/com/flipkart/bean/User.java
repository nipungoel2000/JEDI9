package com.flipkart.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class User {
	private String id;
	private String userName;
//	private String role;
	private String emailId;
	private String password;
	
	
	
	public User(String id, String name, String password, String email) {
		super();
		this.id = id;
		this.userName = name;
		this.emailId = email;
		this.password = password;
	}
	
	
	
	public User() {
		super();
	}


	@JsonProperty
	public String getId() {
		return id;
	}
	
	@JsonProperty
	public void setId(String id) {
		this.id = id;
	
	}


	@JsonProperty
	public String getUserName() {
		return userName;
	}


	@JsonProperty
	public void setUserName(String userName) {
		this.userName = userName;
	}


	@JsonProperty
	public String getEmailId() {
		return emailId;
	}


	@JsonProperty
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


	@JsonProperty
	public String getPassword() {
		return password;
	}


	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}




	
}