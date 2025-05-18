package com.example.goals_management.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.example.goals_management.dto.*;
import com.example.goals_management.mapper.TaskMapper;
import com.example.goals_management.models.Goals;
import com.example.goals_management.models.Task;
import com.example.goals_management.repository.TaskRepo;
import com.example.goals_management.service.GoalsService;
import com.example.goals_management.service.TaskService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class TaskServiceTest {

    @Mock
    private GoalsService goalsService;

    @Mock
    private TaskRepo taskRepo;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    private TaskDTO taskDTO;
    private Task task;
    private Goals goal;

    @BeforeEach
    void setUp() {
        goal = new Goals();
        goal.setGoalId(1L);

        taskDTO = new TaskDTO();
        taskDTO.setGoalId(1L);
        taskDTO.setStatus(false);
        taskDTO.setTitle("Test Task");
        taskDTO.setDescription("This is a test task");

        task = new Task();
        task.setTaskId(10L);
        task.setGoal(goal);
        task.setTitle("Test Task");
        task.setDescription("This is a test task");
        task.setStatus(false);
    }

    @Test
    void testCreateTask_GoalExists() {
        when(goalsService.findGoal(1L)).thenReturn(Optional.of(new GoalGetDTO()));
        when(taskMapper.taskDTOToTask(taskDTO)).thenReturn(task);
        when(taskRepo.save(task)).thenReturn(task);

        TaskDTO expectedDTO = new TaskDTO();
        expectedDTO.setTaskId(10L);
        expectedDTO.setGoalId(1L);
        expectedDTO.setTitle("Test Task");
        expectedDTO.setDescription("This is a test task");
        expectedDTO.setStatus(false);

        when(taskMapper.taskToTaskDTO(task)).thenReturn(expectedDTO);

        Optional<TaskDTO> result = taskService.createTask(taskDTO);

        assertTrue(result.isPresent());
        assertEquals(expectedDTO.getTaskId(), result.get().getTaskId());
        assertEquals(expectedDTO.getTitle(), result.get().getTitle());
    }

    @Test
    void testCreateTask_GoalDoesNotExist() {
        when(goalsService.findGoal(1L)).thenReturn(Optional.empty());

        Optional<TaskDTO> result = taskService.createTask(taskDTO);

        assertTrue(result.isEmpty());
    }

    @Test
    void testMarkTaskAsCompleted_Success() {
        task.setStatus(false);

        when(taskRepo.findById(10L)).thenReturn(Optional.of(task));
        when(taskRepo.save(task)).thenReturn(task);

        TaskGetDTO taskGetDTO = new TaskGetDTO();
        taskGetDTO.setTaskId(10L);
        taskGetDTO.setStatus(true);
        taskGetDTO.setTitle("Test Task");
        taskGetDTO.setDescription("This is a test task");

        when(taskMapper.taskToTaskGetDTO(task)).thenReturn(taskGetDTO);

        Optional<TaskGetDTO> result = taskService.markTaskAsCompleted(10L);

        assertTrue(result.isPresent());
        assertEquals(10L, result.get().getTaskId());
        assertTrue(result.get().getStatus());
        verify(taskRepo, times(1)).save(task);
        verify(taskRepo, times(1)).findById(10L);
    }

    @Test
    void testMarkTaskAsCompleted_TaskNotFound() {
        when(taskRepo.findById(10L)).thenReturn(Optional.empty());

        Optional<TaskGetDTO> result = taskService.markTaskAsCompleted(10L);

        assertTrue(result.isEmpty());
        verify(taskRepo, never()).save(any());
    }

    @Test
    void testGetTaskByGoalId_Success() {
        Long goalId = 1L;

        TaskWithoutGoalDTO task1 = new TaskWithoutGoalDTO();
        task1.setTask_id(1L);
        task1.setStatus(false);
        task1.setTitle("Task 1");
        task1.setDescription("Description 1");

        TaskWithoutGoalDTO task2 = new TaskWithoutGoalDTO();
        task2.setTask_id(2L);
        task2.setStatus(true);
        task2.setTitle("Task 2");
        task2.setDescription("Description 2");

        List<TaskWithoutGoalDTO> list = Arrays.asList(task1, task2);

        when(taskRepo.findByGoalId(goalId)).thenReturn(list);

        Optional<TaskByGoalIdDTO> result = taskService.getTaskByGoalId(goalId);

        assertTrue(result.isPresent());
        assertEquals(goalId, result.get().getId());
        assertEquals(2, result.get().getTasks().size());
        assertEquals("Task 1", result.get().getTasks().get(0).getTitle());
        assertEquals("Task 2", result.get().getTasks().get(1).getTitle());
        verify(taskRepo, times(1)).findByGoalId(goalId);
    }

    @Test
    void testGetTaskByGoalId_NoTasksFound() {
        Long goalId = 1L;
        when(taskRepo.findByGoalId(goalId)).thenReturn(Arrays.asList());

        Optional<TaskByGoalIdDTO> result = taskService.getTaskByGoalId(goalId);

        assertTrue(result.isPresent());
        assertEquals(goalId, result.get().getId());
        assertTrue(result.get().getTasks().isEmpty());
        verify(taskRepo, times(1)).findByGoalId(goalId);
    }
}
