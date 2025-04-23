package com.ksv.hrms.daoimplementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.ksv.hrms.dao.AuditlogDao;
import com.ksv.hrms.entity.AuditLog;
import com.ksv.hrms.utility.AuditlogMapper;

@Component
public class AuditLogImpln implements AuditlogDao{
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AuditlogMapper logMapper;
	@Override
	public int save(AuditLog auditLog) {
		String sql="insert into auditlog (username,method,status,uri,role,timestamp)"
				+ "values(:username,:method,:status,:uri,:role,:timestamp)";
		Map<String, Object> param=new HashMap<>();
		param.put("username",auditLog.getUsername());
		param.put("method", auditLog.getMethod());
		param.put("status", auditLog.getStatus());
		param.put("uri", auditLog.getUri());
		param.put("role", auditLog.getRole());
		param.put("timestamp", auditLog.getTimestamp());
		return namedParameterJdbcTemplate.update(sql, param);
	}
	@Override
	public List<AuditLog> getAuditlog() {
		String sql="select * from auditlog";
		return jdbcTemplate.query(sql, logMapper);
	}
	
}
