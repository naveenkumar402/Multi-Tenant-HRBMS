package com.ksv.hrms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksv.hrms.dao.TenantDao;
import com.ksv.hrms.entity.Tenant;

@Service
public class TenantService {
	
	@Autowired
	private TenantDao tenantDao;

	public String saveTenant(Tenant tenant) {
		if(!tenantDao.isPresent(tenant)) {
			int count=tenantDao.save(tenant);
			return count!=0 ?"Tenant saved Successfully":"Tenant not saved";
		}
		return "Tenant already present with this details";
	}

	public List<Tenant> getAllTenants() {
		List<Tenant> tenants=tenantDao.findAllTenants();
		if(tenants.size()!=0) {
			return tenants;
		}
		return null;
	}

	public String updateTenant(String uuid, Tenant tenant) {
		Tenant dbTenant=tenantDao.findById(uuid);
		if(dbTenant==null) {
			return "Tenant not present with this ID " +uuid;
		}
		if(tenant.getTenantName()!=null) 
			dbTenant.setTenantName(tenant.getTenantName());
		if(tenant.getTenantLocation()!=null)
			dbTenant.setTenantLocation(tenant.getTenantLocation());
		int count=tenantDao.update(dbTenant);
		if(count==1)
			return "Tenant details updated successfully";
		return "Tenant details not updated in database";
	}

	public String deleteTenant(String id) {
		if(tenantDao.isPresent(tenantDao.findById(id))) {
			int row=tenantDao.delete(id);
			if(row==1)
				return "Tenanat details deleted successfully";
			return "Tenant details not deleted from the database";
		}
		return "Tenant details are not present with thid id";
	}

	
	
}
