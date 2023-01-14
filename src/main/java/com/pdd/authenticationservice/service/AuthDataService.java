package com.pdd.authenticationservice.service;


import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import com.pdd.authenticationservice.model.AccountCreationRequest;

public interface AuthDataService {

    Optional<AccountCreationRequest> findByUsername(String username);

    Optional<AccountCreationRequest> findByEmail(String email);

    void deleteByUsernamePassword(String username, String password) throws NoSuchAlgorithmException;

    void createUserProfile(AccountCreationRequest accountCreationRequest) throws NoSuchAlgorithmException;
}
