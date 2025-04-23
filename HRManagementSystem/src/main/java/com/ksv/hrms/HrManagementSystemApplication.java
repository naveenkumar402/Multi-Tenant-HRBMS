package com.ksv.hrms;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ksv.hrms.dao.EmployeeDao;
import com.ksv.hrms.entity.Employee;

@SpringBootApplication
public class HrManagementSystemApplication implements CommandLineRunner{
	
	public static void main(String[] args) {
		SpringApplication.run(HrManagementSystemApplication.class, args);
	}
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	
	
	@Override
	public void run(String... args) throws Exception {
		Employee employee=new Employee();
		employee.setFirstName("Naveen");
		employee.setLastName("kumar");
		employee.setEmail("dnaveenkumar402@gmail.com");
		employee.setPassword(encoder.encode("naveen@123"));
		employee.setMobile(7010717858l);
		employee.setUsername(employee.getEmail());
		employee.setRole("CEO");
		if(!employeeDao.isPresent(employee)) {
			String[] authRoles= {"ADMIN","HEAD","HR","EMPLOYEE"};
			String sql="insert into authority (uuid,rolename) values (:uuid,:name)";
			for(String role:authRoles) {
				Map<String, Object> roleParam=new HashMap<>();
				roleParam.put("name",role);
				roleParam.put("uuid", UUID.randomUUID().toString());
				jdbcTemplate.update(sql, roleParam);
			}
			
			sql="select uuid from authority where roleName= :name";
			Map<String, Object> param=new HashMap<>();
			param.put("name", "ADMIN");
			employee.setAuthority(jdbcTemplate.queryForObject(sql,param, String.class));
			
			sql="insert into employee (uuid,firstName,lastName,email,mobile,username,password,role,authority)"
					+ "values (:uuid, :firstName, :lastName, :email, :mobile, :username, :password, :role,:authority)";
			Map<String, Object> employeeParam=new HashMap<>();
			employeeParam.put("uuid", UUID.randomUUID().toString());
			employeeParam.put("firstName", employee.getFirstName());
			employeeParam.put("lastName", employee.getLastName());
			employeeParam.put("email",employee.getEmail());
			employeeParam.put("mobile", employee.getMobile());
			employeeParam.put("username", employee.getUsername());
			employeeParam.put("password",employee.getPassword());
			employeeParam.put("role", employee.getRole());
			employeeParam.put("authority",employee.getAuthority());
			jdbcTemplate.update(sql, employeeParam);
		}
		
		
	}

	
	

}
