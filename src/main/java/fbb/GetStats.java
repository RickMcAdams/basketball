package fbb;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.rick.fbb.model.Owner;

/*
 * To change this dev\\datalate, choose Tools | Templates
 * and open the dev\\datalate in the editor.
 */
/**
 *
 * @author mcadamsrb
 */
public class GetStats {
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        String source = "ESPN";
        //String source = "Yahoo";
        String week = "week1";

        GetStats stats = new GetStats();
        StatWriter statWriter = new StatWriter(source);
        FileWriter writer = null;
        String file = null;
        Owner[] owners = null;


//        stats.getAllGamesPlayersList(source); //Get the whole player list
        //stats.getAllACCGamesPlayersList(source);

       /* if(source.compareTo("ESPN")==0){
            ESPNWriter espnWriter = new ESPNWriter();
            writer = new FileWriter("c:\\dev\\data\\FACC\\" + week + ".html");
            file = "c:\\dev\\data\\FACC\\" + week + ".txt";
            owners = espnWriter.getOwnerPlayers(file);
        }
        else{
            YahooWriter yahooWriter = new YahooWriter();
            writer = new FileWriter("c:\\dev\\data\\FACC\\" + week + "Yahoo.html");
            file = "c:\\dev\\data\\FACC\\" + week + "Yahoo.txt";
            owners = yahooWriter.getOwnerPlayersYahoo(file);
        }

        statWriter.writeWeeklyStats(writer, owners, week);
        */
    }

    /*public void getAllGamesPlayersList(String source) throws FileNotFoundException, IOException{
        if(source.compareTo("ESPN")==0){
            ESPNWriter espnWriter = new ESPNWriter();
            espnWriter.getAllGamesPlayersListESPN();
        }
        else{
            YahooWriter yahooWriter = new YahooWriter();
            yahooWriter.getAllGamesPlayersListYahoo();
        }
    }

    public void getAllACCGamesPlayersList(String source)throws FileNotFoundException, IOException{
        if(source.compareTo("ESPN")==0){
            ESPNWriter espnWriter = new ESPNWriter();
            espnWriter.getAllACCGamesPlayersListESPN();
        }
        else{
            YahooWriter yahooWriter = new YahooWriter();
            yahooWriter.getAllACCGamesPlayersListYahoo();
        }
    }*/

}
