package com.cti.auth;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.format.datetime.joda.LocalDateTimeParser;
import org.springframework.format.datetime.joda.LocalTimeParser;

import com.cti.model.User;
import com.google.common.base.MoreObjects;

/**
 * Created by ifeify on 5/2/16.
 */
public class AuthenticationToken {
	private static final int EXPIRATION_TIME = 60 * 24; // 24 hours
	private User user;
	private String token;
	private LocalTime expirationTime;
	private boolean verified;
	
	public AuthenticationToken(User user) {
		this.user = user;
		this.token = UUID.randomUUID().toString();
		this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
		this.verified = false;
	}
	
	public AuthenticationToken(User user, long expirationTime) {
		this.user = user;
		this.token = UUID.randomUUID().toString();
		this.expirationTime = calculateExpirationDate(expirationTime);
		this.verified = false;
	}

	private LocalTime calculateExpirationDate(long expirationTime) {
		LocalDateTime now = LocalDateTime.now();
		return now.plusMinutes(expirationTime).toLocalTime();
	}
	
	public boolean hasExpired() {
		return expirationTime.isBefore(LocalTime.now());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}
	
	public LocalTime getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(LocalTime expirationTime) {
		this.expirationTime = expirationTime;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	
	public String toString() {
		return MoreObjects.toStringHelper(AuthenticationToken.class)
							.add("Token ID", token)
							.add("User", user)
							.add("Expiration date", expirationTime)
							.toString();
	}
}
