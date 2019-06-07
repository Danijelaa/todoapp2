package com.todoapp.dto;

public class UserRegisterDto extends UserLoginDto{

	private String passwordConfirm;

	public UserRegisterDto() {
		super();
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	
	
}
