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


@Service
public class GoalsService {
    
    @Autowired
    private GoalsRepo goalsRepo;

    @Autowired
    private GoalMapper goalMapper;

    public Optional<GoalGetDTO> getGoalDetails(Long goalId) {
        Optional<Goals> goal = goalsRepo.findById(goalId);
        if (goal.isPresent()) {
            GoalGetDTO goalGetDTO = goalMapper.toGoalGetDTO(goal.get());
            return Optional.of(goalGetDTO);
        
        } else {
            return Optional.empty();
        }
  
    }

    public void assignGoalToUser(AssignGoalDTO assignGoalDTO) {
        Long goalId = assignGoalDTO.getGoalId();
        Long userId = assignGoalDTO.getUserId();
        Optional<Goals> goal = goalsRepo.findById(goalId);

        //validate if user id exist

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
    
    public Optional<GoalGetDTO> findGoal(Long id){
        return Optional.of(goalMapper.toGoalGetDTO(goalsRepo.findById(id).get()));
    }

}
