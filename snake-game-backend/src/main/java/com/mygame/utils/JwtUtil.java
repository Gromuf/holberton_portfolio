package com.mygame.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys; // Import Keys class for HMAC
import io.jsonwebtoken.JwtParser; // For the builder pattern
import java.util.Date;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public class JwtUtil {

	private static final String SECRET_KEY = "mysecretkey";

	// Generate token with expiration of 24 hours
	public static String generateToken(String username) {
		// Generate Key using the new recommended method
		Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24h expiry
				.signWith(key, SignatureAlgorithm.HS256) // Sign the JWT with the new key
				.compact();
	}

	// Validate token and extract the username
	public static String validateToken(String token) {
		// Generate Key using the new recommended method
		Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

		// Use JwtParserBuilder instead of the deprecated setSigningKey
		JwtParser parser = Jwts.parserBuilder()
				.setSigningKey(key) // Use the key directly
				.build(); // Build the JwtParser

		return parser.parseClaimsJws(token) // Parse the token
				.getBody()
				.getSubject(); // Extract the username
	}
}
