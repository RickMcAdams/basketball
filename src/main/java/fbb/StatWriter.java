package fbb;


import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
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
public class StatWriter {
    
    String source = null;
    
    public StatWriter(String source){
        this.source = source;
    }
           
    public void writeWeeklyStats(FileWriter writer, Owner[] owners, String week) throws IOException, ParseException {
        String begin = Util.getBeginDate(week);
        String end = Util.getEndDate(week);
        
        writer.write("<html><head><style type=\"text/css\">tr.odd {font-size:10px; background-color: #B2B2B2}"
                + "tr.out {font-size:10px; background-color: #999999}"
                + "td {font-size:10px;}</style></head>");
        writer.write("<table>");
        for (int j = 0; j < 3; j++) {
            writer.write("<tr>");
            for (int i = 0; i < 3; i++) {                
                writer.write("<td>");                
                writePlayers(owners[i + (j * 3)].getPlayers(), writer, begin, end, owners[i + (j * 3)].getName());
                writer.write("</td>");
            }
            writer.write("</tr>");
        }
        writer.write("</table>");
        writer.write("</html>");
        writer.flush();
    }

    public void writePlayers(List<Player> players, FileWriter writer, String beginDate, String endDate, String owner) throws IOException, ParseException {
        writePlayerNames(players, writer, owner);
        writePlayerStats(players, writer, beginDate, endDate);
    }

    public void writePlayerNames(List<Player> players, FileWriter writer, String owner) throws IOException {
        writer.write("<table><tr><td><table border>");
        writer.write("<tr><td><b>" + owner + "</b></td></tr>");
        for (int i = 0; i < players.size(); i++) {
            writer.write("<tr><td>" + players.get(i).getNameLink(source) + "</td></tr>");
            writer.write("<tr><td>&nbsp;</td></tr>");
        }
        writer.write("<tr><td>&nbsp;</td></tr>");
        writer.write("</table></td><td><table border>");
    }

    public void writePlayerStats(List<Player>players, FileWriter writer, String beginDate, String endDate) throws IOException, ParseException {
        writer.write("<tr><td align='center'>Pts</td><td align='center'>Reb</td><td align='center'>Ast</td><td align='center'>Stl</td><td align='center'>Blk</td><td align='center'>3s</td></tr>");

        int points = 0;
        int rebounds = 0;
        int assists = 0;
        int steals = 0;
        int blocks = 0;
        int threes = 0;

        for (int i = 0; i < players.size(); i++) {
            List<Game> games = players.get(i).getWeekGames(beginDate, endDate);
            String tag = null;
            if (i == 0 || i == 2 || i == 4) {
                tag = "<tr class='odd'>";
            } else if (i == 6 || i == 7) {
                tag = "<tr class='out'>";
            } else {
                tag = "<tr>";
            }
            writer.write(tag);
            writer.write(games.get(0).gameToStatLine());
            writer.write("</tr>");
            writer.write(tag);
            writer.write(games.get(1).gameToStatLine());
            writer.write("</tr>");
            if( i < 6){
                points += games.get(0).getPoints() + games.get(1).getPoints();
                rebounds += games.get(0).getRebounds() + games.get(1).getRebounds();
                assists += games.get(0).getAssists() + games.get(1).getAssists();
                steals += games.get(0).getSteals() + games.get(1).getSteals();
                blocks += games.get(0).getBlocks() + games.get(1).getBlocks();
                threes += games.get(0).getThrees() + games.get(1).getThrees();
            }
        }
        writer.write("<tr><td align='center'>" + points +
                     "</td><td align='center'>" + rebounds +
                     "</td><td align='center'>" + assists +
                     "</td><td align='center'>" + steals +
                     "</td><td align='center'>" + blocks +
                     "</td><td align='center'>" + threes + "</td></tr>");
        writer.write("</table></td></tr></table>");
    }
}
    
