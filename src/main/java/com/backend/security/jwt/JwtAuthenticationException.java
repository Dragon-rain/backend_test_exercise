package com.backend.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException{

	private static final long serialVersionUID = -3237961061234883527L;

	public JwtAuthenticationException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public JwtAuthenticationException(String msg) {
		super(msg);
	}

}