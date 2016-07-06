package com.cti.common.auth;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author ifeify
 */
public class TokenGenerator {
	private static final SecureRandom secureRandom = new SecureRandom();

	public static String generate() {
        return new BigInteger(130, secureRandom).toString(32);
	}
}
