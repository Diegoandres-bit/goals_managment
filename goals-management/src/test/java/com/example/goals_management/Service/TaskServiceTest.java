package com.example.goals_management.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.goals_management.dto.GoalGetDTO;
import com.example.goals_management.dto.TaskByGoalIdDTO;
import com.example.goals_management.dto.TaskDTO;
import com.example.goals_management.dto.TaskGetDTO;
import com.example.goals_management.dto.TaskWithoutGoalDTO;
import com.example.goals_management.mapper.TaskMapper;
import com.example.goals_management.models.Goals;
import com.example.goals_management.models.Task;
import com.example.goals_management.repository.TaskRepo;
import com.example.goals_management.service.GoalsService;
import com.example.goals_management.service.TaskService;

@ExtendWith(MockitoExtension.class)
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
        // Arrange
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

        // Act
        Optional<TaskDTO> result = taskService.createTask(taskDTO);

        // Assert
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
    void testMarkTaskAsCompleted() {
        task.setStatus(false); // initial status

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
    }

   @Test
void testGetTaskByGoalId() {
    Long goalId = 1L;

    TaskWithoutGoalDTO task1 = new TaskWithoutGoalDTO();
    task1.setTask_id(1L);
    task1.setStatus(false);
    task1.setTitle("Tarea 1");
    task1.setDescription("Descripción 1");

    TaskWithoutGoalDTO task2 = new TaskWithoutGoalDTO();
    task2.setTask_id(2L);
    task2.setStatus(true);
    task2.setTitle("Tarea 2");
    task2.setDescription("Descripción 2");

    List<TaskWithoutGoalDTO> list = Arrays.asList(task1, task2);

    when(taskRepo.findByGoalId(goalId)).thenReturn(list);

    Optional<TaskByGoalIdDTO> result = taskService.getTaskByGoalId(goalId);

    assertTrue(result.isPresent());
    assertEquals(goalId, result.get().getId());
    assertEquals(2, result.get().getTasks().size());
    assertEquals("Tarea 1", result.get().getTasks().get(0).getTitle());
    assertEquals("Tarea 2", result.get().getTasks().get(1).getTitle());
}

}