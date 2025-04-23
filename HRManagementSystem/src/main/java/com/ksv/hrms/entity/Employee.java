package com.ksv.hrms.entity;

public class Employee {
	private String uuid;
	private String firstName;
	private String lastName;
	private String email;
	private long mobile;
	private double salary;
	private String username;
	private String password;
	private String tenant;
	private String department;
	private String authority;
	private String role;
	
	
	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public Employee() {
		// TODO Auto-generated constructor stub
	}




	
	



	public Employee(String uuid, String firstName, String lastName, String email, long mobile, double salary,
			String username, String password, String tenant, String department, String authority, String role) {
		this.uuid = uuid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobile = mobile;
		this.salary = salary;
		this.username = username;
		this.password = password;
		this.tenant = tenant;
		this.department = department;
		this.authority = authority;
		this.role = role;
	}


	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public long getMobile() {
		return mobile;
	}


	public void setMobile(long mobile) {
		this.mobile = mobile;
	}


	public double getSalary() {
		return salary;
	}


	public void setSalary(double salary) {
		this.salary = salary;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public String getTenant() {
		return tenant;
	}


	public void setTenant(String tenant) {
		this.tenant = tenant;
	}


	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
	}


	public String getAuthority() {
		return authority;
	}


	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
}
