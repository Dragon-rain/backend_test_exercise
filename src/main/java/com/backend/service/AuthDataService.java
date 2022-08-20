package com.backend.service;

import com.backend.model.AuthData;

public interface AuthDataService {
	
	void saveData(String username, String refreshToken);
	
    AuthData getUserToken(String refreshToken);
	
    void removeTokenData(String username);

}
