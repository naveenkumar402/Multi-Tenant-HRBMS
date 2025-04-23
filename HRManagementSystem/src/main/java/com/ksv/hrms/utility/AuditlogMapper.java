package com.ksv.hrms.utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ksv.hrms.entity.AuditLog;

@Component
public class AuditlogMapper implements RowMapper<AuditLog>{

	@Override
	public AuditLog mapRow(ResultSet rs, int rowNum) throws SQLException {
		AuditLog log=new AuditLog();
		log.setUsername(rs.getString("username"));
		log.setRole(rs.getString("role"));
		log.setMethod(rs.getString("method"));
		log.setUri(rs.getString("uri"));
		log.setStatus(rs.getInt("status"));
		log.setTimestamp(rs.getObject("timestamp",LocalDateTime.class));
		return log;
	}

}
