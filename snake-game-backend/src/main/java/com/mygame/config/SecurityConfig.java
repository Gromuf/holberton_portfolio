// package com.mygame.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig { // Plus besoin d'hériter de WebSecurityConfigurerAdapter

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     // Méthode pour configurer les règles de sécurité de ton application
//     public void configure(HttpSecurity http) throws Exception {
//         http
//             .authorizeRequests()
//                 .antMatchers("/api/public/**").permitAll() // Permet d'accéder sans authentification à /api/public/**
//                 .anyRequest().authenticated() // Toute autre requête nécessite une authentification
//             .and()
//             .formLogin(); // Ou une autre méthode d'authentification
//     }
// }
