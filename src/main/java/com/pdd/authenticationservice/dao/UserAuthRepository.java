package com.pdd.authenticationservice.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdd.authenticationservice.entity.UserEntity;

public interface UserAuthRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUsername(String username);
	Optional<UserEntity> findByEmail(String email);
	
}
