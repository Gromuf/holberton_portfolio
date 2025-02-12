package com.mygame.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// CSRF protection customization (same as before)
				.csrf(Customizer.withDefaults()) // This disables CSRF protection for simplicity
				// Replaced deprecated authorizeHttpRequests() with Customizer configuration
				.authorizeHttpRequests((authorizeRequests) -> authorizeRequests
						.requestMatchers("/players").permitAll() // Allowing public access to the /players endpoint
						.anyRequest().authenticated() // Require authentication for other endpoints
				)
				.formLogin(Customizer.withDefaults()) // Enable form-based login
				.httpBasic(Customizer.withDefaults()); // Enable basic authentication for REST APIs

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // Using BCrypt for password encryption
	}
}
