package com.backend.service.impl;

import org.springframework.stereotype.Service;

import com.backend.model.AuthData;
import com.backend.repository.AuthRepository;
import com.backend.service.AuthDataService;


@Service
public class AuthDataServiceImpl implements AuthDataService {

	private final AuthRepository authRepository;
	
	public AuthDataServiceImpl(AuthRepository authRepository) {
		this.authRepository = authRepository;
	}
	
	@Override
	public AuthData getUserToken(String refreshToken) {
		return authRepository.getUserToken(refreshToken);
	}

	@Override
	public void removeTokenData(String username) {
		authRepository.removeTokenData(username);
		
	}
	
	@Override
	public void saveData(String username, String refreshToken) {
		AuthData authData = new AuthData();
		authData.setUsername(username);
		authData.setRefreshToken(refreshToken);
		authRepository.save(authData);
		
	}

}
