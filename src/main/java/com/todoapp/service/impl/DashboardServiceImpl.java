package com.todoapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapp.exception.NotFoundException;
import com.todoapp.exception.UnauthorizedException;
import com.todoapp.model.Dashboard;
import com.todoapp.repository.DashboardRepository;
import com.todoapp.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService{

	@Autowired
	DashboardRepository dashboardRepository;
	
	@Override
	public Dashboard save(Dashboard dashboard) {
		return dashboardRepository.save(dashboard);
	}
	@Override
	public Dashboard findById(Long id) {
		return dashboardRepository.findById(id).orElseThrow(()->new NotFoundException("DASHBOARD_DOES_NOT_EXIST."));

	}
	@Override
	public List<Dashboard> findByUserUsername(String username) {
		return dashboardRepository.findByUserUsername(username);
	}
	@Override
	public void delete(Long id) {
		dashboardRepository.deleteById(id);
	}
	@Override
	public Boolean existsById(Long id) {
		return dashboardRepository.existsById(id);
	}
	@Override
	public void checkUserAuth(Dashboard dashboard, String username) {
		if(!username.equals(dashboard.getUser().getUsername())) {
			throw new UnauthorizedException("UNAUTHORIZED_OPERATION_ON_DASHBOARD.");
		}
	}

}
