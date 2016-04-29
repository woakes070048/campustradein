package com.cti.auth;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import javax.naming.AuthenticationException;

import org.apache.commons.codec.binary.Base64;

import com.cti.exception.EncryptionException;
import com.cti.exception.PasswordMismatchException;
import com.cti.exception.PasswordParseException;

public class PasswordVerifier {
	private Encrypter encrypter = EncrypterFactory.getEncrypter(algoName);
	
	PasswordVerifier() {
		this.storedPassword = storedPassword;
		this.testPassword = testPassword;
	}
	
	/**
	 * Compute hash of the provided password using the same salt, hash
	 * and hash length of the stored password
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws AuthenticationException
	 */
	public void verify(String storedPassword, String testPassword) 
						throws PasswordMismatchException, PasswordParseException, EncryptionException {
		PasswordParser parser = new PasswordParser(storedPassword,
				PasswordFormat.DELIMITER);
		parser.parse();
		String algoName = parser.getAlgorithmName();
		int iterations = parser.getIterations();
		byte[] salt = parser.getSalt();
		byte[] hash = parser.getHash();

		// generate hash for supplied password using the same parameters that
		// generated the original password
		
		byte[] testHash = encrypter.encrypt(testPassword.toCharArray(), salt,
				iterations, hash.length);

		if (!isEqual(hash, testHash)) {
			throw new PasswordMismatchException("Passwords do not match");
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
