package com.ksv.hrms.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ksv.hrms.entity.Employee;

@Component
public class UserMapper implements RowMapper<Employee>{
	
	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		Employee employee = new Employee();
		employee.setUuid(rs.getString("uuid"));
		employee.setFirstName(rs.getString("firstName"));
		employee.setLastName(rs.getString("lastName"));
		employee.setEmail(rs.getString("email"));
		employee.setMobile(rs.getLong("mobile"));
		employee.setSalary(rs.getDouble("salary"));
		employee.setPassword(rs.getString("password"));
		employee.setAuthority(rs.getString("authority"));
		employee.setUsername(rs.getString("username"));
		employee.setTenant(rs.getString("tenant"));
		employee.setDepartment(rs.getString("department"));
		employee.setRole(rs.getString("role"));
		return employee;
	}

}
