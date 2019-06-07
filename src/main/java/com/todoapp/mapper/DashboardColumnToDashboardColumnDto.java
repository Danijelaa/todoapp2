package com.todoapp.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.todoapp.dto.DashboardColumnDto;
import com.todoapp.dto.TaskDto;
import com.todoapp.model.DashboardColumn;
import com.todoapp.model.Task;
import com.todoapp.service.TaskService;

@Component
public class DashboardColumnToDashboardColumnDto implements Converter<DashboardColumn, DashboardColumnDto>{

	@Autowired
	TaskToTaskDto toTaskDto;
	@Autowired
	TaskService taskService;
	
	@Override
	public DashboardColumnDto convert(DashboardColumn dashboardColumn) {
		DashboardColumnDto dashboardColumnDto=new DashboardColumnDto();
		dashboardColumnDto.setDashboardId(dashboardColumn.getDashboard().getId());
		dashboardColumnDto.setId(dashboardColumn.getId());
		dashboardColumnDto.setTitle(dashboardColumn.getTitle());
		List<Task> tasks=taskService.findByDashboardColumnId(dashboardColumn.getId());
		List<TaskDto> taskDtos=toTaskDto.convert(tasks);
		dashboardColumnDto.setTasks(taskDtos);
		return dashboardColumnDto;
	}

	public List<DashboardColumnDto> convert(List<DashboardColumn> dashboardColumns){
		List<DashboardColumnDto> dashboardColumnDtos=new ArrayList<DashboardColumnDto>();
		for(DashboardColumn dc:dashboardColumns) {
			dashboardColumnDtos.add(convert(dc));
		}
		return dashboardColumnDtos;
	}
	
}
