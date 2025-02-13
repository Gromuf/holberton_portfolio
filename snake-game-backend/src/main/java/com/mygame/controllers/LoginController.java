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

    // ✅ Accepte JSON via @RequestBody
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String password = requestBody.get("password");

        if (email == null || password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ Email et mot de passe requis.");
        }

        // 🔍 Rechercher l'utilisateur par email
        Optional<Player> playerOptional = playerRepository.findByEmail(email);
        if (playerOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Utilisateur introuvable.");
        }

        Player player = playerOptional.get();

        // 🔑 Vérifier le mot de passe
        if (!passwordEncoder.matches(password, player.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Mot de passe incorrect.");
        }

        // 🛠️ Générer le token JWT
        String token = jwtUtil.generateToken(email);

        // 🍪 Créer un cookie sécurisé
        ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", token)
                .httpOnly(true)
                .secure(false) // Passe à true en production
                .path("/")
                .maxAge(3600)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(Map.of(
					"message", "✅ Connexion réussie.",
					"redirect", "/scenes/menu.html"
					));
    }
}
