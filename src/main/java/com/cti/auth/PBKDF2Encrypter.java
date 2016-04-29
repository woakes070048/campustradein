package com.cti.auth;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PBKDF2Encrypter implements Encrypter {
	@Override
	public String getAlgorithmName() {
		return "PBKDF2WithHmacSHA1";
	}

	@Override
	public byte[] encrypt(char[] password, byte[] salt, int iterations, int hashSize) 
														throws NoSuchAlgorithmException, InvalidKeySpecException {
		PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterations, hashSize * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(getAlgorithmName());
		return skf.generateSecret(keySpec).getEncoded();
	}

}
