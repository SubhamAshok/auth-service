package com.pdd.authenticationservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pdd.authenticationservice.filter.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http					.cors()
								.and()
								.csrf().disable()
								.authorizeHttpRequests((authz) -> {authz
								.requestMatchers("/auth/signup","/auth/authenticate")
								.permitAll()
								.anyRequest()
								.authenticated();})
//								.httpBasic()
//								.and()
								.sessionManagement()
								.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
								.and()
								.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
//								.logout().invalidateHttpSession(true).clearAuthentication(true)
								.authenticationProvider(authProvider())
								.authenticationManager(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)))
								.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
//								.formLogin()
								;

		return http.build();
	}
	
	

	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
		daoAuthProvider.setUserDetailsService(userDetailsService);
		daoAuthProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

//	@Bean
//	public AuthenticationManager authenticationManager(HttpSecurity http) 
//	  throws Exception {
//	    return http.getSharedObject(AuthenticationManagerBuilder.class)
//	      .authenticationProvider(authProvider())
//	      .build();
//	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	    return authenticationConfiguration.getAuthenticationManager();
	}
}
