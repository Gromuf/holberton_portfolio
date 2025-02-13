package com.mygame.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @GetMapping
    public ResponseEntity<?> getMenu() {
        return ResponseEntity.ok(Map.of(
            "message", "🎮 Menu principal",
            "options", Map.of(
                "play", "/game/start",
                "settings", "/settings",
                "leaderboard", "/leaderboard",
                "logout", "/auth/logout"
            )
        ));
    }
}
