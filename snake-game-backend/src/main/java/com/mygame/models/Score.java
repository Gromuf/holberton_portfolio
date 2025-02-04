package com.mygame.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor

public class Score {

	@Id
	@GeneratedValue
	private long id;

	private int value;
	private LocalDateTime dateTime;	//pour save la date et heure du score

	@ManyToOne
	@JoinColumn(name = "user_id") //reference au user dans la table score
	private User user;
}
