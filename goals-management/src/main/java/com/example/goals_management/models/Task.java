package com.example.goals_management.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;
@Data
@Entity
@Table(name = "TASKS")
public class Task {
    @Id 
    @Column(name = "task_id")
    private int taskId;

    private String title;
    private String description;
    private String status;
    
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    
    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goals goals;
}
