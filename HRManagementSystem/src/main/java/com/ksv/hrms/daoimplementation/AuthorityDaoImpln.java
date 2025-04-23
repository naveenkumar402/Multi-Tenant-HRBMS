package com.ksv.hrms.daoimplementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.ksv.hrms.dao.AuthorityDao;
import com.ksv.hrms.entity.Authority;
import com.ksv.hrms.exception.NoDataFoundException;
import com.ksv.hrms.utility.AuthMapper;

@Component
public class AuthorityDaoImpln implements AuthorityDao{
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private AuthMapper roleMapper;
	
	@Override
	public boolean isPresent(Authority authority) {
		Map<String,Object> param=new HashMap<>();
		String sql="select count(*) from authority where roleName= :name";
		
		int count=namedParameterJdbcTemplate.queryForObject(sql, param, Integer.class);
		if(count==0) {
			return false;
		}
		return true;
	}

	

	@Override
	public String getRoleNameById(String uuid) {
		String sql="Select roleName from authority where uuid= :uuid";
		Map<String, Object> param=new HashMap<>();
		param.put("uuid", uuid);
		try {
			return namedParameterJdbcTemplate.queryForObject(sql, param, String.class);
		}
		catch (EmptyResultDataAccessException e) {
			throw new NoDataFoundException("There is no authorized roles with this id");
		}
		
	}

	@Override
	public List<Authority> getAllRole() {
		String sql="select * from authority where roleName not like 'admin'";
		try {
			return namedParameterJdbcTemplate.query(sql, roleMapper);
		}
		catch (EmptyResultDataAccessException e) {
			throw new NoDataFoundException("There is no authorized roles available");
		}
		
	}

	@Override
	public Authority getAuthById(String uuid) {
		String sql="select * from authority where uuid= :uuid";
		Map<String, Object> param=new HashMap<>();
		param.put("uuid", uuid);
		try {
			return namedParameterJdbcTemplate.queryForObject(sql, param, roleMapper);
		}
		catch(EmptyResultDataAccessException e) {
			throw new NoDataFoundException("There is no authorized roles with this id");
		}
	}
	
	
}
