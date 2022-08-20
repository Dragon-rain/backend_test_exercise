package com.backend.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.backend.model.AuthData;
import com.backend.service.AuthDataService;

import io.jsonwebtoken.JwtException;


public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthDataService authDataService;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider
    		 			 ,AuthDataService authDataService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authDataService = authDataService;
    }
    
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
		try {
			if (token != null && jwtTokenProvider.validateToken(token)) {
				Authentication auth = jwtTokenProvider.getAuthentication(token);
				if (auth != null) {
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
		} catch (JwtException | IllegalArgumentException e) {
			response.sendError(403);
        }
		
		String refreshToken = jwtTokenProvider.resolveRefreshToken((HttpServletRequest) req);
		try {
			if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
				AuthData authData = authDataService.getUserToken(refreshToken);
				if(authData.getRefreshToken() == null) {
					response.sendError(401);
				}
			}
		} catch (JwtException | IllegalArgumentException e) {
			response.sendError(401);
        }
		
		
		filterChain.doFilter(req, res);
	}
	
}