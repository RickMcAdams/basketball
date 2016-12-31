package org.rick.fbb.model;

import java.io.Serializable;

public class PlayerJson implements Serializable {
	private static final long serialVersionUID = 6460685261711951504L;
	String name;
	String team;
	long id;
	float minutes;
	float points;
	float rebounds;
	float assists;
	float steals;
	float threes;
	float blocks;
	float rating;
	float ncaaTotal;
	long seed;
	String region;

	public PlayerJson() {

	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getMinutes() {
		return minutes;
	}

	public void setMinutes(float minutes) {
		this.minutes = minutes;
	}

	public float getPoints() {
		return points;
	}

	public void setPoints(float points) {
		this.points = points;
	}

	public float getRebounds() {
		return rebounds;
	}

	public void setRebounds(float rebounds) {
		this.rebounds = rebounds;
	}

	public float getAssists() {
		return assists;
	}

	public void setAssists(float assists) {
		this.assists = assists;
	}

	public float getSteals() {
		return steals;
	}

	public void setSteals(float steals) {
		this.steals = steals;
	}

	public float getThrees() {
		return threes;
	}

	public void setThrees(float threes) {
		this.threes = threes;
	}

	public float getBlocks() {
		return blocks;
	}

	public void setBlocks(float blocks) {
		this.blocks = blocks;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public float getNCAATotal() {
		return ncaaTotal;
	}

	public void setNCAATotal(float ncaaTotal) {
		this.ncaaTotal = ncaaTotal;
	}

}
