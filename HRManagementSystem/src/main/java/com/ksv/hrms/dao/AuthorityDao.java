package com.ksv.hrms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ksv.hrms.entity.Authority;

@Repository
public interface AuthorityDao {
	
	boolean isPresent(Authority role);
	String getRoleNameById(String uuid);
	List<Authority> getAllRole();
	Authority getAuthById(String uuid);
}
