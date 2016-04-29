package com.cti.model;

import java.time.LocalDateTime;

public class User {
	private String username;
	private String email;
	private String password;
	private LocalDateTime dateJoined;
	private boolean isActive;
	
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
	
	public boolean isActivated() {
		return isActive;
	}
	
	public void activate() {
		isActive = true;
	}
	
	public LocalDateTime memberSince() {
		return dateJoined;
	}
	
	public void joinedOn(LocalDateTime now) {
		this.dateJoined = now;
	}
}
