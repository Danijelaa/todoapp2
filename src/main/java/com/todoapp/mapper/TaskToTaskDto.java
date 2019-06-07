package com.todoapp.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.todoapp.dto.TaskDto;
import com.todoapp.model.Task;

@Component
public class TaskToTaskDto implements Converter<Task, TaskDto>{

	@Override
	public TaskDto convert(Task task) {
		TaskDto taskDto=new TaskDto();
		taskDto.setDashboardColumnId(task.getDashboardColumn().getId());
		taskDto.setDescription(task.getDescription());
		taskDto.setId(task.getId());
		taskDto.setTitle(task.getTitle());
		return taskDto;
	}
	
	public List<TaskDto> convert(List<Task> tasks) {
		List<TaskDto> taskDtos=new ArrayList<TaskDto>();
		for(Task t:tasks) {
			taskDtos.add(convert(t));
		}
		return taskDtos;
	}

}
