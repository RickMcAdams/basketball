package org.rick.fbb.model;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.rick.fbb.util.Util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mcadamsrb
 */
public class Game {
    private int points;
    private int rebounds;
    private int assists;
    private int steals;
    private int blocks;
    private int threes;
    private int minutes;
    private String date;
    private int fg;
    private int fga;
    private int ftm;
    private int fta;
    private int threepa;
    private int off;
    private int def;
    private int to;
    private int pf;
    private String opponent;



    public String toString(){
        return "Date: " + this.getDate() +
                " Opponent: " + this.getOpponent() +
                " Minutes: " + this.getMinutes() +
                " Points: " + this.getPoints() +
                " Rebounds:" + this.getRebounds() +
                " Assists:" + this.getAssists() +
                " Steals:" + this.getSteals() +
                " Blocks: " + this.getBlocks() +
                " Threes: " + this.getThrees();
    }

    public String gameToStatLine(){
        return "<td align='center'>" + this.getPoints() + "</td><td align='center'>" + this.getRebounds()+
                "</td><td align='center'>" + this.getAssists()+ "</td><td align='center'>" + this.getSteals() +
                "</td><td align='center'>" + this.getBlocks()+ "</td><td align='center'>" + this.getThrees()+ "</td>";
    }

    public String gameToNCAAStatLine(){
        int total = this.getPoints() + this.getRebounds() + this.getAssists();
        return "<td class='stat'>" + this.getPoints() + "</td><td class='stat'>" + this.getRebounds()+
                "</td><td class='stat'>" + this.getAssists()+ "</td><td class='statTotal'><b>" + total + "</b></td>";
    }

    /**
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * @return the rebounds
     */
    public int getRebounds() {
        return rebounds;
    }

    /**
     * @param rebounds the rebounds to set
     */
    public void setRebounds(int rebounds) {
        this.rebounds = rebounds;
    }

    /**
     * @return the assists
     */
    public int getAssists() {
        return assists;
    }

    /**
     * @param assists the assists to set
     */
    public void setAssists(int assists) {
        this.assists = assists;
    }

    /**
     * @return the steals
     */
    public int getSteals() {
        return steals;
    }

    /**
     * @param steals the steals to set
     */
    public void setSteals(int steals) {
        this.steals = steals;
    }

    /**
     * @return the blocks
     */
    public int getBlocks() {
        return blocks;
    }

    /**
     * @param blocks the blocks to set
     */
    public void setBlocks(int blocks) {
        this.blocks = blocks;
    }

    /**
     * @return the threes
     */
    public int getThrees() {
        return threes;
    }

    /**
     * @param threes the threes to set
     */
    public void setThrees(int threes) {
        this.threes = threes;
    }

    /**
     * @return the minutes
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * @param minutes the minutes to set
     */
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
      int begin = date.indexOf("/");
	  String month = date.substring(0, begin);
	  String day = date.substring(begin+1);

	  if(day.length() == 1){
		day = "0" + day;
	  }
	  this.date = month + "/" + day;
    }

    /**
     * @return the fg
     */
    public int getFg() {
        return fg;
    }

    /**
     * @param fg the fg to set
     */
    public void setFg(int fg) {
        this.fg = fg;
    }

    /**
     * @return the fga
     */
    public int getFga() {
        return fga;
    }

    /**
     * @param fga the fga to set
     */
    public void setFga(int fga) {
        this.fga = fga;
    }

    /**
     * @return the ftm
     */
    public int getFtm() {
        return ftm;
    }

    /**
     * @param ftm the ftm to set
     */
    public void setFtm(int ftm) {
        this.ftm = ftm;
    }

    /**
     * @return the fta
     */
    public int getFta() {
        return fta;
    }

    /**
     * @param fta the fta to set
     */
    public void setFta(int fta) {
        this.fta = fta;
    }

    /**
     * @return the threepa
     */
    public int getThreepa() {
        return threepa;
    }

    /**
     * @param threepa the threepa to set
     */
    public void setThreepa(int threepa) {
        this.threepa = threepa;
    }

    /**
     * @return the off
     */
    public int getOff() {
        return off;
    }

    /**
     * @param off the off to set
     */
    public void setOff(int off) {
        this.off = off;
    }

    /**
     * @return the def
     */
    public int getDef() {
        return def;
    }

    /**
     * @param def the def to set
     */
    public void setDef(int def) {
        this.def = def;
    }

    /**
     * @return the to
     */
    public int getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(int to) {
        this.to = to;
    }

    /**
     * @return the pf
     */
    public int getPf() {
        return pf;
    }

    /**
     * @param pf the pf to set
     */
    public void setPf(int pf) {
        this.pf = pf;
    }

    /**
     * @return the opponent
     */
    public String getOpponent() {
        return opponent;
    }

    /**
     * @param opponent the opponent to set
     */
    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public Calendar getDateAsDate(){
        String dateString = this.date;
        Calendar cal = Calendar.getInstance();
        int month = 0;
        int day = 0;
        if(dateString.indexOf("/") == -1){ // Yahoo date
            String monthString = dateString.substring(0,3);
            if(monthString.compareTo("Nov") == 0){
                month = 11;
            }
            else if(monthString.compareTo("Dec") == 0){
                month = 12;
            }
            else if(monthString.compareTo("Jan") == 0){
                month = 1;
            }
            else if(monthString.compareTo("Feb") == 0){
                month = 2;
            }
            else if(monthString.compareTo("Mar") == 0){
                month = 3;
            }
            else if(monthString.compareTo("Apr") == 0){
                month = 4;
            }
            String dayString = dateString.substring(dateString.indexOf(" ") + 1);
            day = Integer.parseInt(dayString.trim());
        }
        else{
            month = Integer.parseInt(dateString.substring(0,dateString.indexOf("/")));
            day = Integer.parseInt(dateString.substring(dateString.indexOf("/") + 1));
        }

        int year = Util.getCurrentYear();
        if(month > 10){
            year = Util.getCurrentYear()-1;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/y");
        String date = month + "/" + day + "/" + year;
        try{
        cal.setTime(sdf.parse(date));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return cal;
    }

    public int getNCAAGameTotal(){
        return this.getPoints() + this.getRebounds() + this.getAssists();
    }
    
    public GameJson getGameJson(){
    	GameJson gameJson = new GameJson();
    	gameJson.setPoints(points);
    	gameJson.setRebounds(rebounds);
    	gameJson.setAssists(assists);
    	return gameJson;
    }
}
