package com.ksv.hrms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ksv.hrms.entity.Tenant;

@Repository
public interface TenantDao {
	int save(Tenant tenant);
	boolean isPresent(Tenant tenant);
	List<Tenant> findAllTenants();
	Tenant findById(String uuid);
	int update(Tenant tenant);
	int delete(String uuid); 
}
