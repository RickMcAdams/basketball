package org.rick.fbb.model;

import java.io.Serializable;

public class GameJson implements Serializable{	
	private static final long serialVersionUID = 6460645261711951504L;
	
	int points;
	int rebounds;
	int assists;
	int total;
	
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int getRebounds() {
		return rebounds;
	}
	public void setRebounds(int rebounds) {
		this.rebounds = rebounds;
	}
	public int getAssists() {
		return assists;
	}
	public void setAssists(int assists) {
		this.assists = assists;
	}
	public int getTotal() {
		return points+rebounds+assists;
	}
	public void setTotal(int total) {
		this.total = total;
	}

}
