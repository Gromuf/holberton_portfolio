package com.mygame.services;

import com.mygame.models.Player;
import com.mygame.repositories.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Méthode pour créer un utilisateur
    public Player createPlayer(String username, String email, String password) {
        // Vérifie si l'utilisateur existe déjà par son username ou email
        if (playerRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        if (playerRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        // Crée un nouvel utilisateur et le sauvegarde dans la base de données
        Player player = new Player();
        player.setUsername(username);
        player.setEmail(email);
        player.setPassword(passwordEncoder.encode(password)); // Hash password before saving
        return playerRepository.save(player);
    }

    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword); // Compare raw input with stored hash
    }

    // methode pour recuperer tous les players
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    // methode pour recuperer un player avec son email
    public Player getPlayerByEmail(String email) {
        return playerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Player not found"));
    }

    // methode pour recuperer un player avec son username
    public Player getPlayerByUsername(String username) {
        return playerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Player not found"));
    }
}
