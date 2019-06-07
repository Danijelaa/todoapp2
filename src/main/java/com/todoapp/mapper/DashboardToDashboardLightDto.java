package com.todoapp.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.todoapp.dto.DashboardLightDto;
import com.todoapp.model.Dashboard;

@Component
public class DashboardToDashboardLightDto implements Converter<Dashboard, DashboardLightDto>{

	@Override
	public DashboardLightDto convert(Dashboard dashboard) {
		DashboardLightDto dashboardLightDto=new DashboardLightDto();
		dashboardLightDto.setId(dashboard.getId());
		dashboardLightDto.setTitle(dashboard.getTitle());
		return dashboardLightDto;
	}
	
	List<DashboardLightDto> convert(List<Dashboard> dashboards){
		List<DashboardLightDto> dashboardLightDtos=new ArrayList<DashboardLightDto>();
		for(Dashboard d:dashboards) {
			dashboardLightDtos.add(convert(d));
		}
		return dashboardLightDtos;
	}

}
