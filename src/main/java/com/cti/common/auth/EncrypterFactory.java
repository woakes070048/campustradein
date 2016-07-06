package com.cti.common.auth;

public class EncrypterFactory {
	public static Encrypter getEncrypter(String type) {
		if(type.equals("PBKDF2WithHmacSHA1")) {
			return new PBKDF2Encrypter();
		}
		return null;
	}
}
