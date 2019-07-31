package com.fse.jcs.projectmgr.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fse.jcs.projectmgr.project.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

	@Query(value = "select * from project p where p.project = :projectName", nativeQuery = true)
	Project findProjectByName(@Param("projectName")String projectName);
	
	@Modifying
	@Query(value = "update project set suspend='Y' where project_id = :projectId", nativeQuery = true)
	void suspendProject(@Param("projectId") Integer projectId);
}
