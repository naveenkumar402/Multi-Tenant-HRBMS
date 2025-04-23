package com.ksv.hrms.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksv.hrms.dao.LeaveRequestsDao;
import com.ksv.hrms.entity.Employee;
import com.ksv.hrms.entity.LeaveRequest;
import com.ksv.hrms.entity.Authority;

import jakarta.mail.MessagingException;


@Service
public class LeaveRequestsService {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private AuthorityService roleService;
	
	@Autowired
	private LeaveRequestsDao leaveRequestsDao;
	
	@Autowired
	private EmailService emailService;
	
	public List<LeaveRequest> getAllRequests() {
		Employee employee=employeeService.getAuthenticatedUser();
		Authority role=roleService.getAuthorityById(employee.getAuthority());
		return leaveRequestsDao.getLeaveRequests(role);
	}

	public String raiseLeaveRequest(LeaveRequest leaveRequest) {
		Employee employee=employeeService.getAuthenticatedUser();
		Authority role=roleService.getAuthorityById(employee.getAuthority());
		if(leaveRequest.getFromDate().isBefore(LocalDate.now()) ||
				leaveRequest.getToDate().isBefore(LocalDate.now())) {
			return "Enter Valid Date";
		}
		leaveRequest.setNumerOfDays((int)ChronoUnit.DAYS.between(leaveRequest.getFromDate(), leaveRequest.getToDate()));
		leaveRequest.setEmployee(employee.getUuid());
		if(role.getRoleName().equalsIgnoreCase("head"))
			leaveRequest.setHeadapproval(true);
		int count=leaveRequestsDao.saveRequest(leaveRequest);
		return count==1?"request raised":"request not raised";
	}

	public String approveRequest(String id) throws MessagingException {
		Employee authEmployee=employeeService.getAuthenticatedUser();
		Authority role=roleService.getAuthorityById(authEmployee.getAuthority());
		LeaveRequest leaveReq=leaveRequestsDao.getLeaveRequestById(id);
		Map<String, Object> raisedBy=employeeService.getEmployee(leaveReq.getEmployee());
		if(!authEmployee.getDepartment().equals(raisedBy.get("Department").toString()) && !authEmployee.getTenant().equals(raisedBy.get("Tenant").toString())) {
			return "You don't have access to this request";
		}
		if(role.getRoleName().equalsIgnoreCase("head")) 
			leaveReq.setHeadapproval(true);
		if(role.getRoleName().equalsIgnoreCase("Hr")) 
			leaveReq.setHrApproval(true);
		int count=leaveRequestsDao.update(leaveReq);
		if(count==1) {
			emailService.sendLeaveResponse(leaveReq, (String)raisedBy.get("E-mail"));
			return "Approved";
		}
		return null;
	}

	public String rejectRequest(String id) throws MessagingException {
		Employee authEmployee=employeeService.getAuthenticatedUser();
		Authority role=roleService.getAuthorityById(authEmployee.getAuthority());
		LeaveRequest leaveReq=leaveRequestsDao.getLeaveRequestById(id);
		Map<String, Object> raisedBy=employeeService.getEmployee(leaveReq.getEmployee());
		if(!authEmployee.getDepartment().equals(raisedBy.get("Department").toString()) && !authEmployee.getTenant().equals(raisedBy.get("Tenant").toString())) {
			return "You don't have access to this request";
		}
		if(role.getRoleName().equalsIgnoreCase("head")) 
			leaveReq.setHeadapproval(false);
		if(role.getRoleName().equalsIgnoreCase("Hr")) {
			leaveReq.setHrApproval(false);
			leaveReq.setHeadapproval(false);
		}
		int count=leaveRequestsDao.update(leaveReq);
		if(count==1) {
			emailService.sendLeaveResponse(leaveReq, (String)raisedBy.get("E-mail"));
			return "Rejected";
		}
		return null;
	}

}
