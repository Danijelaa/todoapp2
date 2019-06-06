package com.todoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.dao.UserLoggedInDao;
import com.todoapp.dao.UserLoginDao;
import com.todoapp.security.TokenUtils;

@RestController
@RequestMapping(value="/api/users")
public class UserController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetalsService;
	@Autowired
	TokenUtils tokenUtils;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	ResponseEntity<?> login(@RequestBody UserLoginDao userLoginDao) {
		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userLoginDao.getUsername(), userLoginDao.getPassword());
			Authentication authentication=authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetails userDetails=null;
			try {
				userDetails=userDetalsService.loadUserByUsername(userLoginDao.getUsername());
			} catch (UsernameNotFoundException e) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			UserLoggedInDao userLoggedInDao=new UserLoggedInDao();
			userLoggedInDao.setToken(tokenUtils.generateToken(userDetails));
			userLoggedInDao.setUsername(userLoginDao.getUsername());
			return new ResponseEntity<UserLoggedInDao>(userLoggedInDao, HttpStatus.OK);
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("NO_USER_WITH_GIVEN_USERNAME_AND_PASSWORD.", HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
