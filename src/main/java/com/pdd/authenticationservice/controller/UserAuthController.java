package com.pdd.authenticationservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdd.authenticationservice.exception.DuplicatedUserInfoException;
import com.pdd.authenticationservice.model.AccountCreationRequest;
import com.pdd.authenticationservice.model.AccountCreationResponse;
import com.pdd.authenticationservice.model.AuthenticationRequest;
import com.pdd.authenticationservice.model.AuthenticationResponse;
import com.pdd.authenticationservice.service.AuthDataService;
import com.pdd.authenticationservice.service.CustomUserDetailsService;
import com.pdd.authenticationservice.util.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserAuthController {

	private final AuthDataService authDataService;
	private final JwtUtil tokenProvider;
    private final AuthenticationManager authenticationManager;
	private final CustomUserDetailsService customUserDetailsService;
	

	@GetMapping("/test")
	public ResponseEntity<String> test() {
		return ResponseEntity.ok("Hello from test page!<a href='/logout'> logout</a>");
	}

	@GetMapping("/")
	public ResponseEntity<String> testHomepage() {
		return ResponseEntity.ok("Hello from home page!");
	}

	@PostMapping("/signup")
	public ResponseEntity<AccountCreationResponse> createAccount(@Valid @RequestBody AccountCreationRequest accountCreationRequest)
			throws Exception {

		if (authDataService.findByUsername(accountCreationRequest.getUsername()).isPresent()) {
			throw new DuplicatedUserInfoException(String.format("Username %s already been used", accountCreationRequest.getUsername()));
		}
		if (authDataService.findByEmail(accountCreationRequest.getEmail()).isPresent()) {
			throw new DuplicatedUserInfoException(String.format("Email %s already been used", accountCreationRequest.getEmail()));
		}

		authDataService.createUserProfile(accountCreationRequest);
		return new ResponseEntity<AccountCreationResponse>(new AccountCreationResponse("Account created successfully!")
				,HttpStatus.CREATED);
	}
	
	@PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest loginRequest) {
		String username=loginRequest.getUsername();
		String password=loginRequest.getPassword();
		/*//To pass credentials as header:
		 * public ResponseEntity<AuthenticationResponse> login(@RequestHeader(value =
		 * "Authorization") String headerData) {
		 * 
		 * AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		 * String[] data = headerData.split(" "); byte[] decoded =
		 * Base64.getDecoder().decode(data[1]); String decodedStr = new String(decoded,
		 * StandardCharsets.UTF_8); data = decodedStr.split(":"); String
		 * username=data[0]; String password=data[1];
		 */
		
        try{
        	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        } catch (BadCredentialsException e) {
            return ResponseEntity.ok(new AuthenticationResponse(null, "Incorrect username or password.",null));
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.ok(new AuthenticationResponse(null, "Username does not exist.", null));
        }
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        final String jwt = tokenProvider.generateToken(userDetails);
        //TODO
        final String firstName=authDataService.findByUsername(username).get().getFirstName();
        return ResponseEntity.ok(new AuthenticationResponse(jwt, null,firstName));
    }
}
