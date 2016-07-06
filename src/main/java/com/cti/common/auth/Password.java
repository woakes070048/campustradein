package com.cti.common.auth;

import com.cti.common.exception.EncryptionException;
import com.cti.common.exception.PasswordParseException;

import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;
import java.text.MessageFormat;

public class Password {
	public static final String DELIMITER = ":";
	private Encrypter encrypter;
	private byte[] hash;
	private byte[] salt;
	private int iterations;

    public Password() {}
	
	public Password(Builder builder) {
		this.salt = builder.salt;
		this.iterations = builder.iterations;
		this.hash = builder.hash;
		this.encrypter = builder.encrypter;
	}

    public void setEncrypter(Encrypter encrypter) {
        this.encrypter = encrypter;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
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
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof Password)) {
            return false;
        }
        Password matchingPassword = (Password)obj;
        // TODO: make sure other fields match
        if((iterations != matchingPassword.iterations) &&
                (salt.length != matchingPassword.salt.length)
                    && (hash.length != matchingPassword.hash.length)
                    && (encrypter.getAlgorithmName()
                            != matchingPassword.encrypter.getAlgorithmName())){
            return false;
        }
		// perform slow equals
        int diff = hash.length ^ matchingPassword.hash.length;
        for(int i = 0; i < hash.length && i < matchingPassword.hash.length; i++)
            diff |= hash[i] ^ matchingPassword.hash[i];
        return diff == 0;
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
	
	public static class Builder {
		private Encrypter encrypter;
		private int iterations = PasswordFormat.ITERATIONS;
		private int hashSize = PasswordFormat.HASH_BYTE_SIZE;
		private int saltSize = PasswordFormat.SALT_BYTE_SIZE;
        private String password;
		private byte[] salt;
		private byte[] hash;
        private boolean saltProvided = false;
		
		public Builder iterations(int iter) {
			this.iterations = iter;
			return this;
		}

        public Builder plainTextPassword(String password) {
            this.password = password;
            return this;
        }
		
		public Builder hashSize(int size) {
			this.hashSize = size;
			return this;
		}

		public Builder useEncrypter(Encrypter encrypter) {
            this.encrypter = encrypter;
            return this;
        }
		
		public Builder saltSize(int size) {
			this.saltSize = size;
			return this;
		}
		
		private void generateSalt() {
			SecureRandom secureRandom = new SecureRandom();
			salt = new byte[saltSize];
			secureRandom.nextBytes(salt);
		}
		
		public Password hash() throws EncryptionException {
            if(!saltProvided) {
                generateSalt();
            }
			hash = encrypter.encrypt(password.toCharArray(), salt, iterations, hashSize);
			return new Password(this);
		}
		
		public Password build() {
			return new Password(this);
		}

        public Builder useSalt(byte[] salt) {
            this.salt = salt;
            this.saltSize = salt.length;
            saltProvided = true;
            return this;
        }
    }
	
	public static class PasswordParser {
		public static Password parse(String encryptedPassword)
										throws PasswordParseException {
			String[] parts = encryptedPassword.split(PasswordFormat.DELIMITER);
			if (parts.length != PasswordFormat.HASH_SECTIONS) {
				String errorMessage = MessageFormat
						.format("Fields missing from password hash. Expected {0} sections. Found only {1}",
								PasswordFormat.HASH_SECTIONS, parts.length);
				throw new PasswordParseException(errorMessage);
			}

			String algorithmName = parts[PasswordFormat.HASH_ALGORITHM_INDEX];
            Encrypter encrypter = EncrypterFactory.getEncrypter(algorithmName);

			int iterations = Integer.parseInt(parts[PasswordFormat.ITERATION_INDEX]);
			if(iterations < 1) {
				throw new PasswordParseException(
						"Number of iterations must be >= 1");
			}

			byte[] salt = DatatypeConverter
					.parseBase64Binary(parts[PasswordFormat.SALT_INDEX]);
			byte[] hash = DatatypeConverter
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
            Password password = new Password();
            password.setEncrypter(encrypter);
            password.setHash(hash);
            password.setSalt(salt);
            password.setIterations(iterations);
            return password;
		}
	}
}
