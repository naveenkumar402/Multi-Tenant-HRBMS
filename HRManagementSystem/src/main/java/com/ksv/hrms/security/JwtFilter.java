package com.ksv.hrms.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ksv.hrms.exception.InvalidTokenException;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtService jwtService;
	@Autowired
	private ApplicationContext context;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader=request.getHeader("Authorization");
				
		String username = null;
        String jwt = null;
        try {
        	if (authHeader != null && authHeader.startsWith("Bearer ")) {
        		jwt = authHeader.substring(7);
        		username = jwtService.extractUsername(jwt);
        	}
        	if(jwtService.isBlackListed(jwt)) {
        		throw new InvalidTokenException("Login to access");
        	}
        	
        }catch ( |JwtException e) {
			// TODO: handle exception
		}
        if(username != null && SecurityContextHolder.getContext().getAuthentication() ==null) {
        	UserDetails userDetails=context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
            
        	if(jwtService.validateToken(jwt,userDetails)) {
        		
        		 UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                         userDetails, null, userDetails.getAuthorities());
                 authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                 SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                 
        	}
        	else {
            	throw new InvalidTokenException("Jwt token mismatch");
            }
        	
        }
        
        filterChain.doFilter(request, response);
		
	}

}
