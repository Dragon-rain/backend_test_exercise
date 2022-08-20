package com.backend.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.model.AuthData;


@Transactional
@Repository
public interface AuthRepository extends JpaRepository<AuthData, Long>{

	@Query(value = "SELECT * FROM auth_data WHERE refresh_token=?1", nativeQuery = true)
    AuthData getUserToken(String refreshToken);
	
	@Modifying
	@Query(value = "DELETE FROM auth_data WHERE username=?1 AND id > 0", nativeQuery = true)
    void removeTokenData(String username);
}
