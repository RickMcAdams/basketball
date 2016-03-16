package org.rick.fbb.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.rick.fbb.util.Util;

public class Player implements Serializable {
	private static final long serialVersionUID = 3912801499920596383L;
	private long id;
	private String name;
	private String team;
	private String teamId;
	private String region;
	private String seed;
	private String position;
	private boolean playing;
	private List<Game> games;
	private List<Game> ncaaGames;
	private float efficiency;
	private float accEfficiency;
	private String status;
	private String owner;
	private int order;
	private final String link = "http://espn.go.com/ncb/player/profile?playerId=";
	private final String linkYahoo = "http://rivals.yahoo.com/ncaa/basketball/players/";

	public boolean isActive() {
		if (status != null) {
			if (status.compareTo("I") == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getNameLink() {
		return getNameLink("ESPN");
	}

	public String getNameLink(String source) {
		if (source.compareTo("ESPN") == 0) {
			return "<a href=" + link + this.getId() + ">" + this.getName() + "</a>";
		} else {
			return "<a href=" + linkYahoo + this.getId() + ">" + this.getName() + "</a>";
		}
	}

	/**
	 * @return the games
	 */
	public List<Game> getGames() {
		return games;
	}

	/**
	 * @param games
	 *            the games to set
	 */
	public void setGames(List<Game> games) {
		this.games = games;
	}

	/**
	 * @return the games
	 */
	public List<Game> getNCAAGames() {
		return ncaaGames;
	}

	/**
	 * @param games
	 *            the games to set
	 */
	public void setNCAAGames(List<Game> ncaaGames) {
		this.ncaaGames = ncaaGames;
	}

	public int getNCAAGamesTotal() {
		int total = 0;
		if (ncaaGames != null) {
			for (int i = 0; i < ncaaGames.size(); i++) {
				if (ncaaGames.get(i) != null) {
					total += ncaaGames.get(i).getNCAAGameTotal();
				}
			}
		}
		return total;
	}

	public int getNumGames() {
		int numGames = 0;
		for (int i = 0; i < games.size(); i++) {
			if (games.get(i).getMinutes() > 0 || games.get(i).getPoints() > 0 || games.get(i).getRebounds() > 0
					|| games.get(i).getAssists() > 0 || games.get(i).getSteals() > 0 || games.get(i).getBlocks() > 0) {
				numGames++;
			}
		}
		return numGames;
	}

	public int getNumACCGames() {
		int numGames = 0;
		for (int i = 0; i < games.size(); i++) {
			if ((games.get(i).getMinutes() > 0 || games.get(i).getPoints() > 0 || games.get(i).getRebounds() > 0
					|| games.get(i).getAssists() > 0 || games.get(i).getSteals() > 0 || games.get(i).getBlocks() > 0)
					&& this.isACCGame(games.get(i).getOpponent())) {
				numGames++;
			}
		}
		return numGames;
	}

	public float avgPoints() {
		float number = 0;
		if (games.size() == 0) {
			return 0;
		}
		for (int i = 0; i < games.size(); i++) {
			number += games.get(i).getPoints();
		}
		return number / this.getNumGames();
	}

	public float avgRebounds() {
		float number = 0;
		if (games.size() == 0) {
			return 0;
		}
		for (int i = 0; i < games.size(); i++) {
			number += games.get(i).getRebounds();
		}
		return number / this.getNumGames();
	}

	public float avgAssists() {
		float number = 0;
		if (games.size() == 0) {
			return 0;
		}
		for (int i = 0; i < games.size(); i++) {
			number += games.get(i).getAssists();
		}
		return number / this.getNumGames();
	}

	public float avgSteals() {
		float number = 0;
		if (games.size() == 0) {
			return 0;
		}
		for (int i = 0; i < games.size(); i++) {
			number += games.get(i).getSteals();
		}
		return number / this.getNumGames();
	}

	public float avgBlocks() {
		float number = 0;
		if (games.size() == 0) {
			return 0;
		}
		for (int i = 0; i < games.size(); i++) {
			number += games.get(i).getBlocks();
		}
		return number / this.getNumGames();
	}

	public float avgThrees() {
		float number = 0;
		if (games.size() == 0) {
			return 0;
		}
		for (int i = 0; i < games.size(); i++) {
			number += games.get(i).getThrees();
		}
		return number / this.getNumGames();
	}

	public float avgMinutes() {
		float number = 0;
		if (games.size() == 0) {
			return 0;
		}
		for (int i = 0; i < games.size(); i++) {
			number += games.get(i).getMinutes();
		}
		return number / this.getNumGames();
	}

	public float avgACCPoints() {
		float number = 0;
		List<Game> accGames = this.getACCGames();
		if (accGames.size() == 0) {
			return 0;
		}
		for (int i = 0; i < accGames.size(); i++) {
			number += accGames.get(i).getPoints();
		}
		return number / accGames.size();
	}

	public float avgACCRebounds() {
		float number = 0;
		List<Game> accGames = this.getACCGames();
		if (accGames.size() == 0) {
			return 0;
		}
		for (int i = 0; i < accGames.size(); i++) {
			number += accGames.get(i).getRebounds();
		}
		return number / accGames.size();
	}

	public float avgACCAssists() {
		float number = 0;
		List<Game> accGames = this.getACCGames();
		if (accGames.size() == 0) {
			return 0;
		}
		for (int i = 0; i < accGames.size(); i++) {
			number += accGames.get(i).getAssists();
		}
		return number / accGames.size();
	}

	public float avgACCSteals() {
		float number = 0;
		List<Game> accGames = this.getACCGames();
		if (accGames.size() == 0) {
			return 0;
		}
		for (int i = 0; i < accGames.size(); i++) {
			number += accGames.get(i).getSteals();
		}
		return number / accGames.size();
	}

	public float avgACCBlocks() {
		float number = 0;
		List<Game> accGames = this.getACCGames();
		if (accGames.size() == 0) {
			return 0;
		}
		for (int i = 0; i < accGames.size(); i++) {
			number += accGames.get(i).getBlocks();
		}
		return number / accGames.size();
	}

	public float avgACCThrees() {
		float number = 0;
		List<Game> accGames = this.getACCGames();
		if (accGames.size() == 0) {
			return 0;
		}
		for (int i = 0; i < accGames.size(); i++) {
			number += accGames.get(i).getThrees();
		}
		return number / accGames.size();
	}

	public float avgACCMinutes() {
		float number = 0;
		List<Game> accGames = this.getACCGames();
		if (accGames.size() == 0) {
			return 0;
		}
		for (int i = 0; i < accGames.size(); i++) {
			number += accGames.get(i).getMinutes();
		}
		return number / accGames.size();
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.getName() + "," + this.getId() + "," + this.avgMinutes() + "," + this.avgPoints() + ","
				+ this.avgRebounds() + "," + this.avgAssists() + "," + this.avgSteals() + "," + this.avgBlocks() + ","
				+ this.avgThrees();
	}

	public String printAverages(String source) {
		return "<tr><td>" + this.getTeam() + "</td>" + "<td>" + this.getId() + "</td>" + "<td>"
				+ this.getNameLink(source) + "</td><td>" + round(this.avgMinutes()) + "</td><td>"
				+ round(this.avgPoints()) + "</td><td>" + round(this.avgRebounds()) + "</td><td>"
				+ round(this.avgAssists()) + "</td><td>" + round(this.avgSteals()) + "</td><td>"
				+ round(this.avgBlocks()) + "</td><td>" + round(this.avgThrees()) + "</td></tr>";
	}

	public String printAveragesAndEfficiency(String source) {
		return "<tr><td>" + this.getTeam() + "</td>" + "<td>" + this.getId() + "</td>" + "<td>"
				+ this.getNameLink(source) + "</td><td>" + round(this.avgMinutes()) + "</td><td>"
				+ round(this.avgPoints()) + "</td><td>" + round(this.avgRebounds()) + "</td><td>"
				+ round(this.avgAssists()) + "</td><td>" + round(this.avgSteals()) + "</td><td>"
				+ round(this.avgBlocks()) + "</td><td>" + round(this.avgThrees()) + "</td><td>" + this.getEfficiency()
				+ "</td></tr>";
	}

	public String printNCAAAverages(String source) {
		return "<tr><td>" + this.getRegion() + "</td><td>" + this.getSeed() + "</td><td>" + this.getTeam() + "</td><td>"
				+ this.getId() + "</td><td>" + this.getNameLink(source) + "</td><td>" + round(this.avgMinutes())
				+ "</td><td>" + round(this.avgPoints()) + "</td><td>" + round(this.avgRebounds()) + "</td><td>"
				+ round(this.avgAssists()) + "</td></tr>";
	}

	public String printNCAANames(String source) {
		return "<tr><td>" + this.getId() + "</td><td>" + this.getName() + " - " + this.getTeam() + "</td></tr>";
	}

	public String printACCAverages(String source) {
		return "<tr><td>" + this.getTeam() + "</td>" + "<td>" + this.getId() + "</td>" + "<td>"
				+ this.getNameLink(source) + "</td><td>" + round(this.avgACCMinutes()) + "</td><td>"
				+ round(this.avgACCPoints()) + "</td><td>" + round(this.avgACCRebounds()) + "</td><td>"
				+ round(this.avgACCAssists()) + "</td><td>" + round(this.avgACCSteals()) + "</td><td>"
				+ round(this.avgACCBlocks()) + "</td><td>" + round(this.avgACCThrees()) + "</td></tr>";
	}

	public String printACCAveragesAndEfficiency(String source) {
		return "<tr><td>" + this.getOwner() + "</td>" + "<td>" + this.getTeam() + "</td>" + "<td>" + this.getId()
				+ "</td>" + "<td>" + this.getNameLink(source) + "</td><td>" + round(this.avgACCMinutes()) + "</td><td>"
				+ round(this.avgACCPoints()) + "</td><td>" + round(this.avgACCRebounds()) + "</td><td>"
				+ round(this.avgACCAssists()) + "</td><td>" + round(this.avgACCSteals()) + "</td><td>"
				+ round(this.avgACCBlocks()) + "</td><td>" + round(this.avgACCThrees()) + "</td><td>"
				+ this.getAccEfficiency() + "</td></tr>";
	}

	public Game getNCAAGame(String beginDate, String endDate) {
		Game game = null;

		Calendar begin = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		Calendar end1 = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("M/d/y");
		try {
			begin.setTime(sdf.parse(beginDate));
			end.setTime(sdf.parse(endDate));
			end1.setTime(sdf.parse(endDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		end1.roll(Calendar.DATE, false); // Roll it back 1 day

		for (int i = 0; i < games.size(); i++) {
			Calendar date = games.get(i).getDateAsDate();
			// System.out.println("Begin" + begin.getTimeInMillis());
			// System.out.println("IS" + date.getTimeInMillis() + " " +
			// games.get(i).getOpponent() + " " + date.toString());
			// System.out.println("END" + end.getTimeInMillis() +"\n");
			if ((date.after(begin) || date.equals(begin)) && (date.before(end) || date.equals(end))) { 
				// if (date.before(end1)) {
				game = games.get(i);
				//System.out.println("got game " + game.gameToNCAAStatLine());
				break;
			}
		}
		return game;
	}

	public List<Game> getWeekGames(String week) {
		return getWeekGames(Util.getBeginDate(week), Util.getEndDate(week));
	}

	public List<Game> getWeekGames(String beginDate, String endDate) {
		return getWeekGames(beginDate, endDate, true);
	}

	public List<Game> getWeekGames(String beginDate, String endDate, boolean acc) {
		List<Game> weekGames = new ArrayList<Game>();
		weekGames.add(new Game());
		weekGames.add(new Game());
		Calendar begin = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		Calendar end1 = Calendar.getInstance();

		acc = true;
		SimpleDateFormat sdf = new SimpleDateFormat("M/d/y");
		try {
			begin.setTime(sdf.parse(beginDate));
			end.setTime(sdf.parse(endDate));
			end1.setTime(sdf.parse(endDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		end1.roll(Calendar.DATE, false); // Roll it back 1 day to get only
											// Saturday and Sunday games

		for (int i = 0; i < games.size(); i++) {
			Calendar date = games.get(i).getDateAsDate();

			if ((date.after(begin) || date.equals(begin)) && (date.before(end) || date.equals(end))) { // Within
																										// the
																										// range
				if (!acc) {
					if (date.before(end1)) {
						weekGames.set(0, games.get(i));
					} else {
						weekGames.set(1, games.get(i));
					}
				} else {
					if (isACCGame(games.get(i).getOpponent())) {
						if (date.before(end1)) {
							weekGames.set(0, games.get(i));
						} else {
							weekGames.set(1, games.get(i));
						}
					}
				}
			}
		}
		return weekGames;
	}

	public List<Game> getACCGames() {
		List<Game> accGames = new ArrayList<Game>();
		for (int i = 0; i < games.size(); i++) {
			if (isACCGame(games.get(i).getOpponent()) && (games.get(i).getMinutes() > 0 || games.get(i).getPoints() > 0
					|| games.get(i).getRebounds() > 0 || games.get(i).getAssists() > 0 || games.get(i).getSteals() > 0
					|| games.get(i).getBlocks() > 0)) {
				accGames.add(games.get(i));
			}
		}

		return accGames;
	}

	public boolean isACCGame(String opp) {
		opp = opp.toUpperCase();
		if (opp.compareTo("BC") == 0 || opp.compareTo("CLEMSON") == 0 || opp.compareTo("DUKE") == 0
				|| opp.compareTo("FLORIDA ST") == 0 || opp.compareTo("FSU") == 0 || opp.compareTo("GA TECH") == 0
				|| opp.compareTo("LOUISVILLE") == 0 || opp.compareTo("MIAMI") == 0 || opp.compareTo("UNC") == 0
				|| opp.compareTo("NC STATE") == 0 || opp.compareTo("VIRGINIA") == 0 || opp.compareTo("UVA") == 0
				|| opp.compareTo("VA TECH") == 0 || opp.compareTo("W FOREST") == 0
				|| opp.compareTo("BOSTON COLLEGE") == 0 || opp.compareTo("FLORIDA STATE") == 0
				|| opp.compareTo("GEORGIA TECH") == 0 || opp.compareTo("MIAMI (FL)") == 0
				|| opp.compareTo("NORTH CAROLINA") == 0 || opp.compareTo("NORTH CAROLINA STATE") == 0
				|| opp.compareTo("VIRGINIA TECH") == 0 || opp.compareTo("WAKE FOREST") == 0
				|| opp.compareTo("NOTRE DAME") == 0 || opp.compareTo("PITTSBURGH") == 0 || opp.compareTo("PITT") == 0
				|| opp.compareTo("SYRACUSE") == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return the team
	 */
	public String getTeam() {
		return team;
	}

	/**
	 * @param team
	 *            the team to set
	 */
	public void setTeam(String team) {
		this.team = team;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the playing
	 */
	public boolean isPlaying() {
		return playing;
	}

	/**
	 * @param playing
	 *            the playing to set
	 */
	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	public float round(float val) {
		float p = (float) Math.pow(10, 1);
		val = val * p;
		float tmp = Math.round(val);
		return (float) tmp / p;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public void calculateEfficiency(float points, float rebounds, float assists, float steals, float blocks,
			float threes) {

		this.setEfficiency((this.avgPoints() / points) + (this.avgRebounds() / rebounds) + (this.avgAssists() / assists)
				+ (this.avgSteals() / steals) + (this.avgBlocks() / blocks) + (this.avgThrees() / threes));

	}

	public void calculateAccEfficiency(float points, float rebounds, float assists, float steals, float blocks,
			float threes) {

		this.setAccEfficiency((this.avgACCPoints() / points) + (this.avgACCRebounds() / rebounds)
				+ (this.avgACCAssists() / assists) + (this.avgACCSteals() / steals) + (this.avgACCBlocks() / blocks)
				+ (this.avgACCThrees() / threes));

	}

	/**
	 * @return the efficiency
	 */
	public float getEfficiency() {
		return efficiency;
	}

	/**
	 * @param efficiency
	 *            the efficiency to set
	 */
	public void setEfficiency(float efficiency) {
		this.efficiency = efficiency;
	}

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * @return the accEfficiency
	 */
	public float getAccEfficiency() {
		return accEfficiency;
	}

	/**
	 * @param accEfficiency
	 *            the accEfficiency to set
	 */
	public void setAccEfficiency(float accEfficiency) {
		this.accEfficiency = accEfficiency;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the teamId
	 */
	public String getTeamId() {
		return teamId;
	}

	/**
	 * @param teamId
	 *            the teamId to set
	 */
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region
	 *            the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the seed
	 */
	public String getSeed() {
		return seed;
	}

	/**
	 * @param seed
	 *            the seed to set
	 */
	public void setSeed(String seed) {
		this.seed = seed;
	}

	public float getNCAATotal() {
		return this.avgPoints() + this.avgRebounds() + this.avgAssists();
	}

	public PlayerJson getPlayerJson() {
		PlayerJson player = new PlayerJson();
		player.setTeam(team);
		player.setName(name);
		player.setId(id);
		player.setMinutes(avgMinutes());
		player.setPoints(avgPoints());
		player.setRebounds(avgRebounds());
		player.setAssists(avgAssists());
		player.setSteals(avgSteals());
		player.setThrees(avgThrees());
		player.setBlocks(avgBlocks());
		player.setRating(getEfficiency());
		player.setNCAATotal(getNCAATotal());
		if (getSeed() != null) {
			player.setSeed(Long.valueOf(getSeed()).longValue());
		}
		player.setRegion(getRegion());
		return player;
	}

	public NcaaPlayerJson getNCAAPlayerJson() {
		NcaaPlayerJson player = new NcaaPlayerJson();
		player.setTeam(team);
		player.setName(name);
		player.setId(id);
		List<GameJson> gameList = new ArrayList<GameJson>();

		for (Game game : this.getNCAAGames()) {
			System.out.println("Got game");
			if (game != null) {
				gameList.add(game.getGameJson());
			} else {
				gameList.add(new GameJson());
			}
		}
		player.setGames(gameList);
		return player;
	}

	public PlayerJson getACCPlayerJson() {
		PlayerJson player = new PlayerJson();
		player.setTeam(team);
		player.setName(name);
		player.setId(id);
		player.setMinutes(avgACCMinutes());
		player.setPoints(avgACCPoints());
		player.setRebounds(avgACCRebounds());
		player.setAssists(avgACCAssists());
		player.setSteals(avgACCSteals());
		player.setThrees(avgACCThrees());
		player.setBlocks(avgACCBlocks());
		player.setRating(getAccEfficiency());
		return player;
	}

}
