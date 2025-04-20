package com.example.goals_management.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;

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
    private String status;

    // Getters y setters

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