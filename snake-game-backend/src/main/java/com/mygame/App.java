package com.mygame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        // Étape 1 : Créer la base de données si elle n'existe pas
        createDatabaseIfNotExists();

        // Étape 2 : Lancer Spring Boot
        SpringApplication.run(App.class, args);
    }

    private static void createDatabaseIfNotExists() {
        String url = "jdbc:postgresql://localhost:5432/postgres"; // Connexion au PostgreSQL principal
        String username = "postgres";
        String password = "iluecuis";
        String dbName = "snake_db";

        try (Connection conn = DriverManager.getConnection(url, username, password);
                Statement stmt = conn.createStatement()) {

            // Vérifier si la base de données existe
            String checkDbQuery = "SELECT 1 FROM pg_database WHERE datname='" + dbName + "'";
            var resultSet = stmt.executeQuery(checkDbQuery);

            if (!resultSet.next()) {
                // Si la base n'existe pas, on la crée
                stmt.executeUpdate("CREATE DATABASE " + dbName);
                System.out.println("✅ Base de données '" + dbName + "' créée !");
            } else {
                System.out.println("🔹 La base de données '" + dbName + "' existe déjà.");
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la connexion à PostgreSQL : " + e.getMessage());
        }
    }
}
