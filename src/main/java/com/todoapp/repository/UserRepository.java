package com.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todoapp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
