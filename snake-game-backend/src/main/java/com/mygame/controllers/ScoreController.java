package com.mygame.controllers;

import com.mygame.models.Score;
import com.mygame.services.ScoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scores")
public class ScoreController {

	private final ScoreService scoreService;

	public ScoreController(ScoreService scoreService) {
		this.scoreService = scoreService;
	}

	// Post score
	@PostMapping
	public ResponseEntity<Object> createScore(@RequestParam Long playerId, @RequestParam int value) {
		try {
			Score score = scoreService.createScore(playerId, value);
			return new ResponseEntity<>(score, HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// GET recuperer tous les scores
	@GetMapping
	public ResponseEntity<List<Score>> getAllScores() {
		List<Score> scores = scoreService.getAllScores();
		return new ResponseEntity<>(scores, HttpStatus.OK);
	}

	// GET recuperer les scores d'un player par id
	@GetMapping("player/{playerId}")
	public ResponseEntity<List<Score>> getScoreByPlayer(@PathVariable Long playerId) {
		List<Score> scores = scoreService.getScoresByPlayer(playerId);
		return new ResponseEntity<>(scores, HttpStatus.OK);
	}

	// Get recuperer les scores d'un player par son username
	@GetMapping("username/{username}")
	public ResponseEntity<List<Score>> getScoreByUsername(@PathVariable String username) {
		List<Score> scores = scoreService.getScoresByUsername(username);
		return new ResponseEntity<>(scores, HttpStatus.OK);
	}

	// // DTO (data transfer object) interne pour éviter d'envoyer directement
	// l'entité
	// // Score en `POST`
	// public static class ScoreRequest {
	// private Long playerId;
	// private int value;

	// public Long getPlayerId() {
	// return playerId;
	// }

	// public void setPlayerId(Long playerId) {
	// this.playerId = playerId;
	// }

	// public int getValue() {
	// return value;
	// }

	// public void setValue(int value) {
	// this.value = value;
	// }
	// }
}