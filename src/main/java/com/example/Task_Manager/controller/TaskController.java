package com.example.Task_Manager.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Task_Manager.TokenUtil;
import com.example.Task_Manager.model.Task;
import com.example.Task_Manager.model.User;
import com.example.Task_Manager.respository.TaskRepository;
import com.example.Task_Manager.respository.User1Repository;


@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired
	private TaskRepository repo;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	@PostMapping
	public Object createNote(
			@RequestBody Task note,
			@RequestHeader("Authorization")String token) {
		String username=tokenUtil.validateToken(token);
		if(username==null) {
			return "Unauthorized";
		}
		
		note.setUsername(username);
		return repo.save(note);
	}
	
	@GetMapping
	public Object getNotes(@RequestHeader("Authorization")String token) {
		String username=tokenUtil.validateToken(token);
		if(username==null) {
			return "Unauthorized";
		}
		return repo.findByUsername(username);
	}
	@PutMapping("/{id}")
	public Object updateTask(
			@PathVariable Long id,
			@RequestBody Task updateTask,
			@RequestHeader("Authorization")String token) {  
			String username =tokenUtil.validateToken(token);
			if(username==null) {
				return "Unauthorized";
			}
			Optional <Task>optionalTask=repo.findById(id);
			if(optionalTask.isEmpty()) {
				return "Task not found";
			}
			Task existingTask=optionalTask.get();
			if(!existingTask.getUsername().equals(username)) {
				return "You cant update this task";
			}
			existingTask.setTitle(updateTask.getTitle());
			existingTask.setDescription(updateTask.getDescription());
			return repo.save(existingTask);
			
	}
	
/*	@PostMapping
	public Object createNode(@RequestBody Task task,@RequestHeader("Authorization") String token) {
		String username=tokenutil.validateToken(token);
		System.out.println("Received token: " + token);
	    System.out.println("Validated username: " + username);

		if(username==null) {
			return "Unauthorized";
		}
		task.setUsername(username);
		
		System.out.println("Received token: " + token);
		return repo.save(task);
	
		
	}
	
	@GetMapping
	public Object getNotes(@RequestHeader("Authorization")String token) {
		String username=tokenutil.validateToken(token);
		if(username==null) {
			return "Unauthorized";
		}
		return repo.findByUsername(username);
	}
	*/
}