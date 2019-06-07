package com.todoapp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DashboardColumnLightDto {

	private Long id;
	@NotNull
	@Size(min = 1, max = 50)
	private String title;
	private Long dashboardId;
	
	public DashboardColumnLightDto() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
