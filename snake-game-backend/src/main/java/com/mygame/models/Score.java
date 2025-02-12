package com.mygame.models;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Score {

	@Id
	@GeneratedValue
	private long id;

	private int value;
	private LocalDateTime dateTime; // pour save la date et heure du score

	@ManyToOne
	@JoinColumn(name = "player_id") // reference au user dans la table score
	private Player player;

	@OneToMany(mappedBy = "score", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Leaderboard> leaderboards;
}
