package com.todoapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.dto.TaskDto;
import com.todoapp.mapper.TaskToTaskDto;
import com.todoapp.model.DashboardColumn;
import com.todoapp.model.Task;
import com.todoapp.service.DashboardColumnService;
import com.todoapp.service.TaskService;
import com.todoapp.service.UserService;

@RestController
@RequestMapping(value="/api/tasks")
public class TaskController {

	@Autowired
	TaskService taskService;
	@Autowired
	UserService usersErvice;
	@Autowired
	TaskToTaskDto toTaskDto;
	@Autowired
	DashboardColumnService dashboardColumnService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	ResponseEntity<TaskDto> findById(HttpServletRequest request, @PathVariable Long id){
		Task task=taskService.findById(id);
		taskService.checkUserAuth(task, usersErvice.findUsernameFromPrincipal(request));
		return new ResponseEntity<TaskDto>(toTaskDto.convert(task), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	ResponseEntity<TaskDto> deleteById(HttpServletRequest request, @PathVariable Long id){
		Task task=taskService.findById(id);
		taskService.checkUserAuth(task, usersErvice.findUsernameFromPrincipal(request));
		taskService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<TaskDto> create(HttpServletRequest request, @Validated @RequestBody TaskDto taskDto){
		DashboardColumn dashboardColumn=dashboardColumnService.findById(taskDto.getDashboardColumnId());
		dashboardColumnService.checkUserAuth(dashboardColumn, usersErvice.findUsernameFromPrincipal(request));
		Task task=new Task();
		task.setDashboardColumn(dashboardColumn);
		task.setDescription(taskDto.getDescription());
		task.setTitle(taskDto.getTitle());
		task=taskService.save(task);
		return new ResponseEntity<TaskDto>(toTaskDto.convert(task), HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	ResponseEntity<?> update(HttpServletRequest request, @PathVariable Long id, @Validated @RequestBody TaskDto taskDto){
		if(!id.equals(taskDto.getId())) {
			return new ResponseEntity<>("IDS_DO_NOT_MATCH.", HttpStatus.BAD_REQUEST);
		}
		
		Task task=taskService.findById(id);
		taskService.checkUserAuth(task, usersErvice.findUsernameFromPrincipal(request));
		
		DashboardColumn dashboardColumn=dashboardColumnService.findById(taskDto.getDashboardColumnId());
		if(!dashboardColumn.getDashboard().equals(task.getDashboardColumn().getDashboard())) {
			return new ResponseEntity<>("TASK_CAN_NOT_BE_MOVED_TO_ANOTHER_DASHBOARD.", HttpStatus.BAD_REQUEST);
		}
		
		task.setDescription(taskDto.getDescription());
		task.setTitle(taskDto.getTitle());
		task.setDashboardColumn(dashboardColumn);
		task=taskService.save(task);
		return new ResponseEntity<TaskDto>(toTaskDto.convert(task), HttpStatus.OK);
	}
	
}
