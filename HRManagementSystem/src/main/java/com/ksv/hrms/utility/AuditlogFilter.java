package com.ksv.hrms.utility;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ksv.hrms.dao.AuditlogDao;
import com.ksv.hrms.entity.AuditLog;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class AuditlogFilter extends OncePerRequestFilter{
	@Autowired
	private AuditlogDao auditlogDao;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			
			filterChain.doFilter(request, response);
		}
		finally {
			String username=request.getParameter("username");
//			System.out.println(username);
			AuditLog auditLog=new AuditLog();
			Object authUser=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(authUser instanceof UserDetails) {
				username=((UserDetails) authUser).getUsername();
				List<String> role=((UserDetails) authUser).getAuthorities().stream()
						.map(GrantedAuthority::getAuthority)
						.toList();
				auditLog.setRole(role.get(0));
				
			}
			auditLog.setUsername(username);
			auditLog.setMethod(request.getMethod());
			auditLog.setStatus(response.getStatus());
			auditLog.setUri(request.getRequestURI());
			auditLog.setTimestamp(LocalDateTime.now());
//			System.err.println(auditLog.getUsername()+"\n"+auditLog.getRole()+"\n"+auditLog.getMethod()+"\n"+auditLog.getUri()+"\n"+auditLog.getStatus()+"\n"+auditLog.getTimestamp());
			auditlogDao.save(auditLog);
		}
	}
	
}
