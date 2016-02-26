package org.rick.fbb;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.rick.fbb.model.Owner;
import org.rick.fbb.model.OwnerJson;
import org.rick.fbb.model.Player;
import org.rick.fbb.model.PlayerJson;
import org.rick.fbb.repository.FbbRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * Handles requests for the application home page.
 */
@RestController
public class WebController {
	
	private static final Logger logger = LoggerFactory.getLogger(WebController.class);
	
	@Inject
	FbbRepository fbbRepository;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/")//, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "Welcome to the Fantasy ACC Basketball app";
	}
	
	@RequestMapping(value="/fncaa", method = RequestMethod.GET)
	public String fncaaHome(Model model){
		return "fncaa";
	}
	
	@RequestMapping(value="/fncaaStats", method = RequestMethod.GET)
	public String fncaaStats(Model model){
		List<Owner> owners = fbbRepository.getAllNCAAOwnerPlayers();
		model.addAttribute("owners",owners);
		return "fncaaStats";
	}
	
	@RequestMapping(value="/weeklyStats", method = RequestMethod.GET)
	public String weeklyStats(Model model, @RequestParam String week){
		List<Owner> owners = fbbRepository.getAllOwnerPlayers(Integer.parseInt(week));		
		model.addAttribute("week", week);
		model.addAttribute("owners", owners);
		return "weeklyStats";
	}
	
	@RequestMapping(value="/seasonStats", method = RequestMethod.GET, produces = "application/json")
	public List<PlayerJson> seasonStats(){
		List<Player> players = fbbRepository.getAllPlayers();
		List<PlayerJson> playerList = new ArrayList<PlayerJson>();
		for(Player player : players){
			playerList.add(player.getPlayerJson());			
		}
		return playerList;
	}
	
	@RequestMapping(value="/accStats", method = RequestMethod.GET, produces = "application/json")
	public List<PlayerJson> accStats(){	
		List<Player> players = fbbRepository.getAllPlayers();
		List<PlayerJson> playerList = new ArrayList<PlayerJson>();
		for(Player player : players){
			playerList.add(player.getACCPlayerJson());			
		}
		return playerList;
	}
	
	@RequestMapping(value="/ncaaStats", method = RequestMethod.GET, produces = "application/json")
	public List<PlayerJson> ncaaStats(){
		List<Player> players = fbbRepository.getAllNCAAPlayers();
		List<PlayerJson> playerList = new ArrayList<PlayerJson>();
		for(Player player : players){
			playerList.add(player.getPlayerJson());			
		}
		return playerList;
	}
	
	@RequestMapping(value="/ncaaStandings", method = RequestMethod.GET, produces = "application/json")
	public List<OwnerJson> ncaaStandings(){
		List<Owner> owners = fbbRepository.getAllNCAAOwnerPlayers();
		List<OwnerJson> ownerList = new ArrayList<OwnerJson>();
		for(Owner owner : owners){
			ownerList.add(owner.getOwnerJson());			
		}
		return ownerList;
	}
	
	
}
