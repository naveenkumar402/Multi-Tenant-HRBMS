package com.ksv.hrms.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ksv.hrms.entity.Tenant;

@Component
public class TenantMapper implements RowMapper<Tenant>{

	@Override
	public Tenant mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tenant tenant=new Tenant();
		tenant.setUuid(rs.getString("uuid"));
		tenant.setTenantName(rs.getString("tenantName"));
		tenant.setTenantLocation(rs.getString("tenantLocation"));
		return tenant;
	}

}
