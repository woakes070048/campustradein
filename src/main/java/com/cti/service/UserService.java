package com.cti.service;

import javax.inject.Inject;

import com.cti.annotation.encrypter.PBKDF2;
import com.cti.auth.Encrypter;
import com.cti.repository.SessionRepository;
import com.cti.repository.TokenRepository;

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

//	public UserAccount createNewUserAccount(UserDTO userDTO)
//							throws ValidationException, UserAlreadyExistsException {
//		try {
//
//            Password encryptedPassword = encrypt(userDTO.getPassword());
//			UserAccount userAccount = new UserAccount();
//			userAccount.setUsername(userDTO.getUsername());
//			userAccount.setPassword(encryptedPassword.toString());
//			userAccount.setEmail(userDTO.getEmail());
//			userAccount.setCollege(userDTO.getCollege());
//			userRepository.save(userAccount);
//			return userAccount;
//		} catch (Exception e) {
//			throw new UserAlreadyExistsException(MessageFormat.format(
//					"{0} with email {1} is already a registered member",
//					userDTO.getUsername(), userDTO.getEmail()), e);
//		}
//	}
//
//	public void activateUser(UserAccount userAccount, String token) {
//		userAccount.activateAccount();
//		userRepository.update(userAccount);
//		tokenRepository.delete(token);
//	}
//
//	public TokenGenerator createVerificationToken(UserAccount userAccount) throws DuplicateTokenException {
//		TokenGenerator token = new TokenGenerator(userAccount);
//		tokenRepository.save(token);
//		return token;
//    }
//
//    public TokenGenerator getVerificationToken(String token) throws InvalidTokenException {
//        return tokenRepository.findUserByTokenId(token);
//    }
//
//	public boolean isUsernameAlreadyTaken(@NotNull String username) {
//		UserAccount userAccount = userRepository.findByUsername(username);
//		if (userAccount == null) {
//			return false;
//		}
//		return true;
//	}
//
//	public boolean isEmailAlreadyTaken(String email) {
//		UserAccount userAccount = userRepository.findByEmail(email);
//		if(userAccount == null) {
//			return false;
//		}
//		return true;
//	}
//
//    private Password encrypt(String plainTextPassword) throws EncryptionException {
//        return new Password.PasswordBuilder()
//                .plainTextPassword(plainTextPassword)
//                .useEncrypter(encrypter)
//                .hash();
//    }
//
//    private Password encrypt(String plainTextPassword, Password template) throws EncryptionException {
//        return new Password.PasswordBuilder()
//                .plainTextPassword(plainTextPassword)
//                .useSalt(template.getSalt())
//                .iterations(template.getIterations())
//                .hashSize(template.getHashSize())
//                .useEncrypter(template.getEncrypter())
//                .hash();
//    }
//
//	public TokenGenerator startSession(UserAccount userAccount) throws DuplicateTokenException {
//		TokenGenerator sessionToken = new TokenGenerator(userAccount);
//		sessionRepository.save(sessionToken);
//		return sessionToken;
//	}
//
//	public void endSession(String sessionID) throws InvalidTokenException {
//		sessionRepository.delete(sessionID);
//	}
//
//	public UserAccount findUserBySessionID(String sessionID) {
//		try {
//			return sessionRepository.findBySessionID(sessionID);
//		} catch (InvalidTokenException e) {
//			return null;
//		}
//	}
//
//	public UserAccount findByUsername(String uname) {
//		return userRepository.findByUsername(uname);
//	}
//
//	public UserAccount findByEmail(String email) {
//		return userRepository.findByEmail(email);
//	}
}
