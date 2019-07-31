package com.fse.jcs.projectmgr.task.service;

import java.text.ParseException;
import java.util.List;

import com.fse.jcs.projectmgr.task.model.Task;

public interface TaskService {
	
	/** 
	 * This method is used to fetch all the tasks
	 * 
	 * @return list of tasks
	 */
	List<Task> fetchAllTasks();
	
	
	/**
	 * This method returns the task for the input task id
	 * 
	 * @return Task
	 */
	Task fetchTaskById(Integer taskId);
	
	/**
	 * This method update the task information
	 * 
	 * @return
	 */
	void updateTask(Task task) throws ParseException;
	
	
	/**
	 * This method adds/update the task information
	 * 
	 * @return
	 */
	void saveTask(Task task, boolean isUpdate) throws ParseException;
	
	
	/**
	 * This method ends the task for the input task id
	 * 
	 * @return
	 */
	void endTask(Integer taskId);

}
