package com.fse.jcs.projectmgr.project.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.jcs.projectmgr.project.model.Project;
import com.fse.jcs.projectmgr.project.service.ProjectService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectControllerTest {

	@MockBean
	ProjectService mockProjectService;
	
	@Autowired
	private WebApplicationContext webAppCtx;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webAppCtx);
		this.mockMvc = builder.build();
	}
	
	
	@Test
	public void testGetAllProjecs() {

		try {
			
			this.mockMvc.perform(get("/projects/all").contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
			
			verify(this.mockProjectService).fetchAllProjects();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFetchProjectById() {

		try {
			
			this.mockMvc.perform(get("/projects/fetch/" + 1).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
			
			verify(this.mockProjectService).fetchProjectById(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testFetchProjectByName() {

		String projectName = "Project1";
		
		try {
			
			this.mockMvc.perform(get("/projects/" +projectName).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
			
			verify(this.mockProjectService).fetchProjectByProjectName(projectName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testSaveProject() {
		
		Project projectToAdd = getTestProject();
		
		try {
			
			this.mockMvc.perform(post("/projects/addproject")
				.contentType(MediaType.APPLICATION_JSON).content(toJson(projectToAdd)))
				.andExpect(status().isOk());
			
			verify(this.mockProjectService).saveProject(any(Project.class), any(Boolean.class));
			
		} catch(Exception exce) {
			exce.printStackTrace();
		}		
	}
	
	@Test
	public void testSuspendProject() {
		
		Project projectToSuspend = getTestProject();
		Integer projectId = projectToSuspend.getProjectId();
		
		try {
			
			this.mockMvc.perform(put("/projects/suspend")
				.contentType(MediaType.APPLICATION_JSON).content(toJson(projectToSuspend)))
				.andExpect(status().isOk());
			
			verify(this.mockProjectService).suspendProject(projectId);
			
		} catch(Exception exce) {
			exce.printStackTrace();
		}	
		
	}
	
	@Test
	public void testUpdateProject() {
		
		Project projectToUpdate = getTestProject();
			
		try {
			
			this.mockMvc.perform(put("/projects")
				.contentType(MediaType.APPLICATION_JSON).content(toJson(projectToUpdate)))
				.andExpect(status().isOk());
			
			verify(this.mockProjectService).updateProject(any(Project.class));
			
		} catch(Exception exce) {
			exce.printStackTrace();
		}	
	}
	
	
	protected Project getTestProject() {
		
		Project testProject = new Project();
		testProject.setProject("Project1");
		testProject.setManagerStr("111111 - JUNIT TEST");
		testProject.setStartDateStr("2019/06/01");
		testProject.setEndDateStr("2019/12/31");
		testProject.setSuspendFlag("Y");
		
		return testProject;
		
	}
	
	private byte[] toJson(Object obj) throws JsonProcessingException {
		ObjectMapper objMapper = new ObjectMapper();
		return objMapper.writeValueAsBytes(obj);
	}
}
