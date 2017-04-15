package fbb;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.rick.fbb.model.Game;
import org.rick.fbb.model.Player;
import org.rick.fbb.util.Util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class ESPNWriter {
	String source = "ESPN";

	public static final String link = "http://espn.go.com/mens-college-basketball/player/gamelog/_/id/";
	public static final String teamLink = "http://espn.go.com/mens-college-basketball/team/stats/_/id/";

	// public void getAllGamesPlayersListESPN() throws IOException{
	// //BufferedReader reader = new BufferedReader(new
	// FileReader("C:\\dev\\data\\facc\\accplayers.txt"));
	// FileWriter writer = new
	// FileWriter("c:\\dev\\data\\facc\\playerlist.html");
	// FbbController controller = new FbbController();
	// //String text = reader.readLine();
	// //System.out.println(text);
	// writer.write("<table>");
	// List<Player> players = controller.getAllPlayers();
	// for(Player player : players){
	// writer.write(player.printAverages("ESPN"));
	// }
	// writer.write("</table>");
	// writer.flush();
	// }
	//
	// public void getAllACCGamesPlayersListESPN() throws IOException {
	// //BufferedReader reader = new BufferedReader(new
	// FileReader("C:\\dev\\data\\facc\\accplayers.txt"));
	// FileWriter writer = new
	// FileWriter("c:\\dev\\data\\facc\\accplayerlist.html");
	// FbbController controller = new FbbController();
	// //String text = reader.readLine();
	// //System.out.println(text);
	// writer.write("<table>");
	// List<Player> players = controller.getAllPlayers();
	// for(Player player : players){
	// writer.write(player.printACCAverages("ESPN"));
	// }
	// writer.write("</table>");
	// writer.flush();
	// }

	/*
	 * public Owner[] getOwnerPlayers(String file) throws FileNotFoundException,
	 * IOException { Owner[] results = null;
	 * 
	 * ArrayList owners = new ArrayList(); List<Player> players = new
	 * ArrayList();
	 * 
	 * BufferedReader reader = new BufferedReader(new FileReader(file)); for
	 * (int i = 0; i < 9; i++) { String text = reader.readLine(); String name =
	 * text.substring(3, text.indexOf("-->")); for (int j = 0; j < 8; j++) {
	 * text = reader.readLine(); String playerId = text.substring(0, 6).trim();
	 * players.add(getPlayer(playerId)); }
	 * 
	 * Owner owner = new Owner(); owner.setName(name);
	 * owner.setPlayers(players); owners.add(owner); players.clear(); } results
	 * = new Owner[owners.size()]; owners.toArray(results); return results; }
	 */

	public void getTeam(String teamId) throws Exception {
		String teamFile;
		List<String> playerIds = new ArrayList<String>();

		teamFile = Util.getFile(teamLink + teamId);

		String find = "3P%</a></td>";
		int begin = teamFile.indexOf(find);
		int end = teamFile.indexOf("<td>Totals</td>", begin + find.length());

		teamFile = teamFile.substring(begin, end);
		find = "_/id/";
		begin = teamFile.indexOf(find);
		int i = 0;
		while (i < 6) {
			int beginEnd = begin + 10;
			if (teamFile.substring(begin + 5, begin + 6).contains("3")
					|| teamFile.substring(begin + 5, begin + 6).contains("4")) {
				beginEnd = begin + 12;
			}
			playerIds.add(teamFile.substring(begin + 5, beginEnd));
			System.out.println(teamFile.substring(begin + 5, beginEnd));
			teamFile = teamFile.substring(beginEnd);
			begin = teamFile.indexOf(find);
			i++;
		}

		FileWriter writer = new FileWriter("c:\\dev\\data\\fncaa\\teams\\" + teamId + ".txt");
		for (String player : playerIds) {
			writer.write(player + " #####\n");
		}
		writer.flush();
		writer.close();
	}

	public Player getPlayer(String playerId) throws Exception {
		Player player = new Player();
		String playerFile = new String();
		String gameLog = new String();
		List<Game> games = null;
		String url = new String(this.link);
		try {
			playerFile = Util.getFile(url + playerId);
		} catch (Exception e) {
			System.out.println("Try retrieving again for: " + playerId);
			playerFile = Util.getFile(url + playerId);
		}

		String find = "<meta property=\"og:title\" content=\"";
		int begin = playerFile.indexOf(find);
		int end = playerFile.indexOf("\"", begin + find.length());
		player.setName(playerFile.substring(begin + find.length(), end));

		gameLog = getGameLog(playerFile);
		games = getGames(gameLog);
		player.setId(Integer.parseInt(playerId));
		player.setGames(games);
		return player;
	}

	public String getGameLog(String playerFile) {
		int begin = playerFile.indexOf("2016-17 Game Log");
		int end = playerFile.indexOf("</table>", begin);
		return playerFile.substring(begin, end);
	}

	public List<Game> getGames(String gameLog) {
		List<Game> games = new ArrayList();
		int start = gameLog.indexOf("PTS</td></tr>");
		int end = gameLog.indexOf("</tr>", start + 15) + 5;
		String firstGame = gameLog.substring(start + 13, end);
		games.add(parseGame(firstGame));
		start = end;
		boolean more = true;
		int i = 0;
		while (more) {
			end = gameLog.indexOf("</tr>", start) + 5;
			if (end - 5 > 0) {
				String game = gameLog.substring(start, end);
				games.add(parseGame(game));
				start = end;
			} else {
				more = false;
			}
		}
		return games;
	}

	public Game parseGame(String gameString) {

		Game game = new Game();

		if (gameString.contains("colspan=\"17\"")) {
			return game;
		}
		// Get Date
		int dateStart = gameString.indexOf("<td");
		int dateEnd = gameString.indexOf(">", dateStart);
		dateStart = dateEnd;
		dateEnd = gameString.indexOf("</td>", dateEnd);
		game.setDate(gameString.substring(dateStart + 5, dateEnd));

		String find = new String("class=\"team-name\">");
		int oppStart = gameString.indexOf(find) + find.length();
		String teamTest = gameString.substring(oppStart, oppStart + 10);
		int isA = teamTest.indexOf("<a");

		if (isA >= 0) {
			oppStart = gameString.indexOf("\">", oppStart) + 2;
		}
		int oppEnd = gameString.indexOf("</li>", oppStart);
		String opp = gameString.substring(oppStart, oppEnd);

		if (opp.indexOf("</a>") > 0) {
			oppEnd = gameString.indexOf("</a>", oppStart);
		}
		game.setOpponent(gameString.substring(oppStart, oppEnd));

		int ignoreStart = gameString.indexOf("</td>", oppEnd) + 5;
		ignoreStart = gameString.indexOf("</td>", ignoreStart) + 5;

		if (gameString.contains("Did Not Play") || gameString.contains("Canceled") || gameString.contains("Postponed")
				|| gameString.contains("Did not play or did not accumulate any stats.")) {
			return game;
		}
		// Minutes
		int start = gameString.indexOf(">", ignoreStart) + 1;
		int middle = 0;
		int end = gameString.indexOf("</td>", start);
		game.setMinutes(Integer.parseInt(gameString.substring(start, end)));

		// FGs made, FGs att
		start = gameString.indexOf(">", end + 5) + 1;
		middle = gameString.indexOf("-", start);
		end = gameString.indexOf("</td>", start);
		game.setFg(Integer.parseInt(gameString.substring(start, middle)));
		game.setFga(Integer.parseInt(gameString.substring(middle + 1, end)));
		// FG pct -write nothing
		start = gameString.indexOf(">", end + 5) + 1;
		end = gameString.indexOf("</td>", start);
		// 3PM-3PA
		start = gameString.indexOf(">", end + 5) + 1;
		middle = gameString.indexOf("-", start);
		end = gameString.indexOf("</td>", start);
		game.setThrees(Integer.parseInt(gameString.substring(start, middle)));
		game.setThreepa(Integer.parseInt(gameString.substring(middle + 1, end)));
		// 3P%
		start = gameString.indexOf(">", end + 5) + 1;
		end = gameString.indexOf("</td>", start);
		// FTM-FTA
		start = gameString.indexOf(">", end + 5) + 1;
		middle = gameString.indexOf("-", start);
		end = gameString.indexOf("</td>", start);
		game.setFtm(Integer.parseInt(gameString.substring(start, middle)));
		game.setFta(Integer.parseInt(gameString.substring(middle + 1, end)));
		// FT%
		start = gameString.indexOf(">", end + 5) + 1;
		end = gameString.indexOf("</td>", start);
		// REB
		start = gameString.indexOf(">", end + 5) + 1;
		end = gameString.indexOf("</td>", start);
		game.setRebounds(Integer.parseInt(gameString.substring(start, end)));
		// AST
		start = gameString.indexOf(">", end + 5) + 1;
		end = gameString.indexOf("</td>", start);
		game.setAssists(Integer.parseInt(gameString.substring(start, end)));
		// BLK
		start = gameString.indexOf(">", end + 5) + 1;
		end = gameString.indexOf("</td>", start);
		game.setBlocks(Integer.parseInt(gameString.substring(start, end)));
		// STL
		start = gameString.indexOf(">", end + 5) + 1;
		end = gameString.indexOf("</td>", start);
		game.setSteals(Integer.parseInt(gameString.substring(start, end)));
		// PF
		start = gameString.indexOf(">", end + 5) + 1;
		end = gameString.indexOf("</td>", start);
		game.setPf(Integer.parseInt(gameString.substring(start, end)));
		// TO
		start = gameString.indexOf(">", end + 5) + 1;
		end = gameString.indexOf("</td>", start);
		game.setTo(Integer.parseInt(gameString.substring(start, end)));
		// PTS
		start = gameString.indexOf(">", end + 5) + 1;
		end = gameString.indexOf("</td>", start);
		game.setPoints(Integer.parseInt(gameString.substring(start, end)));
		// System.out.println(game.toString());
		return game;
	}

	public static void main(String args[]) {
		ESPNWriter writer = new ESPNWriter();
		try {
			// writer.getTeam("2305");
			// writer.getAllGamesPlayersListESPN();
			writer.getPlayer("46173");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
