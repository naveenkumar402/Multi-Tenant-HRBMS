package com.ksv.hrms.utility;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
@Component
public class EmployeeMapper implements RowMapper<Map<String,Object>>{

	@Override
	public Map<String,Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
		Map<String, Object> employee=new HashMap<>();
		employee.put("uuid", rs.getString("uuid"));
		employee.put("First Name", rs.getString("firstName"));
		employee.put("Last Name", rs.getString("lastName"));
		employee.put("E-mail", rs.getString("email"));
		employee.put("Mobile", rs.getLong("mobile"));
		employee.put("Salary", rs.getDouble("salary"));
		employee.put("Authority", rs.getString("authority"));
		employee.put("Department", rs.getString("department"));
		employee.put("Tenant", rs.getString("tenant"));
		employee.put("Role", rs.getString("role"));
		return employee;
	}

}
