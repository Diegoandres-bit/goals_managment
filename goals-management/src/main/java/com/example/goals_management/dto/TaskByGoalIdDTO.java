package com.example.goals_management.dto;

import java.util.List;

import lombok.Data;

@Data
public class TaskByGoalIdDTO {
    private Long id;
    private List<TaskWithoutGoalDTO> tasks;  
    
}
