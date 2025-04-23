package com.ksv.hrms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksv.hrms.dao.DepartmentDao;
import com.ksv.hrms.dao.TenantDao;
import com.ksv.hrms.entity.Department;
import com.ksv.hrms.entity.Employee;

@Service
public class DepartmentService {
	
	@Autowired
	private DepartmentDao departmentDao;
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private TenantDao tenantDao;
	
	public String save(String tenantId,Department department)  {
		Employee employee=employeeService.getAuthenticatedUser();
		department.setTenant(employee.getTenant());
		if(tenantId!=null && department.getTenant()==null && tenantDao.findById(tenantId)!=null)
			department.setTenant(tenantId);
		if(department.getTenant()==null) {
			return "Enter tenant value";
		}
		if(departmentDao.isPresent(department)) {
			return "Department already exists for this tenant"; 
		}
		int count=departmentDao.save(department);
		if(count==1)
			return "Department added successfully";
		return "Something wrong with adding departments";
		
	}
	
	public int getDepartmentId(Department department) {
		if(departmentDao.isPresent(department)) {
			return departmentDao.getDepartmentId(department);
		}
		return 0;
	}

	public String updateDepartment(String uuid,Department department) {
		Employee employee=employeeService.getAuthenticatedUser();
		Department dbDepartment=departmentDao.getDepartmentById(uuid);
		if(!dbDepartment.getTenant().equals(employee.getTenant())) {
			return "You don't have access for this department";
		}
		if(department.getDepartmentName()!=null) 
			dbDepartment.setDepartmentName(department.getDepartmentName());
		int count=departmentDao.update(dbDepartment);
		if(count==1)
			return "Department details updated";
		return "Department details not updated";
	}

	public List<Department> getAllDepartments() {
		Employee employee=employeeService.getAuthenticatedUser();
		List<Department> departments=departmentDao.getAllDepartments(employee);
		return departments;
	}

	public String deleteDepartment(String uuid) {
		Employee employee=employeeService.getAuthenticatedUser();
		Department dbDepartment=departmentDao.getDepartmentById(uuid);
		if(!dbDepartment.getTenant().equals(employee.getTenant()))
			return "You don't have access for this department";
		int count=departmentDao.delete(uuid);
		if(count==1)
			return "Department details deleted successfully";
		return "Something wrong";
	}

	
}
