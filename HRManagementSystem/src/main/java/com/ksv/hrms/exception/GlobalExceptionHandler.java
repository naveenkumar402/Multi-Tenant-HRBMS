package com.ksv.hrms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<String> dataNotFound(NoDataFoundException e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<String> logout(InvalidTokenException e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
	@ExceptionHandler(SignatureException.class)
	public ResponseEntity<String> invalidJwt(SignatureException e){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid JWT");
	}
	
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<String> jwtException(JwtException e){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
}
