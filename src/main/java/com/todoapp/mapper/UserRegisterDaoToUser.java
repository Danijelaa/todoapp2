package com.todoapp.mapper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.todoapp.dao.UserRegisterDao;
import com.todoapp.model.User;

@Component
public class UserRegisterDaoToUser implements Converter<UserRegisterDao, User>{

	@Override
	public User convert(UserRegisterDao userRegisterDao) {
		User user=new User();
		user.setPassword(userRegisterDao.getPassword());
		user.setUsername(userRegisterDao.getPassword());
		return user;
	}

	
}
