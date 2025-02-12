package com.mygame.services;

import com.mygame.models.Score;
import com.mygame.models.Player;
import com.mygame.repositories.ScoreRepository;
import com.mygame.repositories.PlayerRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScoreService {

	private final ScoreRepository scoreRepository;
	private final PlayerRepository playerRepository;

	public ScoreService(ScoreRepository scoreRepository, PlayerRepository playerRepository) {
		this.scoreRepository = scoreRepository;
		this.playerRepository = playerRepository;
	}

	// methode pour creer un score
	public Score createScore(Long playerId, int value) {
		// trouve le joueur par son id
		Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));

		// creation du score
		Score score = new Score();
		score.setValue(value);
		score.setPlayer(player);
		score.setDateTime(LocalDateTime.now());
		return scoreRepository.save(score);
	}

	// Récupérer tous les scores
	public List<Score> getAllScores() {
		return scoreRepository.findAll();
	}

	// Récupérer les scores par playerId
	public List<Score> getScoresByPlayer(Long playerId) {
		return scoreRepository.findByPlayerId(playerId);
	}

	// Récupérer les scores par username du joueur
	public List<Score> getScoresByUsername(String username) {
		return scoreRepository.findByPlayerUsername(username);
	}
}