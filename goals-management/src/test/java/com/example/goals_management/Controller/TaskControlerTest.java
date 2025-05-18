package com.example.goals_management.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.goals_management.controller.TaskController;
import com.example.goals_management.dto.TaskByGoalIdDTO;
import com.example.goals_management.dto.TaskDTO;
import com.example.goals_management.dto.TaskGetDTO;
import com.example.goals_management.dto.TaskWithoutGoalDTO;
import com.example.goals_management.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTask_ShouldReturnOk_WhenTaskIsCreated() throws Exception {
        TaskDTO inputDto = new TaskDTO();
        inputDto.setGoalId(1L);
        inputDto.setTitle("Title");
        inputDto.setDescription("Description");
        inputDto.setStatus(true);

        when(taskService.createTask(any(TaskDTO.class))).thenReturn(Optional.of(inputDto));

        mockMvc.perform(post("/api/tasks/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goalId").value(inputDto.getGoalId()))
                .andExpect(jsonPath("$.title").value(inputDto.getTitle()))
                .andExpect(jsonPath("$.description").value(inputDto.getDescription()))
                .andExpect(jsonPath("$.status").value(inputDto.getStatus()));

        verify(taskService, times(1)).createTask(any(TaskDTO.class));
    }

    @Test
    void getTaskByGoal_ShouldReturnOk_WhenTasksFound() throws Exception {
        Long goalId = 1L;
        TaskByGoalIdDTO dto = new TaskByGoalIdDTO();
        dto.setId(goalId);
        
        TaskWithoutGoalDTO task1 = new TaskWithoutGoalDTO();
        task1.setTask_id(1L);
        task1.setTitle("Task 1");
        task1.setDescription("Description 1");
        task1.setStatus(false);

        TaskWithoutGoalDTO task2 = new TaskWithoutGoalDTO();
        task2.setTask_id(2L);
        task2.setTitle("Task 2");
        task2.setDescription("Description 2");
        task2.setStatus(true);

        dto.setTasks(Arrays.asList(task1, task2));

        when(taskService.getTaskByGoalId(goalId)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/api/tasks/getTasksByGoal/" + goalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(goalId))
                .andExpect(jsonPath("$.tasks[0].task_id").value(1))
                .andExpect(jsonPath("$.tasks[0].title").value("Task 1"))
                .andExpect(jsonPath("$.tasks[1].task_id").value(2))
                .andExpect(jsonPath("$.tasks[1].title").value("Task 2"));

        verify(taskService, times(1)).getTaskByGoalId(goalId);
    }

    @Test
    void getTaskByGoal_ShouldReturnBadRequest_WhenTasksNotFound() throws Exception {
        Long goalId = 1L;

        when(taskService.getTaskByGoalId(goalId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/tasks/getTasksByGoal/" + goalId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void markTaskAsCompleted_ShouldReturnOk_WhenSuccess() throws Exception {
        Long taskId = 1L;
        TaskGetDTO dto = new TaskGetDTO();
        dto.setTaskId(taskId);
        dto.setTitle("Test Task");
        dto.setDescription("Test Description");
        dto.setStatus(true);

        when(taskService.markTaskAsCompleted(taskId)).thenReturn(Optional.of(dto));

        mockMvc.perform(put("/api/tasks/markAsCompleted/" + taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value(taskId))
                .andExpect(jsonPath("$.title").value(dto.getTitle()))
                .andExpect(jsonPath("$.description").value(dto.getDescription()))
                .andExpect(jsonPath("$.status").value(dto.getStatus()));

        verify(taskService, times(1)).markTaskAsCompleted(taskId);
    }

    @Test
    void markTaskAsCompleted_ShouldReturnBadRequest_WhenNotFound() throws Exception {
        Long taskId = 1L;

        when(taskService.markTaskAsCompleted(taskId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/tasks/markAsCompleted/" + taskId))
                .andExpect(status().isBadRequest());
    }
}
