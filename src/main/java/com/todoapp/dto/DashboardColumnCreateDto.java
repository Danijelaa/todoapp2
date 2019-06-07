package com.todoapp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DashboardColumnCreateDto {

	@NotNull
	@Size(min = 1, max = 50)
	private String title;
	@NotNull
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
