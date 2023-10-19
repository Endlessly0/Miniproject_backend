package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.model.User;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@GetMapping("/user")
	public ResponseEntity<Object> getUser() {
     try {		
	     List<User> user = userRepository.findAll(); 	
		 return new ResponseEntity<>(user, HttpStatus.OK);
	} catch (Exception e) {	
		return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	}
	
	@PostMapping("/user")
	public User createReview( @RequestBody User user) {
	    return userRepository.save(user);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<Object> getUserDetail(@PathVariable Integer userId) {

		try {		
			Optional<User> user = userRepository.findById(userId);
			if(user.isPresent()) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Not found", HttpStatus.BAD_REQUEST);
			}
					
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("user/{userId}")
	public ResponseEntity<Object> updateUser(@PathVariable Integer userId, @RequestBody User body) {

		try {
			Optional<User> user = userRepository.findById(userId);

			if (user.isPresent()) {
				User userEdit = user.get();
				userEdit.setMessage(body.getMessage());

				userRepository.save(userEdit);

				return new ResponseEntity<>(userEdit, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Not found", HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	

	
	
}

