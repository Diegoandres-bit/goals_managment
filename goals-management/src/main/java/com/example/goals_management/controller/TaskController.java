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

@RestController  // Defines this class as a REST controller
@RequestMapping("/api/tasks")  // Base URL for all task-related endpoints
public class TaskController {
    
    @Autowired  // Injects the TaskService to handle business logic
    private TaskService taskService;

    /**
     * Endpoint to create a new task.
     * Receives a TaskDTO in the request body, validates it, and attempts to create a task.
     * @param taskDTO the task data from the client
     * @return HTTP 200 with created TaskDTO if successful, or 400 Bad Request if failure
     */
    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        return taskService.createTask(taskDTO)
            .map(ResponseEntity::ok)                   // Return 200 OK with created task if successful
            .orElse(ResponseEntity.badRequest().build());  // Return 400 Bad Request if creation failed
    }

    /**
     * Endpoint to retrieve all tasks associated with a given goal ID.
     * @param id the goal ID path variable
     * @return HTTP 200 with TaskByGoalIdDTO if found, or 400 Bad Request if none found
     */
    @GetMapping("/getTasksByGoal/{id}")
    public ResponseEntity<TaskByGoalIdDTO> getTaskByGoal(@PathVariable Long id) {
        return taskService.getTaskByGoalId(id)
            .map(ResponseEntity::ok)                  // Return 200 OK with tasks if found
            .orElse(ResponseEntity.badRequest().build()); // Return 400 if none found
    }

    /**
     * Endpoint to mark a task as completed by its ID.
     * @param id the task ID path variable
     * @return HTTP 200 with updated TaskGetDTO if task exists, or 400 Bad Request if not
     */
    @PutMapping("/markAsCompleted/{id}")
    public ResponseEntity<TaskGetDTO> markTaskAsCompleted(@Valid @PathVariable Long id) {
        return taskService.markTaskAsCompleted(id)
            .map(ResponseEntity::ok)                    // Return 200 OK with updated task if successful
            .orElse(ResponseEntity.badRequest().build());  // Return 400 Bad Request if task not found
    }
}
