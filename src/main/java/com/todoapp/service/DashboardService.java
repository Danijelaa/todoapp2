package com.todoapp.service;

import java.util.List;

import com.todoapp.model.Dashboard;

public interface DashboardService {

	Dashboard save(Dashboard dashboard);
	void checkUserAuth(Dashboard dashboard, String username);
	Dashboard findById(Long id);
	List<Dashboard> findByUserUsername(String username);
	void delete(Long id);
	Boolean existsById(Long id);
}
