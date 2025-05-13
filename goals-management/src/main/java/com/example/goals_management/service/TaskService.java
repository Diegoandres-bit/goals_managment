package com.example.goals_management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.goals_management.dto.GoalGetDTO;
import com.example.goals_management.dto.TaskByGoalIdDTO;
import com.example.goals_management.dto.TaskDTO;
import com.example.goals_management.dto.TaskGetDTO;
import com.example.goals_management.dto.TaskWithoutGoalDTO;
import com.example.goals_management.mapper.TaskMapper;
import com.example.goals_management.models.Task;
import com.example.goals_management.repository.TaskRepo;
@Service
public class TaskService {

    @Autowired
    private GoalsService goalsService;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private TaskMapper taskMapper;

    public Optional<TaskDTO> createTask(TaskDTO taskDTO) {
        Optional<GoalGetDTO> goalOptional = goalsService.findGoal(taskDTO.getGoalId());
    
        if (goalOptional.isEmpty()) {
            return Optional.empty(); 
        }
    
        Task task =taskMapper.taskDTOToTask(taskDTO);
    
    
        Task savedTask = taskRepo.save(task);
    
        TaskDTO savedDTO = taskMapper.taskToTaskDTO(savedTask);
    
        return Optional.of(savedDTO);
    }
    
    public Optional<TaskGetDTO> markTaskAsCompleted(Long id){

        Task task=taskRepo.findById(id).get();
        task.setStatus(true);
        taskRepo.save(task);   
        TaskGetDTO taskGetDTO=taskMapper.taskToTaskGetDTO(task);
        return Optional.of(taskGetDTO);
    }

    public Optional<TaskByGoalIdDTO> getTaskByGoalId(Long id){
        TaskByGoalIdDTO taskByGoalIdDTO=new TaskByGoalIdDTO();
        taskByGoalIdDTO.setId(id);
        List<TaskWithoutGoalDTO> tasks=taskRepo.findByGoalId(id);
        taskByGoalIdDTO.setTasks(tasks);
      return Optional.of(taskByGoalIdDTO);

    }

}