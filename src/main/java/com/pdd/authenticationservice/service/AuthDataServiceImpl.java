package com.pdd.authenticationservice.service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pdd.authenticationservice.dao.UserAuthRepository;
import com.pdd.authenticationservice.entity.UserEntity;
import com.pdd.authenticationservice.model.AccountCreationRequest;
import com.pdd.authenticationservice.util.Constants;

@Service
public class AuthDataServiceImpl implements AuthDataService {

	@Autowired
	UserAuthRepository userAuthRepo;

	@Override
	public Optional<AccountCreationRequest> findByUsername(String username) {
		Optional<UserEntity> user = userAuthRepo.findByUsername(username);
		if (user.isEmpty()) {
			return Optional.empty();
		}
		AccountCreationRequest req = new AccountCreationRequest();
		BeanUtils.copyProperties(user.get(), req, "userId", "role");
		return Optional.of(req);
	}

	@Override
	public Optional<AccountCreationRequest> findByEmail(String email) {
		Optional<UserEntity> user = userAuthRepo.findByEmail(email);
		if (user.isEmpty()) {
			return Optional.empty();
		}
		AccountCreationRequest req = new AccountCreationRequest();
		BeanUtils.copyProperties(user.get(), req, "userId", "role");
		return Optional.of(req);
	}

	@Override
	public void deleteByUsernamePassword(String username, String password) throws NoSuchAlgorithmException {
		
	}

	@Override
	@Modifying
	public void createUserProfile(AccountCreationRequest accountCreationRequest){
		
		UserEntity user = new UserEntity();

		user.setFirstName(accountCreationRequest.getFirstName());
		user.setLastName(accountCreationRequest.getLastName());
		user.setEmail(accountCreationRequest.getEmail());
		user.setUsername(accountCreationRequest.getUsername());
		user.setPassword(new BCryptPasswordEncoder().encode(accountCreationRequest.getPassword()));
		user.setRole(Constants.DEFAULT_ROLE);
		user.setAccountCreationDate(LocalDateTime.now());
		
		userAuthRepo.save(user);
	}

}
