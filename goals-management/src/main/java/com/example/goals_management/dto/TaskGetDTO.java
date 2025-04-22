package com.example.goals_management.dto;

import com.example.goals_management.models.Task;

public class TaskGetDTO {
    private Long goalId;
    private Long id;
    private Boolean status; 
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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;  
    }
    public TaskGetDTO convertToDTO(Task task){
        this.setDescription(task.getDescription());
        this.setGoalId(task.getGoal().getGoalId());
        this.setId(task.getTaskId());
        this.setStatus(task.getStatus());
        this.setTitle(task.getTitle());
        return this;
    }
}
