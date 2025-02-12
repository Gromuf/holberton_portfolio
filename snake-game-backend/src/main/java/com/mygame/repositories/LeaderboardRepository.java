package com.mygame.repositories;

import com.mygame.models.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {

	// trouve les scores sur une periode donnee et les tri du plus grand au plus petit
	List<Leaderboard> findByDateBetweenOrderByScoreValueDesc(LocalDateTime startDate, LocalDateTime endDate);

	// trouve tous les scores trié du plus grand au plus petit
	List<Leaderboard> findAllByOrderByScoreValueDesc();
}
