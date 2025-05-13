package com.example.goals_management.mapper;

import org.mapstruct.Mapping;

import org.mapstruct.Mapper;

import com.example.goals_management.dto.GoalDTO;
import com.example.goals_management.dto.GoalGetDTO;
import com.example.goals_management.dto.GoalPostPutDTO;
import com.example.goals_management.models.Goals;

@Mapper(componentModel = "spring")
public interface GoalMapper {

      
        GoalDTO toGoalDTO(Goals goals);
        
        Goals toGoal(GoalDTO goalDTO);
        
        GoalGetDTO toGoalGetDTO(Goals goals);
       
        GoalPostPutDTO toGoalPostPutDTO(Goals goals);

}
