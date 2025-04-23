package com.ksv.hrms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ksv.hrms.entity.Department;
import com.ksv.hrms.entity.Employee;

@Repository
public interface DepartmentDao {
	
	int save(Department department) ;
	boolean isPresent(Department department);
	int getDepartmentId(Department department);
	Department getDepartmentById(String uuid);
	int update(Department dbDepartment);
	List<Department> getAllDepartments(Employee employee);
	int delete(String uuid);
	
}
