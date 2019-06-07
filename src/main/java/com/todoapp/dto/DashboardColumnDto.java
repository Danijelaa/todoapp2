package com.todoapp.dto;

import java.util.ArrayList;
import java.util.List;

public class DashboardColumnDto extends DashboardColumnLightDto{

	private List<TaskDto> tasks=new ArrayList<TaskDto>();

	public DashboardColumnDto() {
		super();
	}

	public List<TaskDto> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskDto> tasks) {
		this.tasks = tasks;
	}
	
}
