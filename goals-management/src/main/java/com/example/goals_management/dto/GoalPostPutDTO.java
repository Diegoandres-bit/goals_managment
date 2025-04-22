package com.example.goals_management.dto;

import java.time.LocalDate;

import com.example.goals_management.models.Goals;

import lombok.Data;
@Data
public class GoalPostPutDTO {
    
    private long id;
    private String description;
    private LocalDate estimatedDate;
    private LocalDate deliveryDate;
    private double target;
    private float dailyHours;
    private Boolean status;
    private long userId;


    public Goals toEntity(GoalDTO dto) {
        Goals goal = new Goals();
        goal.setDescription(this.getDescription());
        goal.setEstimatedDate(this.getEstimatedDate());
        goal.setDeliveryDate(this.getDeliveryDate());
        goal.setTarget(this.getTarget());
        goal.setStatus(this.getStatus()); 
        goal.setUserId(this.getUserId());
        return goal;
    }

}
