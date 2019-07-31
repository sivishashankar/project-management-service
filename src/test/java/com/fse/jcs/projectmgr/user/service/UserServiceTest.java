package com.fse.jcs.projectmgr.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.fse.jcs.projectmgr.user.dao.UserDao;
import com.fse.jcs.projectmgr.user.dao.impl.UserDaoImpl;
import com.fse.jcs.projectmgr.user.model.User;
import com.fse.jcs.projectmgr.user.repository.UserRepository;
import com.fse.jcs.projectmgr.user.service.impl.UserServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {UserServiceImpl.class, UserDaoImpl.class})
public class UserServiceTest {
	
	@Autowired
	UserService userService;
	
	@MockBean
	UserRepository mockUserRepository;
	
	
	@Test
	public void testFetchAllUsers() {
		
		List<User> expected = new ArrayList<User>();
		
		User user1 = getTestUser();
		
		User user2 = new User();
		user2.setUserId(1);
		user2.setEmployeeId("222222");
		user2.setFirstName("User");
		user2.setLastName("2");
		
		expected.add(user1);
		expected.add(user2);
		
		when(this.mockUserRepository.findAll()).thenReturn(expected);
		
		List<User> actual = this.userService.fetchAllUsers();
		
		assertEquals(actual.size(), 2);		
	}
	
	@Test
	public void testFetchUserById() {
		
		Integer userId = 1;
		
		User expected = getTestUser();
		
		when(this.mockUserRepository.findById(userId))
			.thenReturn(Optional.of(expected));
		
		User actual = this.userService.fetchUserById(userId);
		
		assertEquals("Employee Id is not as expected",expected.getEmployeeId(),
				actual.getEmployeeId());
	}
	
	@Test
	public void testFetchUserById_NoUserFound() {
		
		Integer userId = 1;
		
		when(this.mockUserRepository.findById(userId))
			.thenThrow(NoSuchElementException.class);
		
		User actual = this.userService.fetchUserById(userId);
		
		assertNull("User should be NULL",actual);
	}
	
	@Test
	public void testFetchUserByEmployeeId() {
		
		String employeeId = "111111";
		
		User expected = getTestUser();
		
		when(this.mockUserRepository.findUserByEmployeeId(employeeId))
			.thenReturn(expected);
		
		User actual = this.userService.fetchUserByEmployeeId(employeeId);
		
		assertEquals("Employee Id is not as expected",expected.getFirstName(),
				actual.getFirstName());
	}
	
	@Test
	public void testSaveUser() {
		
		User userToSave = getTestUser();
		
		when(this.mockUserRepository.save(userToSave))
			.thenReturn(userToSave);
		
		this.userService.saveUser(userToSave);
		
		verify(this.mockUserRepository).save(userToSave);
	}
	
	@Test 
	public void testDeleteUser() {
		
		User userToDelete = getTestUser();
		Integer userId = 1;
		
		when(this.mockUserRepository.findById(userId))
			.thenReturn(Optional.of(userToDelete));
		
		doNothing().when(this.mockUserRepository).delete(userToDelete);
		
		this.userService.deleteUser(userId);
		
		verify(this.mockUserRepository).findById(userId);
		verify(this.mockUserRepository).delete(userToDelete);	
		
	}
	
	
	protected User getTestUser() {
		
		User testUser = new User();
		testUser.setUserId(1);
		testUser.setEmployeeId("111111");
		testUser.setFirstName("JUNIT");
		testUser.setLastName("1");
		
		return testUser;
	}

}
