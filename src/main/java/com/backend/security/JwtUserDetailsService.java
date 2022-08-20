package com.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.model.User;
import com.backend.security.jwt.JwtUser;
import com.backend.security.jwt.JwtUserFactory;
import com.backend.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService{

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUserName(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("user with username: " + username + "not found");
		}
		
		JwtUser jwtUser = JwtUserFactory.create(user);
		log.info("IN loadUserByUsername - user with name {} successfully loaded ", username);
		return jwtUser;
	}

}
