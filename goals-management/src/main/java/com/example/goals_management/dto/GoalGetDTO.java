package com.example.goals_management.dto;

import java.time.LocalDate;
import java.util.Optional;

import com.example.goals_management.models.Goals;

import lombok.Data;
@Data
public class GoalGetDTO {

    private Long id;
    private String description;

    private LocalDate estimatedDate;

    private LocalDate deliveryDate;

    private double target;

    private float dailyHours;

    private Boolean status;

    private Long userId;

    public GoalGetDTO entityToDTO(Goals goal) {
        GoalGetDTO goalGetDTO = new GoalGetDTO();
        goalGetDTO.setId(goal.getGoalId());
        goalGetDTO.setDescription(goal.getDescription());
        goalGetDTO.setEstimatedDate(goal.getEstimatedDate());
        goalGetDTO.setDeliveryDate(goal.getDeliveryDate());
        goalGetDTO.setTarget(goal.getTarget());
        goalGetDTO.setDailyHours(goal.getDailyHours());
        goalGetDTO.setStatus(goal.getStatus());
        goalGetDTO.setUserId(goal.getUserId());
       
        return goalGetDTO;
    }

}


