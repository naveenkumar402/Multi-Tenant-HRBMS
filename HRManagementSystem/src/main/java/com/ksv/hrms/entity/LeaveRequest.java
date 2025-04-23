package com.ksv.hrms.entity;

import java.time.LocalDate;

public class LeaveRequest {
	private String uuid;
	private String reason;
	private LocalDate fromDate;
	private LocalDate toDate;
	private int numerOfDays;
	private String comments;
	private String employee;
	private boolean headapproval;
	private boolean hrApproval;
	public LeaveRequest() {
		// TODO Auto-generated constructor stub
	}
	public LeaveRequest(String uuid, String reason, LocalDate fromDate, LocalDate toDate, int numerOfDays,
			String comments, String employee, boolean headapproval, boolean hrApproval) {
		this.uuid = uuid;
		this.reason = reason;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.numerOfDays = numerOfDays;
		this.comments = comments;
		this.employee = employee;
		this.headapproval = headapproval;
		this.hrApproval = hrApproval;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	public int getNumerOfDays() {
		return numerOfDays;
	}
	public void setNumerOfDays(int numerOfDays) {
		this.numerOfDays = numerOfDays;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getEmployee() {
		return employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	public boolean isHeadapproval() {
		return headapproval;
	}
	public void setHeadapproval(boolean headapproval) {
		this.headapproval = headapproval;
	}
	public boolean isHrApproval() {
		return hrApproval;
	}
	public void setHrApproval(boolean hrApproval) {
		this.hrApproval = hrApproval;
	}
	
}
