package com.todoapp.dao;

public class UserRegisterDao extends UserLoginDao{

	private String passwordConfirm;

	public UserRegisterDao() {
		super();
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	
	
}
