package com.ksv.hrms.entity;

public class Department {
	private String uuid;
	private String departmentName;
	private String tenant;
	
	public Department() {
		
	}

	public Department(String uuid, String departmentName, String tenant) {
		this.uuid = uuid;
		this.departmentName = departmentName;
		this.tenant = tenant;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}


}
