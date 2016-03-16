package org.rick.fbb.util;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.rick.fbb.model.Game;
import org.rick.fbb.model.Owner;
import org.rick.fbb.model.Player;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class Util {
    public static String getFile(String url) throws Exception{
        String file = new String();
        URL u;
        URLConnection connection;        
        BufferedReader reader;
        String s;
        StringBuffer result = new StringBuffer();
        try {
            u = new URL(url);
            connection = u.openConnection();
            connection.setUseCaches(false);
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((s = reader.readLine()) != null) {
                result.append(s);
            }
            file = result.toString();
            reader.close();
        } catch (Exception e) {
        	System.out.println("Error retrieving: " + url);
        	throw e;
        }
        return file;
    }

    public static int getCurrentYear(){
    	return 2016;
    }
    
    public static String getBeginDate(String week){
        if(week.compareTo("1") == 0){
          return "12/2/2013";
        }
        else if(week.compareTo("2") == 0){
          return "1/6/2014";
        }
        else if(week.compareTo("3") == 0){
            return "1/13/2014";
        }
        else if(week.compareTo("4") == 0){
            return "1/20/2014";
        }
        else if(week.compareTo("5") == 0){
            return "1/27/2014";
        }
        else if(week.compareTo("6") == 0){
            return "2/3/2014";
        }
        else if(week.compareTo("7") == 0){
            return "2/10/2014";
        }
        else if(week.compareTo("8") == 0){
            return "2/17/2014";
        }
        else if(week.compareTo("9") == 0){
            return "2/24/2014";
        }
		else if(week.compareTo("10") == 0){
            return "3/3/2014";
        }
        return "";
    }

    public static String getEndDate(String week){
        if(week.compareTo("1") == 0){
          return "1/5/2014";
        }
        else if(week.compareTo("2") == 0){
          return "1/12/2014";
        }
        else if(week.compareTo("3") == 0){
            return "1/19/2014";
        }
        else if(week.compareTo("4") == 0){
            return "1/26/2014";
        }
        else if(week.compareTo("5") == 0){
            return "2/2/2014";
        }
        else if(week.compareTo("6") == 0){
            return "2/9/2014";
        }
        else if(week.compareTo("7") == 0){
            return "2/16/2014";
        }
        else if(week.compareTo("8") == 0){
            return "2/23/2014";
        }
        else if(week.compareTo("9") == 0){
            return "3/2/2014";
        }
		else if(week.compareTo("10") == 0){
            return "3/9/2014";
        }
        return "";
    }

	public static double calculateRank(List<Integer> origNumbers, Integer origNumber){
	  double total = 0.0;
	  List<Integer> numbers = (ArrayList)((ArrayList)origNumbers).clone();
	  Collections.sort(numbers);
	  double rank = 0.0;
	  for(Integer integer: numbers){
		rank++;
		if(origNumber.equals(integer)){
		  //System.out.println(integer.toString());
		  if(total == 0){
			total = rank;
		  }
		  else{
			total = total + 0.5;
		  }
		}
	  }
	  return total;
	}
	
	public static double calculateRank(List<Double> origNumbers, Double origNumber){
		  double total = 0.0;
		  List<Double> numbers = (ArrayList)((ArrayList)origNumbers).clone();
		  Collections.sort(numbers);
		  double rank = 0.0;
		  for(Double integer: numbers){
			rank++;
			if(origNumber.equals(integer)){
			  //System.out.println(integer.toString());
			  if(total == 0){
				total = rank;
			  }
			  else{
				total = total + 0.5;
			  }
			}
		  }
		  return total;
		}

	public static String calculateRankFormat(List<Integer> origNumbers, Integer origNumber){
	  double total = 0.0;
	  List<Integer> numbers = (ArrayList)((ArrayList)origNumbers).clone();
	  Collections.sort(numbers);
	  double rank = 0.0;
	  for(Integer integer: numbers){
		rank++;
		if(origNumber.equals(integer)){
		  //System.out.println(integer.toString());
		  if(total == 0){
			total = rank;
		  }
		  else{
			total = total + 0.5;
		  }
		}
	  }
	  if (total % 1.0 > 0){
// We have decimal part.
		return Double.toString(total);
	  }
	  else{
		Double num = new Double(total);
		return Integer.toString(num.intValue());
	  }
	}

	public static List<String> getAllTeams(){
	  List<String> teams = new ArrayList();
	  teams.add("BC");
	  teams.add("CLEM");
	  teams.add("DUKE");
	  teams.add("FSU");
	  teams.add("GT");
	  teams.add("LOU");
	  teams.add("MIA");
	  teams.add("NCS");
	  teams.add("ND");
	  teams.add("PITT");
	  teams.add("SYR");
	  teams.add("UNC");
	  teams.add("UVA");
	  teams.add("VT");
	  teams.add("WF");
	  return teams;
	}

	public static int getWeek(){	  
		return 1;
	}         
        
	public static void main(String args[]){
	  Util util = new Util();
	  List<Integer> numbers = new ArrayList();
	  numbers.add(70);
	  numbers.add(30);
	  numbers.add(70);
	  numbers.add(10);
	  numbers.add(20);
	  numbers.add(70);
	  numbers.add(80);
	  numbers.add(70);
	  numbers.add(90);
	  System.out.println(util.calculateRank(numbers, 70));

	  for(Integer integer : numbers){
		System.out.println(integer.toString());
	  }
	}
}
