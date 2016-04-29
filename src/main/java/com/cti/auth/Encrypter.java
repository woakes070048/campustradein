package com.cti.auth;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.cti.exception.EncryptionException;

public interface Encrypter {
	public String getAlgorithmName();
	public byte[] encrypt(char[] data, byte[] salt, int iterations, int hashSize) 
														throws EncryptionException;
}
