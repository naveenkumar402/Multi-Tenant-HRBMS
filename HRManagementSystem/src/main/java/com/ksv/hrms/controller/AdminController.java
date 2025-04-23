package com.ksv.hrms.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import com.ksv.hrms.dao.AuditlogDao;
import com.ksv.hrms.entity.AuditLog;
import com.ksv.hrms.entity.Employee;
import com.ksv.hrms.entity.Tenant;
import com.ksv.hrms.file.PdfGenerator;
import com.ksv.hrms.service.EmployeeService;
import com.ksv.hrms.service.AuthorityService;
import com.ksv.hrms.service.TenantService;

import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/hrbms")
public class AdminController {
	
	@Autowired
	private TenantService tenantService; 
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private AuthorityService roleService;
	
	@Autowired
	private AuditlogDao auditDao;
	
	@Autowired
	private PdfGenerator pdfGenerator;
	
	@PostAuthorize("hasAuthourity('ADMIN')")
	@PostMapping("/tenants")
	public ResponseEntity<?> createTenant(@RequestBody Tenant tenant){
		return ResponseEntity.ok(tenantService.saveTenant(tenant));
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/tenants")
	public ResponseEntity<List<Tenant>> getMethodName() {
		return ResponseEntity.ok(tenantService.getAllTenants());
	}
	
	@PostAuthorize("hasAnyAuthourity('ADMIN','HR')")
	@PostMapping("/users")
	public ResponseEntity<?> saveEmployee(@RequestBody Employee employee,
			@RequestParam String roleId, 
			@RequestParam String tenantId,
			@RequestParam String departmentId){
		return ResponseEntity.ok(employeeService.saveEmployee(employee,roleId,tenantId,departmentId));
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers(){
		return ResponseEntity.ok(employeeService.getAllEmployee());
	}
	
	@PreAuthorize("hasAuthourity('ADMIN')")
	@PutMapping("/tenants/{uuid}")
	public ResponseEntity<String> updateTenant(@PathVariable String uuid,
			@RequestBody Tenant tenant){
		return ResponseEntity.ok(tenantService.updateTenant(uuid,tenant));
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/tenant/{uuid}")
	public ResponseEntity<String> deleteTenant(@PathVariable String uuid){
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(tenantService.deleteTenant(uuid));
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
	@GetMapping("/users/{uuid}")
	public ResponseEntity<?> getEmployee(@PathVariable String uuid){
		return ResponseEntity.ok(employeeService.getEmployee(uuid)); 
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
	@DeleteMapping("/users/{uuid}")
	public ResponseEntity<String> deleteEmployee(@PathVariable String uuid){
		return ResponseEntity.ok(employeeService.deleteEmployee(uuid));
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
	@PutMapping("/users/{uuid}")
	public ResponseEntity<String> updateEmployeeInformation(@PathVariable String uuid,@RequestBody Employee employee){
		return ResponseEntity.ok(employeeService.updateEmployeeDetails(uuid,employee));
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
	@PutMapping("/users/{id}/roles")
	public ResponseEntity<String> updateEmployeeRole(@PathVariable String id,
			@RequestParam String roleId){
		return ResponseEntity.ok(employeeService.updateEmployeeRole(id,roleId));
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
	@GetMapping("/roles")
	public ResponseEntity<?> getAllRoles(){
		return ResponseEntity.ok(roleService.getAllRoles());
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/auditlog")
	public ResponseEntity<List<AuditLog>> getAuditlogs(){
		return ResponseEntity.ok(auditDao.getAuditlog());
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/auditlog/pdf")
	public void getAuditlogsPdf(HttpServletResponse response){
		try {
			pdfGenerator.generatePdf(response);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
