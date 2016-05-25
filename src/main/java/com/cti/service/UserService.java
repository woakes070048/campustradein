package com.cti.service;

import java.text.MessageFormat;

import javax.inject.Inject;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;

import com.cti.annotation.encrypter.PBKDF2;
import com.cti.auth.AuthenticationToken;
import com.cti.auth.Encrypter;
import com.cti.auth.Password;
import com.cti.dto.UserDTO;
import com.cti.exception.DuplicateTokenException;
import com.cti.exception.EncryptionException;
import com.cti.exception.InvalidTokenException;
import com.cti.exception.UserAlreadyExistsException;
import com.cti.model.User;
import com.cti.repository.SessionRepository;
import com.cti.repository.TokenRepository;
import com.cti.repository.UserRepository;

public class UserService {
	@Inject
	private UserRepository userRepository;
	
	@Inject
	private TokenRepository tokenRepository;
	
	@Inject
	private SessionRepository sessionRepository;
	
	@Inject
    @PBKDF2
	private Encrypter encrypter;
    
//	@Inject
//	public UserService(UserRepository userRepository, 
//						TokenRepository tokenRepository,
//						SessionRepository sessionRepository) {
//		this.userRepository = userRepository;
//		this.tokenRepository = tokenRepository;
//		this.sessionRepository = sessionRepository;
//	}

	public User createNewUserAccount(UserDTO userDTO)
							throws ValidationException, UserAlreadyExistsException {
		try {
            
            Password encryptedPassword = encrypt(userDTO.getPassword());
			User user = new User();
			user.setUsername(userDTO.getUsername());
			user.setPassword(encryptedPassword.toString());
			user.setEmail(userDTO.getEmail());
			user.setCollege(userDTO.getCollege());
			userRepository.save(user);
			return user;
		} catch (Exception e) {
			throw new UserAlreadyExistsException(MessageFormat.format(
					"{0} with email {1} is already a registered member",
					userDTO.getUsername(), userDTO.getEmail()), e);
		}
	}
	
	public void activateUser(User user, String token) {
		user.activate();
		userRepository.update(user);
		tokenRepository.delete(token);
	}

	public AuthenticationToken createVerificationToken(User user) throws DuplicateTokenException {
		AuthenticationToken token = new AuthenticationToken(user);
		tokenRepository.save(token);
		return token;
    }

    public AuthenticationToken getVerificationToken(String token) throws InvalidTokenException {
        return tokenRepository.findByTokenId(token);
    }

	public boolean isUsernameAlreadyTaken(@NotNull String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			return false;
		}
		return true;
	}

	public boolean isEmailAlreadyTaken(String email) {
		User user = userRepository.findByEmail(email);
		if(user == null) {
			return false;
		}
		return true;
	}

    private Password encrypt(String plainTextPassword) throws EncryptionException {
        return new Password.PasswordBuilder()
                .plainTextPassword(plainTextPassword)
                .useEncrypter(encrypter)
                .hash();
    }

    private Password encrypt(String plainTextPassword, Password template) throws EncryptionException {
        return new Password.PasswordBuilder()
                .plainTextPassword(plainTextPassword)
                .useSalt(template.getSalt())
                .iterations(template.getIterations())
                .hashSize(template.getHashSize())
                .useEncrypter(template.getEncrypter())
                .hash();
    }

	public AuthenticationToken startSession(User user) throws DuplicateTokenException {
		AuthenticationToken sessionToken = new AuthenticationToken(user);
		sessionRepository.save(sessionToken);
		return sessionToken;
	}
	
	public void endSession(String sessionID) throws InvalidTokenException {
		sessionRepository.delete(sessionID);
	}

	public User findUserBySessionID(String sessionID) {
		try {
			return sessionRepository.findBySessionID(sessionID);
		} catch (InvalidTokenException e) {
			return null;
		}
	}

	public User findByUsername(String uname) {
		return userRepository.findByUsername(uname);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
