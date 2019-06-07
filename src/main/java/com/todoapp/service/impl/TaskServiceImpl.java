package com.todoapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapp.exception.NotFoundException;
import com.todoapp.model.Task;
import com.todoapp.repository.TaskRepository;
import com.todoapp.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{

	@Autowired
	TaskRepository taskRepository;
	
	@Override
	public Task findById(Long id) {
		return taskRepository.findById(id).orElseThrow(() -> new NotFoundException("TEAM_DOES_NOT_EXIST"));
	}

	@Override
	public List<Task> findByDashboardColumnId(Long id) {
		return taskRepository.findByDashboardColumnId(id);
	}

	@Override
	public void delete(Long id) {
		findById(id);
		taskRepository.deleteById(id);
	}

	@Override
	public Task save(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public Task update(Task task) {
		findById(task.getId());
		
		return taskRepository.save(task);
	}

}
