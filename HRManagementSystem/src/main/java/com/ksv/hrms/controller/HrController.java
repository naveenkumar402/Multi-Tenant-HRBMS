package com.ksv.hrms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ksv.hrms.entity.Department;
import com.ksv.hrms.service.DepartmentService;
import com.ksv.hrms.service.EmployeeService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/hrbms")
public class HrController {
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
	@PostMapping("/departments")
	public ResponseEntity<String> addDepartment(@RequestParam String tenantId, @RequestBody Department department){
		return ResponseEntity.ok(departmentService.save(tenantId,department));
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
	@PutMapping("/departments/{id}")
	public ResponseEntity<String> updateDepartment(@PathVariable String id,@RequestBody Department department){
		return ResponseEntity.ok(departmentService.updateDepartment(id,department));
	}
	
	@PreAuthorize("hasAuthority('HR')")
	@GetMapping("/departments/{departmentId}/employees")
	public ResponseEntity<?> getAllEmployees(@PathVariable String departmentId){
		return ResponseEntity.ok(employeeService.getAllEmployee(departmentId));
	}
	
	@PreAuthorize("hasAuthority('HR')")
	@DeleteMapping("/departments/{id}")
	public ResponseEntity<String> deleteDepartment(@PathVariable String id){
		return ResponseEntity.ok(departmentService.deleteDepartment(id));
	}
	
	
}
