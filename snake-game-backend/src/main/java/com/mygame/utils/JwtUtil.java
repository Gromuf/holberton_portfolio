package com.mygame.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import java.util.Date;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtUtil {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

	@Value("${jwt.secret}") // Charge la clé depuis application.properties
	private String secretKey;

	// 🔐 Méthode privée pour récupérer la clé de signature correcte
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	// 🔑 Générer un token JWT
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email) // ✅ On passe maintenant l'email
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Token valide 24h
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	// ✅ Valider un token JWT
	public String validateToken(String token) {
		try {
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(getSigningKey()) // ✅ Corrigé ici
					.build()
					.parseClaimsJws(token)
					.getBody();

			return claims.getSubject();
		} catch (Exception e) {
			logger.error("❌ Erreur de validation du token : {}", e.getMessage());
			return null;
		}
	}
}
