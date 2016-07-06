package com.cti.service;

import com.cti.common.auth.Credential;
import com.cti.common.auth.Password;
import com.cti.common.auth.Password.PasswordParser;
import com.cti.common.exception.AuthenticationException;
import com.cti.common.exception.EncryptionException;
import com.cti.common.exception.PasswordParseException;
import com.cti.common.exception.UserNotFoundException;
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
    public Credential authenticate(String usernameOrEmail, String password) throws AuthenticationException {
        try {
            usernameOrEmail = usernameOrEmail.toLowerCase();
            Optional<Credential> result = userRepository.getUserCredential(usernameOrEmail);
            if (!result.isPresent()) {
                throw new AuthenticationException(usernameOrEmail + " is not a recognized user/email");
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
            if(!loginPassword.equals(storedPassword)) {
                throw new AuthenticationException("Failed to authenticate " + usernameOrEmail);
            }
            return credential;
        } catch(PasswordParseException e) {
            throw new AuthenticationException(e);
        } catch (EncryptionException e) {
            throw new AuthenticationException(e);
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
