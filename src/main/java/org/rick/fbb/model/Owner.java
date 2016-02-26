package org.rick.fbb.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.rick.fbb.util.Util;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mcadamsrb
 */
public class Owner {

  private int id;
  private List<Player> players;
  private String name;
  private List<BigDecimal> totals;
  private int rank;
  private int numPlayers;
  private int grandTotal;
  
  List<Integer> weeklyTotals;
  List<Integer> seasonTotals;
  
  List<Double> weeklyStandings;
  List<Double> seasonStandings;
  
  List<String> weeklyStandingsFormatted;
  List<String> seasonStandingsFormatted;
  
  public List<String> getWeeklyStandingsFormatted() {
	return weeklyStandingsFormatted;
}

public void setWeeklyStandingsFormatted(List<String> weeklyStandingsFormatted) {
	this.weeklyStandingsFormatted = weeklyStandingsFormatted;
}

public List<String> getSeasonStandingsFormatted() {
	return seasonStandingsFormatted;
}

public void setSeasonStandingsFormatted(List<String> seasonStandingsFormatted) {
	this.seasonStandingsFormatted = seasonStandingsFormatted;
}

public List<Double> getWeeklyStandings() {
	return weeklyStandings;
}
  
  public Double getWeeklyStandingsTotal(){
	  return weeklyStandings.get(0) +
			  weeklyStandings.get(1) +
			  weeklyStandings.get(2) +
			  weeklyStandings.get(3) +
			  weeklyStandings.get(4) +
			  weeklyStandings.get(5); 
  }
  
  public Double getSeasonStandingsTotal(){
	  return seasonStandings.get(0) +
			  seasonStandings.get(1) +
			  seasonStandings.get(2) +
			  seasonStandings.get(3) +
			  seasonStandings.get(4) +
			  seasonStandings.get(5); 
  }

public void setWeeklyStandings(List<Double> weeklyStandings) {
	this.weeklyStandings = weeklyStandings;
}

public List<Double> getSeasonStandings() {
	return seasonStandings;
}

public void setSeasonStandings(List<Double> seasonStandings) {
	this.seasonStandings = seasonStandings;
}



  public List<Integer> getSeasonTotals() {
	return seasonTotals;
}

public void setSeasonTotals(List<Integer> seasonTotals) {
	this.seasonTotals = seasonTotals;
}

public List<Integer> getWeeklyTotals() {
	return weeklyTotals;
}

public void setWeeklyTotals(List<Integer> weeklyTotals) {
	this.weeklyTotals = weeklyTotals;
}

/**
   * @return the players
   */
  public List<Player> getPlayers() {
	return players;
  }

  /**
   * @param players the players to set
   */
  public void setPlayers(List<Player> players) {
	this.players = players;
  }

  /**
   * @return the name
   */
  public String getName() {
	return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
	this.name = name;
  }

  /**
   * @return the totals
   */
  public List<BigDecimal> getTotals() {
	return totals;
  }

  /**
   * @param totals the totals to set
   */
  public void setTotals(List<BigDecimal> totals) {
	this.totals = totals;
  }

  /**
   * @return the rank
   */
  public int getRank() {
	return rank;
  }

  /**
   * @param rank the rank to set
   */
  public void setRank(int rank) {
	this.rank = rank;
  }

  public int getGrandTotal() {
	int grandTotal = 0;
	for (int i = 0; i < totals.size(); i++) {
	  grandTotal += totals.get(i).intValue();
	}
	return grandTotal;
  }

  /**
   * @return the numPlayers
   */
  public int getNumPlayers() {
	return numPlayers;
  }

  /**
   * @param numPlayers the numPlayers to set
   */
  public void setNumPlayers(int numPlayers) {
	this.numPlayers = numPlayers;
  }

  /**
   * @return the id
   */
  public int getId() {
	return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(int id) {
	this.id = id;
  }
  
  public OwnerJson getOwnerJson(){
	  OwnerJson ownerJson = new OwnerJson();
	  ownerJson.setName(name);
	  List<NcaaPlayerJson> playerList = new ArrayList<NcaaPlayerJson>();
	  for(Player player : this.getPlayers()){
		  playerList.add(player.getNCAAPlayerJson());
	  }
	  ownerJson.setPlayers(playerList);	  
	  return ownerJson;
  }

 
}
