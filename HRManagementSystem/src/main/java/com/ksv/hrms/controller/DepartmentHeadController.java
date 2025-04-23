package com.ksv.hrms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ksv.hrms.service.LeaveRequestsService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/hrbms")
public class DepartmentHeadController {
	@Autowired
	private LeaveRequestsService leaveReqService;
	
	@PreAuthorize("hasAnyAuthority('HEAD','HR'))")
	@GetMapping("/leaves")
	public ResponseEntity<?> getLeaveRequests(){
		return ResponseEntity.ok(leaveReqService.getAllRequests());
	}
	@PreAuthorize("hasAnyAuthority('HEAD','HR'))")
	@PutMapping("/leaves/{uuid}/approve")
	public ResponseEntity<String> approveLeaveRequest(@PathVariable String uuid){
		try {
			return ResponseEntity.ok(leaveReqService.approveRequest(uuid));
		} catch (MessagingException e) {
			return ResponseEntity.ok(e.getMessage());
		}
	}
	
	@PreAuthorize("hasAnyAuthority('HEAD','HR'))")
	@PutMapping("/leaves/{uuid}/reject")
	public ResponseEntity<String> rejectLeaveRequest(@PathVariable String uuid){
		try {
			return ResponseEntity.ok(leaveReqService.rejectRequest(uuid));
		} catch (MessagingException e) {
			return ResponseEntity.ok(e.getMessage());
		}
	}
}
