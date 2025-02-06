package com.mygame.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "player")

public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // id generé automatiquement par la db
	@Column(name = "id")
	private long id;

	@Column(nullable = false, unique = true, name = "username") // username ne peut pas etre null et dois etre unique
	private String username;

	@Column(nullable = false, unique = true, name = "email") // email obligatoire et unique
	private String email;

	@Column(nullable = false, name = "password") // password obligatoire
	private String password;

	@OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true) // relation un a plusieurs avec Score
																					// soit un player peut avoir plusieurs
																					// scores
	@JsonIgnore
	private List<Score> scores;
}