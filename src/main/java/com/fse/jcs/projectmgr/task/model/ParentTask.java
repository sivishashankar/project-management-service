package com.fse.jcs.projectmgr.task.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="parent_task")
public class ParentTask {
	
	@Id
	@Column(name="parent_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer parentId;
	
	@Column(name="parent_task")
	private String parentTaskName;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="parentTask")
	private List<Task> tasks;

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public String getParentTaskName() {
		return parentTaskName;
	}

	public void setParentTaskName(String parentTaskName) {
		this.parentTaskName = parentTaskName;
	}
	
	

}
