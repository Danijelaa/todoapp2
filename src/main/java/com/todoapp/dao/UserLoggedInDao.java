package com.todoapp.dao;

public class UserLoggedInDao {

	private String username;
	private String token;
	
	public UserLoggedInDao() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	
}
