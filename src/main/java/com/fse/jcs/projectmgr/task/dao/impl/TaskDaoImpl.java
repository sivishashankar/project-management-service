package com.fse.jcs.projectmgr.task.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fse.jcs.projectmgr.task.dao.TaskDao;
import com.fse.jcs.projectmgr.task.model.ParentTask;
import com.fse.jcs.projectmgr.task.model.Task;
import com.fse.jcs.projectmgr.task.repository.ParentTaskRepository;
import com.fse.jcs.projectmgr.task.repository.TaskRepository;

@Repository
public class TaskDaoImpl implements TaskDao {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private ParentTaskRepository parentTaskRepository;
	
	@Override
	public List<Task> fetchAllTasks() {
		
		return this.taskRepository.findAll();
	}

	@Override
	public Task fetchTaskById(Integer taskId) {

		Task task = null;
		
		try {			
			task =this.taskRepository.findById(taskId).get();		
		} catch(NoSuchElementException nseExce) {
			
		}
		
		return task;		
	}

	@Override
	public void saveTask(Task task) {		
		this.taskRepository.save(task);
	}

	@Override
	public Integer fetchParentTask(String parenTaskName) {		
		return this.taskRepository.findParentTask(parenTaskName);
	}

	@Override
	public void saveParentTask(ParentTask parentTask) {		
		this.parentTaskRepository.save(parentTask);
		
	}

	@Override
	public void endTask(Integer taskId) {
		this.taskRepository.endTask(taskId);		
	}

}
