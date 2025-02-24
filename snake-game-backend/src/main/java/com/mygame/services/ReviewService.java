package com.mygame.services;

import com.mygame.models.Review;
import com.mygame.models.Player;
import com.mygame.repositories.ReviewRepository;
import com.mygame.repositories.PlayerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final PlayerRepository playerRepository;

	public ReviewService(ReviewRepository reviewRepository, PlayerRepository playerRepository) {
		this.reviewRepository = reviewRepository;
		this.playerRepository = playerRepository;
	}

	public Review createReview(Long playerId, String text, int score) {
		Player player = playerRepository.findById(playerId)
				.orElseThrow(() -> new RuntimeException("Player not found"));

		Review review = new Review();
		review.setPlayer(player);
		review.setText(text);
		review.setScore(score);

		return reviewRepository.save(review);
	}

	public List<Review> getReviewsByPlayer(Long playerId) {
		return reviewRepository.findByPlayerId(playerId);
	}

	public List<Map<String, Object>> getAllReviewsWithPlayerNames() {
		List<Review> reviews = reviewRepository.findAllByOrderByIdDesc(); // Les plus récents en premier
		return reviews.stream().map(review -> {
			Map<String, Object> reviewData = new HashMap<>();
			reviewData.put("playerName", review.getPlayer().getUsername());
			reviewData.put("text", review.getText());
			reviewData.put("score", review.getScore());
			return reviewData;
		}).collect(Collectors.toList());
	}
}
