package org.rick.fbb.model;

import java.io.Serializable;
import java.util.List;

public class OwnerJson implements Serializable{	
	private static final long serialVersionUID = 6460685261711951504L;
	
	String name;
	List<NcaaPlayerJson> players;	

	public List<NcaaPlayerJson> getPlayers() {
		return players;
	}

	public void setPlayers(List<NcaaPlayerJson> players) {
		this.players = players;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

}
