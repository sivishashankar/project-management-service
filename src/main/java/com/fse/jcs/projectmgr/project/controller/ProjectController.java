package com.fse.jcs.projectmgr.project.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fse.jcs.projectmgr.project.model.Project;
import com.fse.jcs.projectmgr.project.service.ProjectService;

@RestController
@CrossOrigin
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	/** 
	 * This method is used to fetch all the projects
	 * 
	 * @return list of projects
	 */
	@RequestMapping( value = "/all", method = RequestMethod.GET)
	public List<Project> getAllProjects() {
		
		return this.projectService.fetchAllProjects();
	}
	
	
	/**
	 * This method returns the project for the input project id
	 * 
	 * @return Project
	 */
	@RequestMapping(value = "/fetch/{projectId}", method = RequestMethod.GET)
	public Project fetchProjectById(@PathVariable Integer projectId) {
		
		return this.projectService.fetchProjectById(projectId);
	}
	
	
	/**
	 * This method returns the project for the input project Name
	 *  
	 * @return Project
	 */
	@RequestMapping(value = "/{projectName}", method = RequestMethod.GET)
	public Project fetchProjectByName(@PathVariable String projectName) {
		
		return this.projectService.fetchProjectByProjectName(projectName);
	}
	
	/**
	 * This method will suspend/end the project
	 * 
	 * @return
	 */
	@RequestMapping(value = "/suspend", method = RequestMethod.PUT)
	public void suspendProject(@RequestBody Project project) {
		
		this.projectService.suspendProject(project.getProjectId());
	}
	
	/**
	 * This method save the new project received
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addproject", method = RequestMethod.POST)
	public void saveProject(@RequestBody Project project) throws ParseException {
		
		this.projectService.saveProject(project,false);
	}
	
	
	@RequestMapping(method = RequestMethod.PUT)
	public void updateProject(@RequestBody Project project) throws ParseException {
		
		this.projectService.updateProject(project);
	}
	
}
