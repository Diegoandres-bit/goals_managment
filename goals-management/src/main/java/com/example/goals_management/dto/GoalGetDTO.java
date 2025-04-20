package com.example.goals_management.dto;

import java.time.LocalDate;

import com.example.goals_management.models.Goals;

public class GoalGetDTO {
    private long id;
    private String description;
    private LocalDate estimatedDate;
    private LocalDate deliveryDate;
    private double target;
    private float dailyHours;
    private boolean status;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEstimatedDate() {
        return estimatedDate;
    }

    public void setEstimatedDate(LocalDate estimatedDate) {
        this.estimatedDate = estimatedDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public float getDailyHours() {
        return dailyHours;
    }

    public void setDailyHours(float dailyHours) {
        this.dailyHours = dailyHours;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public long getId() {
        return id;
   
    }
    public void setId(long id) {
        this.id = id; 
    }
    public void convertToDTO(Goals goal) {
        this.id = goal.getGoalId();
        this.description = goal.getDescription();
        this.estimatedDate = goal.getEstimatedDate();
        this.deliveryDate = goal.getDeliveryDate();
        this.target = goal.getTarget();
        this.status = goal.getStatus();
    }

}
