package com.cti.common.auth;

import com.cti.common.exception.EncryptionException;

public interface Encrypter {
	public String getAlgorithmName();
	public byte[] encrypt(char[] data, byte[] salt, int iterations, int hashSize) 
														throws EncryptionException;
}
