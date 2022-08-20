package com.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByName(String name);

}
