package com.mygame.services;

import com.mygame.models.Player;
import com.mygame.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    // Méthode pour créer un utilisateur
    public Player createPlayer(String username, String email, String password) {
        // Vérifie si l'utilisateur existe déjà par son username ou email
        Optional<Player> existingPlayerByUsername = playerRepository.findByUsername(username);
        if (existingPlayerByUsername.isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Optional<Player> existingPlayerByEmail = playerRepository.findByEmail(email);
        if (existingPlayerByEmail.isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Si l'utilisateur n'existe pas, on le crée
        Player player = new Player();
        player.setUsername(username);
        player.setEmail(email);
        player.setPassword(password);
        player.setScores(null);
        return playerRepository.save(player); // Sauvegarde l'utilisateur dans la base de données
    }

    // Méthode pour récupérer un utilisateur par son username
    public Optional<Player> findByUsername(String username) {
        return playerRepository.findByUsername(username);
    }

    // Méthode pour récupérer un utilisateur par son email
    public Optional<Player> findByEmail(String email) {
        return playerRepository.findByEmail(email);
    }
}
