package com.example.goals_management.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.goals_management.dto.TaskByGoalIdDTO;
import com.example.goals_management.dto.TaskDTO;
import com.example.goals_management.dto.TaskGetDTO;
import com.example.goals_management.service.TaskService;
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public Optional<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        return taskService.createTask(taskDTO);
    }
    @GetMapping("/getTasksByGoal/{id}")
    public Optional<TaskByGoalIdDTO> getTaskByGoal(@PathVariable Long id) {
        return taskService.getTaskByGoalId(id);
    }

@PutMapping("/markAsCompleted/{id}")
public Optional<TaskGetDTO> markTaskAsCompleted(@PathVariable Long id) {
    return taskService.markTaskAsCompleted(id);
}
}