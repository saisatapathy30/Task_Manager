package com.example.Task_Manager.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Task_Manager.model.User;

public interface User1Repository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}