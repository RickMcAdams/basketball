package org.rick.fbb.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.rick.fbb.model.Owner;
import org.springframework.jdbc.core.RowMapper;

public class OwnerMapper implements RowMapper<Owner> {

	@Override
	public Owner mapRow(ResultSet rs, int i) throws SQLException {
		Owner owner = new Owner();
		owner.setId(rs.getInt("id"));
        owner.setName(rs.getString("name"));
		return owner;
	}

}
