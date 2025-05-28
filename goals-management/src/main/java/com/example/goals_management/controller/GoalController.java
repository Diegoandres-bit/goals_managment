package com.example.goals_management.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.goals_management.dto.AssignGoalDTO;
import com.example.goals_management.dto.GoalDTO;
import com.example.goals_management.dto.GoalGetDTO;
import com.example.goals_management.dto.GoalPostPutDTO;
import com.example.goals_management.service.GoalsService;

import jakarta.validation.Valid;

@RestController  // Marks this class as a REST controller
@RequestMapping("/api/goals")  // Base URL path for goal-related endpoints
public class GoalController {
    
    @Autowired  // Injects the GoalsService to handle business logic
    private GoalsService goalsService;

    /**
     * Endpoint to create a new goal.
     * @param assignGoalDTO The goal data passed in the request body, validated.
     * @return HTTP 200 with created GoalPostPutDTO if successful, or 404 Not Found if failed.
     */
    @PostMapping("/createGoal")
    public ResponseEntity<GoalPostPutDTO> createGoal(@Valid @RequestBody GoalDTO assignGoalDTO) {
        return goalsService.createGoal(assignGoalDTO)
            .map(ResponseEntity::ok)                  // Return 200 OK with created goal
            .orElse(ResponseEntity.notFound().build()); // Return 404 if creation failed
    }

    /**
     * Endpoint to get goal details by ID.
     * @param id the goal ID path variable.
     * @return HTTP 200 with GoalGetDTO if found, or 404 Not Found if no goal exists.
     */
    @GetMapping("/GetGoalDetails/{id}")
    public ResponseEntity<GoalGetDTO> getGoalDetails(@PathVariable Long id) {
        return goalsService.getGoalDetails(id)
            .map(ResponseEntity::ok)                   // Return 200 OK with goal details
            .orElse(ResponseEntity.notFound().build()); // Return 404 if goal not found
    }

    /**
     * Endpoint to assign a goal to a user.
     * @param assignGoalDTO the DTO containing goalId and userId, validated.
     * @param authorizationHeader the Authorization header used for user validation.
     * No response body; this method performs the update or throws an exception if failed.
     */
    @PutMapping("/AssignGoal")
    public void updateGoal(@Valid @RequestBody AssignGoalDTO assignGoalDTO, 
                           @RequestHeader("Authorization") String authorizationHeader) {
        goalsService.assignGoalToUser(assignGoalDTO, authorizationHeader);
    }

}
