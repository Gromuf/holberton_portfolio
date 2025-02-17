package com.mygame.models;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Settings {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "player_id", nullable = false, unique = true)
	@JsonBackReference // Ignore cette référence lors de la sérialisation pour éviter la boucle
	private Player player;

	private String backgroundTheme;
	private String snakeColor;
}
