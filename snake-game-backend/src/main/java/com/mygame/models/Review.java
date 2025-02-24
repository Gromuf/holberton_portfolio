package com.mygame.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "player_id", nullable = false)
	private Player player;

	@Column(nullable = false, length = 500)
	private String text;

	@Column(nullable = false)
	private int score; // Le score de la partie au moment de la review
}
