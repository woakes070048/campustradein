package com.cti.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cti.annotation.PasswordMatches;
import com.cti.annotation.ValidEmail;
import com.google.common.base.MoreObjects;

@PasswordMatches
public class UserDto {
	@NotNull
	@Size(min = 3)
	private String username;
	
	@ValidEmail
	@NotNull
	private String email;
	
	@NotNull
	@Size(min = 8)
	private String password;
	
	@NotNull
	@Size(min = 8)
	private String password2;
	
	@NotNull
	private String college;
	
	private boolean isActive;
	private LocalDateTime dateJoined;
	
	public UserDto() {
		isActive = false;
		dateJoined = LocalDateTime.now();
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
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
	
	public LocalDateTime getDateJoined() {
		return dateJoined;
	}
	
	public void setDateJoined(LocalDateTime dateJoined) {
		this.dateJoined = dateJoined;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public String toString() {
		return MoreObjects.toStringHelper(UserDto.class)
                            .add("username", username)
                            .add("email", email)
                            .add("college", college)
                            .add("dateJoined", dateJoined)
                            .add("accountActive?", isActive)
                            .toString();
	}
	
	
}
