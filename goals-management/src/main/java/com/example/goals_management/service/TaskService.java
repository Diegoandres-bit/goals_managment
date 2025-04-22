package com.example.goals_management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.goals_management.dto.TaskByGoalIdDTO;
import com.example.goals_management.dto.TaskDTO;
import com.example.goals_management.dto.TaskGetDTO;
import com.example.goals_management.dto.TaskWithoutGoalDTO;
import com.example.goals_management.models.Goals;
import com.example.goals_management.models.Task;
import com.example.goals_management.repository.GoalsRepo;
import com.example.goals_management.repository.TaskRepo;
@Service
public class TaskService {

    @Autowired
    private GoalsService goalsService;

    @Autowired
    private TaskRepo taskRepo;

    public Optional<TaskDTO> createTask(TaskDTO taskDTO) {
        Optional<Goals> goalOptional = goalsService.finGoal(taskDTO.getGoalId());
    
        if (goalOptional.isEmpty()) {
            return Optional.empty(); // o lanzar una excepción si preferís
        }
    
        Task task = new Task();
        task.setGoal(goalOptional.get());
        task.setStatus(taskDTO.getStatus());
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
    
        Task savedTask = taskRepo.save(task);
    
        TaskDTO savedDTO = new TaskDTO();
        savedDTO.setId(savedTask.getTaskId());
        savedDTO.setTitle(savedTask.getTitle());
        savedDTO.setDescription(savedTask.getDescription());
        savedDTO.setStatus(savedTask.getStatus());
        savedDTO.setGoalId(savedTask.getGoal().getGoalId());
    
        return Optional.of(savedDTO);
    }
    
    public Optional<TaskGetDTO> markTaskAsCompleted(Long id){

        Task task=taskRepo.findById(id).get();
        task.setStatus(true);
        taskRepo.save(task);   
        TaskGetDTO taskGetDTO=new TaskGetDTO();
        taskGetDTO.setId(task.getTaskId());
        taskGetDTO.setDescription(task.getDescription());
        taskGetDTO.setGoalId(task.getGoal().getGoalId());
        taskGetDTO.setStatus(task.getStatus());
        taskGetDTO.setTitle(task.getTitle());
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