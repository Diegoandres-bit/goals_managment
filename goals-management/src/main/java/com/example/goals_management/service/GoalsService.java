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

@Service  // Marks this class as a Spring service component
public class GoalsService {

    @Autowired  // Injects the Goals repository for database access
    private GoalsRepo goalsRepo;

    @Autowired  // Injects the mapper to convert between entities and DTOs
    private GoalMapper goalMapper;

    @Autowired  // Injects the API client to validate user existence
    private ApiClient apiClient;


    /**
     * Retrieves goal details by its ID.
     * @param goalId the ID of the goal to find.
     * @return Optional containing GoalGetDTO if found, or empty if not.
     */
    public Optional<GoalGetDTO> getGoalDetails(Long goalId) {
        Optional<Goals> goal = goalsRepo.findById(goalId); // Fetch goal from DB
        if (goal.isPresent()) {
            // Convert entity to DTO to return
            GoalGetDTO goalGetDTO = goalMapper.toGoalGetDTO(goal.get());
            return Optional.of(goalGetDTO);
        } else {
            // Return empty if goal not found
            return Optional.empty();
        }
    }

    /**
     * Assigns a goal to a user after validating that the user exists.
     * @param assignGoalDTO DTO containing goalId and userId to assign.
     * @param authorization Authorization token or credentials for the API call.
     */
    public void assignGoalToUser(AssignGoalDTO assignGoalDTO, String authorization) {
        Long goalId = assignGoalDTO.getGoalId();
        Long userId = assignGoalDTO.getUserId();

        Optional<Goals> goal = goalsRepo.findById(goalId); // Fetch goal

        // Validate if user exists via external API call
        Boolean employeeExists = apiClient.employeeExists(userId, authorization);
        if (employeeExists == null || !employeeExists) {
            // Throw exception if user doesn't exist or request failed
            throw new IllegalArgumentException("Employee does not exist or request failed");
        }

        if (goal.isPresent()) {
            // Assign the goal to the user and set assignment date
            goal.get().setAssignmentDate(LocalDate.now());
            goal.get().setUserId(userId);
            goalsRepo.save(goal.get()); // Save changes to DB
        } else {
            // Throw exception if goal not found
            throw new RuntimeException("Goal not found");
        }
    }

    /**
     * Creates a new goal from a DTO.
     * @param goalDTO DTO containing goal data to create.
     * @return Optional containing the created goal's DTO.
     */
    public Optional<GoalPostPutDTO> createGoal(GoalDTO goalDTO) {
        // Convert DTO to Goals entity
        Goals goal = goalMapper.toGoal(goalDTO);
        Goals savedGoal = goalsRepo.save(goal); // Save to DB

        // Initialize the goal status to false (inactive)
        savedGoal.setStatus(false);

        // Convert saved entity to DTO for returning
        GoalPostPutDTO savedGoalDTO = goalMapper.toGoalPostPutDTO(savedGoal);
        return Optional.of(savedGoalDTO);
    }

    /**
     * Finds a goal by ID and returns it as a DTO.
     * @param id the ID of the goal to find.
     * @return Optional containing GoalGetDTO if found.
     */
    public Optional<GoalGetDTO> findGoal(Long id) {
        // Fetch goal or throw exception if not found
        Goals goal = goalsRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Goal not found"));

        // Convert entity to DTO to return
        return Optional.of(goalMapper.toGoalGetDTO(goal));
    }

}
