package com.example.goals_management.dto;

import lombok.Data;

@Data
public class TaskWithoutGoalDTO {


    private Long task_id;
    private Boolean status; 
    private String title;
    private String description;
    public TaskWithoutGoalDTO(Long task_id, Boolean status, String title, String description) {
        this.task_id = task_id;
        this.status = status;
        this.title = title;
        this.description = description;
    }
    
}
