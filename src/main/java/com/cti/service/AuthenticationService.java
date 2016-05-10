package com.cti.service;

import com.cti.auth.Password;
import com.cti.auth.Password.PasswordBuilder;
import com.cti.auth.Password.PasswordParser;
import com.cti.exception.EncryptionException;
import com.cti.exception.PasswordParseException;

public class AuthenticationService {
	
	public boolean isPasswordCorrect(String correctHash, String suppliedPassword) 
													throws PasswordParseException, EncryptionException {
		Password dbPassword = PasswordParser.parse(correctHash);
		Password userPassword = encryptPassword(suppliedPassword, dbPassword);
		return dbPassword.equals(userPassword);
	}
	
	public Password encryptPassword(String plainTextPassword, Password template) throws EncryptionException {
        return new PasswordBuilder()
                    .plainTextPassword(plainTextPassword)
                    .useSalt(template.getSalt())
                    .iterations(template.getIterations())
                    .hashSize(template.getHashSize())
                    .useEncrypter(template.getEncrypter())
                    .hash();
    }
}
