package com.example.goals_management.dto;

import com.example.goals_management.models.Goals;

import jakarta.validation.constraints.NotNull;

public class AssignGoalDTO {
    @NotNull(message = "Goal ID is required")
    private Long id;
    @NotNull(message = "User ID is required")
    private Long userId;
    public long getId() {
        return id;
   }
   public void setId(long id) {
       this.id = id; 
   }
   public long getUserId() {
       return userId; 
   }
   public void setUserId(long userid) {
       this.userId = userid; 
   }
   public AssignGoalDTO convertToDTO(Goals goal) {
       this.id = goal.getGoalId();
       this.userId = goal.getUserId();
       return this;
   }
}
