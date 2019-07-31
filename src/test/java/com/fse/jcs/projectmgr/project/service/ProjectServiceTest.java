package com.fse.jcs.projectmgr.project.service;

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
import com.fse.jcs.projectmgr.task.model.Task;
import com.fse.jcs.projectmgr.user.model.User;
import com.fse.jcs.projectmgr.user.repository.UserRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {
	
	@Autowired
	ProjectService projectService;
	
	@MockBean
	private ProjectRepository mockProjectRepo;
	
	@MockBean
	private UserRepository mockUserRepo;
	
	final String DATE_PATTERN = "yyyy/MM/dd";
	SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
	
	@Test
	public void testFetchAllProjects() {
		
		List<Project> expected = getTestProjectsList();
	
		when(this.mockProjectRepo.findAll()).thenReturn(expected);
		
		List<Project> actual =  this.projectService.fetchAllProjects();
		
		assertEquals("Number of projects is not as expected",
				expected.size(),actual.size());			
		assertEquals("Completed Flag should be Y", "Y",actual.get(1).getCompleted());
		assertEquals("Suspend Flag should be N", "Y",actual.get(2).getSuspendFlag());
		assertEquals("Number of tasks should be 2",2,actual.get(0).getNumberOfTasks().intValue());
		
		verify(this.mockProjectRepo).findAll();
	}
	
	
	@Test
	public void testFetchProjectById() {
		
		Project expected = getTestProject();
		Integer projectId = expected.getProjectId();		
		
		when(this.mockProjectRepo.findById(projectId))
			.thenReturn(Optional.of(expected));
		
		Project actual = this.projectService.fetchProjectById(projectId);
		
		assertEquals("Project Name is not as expected","Project1",actual.getProject());
		verify(this.mockProjectRepo).findById(projectId);
	}
	
	
	@Test
	public void testFetchProjectById_No_Project_Found() {
		
		Integer projectId = getTestProject().getProjectId();		
		
		when(this.mockProjectRepo.findById(projectId))
			.thenThrow(NoSuchElementException.class);
		
		Project actual = this.projectService.fetchProjectById(projectId);
		
		assertNull("Project should be NULL",actual);
		verify(this.mockProjectRepo).findById(projectId);
	}
	
	@Test
	public void testFetchProjectByProjectName() {
		
		Project expected = getTestProject();
		String projectName = expected.getProject();		
		
		when(this.mockProjectRepo.findProjectByName(projectName))
			.thenReturn(expected);
		
		Project actual = this.projectService.fetchProjectByProjectName(projectName);
		
		assertEquals("Project Name is not as expected","Project1",actual.getProject());
		verify(this.mockProjectRepo).findProjectByName(projectName);
	}

	@Test
	public void testSaveProject_Success() throws ParseException {
		
		String employeeId = "111111";
		Project projectToSave = getTestProjectForSaveOrUpdate();
		User testUser = getTestUser();
				
		when(this.mockProjectRepo.save(projectToSave))
			.thenReturn(projectToSave);		
		when(this.mockUserRepo.findUserByEmployeeId(employeeId))
			.thenReturn(testUser);
		when(this.mockUserRepo.save(testUser))
			.thenReturn(testUser);
		
		this.projectService.saveProject(projectToSave, false);
		
		verify(this.mockProjectRepo).save(projectToSave);
		verify(this.mockUserRepo).save(testUser);
	}
	
	
	@Test
	public void testSaveProject_No_Manager_String() throws ParseException {
		
		Project projectToSave = getTestProjectForSaveOrUpdate();
		projectToSave.setManagerStr(null);
		
		when(this.mockProjectRepo.save(projectToSave))
			.thenReturn(projectToSave);		
		
		this.projectService.saveProject(projectToSave, false);
		
		verify(this.mockProjectRepo).save(projectToSave);
		verify(this.mockUserRepo,never()).findUserByEmployeeId(anyString());
		verify(this.mockUserRepo,never()).save(any(User.class));
	}
	
	
	@Test
	public void testUpdateProject() throws ParseException {
		
		String employeeId = "111111";
		Project projectToUpdate = getTestProjectForSaveOrUpdate();
		projectToUpdate.setEndDateStr("2019/07/26");
		
		User testUser = getTestUser();
		
		when(this.mockProjectRepo.save(projectToUpdate))
			.thenReturn(projectToUpdate);		
		when(this.mockUserRepo.findUserByEmployeeId(employeeId))
			.thenReturn(testUser);
		doNothing().when(this.mockUserRepo)
			.updateUserForProject(projectToUpdate.getProjectId());
		
		this.projectService.updateProject(projectToUpdate);
		
		verify(this.mockProjectRepo).save(projectToUpdate);
		verify(this.mockUserRepo,never()).save(testUser);
		verify(this.mockUserRepo).updateUserForProject(projectToUpdate.getProjectId());	
	}
	
	@Test
	public void testSuspendProject() {
		
		Integer projectId = 1234;
		
		doNothing().when(this.mockProjectRepo)
			.suspendProject(projectId);
		doNothing().when(this.mockUserRepo)
			.updateUserForProject(projectId);
		
		this.projectService.suspendProject(projectId);
		
		verify(this.mockProjectRepo).suspendProject(projectId);
		verify(this.mockUserRepo).updateUserForProject(projectId);
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
	
	
	protected Project getTestProjectForSaveOrUpdate() {
		
		this.dateFormat.setLenient(false);
		
		Calendar cal = Calendar.getInstance();				
		String startDateStr = this.dateFormat.format(cal.getTime());
		
		cal.add(Calendar.DATE, 50);
		String endDateStr = this.dateFormat.format(cal.getTime());
		
		Project testProject = new Project();
		testProject.setProjectId(1);
		testProject.setProject("Project1");
		testProject.setManagerStr("111111 - JUNIT TEST");
		testProject.setStartDateStr(startDateStr);
		testProject.setEndDateStr(endDateStr);
		testProject.setSuspendFlag("N");
		
		return testProject;		
	}
	
	protected List<Project> getTestProjectsList() {
		
		List<Project> testProjectsList = new ArrayList<Project>();
		this.dateFormat.setLenient(false);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -10);
		
		Date pastDate = calendar.getTime(); 
		
		calendar.add(Calendar.DATE, 100);
		Date futureDate = calendar.getTime();
		
		List<Task> dummyTaskList = new ArrayList<Task>();
		
		Project project1 = new Project();
		project1.setProjectId(1);
		project1.setProject("Project1");
		project1.setStartDateStr("2019/06/01");
		project1.setStartDate(new Date());
		project1.setEndDateStr("2019/12/31");
		project1.setEndDate(futureDate);
		project1.setSuspendFlag("N");
		project1.setUser(getTestUser());
		project1.setTasks(getTestTasksList());
		
		Project project2 = new Project();
		project2.setProjectId(2);
		project2.setProject("Project2");
		project2.setStartDate(pastDate);
		project2.setEndDate(new Date());
		project2.setSuspendFlag("N");			
		
		Project project3 = new Project();
		project2.setProjectId(3);
		project3.setProject("Project3");
		project3.setStartDate(pastDate);
		project3.setEndDate(futureDate);
		project3.setSuspendFlag("Y");
		project3.setUser(getTestUser());
		project3.setTasks(dummyTaskList);
		
		testProjectsList.add(project1);
		testProjectsList.add(project2);
		testProjectsList.add(project3);
		
		return testProjectsList;
	}
	
	
	protected User getTestUser() {
		
		User testUser = new User();
		testUser.setUserId(1);
		testUser.setEmployeeId("111111");
		testUser.setFirstName("JUNIT");
		testUser.setLastName("TEST");
		
		return testUser;
	}
	
	protected List<Task> getTestTasksList() {
		
		List<Task> testTasksList = new ArrayList<Task>();
		
		Task task1 = new Task();
		task1.setTaskId(1);
		task1.setTaskName("Task1");
		
		Task task2 = new Task();
		task2.setTaskId(2);
		task2.setTaskName("Task2");
		
		testTasksList.add(task1);
		testTasksList.add(task2);
		
		return testTasksList;
	}

}
