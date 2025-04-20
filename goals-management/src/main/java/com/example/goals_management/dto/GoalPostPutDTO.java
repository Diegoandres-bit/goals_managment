package com.example.goals_management.dto;

import java.time.LocalDate;

public class GoalPostPutDTO {
    private long id;
    private String description;
    private LocalDate estimatedDate;
    private LocalDate deliveryDate;
    private double target;
    private float dailyHours;
    private String status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
