package com.mygame.controllers;

import com.mygame.models.Player;
import com.mygame.repositories.PlayerRepository;
import com.mygame.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {

	private final PlayerRepository playerRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public LoginController(PlayerRepository playerRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.playerRepository = playerRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping
	public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
		Optional<Player> playerOptional = playerRepository.findByUsername(username);

		if (playerOptional.isEmpty()) {
			System.out.println("❌ User not found: " + username);
			return ResponseEntity.status(401).body("Invalid username or password");
		}

		Player player = playerOptional.get();
		System.out.println("✅ User found: " + username);
		System.out.println("🔑 Stored hashed password: " + player.getPassword());
		System.out.println("🔑 Raw password provided: " + password);

		if (!passwordEncoder.matches(password, player.getPassword())) {
			System.out.println("❌ Password mismatch for: " + username);
			return ResponseEntity.status(401).body("Invalid username or password");
		}

		System.out.println("✅ Password matched! Generating token...");
		String token = jwtUtil.generateToken(username);
		return ResponseEntity.ok(token);
	}

}
