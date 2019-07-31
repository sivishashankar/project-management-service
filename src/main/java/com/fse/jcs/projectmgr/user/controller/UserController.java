package com.fse.jcs.projectmgr.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fse.jcs.projectmgr.user.model.User;
import com.fse.jcs.projectmgr.user.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	/** 
	 * This method is used to fetch all the users
	 * 
	 * @return list of users
	 */
	@RequestMapping( value = "/all", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		
		return this.userService.fetchAllUsers();
	}
	
	
	/**
	 * This method returns the user for the input user id
	 * 
	 * @return User
	 */
	@RequestMapping(value = "/fetch/{userId}", method = RequestMethod.GET)
	public User fetchUserById(@PathVariable Integer userId) {
		
		return this.userService.fetchUserById(userId);
	}
	
	
	/**
	 * This method returns the user for the input employee id
	 * 
	 * @return User
	 */
	@RequestMapping(value = "/employee/{employeeId}", method = RequestMethod.GET)
	public User fetchUserById(@PathVariable String employeeId) {
		
		return this.userService.fetchUserByEmployeeId(employeeId);
	}
	
	/**
	 * This method save the new user recieved
	 * 
	 * @return User
	 */
	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public void saveUser(@RequestBody User user) {
		
		this.userService.saveUser(user);
	}
	
	/**
	 * This method updates the user
	 * 
	 * @return User
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public void updateUser(@RequestBody User user) {
		
		this.userService.saveUser(user);
	}
	
	/**
	 * This method save the new user recieved
	 * 
	 * @return User
	 */
	@RequestMapping(value="/{userId}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable Integer userId) {
		
		this.userService.deleteUser(userId);
	}
	
	
}
