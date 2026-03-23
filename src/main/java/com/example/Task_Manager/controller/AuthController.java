package com.example.Task_Manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private TokenUtil tokenutil;
	
	@Autowired
	private TaskRepository repo;
	@Autowired
	private User1Repository userRepo;
	
	@GetMapping("/admin")
	public Object getAllTasks(@RequestHeader("Authorization")String token) {
		String username=tokenutil.validateToken(token);
		if(username==null) {
			return "Unauthorized";
		}
		User user=userRepo.findByUsername(username);
		if(!user.getRole().equals("ADMIN")) {
			return "Access Denied";
		}
		return repo.findAll();
	}
	@PostMapping("/register")
    public String register(@RequestBody User user){
        System.out.println("Registered");
        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getRole());
        userRepo.save(user);
        return "Registered";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        User dbUser = userRepo.findByUsername(user.getUsername());
        if(dbUser != null && dbUser.getPassword().equals(user.getPassword())){
            return tokenutil.generateToken(user.getUsername());
        }
        return "Invalid";
    }
    @DeleteMapping("/{username}")
    public String deleteUser(@PathVariable String username) {
    	User user=userRepo.findByUsername(username);
    	if(user == null) {
    		return "User not found";
    	}
    	repo.deleteAll(repo.findByUsername(username));
    	userRepo.delete(user);
		return "User and note deleted";
    }
}
