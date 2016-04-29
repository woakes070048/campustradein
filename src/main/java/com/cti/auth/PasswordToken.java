package com.cti.auth;

import java.security.SecureRandom;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.apache.commons.codec.binary.Base64;

public class PasswordToken {
	// These constants may be changed without breaking existing hashes.
    public static final int SALT_BYTE_SIZE = 24;
    public static final int HASH_BYTE_SIZE = 18;
    public static final int ITERATIONS = 64000;

    // These constants define the encoding and may not be changed.
    public static final int HASH_SECTIONS = 5;
    public static final int HASH_ALGORITHM_INDEX = 0;
    public static final int ITERATION_INDEX = 1;
    public static final int HASH_SIZE_INDEX = 2;
    public static final int SALT_INDEX = 3;
    public static final int HASH_INDEX = 4;
    
    @Inject
	private Encrypter encryptionStrategy;
	
	public PasswordToken() {}
	
	@Inject
	public PasswordToken(Encrypter encryptionStrategy) {
		this.encryptionStrategy = encryptionStrategy;
	}
	
	@Inject
	public void setEncryptionStrategy(Encrypter encryptionStrategy) {
		this.encryptionStrategy = encryptionStrategy;
	}
	
	/**
	 * Generates a random salt
	 * @return
	 */
	private byte[] generateSalt() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] salt = new byte[SALT_BYTE_SIZE];
		secureRandom.nextBytes(salt);
		return salt;
	}
	
	/**
	 * Generates the hash of the password in the format
	 * algorithname:iterations:hashSize:salt:hash
	 * @param password
	 * @return
	 */
	public String generate(@NotNull String password) throws Exception {
		byte[] salt = generateSalt();
		byte[] passwordHash = encryptionStrategy.encrypt(password.toCharArray(), salt, ITERATIONS, HASH_BYTE_SIZE);
		
		StringBuilder pwBuilder = new StringBuilder();
		pwBuilder.append(encryptionStrategy.getAlgorithmName());
		pwBuilder.append(":");
		pwBuilder.append(ITERATIONS);
		pwBuilder.append(":");
		pwBuilder.append(passwordHash.length);
		pwBuilder.append(":");
		pwBuilder.append(Base64.encodeBase64(salt));
		pwBuilder.append(":");
		pwBuilder.append(Base64.encodeBase64(passwordHash));
		
		return pwBuilder.toString();
	}
}
