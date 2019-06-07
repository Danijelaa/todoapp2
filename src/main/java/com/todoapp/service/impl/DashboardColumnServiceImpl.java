package com.todoapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapp.exception.NotFoundException;
import com.todoapp.exception.UnauthorizedException;
import com.todoapp.model.DashboardColumn;
import com.todoapp.repository.DashboardColumnRepository;
import com.todoapp.service.DashboardColumnService;

@Service
public class DashboardColumnServiceImpl implements DashboardColumnService{

	@Autowired
	DashboardColumnRepository dashboardColumnRepository;
	
	@Override
	public DashboardColumn save(DashboardColumn dashboardColumn) {
		return dashboardColumnRepository.save(dashboardColumn);
	}

	@Override
	public DashboardColumn findById(Long id) {
		return dashboardColumnRepository.findById(id).orElseThrow(()-> new NotFoundException("DASHBOARD_COLUM_DOES_NOT_EXIST."));
	}

	@Override
	public List<DashboardColumn> findByDashboardId(Long id) {
		return dashboardColumnRepository.findByDashboardId(id);
	}

	@Override
	public void delete(Long id) {
		dashboardColumnRepository.deleteById(id);
	}

	@Override
	public void checkUserAuth(DashboardColumn dashboardColumn, String username) {
		if(!username.equals(dashboardColumn.getDashboard().getUser().getUsername())) {
			throw new UnauthorizedException("UNAUTHORIZED_OPERATION_ON_DASHBOARD_COLUMN.");
		}
	}

}
