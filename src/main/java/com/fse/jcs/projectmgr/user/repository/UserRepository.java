package com.fse.jcs.projectmgr.user.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fse.jcs.projectmgr.user.model.User;

public interface UserRepository extends JpaRepository<User,Integer> {
	
	@Query(value = "select * from user u where u.employee_id = :employeeId", nativeQuery = true)
	User findUserByEmployeeId(@Param("employeeId")String employeeId);
	
	@Transactional
	@Modifying
	@Query(value = "update user u set u.project_id = null where u.project_id= :projectId", nativeQuery = true)
	void updateUserForProject(@Param("projectId") Integer projectId);
	
	@Transactional
	@Modifying
	@Query(value = "update user u set u.task_id = null where u.task_id= :taskId", nativeQuery = true)
	void updateUserForTask(@Param("taskId") Integer taskId);

}
