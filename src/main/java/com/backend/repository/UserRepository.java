package com.backend.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.model.User;


@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
	
	@Query(value="SELECT * FROM user WHERE status=:status order by created DESC", nativeQuery = true)
	Page<User> findAllByStatus(Pageable pageable, @Param("status") String status);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE user SET status=:status WHERE id=:userId", nativeQuery = true)
	void updateUserStatus(Long userId, String status);

}
