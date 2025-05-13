package com.example.goals_management.dto;

import java.time.LocalDate;




import lombok.Data;
@Data
public class GoalGetDTO {

    private Long goalId;

    private String description;

    private LocalDate estimatedDate;

    private LocalDate deliveryDate;

    private double target;

    private float dailyHours;

    private Boolean status;

    private Long userId;

   

}


