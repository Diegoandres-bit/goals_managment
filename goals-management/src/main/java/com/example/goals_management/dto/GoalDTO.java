package com.example.goals_management.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class GoalDTO {

    @NotBlank(message = "Description is required")
    private String description;

    @FutureOrPresent(message = "Estimated date must be today or in the future")
    private LocalDate estimatedDate;

    private LocalDate deliveryDate;

    @Positive(message = "Target must be greater than 0")
    private double target;

    @PositiveOrZero(message = "Daily hours must be 0 or more")
    private float dailyHours;

    @NotBlank(message = "Status is required")
    private Boolean status;

    private Long userId;

    private LocalDate assignmentDate;

    
  
    
}