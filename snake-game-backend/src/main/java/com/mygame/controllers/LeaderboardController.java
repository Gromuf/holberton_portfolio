package com.mygame.controllers;

import com.mygame.models.Leaderboard;
import com.mygame.services.LeaderboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

	private final LeaderboardService leaderboardService;

	public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

	// GET récupérer le leaderboard par période (daily, weekly, monthly, all-time)
    @GetMapping("/{period}")
    public ResponseEntity<List<Leaderboard>> getLeaderboardByPeriod(@PathVariable String period) {
        try {
            List<Leaderboard> leaderboard = leaderboardService.getLeaderboardByPeriod(period);
            return new ResponseEntity<>(leaderboard, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Erreur 400 si période invalide
        }
    }
}
