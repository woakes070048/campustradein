package com.cti.auth;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import com.cti.model.UserAccount;
import com.google.common.base.MoreObjects;

/**
 * Created by ifeify on 5/2/16.
 */
public class AuthenticationToken {
	private static final int EXPIRATION_TIME = 60 * 24; // 24 hours
	private UserAccount userAccount;
	private String token;
	private LocalDateTime expirationTime;
	private boolean verified;
	
	public AuthenticationToken(UserAccount userAccount) {
		this.userAccount = userAccount;
		this.token = UUID.randomUUID().toString();
		this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
		this.verified = false;
	}
	
	public AuthenticationToken(UserAccount userAccount, long expirationTime) {
		this.userAccount = userAccount;
		this.token = UUID.randomUUID().toString();
		this.expirationTime = calculateExpirationDate(expirationTime);
		this.verified = false;
	}

	private LocalDateTime calculateExpirationDate(long expirationTime) {
		LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Chicago"));
		return now.plusMinutes(expirationTime);
	}
	
	public boolean hasExpired() {
		return expirationTime.isBefore(LocalDateTime.now());
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public String getToken() {
		return token;
	}
	
	public LocalDateTime getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(LocalDateTime expirationTime) {
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
							.add("UserAccount", userAccount)
							.add("Expiration date", expirationTime)
							.toString();
	}
}
