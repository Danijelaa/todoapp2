package com.todoapp.dto;

import java.util.ArrayList;
import java.util.List;

public class DashboardDto extends DashboardLightDto{

	private List<DashboardColumnDto> dashboardColumns=new ArrayList<DashboardColumnDto>();

	public DashboardDto() {
		super();
	}

	public List<DashboardColumnDto> getDashboardColumns() {
		return dashboardColumns;
	}

	public void setDashboardColumns(List<DashboardColumnDto> dashboardColumns) {
		this.dashboardColumns = dashboardColumns;
	}
	
	
}
