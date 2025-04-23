package com.ksv.hrms.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ksv.hrms.entity.Authority;

@Component
public class AuthMapper implements RowMapper<Authority>{
	
	@Override
	public Authority mapRow(ResultSet rs, int rowNum) throws SQLException {
		Authority role= new Authority();
		role.setUuid(rs.getString("uuid"));
		role.setRoleName(rs.getString("roleName"));
		return role;
	}

}
