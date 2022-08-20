package com.backend.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dto.UserDto;
import com.backend.model.Status;
import com.backend.model.User;
import com.backend.service.UserService;


@RestController
@RequestMapping("/main/")
public class MainController {
	
	private final UserService userService;
	
	public MainController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("users")
	public ResponseEntity<Object> getPosts(@RequestParam int currentPage, @RequestParam int pageSize) {
		Map<Object, Object> response = new HashMap<>();
		Page<User> users = userService.findAllByStatus(PageRequest.of(currentPage - 1, pageSize), Status.ACTIVE.toString());
		List<User> usersList = users.getContent();
		if(usersList.size() == 0) {
			response.put("message", "No any users found");
			response.put("resultCode", 1);
			return ResponseEntity.ok(response);
		}
		List<UserDto> responseList = new ArrayList<>();
		usersList.forEach(user -> {
			responseList.add(UserDto.fromUser(user));
		});
		response.put("posts", responseList);
		response.put("totalPostsCount", users.getTotalElements());
		response.put("totalPagesCount", users.getTotalPages());
		response.put("resultCode", 0);
		return ResponseEntity.ok(response);
	}
	
	
}