package com.mygame.controllers;

import com.mygame.models.Player;
import com.mygame.repositories.PlayerRepository;
import com.mygame.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginController(PlayerRepository playerRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String password = requestBody.get("password");

        Optional<Player> playerOptional = playerRepository.findByEmail(email);
        if (playerOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Identifiants incorrects.");
        }

        Player player = playerOptional.get();

        if (!passwordEncoder.matches(password, player.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Identifiants incorrects.");
        }

        String token = jwtUtil.generateToken(email);

        // Inclure le playerId dans la réponse
        Map<String, Object> response = new HashMap<>();
        response.put("message", "✅ Connexion réussie.");
        response.put("token", token);
        response.put("playerId", player.getId());
        response.put("redirect", "/scenes/menu.html");

        return ResponseEntity.ok(response);
    }

}
