package com.mygame;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.configure().directory("./").load();

        // Set environment variables manually for Spring Boot
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        // Step 1: Create the database if it doesn't exist
        createDatabaseIfNotExists(dotenv);

        // Step 2: Launch Spring Boot application
        SpringApplication.run(App.class, args);
    }

    private static void createDatabaseIfNotExists(Dotenv dotenv) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = dotenv.get("DB_USER", "postgres");
        String password = dotenv.get("DB_PASS", "");
        String dbName = dotenv.get("DB_NAME", "snake_db");

        try (Connection conn = DriverManager.getConnection(url, username, password);
                Statement stmt = conn.createStatement()) {

            // Check if the database exists
            String checkDbQuery = "SELECT 1 FROM pg_database WHERE datname='" + dbName + "'";
            var resultSet = stmt.executeQuery(checkDbQuery);

            if (!resultSet.next()) {
                // Create the database if it doesn't exist
                stmt.executeUpdate("CREATE DATABASE " + dbName);
                System.out.println("✅ Database '" + dbName + "' created successfully!");
            } else {
                System.out.println("🔹 Database '" + dbName + "' already exists.");
            }

        } catch (SQLException e) {
            System.err.println("❌ Error connecting to PostgreSQL: " + e.getMessage());
        }
    }
}
