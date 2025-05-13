package com.example.goals_management.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        return taskService.createTask(taskDTO).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }
    @GetMapping("/getTasksByGoal/{id}")
    public ResponseEntity<TaskByGoalIdDTO> getTaskByGoal(@PathVariable Long id) {
        return taskService.getTaskByGoalId(id).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

@PutMapping("/markAsCompleted/{id}")
public ResponseEntity<TaskGetDTO> markTaskAsCompleted(@Valid @PathVariable Long id) {
    return taskService.markTaskAsCompleted(id).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
}
}