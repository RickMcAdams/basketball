package fbb;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.rick.fbb.model.Game;
import org.rick.fbb.model.Owner;
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
public class YahooWriter {
    String source = "Yahoo";
    public static final String linkYahoo = "http://rivals.yahoo.com/ncaa/basketball/players/";

    public void getAllGamesPlayersListYahoo() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("C:\\dev\\data\\FACC\\playersYahoo.txt"));
        FileWriter writer = new FileWriter("c:\\dev\\data\\FACC\\playerlistYahoo.html");
        String text = reader.readLine();

        writer.write("<table>");
        while (text != null) {
            Player player = getPlayerYahoo(text);
            writer.write(player.printAverages(source));
            text = reader.readLine();
        }
        writer.write("</table>");
        writer.flush();
    }



    public void getAllACCGamesPlayersListYahoo() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("C:\\dev\\data\\facc\\accplayersYahoo.txt"));
        FileWriter writer = new FileWriter("c:\\dev\\data\\facc\\accplayerlistYahoo.html");
        String text = reader.readLine();

        writer.write("<table>");
        while (text != null) {
            Player player = getPlayerYahoo(text);
            writer.write(player.printACCAverages("Yahoo"));
            text = reader.readLine();
        }
        writer.write("</table>");
        writer.flush();
    }


    public Owner[] getOwnerPlayersYahoo(String file) throws FileNotFoundException, IOException {
        Owner[] results = null;
        ArrayList owners = new ArrayList();
        List<Player> players = new ArrayList();

        BufferedReader reader = new BufferedReader(new FileReader(file));

        for (int i = 0; i < 9; i++) {
            String text = reader.readLine();
            String name = text.substring(3, text.indexOf("-->"));
            for (int j = 0; j < 8; j++) {
                text = reader.readLine();
                String playerId = text.substring(0, 6).trim();
                players.add(getPlayerYahoo(playerId));
            }
            Owner owner = new Owner();
            owner.setName(name);
            owner.setPlayers(players);
            owners.add(owner);
            players.clear();
        }
        results = new Owner[owners.size()];
        owners.toArray(results);
        return results;
    }

    public String getGameLogYahoo(String playerFile) {
        int begin = playerFile.indexOf("2012-13 Season");
        int end = playerFile.indexOf("</table>", begin);
        return playerFile.substring(begin, end);
    }

    public Player getPlayerYahoo(String playerId) throws IOException {
        Player player = new Player();
        String playerFile = new String();
        String gameLog = new String();
        List<Game> games = new ArrayList();
        String url = new String(this.linkYahoo);

        playerFile = "";//Util.getFile(url + playerId);
        String find = "<li class=\"player-name\" idev\\datarop=\"name\">";
        int begin = playerFile.indexOf(find);
        int end = playerFile.indexOf("<", begin + find.length());
        player.setName(playerFile.substring(begin + find.length(), end));
        gameLog = getGameLogYahoo(playerFile);
        //games = getGamesYahoo(gameLog);
        player.setId(Integer.parseInt(playerId));
        player.setGames(games);
        return player;
    }

    public Game[] getGamesYahoo(String gameLog) {
        Game[] games = null;
        ArrayList gameArray = new ArrayList();
        String startString = "PPG</td><td>&nbsp;</td></tr>";
        int start = gameLog.indexOf(startString);
        int end = gameLog.indexOf("</tr>", start + startString.length()) + 5;
        String firstGame = gameLog.substring(start + startString.length(), end);
        gameArray.add(parseGameYahoo(firstGame));
        start = end;
        boolean more = true;
        int i = 0;
        while (more) {
            end = gameLog.indexOf("</tr>", start) + 5;
            if (end - 5 > 0) {
                String game = gameLog.substring(start, end);
                gameArray.add(parseGameYahoo(game));
                start = end;
            } else {
                more = false;
            }
        }
        games = new Game[gameArray.size()];
        gameArray.toArray(games);
        return games;
    }

    public Game parseGameYahoo(String gameString) {
        Game game = new Game();
        // Get Date
        int dateStart = gameString.indexOf("<td");
        int dateEnd = gameString.indexOf(">", dateStart);
        dateStart = dateEnd;
        dateEnd = gameString.indexOf("</td>", dateEnd);
        game.setDate(gameString.substring(dateStart + 1, dateEnd));

        String find = new String("<a href=\"");
        int oppStart = gameString.indexOf(find) + find.length();
        oppStart = gameString.indexOf("\">", oppStart) + 2;
        int oppEnd = gameString.indexOf("</a>", oppStart);
        game.setOpponent(gameString.substring(oppStart, oppEnd));

        int ignoreStart = gameString.indexOf("</td>") + 5;
        ignoreStart = gameString.indexOf("</td>", ignoreStart) + 5;
        ignoreStart = gameString.indexOf("</td>", ignoreStart) + 5;
        ignoreStart = gameString.indexOf("</td>", ignoreStart) + 5;

        if (gameString.contains("Did Not Play") || gameString.contains("Cancelled")) {
            return game;
        }

        int start = gameString.indexOf(">", ignoreStart);
        int end = gameString.indexOf("</td>", start);

        if(end < 0){ // Means game has not been played
            return game;
        }
        game.setMinutes(Integer.parseInt(gameString.substring(start+1, end)));

        // Skip FG Header
        start = gameString.indexOf(">", end+5);
        end = gameString.indexOf("</td>", start);

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setFg(Integer.parseInt(gameString.substring(start+1, end)));

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setFga(Integer.parseInt(gameString.substring(start+1, end)));

        //Skip FG %
        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);

        //Skip 3Pt Header
        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setThrees(Integer.parseInt(gameString.substring(start+1, end)));

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setThreepa(Integer.parseInt(gameString.substring(start+1, end)));

        //Skip 3Pt %
        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);

        //Skip FT Header
        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setFtm(Integer.parseInt(gameString.substring(start+1, end)));

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setFta(Integer.parseInt(gameString.substring(start+1, end)));

        //Skip FT %
        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);

        //Skip Rebound Header
        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setOff(Integer.parseInt(gameString.substring(start+1, end)));

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setDef(Integer.parseInt(gameString.substring(start+1, end)));

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setRebounds(Integer.parseInt(gameString.substring(start+1, end)));

        //Skip Header
        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setAssists(Integer.parseInt(gameString.substring(start+1, end)));

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setTo(Integer.parseInt(gameString.substring(start+1, end)));

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setSteals(Integer.parseInt(gameString.substring(start+1, end)));

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setBlocks(Integer.parseInt(gameString.substring(start+1, end)));

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setPf(Integer.parseInt(gameString.substring(start+1, end)));

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setPoints(Integer.parseInt(gameString.substring(start+1, end)));

        //System.out.println(game.toString());
        return game;
    }

}
