package com.example.goals_management.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.goals_management.repository.GoalsRepo;
import com.example.goals_management.service.GoalsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/goals")
public class GoalController {
    
    @Autowired
    private GoalsService goalsService;



    @PostMapping("/createGoal")
    public Optional<GoalPostPutDTO> createGoal(@RequestBody GoalDTO assignGoalDTO) {
        return goalsService.createGoal(assignGoalDTO);

    }

    @GetMapping("/GetGoalDetails/{id}")
    public Optional<GoalGetDTO> getGoalDetails(@PathVariable Long id) {
        return goalsService.getGoalDetails(id); 
    }

    @PutMapping ("/AssignGoal")
    public void updateGoal(@RequestBody AssignGoalDTO assignGoalDTO) {
        goalsService.assignGoalToUser(assignGoalDTO);
    }

}
