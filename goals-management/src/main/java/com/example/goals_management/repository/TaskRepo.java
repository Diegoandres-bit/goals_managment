package com.example.goals_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.goals_management.models.Task;
public interface TaskRepo  extends JpaRepository<Task, Long>{
    
}
