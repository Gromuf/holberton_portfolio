package com.mygame.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LeaderboardDTO {
	private String username;
	private int score;
	private int rank;
	private String period;
}
