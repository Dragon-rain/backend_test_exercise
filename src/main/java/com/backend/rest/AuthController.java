package com.backend.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dto.UserDto;
import com.backend.model.User;
import com.backend.security.jwt.JwtTokenProvider;
import com.backend.service.AuthDataService;
import com.backend.service.UserService;


@RestController
@RequestMapping("/auth/")
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserService userService;
	private final AuthDataService authDataService;
	
	@Autowired
	public AuthController(AuthenticationManager authenticationManager
						 ,UserService userService
						 ,JwtTokenProvider jwtTokenProvider
						 ,AuthDataService authDataService) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.jwtTokenProvider = jwtTokenProvider;
		this.authDataService = authDataService;
	}
	
	@PostMapping("login")
	public ResponseEntity<Object> login(@RequestBody UserDto reqestDTO) {
		
		try {
			String username = reqestDTO.getUsername();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, reqestDTO.getPassword()));
			User user = userService.findByUserName(username);
			if(user == null) {
				throw new UsernameNotFoundException("User with username: " + username + " not found");
			}
			String token = jwtTokenProvider.createToken(username, user.getRoles());
			String refreshToken = jwtTokenProvider.refreshToken(username, user.getRoles());
			authDataService.saveData(username, refreshToken);
			Map<Object, Object> response = new HashMap<>();
			response.put("user", UserDto.fromUser(user));
			response.put("token", token);
			response.put("refreshToken", refreshToken);
			response.put("resultCode", 0);
			return ResponseEntity.ok(response);
			
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid username or password");
		}
		
	}
	
	@PostMapping("registration")
	public ResponseEntity<Object> registration (@RequestBody User user) {
		Map<Object, Object> response = new HashMap<Object, Object>();
		String username = user.getUsername();
		User registredUser = userService.registration(user);
		String token = jwtTokenProvider.createToken(username, registredUser.getRoles());
		String refreshToken = jwtTokenProvider.refreshToken(username, registredUser.getRoles());
		authDataService.saveData(username, refreshToken);
		response.put("user", UserDto.fromUser(user));
		response.put("token", token);
		response.put("refreshToken", refreshToken);
		response.put("resultCode", 0);
		return ResponseEntity.ok(response);
		
	}
 	 
	@PostMapping("authme")
	public ResponseEntity<Object> getUser(@RequestBody UserDto reqestDTO) {
		Map<Object, Object> response = new HashMap<>();
		User user = userService.findByUserName(reqestDTO.getUsername());
		if(user == null) {
			response.put("resultCode", 1);
			response.put("message", "user not found");
			return ResponseEntity.ok(response);
		}
		response.put("user", UserDto.fromUser(user));
		response.put("resultCode", 0);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("refresh")
	public ResponseEntity<Object> refreshToken(@RequestBody UserDto reqestDTO) {
		Map<Object, Object> response = new HashMap<Object, Object>();
		String username = reqestDTO.getUsername();
		User user = userService.findByUserName(username);
		if(user == null) {
			throw new UsernameNotFoundException("User with username: " + username + " not found");
		}
		authDataService.removeTokenData(username);
		String token = jwtTokenProvider.createToken(username, user.getRoles());
		String refreshToken = jwtTokenProvider.refreshToken(username, user.getRoles());
		authDataService.saveData(username, refreshToken);
		response.put("resultCode", 0);
		response.put("username", username);
		response.put("token", token);
		response.put("refreshToken", refreshToken);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("logout")
	public ResponseEntity<Object> logout(@RequestBody UserDto authData) {
		authDataService.removeTokenData(authData.getUsername());
		return ResponseEntity.ok("logout success!");
	}

}
