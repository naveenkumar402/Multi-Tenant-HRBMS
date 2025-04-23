package com.ksv.hrms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ksv.hrms.entity.LeaveRequest;
import com.ksv.hrms.service.DepartmentService;
import com.ksv.hrms.service.EmployeeService;
import com.ksv.hrms.service.LeaveRequestsService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/hrbms")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private LeaveRequestsService leaveRequestsService;
	
	
	@PostMapping("/auth/login")
	public ResponseEntity<Map<String,String>> login(@RequestParam String username,@RequestParam String password){
		Map<String,String> token=new HashMap<>();
		token.put("token",employeeService.login(username, password));
		return ResponseEntity.ok(token);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/auth/me")
	public ResponseEntity<?> getLoggedInEmployee(){
		return ResponseEntity.ok(employeeService.getEmployee());
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/auth/logout")
	public ResponseEntity<String> logout(HttpServletRequest request){
		return ResponseEntity.ok(employeeService.logout(request.getHeader("Authorization")));	
	}
	
	@PreAuthorize("hasAnyAuthority('HR','ADMIN')")
	@GetMapping("/departments")
	public ResponseEntity<?> getAllDepartments(){
		return ResponseEntity.ok(departmentService.getAllDepartments());
	}
	
	@PreAuthorize("hasAuthority('EMPLOYEE')")
	@PostMapping("/leaves")
	public ResponseEntity<String> raiseLeaveRequest(@RequestBody LeaveRequest leaveRequest){
		return ResponseEntity.ok(leaveRequestsService.raiseLeaveRequest(leaveRequest));
	}
}
