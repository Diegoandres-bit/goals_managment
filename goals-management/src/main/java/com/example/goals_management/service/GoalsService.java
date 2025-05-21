package com.example.goals_management.service;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.goals_management.dto.AssignGoalDTO;
import com.example.goals_management.dto.GoalDTO;
import com.example.goals_management.dto.GoalGetDTO;
import com.example.goals_management.dto.GoalPostPutDTO;
import com.example.goals_management.mapper.GoalMapper;
import com.example.goals_management.models.Goals;
import com.example.goals_management.repository.GoalsRepo;
import com.example.goals_management.client.ApiClient;

@Service
public class GoalsService {
    
    @Autowired
    private GoalsRepo goalsRepo;

    @Autowired
    private GoalMapper goalMapper;

    @Autowired
    private ApiClient apiClient;


    public Optional<GoalGetDTO> getGoalDetails(Long goalId) {
        Optional<Goals> goal = goalsRepo.findById(goalId);
        if (goal.isPresent()) {
            GoalGetDTO goalGetDTO = goalMapper.toGoalGetDTO(goal.get());
            return Optional.of(goalGetDTO);
        
        } else {
            return Optional.empty();
        }
  
    }

    public void assignGoalToUser(AssignGoalDTO assignGoalDTO, String authorization) {
        Long goalId = assignGoalDTO.getGoalId();
        Long userId = assignGoalDTO.getUserId();
        Optional<Goals> goal = goalsRepo.findById(goalId);

        //validate if user id exist
        Boolean employeeExists = apiClient.employeeExists(userId, authorization);
        if (employeeExists == null || !employeeExists) {
            throw new IllegalArgumentException("Employee does not exist or request failed");
        }

        if (goal.isPresent()) {

            goal.get().setAssignmentDate(LocalDate.now());
            goal.get().setUserId(userId);
            
            goalsRepo.save(goal.get());
            
        }else{
            throw new RuntimeException("Goal not found");
        }   
    }
    
    public Optional<GoalPostPutDTO> createGoal(GoalDTO goalDTO) {
        
            Goals goal =goalMapper.toGoal(goalDTO);
            Goals savedGoal = goalsRepo.save(goal);
            savedGoal.setStatus(false);
            GoalPostPutDTO savedGoalDTO = goalMapper.toGoalPostPutDTO(savedGoal);

            return Optional.of(savedGoalDTO);
    }
    
    public Optional<GoalGetDTO> findGoal(Long id) {
        Goals goal = goalsRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Goal not found"));
        return Optional.of(goalMapper.toGoalGetDTO(goal));
    }

}
