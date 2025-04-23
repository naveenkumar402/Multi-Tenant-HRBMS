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

import com.ksv.hrms.dao.EmployeeDao;
import com.ksv.hrms.entity.Employee;
import com.ksv.hrms.entity.Authority;
import com.ksv.hrms.exception.NoDataFoundException;
import com.ksv.hrms.utility.EmployeeMapper;
import com.ksv.hrms.utility.UserMapper;

@Component
public class EmployeeDaoImpln implements EmployeeDao{
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private EmployeeMapper employeeMapper;
	
	@Override
	public Employee findByUsername(String username) {
		String sql="select * from employee where username like :username";
		Map<String,Object> param=new HashMap<>();
		param.put("username", username);
		return namedJdbcTemplate.queryForObject(sql, param,userMapper);
	}

	

	@Override
	public int save(Employee employee) {
		
		Map<String, Object> param=new HashMap<>();
		String sql="insert into Employee(uuid,firstName,lastName,email,mobile,salary,username,password,authority,tenant,role,department)"
				+ "values(:uuid,:firstName, :lastName, :email, :mobile, :salary, :username, :password, :authority, :tenant,:role,:department)";
		param.put("uuid", UUID.randomUUID().toString());
		param.put("firstName", employee.getFirstName());
		param.put("lastName", employee.getLastName());
		param.put("email",employee.getEmail());
		param.put("mobile", employee.getMobile());
		param.put("username", employee.getUsername());
		param.put("password",employee.getPassword());
		param.put("authority", employee.getAuthority());
		param.put("tenant", employee.getTenant());
		param.put("salary", employee.getSalary());
		param.put("role", employee.getRole());
		param.put("department", employee.getDepartment());
		System.out.println(param.get("uuid"));
		return namedJdbcTemplate.update(sql, param);
	}

	@Override
	public boolean isPresent(Employee employee) {
		String sql="select count(*) from employee where username like :username";
		Map<String, Object> param=new HashMap<>();
		param.put("username", employee.getUsername());
		int count=namedJdbcTemplate.queryForObject(sql,param,Integer.class);
		if(count==0) {
			return false;
		}
		return true;
	}


	@Override
	public List<Map<String, Object>> findAllEmployee() {
		String sql="select uuid,firstName,lastName,email,mobile,salary,role,department,tenant from employee";
		return jdbcTemplate.queryForList(sql);
	}



	@Override
	public List<Map<String,Object>> findAllEmployeeByDepartment(String departmentId) {
		String sql="Select * from employee where department= :department";
		Map<String , Object> param=new HashMap<>();
		param.put("department", departmentId);
		try {
			return namedJdbcTemplate.query(sql, param,employeeMapper);	
		}catch(EmptyResultDataAccessException e) {
			throw new NoDataFoundException("There are no employee in this department");
		}
	}



	@Override
	public Map<String,Object> getById(String uuid) {
		String sql="Select * from employee where uuid like :uuid";
		Map<String, Object> param=new HashMap<>();
		param.put("uuid", uuid);
		try {
			return  namedJdbcTemplate.queryForObject(sql, param, employeeMapper);
		}
		catch(EmptyResultDataAccessException e) {
			throw new NoDataFoundException("Employee not found with this id "+uuid);
		}
	}



	@Override
	public int deleteEmployee(String uuid) {
		String sql="delete from employee where uuid= :id";
		Map<String, Object> param=new HashMap<>();
		param.put("id", uuid);
		return namedJdbcTemplate.update(sql, param);
	}



	@Override
	public int updateEmployeeRole(String uuid, Authority role) {
		String sql="update employee set authority= :role where uuid= :id";
		Map<String, Object> param=new HashMap<>();
		param.put("role", role.getUuid());
		param.put("id", uuid);
		return namedJdbcTemplate.update(sql, param);
	}



	@Override
	public int updateEmployee(String uuid, Employee employee) {
		String sql="update employee set email= :email,mobile= :mobile,salary= :salary where uuid= :id";
		Map<String, Object> param=new HashMap<>();
		param.put("email", employee.getEmail());
		param.put("mobile", employee.getMobile());
		param.put("salary",employee.getSalary());
		param.put("id", uuid);
		return namedJdbcTemplate.update(sql, param);
	}



	@Override
	public boolean isTenantHasHr(String tenantId, String authId) {
		String sql="select count(*) from employee where tenant= :tenantId and authority= :authId";
		Map<String, Object> param=new HashMap<>();
		param.put("tenantId", tenantId);
		param.put("authId", authId);
		int count=namedJdbcTemplate.queryForObject(sql, param, Integer.class);
		System.out.println(count);
		return count!=0?true:false;
	}



	@Override
	public boolean isDepartmentHasHead(String departmentId, String authId) {
		String sql="Select count(*) from employee where department= :department and authority=:role";
		Map<String, Object> param=new HashMap<>();
		param.put("department", departmentId);
		param.put("role", authId);
		int count=namedJdbcTemplate.queryForObject(sql, param, Integer.class);
		System.out.println(count);
		return count!=0?true:false;
	}

}
