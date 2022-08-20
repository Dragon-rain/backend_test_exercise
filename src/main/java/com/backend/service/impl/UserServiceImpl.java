package com.backend.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;
import com.backend.model.Role;
import com.backend.model.Status;
import com.backend.model.User;
import com.backend.repository.RoleRepository;
import com.backend.repository.UserRepository;
import com.backend.service.UserService;
import com.backend.util.DateUtil;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public User registration(User user) {
		String today = DateUtil.getStringDate();
		Role roleUser = roleRepository.findByName("ROLE_USER");
 		List<Role> userRoles = new ArrayList<>();
		userRoles.add(roleUser);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(userRoles);
		user.setStatus(Status.ACTIVE);
		user.setCreated(today);
		user.setUpdated(today);
		User registredUser = userRepository.save(user);
		log.info("IN registration - user{} successufully registred: ", registredUser);
		
		return registredUser;
		
	}

	@Override
	public User findByUserName(String username) {
		User result = userRepository.findByUsername(username);
		return result;
	}

	@Override
	public User findById(Long id) {
		User result = userRepository.findById(id).orElse(null);
		
		if(result == null) {
			log.info("IN findById - no user found by id: {} ", id);
			return null;
		}
		
		log.info("IN findById - user: {} found by findById: {} ", result);
		return result;
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
		log.info("IN deleteUser - user with id: {} successfully deleted", id);
		
	}

	@Override
	public List<User> getAll() {
		List<User> result = userRepository.findAll();
		log.info("IN getAll - {} users found ", result);
		return result;
	}

	@Override
	public User updateUser(User user) {
		User currentUser = userRepository.findById(user.getId()).orElse(null);
		if(currentUser.getUsername().equals(null)) {
			log.info("in updateUser user with username: {}, not found", user.getUsername());
			throw new UsernameNotFoundException("in updateUser user with username: " + user.getUsername() + "not found");
		}
		currentUser.setUsername(user.getUsername());
		currentUser.setName(user.getName());
		currentUser.setUpdated(DateUtil.getStringDate());
		userRepository.save(currentUser);
		log.info("in updateUser - user with username: {}, successfully updated", user.getUsername());
		return user;
	}

	@Override
	public void updateUserStatus(Long userId, String status) {
		userRepository.updateUserStatus(userId, status);
		log.info("in updateUserStatus - user with id: {}, successfuly changed status to - {}", userId, status);
	}

	@Override
	public Page<User> findAllByStatus(Pageable pageable, String status) {
		return userRepository.findAllByStatus(pageable, status);
	}

}
