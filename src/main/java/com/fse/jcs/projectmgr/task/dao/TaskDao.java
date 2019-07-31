package com.fse.jcs.projectmgr.task.dao;

import java.text.ParseException;
import java.util.List;

import com.fse.jcs.projectmgr.task.model.ParentTask;
import com.fse.jcs.projectmgr.task.model.Task;

public interface TaskDao {
	
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
	 * This method adds/update the task information
	 * 
	 * @return
	 */
	void saveTask(Task task);
	
	
	/**
	 * This method returns the parent task information
	 * 
	 * @return Integer
	 */
	Integer fetchParentTask(String parenTaskName);
	
	
	/**
	 * This method saves the parent task information
	 * 
	 * @return 
	 */
	void saveParentTask(ParentTask parentTask);
	
	
	/**
	 * This method ends the task for the given task id
	 * 
	 * @return 
	 */
	void endTask(Integer taskId);

}
