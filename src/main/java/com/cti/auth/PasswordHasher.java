package com.cti.auth;

import com.cti.exception.EncryptionException;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;

public class PasswordHasher {
	@Inject
	private Encrypter encrypter;
	
	public PasswordHasher() {}
	
	@Inject
	public PasswordHasher(Encrypter encryptionStrategy) {
		this.encrypter = encryptionStrategy;
	}
	
	@Inject
	public void setEncryptionStrategy(Encrypter encryptionStrategy) {
		this.encrypter = encryptionStrategy;
	}
	
	/**
	 * Generates a random salt
	 * @return
	 */
	private byte[] generateSalt() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] salt = new byte[PasswordFormat.SALT_BYTE_SIZE];
		secureRandom.nextBytes(salt);
		return salt;
	}
	
	/**
	 * Generates the hash of the password in the format
	 * algorithname:iterations:hashSize:salt:hash
	 * @param password
	 * @return
	 */
	public String generateHash(@NotNull String password) throws EncryptionException {
		byte[] salt = generateSalt();
		byte[] passwordHash = encrypter.encrypt(password.toCharArray(), salt, PasswordFormat.ITERATIONS, PasswordFormat.HASH_BYTE_SIZE);
		
		StringBuilder pwBuilder = new StringBuilder();
		pwBuilder.append(encrypter.getAlgorithmName());
		pwBuilder.append(PasswordFormat.DELIMITER);
		pwBuilder.append(PasswordFormat.ITERATIONS);
		pwBuilder.append(PasswordFormat.DELIMITER);
		pwBuilder.append(passwordHash.length);
		pwBuilder.append(PasswordFormat.DELIMITER);
		pwBuilder.append(DatatypeConverter.printBase64Binary(salt));
		pwBuilder.append(PasswordFormat.DELIMITER);
		pwBuilder.append(DatatypeConverter.printBase64Binary(passwordHash));
		
		return pwBuilder.toString();
	}
}
