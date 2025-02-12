package com.mygame.controllers;

import com.mygame.models.Player;
import com.mygame.services.PlayerService;
import com.mygame.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final PlayerService playerService;
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public AuthController(PlayerService playerService) {
		this.playerService = playerService;
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestParam String username, @RequestParam String password) {
		try {
			Player player = playerService.getPlayerByUsername(username);

			if (!passwordEncoder.matches(password, player.getPassword())) {
				return ResponseEntity.status(401).body("Invalid username or password");
			}

			String token = JwtUtil.generateToken(username);
			return ResponseEntity.ok(Map.of("token", token));

		} catch (RuntimeException e) {
			return ResponseEntity.status(401).body(e.getMessage());
		}
	}
}
