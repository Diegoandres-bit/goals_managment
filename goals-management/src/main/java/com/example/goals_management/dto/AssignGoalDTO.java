package com.example.goals_management.dto;



import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class AssignGoalDTO {
    @NotNull(message = "Goal ID is required")
    private Long goalId;

    @NotNull(message = "User ID is required")
    private Long userId;

}
