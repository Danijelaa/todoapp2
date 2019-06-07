package com.todoapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.dto.DashboardColumnCreateDto;
import com.todoapp.dto.DashboardColumnDto;
import com.todoapp.dto.DashboardColumnLightDto;
import com.todoapp.dto.TaskDto;
import com.todoapp.mapper.DashboardColumnToDashboardColumnDto;
import com.todoapp.mapper.DashboardColumnToDashboardColumnLightDto;
import com.todoapp.mapper.TaskToTaskDto;
import com.todoapp.model.Dashboard;
import com.todoapp.model.DashboardColumn;
import com.todoapp.model.Task;
import com.todoapp.service.DashboardColumnService;
import com.todoapp.service.DashboardService;
import com.todoapp.service.TaskService;
import com.todoapp.service.UserService;

@RestController
@RequestMapping(value="/api/dashboard-columns")
public class DashboardColumnController {

	@Autowired
	DashboardColumnService dashboardColumnService;
	@Autowired
	UserService userService;
	@Autowired
	DashboardColumnToDashboardColumnDto toDashboardColumn;
	@Autowired
	DashboardColumnToDashboardColumnLightDto toDashboardLightDto;
	@Autowired
	DashboardService dashboardService;
	@Autowired
	TaskService taskService;
	@Autowired
	TaskToTaskDto toTaskDto;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	ResponseEntity<DashboardColumnDto> findById(HttpServletRequest request, @PathVariable Long id) {
		DashboardColumn dashboardColumn=dashboardColumnService.findById(id);
		dashboardColumnService.checkUserAuth(dashboardColumn, userService.findUsernameFromPrincipal(request));
		return new ResponseEntity<DashboardColumnDto>(toDashboardColumn.convert(dashboardColumn), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	ResponseEntity<?> deleteById(HttpServletRequest request, @PathVariable Long id) {
		DashboardColumn dashboardColumn=dashboardColumnService.findById(id);
		dashboardColumnService.checkUserAuth(dashboardColumn, userService.findUsernameFromPrincipal(request));
		dashboardColumnService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> create(HttpServletRequest request, @RequestBody DashboardColumnCreateDto dashboardColumnLightDto) {
		Dashboard dashboard=dashboardService.findById(dashboardColumnLightDto.getDashboardId());
		dashboardService.checkUserAuth(dashboard, userService.findUsernameFromPrincipal(request));
		DashboardColumn dashboardColumn=new DashboardColumn();
		dashboardColumn.setTitle(dashboardColumnLightDto.getTitle());
		dashboardColumn.setDashboard(dashboard);
		dashboardColumn=dashboardColumnService.save(dashboardColumn);
		return new ResponseEntity<DashboardColumnDto>(toDashboardColumn.convert(dashboardColumn), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	ResponseEntity<?> update(HttpServletRequest request, @PathVariable Long id, @RequestBody DashboardColumnLightDto dashboardColumnLightDto) {
		if(!id.equals(dashboardColumnLightDto.getId())) {
			return new ResponseEntity<>("IDS_DO_NOT_MATCH.", HttpStatus.BAD_REQUEST);
		}
		DashboardColumn dashboardColumn=dashboardColumnService.findById(id);
		dashboardColumnService.checkUserAuth(dashboardColumn, userService.findUsernameFromPrincipal(request));
		dashboardColumn.setTitle(dashboardColumnLightDto.getTitle());
		dashboardColumn=dashboardColumnService.save(dashboardColumn);
		return new ResponseEntity<DashboardColumnDto>(toDashboardColumn.convert(dashboardColumn), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}/tasks", method = RequestMethod.GET)
	ResponseEntity<List<TaskDto>> getTasks(HttpServletRequest request, @PathVariable Long id) {
		DashboardColumn dashboardColumn=dashboardColumnService.findById(id);
		dashboardColumnService.checkUserAuth(dashboardColumn, userService.findUsernameFromPrincipal(request));
		List<Task> tasks=taskService.findByDashboardColumnId(id);
		return new ResponseEntity<List<TaskDto>>(toTaskDto.convert(tasks), HttpStatus.OK);
	}
	
}
