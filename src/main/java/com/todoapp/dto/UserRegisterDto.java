package com.todoapp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterDto extends UserLoginDto{

	@NotNull
	@Size(min = 3, max = 20)
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
