package com.mygame.controllers;

import com.mygame.models.Player;
import com.mygame.repositories.PlayerRepository;
import com.mygame.utils.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    // LoginController.java
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String password = requestBody.get("password");

        if (email == null || password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ Email et mot de passe requis.");
        }

        Optional<Player> playerOptional = playerRepository.findByEmail(email);
        if (playerOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Identifiants incorrects.");
        }

        Player player = playerOptional.get();

        if (!passwordEncoder.matches(password, player.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Identifiants incorrects.");
        }

        // 🔐 Générer le token
        String token = jwtUtil.generateToken(email);

        // 🍪 Définir le cookie JWT
        ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", token)
                .httpOnly(true)
                .secure(false)  // ⚠️ true en production
                .path("/")
                .maxAge(3600)
                .sameSite("None") // ✅ obligatoire si origine différente
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(Map.of("message", "✅ Connexion réussie.", "redirect", "/scenes/menu.html"));
    }
}
