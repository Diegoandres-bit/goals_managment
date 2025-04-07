package com.example.goals_management.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;

/**
 * Entity class representing tasks associated with goals.
 * This class manages task-related information and maintains a many-to-one relationship with Goals.
 * Uses JPA annotations for database mapping and Lombok for boilerplate code generation.
 */
@Data
@Entity
@Table(name = "TASKS")
public class Task {
    /** Unique identifier for the task */
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private long taskId;

    /** Title of the task */
    private String title;

    /** Detailed description of the task */
    private String description;

    /** Current status of the task */
    private String status;
    
    /** Timestamp when the task was created */
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    /** Timestamp of the last update to the task */
    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;
    
    /** Reference to the parent goal. Represents many-to-one relationship with Goals */
    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goals goals;
}