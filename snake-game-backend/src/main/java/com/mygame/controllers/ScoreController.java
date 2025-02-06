package com.mygame.controllers;

import com.mygame.models.Score;
import com.mygame.services.ScoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scores")
public class ScoreController {

	private final ScoreService scoreService;

	public ScoreController(ScoreService scoreService) {
		this.scoreService = scoreService;
	}

	//Post score
	@PostMapping
	public ResponseEntity<Object> createScore(@RequestParam Long playerId, @RequestParam int value) {
		try {
			Score score = scoreService.createScore(playerId, value);
			return new ResponseEntity<>(score, HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}