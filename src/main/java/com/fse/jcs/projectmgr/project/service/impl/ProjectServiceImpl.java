package com.fse.jcs.projectmgr.project.service.impl;

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
import com.fse.jcs.projectmgr.project.service.ProjectService;
import com.fse.jcs.projectmgr.user.dao.UserDao;
import com.fse.jcs.projectmgr.user.model.User;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private UserDao userDao;
	
	final String DATE_PATTERN = "yyyy/MM/dd";
	
	@Override
	public List<Project> fetchAllProjects() {
		
		List<Project> projects = this.projectDao.fetchAllProjects();
		
		projects.forEach( project -> {			
			convertStartAndEndDatesToString(project);
			setManagerName(project);
			setNumberOfTasksForAProject(project);
		});
		
		return projects;
	}

	@Override
	public Project fetchProjectById(Integer projectId) {

		Project project = this.projectDao.fetchProjectById(projectId);
		
		if(project != null) {
			
			convertStartAndEndDatesToString(project);
			setManagerName(project);
			setNumberOfTasksForAProject(project);
		}
		
		return project;
	}

	@Override
	public Project fetchProjectByProjectName(String projectName) {

		Project project = this.projectDao.fetchProjectByProjectName(projectName);
		
		convertStartAndEndDatesToString(project);
		setManagerName(project);
		setNumberOfTasksForAProject(project);
		
		return project;
		
	}

	@Override
	@Transactional
	public void suspendProject(Integer projectId) {
		
		this.projectDao.suspendProject(projectId);
		
		//Removing manager association from user table when project is suspended
		this.udpateUserForProject(projectId);
		
	}
	
	@Override
	public void updateProject(Project project) throws ParseException {
		
		this.saveProject(project,true);
	}	

	@Transactional
	@Override
	public void saveProject(Project project, boolean isUpdate) throws ParseException {
		
		SimpleDateFormat dateFrmt = new SimpleDateFormat(DATE_PATTERN);
		dateFrmt.setLenient(false);
		
		project.setStartDate(dateFrmt.parse(project.getStartDateStr()));
		project.setEndDate(dateFrmt.parse(project.getEndDateStr()));	
		
		Date today = Calendar.getInstance().getTime();
		
		User user = null;		
		
		if(project.getManagerStr() != null) {
			
			String employeeId = project.getManagerStr().substring(0, 
					project.getManagerStr().indexOf("-")).trim();			
			
			user = this.userDao.fetchUserByEmployeeId(employeeId);
		}
		
		project.setUser(null);		
		this.projectDao.saveProject(project);
		
		if(isUpdate) {
			this.udpateUserForProject(project.getProjectId());
		}
		
		if(null != user && project.getEndDate().after(today)) {
			user.setProject(project);
			this.userDao.saveUser(user);
		}	
		
	}
	
	protected void udpateUserForProject(Integer projectId) {
		
		this.userDao.updateUserForProject(projectId);
	}
	
	protected void setManagerName(Project project) {
		
		if(null != project.getUser()) {
			project.setManagerStr(project.getUser().getEmployeeId()+" - "
					+project.getUser().getFirstName()+" "
					+project.getUser().getLastName());
		}
	}
	
	protected void convertStartAndEndDatesToString(Project project) {
		
		SimpleDateFormat dateFrmt = new SimpleDateFormat(DATE_PATTERN);
		
		Date today = Calendar.getInstance().getTime();
		
		String startDate = dateFrmt.format(project.getStartDate());
		String endDate = dateFrmt.format(project.getEndDate());
		
		project.setStartDateStr(startDate);
		project.setEndDateStr(endDate);
		
		if(project.getEndDate().after(today)) {
			project.setCompleted("N");
		} else {
			project.setCompleted("Y");
		}		
	}

	
	protected void setNumberOfTasksForAProject(Project project) {
	
		if(null != project.getTasks() && !project.getTasks().isEmpty())
			project.setNumberOfTasks(project.getTasks().size());
	}
	
	

}
