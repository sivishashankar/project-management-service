package com.fse.jcs.projectmgr.project.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fse.jcs.projectmgr.project.dao.ProjectDao;
import com.fse.jcs.projectmgr.project.model.Project;
import com.fse.jcs.projectmgr.project.repository.ProjectRepository;

@Repository
public class ProjectDaoImpl implements ProjectDao {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Override
	public List<Project> fetchAllProjects() {
		
		return this.projectRepository.findAll(); 
	}

	@Override
	public Project fetchProjectById(Integer projectId) {
	
		Project project = null;		
		
		try {
			
			project =this.projectRepository.findById(projectId).get();
		
		} catch(NoSuchElementException nseExce) {
			
		}
		
		return project;
	}

	@Override
	public Project fetchProjectByProjectName(String projectName) {
		
		return this.projectRepository.findProjectByName(projectName);
	}

	@Override
	public void saveProject(Project project) {	
		
		this.projectRepository.save(project);
	}

	@Override
	public void suspendProject(Integer projectId) {
		
		this.projectRepository.suspendProject(projectId);
		
	}

}
