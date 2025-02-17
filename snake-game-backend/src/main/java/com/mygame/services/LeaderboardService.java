package com.mygame.services;

import com.mygame.models.Leaderboard;
import com.mygame.models.Score;
import com.mygame.repositories.ScoreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

	private final ScoreRepository scoreRepository;

	public LeaderboardService(ScoreRepository scoreRepository) {
		this.scoreRepository = scoreRepository;
	}

	public List<Leaderboard> getLeaderboardByPeriod(String period) {
		LocalDate now = LocalDate.now();
		final LocalDateTime startDate;
		final LocalDateTime endDate;

		switch (period) {
			case "daily" -> {
				startDate = now.atStartOfDay();
				endDate = now.atTime(23, 59, 59);
			}
			case "weekly" -> {
				LocalDate startOfWeek = now.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);
				LocalDate endOfWeek = now.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 7);
				startDate = startOfWeek.atStartOfDay();
				endDate = endOfWeek.atTime(23, 59, 59);
			}
			case "monthly" -> {
				LocalDate startOfMonth = now.withDayOfMonth(1);
				LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
				startDate = startOfMonth.atStartOfDay();
				endDate = endOfMonth.atTime(23, 59, 59);
			}
			default -> {
				startDate = null;
				endDate = null;
			}
		}

		List<Score> scores = ("all-time".equals(period))
				? scoreRepository.findAll()
				: scoreRepository.findAll().stream()
						.filter(score -> score.getDateTime().isAfter(startDate)
								&& score.getDateTime().isBefore(endDate))
						.collect(Collectors.toList());

		scores.sort((s1, s2) -> Integer.compare(s2.getValue(), s1.getValue()));

		int[] rank = { 1 };
		return scores.stream().map(score -> {
			Leaderboard lb = new Leaderboard();
			lb.setPlayer(score.getPlayer());
			lb.setScore(score);
			lb.setRank(rank[0]++);
			lb.setPeriod(period);
			lb.setDate(score.getDateTime());
			return lb;
		}).collect(Collectors.toList());
	}
}
