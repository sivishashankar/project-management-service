package com.fse.jcs.projectmgr.user.controller;

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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.jcs.projectmgr.user.model.User;
import com.fse.jcs.projectmgr.user.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
	
	@MockBean
	UserService mockUserService;
	
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webAppCtx;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webAppCtx);
		this.mockMvc = builder.build();
	}
	
	
	@Test
	public void testGetAllUsers() {

		try {
			
			this.mockMvc.perform(get("/users/all").contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
			
			verify(this.mockUserService).fetchAllUsers();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFetchUserById() {

		try {
			this.mockMvc.perform(get("/users/fetch/" + 1).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
			
			verify(this.mockUserService).fetchUserById(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFetchUserByEmployeeId() {

		String employeeId= "152915";
		
		try {
			
			this.mockMvc.perform(get("/users/employee/" + employeeId).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
			
			verify(this.mockUserService).fetchUserByEmployeeId(employeeId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSaveUser() {
		
		User user = new User();
		user.setEmployeeId("111111");
		user.setFirstName("JUNIT");
		user.setLastName("TEST");
		
		try {
			
			this.mockMvc.perform(post("/users/adduser").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(user)))
				.andExpect(status().isOk());
			
			verify(this.mockUserService).saveUser(any(User.class));
			
		} catch(Exception exce) {
			exce.printStackTrace();
		}		
	}
	
	
	@Test
	public void testUpdateUser() {
		
		User userToUpdate = new User();
		userToUpdate.setEmployeeId("111111");
		userToUpdate.setFirstName("JUNIT");
		userToUpdate.setLastName("TEST");
		
		try {
			
			this.mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(userToUpdate)))
				.andExpect(status().isOk());
			
			verify(this.mockUserService).saveUser(any(User.class));
			
		} catch(Exception exce) {
			exce.printStackTrace();
		}		
	}
	
	
	@Test
	public void testDeleteUser() {
		
		Integer userIdToDelete = 34567;
		
		try {
			
			this.mockMvc.perform(delete("/users/"+userIdToDelete).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
			
			verify(this.mockUserService).deleteUser(userIdToDelete);
			
		} catch(Exception exce) {
			exce.printStackTrace();
		}	
		
	}	
	
	
	private byte[] toJson(Object obj) throws JsonProcessingException {
		ObjectMapper objMapper = new ObjectMapper();
		return objMapper.writeValueAsBytes(obj);
	}


}
