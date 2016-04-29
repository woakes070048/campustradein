package com.cti.auth;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import javax.naming.AuthenticationException;

import com.cti.exception.PasswordMismatchException;

public class PasswordVerifier {
	private String storedPassword;
	private String testPassword;
	
	public PasswordVerifier(String storedPassword, String testPassword) {
		this.storedPassword = storedPassword;
		this.testPassword = testPassword;
	}
	
	/**
	 * Compute hash of the provided password using the same salt, hash
	 * and hash length of the stored password
	 * @throws AuthenticationException
	 */
	public void verify() throws PasswordMismatchException {
		try {
			PasswordParser parser = new PasswordParser(storedPassword, PasswordFormat.DELIMITER);
			parser.parse();
			String algoName = parser.getAlgorithmName();
			int iterations = parser.getIterations();
			byte[] salt = parser.getSalt();
			byte[] hash = parser.getHash();
			
			// generate hash for supplied password using the same parameters that
			// generated the original password
			Encrypter encrypter = EncrypterFactory.getEncrypter(algoName);
			byte[] testHash = encrypter.encrypt(testPassword.toCharArray(), salt, iterations, hash.length);
			
			if(!isEqual(hash, testHash)) {
				throw new PasswordMismatchException("Passwords do not match");
			}
			
		} catch(ParseException | NumberFormatException e) {
			throw new PasswordMismatchException("Failed to parse stored password", e);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new PasswordMismatchException("Failed to generate ahash for the test passwod", e);
		} 
	}
	
	/**
	 * Perform a slow equals to check if both passwords are the same
	 */
	private static boolean isEqual(byte[] storedHash, byte[] testHash) {
		int diff = storedHash.length ^ testHash.length;
        for(int i = 0; i < storedHash.length && i < testHash.length; i++)
            diff |= storedHash[i] ^ testHash[i];
        return diff == 0;
	}
}
