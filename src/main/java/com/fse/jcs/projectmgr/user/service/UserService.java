package com.fse.jcs.projectmgr.user.service;

import java.util.List;

import com.fse.jcs.projectmgr.user.model.User;

public interface UserService {

	/** 
	 * This method is used to fetch all the users
	 * 
	 * @return list of users
	 */
	List<User> fetchAllUsers();
	
	
	/**
	 * This method returns the user for the input user id
	 * 
	 * @return User
	 */
	User fetchUserById(Integer userId);
	
	/**
	 * This method returns the user for the given employee id
	 * 
	 * @return User
	 */
	User fetchUserByEmployeeId(String employeeId);
	
	
	/**
	 * This method adds/update the user information
	 * 
	 * @return User
	 */
	void saveUser(User user);
	
	/**
	 * This method adds/update the user information
	 * 
	 * @return User
	 */
	void deleteUser(Integer userId);
	
}
