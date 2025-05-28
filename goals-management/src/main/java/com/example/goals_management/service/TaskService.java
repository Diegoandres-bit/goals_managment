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

@Service  // Marks this class as a Spring service component
public class TaskService {

    @Autowired  // Injects GoalsService to validate and retrieve goals
    private GoalsService goalsService;

    @Autowired  // Injects Task repository for DB access
    private TaskRepo taskRepo;

    @Autowired  // Injects mapper to convert between Task entity and DTOs
    private TaskMapper taskMapper;

    /**
     * Creates a new task linked to a goal.
     * @param taskDTO Data Transfer Object containing task data.
     * @return Optional with saved TaskDTO if creation successful; empty if goal not found.
     */
    public Optional<TaskDTO> createTask(TaskDTO taskDTO) {
        // Check if the goal associated with the task exists
        Optional<GoalGetDTO> goalOptional = goalsService.findGoal(taskDTO.getGoalId());
    
        if (goalOptional.isEmpty()) {
            // Return empty if the goal does not exist (cannot create task without goal)
            return Optional.empty(); 
        }
    
        // Convert the DTO to the Task entity
        Task task = taskMapper.taskDTOToTask(taskDTO);
    
        // Save the new task in the database
        Task savedTask = taskRepo.save(task);
    
        // Convert saved Task entity back to DTO
        TaskDTO savedDTO = taskMapper.taskToTaskDTO(savedTask);
    
        return Optional.of(savedDTO);
    }
    
    /**
     * Marks a task as completed by updating its status.
     * @param id the ID of the task to update.
     * @return Optional with TaskGetDTO of updated task if found, empty otherwise.
     */
    public Optional<TaskGetDTO> markTaskAsCompleted(Long id) {
        // Find the task by ID, update its status, save, then map to DTO
        return taskRepo.findById(id)
            .map(task -> {
                task.setStatus(true);  // Mark task as completed
                taskRepo.save(task);   // Save changes
                return taskMapper.taskToTaskGetDTO(task);
            });
    }

    /**
     * Retrieves tasks associated with a specific goal ID.
     * @param id the ID of the goal.
     * @return Optional containing TaskByGoalIdDTO which includes the goal ID and its tasks.
     */
    public Optional<TaskByGoalIdDTO> getTaskByGoalId(Long id) {
        TaskByGoalIdDTO taskByGoalIdDTO = new TaskByGoalIdDTO();
        taskByGoalIdDTO.setId(id);  // Set the goal ID

        // Retrieve list of tasks linked to this goal (without goal info inside tasks)
        List<TaskWithoutGoalDTO> tasks = taskRepo.findByGoalId(id);

        taskByGoalIdDTO.setTasks(tasks);  // Set the task list
        
        return Optional.of(taskByGoalIdDTO);
    }

}
