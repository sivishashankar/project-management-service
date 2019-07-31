package com.fse.jcs.projectmgr.task.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse.jcs.projectmgr.project.dao.ProjectDao;
import com.fse.jcs.projectmgr.project.model.Project;
import com.fse.jcs.projectmgr.task.dao.TaskDao;
import com.fse.jcs.projectmgr.task.model.ParentTask;
import com.fse.jcs.projectmgr.task.model.Task;
import com.fse.jcs.projectmgr.task.service.TaskService;
import com.fse.jcs.projectmgr.user.dao.UserDao;
import com.fse.jcs.projectmgr.user.model.User;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TaskDao taskDao;
	
	final String DATE_PATTERN = "yyyy/MM/dd";
	
	@Override
	public List<Task> fetchAllTasks() {
		
		List<Task> tasksList = this.taskDao.fetchAllTasks();
		
		tasksList.forEach( task -> {			
			convertStartAndEndDatesToString(task);
			setUserAndProjectDetails(task);
		});
		
		return tasksList;
	}

	@Override
	public Task fetchTaskById(Integer taskId) {
		
		Task task = null;
		
		task = this.taskDao.fetchTaskById(taskId);
		
		if(task != null) {
			convertStartAndEndDatesToString(task);
			setUserAndProjectDetails(task);
		}
		
		return task;
	}

	@Override
	public void updateTask(Task task) throws ParseException {
		
		if(task != null) {
			saveTask(task,true);
		}
		
	}
	
	@Transactional
	@Override
	public void endTask(Integer taskId) {		
		this.taskDao.endTask(taskId);
		
		udpateUserForTask(taskId);
	}

	@Transactional
	@Override
	public void saveTask(Task task, boolean isUpdate) throws ParseException {
	
		if(task != null) {		
			
			if(null != task.getParentTaskName()) {
				saveParentTask(task);
			}
			
			User user = setUserAndProjecDetails(task);

			SimpleDateFormat dateFrmt = new SimpleDateFormat(DATE_PATTERN);
			
			if(null != task.getStartDateStr() && !"".equalsIgnoreCase(task.getStartDateStr()))
				task.setStartDate(dateFrmt.parse(task.getStartDateStr()));
			
			if(null != task.getEndDateStr() && !"".equalsIgnoreCase(task.getEndDateStr()))
				task.setEndDate(dateFrmt.parse(task.getEndDateStr()));
			
			task.setUser(null);
			this.taskDao.saveTask(task);
			
			if(isUpdate) {
				this.udpateUserForTask(task.getTaskId());
			}
			
			
			if(user != null) {
				user.setTask(task);
				this.userDao.saveUser(user);
			}
		}
	}
	
	protected void setUserAndProjectDetails(Task task) {
		
		if(null != task.getUser()) {
			task.setUserString(task.getUser().getEmployeeId()+" - "
					+task.getUser().getFirstName()+" "
					+task.getUser().getLastName());
		}
		
		if(null != task.getProject()) {
			task.setProjectName(task.getProject().getProject());
		} else {
			task.setProjectName("");
		}
		
		if(null != task.getParentTask()) {
			task.setParentTaskName(task.getParentTask().getParentTaskName());
		} else {
			task.setParentTaskName("No Parent Task");
		}
	}
	
	protected void saveParentTask(Task task) {
		
		ParentTask parentTask = new ParentTask();
		
		Integer parentId = this.taskDao.fetchParentTask(task.getParentTaskName());
		
		if(null != parentId) {
			parentTask.setParentId(parentId);
		} else {
			parentTask.setParentTaskName(task.getParentTaskName());
			this.taskDao.saveParentTask(parentTask);
		}
		
		task.setParentTask(parentTask);
		
	}

	protected void convertStartAndEndDatesToString(Task task) {
		
		SimpleDateFormat dateFrmt = new SimpleDateFormat(DATE_PATTERN);
		
		Date today = Calendar.getInstance().getTime();
		
		String startDate = "";
		String endDate = ""; 
		
		if(null != task.getStartDate()) {
			startDate = dateFrmt.format(task.getStartDate());	
		}
		
		if(null != task.getEndDate())
			endDate = dateFrmt.format(task.getEndDate());	
		
		task.setStartDateStr(startDate);
		task.setEndDateStr(endDate);
		
		if(null == task.getEndDate() || task.getEndDate().after(today)) {
			task.setCompleted("N");
		} else {
			task.setCompleted("Y");
		}	
	}
	
	protected User setUserAndProjecDetails(Task task) {
		
		User assignedUser = null;
		Project assignedProject = null;
		
		if(null != task.getProjectName()) {
			assignedProject = this.projectDao.fetchProjectByProjectName(task.getProjectName().trim());
			task.setProject(assignedProject);
		}
		
		if(null != task.getUserString()) {
			
			String employeeId =  task.getUserString().substring(0, 
					task.getUserString().indexOf("-")).trim();			
			
			assignedUser = this.userDao.fetchUserByEmployeeId(employeeId);
			
			task.setUser(assignedUser);
		}
		
		return assignedUser;
		
	}
	
	protected void udpateUserForTask(Integer taskId) {
		
		this.userDao.updateUserForTask(taskId);
	}
	
	
}
