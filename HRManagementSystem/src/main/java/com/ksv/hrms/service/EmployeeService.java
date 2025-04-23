package com.ksv.hrms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ksv.hrms.dao.DepartmentDao;
import com.ksv.hrms.dao.EmployeeDao;
import com.ksv.hrms.dao.TenantDao;
import com.ksv.hrms.entity.Authority;
import com.ksv.hrms.entity.Department;
import com.ksv.hrms.entity.Employee;
import com.ksv.hrms.entity.Tenant;
import com.ksv.hrms.exception.NoDataFoundException;
import com.ksv.hrms.security.JwtService;



@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private DepartmentDao departmentDao;
	
	@Autowired
	private TenantDao tenantDao;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	
	public String logout(String authHeader) {
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			jwtService.blackListToken(authHeader.substring(7));
			return "Thank you";
		}
		return "Provide token";
	}
	
	public String login(String username,String password) {
		Authentication authentication=authManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(username);
		}
		return null;
	}

	public Map<String, Object> getEmployee() {
		Employee employee=getAuthenticatedUser();
		return employeeDao.getById(employee.getUuid());
	}

	public Employee getAuthenticatedUser() {
		Object authUser=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(authUser instanceof UserDetails) {
			String username=((UserDetails) authUser).getUsername();
			Employee employee=employeeDao.findByUsername(username);
			return employee;
		}
		return null;
	}

	public String saveEmployee(Employee employee, String authId, String tenantId,String departmentId) {
		if(tenantId == null || departmentId==null || authId==null)
			return "Enter the missing value";
		Tenant tenant = tenantDao.findById(tenantId);
		Authority authority=authorityService.getAuthorityById(authId);
		Department department=departmentDao.getDepartmentById(departmentId);
		employee.setUsername(employee.getEmail());
		employee.setPassword(encoder.encode(employee.getPassword()));
		employee.setDepartment(department.getUuid());
		employee.setAuthority(authority.getUuid());
		employee.setTenant(tenant.getUuid());
		if(authority.getRoleName().equalsIgnoreCase("hr")) {
			if(employeeDao.isTenantHasHr(tenantId, authId))
				return "HR already assigned to this tennat";
		}
		if(authority.getRoleName().equalsIgnoreCase("head")) {
			if(employeeDao.isDepartmentHasHead(department.getUuid(), authority.getUuid())) 
				return "The Head is already assignes to this department";	
		}
		if(!employeeDao.isPresent(employee)) {
			if(employeeDao.save(employee)==1) 
				return "Employee saved successfully";
			return "Employee not saved successfully";
		}
		return "Employee already present with this username";
	}

	public List<Map<String,Object>> getAllEmployee() {
		List<Map<String,Object>> employeeList=employeeDao.findAllEmployee();
		if(employeeList.size()==0)
			throw new NoDataFoundException("There are no employee");
		return employeeList;
	}

	public List<Map<String,Object>> getAllEmployee(String departmentId) {
		List<Map<String,Object>> employees=employeeDao.findAllEmployeeByDepartment(departmentId);
		return employees;
	}

	public Map<String,Object> getEmployee(String uuid) {
		return employeeDao.getById(uuid);
	}

	public String deleteEmployee(String uuid) {
		if(employeeDao.getById(uuid)!=null) {
			if(employeeDao.deleteEmployee(uuid)==1)
				return "Employee deleted successfully";
		}
		return null;
	}

	public String updateEmployeeRole(String uuid, String roleId) {
		Authority authority=authorityService.getAuthorityById(roleId);
		Map<String, Object> employee=getEmployee(uuid);
		if(authority.getRoleName().equalsIgnoreCase("hr") && authority.getUuid().equals(employee.get("Authority").toString())) {
			return authority.getRoleName()+" is available for this tenant";
		}
		else if(authority.getRoleName().equalsIgnoreCase("head") && authority.getUuid().equals(employee.get("department").toString())) {
			return authority.getRoleName()+" is available for this department";
		}
		int count=employeeDao.updateEmployeeRole(uuid,authority);
		return count==1?"Role Updated successfully":null;
		
	}

	public String updateEmployeeDetails(String uuid, Employee employee) {
		Map<String, Object> dbEmployee=getEmployee(uuid);
		if(dbEmployee!=null) {
			int count=employeeDao.updateEmployee(uuid, employee);
			return count==1?"Employee detail updated successfully":null;
		}
		return null;
	}
	
}
