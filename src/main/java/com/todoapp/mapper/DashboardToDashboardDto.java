package com.todoapp.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.todoapp.dto.DashboardColumnDto;
import com.todoapp.dto.DashboardDto;
import com.todoapp.model.Dashboard;
import com.todoapp.model.DashboardColumn;
import com.todoapp.service.DashboardColumnService;

@Component
public class DashboardToDashboardDto implements Converter<Dashboard, DashboardDto>{

	@Autowired
	DashboardColumnService dashboardColumnService;
	@Autowired
	DashboardColumnToDashboardColumnDto toDashboardColumnDto;
	@Override
	public DashboardDto convert(Dashboard dashboard) {
		DashboardDto dashboardDto=new DashboardDto();
		dashboardDto.setId(dashboard.getId());
		dashboardDto.setTitle(dashboard.getTitle());
		List<DashboardColumn> dashboardColumns=dashboardColumnService.findByDashboardId(dashboard.getId());
		List<DashboardColumnDto> dashboardColumnDtos=toDashboardColumnDto.convert(dashboardColumns);
		dashboardDto.setDashboardColumns(dashboardColumnDtos);
		return dashboardDto;
	}

	public List<DashboardDto> convert(List<Dashboard> dashboards) {
		List<DashboardDto> dashboardDtos=new ArrayList<DashboardDto>();
		for(Dashboard d:dashboards) {
			dashboardDtos.add(convert(d));
		}
		return dashboardDtos;
	}
	
}
