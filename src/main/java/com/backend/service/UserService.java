
package com.backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.backend.model.User;


public interface UserService {
	
	User registration(User user);
	
	List<User> getAll();
	
	Page<User> findAllByStatus(Pageable pageable, String status);
	
	User findByUserName(String email);
	
	User findById(Long id);
	
	void deleteUser(Long id);
	
	User updateUser(User user);
	
	void updateUserStatus(Long userId, String status);

}
