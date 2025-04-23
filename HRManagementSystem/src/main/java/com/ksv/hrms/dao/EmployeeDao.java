package com.ksv.hrms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ksv.hrms.entity.Employee;
import com.ksv.hrms.entity.Authority;

@Repository
public interface EmployeeDao {
	int save(Employee employee);
	Employee findByUsername(String username);
	boolean isPresent(Employee employee);
	List<Map<String, Object>> findAllEmployee();
	List<Map<String,Object>> findAllEmployeeByDepartment(String departmentId);
	Map<String,Object> getById(String uuid);
	int deleteEmployee(String uuid);
	int updateEmployeeRole(String uuid, Authority authrole);
	int updateEmployee(String uuid, Employee employee);
	boolean isTenantHasHr(String tenantId, String authid);
	boolean isDepartmentHasHead(String department, String authid);
}
