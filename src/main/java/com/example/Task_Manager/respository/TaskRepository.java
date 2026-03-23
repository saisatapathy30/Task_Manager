package com.example.Task_Manager.respository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Task_Manager.model.Task;

public interface TaskRepository extends JpaRepository<Task,Long>{
	List<Task> findByUsername(String username);
}
