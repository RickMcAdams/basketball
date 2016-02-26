package org.rick.fbb.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.rick.fbb.model.Game;
import org.springframework.jdbc.core.RowMapper;

public class GameMapper implements RowMapper<Game> {

	@Override
	public Game mapRow(ResultSet rs, int i) throws SQLException {
		Game game = new Game();
		  game.setDate(rs.getString("date"));
          game.setOpponent(rs.getString("opponent"));
          game.setMinutes(rs.getInt("minutes"));
          game.setPoints(rs.getInt("points"));
          game.setRebounds(rs.getInt("rebounds"));
          game.setAssists(rs.getInt("assists"));
          game.setSteals(rs.getInt("steals"));
          game.setBlocks(rs.getInt("blocks"));
          game.setThrees(rs.getInt("threes"));
		return game;
	}

}
