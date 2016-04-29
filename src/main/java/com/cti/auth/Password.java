package com.cti.auth;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.Arrays;

import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;

import com.cti.exception.EncryptionException;
import com.cti.exception.PasswordParseException;

public class Password {
	public static final String DELIMITER = ":";
	private Encrypter encrypter;
	private byte[] hash;
	private byte[] salt;
	private int iterations;
	
	public Password(PasswordBuilder builder) {
		this.salt = builder.salt;
		this.iterations = builder.iterations;
		this.hash = builder.hash;
		this.encrypter = builder.encrypter;
	}
	
	public Password(PasswordParser parser) {
		this.salt = parser.salt;
		this.hash = parser.hash;
		this.iterations = iterations;
		this.encrypter = encrypter;
	}
	
	public byte[] getHash() {
		return hash;
	}
	
	public int getHashSize() {
		return hash.length;
	}

	public byte[] getSalt() {
		return salt;
	}

	public int getIterations() {
		return iterations;
	}
	
	public Encrypter getEncrypter() {
		return encrypter;
	}
	
	@Override
	public boolean equals(Object obj) {
		// implement slow equals
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(encrypter.getAlgorithmName());
		sb.append(Password.DELIMITER);
		sb.append(iterations);
		sb.append(Password.DELIMITER);
		sb.append(hash.length);
		sb.append(Password.DELIMITER);
		sb.append(DatatypeConverter.printBase64Binary(salt));
		sb.append(Password.DELIMITER);
		sb.append(DatatypeConverter.printBase64Binary(hash));
		return sb.toString();
	}
	
	public static class PasswordBuilder {
		@Inject
		private Encrypter encrypter;
		private int iterations = PasswordFormat.ITERATIONS;
		private int hashSize = PasswordFormat.HASH_BYTE_SIZE;
		private int saltSize = PasswordFormat.SALT_BYTE_SIZE;
		private byte[] salt;
		private byte[] hash;
		
		public PasswordBuilder iterations(int iter) {
			this.iterations = iter;
			return this;
		}
		
		public PasswordBuilder hashSize(int size) {
			this.hashSize = size;
			return this;
		}
		
		public PasswordBuilder saltSize(int size) {
			this.saltSize = size;
			return this;
		}
		
		public PasswordBuilder salted() {
			SecureRandom secureRandom = new SecureRandom();
			salt = new byte[saltSize];
			secureRandom.nextBytes(salt);
			return this;
		}
		
		public PasswordBuilder hashed(String password) throws EncryptionException {
			hash = encrypter.encrypt(password.toCharArray(), salt, iterations, hashSize);
			return this;
		}
		
		public Password build() {
			return new Password(this);
		}
	}
	
	public static class PasswordParser {
		@Inject
		private EncrypterFactory encrypterFactory;
		
		@Inject
		public PasswordParser(EncrypterFactory encrypterFactory) {
			this.encrypterFactory = encrypterFactory;
		}

		public Password parse(String encryptedPassword)
										throws PasswordParseException {
			String[] parts = encryptedPassword.split(PasswordFormat.DELIMITER);
			if (parts.length != PasswordFormat.HASH_SECTIONS) {
				String errorMessage = MessageFormat
						.format("Fields missing from password hash. Expected {0} sections. Found only {1}",
								PasswordFormat.HASH_SECTIONS, parts.length);
				throw new PasswordParseException(errorMessage);
			}

			String algorithmName = parts[PasswordFormat.HASH_ALGORITHM_INDEX];

			int iterations = Integer.parseInt(parts[PasswordFormat.ITERATION_INDEX]);
			if(iterations < 1) {
				throw new PasswordParseException(
						"Number of iterations must be >= 1");
			}

			salt = DatatypeConverter
					.parseBase64Binary(parts[PasswordFormat.SALT_INDEX]);
			hash = DatatypeConverter
					.parseBase64Binary(parts[PasswordFormat.HASH_INDEX]);

			int storedHashSize = Integer
					.parseInt(parts[PasswordFormat.HASH_SIZE_INDEX]);
			if (storedHashSize != hash.length) {
				String errorMessage = MessageFormat.format(
						"Hash size mismatch. Stored hash size length is {0}. "
								+ "Computed hash length is {1}", storedHashSize,
						hash.length);
				throw new PasswordParseException(errorMessage);
			}
		}

	}
}
