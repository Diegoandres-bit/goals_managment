package com.example.goals_management.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.goals_management.dto.AssignGoalDTO;
import com.example.goals_management.dto.GoalDTO;
import com.example.goals_management.dto.GoalGetDTO;
import com.example.goals_management.dto.GoalPostPutDTO;
import com.example.goals_management.mapper.GoalMapper;
import com.example.goals_management.models.Goals;
import com.example.goals_management.repository.GoalsRepo;
import com.example.goals_management.service.GoalsService;

@ExtendWith(MockitoExtension.class)
public class GoalServiceTest {

    @Mock
    private GoalsRepo goalRepository;

    @Mock
    private GoalMapper goalMapper;

    @InjectMocks
    private GoalsService goalService;

    private Goals goal;
    private GoalDTO goalDTO;
    private GoalPostPutDTO goalPostPutDTO;
    private GoalGetDTO goalGetDTO;
    private String authorization;

    @BeforeEach
    public void setup() {
        goal = new Goals();
        goal.setGoalId(1L);
        goal.setDescription("Test Goal");
        goal.setEstimatedDate(LocalDate.now());
        goal.setUserId(1L);

        goalDTO = new GoalDTO();
        goalDTO.setDescription(goal.getDescription());
        goalDTO.setEstimatedDate(goal.getEstimatedDate());
        goalDTO.setUserId(goal.getUserId());

        goalPostPutDTO = new GoalPostPutDTO();
        goalPostPutDTO.setGoalId(goal.getGoalId());
        goalPostPutDTO.setDescription(goal.getDescription());
        goalPostPutDTO.setEstimatedDate(goal.getEstimatedDate());
        goalPostPutDTO.setUserId(goal.getUserId());

        goalGetDTO = new GoalGetDTO();
        goalGetDTO.setGoalId(goal.getGoalId());
        goalGetDTO.setDescription(goal.getDescription());
        goalGetDTO.setEstimatedDate(goal.getEstimatedDate());
        goalGetDTO.setUserId(goal.getUserId());

        authorization = "Bearer token";
    }

    @Test
    public void testSaveGoal() {
        when(goalMapper.toGoal(goalDTO)).thenReturn(goal);
        when(goalRepository.save(goal)).thenReturn(goal);
        when(goalMapper.toGoalPostPutDTO(goal)).thenReturn(goalPostPutDTO);

        Optional<GoalPostPutDTO> result = goalService.createGoal(goalDTO);

        assertTrue(result.isPresent());
        assertEquals(goal.getGoalId(), result.get().getGoalId());
        assertEquals(goal.getDescription(), result.get().getDescription());
    }

    @Test
    public void testGetGoalDetails() {
        when(goalRepository.findById(goal.getGoalId())).thenReturn(Optional.of(goal));
        when(goalMapper.toGoalGetDTO(goal)).thenReturn(goalGetDTO);

        Optional<GoalGetDTO> result = goalService.getGoalDetails(goal.getGoalId());

        assertTrue(result.isPresent());
        assertEquals(goal.getGoalId(), result.get().getGoalId());
        assertEquals(goal.getDescription(), result.get().getDescription());
    }

    @Test
    public void testAssignGoalToUser() {
        AssignGoalDTO assignGoalDTO = new AssignGoalDTO();
        assignGoalDTO.setGoalId(goal.getGoalId());
        assignGoalDTO.setUserId(goal.getUserId());

        when(goalRepository.findById(goal.getGoalId())).thenReturn(Optional.of(goal));

        goalService.assignGoalToUser(assignGoalDTO, authorization);

        assertEquals(goal.getUserId(), assignGoalDTO.getUserId());
    }

    @Test
    public void testGetGoalDetailsNotFound() {
        Optional<GoalGetDTO> result = goalService.getGoalDetails(2L);
        assertFalse(result.isPresent());
    }

    @Test
    public void testAssignGoalToUserGoalNotFound() {
        AssignGoalDTO assignGoalDTO = new AssignGoalDTO();
        assignGoalDTO.setGoalId(goal.getGoalId());
        assignGoalDTO.setUserId(goal.getUserId());

        when(goalRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> goalService.assignGoalToUser(assignGoalDTO, authorization));
    }

    @Test
    public void testFindGoal() {
        GoalGetDTO dto = new GoalGetDTO();
        dto.setGoalId(1L);
        dto.setDescription("Test Goal");

        when(goalRepository.findById(1L)).thenReturn(Optional.of(goal));
        when(goalMapper.toGoalGetDTO(goal)).thenReturn(dto);

        Optional<GoalGetDTO> result = goalService.findGoal(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getGoalId());
        assertEquals("Test Goal", result.get().getDescription());
    }
}
