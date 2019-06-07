package com.todoapp.repository;

import com.todoapp.model.Task;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

	Optional<Task> findById(Long id);
	List<Task> findByDashboardColumnId(Long id);
	
}
