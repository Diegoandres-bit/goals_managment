package com.example.goals_management.service;

import java.lang.StackWalker.Option;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.goals_management.models.Goals;
import com.example.goals_management.repository.GoalsRepo;

public class GoalsService {
    
    @Autowired
    private GoalsRepo goalsRepo;

    public Optional<Goals> getGoalsById(Long goalId) {
        return goalsRepo.findById(goalId);
    }
    
    public void createGoal(){

    }
}
