package com.todoapp.service.impl;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.todoapp.model.User;
import com.todoapp.repository.UserRepository;
import com.todoapp.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public String findUsernameFromPrincipal(HttpServletRequest request) {
		Principal principal=request.getUserPrincipal();
		return principal.getName();
	}

	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
		
	}

}
