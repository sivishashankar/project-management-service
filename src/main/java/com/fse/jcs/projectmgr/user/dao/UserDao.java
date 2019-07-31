package com.fse.jcs.projectmgr.user.dao;

import java.util.List;

import com.fse.jcs.projectmgr.user.model.User;

public interface UserDao {

	
	/** 
	 * This method is used to fetch all the users
	 * 
	 * @return list of users
	 */
	List<User> fetchAllUsers();
	
	
	/**
	 * This method returns the user for the input id
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
	 * This method udpates the project id for the user
	 * 
	 * @return
	 */
	void updateUserForProject(Integer projectId);
	
	/**
	 * This method udpates the project id for the user
	 * 
	 * @return
	 */
	void updateUserForTask(Integer taskId);
	
	/**
	 * This method delete the user information
	 * 
	 * @return User
	 */
	void deleteUser(User user);
}
