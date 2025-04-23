package com.ksv.hrms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ksv.hrms.dao.EmployeeDao;
import com.ksv.hrms.entity.Employee;
import com.ksv.hrms.service.AuthorityService;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private AuthorityService roleService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee=employeeDao.findByUsername(username);
		if(employee!=null) {
			return new UserPrincipal(employee,roleService);
		}
		throw new UsernameNotFoundException("Employee not found");
	}

}
