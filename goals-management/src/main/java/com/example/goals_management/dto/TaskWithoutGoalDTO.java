package com.example.goals_management.dto;

import lombok.Data;

@Data
public class TaskWithoutGoalDTO {
    private Long task_id;
    private Boolean status; 
    private String title;
    private String description;
    
}
