package org.rick.fbb.model;

import java.io.Serializable;
import java.util.List;

public class NcaaPlayerJson implements Serializable{	
	private static final long serialVersionUID = 6460685261711951504L;
	String name;
	String team;
	long id;
	List<GameJson> games;

	
	public NcaaPlayerJson(){
		
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

	public List<GameJson> getGames() {
		return games;
	}

	public void setGames(List<GameJson> games) {
		this.games = games;
	}	
}
