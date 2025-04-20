package com.example.goals_management.dto;

import jakarta.validation.constraints.*;

public class TaskDTO {

    @NotNull(message = "Goal ID is required")
    private Long goalId;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    // Getters y setters

    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}