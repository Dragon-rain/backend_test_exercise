package com.backend.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.backend.service.AuthDataService;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private final JwtTokenProvider jwtTokenProvider;
	private final AuthDataService authDataService;

	public JwtConfigurer(JwtTokenProvider jwtTokenProvider
						,AuthDataService authDataService) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.authDataService = authDataService;
	}

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider, authDataService);
		httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
