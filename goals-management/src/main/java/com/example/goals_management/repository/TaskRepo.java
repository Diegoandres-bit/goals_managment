package com.example.goals_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.goals_management.dto.TaskWithoutGoalDTO;
import com.example.goals_management.models.Task;

public interface TaskRepo extends JpaRepository<Task, Long> {

    @Query(value = "SELECT t.task_id, t.status, t.description, t.title FROM tasks t WHERE t.goal_id = :goalId", nativeQuery = true)
    List<TaskWithoutGoalDTO> findByGoalId(@Param("goalId") Long goalId);
}
