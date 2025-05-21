package com.example.goals_management.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.example.goals_management.controller.GoalController;
import com.example.goals_management.dto.AssignGoalDTO;
import com.example.goals_management.dto.GoalDTO;
import com.example.goals_management.dto.GoalGetDTO;
import com.example.goals_management.dto.GoalPostPutDTO;
import com.example.goals_management.models.Goals;
import com.example.goals_management.service.GoalsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.doNothing;

@WebMvcTest(GoalController.class)
@ActiveProfiles("test")
public class GoalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GoalsService goalService;

    @Autowired
    private ObjectMapper objectMapper;

    private Goals goal;
    private GoalDTO goalDTO;
    private GoalPostPutDTO goalPostPutDTO;
    private GoalGetDTO goalGetDTO;
    private AssignGoalDTO assignGoalDTO;
    private String authorization;

    @BeforeEach
    public void setUp() {
        goal = new Goals();
        goal.setGoalId(1L);
        goal.setDescription("Test Goal");
        goal.setEstimatedDate(LocalDate.now());
        goal.setUserId(1L); 

        
        goalDTO = new GoalDTO();
        goalDTO.setDescription(goal.getDescription());
        goalDTO.setEstimatedDate(goal.getEstimatedDate());
        goalDTO.setUserId(goal.getUserId());
        goalDTO.setTarget(100); // debe ser > 0
goalDTO.setDailyHours(2); 

        goalPostPutDTO = new GoalPostPutDTO();
        goalPostPutDTO.setGoalId(goal.getGoalId());
        goalPostPutDTO.setDescription(goal.getDescription());
        goalPostPutDTO.setEstimatedDate(goal.getEstimatedDate());
        goalPostPutDTO.setUserId(goal.getUserId());

        goalGetDTO = new GoalGetDTO();
        goalGetDTO.setGoalId(goal.getGoalId());
        goalGetDTO.setDescription(goal.getDescription());
        goalGetDTO.setEstimatedDate(goal.getEstimatedDate());
        goalGetDTO.setUserId(goal.getUserId());

        assignGoalDTO = new AssignGoalDTO();
        assignGoalDTO.setGoalId(1L);
        assignGoalDTO.setUserId(1L);

        authorization = "Bearer token";
}

    @Test
    void testSaveGoal_Success() throws Exception {
        when(goalService.createGoal(any(GoalDTO.class))).thenReturn(Optional.of(goalPostPutDTO));

        mockMvc.perform(post("/api/goals/createGoal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(goalDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.goalId").value(goal.getGoalId()))
                .andExpect(jsonPath("$.description").value(goal.getDescription()))
                .andExpect(jsonPath("$.estimatedDate").value(goal.getEstimatedDate().toString()))
                .andExpect(jsonPath("$.userId").value(goal.getUserId()));

        verify(goalService, times(1)).createGoal(any(GoalDTO.class));
    }

 

    @Test
    void getGoalDetails_Success() throws Exception {
        when(goalService.getGoalDetails(goal.getGoalId())).thenReturn(Optional.of(goalGetDTO));
        
        mockMvc.perform(get("/api/goals/GetGoalDetails/{id}", goal.getGoalId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.goalId").value(goal.getGoalId()))
                .andExpect(jsonPath("$.description").value(goal.getDescription()))
                .andExpect(jsonPath("$.estimatedDate").value(goal.getEstimatedDate().toString()))
                .andExpect(jsonPath("$.userId").value(goal.getUserId()));

        verify(goalService, times(1)).getGoalDetails(goal.getGoalId());
    }

    @Test
    void getGoalDetails_NotFound() throws Exception {
        when(goalService.getGoalDetails(goal.getGoalId())).thenReturn(Optional.empty());
        
        mockMvc.perform(get("/api/goals/GetGoalDetails/{id}", goal.getGoalId()))
        .andExpect(status().isNotFound());


        verify(goalService, times(1)).getGoalDetails(goal.getGoalId());
    }

    @Test
    void testAssignGoalToUser_Success() throws Exception {
        doNothing().when(goalService).assignGoalToUser(any(AssignGoalDTO.class), any(String.class));

        mockMvc.perform(put("/api/goals/AssignGoal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(assignGoalDTO)))
                .andExpect(status().isOk());

        verify(goalService, times(1)).assignGoalToUser(any(AssignGoalDTO.class), any(String.class));
    }

   
}
