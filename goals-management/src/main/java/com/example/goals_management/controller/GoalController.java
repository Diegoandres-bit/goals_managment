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

import com.example.goals_management.dto.AssignGoalDTO;
import com.example.goals_management.dto.GoalDTO;
import com.example.goals_management.dto.GoalGetDTO;
import com.example.goals_management.dto.GoalPostPutDTO;
import com.example.goals_management.service.GoalsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/goals")
public class GoalController {
    
    @Autowired
    private GoalsService goalsService;



    @PostMapping("/createGoal")
    public ResponseEntity<GoalPostPutDTO> createGoal(@Valid @RequestBody GoalDTO assignGoalDTO) {
        return goalsService.createGoal(assignGoalDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/GetGoalDetails/{id}")
    public ResponseEntity<GoalGetDTO> getGoalDetails(@PathVariable Long id) {
        return goalsService.getGoalDetails(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); 
    }

    @PutMapping ("/AssignGoal")
    public void updateGoal(@Valid @RequestBody AssignGoalDTO assignGoalDTO) {
        goalsService.assignGoalToUser(assignGoalDTO);
    }

}
