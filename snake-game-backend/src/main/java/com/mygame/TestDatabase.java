package com.mygame;

import com.mygame.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Component
public class TestDatabase {

	private final UserService userService;

	@Value("${spring.datasource.username}")
	private String datasourceUsername;

	@Value("${spring.datasource.password}")
	private String datasourcePassword;

	@Value("${spring.datasource.url}")
	private String datasourceUrl;

	public TestDatabase(UserService userService) {
		this.userService = userService;
	}

	@PostConstruct
	public void init() throws Exception {
		// Connect to the 'postgres' database first, to check for 'snake_db'
		createDatabaseIfNeeded("snake_db");

		// Proceed with any other initializations needed for your app
	}

	// Method to check if the database exists and create it if not
	public void createDatabaseIfNeeded(String dbName) {
		DataSource dataSource = getDataSource("jdbc:postgresql://localhost:5432/postgres"); // Connect to the default
																							// 'postgres' DB

		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			// Check if 'snake_db' exists
			String query = "SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'";
			var resultSet = stmt.executeQuery(query);

			if (!resultSet.next()) {
				// If it doesn't exist, create it
				String createDbQuery = "CREATE DATABASE " + dbName;
				stmt.executeUpdate(createDbQuery);
				System.out.println("Database " + dbName + " created.");
			} else {
				System.out.println("Database " + dbName + " already exists.");
			}
		} catch (Exception e) {
			System.err.println("Error while creating the database: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Returns a DataSource for the connection
	private DataSource getDataSource(String url) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl(url);
		dataSource.setUsername(datasourceUsername);
		dataSource.setPassword(datasourcePassword);
		return dataSource;
	}
}
