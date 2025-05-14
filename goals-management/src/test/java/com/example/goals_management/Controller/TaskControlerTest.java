package com.example.goals_management.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.goals_management.controller.TaskController;
import com.example.goals_management.dto.TaskByGoalIdDTO;
import com.example.goals_management.dto.TaskDTO;
import com.example.goals_management.dto.TaskGetDTO;
import com.example.goals_management.service.TaskService;

class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createTask_ShouldReturnOk_WhenTaskIsCreated() {
        TaskDTO inputDto = new TaskDTO();
        inputDto.setGoalId(1L);
        inputDto.setTitle("Title");
        inputDto.setDescription("Description");
        inputDto.setStatus(true);

        when(taskService.createTask(inputDto)).thenReturn(Optional.of(inputDto));

        ResponseEntity<TaskDTO> response = taskController.createTask(inputDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(inputDto, response.getBody());
    }

    @Test
    void createTask_ShouldReturnBadRequest_WhenServiceReturnsEmpty() {
        TaskDTO inputDto = new TaskDTO();
        inputDto.setGoalId(1L);
        inputDto.setTitle("Title");
        inputDto.setDescription("Description");
        inputDto.setStatus(true);

        when(taskService.createTask(inputDto)).thenReturn(Optional.empty());

        ResponseEntity<TaskDTO> response = taskController.createTask(inputDto);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }


    @Test
    void getTaskByGoal_ShouldReturnOk_WhenTasksFound() {
        Long goalId = 1L;
        TaskByGoalIdDTO dto = new TaskByGoalIdDTO();
        dto.setId(goalId);
        dto.setTasks(new ArrayList<>());

        when(taskService.getTaskByGoalId(goalId)).thenReturn(Optional.of(dto));

        ResponseEntity<TaskByGoalIdDTO> response = taskController.getTaskByGoal(goalId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getTaskByGoal_ShouldReturnBadRequest_WhenTasksNotFound() {
        Long goalId = 1L;

        when(taskService.getTaskByGoalId(goalId)).thenReturn(Optional.empty());

        ResponseEntity<TaskByGoalIdDTO> response = taskController.getTaskByGoal(goalId);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }


    @Test
    void markTaskAsCompleted_ShouldReturnOk_WhenSuccess() {
        Long taskId = 1L;
        TaskGetDTO dto = new TaskGetDTO();
        dto.setTaskId(taskId);

        when(taskService.markTaskAsCompleted(taskId)).thenReturn(Optional.of(dto));

        ResponseEntity<TaskGetDTO> response = taskController.markTaskAsCompleted(taskId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    void markTaskAsCompleted_ShouldReturnBadRequest_WhenNotFound() {
        Long taskId = 1L;

        when(taskService.markTaskAsCompleted(taskId)).thenReturn(Optional.empty());

        ResponseEntity<TaskGetDTO> response = taskController.markTaskAsCompleted(taskId);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}
