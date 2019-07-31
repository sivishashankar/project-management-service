package com.fse.jcs.projectmgr.task.controller;

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
import com.fse.jcs.projectmgr.task.model.Task;
import com.fse.jcs.projectmgr.task.service.TaskService;

@RestController
@CrossOrigin
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	/** 
	 * This method is used to fetch all the projects
	 * 
	 * @return list of projects
	 */
	@RequestMapping( value = "/all", method = RequestMethod.GET)
	public List<Task> getAllTasks() {		
		return this.taskService.fetchAllTasks();
	}
	
	/**
	 * This method returns the task for the input task id
	 * 
	 * @return Project
	 */
	@RequestMapping(value = "/fetch/{taskId}", method = RequestMethod.GET)
	public Task fetchUserById(@PathVariable Integer taskId) {
		
		return this.taskService.fetchTaskById(taskId);
	}
	
	
	@RequestMapping(method= RequestMethod.POST)
	public void saveTask(@RequestBody Task task) throws ParseException {
		
		this.taskService.saveTask(task, false);		
	}
	
	@RequestMapping(method= RequestMethod.PUT)
	public void updateTask(@RequestBody Task task) throws ParseException {		
		
		this.taskService.updateTask(task);	
	}
	
	@RequestMapping(value= "/endtask/{taskId}", method= RequestMethod.PUT)
	public void endtask(@PathVariable Integer taskId) {
		
		this.taskService.endTask(taskId);
	}
	

}
