package com.fse.jcs.projectmgr.project.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fse.jcs.projectmgr.task.model.Task;
import com.fse.jcs.projectmgr.user.model.User;

@Entity
@Table(name="project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer projectId;
	
	@Column(name = "project")
	private String project;
	
	@JsonIgnore
	@Column(name = "start_date")
	private Date startDate;
	
	@JsonIgnore
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "priority")
	private Integer priority;
	
	@Column(name = "suspend")
	private String suspendFlag;
	
	@Transient
	private String startDateStr;
	
	@Transient
	private String endDateStr;
	
	@Transient
	private Integer numberOfTasks;
	
	@Transient
	private String completed;
	
	@Transient
	private String managerStr;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="project")
	@JsonIgnore
	private List<Task> tasks;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="project")
	@JsonIgnore
	private User user;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public String getSuspendFlag() {
		return suspendFlag;
	}

	public void setSuspendFlag(String suspendFlag) {
		this.suspendFlag = suspendFlag;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getManagerStr() {
		return managerStr;
	}

	public void setManagerStr(String managerStr) {
		this.managerStr = managerStr;
	}

	public Integer getNumberOfTasks() {
		return numberOfTasks;
	}

	public void setNumberOfTasks(Integer numberOfTasks) {
		this.numberOfTasks = numberOfTasks;
	}

	public String getCompleted() {
		return completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}
	
	
	
}
