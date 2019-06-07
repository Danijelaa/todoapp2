package com.todoapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapp.exception.NotFoundException;
import com.todoapp.exception.UnauthorizedException;
import com.todoapp.model.Task;
import com.todoapp.repository.TaskRepository;
import com.todoapp.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{

	@Autowired
	TaskRepository taskRepository;
	
	@Override
	public Task findById(Long id) {
		return taskRepository.findById(id).orElseThrow(() -> new NotFoundException("TASK_DOES_NOT_EXIST"));
	}

	@Override
	public List<Task> findByDashboardColumnId(Long id) {
		return taskRepository.findByDashboardColumnId(id);
	}

	@Override
	public void delete(Long id) {
		taskRepository.deleteById(id);
	}

	@Override
	public Task save(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public Task update(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public void checkUserAuth(Task task, String username) {
		if(!username.equals(task.getDashboardColumn().getDashboard().getUser().getUsername())) {
			throw new UnauthorizedException("UNAUTHORIZED_OPERATION_ON_TASK.");
		}
	}

}
