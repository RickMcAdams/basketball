package org.rick.fbb.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.rick.fbb.model.Player;
import org.springframework.jdbc.core.RowMapper;

public class PlayerMapper implements RowMapper<Player> {
	public Player mapRow(ResultSet rs, int i) throws SQLException{
		Player player = new Player();
		 player.setId(rs.getLong("id"));
         player.setName(rs.getString("name"));
         player.setTeam(rs.getString("team"));		 
		return player;
	}
}
