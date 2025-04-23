
package com.ksv.hrms.entity;

public class Tenant {
	private String uuid;
	private String tenantName;
	private String tenantLocation;
	public Tenant() {
		// TODO Auto-generated constructor stub
	}
	
	public Tenant(String uuid, String tenantName, String tenantLocation) {
		this.uuid = uuid;
		this.tenantName = tenantName;
		this.tenantLocation = tenantLocation;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getTenantLocation() {
		return tenantLocation;
	}
	public void setTenantLocation(String tenantLocation) {
		this.tenantLocation = tenantLocation;
	}
	
	
}
