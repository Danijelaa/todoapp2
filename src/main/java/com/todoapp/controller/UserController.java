package com.todoapp.controller;

import javax.servlet.http.HttpServletRequest;

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

import com.todoapp.dto.UserLoggedInDto;
import com.todoapp.dto.UserLoginDto;
import com.todoapp.dto.UserRegisterDto;
import com.todoapp.mapper.UserRegisterDaoToUser;
import com.todoapp.model.Task;
import com.todoapp.model.User;
import com.todoapp.security.TokenUtils;
import com.todoapp.service.TaskService;
import com.todoapp.service.UserService;

@RestController
@RequestMapping(value="/api/users")
public class UserController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetalsService;
	@Autowired
	TokenUtils tokenUtils;
	@Autowired
	UserService userService;
	@Autowired
	UserRegisterDaoToUser toUser;
	@Autowired
	TaskService taskService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDao) {
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
			UserLoggedInDto userLoggedInDao=new UserLoggedInDto();
			userLoggedInDao.setToken(tokenUtils.generateToken(userDetails));
			userLoggedInDao.setUsername(userLoginDao.getUsername());
			return new ResponseEntity<UserLoggedInDto>(userLoggedInDao, HttpStatus.OK);
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("NO_USER_WITH_GIVEN_USERNAME_AND_PASSWORD.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	ResponseEntity<?> register(@RequestBody UserRegisterDto userRegisterDao) {
		if(!userRegisterDao.getPassword().equals(userRegisterDao.getPasswordConfirm())) {
			return new ResponseEntity<String>("PASSWORDS_DO_NOT_MATCH.", HttpStatus.BAD_REQUEST);
		}
		Boolean exists=userService.existsByUsername(userRegisterDao.getUsername());
		if(exists) { 
			return new ResponseEntity<String>("USER_WITH_GIVEN_USERNAME_ALREADY_EXISTS.", HttpStatus.CONFLICT); 
		}
			 
		User user=toUser.convert(userRegisterDao);
		user=userService.save(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
