package com.fse.jcs.projectmgr.task.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.jcs.projectmgr.task.model.Task;
import com.fse.jcs.projectmgr.task.service.TaskService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskControllerTest {
	
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webAppCtx;
	
	@MockBean
	TaskService mockTaskService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webAppCtx);
		this.mockMvc = builder.build();
	}
	
	
	@Test
	public void testGetAllTasks() {

		try {
			
			this.mockMvc.perform(get("/tasks/all").contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
			
			verify(this.mockTaskService).fetchAllTasks();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFetchTaskById() {

		try {
			this.mockMvc.perform(get("/tasks/fetch/" + 1).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
			
			verify(this.mockTaskService).fetchTaskById(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSaveTask() {
		
		Task testTask = getTestTask();
		
		try {
			
			this.mockMvc.perform(post("/tasks").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(testTask)))
				.andExpect(status().isOk());
			
			verify(this.mockTaskService).saveTask(any(Task.class),any(Boolean.class));
			
		} catch(Exception exce) {
			exce.printStackTrace();
		}		
	}
	
	@Test
	public void testUpdateTask() {
		
		Task testTaskToUpdate = getTestTask();
		
		try {
			
			this.mockMvc.perform(put("/tasks").contentType(MediaType.APPLICATION_JSON)
					.content(toJson(testTaskToUpdate)))
					.andExpect(status().isOk());
			
			verify(this.mockTaskService).updateTask(any(Task.class));
			
		} catch(Exception exce) {
			exce.printStackTrace();
		}		
	}
	
	@Test
	public void tesEndTask() {
		
		Integer taskId = 1;
		
		try {
			
			this.mockMvc.perform(put("/tasks/endtask/"+taskId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
			
			verify(this.mockTaskService).endTask(taskId);
			
		} catch(Exception exce) {
			exce.printStackTrace();
		}		
	}
	
	protected Task getTestTask() {
		
		Task testTask =  new Task();
		
		testTask.setTaskId(1);
		testTask.setTaskName("Task 1");
		testTask.setStartDateStr("2019/07/28");
		testTask.setEndDateStr("2019/10/31");
		
		return testTask;
	}
	
	private byte[] toJson(Object obj) throws JsonProcessingException {
		ObjectMapper objMapper = new ObjectMapper();
		return objMapper.writeValueAsBytes(obj);
	}

}
