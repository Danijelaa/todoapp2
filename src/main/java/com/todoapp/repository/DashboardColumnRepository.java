package com.todoapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todoapp.model.DashboardColumn;
@Repository
public interface DashboardColumnRepository extends JpaRepository<DashboardColumn, Long>{

	Optional<DashboardColumn> findById(Long id);
	List<DashboardColumn> findByDashboardId(Long id);
}
