package com.ksv.hrms.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ksv.hrms.entity.Employee;
import com.ksv.hrms.service.AuthorityService;

public class UserPrincipal implements UserDetails{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Employee employee;
	AuthorityService authService;
	public UserPrincipal(Employee employee,AuthorityService authService) {
		this.employee = employee;
		this.authService=authService;
	}

	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String role=authService.getRoleName(employee.getAuthority()).toUpperCase();
//		System.out.println(role);
//		Set<?> auth=Collections.singleton(new SimpleGrantedAuthority(role));
//		System.out.println(auth.toString());
		return Collections.singleton(new SimpleGrantedAuthority(role));
	}

	@Override
	public String getPassword() {
		
		return employee.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return employee.getUsername();
	}

	
}
