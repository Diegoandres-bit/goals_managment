package com.example.goals_management.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.goals_management.dto.TaskDTO;
import com.example.goals_management.dto.TaskGetDTO;
import com.example.goals_management.models.Task;


@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface TaskMapper {

    @Mapping(source = "goal.goalId", target = "goalId")
    TaskDTO taskToTaskDTO(Task task);

    @Mapping(source = "goalId", target = "goal.goalId")
    Task taskDTOToTask(TaskDTO taskDTO);

    TaskGetDTO taskToTaskGetDTO(Task task);

}
