package com.mygame.services;

import com.mygame.models.User;
import com.mygame.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Méthode pour créer un utilisateur
    public User createUser(String username, String email, String password) {
        // Vérifie si l'utilisateur existe déjà par son username ou email
        Optional<User> existingUserByUsername = userRepository.findByUsername(username);
        if (existingUserByUsername.isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Optional<User> existingUserByEmail = userRepository.findByEmail(email);
        if (existingUserByEmail.isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Si l'utilisateur n'existe pas, on le crée
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setScores(null);
        return userRepository.save(user); // Sauvegarde l'utilisateur dans la base de données
    }

    // Méthode pour récupérer un utilisateur par son username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Méthode pour récupérer un utilisateur par son email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
