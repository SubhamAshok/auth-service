package com.pdd.authenticationservice.entity;

import java.time.LocalDateTime;

import com.pdd.authenticationservice.util.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Users")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	private String firstName;

	private String lastName;

	@Column(unique = true)
	private String username;

	private String password;

	@Column(unique = true)
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private LocalDateTime accountCreationDate;
}
