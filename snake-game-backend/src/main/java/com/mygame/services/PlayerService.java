package com.mygame.services;

import com.mygame.models.Player;
import com.mygame.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    // Méthode pour créer un utilisateur
    public Player createPlayer(String username, String email, String password) {
        // Vérifie si l'utilisateur existe déjà par son username ou email
        if (playerRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (playerRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Crée un nouvel utilisateur et le sauvegarde dans la base de données
        Player player = new Player();
        player.setUsername(username);
        player.setEmail(email);
        player.setPassword(password);  // Pas de hashage ici pour l'instant
        return playerRepository.save(player);
    }

    // methode pour recuperer tous les players
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    //methode pour recuperer un player avec son email
    public Player getPlayerByEmail(String email) {
        return playerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Player not found"));
    }
}
