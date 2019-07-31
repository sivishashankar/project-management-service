package com.fse.jcs.projectmgr.task.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fse.jcs.projectmgr.project.model.Project;
import com.fse.jcs.projectmgr.project.repository.ProjectRepository;
import com.fse.jcs.projectmgr.task.model.ParentTask;
import com.fse.jcs.projectmgr.task.model.Task;
import com.fse.jcs.projectmgr.task.repository.TaskRepository;
import com.fse.jcs.projectmgr.user.model.User;
import com.fse.jcs.projectmgr.user.repository.UserRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {
	
	@Autowired
	TaskService taskService;
	
	@MockBean
	private TaskRepository mockTaskRepo;
	
	@MockBean
	private ProjectRepository mockProjectRepo;
	
	@MockBean
	private UserRepository mockUserRepo;
	
	final String DATE_PATTERN = "yyyy/MM/dd";
	SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
	
	@Test
	public void testFetchAllTasks() {
		
		List<Task> expected = getTestTasksList();
		
		String expectedDateStr = this.dateFormat
				.format(Calendar.getInstance().getTime());
		String expectedUserStr = "111111 - JUNIT TEST";
		String expectedProjectName = "Project1";
		
		when(this.mockTaskRepo.findAll()).thenReturn(expected);
		
		List<Task> actual = this.taskService.fetchAllTasks();
		
		assertEquals("Number of Tasks should be 3", 3, actual.size());
		assertEquals("Date is not as expected", expectedDateStr, 
				actual.get(0).getStartDateStr());
		
		assertEquals("Completed is not as expected", "Y", 
				actual.get(0).getCompleted());
		assertEquals("Completed is not as expected", "N", 
				actual.get(2).getCompleted());
		
		assertEquals("User is not as expected",expectedUserStr,
				actual.get(0).getUserString());
		assertEquals("Project is not as expected",expectedProjectName,
				actual.get(0).getProjectName());
		
		verify(this.mockTaskRepo).findAll();
	}
	
	@Test
	public void testFetchTaskById() {
		
		Integer taskIdToToFetch = 1;
		
		Task expected = getTestTasksList().get(0);
		String expectedUserStr = "111111 - JUNIT TEST";
		String expectedProjectName = "Project1";
		
		when(this.mockTaskRepo.findById(taskIdToToFetch))
			.thenReturn(Optional.of(expected));
		
		Task actual = this.taskService.fetchTaskById(taskIdToToFetch);
		
		assertNotNull("Task should not be NULL", actual);
		assertEquals("Completed is not as expected", "Y", actual.getCompleted());
		assertEquals("User is not as expected",expectedUserStr, actual.getUserString());
		assertEquals("Project is not as expected",expectedProjectName, actual.getProjectName());
		
		verify(this.mockTaskRepo).findById(taskIdToToFetch);
		
	}
	
	@Test
	public void testFetchTaskById_No_Task_Found() {
		
		Integer taskIdToToFetch = 1;
		
		when(this.mockTaskRepo.findById(taskIdToToFetch))
			.thenThrow(NoSuchElementException.class);
		
		Task actual = this.taskService.fetchTaskById(taskIdToToFetch);
		
		assertNull("Task should be NULL", actual);
		verify(this.mockTaskRepo).findById(taskIdToToFetch);
	}
	
	@Test
	public void testSaveTask_Save_Parent_Task() throws ParseException {
		
		Task testTaskToSave = new Task();
		String parentTaskName = "Test Parent Task";
		String projectName = "Project1";
		
		testTaskToSave.setTaskName("Test Task");
		testTaskToSave.setParentTaskName(parentTaskName);
		testTaskToSave.setProjectName(projectName);
		
		when(this.mockTaskRepo.findParentTask(parentTaskName))
			.thenReturn(null);
		when(this.mockTaskRepo.save(testTaskToSave))
			.thenReturn(testTaskToSave);
		when(this.mockProjectRepo.findProjectByName(projectName))
			.thenReturn(getTestProject());
		
		this.taskService.saveTask(testTaskToSave, false);
		
		verify(this.mockTaskRepo).save(testTaskToSave);
		verify(this.mockTaskRepo).findParentTask(parentTaskName);
		
		verify(this.mockProjectRepo).findProjectByName(projectName);
	}
	
	@Test
	public void testSaveTask() throws ParseException {
		
		Task testTaskToSave = getTestTaskToSaveOrUpdate();
		testTaskToSave.setProjectName(null);
		
		when(this.mockTaskRepo.findParentTask(testTaskToSave.getParentTaskName()))
			.thenReturn(1);
		when(this.mockTaskRepo.save(testTaskToSave))
			.thenReturn(testTaskToSave);
		when(this.mockUserRepo.findUserByEmployeeId("111111"))
			.thenReturn(getTestUser());
		
		this.taskService.saveTask(testTaskToSave, false);
		
		verify(this.mockTaskRepo).save(testTaskToSave);
		verify(this.mockTaskRepo).findParentTask(
				testTaskToSave.getParentTaskName());
		
		verify(this.mockUserRepo).findUserByEmployeeId("111111");		
	}
	
	
	@Test
	public void testSaveTask_Null() throws ParseException {
		
		Task testTaskToSave = null;
		
		this.taskService.saveTask(testTaskToSave, false);
		
		verify(this.mockTaskRepo,never()).save(testTaskToSave);
	}
	
	@Test
	public void testUpdateTask() throws ParseException {
		
		Task testTaskToUpdate = getTestTaskToSaveOrUpdate();
		testTaskToUpdate.setParentTaskName(null);
		testTaskToUpdate.setStartDateStr("");
		testTaskToUpdate.setEndDateStr("");
		
		doNothing().when(this.mockUserRepo).updateUserForTask(testTaskToUpdate.getTaskId());
		when(this.mockTaskRepo.save(testTaskToUpdate))
			.thenReturn(testTaskToUpdate);
		
		this.taskService.updateTask(testTaskToUpdate);
		
		verify(this.mockTaskRepo).save(testTaskToUpdate);
		verify(this.mockUserRepo).updateUserForTask(testTaskToUpdate.getTaskId());
	}
	
	@Test
	public void testUpdateTask_Null() throws ParseException {
		
		Task testTaskToUpdate = null;
		
		this.taskService.updateTask(testTaskToUpdate);
		
		verify(this.mockTaskRepo,never()).save(testTaskToUpdate);
	}
	
	@Test
	public void testEndTask() {
		
		Integer taskId = 12;
		
		doNothing().when(this.mockTaskRepo).endTask(taskId);
		doNothing().when(this.mockUserRepo).updateUserForTask(taskId);
		
		this.taskService.endTask(taskId);
		
		verify(this.mockTaskRepo).endTask(taskId);
		verify(this.mockUserRepo).updateUserForTask(taskId);
	}
	
	protected List<Task> getTestTasksList() {
		
		List<Task> testTasksList = new ArrayList<Task>();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 30);
		Date futureDate = cal.getTime();
		
		ParentTask testParentTask = new ParentTask();
		testParentTask.setParentTaskName("Test Parent Task");
		
		Task task1 = new Task();
		task1.setTaskId(1);
		task1.setTaskName("Task1");
		task1.setProject(getTestProject());
		task1.setUser(getTestUser());
		task1.setStartDate(new Date());
		task1.setEndDate(new Date());
		
		Task task2 = new Task();
		task2.setTaskId(2);
		task2.setTaskName("Task2");
		task2.setParentTask(testParentTask);		
		
		Task task3 = new Task();
		task3.setTaskId(3);
		task3.setTaskName("Task3");
		task3.setProject(getTestProject());
		task3.setStartDate(new Date());
		task3.setEndDate(futureDate);
		
		testTasksList.add(task1);
		testTasksList.add(task2);
		testTasksList.add(task3);
		
		return testTasksList;
	}
		

	protected Project getTestProject() {
		
		Project testProject = new Project();
		testProject.setProjectId(1);
		testProject.setProject("Project1");
		testProject.setManagerStr("111111 - JUNIT TEST");
		testProject.setStartDate(new Date());
		testProject.setEndDate(new Date());
		testProject.setSuspendFlag("N");
		testProject.setUser(getTestUser());
		
		return testProject;		
	}
	
	protected User getTestUser() {
		
		User testUser = new User();
		testUser.setUserId(1);
		testUser.setEmployeeId("111111");
		testUser.setFirstName("JUNIT");
		testUser.setLastName("TEST");
		
		return testUser;
	}
	
	protected Task getTestTaskToSaveOrUpdate() {
		
		Task testTaskToSave = new Task();
		Calendar cal = Calendar.getInstance();
		
		String parentTaskName = "Test Parent Task";
		String projectName = "Project1";
		String startDateStr = this.dateFormat.format(cal.getTime());
		
		cal.add(Calendar.DATE, 50);
		String endDateStr = this.dateFormat.format(cal.getTime());		
		
		testTaskToSave.setTaskId(1);
		testTaskToSave.setTaskName("Test Task");
		testTaskToSave.setParentTaskName(parentTaskName);
		testTaskToSave.setProjectName(projectName);
		testTaskToSave.setStartDateStr(startDateStr);
		testTaskToSave.setEndDateStr(endDateStr);
		testTaskToSave.setUserString("111111 - JUNIT TEST");
		
	    return testTaskToSave;
	}


}

