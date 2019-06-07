package com.todoapp.service;

import java.util.List;

import com.todoapp.model.Task;

public interface TaskService {

	Task findById(Long id);
	List<Task> findByDashboardColumnId(Long id);
	void delete(Long id);
	Task save(Task task);
	Task update(Task task);
}
