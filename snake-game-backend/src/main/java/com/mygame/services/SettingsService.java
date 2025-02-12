package com.mygame.services;

import com.mygame.models.Player;
import com.mygame.models.Settings;
import com.mygame.repositories.PlayerRepository;
import com.mygame.repositories.SettingsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettingsService {

	private final SettingsRepository settingsRepository;
	private final PlayerRepository playerRepository;

	public SettingsService(SettingsRepository settingsRepository, PlayerRepository playerRepository) {
		this.settingsRepository = settingsRepository;
		this.playerRepository = playerRepository;
	}

	public Settings getSettingsByPlayerId(Long playerId) {
		return settingsRepository.findByPlayerId(playerId);
	}

	public Settings updateSettings(Long playerId, String backgroundTheme, String snakeColor) {
		Optional<Player> playerOptional = playerRepository.findById(playerId);

		if (playerOptional.isEmpty()) {
			throw new RuntimeException("Player not found");
		}

		Player player = playerOptional.get();
		Settings settings = settingsRepository.findByPlayerId(playerId);

		if (settings == null) {
			settings = new Settings();
			settings.setPlayer(player);
		}

		settings.setBackgroundTheme(backgroundTheme);
		settings.setSnakeColor(snakeColor);

		return settingsRepository.save(settings);
	}
}
