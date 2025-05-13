package com.example.goals_management.dto;

import com.example.goals_management.models.Goals;
import lombok.Data;
@Data
public class TaskGetDTO {
    private Goals goal;
    private Long taskId;
    private Boolean status; 
    private String title;
    private String description;

}
