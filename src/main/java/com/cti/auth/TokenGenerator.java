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
public class TokenGenerator {
	private static final SecureRandom secureRandom = new SecureRandom();

	public static String generate() {
        return new BigInteger(130, secureRandom).toString(32);
	}
}
