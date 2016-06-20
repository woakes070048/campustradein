package com.cti.auth;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import com.cti.model.UserAccount;
import com.google.common.base.MoreObjects;

/**
 * @author ifeify
 */
public class AuthenticationToken {
	private static final int EXPIRATION_TIME = 60 * 24; // 24 hours
	private static final SecureRandom secureRandom = new SecureRandom();
	private UserAccount userAccount;
	private String token;
	private LocalDateTime expirationTime;

	public static String generate() {
        return new BigInteger(130, secureRandom).toString(32);
	}

	public AuthenticationToken(UserAccount userAccount) {
		this.userAccount = userAccount;
		this.token = UUID.randomUUID().toString();
		this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
	}
	
	public AuthenticationToken(UserAccount userAccount, long expirationTime) {
		this.userAccount = userAccount;
		this.token = UUID.randomUUID().toString();
		this.expirationTime = calculateExpirationDate(expirationTime);
	}

	private LocalDateTime calculateExpirationDate(long expirationTime) {
		LocalDateTime now = LocalDateTime.now();
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

	public String toString() {
		return MoreObjects.toStringHelper(this)
							.add("Token ID", token)
							.add("UserAccount", userAccount)
							.add("Expiration date", expirationTime)
							.toString();
	}
}
