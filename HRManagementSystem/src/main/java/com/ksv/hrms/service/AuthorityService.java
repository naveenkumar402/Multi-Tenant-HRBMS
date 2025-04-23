package com.ksv.hrms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksv.hrms.dao.AuthorityDao;
import com.ksv.hrms.entity.Authority;
import com.ksv.hrms.exception.NoDataFoundException;

@Service
public class AuthorityService {
	
	@Autowired
	private AuthorityDao authDao;

	public String getRoleName(String uuid) {
		return authDao.getRoleNameById(uuid);
	}

	public List<Authority> getAllRoles() {
		List<Authority> roles=authDao.getAllRole();
		if(roles.size()==0)
			throw new NoDataFoundException("No roles are available");
		return roles;
	}

	public Authority getAuthorityById(String uuid) {
		return authDao.getAuthById(uuid);
	}

}
