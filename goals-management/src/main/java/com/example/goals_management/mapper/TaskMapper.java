package com.example.goals_management.mapper;

import org.mapstruct.Mapper;

import com.example.goals_management.dto.TaskDTO;
import com.example.goals_management.dto.TaskGetDTO;
import com.example.goals_management.models.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDTO taskToTaskDTO(Task task);
    
    Task taskDTOToTask(TaskDTO taskDTO);

    TaskGetDTO taskToTaskGetDTO(Task task);

}
