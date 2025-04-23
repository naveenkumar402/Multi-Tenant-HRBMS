package com.ksv.hrms.entity;

public class Authority {
	private String uuid;
	private String roleName;
	public Authority() {
		// TODO Auto-generated constructor stub
	}
	public Authority(String uuid, String roleName) {
		this.uuid = uuid;
		this.roleName = roleName;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
