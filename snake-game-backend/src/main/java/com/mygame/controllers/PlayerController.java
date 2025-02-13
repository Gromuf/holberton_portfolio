package com.mygame.controllers;

import com.mygame.models.Player;
import com.mygame.services.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<Object> createPlayer(@RequestBody Map<String, String> requestBody) {
        try {
            String username = requestBody.get("username");
            String email = requestBody.get("email");
            String password = requestBody.get("password");

            Player player = playerService.createPlayer(username, email, password);
            return new ResponseEntity<>(player, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();
            if ("Username already exists".equals(errorMessage) || "Email already exists".equals(errorMessage)) {
                return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

	//recuperer les players
	@GetMapping
	public ResponseEntity<List<Player>> getAllPlayers() {
		List<Player> players = playerService.getAllPlayers();
		return new ResponseEntity<>(players, HttpStatus.OK);
	}

	//recuperer un player avec son email
	@GetMapping("/email/{email}")
	public ResponseEntity<Player> getPlayerByEmail(@PathVariable String email) {
		try {
			Player player = playerService.getPlayerByEmail(email);
			return new ResponseEntity<>(player, HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); //retourne 404 si not found
		}
	}
}
