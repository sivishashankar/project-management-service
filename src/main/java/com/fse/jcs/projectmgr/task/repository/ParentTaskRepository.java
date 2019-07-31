package com.fse.jcs.projectmgr.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fse.jcs.projectmgr.task.model.ParentTask;

public interface ParentTaskRepository extends JpaRepository<ParentTask, Integer> {

}
