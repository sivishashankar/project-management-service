package com.fse.jcs.projectmgr.user.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse.jcs.projectmgr.user.dao.UserDao;
import com.fse.jcs.projectmgr.user.model.User;
import com.fse.jcs.projectmgr.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> fetchAllUsers() {
		
		List<User> usersList = this.userDao.fetchAllUsers();
		
		return usersList;
	}

	@Override
	public User fetchUserById(Integer userId) {
		
		User user = this.userDao.fetchUserById(userId);
		
		return user;
		
	}

	@Override
	public User fetchUserByEmployeeId(String employeeId) {
		
		User user = this.userDao.fetchUserByEmployeeId(employeeId);
		
		return user;
	}

	@Transactional
	@Override
	public void saveUser(User user) {
		
		this.userDao.saveUser(user);		
	}
	
	
	@Transactional
	@Override
	public void deleteUser(Integer userId) {
		
		User userToDelete = this.userDao.fetchUserById(userId);
		
		this.userDao.deleteUser(userToDelete);		
	}
	

}
