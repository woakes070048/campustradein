package com.cti.service;

import com.cti.auth.Credential;
import com.cti.auth.Password;
import com.cti.auth.Password.PasswordParser;
import com.cti.exception.EncryptionException;
import com.cti.exception.PasswordParseException;
import com.cti.exception.UserNotFoundException;
import com.cti.repository.SessionRepository;
import com.cti.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Optional;

public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	@Inject
    private UserRepository userRepository;
    @Inject
    private SessionRepository sessionRepository;

    /**
     * @param usernameOrEmail the username or email of the user
     * @param password password to authenticate with
     * @return true if user is was successfully logged in. false if the usernameOrEmail does not
     * exist or in case a password parse and encryption exception occurs.
     */
    public boolean login(String usernameOrEmail, String password) {
        try {
            usernameOrEmail = usernameOrEmail.toLowerCase();
            Optional<Credential> result = userRepository.getUserCredential(usernameOrEmail);
            if (!result.isPresent()) {
                logger.error("{} is not recognized", usernameOrEmail);
                return false;
            }
            Credential credential = result.get();
            Password storedPassword = PasswordParser.parse(credential.getEncryptedPassword());
            // encrypt the supplied password with the same way the stored password was encrypted
            Password loginPassword = new Password.Builder()
                                                    .useSalt(storedPassword.getSalt())
                                                    .iterations(storedPassword.getIterations())
                                                    .plainTextPassword(password)
                                                    .useEncrypter(storedPassword.getEncrypter())
                                                    .hashSize(storedPassword.getHashSize())
                                                    .hash();
            return loginPassword.equals(storedPassword);
        } catch(PasswordParseException e) {
            logger.error("Failed to parse stored password for {}", usernameOrEmail);
            return false;
        } catch (EncryptionException e) {
            logger.error("Failed to encrypt login password for {}", usernameOrEmail);
            return false;
        }
    }

    /**
     * Logs the user out of the application and deletes any authentication and session tokens
     * @param username
     * @throws UserNotFoundException
     */
    public boolean logout(String username) {
        try {
            sessionRepository.deleteSession(username);
            return true;
        } catch (UserNotFoundException e) {
            logger.error(username + " does not exist");
            return false;
        }
    }
}
