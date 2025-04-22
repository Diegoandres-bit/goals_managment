package com.example.goals_management.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class TaskDTO {

    @NotNull(message = "Goal ID is required")
    private Long goalId;

    private Long id;

    private Boolean status; 

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;


}