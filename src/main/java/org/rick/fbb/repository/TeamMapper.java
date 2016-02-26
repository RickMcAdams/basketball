package org.rick.fbb.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.rick.fbb.model.Team;
import org.springframework.jdbc.core.RowMapper;

public class TeamMapper implements RowMapper<Team> {

	@Override
	public Team mapRow(ResultSet rs, int i) throws SQLException {
		Team team = new Team();
        team.setId(rs.getString("teamId"));
        team.setName(rs.getString("name"));
        team.setRegion(rs.getString("region"));
        team.setSeed(rs.getString("seed"));
		return team;
	}

}
