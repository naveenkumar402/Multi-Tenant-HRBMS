package com.ksv.hrms.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ksv.hrms.entity.Department;

@Component
public class DepartmentMapper implements RowMapper<Department>{

	@Override
	public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
		Department department=new Department();
		department.setUuid(rs.getString("uuid"));
		department.setDepartmentName(rs.getString("departmentName"));
		department.setTenant(rs.getString("tenant"));
		return department;
	}
	
}
