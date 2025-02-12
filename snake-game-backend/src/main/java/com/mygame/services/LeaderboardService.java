package com.mygame.services;

import com.mygame.models.Leaderboard;
import com.mygame.repositories.LeaderboardRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Service
public class LeaderboardService {

	private final LeaderboardRepository leaderboardRepository;

	public LeaderboardService(LeaderboardRepository leaderboardRepository) {
		this.leaderboardRepository = leaderboardRepository;
	}

	// Méthode pour obtenir les scores dans une période donnée et triés par score
	public List<Leaderboard> getLeaderboardByPeriod(String period) {
		LocalDate now = LocalDate.now();
		LocalDateTime startDate = null;
		LocalDateTime endDate = null;

		// En fonction de la période, on définit les dates de début et de fin
		if (period.equals("daily")) {
			startDate = now.atStartOfDay();
			endDate = now.atTime(23, 59, 59);
		} else if (period.equals("weekly")) {
			LocalDate startOfWeek = now.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);
			LocalDate endOfWeek = now.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 7);
			startDate = startOfWeek.atStartOfDay();
			endDate = endOfWeek.atTime(23, 59, 59);
		} else if (period.equals("monthly")) {
			LocalDate startOfMonth = now.withDayOfMonth(1);
			LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
			startDate = startOfMonth.atStartOfDay();
			endDate = endOfMonth.atTime(23, 59, 59);
		} else if (period.equals("all-time")) {
			// Si la période est "all-time", on ne filtre par aucune date
			return leaderboardRepository.findAllByOrderByScoreValueDesc();
		} else {
			throw new IllegalArgumentException("Invalid period: " + period);
		}

		// Utilisation de la méthode de repository pour récupérer et trier les scores
		return leaderboardRepository.findByDateBetweenOrderByScoreValueDesc(startDate, endDate);
	}
}
