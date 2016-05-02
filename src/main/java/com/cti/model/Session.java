package com.cti.model;

import org.apache.commons.codec.binary.Base64;

import java.security.SecureRandom;

public class Session {
    public static final int BYTE_SIZE = 32;

	public static String generate() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[BYTE_SIZE];
        secureRandom.nextBytes(randomBytes);
        return new String(Base64.encodeBase64(randomBytes));
	}
}
