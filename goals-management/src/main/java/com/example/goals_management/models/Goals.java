package com.example.goals_management.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

/**
 * Entity class representing goals in the system.
 * This class manages goal-related information including descriptions, dates, targets, and status.
 * It uses JPA annotations for database mapping and Lombok for boilerplate code generation.
 */
@Data
@Entity
public class Goals {
    /** Unique identifier for the goal */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Long goalId;

    /** Detailed description of the goal */
    private String description;

    
    @Column(name = "user_id")
    private Long userId;

    /** Estimated date for goal completion */
    @Column(name = "estimated_date")
    private LocalDate estimatedDate;

    /** Date when the goal was assigned */
    @Column(name = "assignment_date")
    private LocalDate assignmentDate;

    /** Actual date when the goal was delivered/completed */
    @Column(name = "delivery_date")
    private LocalDate deliveryDate;
    
    /** Numerical target or metric for the goal */
    private double target;
    
    /** Current status of the goal (true for completed, false for pending) */
    @Column(name = "status", columnDefinition = "TINYINT(1)")
    private Boolean status;

    /** Daily hours allocated for the goal */
    @Column(name = "daily_hours")
    private float dailyHours;

    /** Timestamp when the goal was created */
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    /** Timestamp of the last update to the goal */
    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;
    
}