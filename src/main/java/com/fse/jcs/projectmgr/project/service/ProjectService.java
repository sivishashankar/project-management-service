package com.fse.jcs.projectmgr.project.service;

import java.text.ParseException;
import java.util.List;

import com.fse.jcs.projectmgr.project.model.Project;

public interface ProjectService {

	
	/** 
	 * This method is used to fetch all the projects
	 * 
	 * @return list of projects
	 */
	List<Project> fetchAllProjects();
	
	
	/**
	 * This method returns the project for the input project id
	 * 
	 * @return Project
	 */
	Project fetchProjectById(Integer projectId);
	
	/**
	 * This method returns the project for the given project name
	 * 
	 * @return Project
	 */
	Project fetchProjectByProjectName(String projectName);
	
	
	/**
	 * This method suspend the project for the given project id
	 * 
	 * @return
	 */
	void suspendProject(Integer projectId);
	
	
	/**
	 * This method update the project information
	 * 
	 * @return
	 */
	void updateProject(Project project) throws ParseException;
	
	
	/**
	 * This method adds/update the project information
	 * 
	 * @return
	 */
	void saveProject(Project project, boolean isUpdate) throws ParseException;
}

