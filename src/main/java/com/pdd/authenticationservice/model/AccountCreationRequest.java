package com.pdd.authenticationservice.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountCreationRequest {
	
	@NotBlank(message = "{accountcreationrequest.username.notblank}")
	@Size(min = 5, max=20, message = "{accountcreationrequest.username.size}")
    private String username;
	
//	@NotBlank(message = "{accountcreationrequest.password.notblank}")
//    @Size(min = 6, max=50, message = "{accountcreationrequest.password.size}")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$", message = "{accountcreationrequest.password.pattern}")
    private String password;
    
	@NotEmpty(message = "{accountcreationrequest.fname.notblank}")
    private String firstName;
	@NotEmpty(message = "{accountcreationrequest.lname.notblank}")
    private String lastName;
    
    @Email(message = "{accountcreationrequest.email.pattern}")
    private String email;
}
