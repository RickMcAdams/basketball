package fbb;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.rick.fbb.model.Game;
import org.rick.fbb.model.Owner;
import org.rick.fbb.model.Player;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mcadamsrb
 */
public class GetStatsNCAA {

    public static final String link =  "http://espn.go.com/mens-college-basketball/player/gamelog/_/id/";
    public static final String linkYahoo = "http://rivals.yahoo.com/ncaa/basketball/players/";
    //String source = "ESPN";
    String source = "Yahoo";

    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {

        GetStatsNCAA stats = new GetStatsNCAA();
        FileWriter writer = null;
        String file = null;
        Owner[] owners = null;

        //stats.getAllPlayersListNCAA(); //Get the whole player list
        //stats.getAllPlayersListYahoo();

        if (stats.source.compareTo("ESPN") == 0) {
            writer = new FileWriter("c:\\temp\\FNCAA\\fncaa12.html");
            file = "c:\\temp\\FNCAA\\fncaa12.txt";
            owners = stats.getOwnerPlayers(file);
        } else {
            writer = new FileWriter("c:\\temp\\FNCAA\\fncaa12Yahoo.html");
            file = "c:\\temp\\FNCAA\\fncaa12Yahoo.txt";
            owners = stats.getOwnerPlayersYahoo(file);
        }
                
        stats.writeStats(writer, owners);
        writer.flush();
    }

    public void writeStats(FileWriter writer, Owner[] owners) throws IOException, ParseException {
    /*    writer.write("<html><head><style type=\"text/css\">tr.odd {font-size:10px; background-color: #B2B2B2}"
                + "tr.red {font-size:10px; background-color: rgb(255,0,0)}"
                + "tr.yellow {font-size:10px; background-color: rgb(250,192,144)}"
                + "tr.green {font-size:10px; background-color: rgb(255,255,255)}"
                + "td {font-size:13px;} td.small {font-size:10px;}</style></head>");
        // Write the summary at the top
        writer.write("<table border>");
        writer.write("<tr class='yellow'><td>Rank</td><td>&nbsp;</td><td class='small'>Players Left</td>");
        writer.write("<td class='small'>Round 1</td><td class='small'>Round 2</td><td class='small'>Round 3</td>");
        writer.write("<td class='small'>Round 4</td><td class='small'>Round 5</td><td class='small'>Round 6</td>");
        writer.write("<td><b>Total</b></td>");
        writer.write("</tr>");
        Vector ranks = new Vector();
        for(int i = 0; i < owners.length; i++){
            List<Player> players = owners[i].getPlayers();
            int playing = 0;
            for (int a = 0; a < players.size(); a++) {
                players.get(a).setNCAAGames(createNCAAGameList(players.get(a)));
                if(players.get(a).isPlaying()){
                    playing++;
                }
            }
            BigDecimal[] totals = getTotals(players);
            owners[i].setPlayers(players);
            owners[i].setTotals(totals);
            owners[i].setNumPlayers(playing);
            ranks.add(new BigDecimal(owners[i].getGrandTotal()));
        }
        // Set ranks
        Collections.sort(ranks);
        Collections.reverse(ranks);
        for(int i = 0; i < owners.length; i++){
            for(int j = 0; j < ranks.size(); j++){
                if(owners[i].getGrandTotal() == ((BigDecimal)ranks.get(j)).intValue()){
                    owners[i].setRank(j+1);
                }
            }
        }

        for (int i = 0; i < owners.length; i++) {            
            BigDecimal[] totals = owners[i].getTotals();
            
            int grandTotal = 0;
            if(i == 0|| i == 2 || i == 4 || i == 6){
                writer.write("<tr class='green'>");
            }
            else{
                writer.write("<tr class='yellow'>");
            }
            writer.write("<td align='center'>" + owners[i].getRank() + "</td>");
            writer.write("<td align='center'>" + owners[i].getName() + "</td>");
            writer.write("<td align='center'>" + owners[i].getNumPlayers() + "</td>");
            for(int j = 0; j < totals.length; j++){
                writer.write("<td align='center'>" + totals[j].intValue() + "</td>");
                grandTotal += totals[j].intValue();
            }
            writer.write("<td align='center'><b>" + grandTotal + "</b></td>");
            writer.write("</tr>");
        }
        writer.write("</table>");

        for (int i = 0; i < owners.length; i++) {
            List<Player> players = owners[i].getPlayers();            
            BigDecimal[] totals = owners[i].getTotals();

            writer.write("<table border>");
            writer.write("<tr><td align='center'><b>" + owners[i].getName());
            writer.write("</b></td>");
            for (int z = 0; z < 6; z++) {
                writer.write("<td align='center'>P</td><td align='center'>R</td><td align='center'>A</td><td align='center'>T</td>");
            }
            writer.write("<td align='center'>T</td>");
            writer.write("</tr>");

            for (int j = 0; j < players.size(); j++) {
                if(players.get(j).isPlaying()){
                    writer.write("<tr>");
                }
                else{
                    writer.write("<tr class='red'>");
                }
                writer.write("<td>" + players.get(j).getName());// + " - " + players.get(j).getTeam());
                writePlayerStats(players.get(j), writer);
                writer.write("</tr>");
            }
            //create totals            
            int grandTotal = 0;
            writer.write("<tr>");
            writer.write("<td><b>Totals</b></td>");
            for (int k = 0; k < 6; k++) {
                writer.write("<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>");
                writer.write("<td align='right'><b>" + totals[k].intValue() + "</b></td>");
                grandTotal += totals[k].intValue();
            }
            writer.write("<td>" + grandTotal + "</td>");
            writer.write("</tr>");
            writer.write("</table>");
            writer.write("<br>");
        }
        writer.write("</html>");*/
    }

    public void writePlayerStats(Player player, FileWriter writer) throws IOException, ParseException {
        List<Game> games = player.getNCAAGames();
        int total = 0;
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i) != null) {
                writer.write(games.get(i).gameToNCAAStatLine());
                total += games.get(i).getNCAAGameTotal();
            } else {
                writer.write("<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td align='center'><b>0</b></td>");
            }
        }


        writer.write("<td><b>" + total + "</b></td>");
    }

    public BigDecimal[] getTotals(List<Player> players) throws ParseException {
        BigDecimal[] totals = new BigDecimal[6];

        for (int i = 0; i < players.size(); i++) {
            List<Game> games = players.get(i).getNCAAGames();
            for (int j = 0; j < games.size(); j++) {
                if (totals[j] == null) {
                    totals[j] = new BigDecimal(0);
                }
                if (games.get(j) != null) {
                    totals[j] = totals[j].add(new BigDecimal(games.get(i).getNCAAGameTotal()));
                }
            }
        }
        return totals;
    }

    public List<Game> createNCAAGameList(Player player) throws ParseException {
        List<Game> games = new ArrayList();
        /*games[0] = player.getNCAAGame("02/14/2011", "02/17/2011");
        games[1] = player.getNCAAGame("02/18/2011", "02/20/2011");
        games[2] = player.getNCAAGame("02/21/2011", "02/24/2011");
        games[3] = player.getNCAAGame("02/25/2011", "02/27/2011");
        games[4] = player.getNCAAGame("02/28/2011", "03/03/2011");
        games[5] = player.getNCAAGame("03/04/2011", "03/06/2011");*/

        games.add(player.getNCAAGame("03/11/2016", "03/12/2016"));
        games.add(player.getNCAAGame("03/17/2016", "03/18/2016"));
        games.add(player.getNCAAGame("03/22/2016", "03/23/2016"));
        games.add(player.getNCAAGame("03/24/2016", "03/25/2016"));
        games.add(player.getNCAAGame("03/31/2016", "03/31/2016"));
        games.add(player.getNCAAGame("04/02/2016", "04/02/2016"));
        System.out.println(player.getName());
        System.out.println(games.get(0).gameToNCAAStatLine());
        return games;
    }

    public Owner[] getOwnerPlayers(String file) throws FileNotFoundException, IOException {
        Owner[] results = null;
        Player[] playerArray = null;
        ArrayList owners = new ArrayList();
        List<Player> players = new ArrayList();

        BufferedReader reader = new BufferedReader(new FileReader(file));
        for (int i = 0; i < 7; i++) {
            String text = reader.readLine();
            String name = text.substring(3, text.indexOf("-->"));
            for (int j = 0; j < 10; j++) {
                text = reader.readLine();
                String playerId = text.substring(0, 5);
                Player player = getPlayer(playerId);                                
                if(text.substring(5,6).contains("-")){
                    player.setPlaying(false);                    
                }
                else{
                    player.setPlaying(true);
                }
                players.add(player);
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

    public Owner[] getOwnerPlayersYahoo(String file) throws FileNotFoundException, IOException {
        Owner[] results = null;
        
        ArrayList owners = new ArrayList();
        List<Player> players = new ArrayList();

        BufferedReader reader = new BufferedReader(new FileReader(file));
        for (int i = 0; i < 7; i++) {
            String text = reader.readLine();
            String name = text.substring(3, text.indexOf("-->"));
            for (int j = 0; j < 10; j++) {
                text = reader.readLine();
                String playerId = text.substring(0, 6).trim();
                Player player = null;
                if(playerId.length() == 6){
                    if(playerId.substring(5, 6).contains("-")){
                        player = getPlayerYahoo(playerId.substring(0,5));                    
                    }
                    else{
                        player = getPlayerYahoo(playerId);
                    }
                }
                else{
                    player = getPlayerYahoo(playerId);
                }
                if(playerId.length()==6){
                    if(text.substring(5,6).contains("-")){
                        player.setPlaying(false);                    
                    }
                    else if(text.substring(6,7).contains("-")){
                        player.setPlaying(false);                    
                    }
                    else{
                        player.setPlaying(true);
                    }
                }                
                else{                   
                    player.setPlaying(true);                    
                }                
                players.add(player);
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

    public String getFile(String url) {
        String file = new String();
        URL u;
        InputStream is = null;
        BufferedReader reader;
        String s;
        StringBuffer result = new StringBuffer();
        try {
            u = new URL(url);
            reader = new BufferedReader(new InputStreamReader(u.openStream()));
            while ((s = reader.readLine()) != null) {
                result.append(s);// + "\n");
            }
            file = result.toString();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public String getGameLog(String playerFile) {
        int begin = playerFile.indexOf("2011-12 Game Log");
        int end = playerFile.indexOf("</table>", begin);
        return playerFile.substring(begin, end);
    }

    public String getGameLogYahoo(String playerFile) {
        int begin = playerFile.indexOf("2011-12 Season");
        int end = playerFile.indexOf("</table>", begin);
        return playerFile.substring(begin, end);
    }

     public Player getPlayer(String playerId) throws IOException {
        Player player = new Player();
        String playerFile = new String();
        String gameLog = new String();
        List<Game> games = new ArrayList();
        String url = new String(this.link);

        playerFile = getFile(url + playerId);        
        String find = "<meta property=\"og:title\" content=\"";
        int begin = playerFile.indexOf(find);
        int end = playerFile.indexOf("\"", begin + find.length());        
        player.setName(playerFile.substring(begin + find.length(), end));        
        gameLog = getGameLog(playerFile);        
        games = getGames(gameLog);
        player.setId(Integer.parseInt(playerId));
        player.setGames(games);
        //System.out.println(player.getName() + ":::" + games[0].gameToNCAAStatLine());
        return player;
    }

    public Player getPlayerYahoo(String playerId) throws IOException {
        Player player = new Player();
        String playerFile = new String();
        String gameLog = new String();
        List<Game> games = new ArrayList();
        String url = new String(this.linkYahoo);

        playerFile = getFile(url + playerId);
        String find = "<title>";
        //String find = "<li class=\"player-name\">";
        int begin = playerFile.indexOf(find);
        int end = playerFile.indexOf(" - ", begin + find.length());
        end = playerFile.indexOf(" - ", end +2);
        player.setName(playerFile.substring(begin + find.length(), end));
        //System.out.println(player.getName());
        gameLog = getGameLogYahoo(playerFile);
        games = getGamesYahoo(gameLog);
        player.setId(Integer.parseInt(playerId));
        player.setGames(games);
        return player;
    }

    public List<Game> getGames(String gameLog) {
        //System.out.println(gameLog);
        List<Game> games = new ArrayList();
        // Code to find with Championship tag
        int preStart = gameLog.indexOf("MEN'S BASKETBALL CHAMPIONSHIP");
        if(preStart < 0){
            Game game = new Game();
            game.setDate("3/14");
            games.add(game);
            return games;
        }
        int start = gameLog.indexOf("</tr>", preStart);
        int end = gameLog.indexOf("</tr>", start+7)+5;
        
        
        //int start = gameLog.indexOf("PTS</td></tr>");        
        //int end = gameLog.indexOf("</tr>", start + 15) + 5;
        
        String firstGame = gameLog.substring(start + 13, end);        
        //System.out.println(firstGame);
        games.add(parseGame(firstGame));
        start = end;
        boolean more = false;
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

    public List<Game> getGamesYahoo(String gameLog) {
        List<Game> games = new ArrayList();
        
        String startString = "PPG</td><td>&nbsp;</td></tr>";
        int start = gameLog.indexOf(startString);
        int end = gameLog.indexOf("</tr>", start + startString.length()) + 5;
        String firstGame = gameLog.substring(start + startString.length(), end);
        games.add(parseGameYahoo(firstGame));
        start = end;
        boolean more = true;
        int i = 0;
        while (more) {
            end = gameLog.indexOf("</tr>", start) + 5;
            if (end - 5 > 0) {
                String game = gameLog.substring(start, end);
                games.add(parseGameYahoo(game));
                start = end;
            } else {
                more = false;
            }
        }
        return games;
    }

    public Game parseGame(String gameString) {
        //System.out.println("----------------------------------");
        //System.out.println(gameString);
        Game game = new Game();
        // Get Date
        int dateStart = gameString.indexOf("<td");
        int dateEnd = gameString.indexOf(">", dateStart);
        dateStart = dateEnd;
        dateEnd = gameString.indexOf("</td>", dateEnd);
        game.setDate(gameString.substring(dateStart + 5, dateEnd));  
        //System.out.println(game.getDate());

        String find = new String("class=\"team-name\">");
        int oppStart = gameString.indexOf(find) + find.length();
        oppStart = gameString.indexOf("\">", oppStart) + 2;
        int oppEnd = gameString.indexOf("</a>", oppStart);
        game.setOpponent(gameString.substring(oppStart, oppEnd));       

        int ignoreStart = gameString.indexOf("</td>",oppEnd) + 5;
        ignoreStart = gameString.indexOf("</td>", ignoreStart) + 5;
        
        if (gameString.contains("Did Not Play") || gameString.contains("Cancelled") ||
                gameString.contains("Did not play or did not accumulate any stats.")) {
            return game;
        }
        // Minutes
        int start = gameString.indexOf(">", ignoreStart) +1;
        int middle = 0;
        int end = gameString.indexOf("</td>", start);
        game.setMinutes(Integer.parseInt(gameString.substring(start, end)));
        
        //FGs made, FGs att
        start = gameString.indexOf(">", end+5)+1;
        middle = gameString.indexOf("-", start);
        end = gameString.indexOf("</td>", start);
        game.setFg(Integer.parseInt(gameString.substring(start, middle)));
        game.setFga(Integer.parseInt(gameString.substring(middle+1, end)));
        //FG pct -write nothing
        start = gameString.indexOf(">", end+5) +1;
        end = gameString.indexOf("</td>", start);
        //3PM-3PA        
         start = gameString.indexOf(">", end+5)+1;
        middle = gameString.indexOf("-", start);
        end = gameString.indexOf("</td>", start);
        game.setThrees(Integer.parseInt(gameString.substring(start, middle)));
        game.setThreepa(Integer.parseInt(gameString.substring(middle+1, end)));
        //3P%
        start = gameString.indexOf(">", end+5) +1;
        end = gameString.indexOf("</td>", start);
        //FTM-FTA
            start = gameString.indexOf(">", end+5)+1;
        middle = gameString.indexOf("-", start);
        end = gameString.indexOf("</td>", start);
        game.setFtm(Integer.parseInt(gameString.substring(start, middle)));
        game.setFta(Integer.parseInt(gameString.substring(middle+1, end)));
        //FT%
        start = gameString.indexOf(">", end+5) +1;
        end = gameString.indexOf("</td>", start);
        //REB
         start = gameString.indexOf(">", end+5) +1;
        end = gameString.indexOf("</td>", start);
        game.setRebounds(Integer.parseInt(gameString.substring(start, end)));
        //AST
       start = gameString.indexOf(">", end+5) +1;
        end = gameString.indexOf("</td>", start);
        game.setAssists(Integer.parseInt(gameString.substring(start, end)));
        //BLK
        start = gameString.indexOf(">", end+5) +1;
        end = gameString.indexOf("</td>", start);
        game.setBlocks(Integer.parseInt(gameString.substring(start, end)));
        //STL
           start = gameString.indexOf(">", end+5) +1;
        end = gameString.indexOf("</td>", start);
        game.setSteals(Integer.parseInt(gameString.substring(start, end)));
        //PF
           start = gameString.indexOf(">", end+5) +1;
        end = gameString.indexOf("</td>", start);
        game.setPf(Integer.parseInt(gameString.substring(start, end)));
        //TO
           start = gameString.indexOf(">", end+5) +1;
        end = gameString.indexOf("</td>", start);
        game.setTo(Integer.parseInt(gameString.substring(start, end)));
        //PTS
     start = gameString.indexOf(">", end+5) +1;
        end = gameString.indexOf("</td>", start);
        game.setPoints(Integer.parseInt(gameString.substring(start, end)));
        //System.out.println(game.toString());
        return game;
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

        if (end < 0) { // Means game has not been played
            return game;
        }
        game.setMinutes(Integer.parseInt(gameString.substring(start + 1, end)));

        // Skip FG Header
        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setFg(Integer.parseInt(gameString.substring(start + 1, end)));

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setFga(Integer.parseInt(gameString.substring(start + 1, end)));

        //Skip FG %
        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);

        //Skip 3Pt Header
        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setThrees(Integer.parseInt(gameString.substring(start + 1, end)));

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setThreepa(Integer.parseInt(gameString.substring(start + 1, end)));

        //Skip 3Pt %
        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);

        //Skip FT Header
        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setFtm(Integer.parseInt(gameString.substring(start + 1, end)));

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setFta(Integer.parseInt(gameString.substring(start + 1, end)));

        //Skip FT %
        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);

        //Skip Rebound Header
        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setOff(Integer.parseInt(gameString.substring(start + 1, end)));

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setDef(Integer.parseInt(gameString.substring(start + 1, end)));

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setRebounds(Integer.parseInt(gameString.substring(start + 1, end)));

        //Skip Header
        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setAssists(Integer.parseInt(gameString.substring(start + 1, end)));

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setTo(Integer.parseInt(gameString.substring(start + 1, end)));

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setSteals(Integer.parseInt(gameString.substring(start + 1, end)));

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setBlocks(Integer.parseInt(gameString.substring(start + 1, end)));

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setPf(Integer.parseInt(gameString.substring(start + 1, end)));

        start = gameString.indexOf(">", end + 5);
        end = gameString.indexOf("</td>", start);
        game.setPoints(Integer.parseInt(gameString.substring(start + 1, end)));

        //System.out.println(game.toString());
        return game;
    }

    public void getAllPlayersListNCAA() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("C:\\temp\\FNCAA\\playersNCAA.txt"));
        FileWriter writer = new FileWriter("c:\\temp\\FNCAA\\playerlistNCAA.html");
        String text = reader.readLine();

        writer.write("<table>");
        while (text != null && text.length() > 5) {
            //System.out.println(text);
            Player player = getPlayer(text.substring(0, 5));            
            //writer.write(player.printNCAANames(source));
            //Player player = getPlayer(text);
            writer.write(player.printNCAAAverages(source));
            //System.out.println(player.printAverages());
            text = reader.readLine();
        }
        writer.write("</table>");
        writer.flush();
    }
}
