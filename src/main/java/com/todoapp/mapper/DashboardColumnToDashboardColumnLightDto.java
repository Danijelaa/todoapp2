package com.todoapp.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.todoapp.dto.DashboardColumnLightDto;
import com.todoapp.model.DashboardColumn;
@Component
public class DashboardColumnToDashboardColumnLightDto implements Converter<DashboardColumn, DashboardColumnLightDto>{

	@Override
	public DashboardColumnLightDto convert(DashboardColumn dashboardColumn) {
		DashboardColumnLightDto dashboardColumnLightDto=new DashboardColumnLightDto();
		dashboardColumnLightDto.setDashboardId(dashboardColumn.getDashboard().getId());
		dashboardColumnLightDto.setId(dashboardColumn.getId());
		dashboardColumnLightDto.setTitle(dashboardColumn.getTitle());
		return dashboardColumnLightDto;
	}
	
	List<DashboardColumnLightDto> convert(List<DashboardColumn> dashboardColumns){
		List<DashboardColumnLightDto> dashboardColumnDtos=new ArrayList<DashboardColumnLightDto>();
		for(DashboardColumn dc:dashboardColumns) {
			dashboardColumnDtos.add(convert(dc));
		}
		return dashboardColumnDtos;
	}

}
