package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Review;

import com.example.demo.repository.ReviewRepository;

@RestController
@CrossOrigin(origins = "*")
public class ReviewController {
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@GetMapping("/review")
	public ResponseEntity<Object> getReview() {
     try {		
	     List<Review> reviews = reviewRepository.findAll(); 	
		 return new ResponseEntity<>(reviews, HttpStatus.OK);
	} catch (Exception e) {	
		return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	}
	
	@PostMapping("/review")
	public Review createReview( @RequestBody Review reviews) {
	    return reviewRepository.save(reviews);
	}
	
	@GetMapping("/review/{reviewId}")
	public ResponseEntity<Object> getReviewDetail(@PathVariable Integer reviewId) {

		try {		
			Optional<Review> review = reviewRepository.findById(reviewId);
			if(review.isPresent()) {
				return new ResponseEntity<>(review, HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Not found", HttpStatus.BAD_REQUEST);
			}
					
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("review/{reviewId}")
	public ResponseEntity<Object> updateReview(@PathVariable Integer reviewId, @RequestBody Review body) {

		try {
			Optional<Review> review = reviewRepository.findById(reviewId);

			if (review.isPresent()) {
				Review reviewEdit = review.get();
				reviewEdit.setMessage(body.getMessage());

				reviewRepository.save(reviewEdit);

				return new ResponseEntity<>(reviewEdit, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Not found", HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("review/{reviewId}")
	public ResponseEntity<Object> deleteReview(@PathVariable Integer reviewId) {
        
		try {
			Optional<Review> review = reviewRepository.findById(reviewId);

			if (review.isPresent()) {
				reviewRepository.delete(review.get());

				return  new ResponseEntity<>("DELETE SUCSESS", HttpStatus.OK );
			} else {
				return new ResponseEntity<>("Not found", HttpStatus.BAD_REQUEST);
			}			
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	
}

