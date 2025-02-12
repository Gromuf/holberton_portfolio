package com.mygame.controllers;

import com.mygame.models.Settings;
import com.mygame.services.SettingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settings")
public class SettingsController {

	private final SettingsService settingsService;

	public SettingsController(SettingsService settingsService) {
		this.settingsService = settingsService;
	}

	// GET settings by player ID
	@GetMapping("/{playerId}")
	public ResponseEntity<Settings> getSettings(@PathVariable Long playerId) {
		Settings settings = settingsService.getSettingsByPlayerId(playerId);
		return settings != null ? ResponseEntity.ok(settings) : ResponseEntity.notFound().build();
	}

	// UPDATE settings
	@PutMapping("/{playerId}")
	public ResponseEntity<Settings> updateSettings(
			@PathVariable Long playerId,
			@RequestParam String backgroundTheme,
			@RequestParam String snakeColor) {

		Settings updatedSettings = settingsService.updateSettings(playerId, backgroundTheme, snakeColor);
		return ResponseEntity.ok(updatedSettings);
	}
}
