
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rick.fbb.repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.rick.fbb.model.Game;
import org.rick.fbb.model.Owner;
import org.rick.fbb.model.Player;
import org.rick.fbb.model.Team;
import org.rick.fbb.util.Util;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import fbb.ESPNWriter;

/**
 *
 * @author User
 */
@Component
public class FbbRepository {

	private JdbcTemplate jdbcTemplate;
	String url = "jdbc:mysql://localhost:3306/fbb?useSSL=false";
	String driver = "com.mysql.jdbc.Driver";
	String userName = "root";
	String password = "root";

	public FbbRepository() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(this.url, this.userName, this.password);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return conn;
	}

	public boolean playerExists(long playerId) {
		boolean playerExists = false;
		Connection conn = null;
		PreparedStatement ps;

		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT * FROM players where id=?");
			ps.setLong(1, playerId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				playerExists = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return playerExists;
	}

	public boolean playerExistsNCAA(long playerId) {
		boolean playerExists = false;
		Connection conn = null;
		PreparedStatement ps;

		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT * FROM ncaa_players where id=?");
			ps.setLong(1, playerId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				playerExists = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return playerExists;
	}

	public boolean gameExists(long playerId, String date) {
		boolean gameExists = false;
		Connection conn = null;
		PreparedStatement ps;

		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT * FROM games WHERE playerId=? AND date = ?");
			ps.setLong(1, playerId);
			ps.setString(2, date);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				gameExists = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return gameExists;
	}

	public String insertPlayer(Player player) {
		Connection conn = null;
		PreparedStatement ps;

		System.out.println("Loading " + player.getName());

		if (!playerExists(player.getId())) {
			try {
				conn = getConnection();
				ps = conn.prepareStatement("INSERT INTO PLAYERS (id, name, team) VALUES(?,?,?)");
				ps.setLong(1, player.getId());
				ps.setString(2, player.getName());
				ps.setString(3, player.getTeam());
				ps.execute();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
		List<Game> games = player.getGames();
		for (int i = 0; i < games.size(); i++) {
			if (!gameExists(player.getId(), games.get(i).getDate())) {
				insertGame(player.getId(), games.get(i));
			} else {
				updateGame(player.getId(), games.get(i));
			}
		}
		return "Updated " + player.getName();
	}

	public String insertNCAAPlayer(Player player) {
		Connection conn = null;
		PreparedStatement ps;

		System.out.println("Loading " + player.getName());

		if (!playerExistsNCAA(player.getId())) {
			try {
				conn = getConnection();
				ps = conn.prepareStatement("INSERT INTO NCAA_PLAYERS (id, name, team) VALUES(?,?,?)");
				ps.setLong(1, player.getId());
				ps.setString(2, player.getName());
				ps.setString(3, player.getTeamId());
				ps.execute();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
		List<Game> games = player.getGames();
		for (int i = 0; i < games.size(); i++) {
			if (!gameExists(player.getId(), games.get(i).getDate())) {
				insertGame(player.getId(), games.get(i));
			} else {
				updateGame(player.getId(), games.get(i));
			}
		}
		return "Updated " + player.getName();
	}

	public void insertOwnerPlayer(long ownerId, long playerId, long week, String status, long order) {
		Connection conn = null;
		PreparedStatement ps;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(
					"INSERT INTO OWNER_PLAYER (ownerId, playerId, week, status, playerOrder) VALUES(?,?,?,?,?)");
			ps.setLong(1, ownerId);
			ps.setLong(2, playerId);
			ps.setLong(3, week);
			ps.setString(4, status);
			ps.setLong(5, order);
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	public void insertNCAAOwnerPlayer(long ownerId, long playerId, String status, long order) {
		Connection conn = null;
		PreparedStatement ps;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(
					"INSERT INTO NCAA_OWNER_PLAYER (ownerId, playerId, status, playerOrder) VALUES(?,?,?,?)");
			ps.setLong(1, ownerId);
			ps.setLong(2, playerId);
			ps.setString(3, status);
			ps.setLong(4, order);
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	public void deleteOwnerPlayers(long ownerId, long week) {
		Connection conn = null;
		PreparedStatement ps;

		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM OWNER_PLAYER WHERE ownerId = ? AND week =?");
			ps.setLong(1, ownerId);
			ps.setLong(2, week);
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	public void deleteNCAAOwnerPlayers(long ownerId) {
		Connection conn = null;
		PreparedStatement ps;

		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM NCAA_OWNER_PLAYER WHERE ownerId = ?");
			ps.setLong(1, ownerId);
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	public void mockPopulateOwners() {
		List<Player> players = this.getAllPlayers();
		int index = 0;
		String active;
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 8; j++) {
				if (j < 6) {
					active = "A";
				} else {
					active = "I";
				}
				insertOwnerPlayer(i + 1, players.get(index).getId(), 1, active, j + 1);
				index++;
			}
		}
	}

	public List<String> populateTeam(String teamName) throws FileNotFoundException, IOException {
		List<String> results = new ArrayList<String>();

		BufferedReader reader = new BufferedReader(new FileReader("C:\\dev\\data\\FACC\\teams\\" + teamName + ".txt"));
		ESPNWriter writer = new ESPNWriter();
		String text = reader.readLine();
		if (text.length() > 10) {
			text = text.substring(0, text.indexOf(" "));
		}

		while (text != null) {
			System.out.println(text);
			Player player;
			try {
				player = writer.getPlayer(text);
				player.setTeam(teamName);
				results.add(insertPlayer(player));
			} catch (Exception e) {
				results.add(e.toString());
				e.printStackTrace();
			}
			text = reader.readLine();
			if (text != null && text.length() > 10) {
				text = text.substring(0, text.indexOf(" "));
			}
		}
		reader.close();
		return results;
	}

	public List<String> populateNCAATeam(String teamId) throws FileNotFoundException, IOException {
		List<String> results = new ArrayList<String>();
		System.out.println(teamId);
		BufferedReader reader = new BufferedReader(new FileReader("C:\\dev\\data\\FNCAA\\teams\\" + teamId + ".txt"));
		ESPNWriter writer = new ESPNWriter();
		String text = reader.readLine();
		if (text != null && text.length() > 10) {
			text = text.substring(0, text.indexOf(" "));
		}

		while (text != null) {
			Player player;
			try {
				player = writer.getPlayer(text);
				player.setTeamId(teamId);
				results.add(insertNCAAPlayer(player));
			} catch (Exception e) {
				e.printStackTrace();
				results.add(e.toString());
			}
			text = reader.readLine();
			if (text != null && text.length() > 10) {
				text = text.substring(0, text.indexOf(" "));
			}

		}
		reader.close();
		return results;
	}

	public List<String> populateAllTeams() throws FileNotFoundException, IOException {
		List<String> results = new ArrayList<String>();
		List<String> teams = Util.getAllTeams();
		List<String> tempResults;

		for (String team : teams) {
			tempResults = populateTeam(team);
			for (String temp : tempResults) {
				results.add(temp);
			}
			tempResults.clear();
		}
		return results;
	}

	public List<String> populateAllNCAATeams() throws FileNotFoundException, IOException {
		List<Team> teams = this.getAllNCAATeams();
		List<String> results = new ArrayList<String>();
		List<String> tempResults;

		for (Team team : teams) {
			tempResults = populateNCAATeam(team.getId());
			for (String temp : tempResults) {
				results.add(temp);
			}
			tempResults.clear();
		}
		return results;
	}

	public List<Team> getAllNCAATeams() {

		String sql = "SELECT teamId, name, region, seed FROM ncaa_teams ORDER BY name";
		List<Team> teams = jdbcTemplate.query(sql, new TeamMapper());

		return teams;
	}

	// @SuppressWarnings("CallToThreadDumpStack")
	public void createNCAATeamFiles() {
		List<Team> teams = this.getAllNCAATeams();
		ESPNWriter espnWriter = new ESPNWriter();
		try {
			for (Team team : teams) {
				System.out.println(team.getId() + " - " + team.getName());
				espnWriter.getTeam(team.getId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Player> getAllPlayers() {
		List<Player> players = new ArrayList<Player>();

		String sql = "SELECT id, name, team FROM players ORDER BY team";

		players = jdbcTemplate.query(sql, new PlayerMapper());
		for (Player player : players) {
			player.setGames(this.getPlayerGames(player.getId()));
			player.setOwner(this.getPlayerOwner(player.getId(), Util.getWeek()));
		}
		players = calculateEfficiency(players);
		players = calculateACCEfficiency(players);
		return players;
	}

	private List<Player> calculateEfficiency(List<Player> players) {
		float points = 0;
		float rebounds = 0;
		float assists = 0;
		float steals = 0;
		float blocks = 0;
		float threes = 0;

		// Calculate max
		for (Player player : players) {
			if (player.avgPoints() > points) {
				points = player.avgPoints();
			}
			if (player.avgRebounds() > rebounds) {
				rebounds = player.avgRebounds();
			}
			if (player.avgAssists() > assists) {
				assists = player.avgAssists();
			}
			if (player.avgSteals() > steals) {
				steals = player.avgSteals();
			}
			if (player.avgBlocks() > blocks) {
				blocks = player.avgBlocks();
			}
			if (player.avgThrees() > threes) {
				threes = player.avgThrees();
			}
		}
		for (Player player : players) {
			player.calculateEfficiency(points, rebounds, assists, steals, blocks, threes);
		}

		return players;
	}

	private List<Player> calculateACCEfficiency(List<Player> players) {
		float points = 0;
		float rebounds = 0;
		float assists = 0;
		float steals = 0;
		float blocks = 0;
		float threes = 0;

		// Calculate max
		for (Player player : players) {
			if (player.avgACCPoints() > points) {
				points = player.avgACCPoints();
			}
			if (player.avgACCRebounds() > rebounds) {
				rebounds = player.avgACCRebounds();
			}
			if (player.avgACCAssists() > assists) {
				assists = player.avgACCAssists();
			}
			if (player.avgACCSteals() > steals) {
				steals = player.avgACCSteals();
			}
			if (player.avgACCBlocks() > blocks) {
				blocks = player.avgACCBlocks();
			}
			if (player.avgACCThrees() > threes) {
				threes = player.avgACCThrees();
			}
		}
		for (Player player : players) {
			player.calculateAccEfficiency(points, rebounds, assists, steals, blocks, threes);
		}
		return players;
	}

	public List<Player> getAllNCAAPlayers() {
		List<Player> players = new ArrayList<Player>();
		Connection conn = null;
		PreparedStatement ps;

		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT id, name, teamName, region, seed FROM ncaa_players_v ORDER BY teamName");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Player player = new Player();
				player.setId(rs.getLong("id"));
				player.setName(rs.getString("name"));
				player.setTeam(rs.getString("teamName"));
				player.setRegion(rs.getString("region"));
				player.setSeed(rs.getString("seed"));
				player.setGames(this.getPlayerGames(player.getId()));
				players.add(player);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return players;
	}

	public List<Game> getPlayerGames(long id) {
		String sql = "SELECT date, opponent, minutes, points, rebounds, assists, steals, blocks, threes FROM games "
				+ "WHERE playerId = ?";

		List<Game> games = jdbcTemplate.query(sql, new GameMapper(), id);

		return games;
	}

	public List<Owner> getAllOwnerPlayers(int week) {
		List<Owner> owners = new ArrayList<Owner>();

		String sql = "SELECT id, name FROM owners";
		String sql2 = "SELECT A.playerId playerId, A.status status, B.name name, B.team team, A.playerOrder playerOrder "
				+ "FROM owner_player A, players B WHERE ownerId = ? AND week = ? "
				+ "AND A.playerId = B.id ORDER BY playerOrder";

		owners = jdbcTemplate.query(sql, new OwnerMapper());
		for (Owner owner : owners) {
			List<Player> players = jdbcTemplate.query(sql2, new OwnerPlayerMapper(), owner.getId(), week);

			for (Player player : players) {
				player.setGames(getPlayerGames(player.getId()));
				player.setOwner(this.getPlayerOwner(player.getId(), week));
			}
			owner.setPlayers(players);
			owner.setWeeklyTotals(this.calculateWeeklyTotal(players, Integer.valueOf(week).toString()));
			owner.setSeasonTotals(this.calculateSeasonTotal(owner, Integer.valueOf(week).toString()));
		}
		// Once we have all the owners we need to calculate standings
		owners = calculateStandings(owners);

		return owners;
	}

	private List<Owner> calculateStandings(List<Owner> owners) {
		List<Integer> points = new ArrayList<Integer>();
		List<Integer> rebounds = new ArrayList<Integer>();
		List<Integer> assists = new ArrayList<Integer>();
		List<Integer> steals = new ArrayList<Integer>();
		List<Integer> blocks = new ArrayList<Integer>();
		List<Integer> threes = new ArrayList<Integer>();

		// Get the numbers for weekly standings
		for (Owner owner : owners) {
			points.add(owner.getWeeklyTotals().get(0));
			rebounds.add(owner.getWeeklyTotals().get(1));
			assists.add(owner.getWeeklyTotals().get(2));
			steals.add(owner.getWeeklyTotals().get(3));
			blocks.add(owner.getWeeklyTotals().get(4));
			threes.add(owner.getWeeklyTotals().get(5));
		}
		List<Double> weeklyStandings;
		List<String> weeklyStandingsFormatted;

		for (Owner owner : owners) {
			weeklyStandings = new ArrayList<Double>();
			weeklyStandingsFormatted = new ArrayList<String>();
			weeklyStandings.add(Util.calculateRank(points, owner.getWeeklyTotals().get(0)));
			weeklyStandings.add(Util.calculateRank(rebounds, owner.getWeeklyTotals().get(1)));
			weeklyStandings.add(Util.calculateRank(assists, owner.getWeeklyTotals().get(2)));
			weeklyStandings.add(Util.calculateRank(steals, owner.getWeeklyTotals().get(3)));
			weeklyStandings.add(Util.calculateRank(blocks, owner.getWeeklyTotals().get(4)));
			weeklyStandings.add(Util.calculateRank(threes, owner.getWeeklyTotals().get(5)));

			weeklyStandingsFormatted.add(Util.calculateRankFormat(points, owner.getWeeklyTotals().get(0)));
			weeklyStandingsFormatted.add(Util.calculateRankFormat(rebounds, owner.getWeeklyTotals().get(1)));
			weeklyStandingsFormatted.add(Util.calculateRankFormat(assists, owner.getWeeklyTotals().get(2)));
			weeklyStandingsFormatted.add(Util.calculateRankFormat(steals, owner.getWeeklyTotals().get(3)));
			weeklyStandingsFormatted.add(Util.calculateRankFormat(blocks, owner.getWeeklyTotals().get(4)));
			weeklyStandingsFormatted.add(Util.calculateRankFormat(threes, owner.getWeeklyTotals().get(5)));
			owner.setWeeklyStandings(weeklyStandings);
			owner.setWeeklyStandingsFormatted(weeklyStandingsFormatted);
		}

		// Get the numbers for season standings
		List<Integer> seasonPoints = new ArrayList<Integer>();
		List<Integer> seasonRebounds = new ArrayList<Integer>();
		List<Integer> seasonAssists = new ArrayList<Integer>();
		List<Integer> seasonSteals = new ArrayList<Integer>();
		List<Integer> seasonBlocks = new ArrayList<Integer>();
		List<Integer> seasonThrees = new ArrayList<Integer>();
		for (Owner owner : owners) {
			seasonPoints.add(owner.getSeasonTotals().get(0));
			seasonRebounds.add(owner.getSeasonTotals().get(1));
			seasonAssists.add(owner.getSeasonTotals().get(2));
			seasonSteals.add(owner.getSeasonTotals().get(3));
			seasonBlocks.add(owner.getSeasonTotals().get(4));
			seasonThrees.add(owner.getSeasonTotals().get(5));
		}
		List<Double> seasonStandings;
		List<String> seasonStandingsFormatted;

		for (Owner owner : owners) {
			seasonStandings = new ArrayList<Double>();
			seasonStandingsFormatted = new ArrayList<String>();
			seasonStandings.add(Util.calculateRank(seasonPoints, owner.getSeasonTotals().get(0)));
			seasonStandings.add(Util.calculateRank(seasonRebounds, owner.getSeasonTotals().get(1)));
			seasonStandings.add(Util.calculateRank(seasonAssists, owner.getSeasonTotals().get(2)));
			seasonStandings.add(Util.calculateRank(seasonSteals, owner.getSeasonTotals().get(3)));
			seasonStandings.add(Util.calculateRank(seasonBlocks, owner.getSeasonTotals().get(4)));
			seasonStandings.add(Util.calculateRank(seasonThrees, owner.getSeasonTotals().get(5)));

			seasonStandingsFormatted.add(Util.calculateRankFormat(seasonPoints, owner.getSeasonTotals().get(0)));
			seasonStandingsFormatted.add(Util.calculateRankFormat(seasonRebounds, owner.getSeasonTotals().get(1)));
			seasonStandingsFormatted.add(Util.calculateRankFormat(seasonAssists, owner.getSeasonTotals().get(2)));
			seasonStandingsFormatted.add(Util.calculateRankFormat(seasonSteals, owner.getSeasonTotals().get(3)));
			seasonStandingsFormatted.add(Util.calculateRankFormat(seasonBlocks, owner.getSeasonTotals().get(4)));
			seasonStandingsFormatted.add(Util.calculateRankFormat(seasonThrees, owner.getSeasonTotals().get(5)));
			owner.setSeasonStandings(seasonStandings);
			owner.setSeasonStandingsFormatted(seasonStandingsFormatted);
		}
		return owners;
	}

	public List<Owner> getAllNCAAOwnerPlayers() {
		List<Owner> owners = new ArrayList<Owner>();
		Connection conn = null;
		Connection conn2;
		PreparedStatement ps;
		PreparedStatement ps2;
		ResultSet rs;
		ResultSet rs2;

		try {
			conn = getConnection();
			conn2 = getConnection();
			ps = conn.prepareStatement("SELECT id, name FROM ncaa_owners");
			ps2 = conn2.prepareStatement(
					"SELECT A.playerId playerId, B.name name, B.teamName team, A.playerOrder playerOrder, B.status "
							+ "FROM ncaa_owner_player A, ncaa_players_v B WHERE ownerId = ? "
							+ "AND A.playerId = B.id ORDER BY playerOrder");
			rs = ps.executeQuery();
			while (rs.next()) {
				Owner owner = new Owner();
				owner.setId(rs.getInt("id"));
				owner.setName(rs.getString("name"));
				ps2.setInt(1, rs.getInt("id"));
				rs2 = ps2.executeQuery();
				List<Player> players = new ArrayList<Player>();
				while (rs2.next()) {
					Player player = new Player();
					player.setId(rs2.getInt("playerId"));
					if (rs2.getString("status").equals("A")) {
						player.setPlaying(true);
					} else {
						player.setPlaying(false);
					}
					player.setStatus(rs2.getString("status"));
					player.setName(rs2.getString("name"));
					player.setTeam(rs2.getString("team"));
					player.setOrder(rs2.getInt("playerOrder"));
					player.setGames(getPlayerGames(player.getId()));
					player.setOwner(owner.getName());
					players.add(player);
				}
				owner.setPlayers(players);
				owners.add(owner);
			}

			Vector<BigDecimal> ranks = new Vector<BigDecimal>();
			for (Owner owner : owners) {
				System.out.println(owner.getName());
				List<Player> players = owner.getPlayers();
				int playing = 0;
				for (int a = 0; a < players.size(); a++) {
					players.get(a).setNCAAGames(createNCAAGameList(players.get(a)));
					if (players.get(a).isPlaying()) {
						playing++;
					}
					// System.out.println(players.get(a).getName() + ": " +
					// players.get(a).getNCAAGamesTotal());
					if (players.get(a).getNCAAGames().get(0) != null) {
						System.out.println(
								players.get(a).getName() + ": " + players.get(a).getNCAAGames().get(0).getPoints());
					}
				}
				List<BigDecimal> totals = getTotals(players);
				System.out.println("Total = " + totals.get(0));
				owner.setPlayers(players);
				owner.setTotals(totals);
				owner.setNumPlayers(playing);
				ranks.add(new BigDecimal(owner.getGrandTotal()));
			}
			// Set ranks
			Collections.sort(ranks);
			Collections.reverse(ranks);
			for (int i = 0; i < owners.size(); i++) {
				for (int j = 0; j < ranks.size(); j++) {
					if (owners.get(i).getGrandTotal() == ((BigDecimal) ranks.get(j)).intValue()) {
						owners.get(i).setRank(j + 1);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return owners;
	}

	public List<Game> createNCAAGameList(Player player) {
		List<Game> games = new ArrayList<Game>();
		games.add(player.getNCAAGame("03/16/2017", "03/17/2017"));
		games.add(player.getNCAAGame("03/18/2017", "03/19/2017"));
		games.add(player.getNCAAGame("03/23/2017", "03/24/2017"));
		games.add(player.getNCAAGame("03/25/2017", "03/26/2017"));
		games.add(player.getNCAAGame("04/01/2017", "04/01/2017"));
		games.add(player.getNCAAGame("04/03/2017", "04/03/2017"));
		return games;
	}

	public List<BigDecimal> getTotals(List<Player> players) {
		List<BigDecimal> totals = new ArrayList<BigDecimal>();
		BigDecimal[] tempTotals = new BigDecimal[6];

		for (int i = 0; i < players.size(); i++) {
			List<Game> games = players.get(i).getNCAAGames();
			for (int j = 0; j < games.size(); j++) {
				if (tempTotals[j] == null) {
					tempTotals[j] = new BigDecimal(0);
				}
				if (games.get(j) != null) {
					tempTotals[j] = tempTotals[j].add(new BigDecimal(games.get(j).getNCAAGameTotal()));
				}
			}

		}
		totals.add(tempTotals[0]);
		totals.add(tempTotals[1]);
		totals.add(tempTotals[2]);
		totals.add(tempTotals[3]);
		totals.add(tempTotals[4]);
		totals.add(tempTotals[5]);
		return totals;
	}

	public String getPlayerOwner(long id, int week) {
		String owner = "FA";

		String sql = "SELECT ownerName from owner_player_v WHERE playerId = ? AND week = ?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, id, week);
		if (list.size() > 0) {
			owner = (String) list.get(0).get("ownerName");
		}
		return owner;
	}

	public void insertGame(long id, Game game) {

		if ((game.getMinutes() > 0 || game.getPoints() > 0 || game.getRebounds() > 0 || game.getAssists() > 0
				|| game.getSteals() > 0 || game.getBlocks() > 0)) {
			Connection conn = null;
			PreparedStatement ps;
			String sql = "INSERT INTO GAMES "
					+ "(playerId, date, opponent, points, rebounds, assists, steals, blocks, threes, minutes)"
					+ "VALUES (?,?,?,?,?,?,?,?,?,?)";

			try {
				conn = getConnection();
				ps = conn.prepareStatement(sql);
				ps.setLong(1, id);
				ps.setString(2, game.getDate());
				ps.setString(3, game.getOpponent());
				ps.setInt(4, game.getPoints());
				ps.setInt(5, game.getRebounds());
				ps.setInt(6, game.getAssists());
				ps.setInt(7, game.getSteals());
				ps.setInt(8, game.getBlocks());
				ps.setInt(9, game.getThrees());
				ps.setInt(10, game.getMinutes());
				ps.execute();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public void updateGame(long id, Game game) {
		Connection conn = null;
		PreparedStatement ps;
		String sql = "UPDATE GAMES set " + "opponent = ?, points = ?, rebounds = ?, assists = ?, steals = ?, "
				+ "blocks = ?, threes = ?, minutes = ? " + "WHERE playerId = ? AND date = ?";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, game.getOpponent());
			ps.setInt(2, game.getPoints());
			ps.setInt(3, game.getRebounds());
			ps.setInt(4, game.getAssists());
			ps.setInt(5, game.getSteals());
			ps.setInt(6, game.getBlocks());
			ps.setInt(7, game.getThrees());
			ps.setInt(8, game.getMinutes());
			ps.setLong(9, id);
			ps.setString(10, game.getDate());
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	public void updateNCAATeam(Team team) {
		Connection conn = null;
		PreparedStatement ps;
		String sql = "UPDATE ncaa_teams set " + "region = ?, seed = ? " + "WHERE teamId = ?";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, team.getRegion());
			ps.setString(2, team.getSeed());
			ps.setString(3, team.getId());
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	// Saves the players for an owner for a given week
	public void saveWeeklyOwners(Owner owner, String week) {
		String active;
		this.deleteOwnerPlayers(owner.getId(), Long.parseLong(week));
		for (Player player : owner.getPlayers()) {
			if (player.getOrder() < 7) {
				active = "A";
			} else {
				active = "I";
			}
			insertOwnerPlayer(owner.getId(), player.getId(), Integer.parseInt(week), active, player.getOrder());
		}
	}

	public void saveNCAAOwners(Owner owner) {
		this.deleteNCAAOwnerPlayers(owner.getId());
		for (Player player : owner.getPlayers()) {
			insertNCAAOwnerPlayer(owner.getId(), player.getId(), null, player.getOrder());
		}
	}

	public void saveNCAATeam(Team team) {
		updateNCAATeam(team);
	}

	// Copies an owner's players from one week to the next
	public void copyOwnerPlayers(int origWeek, int newWeek) {
		List<Owner> owners = this.getAllOwnerPlayers(origWeek);
		for (Owner owner : owners) {
			saveWeeklyOwners(owner, String.valueOf(newWeek));
		}
	}

	public List<Player> getOwnerPlayers(int id, int week) {
		List<Player> players = new ArrayList<Player>();
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(
					"SELECT A.playerId playerId, A.status status, B.name name, B.team team, A.playerOrder playerOrder "
							+ "FROM owner_player A, players B WHERE ownerId = ? AND week = ? "
							+ "AND A.playerId = B.id ORDER BY playerOrder");

			ps.setInt(1, id);
			ps.setInt(2, week);
			rs = ps.executeQuery();

			while (rs.next()) {
				Player player = new Player();
				player.setId(rs.getInt("playerId"));
				player.setStatus(rs.getString("status"));
				player.setName(rs.getString("name"));
				player.setTeam(rs.getString("team"));
				player.setOrder(rs.getInt("playerOrder"));
				player.setGames(getPlayerGames(player.getId()));
				player.setOwner(this.getPlayerOwner(player.getId(), week));
				players.add(player);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return players;
	}

	public List<Integer> calculateWeeklyTotal(List<Player> players, String week) {
		List<Integer> weeklyTotals = new ArrayList<Integer>();
		int points = 0;
		int rebounds = 0;
		int assists = 0;
		int steals = 0;
		int blocks = 0;
		int threes = 0;

		String begin = Util.getBeginDate(week);
		String end = Util.getEndDate(week);
		for (Player player : players) {
			List<Game> games = player.getWeekGames(begin, end, false);

			if (player.getStatus().compareTo("A") == 0) {
				points += games.get(0).getPoints() + games.get(1).getPoints();
				rebounds += games.get(0).getRebounds() + games.get(1).getRebounds();
				assists += games.get(0).getAssists() + games.get(1).getAssists();
				steals += games.get(0).getSteals() + games.get(1).getSteals();
				blocks += games.get(0).getBlocks() + games.get(1).getBlocks();
				threes += games.get(0).getThrees() + games.get(1).getThrees();
			}
		}
		weeklyTotals.add(points);
		weeklyTotals.add(rebounds);
		weeklyTotals.add(assists);
		weeklyTotals.add(steals);
		weeklyTotals.add(blocks);
		weeklyTotals.add(threes);
		return weeklyTotals;
	}

	public List<Integer> calculateSeasonTotal(Owner owner, String week) {
		List<Integer> seasonTotals = new ArrayList<Integer>();
		int points = 0;
		int rebounds = 0;
		int assists = 0;
		int steals = 0;
		int blocks = 0;
		int threes = 0;

		int weekNum = Integer.parseInt(week);

		for (int i = 0; i < weekNum; i++) {
			List<Player> players = this.getOwnerPlayers(owner.getId(), i + 1);
			List<Integer> weeklyTotals = calculateWeeklyTotal(players, String.valueOf(i + 1));
			points += weeklyTotals.get(0);
			rebounds += weeklyTotals.get(1);
			assists += weeklyTotals.get(2);
			steals += weeklyTotals.get(3);
			blocks += weeklyTotals.get(4);
			threes += weeklyTotals.get(5);
		}
		seasonTotals.add(points);
		seasonTotals.add(rebounds);
		seasonTotals.add(assists);
		seasonTotals.add(steals);
		seasonTotals.add(blocks);
		seasonTotals.add(threes);
		return seasonTotals;
	}

	public static void main(String args[]) {
		FbbRepository controller = new FbbRepository();
		System.out.println("Running main");
		// Player player = new Player();
		try {
			// controller.getAllOwnerPlayers(1);
			// controller.mockPopulateOwners();
			// controller.populateAllTeams();
			// controller.populateTeam("CLEM");
			// controller.createNCAATeamFiles();
			controller.populateAllNCAATeams();
			// controller.populateNCAATeam("12");
			/*
			 * List<Player> players = controller.getAllNCAAPlayers(); for(Player
			 * player : players){ System.out.println(player.getName() +
			 * player.avgPoints()); }
			 */
			// controller.getAllNCAAOwnerPlayers();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
