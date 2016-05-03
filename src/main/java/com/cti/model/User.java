package com.cti.model;

import com.google.common.base.MoreObjects;

import java.time.LocalDateTime;

public class User {
	private String username;
	private String email;
	private String password;
	private String college;
	private LocalDateTime dateJoined;
	private boolean isActive;

    public User() {
		dateJoined = LocalDateTime.now();
		isActive = false;
	}
	
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
	
	public void deactivate() {
		isActive = false;
	}
	
	public LocalDateTime memberSince() {
		return dateJoined;
	}
	
	public void setCollege(String college) {
		this.college = college;
	}
	
	public String getCollege() {
		return college;
	}
	
	public String toString() {
		return MoreObjects.toStringHelper(User.class)
					.add("username", username)
					.add("email", email)
					.add("password", password)
					.add("college", college)
					.add("dateJoined", dateJoined)
					.add("isActive?", isActive)
					.toString();
	}
}
