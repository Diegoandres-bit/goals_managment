package com.example.goals_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.goals_management.models.Goals;
public interface GoalsRepo extends JpaRepository <Goals, Long>{
    
}
