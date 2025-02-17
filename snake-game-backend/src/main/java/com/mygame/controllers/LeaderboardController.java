package com.mygame.controllers;

import com.mygame.models.Leaderboard;
import com.mygame.services.LeaderboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @GetMapping("/{period}")
    public ResponseEntity<List<LeaderboardDTO>> getLeaderboardByPeriod(@PathVariable String period) {
        try {
            List<Leaderboard> leaderboard = leaderboardService.getLeaderboardByPeriod(period);

            List<LeaderboardDTO> leaderboardDTOs = leaderboard.stream()
                    .map(lb -> new LeaderboardDTO(
                            lb.getPlayer().getUsername(),
                            lb.getScore().getValue(),
                            lb.getRank(),
                            lb.getPeriod()))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(leaderboardDTOs, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
