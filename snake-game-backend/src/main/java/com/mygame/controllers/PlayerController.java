package com.mygame.controllers;

import com.mygame.models.Player;
import com.mygame.services.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<Object> createPlayer(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        try {
            // Appel à la méthode pour créer le joueur
            Player player = playerService.createPlayer(username, email, password);
            return new ResponseEntity<>(player, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Gestion d'erreur spécifique en fonction du message
            String errorMessage = e.getMessage(); // Récupère le message d'erreur de la RuntimeException
            if (errorMessage.equals("Username already exists")) {
                return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);  // Code 409 pour un conflit
            } else if (errorMessage.equals("Email already exists")) {
                return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);  // Code 409 pour un conflit
            } else {
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);  // Code 400 pour les erreurs générales
            }
        }
    }
}
