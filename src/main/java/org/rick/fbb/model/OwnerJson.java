package org.rick.fbb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OwnerJson implements Serializable {
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

	public List<Integer> getTotals() {
		List<Integer> totals = new ArrayList<Integer>();
		int grandTotal = 0;
		for (int i = 1; i < 7; i++) {
			totals.add(getRoundTotal(i));
			grandTotal += getRoundTotal(i);
		}
		totals.add(grandTotal);
		return totals;
	}

	private int getRoundTotal(int round) {
		int total = 0;
		for(NcaaPlayerJson player : players){
			total += player.getGames().get(round-1).getTotal();
		}
		return total;
	}

}
