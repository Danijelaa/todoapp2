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

import com.todoapp.dto.DashboardDto;
import com.todoapp.dto.DashboardLightDto;
import com.todoapp.mapper.DashboardToDashboardDto;
import com.todoapp.mapper.DashboardToDashboardLightDto;
import com.todoapp.model.Dashboard;
import com.todoapp.service.DashboardService;
import com.todoapp.service.UserService;

@RestController
@RequestMapping(value="/api/dashboards")
public class DashboardController {

	@Autowired
	DashboardService dashboardService;
	@Autowired
	DashboardToDashboardDto toDashboardDto;
	@Autowired
	DashboardToDashboardLightDto toDashboardLightDto;
	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<List<DashboardLightDto>> findByUser(HttpServletRequest request) {
		List<Dashboard> dashboards=dashboardService.findByUserUsername(userService.findUsernameFromPrincipal(request));
		return new ResponseEntity<List<DashboardLightDto>>(toDashboardLightDto.convert(dashboards), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	ResponseEntity<DashboardDto> findById(HttpServletRequest request, @PathVariable Long id) {
		Dashboard dashboard=dashboardService.findById(id);
		dashboardService.checkUserAuth(dashboard, userService.findUsernameFromPrincipal(request));
		return new ResponseEntity<DashboardDto>(toDashboardDto.convert(dashboard), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	ResponseEntity<?> deleteById(HttpServletRequest request, @PathVariable Long id) {
		Dashboard dashboard=dashboardService.findById(id);
		dashboardService.checkUserAuth(dashboard, userService.findUsernameFromPrincipal(request));
		dashboardService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> create(HttpServletRequest request, @RequestBody DashboardLightDto dashboardLightDto) {
		Dashboard dashboard=new Dashboard();
		dashboard.setTitle(dashboardLightDto.getTitle());
		dashboard.setUser(userService.findByUsername(userService.findUsernameFromPrincipal(request)));
		dashboard=dashboardService.save(dashboard);
		return new ResponseEntity<DashboardDto>(toDashboardDto.convert(dashboard), HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	ResponseEntity<?> update(HttpServletRequest request, @RequestBody DashboardLightDto dashboardLightDto, @PathVariable Long id) {
		if(!id.equals(dashboardLightDto.getId())) {
			return new ResponseEntity<>("IDS_DO_NOT_MATCH.", HttpStatus.BAD_REQUEST);
		}
		Dashboard dashboard=dashboardService.findById(id);
		dashboardService.checkUserAuth(dashboard, userService.findUsernameFromPrincipal(request));
		dashboard.setTitle(dashboardLightDto.getTitle());
		dashboard=dashboardService.save(dashboard);
		return new ResponseEntity<DashboardDto>(toDashboardDto.convert(dashboard), HttpStatus.CREATED);
	}
	
}
