package com.example.goals_management.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.goals_management.dto.AssignGoalDTO;
import com.example.goals_management.dto.GoalDTO;
import com.example.goals_management.dto.GoalGetDTO;
import com.example.goals_management.dto.GoalPostPutDTO;
import com.example.goals_management.models.Goals;
import com.example.goals_management.repository.GoalsRepo;


@Service

public class GoalsService {
    
    @Autowired
    private GoalsRepo goalsRepo;

    public Optional<GoalGetDTO> getGoalDetails(Long goalId) {

        Optional<Goals> goal = goalsRepo.findById(goalId);
        if (goal.isPresent()) {
            GoalGetDTO goalGetDTO = new GoalGetDTO();
            goalGetDTO=goalGetDTO.entityToDTO(goal.get());
            return Optional.of(goalGetDTO);
        
        } else {
            return Optional.empty();
        }
  
    }

    public void assignGoalToUser(AssignGoalDTO assignGoalDTO) {
        Long goalId = assignGoalDTO.getId();
        Long userId = assignGoalDTO.getUserId();
        Optional<Goals> goal = goalsRepo.findById(goalId);
        //validate if userid exist

        if (goal.isPresent()) {
            Goals goals = goal.get();
            goals.setUserId(userId);
            goalsRepo.save(goals);
        }else{
            throw new RuntimeException("Goal not found");
        }   
    }
    
    public Optional<GoalPostPutDTO> createGoal(GoalDTO goalDTO) {
            Goals goal =new Goals();
            goal.setDescription(goalDTO.getDescription());
            goal.setStatus(goalDTO.getStatus());
            goal.setTarget(goalDTO.getTarget());
            goal.setEstimatedDate(goalDTO.getEstimatedDate());
            goal.setDeliveryDate(goalDTO.getDeliveryDate());
            goal.setUserId(goalDTO.getUserId());
            goal.setDailyHours(goalDTO.getDailyHours());
            goal.setAssignmentDate(goalDTO.getAssignmentDate());
            Goals savedGoal = goalsRepo.save(goal);
            
            GoalPostPutDTO savedGoalDTO = new GoalPostPutDTO();
            savedGoalDTO.setDescription(savedGoal.getDescription());
            savedGoalDTO.setStatus(savedGoal.getStatus());
            savedGoalDTO.setTarget(savedGoal.getTarget());
            savedGoalDTO.setEstimatedDate(savedGoal.getEstimatedDate());
            savedGoalDTO.setDeliveryDate(savedGoal.getDeliveryDate());
            savedGoalDTO.setId(savedGoal.getGoalId());

            return Optional.of(savedGoalDTO);
    }

    public Optional<Goals> finGoal(Long id){
        return Optional.of(goalsRepo.findById(id).get());
    }

}
