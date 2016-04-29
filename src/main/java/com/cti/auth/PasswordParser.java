package com.cti.auth;

import java.text.ParseException;

import javax.validation.constraints.NotNull;

import org.apache.commons.codec.binary.Base64;

public class PasswordParser {
	private String password;
	private String delimiter;
	private String algorithmName;
	private int iterations;
	private byte[] salt;
	private byte[] hash;
	
	public PasswordParser(@NotNull String storedPassword, String delimiter) {
		this.password = storedPassword;
		this.delimiter = delimiter;
	}
	
	public void parse() throws ParseException, NumberFormatException {
		String[] parts = password.split(delimiter);
		if (parts.length != PasswordToken.HASH_SECTIONS) {
			throw new ParseException("Fields missing from password hash", 0);
		}

		algorithmName = parts[PasswordToken.HASH_ALGORITHM_INDEX];

		iterations = Integer.parseInt(parts[PasswordToken.ITERATION_INDEX]);
		if (iterations < 1) {
			throw new NumberFormatException("Number of iterations must be >= 1");
		}

		salt = Base64.decodeBase64(parts[PasswordToken.SALT_INDEX]);
		hash = Base64.decodeBase64(parts[PasswordToken.HASH_INDEX]);

		int storedHashSize = Integer
				.parseInt(parts[PasswordToken.HASH_SIZE_INDEX]);
		if (storedHashSize != hash.length) {
			throw new NumberFormatException("Hash size mismatch");
		}
	}

	public String getAlgorithmName() {
		return algorithmName;
	}

	public int getIterations() {
		return iterations;
	}

	public byte[] getSalt() {
		return salt;
	}

	public byte[] getHash() {
		return hash;
	}
}
