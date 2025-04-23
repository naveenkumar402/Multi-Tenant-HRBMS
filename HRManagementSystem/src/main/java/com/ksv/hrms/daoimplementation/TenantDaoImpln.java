package com.ksv.hrms.daoimplementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.ksv.hrms.dao.TenantDao;
import com.ksv.hrms.entity.Tenant;
import com.ksv.hrms.exception.NoDataFoundException;
import com.ksv.hrms.utility.TenantMapper;

@Component
public class TenantDaoImpln implements TenantDao{
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private TenantMapper tenantMapper;
	
	@Override
	public int save(Tenant tenant) {
		String sql="insert into tenant(uuid,tenantName,tenantLocation) values (:uuid, :name, :location)";
		Map<String, Object> param=new HashMap<>();
		param.put("uuid", UUID.randomUUID().toString());
		param.put("name",tenant.getTenantName());
		param.put("location", tenant.getTenantLocation());
		return namedParameterJdbcTemplate.update(sql, param);
	}

	@Override
	public boolean isPresent(Tenant tenant) {
		String sql="select count(*) from tenant where tenantName like :name and tenantLocation like :location";
		Map<String , Object> param=new HashMap<>();
		param.put("name", tenant.getTenantName());
		param.put("location", tenant.getTenantLocation());
		int count=namedParameterJdbcTemplate.queryForObject(sql, param, Integer.class);
		if(count==0) {
			return false;
		}
		return true;
	}

	@Override
	public List<Tenant> findAllTenants() {
		String sql="select * from tenant";
		return jdbcTemplate.query(sql, tenantMapper);
	}

	@Override
	public Tenant findById(String uuid) {
		String sql="select * from tenant where uuid= :uuid";
		Map<String,Object> param=new HashMap<>();
		param.put("uuid",uuid);
		try {
			return namedParameterJdbcTemplate.queryForObject(sql, param, tenantMapper);
		}catch (EmptyResultDataAccessException e) {
			throw new NoDataFoundException("No tenant exists with this id");
		}
	}

	@Override
	public int update(Tenant tenant) {
		String sql="update tenant set tenantName= :name,tenantLocation=:location where uuid= :uuid";
		Map<String ,Object> param=new HashMap<>();
		param.put("name", tenant.getTenantName());
		param.put("location" , tenant.getTenantLocation());
		param.put("uuid",tenant.getUuid());
		return namedParameterJdbcTemplate.update(sql, param);
	}

	@Override
	public int delete(String uuid) {
		String sql="delete from tenant where uuid=:uuid";
		 Map<String, Object> param=new HashMap<>();
		 param.put("uuid", uuid);
		 int count=namedParameterJdbcTemplate.update(sql, param);
		 return count;	 
	}

}
