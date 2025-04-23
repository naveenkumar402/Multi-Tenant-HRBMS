package com.ksv.hrms.entity;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class AuditLog {
	private String username;
	private String role;
	private String method;
	private String uri;
	private int status;
	private LocalDateTime timestamp;
	public AuditLog() {
		// TODO Auto-generated constructor stub
	}
	public AuditLog(String username, String role, String method, String uri, int status, LocalDateTime timestamp) {
		this.username = username;
		this.role = role;
		this.method = method;
		this.uri = uri;
		this.status = status;
		this.timestamp = timestamp;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
