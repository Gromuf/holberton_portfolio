package com.mygame.repositories;

import com.mygame.models.Score;
import com.mygame.models.Player;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
	// Trouver un score par l'id du player
    List<Score> findByPlayerId(Long playerId);

    // Trouver un score par player username
    List<Score> findByPlayerUsername(String username);

	//trouver tout les scores
	List<Score> findAll();
}