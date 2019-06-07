package com.todoapp.service;

import javax.servlet.http.HttpServletRequest;

import com.todoapp.model.User;

public interface UserService {

	User findByUsername(String username);
	User save(User user);
	Boolean existsByUsername(String username);
	String findUsernameFromPrincipal(HttpServletRequest request);
}
