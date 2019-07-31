package com.fse.jcs.projectmgr.user.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fse.jcs.projectmgr.user.dao.UserDao;
import com.fse.jcs.projectmgr.user.model.User;
import com.fse.jcs.projectmgr.user.repository.UserRepository;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private UserRepository userRepository;
	
	
	/** 
	 * This method is used to fetch all the users
	 * 
	 * @return list of users
	 */
	@Override
	public List<User> fetchAllUsers() {
		
		return this.userRepository.findAll();
	}

	/**
	 * This method returns the user for the input id
	 * 
	 * @return User
	 */
	@Override
	public User fetchUserById(Integer userId) {
	
		User user = null;		
		
		try {
			
			user =this.userRepository.findById(userId).get();
		
		} catch(NoSuchElementException nseExce) {
			
		}
		
		return user;
	}

	/**
	 * This method returns the user for the given employee id
	 * 
	 * @return User
	 */
	@Override
	public User fetchUserByEmployeeId(String employeeId) {
		
		return this.userRepository.findUserByEmployeeId(employeeId);
	}


	/**
	 * This method adds/update the user information
	 * 
	 * @return User
	 */
	@Override
	public void saveUser(User user) {
		
		this.userRepository.save(user);	
	}
	
		
	/**
	 * This method delete the user information
	 * 
	 * @return User
	 */
	@Override
	public void deleteUser(User user) {
		
		this.userRepository.delete(user);	
	}

	
	@Override
	public void updateUserForProject(Integer projectId) {

		this.userRepository.updateUserForProject(projectId);
		
	}

	@Override
	public void updateUserForTask(Integer taskId) {
		this.userRepository.updateUserForTask(taskId);
	}
	
	

}
