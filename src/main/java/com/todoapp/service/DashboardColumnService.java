package com.todoapp.service;

import java.util.List;

import com.todoapp.model.DashboardColumn;

public interface DashboardColumnService {

	DashboardColumn save(DashboardColumn dashboardColumn);
	DashboardColumn findById(Long id);
	List<DashboardColumn> findByDashboardId(Long id);
	void checkUserAuth(DashboardColumn dashboardColumn, String username);
	void delete(Long id);
}
