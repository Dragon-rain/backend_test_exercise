package com.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class AuthData extends BaseEntity{
	
	@Column(name="username")
	private String username;
	
	@Column(name="refresh_token")
	private String refreshToken;
	

	public AuthData() {
		// TODO Auto-generated constructor stub
	}

}
