package com.pdd.authenticationservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pdd.authenticationservice.dao.UserAuthRepository;
import com.pdd.authenticationservice.entity.UserEntity;
import com.pdd.authenticationservice.util.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserAuthRepository userAuthRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> user = userAuthRepo.findByUsername(username);
		if (user.isEmpty())
			throw new UsernameNotFoundException("Username does not exist!");
		return new CustomUserDetails(user.get());
	}

}
