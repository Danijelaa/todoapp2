package com.todoapp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TaskDto {

	private Long id;
	@NotNull
	@Size(min = 1, max = 50)
	private String title;
	@Size(min = 1, max = 200)
	private String description;
	@NotNull
	private Long dashboardColumnId;
	
	public TaskDto() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getDashboardColumnId() {
		return dashboardColumnId;
	}

	public void setDashboardColumnId(Long dashboardColumnId) {
		this.dashboardColumnId = dashboardColumnId;
	}
	
}
