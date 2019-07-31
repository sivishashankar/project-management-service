package com.fse.jcs.projectmgr.project.dao;

import java.util.List;

import com.fse.jcs.projectmgr.project.model.Project;

public interface ProjectDao {

	
	/** 
	 * This method is used to fetch all the projects
	 * 
	 * @return list of users
	 */
	List<Project> fetchAllProjects();
	
	
	/**
	 * This method returns the project for the input project id
	 * 
	 * @return User
	 */
	Project fetchProjectById(Integer projectId);
	
	/**
	 * This method returns the project for the given project name
	 * 
	 * @return User
	 */
	Project fetchProjectByProjectName(String projectName);
	
	
	/**
	 * This method suspend the project for the given project id
	 * 
	 * @return User
	 */
	void suspendProject(Integer projectId);
	
	
	/**
	 * This method adds/update the project information
	 * 
	 * @return User
	 */
	void saveProject(Project project);
}
