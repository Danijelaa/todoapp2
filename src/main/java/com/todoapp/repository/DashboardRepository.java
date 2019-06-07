package com.todoapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todoapp.model.Dashboard;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, Long>{

	Optional<Dashboard> findById(Long id);
	List<Dashboard> findByUserUsername(String username);
}
