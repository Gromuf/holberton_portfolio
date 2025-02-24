package com.mygame.controllers;

import com.mygame.models.Review;
import com.mygame.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

	private final ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	// Endpoint pour créer une review
	@PostMapping
	public ResponseEntity<Review> createReview(
			@RequestParam Long playerId,
			@RequestParam String text,
			@RequestParam int score) { // Récupération du score
		Review review = reviewService.createReview(playerId, text, score);
		return new ResponseEntity<>(review, HttpStatus.CREATED);
	}

	// Récupérer toutes les reviews triées par ordre décroissant (les plus récentes
	// en premier)
	@GetMapping("/with-players")
	public ResponseEntity<List<Map<String, Object>>> getAllReviewsWithPlayers() {
		List<Map<String, Object>> reviews = reviewService.getAllReviewsWithPlayerNames();
		return new ResponseEntity<>(reviews, HttpStatus.OK);
	}

	// Récupérer les reviews d'un joueur spécifique
	@GetMapping("/player/{playerId}")
	public ResponseEntity<List<Review>> getReviewsByPlayer(@PathVariable Long playerId) {
		List<Review> reviews = reviewService.getReviewsByPlayer(playerId);
		return new ResponseEntity<>(reviews, HttpStatus.OK);
	}
}
