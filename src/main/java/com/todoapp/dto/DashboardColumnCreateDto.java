package com.todoapp.dto;

public class DashboardColumnCreateDto {

	private String title;
	private Long dashboardId;
	
	public DashboardColumnCreateDto() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(Long dashboardId) {
		this.dashboardId = dashboardId;
	}
	
}
