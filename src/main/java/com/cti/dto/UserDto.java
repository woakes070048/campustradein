package com.cti.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.cti.annotation.PasswordMatches;
import com.cti.annotation.ValidEmail;
import com.google.common.base.MoreObjects;

@PasswordMatches
public class UserDto {
	@NotNull(message = "Username not specified")
	@NotBlank(message = "Username not specified")
	@Pattern(regexp = "[a-zA-Z]*", message = "Username has invalid characters")
	@Size(min = 3)
	private String username;
	
	@ValidEmail
	@NotNull
	@NotBlank(message = "Email not specified")
	private String email;
	
	@NotNull
	@NotBlank(message = "Password not specified")
	@Size(min = 8)
	private String password;
	
	@NotNull
	@NotBlank(message = "Second password not specified")
	@Size(min = 8)
	private String password2;
	
	@NotNull
	private String college;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getMatchingPassword() {
		return password2;
	}
	
	public void setMatchingPassword(String password2) {
		this.password2 = password2;
	}
	
	public String getCollege() {
		return college;
	}
	
	public void setCollege(String college) {
		this.college = college;
	}
	
	public String toString() {
		return MoreObjects.toStringHelper(UserDto.class)
                            .add("username", username)
                            .add("email", email)
                            .add("college", college)
                            .toString();
	}
}
